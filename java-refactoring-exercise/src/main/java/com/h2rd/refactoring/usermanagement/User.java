package com.h2rd.refactoring.usermanagement;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * The User class maintains information for individual users who have been registered.
 */
@XmlRootElement
public class User {
    private String email;       // Unique identifier for user.
    private String name;        // User's name.
    private List<String> roles; // User roles, eg admin, operations

    /**
     * Create a new User.  If arguments are illegal, return null  Otherwise,
     * return user object.
     */
    public User(String name, String email, List<String> roles) {
        this.name = name;
        this.email = email;
        this.roles = roles;
    }

    /**
     * Update user name and roles.  If arguments are illegal, return false.
     * Otherwise, return true.
     */
    public void update(String name, List<String> roles) {
        this.name = name;
        this.roles = roles;
    }

    /**
     * Get and Set Methods.
     */
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
    public List<String> getRoles() {
        return roles;
    }
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
