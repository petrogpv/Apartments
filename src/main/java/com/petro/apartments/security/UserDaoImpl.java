package com.petro.apartments.security;

import com.petro.apartments.entity.Client;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    EntityManager entityManager;

    //    @SuppressWarnings("unchecked")
    @Override
    public User findByUserName(String username) {
        Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :pattern", User.class);
        query.setParameter("pattern", username);
        User user = (User)query.getSingleResult();
        return user;
    }

}
