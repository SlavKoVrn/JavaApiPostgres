package skillsrock.apiusers.model;

public class UserResponse {
    private String userID;
    private String name;
    private String email;

    public UserResponse(String userID, String name, String email) {
        this.userID = userID;
        this.name = name;
        this.email = email;
    }

    // Getters
    public String getUserID() { return userID; }
    public String getName() { return name; }
    public String getEmail() { return email; }
}