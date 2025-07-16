package skillsrock.apiusers.controller;

import skillsrock.apiusers.model.UserRequest;
import skillsrock.apiusers.model.UserResponse;
import skillsrock.apiusers.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // POST /api/createNewUser
    @PostMapping("/createNewUser")
    public UserResponse createNewUser(@RequestBody UserRequest userRequest) {
        return userService.createNewUser(userRequest);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsers(@RequestParam(required = false) String userID) {
        if (userID == null || userID.isEmpty()) {
            return ResponseEntity.ok(userService.getAllUsers());
        } else {
            UserResponse user = userService.getUserById(userID);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("User not found with ID: " + userID);
            }
            return ResponseEntity.ok(user);
        }
    }

    // PUT /api/userDetailsUpdate
    @PutMapping("/userDetailsUpdate")
    public UserResponse updateUserDetails(@RequestBody UserRequest userRequest) {
        return userService.updateUserDetails(userRequest);
    }

    // DELETE /api/users?userID=anyUUID
    @DeleteMapping("/users")
    public void deleteUserById(@RequestParam String userID) {
        userService.deleteUserById(userID);
    }
}
