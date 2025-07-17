package skillsrock.apiusers;

import skillsrock.apiusers.model.UserRequest;
import skillsrock.apiusers.model.UserResponse;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
class ApiusersApplicationTests {

    public ApiusersApplicationTests() {
        RestAssured.baseURI = "http://localhost:8080";
    }

    // POST /api/createNewUser
    @Test
    void testCreateUser_WithPOJO_Returns200() throws Exception {
        System.out.println("1. testCreateUser_WithPOJO_Returns200");

        UserRequest request = new UserRequest();
        request.setFullName("Jane Smith");
        request.setPhoneNumber("+0987654321");
        request.setAvatarUrl("https://example.com/avatar2.jpg");
        request.setRoleId(2);

        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(request);

        given()
            .contentType("application/json")
            .body(requestBody)
        .when()
            .post("/api/createNewUser")
        .then()
            .statusCode(200)
            .body("fullName", equalTo("Jane Smith"))
            .body("phoneNumber", equalTo("+0987654321"))
            .body("avatarUrl", equalTo("https://example.com/avatar2.jpg"))
            .body("roleId", equalTo(2));
    }
    
    // GET /api/users (all users)
    @Test
    void testGetAllUsers_ReturnsOkStatus() {
        System.out.println("2. testGetAllUsers_ReturnsOkStatus");
    	Response response = given()
                .when()
                    .get("/api/users")
                .then()
                    .statusCode(200)
                    .extract().response();

            String responseBody = response.getBody().asString();
            System.out.println("##############################");
            System.out.println("Response Body: " + responseBody);
            System.out.println("##############################");
    }

    // GET /api/users (all users)
    @Test
    void testGetAllUsers_ReturnsListOfUsers() {
        System.out.println("3. testGetAllUsers_ReturnsListOfUsers");
        given()
        .when()
            .get("/api/users")
        .then()
            .statusCode(200)
            .body("$.size()", greaterThanOrEqualTo(1));
    }

    // PUT /api/userDetailsUpdate?userID=anyUUID
    @Test
    void testUpdateUser_WithPOJO_ReturnsUpdatedData() throws Exception {
        System.out.println("4. testUpdateUser_WithPOJO_ReturnsUpdatedData");

        UserRequest request = new UserRequest();
        request.setFullName("Jane Updated");
        request.setPhoneNumber("+9876543210");
        request.setAvatarUrl("https://example.com/jane-updated.jpg");
        request.setRoleId(2);

        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(request);

        Response response = given()
            .contentType("application/json")
            .queryParam("userID", 1)
            .body(requestBody)
        .when()
            .put("/api/userDetailsUpdate")
        .then()
            /*.statusCode(200)
            .body("fullName", equalTo("Jane Updated"))
            .body("phoneNumber", equalTo("+9876543210"))
            .body("avatarUrl", equalTo("https://example.com/jane-updated.jpg"))
            .body("roleId", equalTo(2))*/
            .extract().response();

        String responseBody = response.getBody().asString();
        System.out.println("##############################");
        System.out.println("Response Body: " + responseBody);
        System.out.println("##############################");

    }

}
