package skillsrock.apiusers.model;

public class UserResponse {

    private Integer uuid;
    private String fullName;
    private String phoneNumber;
    private String avatarUrl;
    private Integer roleId;

    // Constructor from User entity
    public UserResponse(Integer uuid, String fullName, String phoneNumber, String avatarUrl, Integer roleId) {
        this.uuid = uuid;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.avatarUrl = avatarUrl;
        this.roleId = roleId;
    }

    // Getters
    public Integer getUuid() { return uuid; }
    public String getFullName() { return fullName; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getAvatarUrl() { return avatarUrl; }
    public Integer getRoleId() { return roleId; }

    // Optional: Add setters if needed
}