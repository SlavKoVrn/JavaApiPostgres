package skillsrock.apiusers.controller;

import skillsrock.apiusers.model.User;
import skillsrock.apiusers.model.UserRequest;
import skillsrock.apiusers.model.UserResponse;
import skillsrock.apiusers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    // POST /api/createNewUser
    @PostMapping("/createNewUser")
    public UserResponse createUser(@RequestBody UserRequest request) {
        User user = new User();
        user.setFullName(request.getFullName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setAvatarUrl(request.getAvatarUrl());
        user.setRoleId(request.getRoleId());

        User createdUser = userService.createUser(user);

        return new UserResponse(
            createdUser.getUuid(),
            createdUser.getFullName(),
            createdUser.getPhoneNumber(),
            createdUser.getAvatarUrl(),
            createdUser.getRoleId()
        );
    }

    // GET /api/users?userID=anyUUID
    @GetMapping("/users")
    public ResponseEntity<?> getUsers(@RequestParam(required = false) Integer userID) {
        if (userID == null) {
            List<User> allUsers = userService.getAllUsers();
            return ResponseEntity.ok(allUsers);
        } else {
            Optional<User> userOptional = userService.getUserById(userID);
            if (userOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("User not found with ID: " + userID);
            }
            return ResponseEntity.ok(userOptional.get());
        }
    }

    // PUT /api/userDetailsUpdate?userID=anyUUID
    @PutMapping("/userDetailsUpdate")
    public UserResponse updateUser(@RequestParam Integer uuid, @RequestBody UserRequest request) {
        User user = userService.getUserById(uuid)
            .orElseThrow(() -> new RuntimeException("User not found"));

        user.setFullName(request.getFullName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setAvatarUrl(request.getAvatarUrl());
        user.setRoleId(request.getRoleId());

        User updatedUser = userService.updateUser(uuid, user);

        return new UserResponse(
            updatedUser.getUuid(),
            updatedUser.getFullName(),
            updatedUser.getPhoneNumber(),
            updatedUser.getAvatarUrl(),
            updatedUser.getRoleId()
        );
    }
    /*
    public ResponseEntity<?> updateUser(
            @RequestParam Integer userID,
            @RequestBody User userDetails) {
        User updatedUser = userService.updateUser(userID, userDetails);
        if (updatedUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found with ID: " + userID);
        }
        return ResponseEntity.ok(updatedUser);
    }
    */

    // DELETE /api/users?userID=anyUUID
    @DeleteMapping("/users")
    public ResponseEntity<String> deleteUser(@RequestParam Integer userID) {
        Optional<User> userOptional = userService.getUserById(userID);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found with ID: " + userID);
        }

        userService.deleteUser(userID); // assuming this method returns void
        return ResponseEntity.ok("User deleted successfully");
    }
}