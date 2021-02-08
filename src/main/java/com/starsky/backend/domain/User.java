package com.starsky.backend.domain;

import com.starsky.backend.api.user.UserResponse;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class User extends BaseEntity {

    public User() {
    }


    /** Register a new manager type user.
     * @param password bcrypt hashed password, do not pass raw version
     */
    public User(@NotNull String name, @NotNull String email, @NotNull String password, @NotNull String jobTitle) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.jobTitle = jobTitle;
        this.enabled = true;
        this.notificationType = NotificationType.EMAIL;
        this.role = Role.MANAGER;
        this.parentUser = null; //managers dont have parent users
        this.phoneNumber = null; //we dont use phone notifications atm, TODO fix this if we ever use them
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user-id-generator")
    @SequenceGenerator(name = "user-id-generator", sequenceName = "user_sequence", allocationSize = 1)
    private long id;

    @NotNull
    private String name;

    @NotNull
    @Column(unique = true)
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String jobTitle;

    private String phoneNumber;

    @NotNull
    private boolean enabled;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private NotificationType notificationType;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private Role role;

    @OneToOne
    private User parentUser;

    public UserResponse toResponse(){
        return new UserResponse(getId(), getName(), getEmail(), getJobTitle(), getPhoneNumber(), getNotificationType().name(), getRole().name());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public User getParentUser() {
        return parentUser;
    }

    public void setParentUser(User parentUser) {
        this.parentUser = parentUser;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
