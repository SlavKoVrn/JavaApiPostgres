package skillsrock.apiusers;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CascadeDeleteTest {

    @Autowired
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setup() throws SQLException {
        jdbcTemplate = new JdbcTemplate(dataSource);
        //clearData();
        insertTestData();
    }

    @AfterEach
    public void teardown() throws SQLException {
        //clearData();
    }

    private void clearData() {
        jdbcTemplate.update("DELETE FROM users");
        jdbcTemplate.update("DELETE FROM roles");
    }

    private void insertTestData() {
        // Insert role
        jdbcTemplate.update("INSERT INTO roles (uuid, rolename) VALUES (?, ?)", 1000, "TEST_ROLE");

        // Insert user
        jdbcTemplate.update("INSERT INTO users (uuid, fio, phonenumber, avatar, role) VALUES (?, ?, ?, ?, ?)",
                1000, "Test User", "+123456789", "http://example.com/avatar.png", 1000);
    }

    @Test
    public void testCascadeDelete() {
        // Delete the role
        jdbcTemplate.update("DELETE FROM roles WHERE uuid = ?", 1000);

        Integer userCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users WHERE uuid = ?", Integer.class, 1000);

        assertEquals(0, userCount, "User should have been deleted due to cascade.");
    }
}
