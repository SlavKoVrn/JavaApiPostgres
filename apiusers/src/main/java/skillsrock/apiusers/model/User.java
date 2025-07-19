package skillsrock.apiusers.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uuid;

    @Size(max = 255, message = "Full name cannot exceed 255 characters")
    @Column(name = "fio", nullable = false)
    private String fullName;

    @Size(max = 255, message = "Phone number cannot exceed 255 characters")
    @Column(name = "phonenumber")
    private String phoneNumber;

    @Size(max = 255, message = "Avatar url cannot exceed 255 characters")
    @Column(name = "avatar")
    private String avatarUrl;

    @Column(name = "role")
    private Integer roleId;

    // Getters
    public Integer getUuid() { return uuid; }
    public String getFullName() { return fullName; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getAvatarUrl() { return avatarUrl; }
    public Integer getRoleId() { return roleId; }

    // Setters
    public void setUuid(Integer uuid) { this.uuid = uuid; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
    public void setRoleId(Integer roleId) { this.roleId = roleId; }
}