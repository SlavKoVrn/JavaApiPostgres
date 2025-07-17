package skillsrock.apiusers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

@SpringBootTest
class ApiusersApplicationTests {

    public ApiusersApplicationTests() {
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Test
    void testGetAllUsers_ReturnsOkStatus() {
        System.out.println("test begins");
        given()
            .when()
                .get("/api/users")
            .then()
                .statusCode(200);
    }

	@Test
	void contextLoads() {
	}

}
