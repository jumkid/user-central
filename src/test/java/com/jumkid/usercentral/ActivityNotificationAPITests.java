package com.jumkid.usercentral;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@PropertySource("classpath:application.share.properties")
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:10092", "port=10092" })
@EnableTestContainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ActivityNotificationAPITests {
	@LocalServerPort
	private int port;

	@Autowired
	private WebApplicationContext webApplicationContext;
	@Value("${com.jumkid.jwt.test.user-token}")
	private String testUserToken;

	@BeforeAll
	void setup() {
		//void
	}

	@Test
	@Order(1)
	void whenGuestAccess_shouldGet401Forbidden() throws Exception {
		RestAssured
				.given()
					.baseUri("http://localhost").port(port)
					.contentType(ContentType.JSON)
				.when()
					.get("/user/activityNotifications")
				.then()
					.log()
					.all()
					.statusCode(HttpStatus.FORBIDDEN.value());
	}

	@Test
	@Order(2)
	void whenUserCall_shouldGetActivities() throws Exception {
		RestAssured
				.given()
					.baseUri("http://localhost").port(port)
					.headers("Authorization", "Bearer " + testUserToken)
					.contentType(ContentType.JSON)
				.when()
					.get("/user/activityNotifications")
				.then()
					.log()
					.all()
					.statusCode(HttpStatus.OK.value())
					.body("size()", greaterThan(0));
	}

	@Test
	@Order(3)
	void whenGivenId_shouldGetActivityByUser() throws Exception {
		RestAssured
				.given()
					.baseUri("http://localhost").port(port)
					.headers("Authorization", "Bearer " + testUserToken)
					.contentType(ContentType.JSON)
				.when()
					.get("/user/activityNotifications/1")
				.then()
					.log()
					.all()
					.statusCode(HttpStatus.OK.value())
					.body("id", equalTo(1));
	}

	@Test
	@Order(4)
	void whenNotOwnerGetActivity_shouldGet403Forbidden() throws Exception {
		RestAssured
				.given()
					.baseUri("http://localhost").port(port)
					.contentType(ContentType.JSON)
				.when()
					.get("/user/activityNotifications/1")
				.then()
					.log()
					.all()
					.statusCode(HttpStatus.FORBIDDEN.value());
	}

	@Test
	@Order(5)
	void whenGivenId_ShouldUpdateUnreadFlag() throws Exception {
		RestAssured
				.given()
					.baseUri("http://localhost").port(port)
					.headers("Authorization", "Bearer " + testUserToken)
					.contentType(ContentType.JSON)
				.when()
					.put("/user/activityNotifications/1/unread")
				.then()
					.log()
					.all()
					.statusCode(HttpStatus.ACCEPTED.value())
					.body("success", equalTo(true));
	}

	@Test
	@Order(6)
	void whenGivenId_ShouldDeleteSuccessfully() throws Exception {
		RestAssured
				.given()
					.baseUri("http://localhost").port(port)
					.headers("Authorization", "Bearer " + testUserToken)
					.contentType(ContentType.JSON)
				.when()
					.delete("/user/activityNotifications/1")
				.then()
					.log()
					.all()
					.statusCode(HttpStatus.NO_CONTENT.value());
	}
}
