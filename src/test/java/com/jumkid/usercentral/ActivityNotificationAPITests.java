package com.jumkid.usercentral;

import com.jumkid.usercentral.model.mapper.ActivityNotificationMapper;
import com.jumkid.usercentral.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {UserCentralApplication.class, H2JpaConfig.class})
@AutoConfigureMockMvc
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:10092", "port=10092" })
public class ActivityNotificationAPITests {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private UserService userService;
	@Autowired
	private ActivityNotificationMapper activityNotificationMapper;

	private static boolean isSetup = false;

	@Before
	public void setup() {
		if (!isSetup) {
			userService.saveAll(APITestsSetup.buildActivityNotificationEntities());
			isSetup = true;
		}
	}

	@Test
	@Order(1)
	@WithMockUser(username="guest", password="guest", authorities="GUEST_ROLE")
	public void whenGuestAccess_shouldGet401Forbidden() throws Exception {
		mockMvc.perform(get("/user/activityNotifications")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden());
	}

	@Test
	@Order(2)
	@WithMockUser(username="test", password="test", authorities="USER_ROLE")
	public void whenUserCall_shouldGetActivities() throws Exception {
		mockMvc.perform(get("/user/activityNotifications")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", not(empty())));
	}

	@Test
	@Order(3)
	@WithMockUser(username="test", password="test", authorities="USER_ROLE")
	public void whenGivenId_shouldGetActivityByUser() throws Exception {
		mockMvc.perform(get("/user/activityNotifications/2")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(2));
	}

	@Test
	@Order(4)
	@WithMockUser(username="guest", password="guest", authorities="GUEST_ROLE")
	public void whenNotOwnerGetActivity_shouldGet403Forbidden() throws Exception {
		mockMvc.perform(get("/user/activityNotifications/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden());
	}

	@Test
	@Order(5)
	@WithMockUser(username="test", password="test", authorities="USER_ROLE")
	public void whenGivenId_ShouldUpdateUnreadFlag() throws Exception {
		mockMvc.perform(put("/user/activityNotifications/2/unread")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isAccepted())
				.andExpect(jsonPath("$.success").value(true));
	}

	@Test
	@Order(6)
	@WithMockUser(username="test", password="test", authorities="USER_ROLE")
	public void whenGivenId_ShouldDeleteSuccessfully() throws Exception {
		mockMvc.perform(delete("/user/activityNotifications/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent())
				.andExpect(jsonPath("$.success").value(true));
	}

}
