package skillsrock.apiusers.controller;

import skillsrock.apiusers.model.User;
import skillsrock.apiusers.model.UserRequest;
import skillsrock.apiusers.model.UserResponse;
import skillsrock.apiusers.service.UserService;
import skillsrock.apiusers.exception.UserNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.validation.ConstraintViolation;
import org.springframework.validation.annotation.Validated;

@RestController
@RequestMapping("/api")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    private final Validator validator;

    public UserController(Validator validator) {
        this.validator = validator;
    }

    // POST /api/createNewUser
    @PostMapping("/createNewUser")
    public UserResponse createUser(@Valid @RequestBody UserRequest request) {
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
            User user = userService.getUserById(userID);
            if (user == null) {
            	throw new UserNotFoundException("User not found with ID: " + userID);
            }
            return ResponseEntity.ok(user);
        }
    }

    // PUT /api/userDetailsUpdate?userID=anyUUID
    // OR
    // PUT /api/userDetailsUpdate?userID=anyUUID&fullName=John&phoneNumber=123
    @PutMapping("/userDetailsUpdate")
    public UserResponse updateUser(
        @RequestParam Integer userID,
        @Valid @RequestBody(required = false) UserRequest bodyRequest,
        @RequestParam(required = false) String fullName,
        @RequestParam(required = false) String phoneNumber,
        @RequestParam(required = false) String avatarUrl,
        @RequestParam(required = false) Integer roleId
    ) {
        User user = userService.getUserById(userID);
        if (user == null) {
            throw new UserNotFoundException("User not found with ID: " + userID);
        }

        UserRequest request = new UserRequest();
        request.setFullName(fullName);
        request.setPhoneNumber(phoneNumber);
        request.setAvatarUrl(avatarUrl);
        request.setRoleId(roleId);

        // Manually validate using Jakarta Bean Validation
        Set<ConstraintViolation<UserRequest>> violations = validator.validate(request);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<UserRequest> violation : violations) {
                sb.append(violation.getMessage()).append("; ");
            }
            throw new IllegalArgumentException(sb.toString().trim());
        }
    
        // Use query params if provided, otherwise use bodyRequest
        user.setFullName((fullName != null) ? fullName : (bodyRequest != null ? bodyRequest.getFullName() : user.getFullName()));
        user.setPhoneNumber((phoneNumber != null) ? phoneNumber : (bodyRequest != null ? bodyRequest.getPhoneNumber() : user.getPhoneNumber()));
        user.setAvatarUrl((avatarUrl != null) ? avatarUrl : (bodyRequest != null ? bodyRequest.getAvatarUrl() : user.getAvatarUrl()));
        user.setRoleId((roleId != null) ? roleId : (bodyRequest != null ? bodyRequest.getRoleId() : user.getRoleId()));
    
        User updatedUser = userService.updateUser(userID, user);
    
        return new UserResponse(
            updatedUser.getUuid(),
            updatedUser.getFullName(),
            updatedUser.getPhoneNumber(),
            updatedUser.getAvatarUrl(),
            updatedUser.getRoleId()
        );
    }

    // DELETE /api/users?userID=anyUUID
    @DeleteMapping("/users")
    public ResponseEntity<String> deleteUser(@RequestParam Integer userID) {
        User user = userService.getUserById(userID);
        if (user == null) {
        	throw new UserNotFoundException("User not found with ID: " + userID);
        }

        userService.deleteUser(userID);
        return ResponseEntity.ok("User deleted successfully");
    }
}