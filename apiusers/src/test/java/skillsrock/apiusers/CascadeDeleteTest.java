package skillsrock.apiusers;

import org.junit.jupiter.api.*;
import java.sql.*;
import static org.junit.jupiter.api.Assertions.*;

public class CascadeDeleteTest {

    private static Connection connection;

    @BeforeAll
    public static void setup() throws Exception {
        String url = "jdbc:postgresql://localhost:5432/users";
        String user = "postgres";
        String password = "password";
        connection = DriverManager.getConnection(url, user, password);
        clearData();
        insertTestData();
    }

    @AfterAll
    public static void teardown() throws Exception {
        clearData();
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    private static void clearData() throws Exception {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DELETE FROM users");
            stmt.execute("DELETE FROM roles");
        }
    }

    private static void insertTestData() throws Exception {
        try (PreparedStatement psRole = connection.prepareStatement("INSERT INTO roles (uuid, rolename) VALUES (?, ?)");
             PreparedStatement psUser = connection.prepareStatement("INSERT INTO users (uuid, fio, phonenumber, avatar, role) VALUES (?, ?, ?, ?, ?)")) {

            // Insert role
            psRole.setInt(1, 1);
            psRole.setString(2, "TEST_ROLE");
            psRole.executeUpdate();

            // Insert user
            psUser.setInt(1, 1);
            psUser.setString(2, "Test User");
            psUser.setString(3, "+123456789");
            psUser.setString(4, "http://example.com/avatar.png");
            psUser.setInt(5, 1);
            psUser.executeUpdate();
        }
    }

    @Test
    public void testCascadeDelete() throws Exception {
        // Delete the role
        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM roles WHERE uuid = ?")) {
            ps.setInt(1, 1);
            ps.executeUpdate();
        }

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE uuid = 1")) {
            assertFalse(rs.next(), "User should have been deleted due to cascade.");
        }
    }
}
