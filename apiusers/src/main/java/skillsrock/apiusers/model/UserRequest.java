package skillsrock.apiusers.model;

public class UserRequest {

    private String fullName;
    private String phoneNumber;
    private String avatarUrl;
    private Integer roleId;

    // Getters
    public String getFullName() { return fullName; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getAvatarUrl() { return avatarUrl; }
    public Integer getRoleId() { return roleId; }

    // Setters
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
    public void setRoleId(Integer roleId) { this.roleId = roleId; }
}