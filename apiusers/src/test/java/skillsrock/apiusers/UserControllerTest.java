package skillsrock.apiusers;

import skillsrock.apiusers.model.UserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateUserWithValidFullNameLength() throws Exception {
        // 255 characters is allowed
        String validName = "A".repeat(255);

        UserRequest request = new UserRequest();
        request.setFullName(validName);
        request.setPhoneNumber("1234567890");
        request.setAvatarUrl("http://example.com/avatar.jpg");
        request.setRoleId(1);

        mockMvc.perform(post("/api/createNewUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value(validName));
    }

    @Test
    public void testCreateUserWithInvalidFullNameLength() throws Exception {
        // 256 characters is NOT allowed
        String invalidName = "A".repeat(256);

        UserRequest request = new UserRequest();
        request.setFullName(invalidName);
        request.setPhoneNumber("1234567890");
        request.setAvatarUrl("http://example.com/avatar.jpg");
        request.setRoleId(1);

        ResultActions result = mockMvc.perform(post("/api/createNewUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        result.andDo(print());
    }
}