package skillsrock.apiusers.service;

import skillsrock.apiusers.model.UserRequest;
import skillsrock.apiusers.model.UserResponse;
import skillsrock.apiusers.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserService {

    private final Map<String, UserResponse> userStore = new HashMap<>();

    public UserResponse createNewUser(UserRequest request) {
        String userID = UUID.randomUUID().toString();
        UserResponse user = new UserResponse(userID, request.getName(), request.getEmail());
        userStore.put(userID, user);
        return user;
    }

    // Новый метод: получить всех пользователей
    public Collection<UserResponse> getAllUsers() {
        return userStore.values();
    }

    public UserResponse getUserById(String userID) {
        UserResponse user = userStore.get(userID);
        if (user == null) {
            throw new UserNotFoundException("User not found with ID: " + userID);
        }
        return user;
    }

    public UserResponse updateUserDetails(UserRequest request) {
        String userID = request.getUserID();
        UserResponse existing = getUserById(userID);
        UserResponse updated = new UserResponse(
                userID,
                request.getName(),
                request.getEmail()
        );
        userStore.put(userID, updated);
        return updated;
    }

    public void deleteUserById(String userID) {
        if (!userStore.containsKey(userID)) {
            throw new UserNotFoundException("User not found with ID: " + userID);
        }
        userStore.remove(userID);
    }
}