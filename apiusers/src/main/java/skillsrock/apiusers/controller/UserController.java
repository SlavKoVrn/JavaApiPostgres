package skillsrock.apiusers.controller;

import skillsrock.apiusers.model.User;
import skillsrock.apiusers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    // POST /api/createNewUser
    @PostMapping("/createNewUser")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    // GET /api/users?userID=anyUUID
    @GetMapping("/users")
    public User getUserById(@RequestParam Integer userID) {
        return userService.getUserById(userID)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // PUT /api/userDetailsUpdate
    @PutMapping("/userDetailsUpdate")
    public User updateUser(@RequestParam Integer userID, @RequestBody User userDetails) {
        return userService.updateUser(userID, userDetails);
    }

    // DELETE /api/users?userID=anyUUID
    @DeleteMapping("/users")
    public void deleteUser(@RequestParam Integer userID) {
        userService.deleteUser(userID);
    }
}