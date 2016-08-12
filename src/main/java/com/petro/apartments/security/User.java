package com.petro.apartments.security;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "username", unique = true,
            nullable = false, length = 45)
    private String username;

    @Column(name = "password",
            nullable = false, length = 60)
    private String password;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="user_userRole",
            joinColumns={@JoinColumn(name="username")},
            inverseJoinColumns={@JoinColumn(name="userRole_id")})
    private Set<UserRole> userRoles = new HashSet<UserRole>(0);

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, Set<UserRole> userRole) {
        this.username = username;
        this.password = password;
        this.userRoles = userRole;
    }


    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Set<UserRole> getUserRoles() {
        return this.userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }
}
