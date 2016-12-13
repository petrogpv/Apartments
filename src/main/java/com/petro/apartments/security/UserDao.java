package com.petro.apartments.security;

public interface UserDao {
    User findByUserName(String username);
}
