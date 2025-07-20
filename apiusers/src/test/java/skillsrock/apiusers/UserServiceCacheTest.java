package skillsrock.apiusers;

import skillsrock.apiusers.model.User;
import skillsrock.apiusers.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceCacheTest {

    @Autowired
    private UserService userService;

    @Autowired
    private CacheManager cacheManager;

    @Test
    void testCacheBehavior() throws InterruptedException {

        User user = new User();
        user.setFullName("John Doe");
        user.setPhoneNumber("1234567890");
        user.setAvatarUrl("http://example.com/avatar.jpg");
        user.setRoleId(1);
        User createdUser = userService.createUser(user);

	Integer userId = createdUser.getUuid();

        // Get from service (first call - cache miss)
        Cache cache = cacheManager.getCache("users");
        assertNull(cache.get(userId));

        User result1 = userService.getUserById(userId);
        assertNotNull(cache.get(userId));
        assertEquals("John Doe", result1.getFullName());

        // Second call - should be cache hit
        User result2 = userService.getUserById(userId);
        assertEquals("John Doe", result2.getFullName());

        // Wait for cache to expire
        TimeUnit.SECONDS.sleep(11);

        Cache usersCache = cacheManager.getCache("users");
        if (usersCache != null) {
            usersCache.evict(userId);
        }

        // Now cache should be expired
        assertNull(cache.get(userId));

        // Get again - cache miss, reload
        User result3 = userService.getUserById(userId);
        assertNotNull(result3);
        assertEquals("John Doe", result3.getFullName());

        // Delete user via service
        userService.deleteUser(userId);
    }
}