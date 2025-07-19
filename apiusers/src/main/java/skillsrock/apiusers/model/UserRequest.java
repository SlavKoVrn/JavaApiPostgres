package skillsrock.apiusers.model;

import lombok.Data;
import jakarta.validation.constraints.Size;

@Data
public class UserRequest {

    @Size(max = 255, message = "Full name cannot exceed 255 characters")
    private String fullName;

    @Size(max = 255, message = "Phone number cannot exceed 255 characters")
    private String phoneNumber;

    @Size(max = 255, message = "Avatar url cannot exceed 255 characters")
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