package skillsrock.apiusers.service;

import skillsrock.apiusers.exception.UserNotFoundException;
import skillsrock.apiusers.model.User;
import skillsrock.apiusers.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /*public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }*/
    
    @Cacheable("users")
    public User getUserById(Integer id) {
        System.out.println("Fetching user from DB for ID: " + id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
        return user;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    // Update user and update cache
    @CachePut("users")
    public User updateUser(Integer id, User userDetails) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
        user.setFullName(userDetails.getFullName());
        user.setPhoneNumber(userDetails.getPhoneNumber());
        user.setAvatarUrl(userDetails.getAvatarUrl());
        user.setRoleId(userDetails.getRoleId());
        return userRepository.save(user);
    }

    // Delete user and evict from cache
    @CacheEvict("users")
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}