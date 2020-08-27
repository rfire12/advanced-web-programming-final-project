package com.pucmm.edu.usersmicroservice.Entities;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    private String username;
    private String name;
    private String password;
    private String email;
    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    List<String> roles;
    boolean isAdmin;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public User(String username, String name, String password, String email, List<String> roles, boolean isAdmin) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
        this.roles = roles;
        this.isAdmin = isAdmin;
    }

    public User() {
    }
}
