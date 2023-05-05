package com.jumkid.usercentral;

import com.jumkid.usercentral.model.ActivityEntity;
import com.jumkid.usercentral.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:10092", "port=10092" })
public class UserCentralAPITests {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private UserService userService;

	@Before
	public void setup() {
		List<ActivityEntity> activityEntities = APITestsSetup.buildActivityEntities();
		userService.saveAll(activityEntities);
	}

	@Test
	@WithMockUser(username="guest", password="guest", authorities="GUEST_ROLE")
	public void whenGuestCall_shouldGet401Forbidden() throws Exception {
		mockMvc.perform(get("/user/activities")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username="test", password="test", authorities="USER_ROLE")
	public void whenUserCall_shouldGetActivities() throws Exception {
		mockMvc.perform(get("/user/activities")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)));
	}

}
