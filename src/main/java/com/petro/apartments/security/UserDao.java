package com.petro.apartments.security;

/**
 * Created by Администратор on 03.08.2016.
 */
public interface UserDao {
    User findByUserName(String username);
}
