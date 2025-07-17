package skillsrock.apiusers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
class ApiusersApplicationTests {

    public ApiusersApplicationTests() {
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Test
    void testGetAllUsers_ReturnsOkStatus() {
    	Response response = given()
                .when()
                    .get("/api/users")
                .then()
                    .statusCode(200)
                    .extract().response();

            // Get response body as String
            String responseBody = response.getBody().asString();

            // Print response body to console
            System.out.println("##############################");
            System.out.println("Response Body: " + responseBody);
            System.out.println("##############################");
    }

    // GET /api/users (all users)
    @Test
    void testGetAllUsers_ReturnsListOfUsers() {
        given()
        .when()
            .get("/api/users")
        .then()
            .statusCode(200)
            .body("$.size()", greaterThanOrEqualTo(1));
    }


}
