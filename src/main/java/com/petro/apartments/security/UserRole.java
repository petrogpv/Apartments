package com.petro.apartments.security;

import javax.persistence.*;

import java.util.Set;

import static java.awt.font.TransformAttribute.IDENTITY;


@Entity
@Table(name = "user_roles")
public class UserRole{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userRole_id",
            unique = true, nullable = false)
    private Integer userRoleId;

    @ManyToMany(mappedBy="userRoles")
    private Set<User> users;

    @Column(name = "role", nullable = false, length = 45)
    private String role;

    public UserRole() {
    }

    public UserRole(Set<User> users, String role) {
        this.users = users;
        this.role = role;
    }


    public Integer getUserRoleId() {
        return this.userRoleId;
    }

    public void setUserRoleId(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }


    public Set<User> getUsers() {
        return this.users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }


    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
