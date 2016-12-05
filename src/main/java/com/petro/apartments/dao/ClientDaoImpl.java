package com.petro.apartments.dao;


import com.petro.apartments.entity.Client;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ClientDaoImpl implements ClientDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void add(Client client) {
        entityManager.merge(client);
    }

    @Override
    public void delete(Client client) {
        entityManager.remove(client);
    }

    @Override
    public List<Client> list() {
        Query query = entityManager.createQuery("SELECT c FROM Client c ", Client.class);
        return (List<Client>)query.getResultList();
    }

    @Override
    public List<Client> list(String pattern) {
        Query query = entityManager.createQuery("SELECT c FROM Client c WHERE c.firstName   LIKE :pattern OR c.lastName LIKE :pattern", Client.class);
        query.setParameter("pattern", "%" + pattern + "%");
        return (List<Client>) query.getResultList();
    }

    @Override
    public Client findOne(long id) {
        return entityManager.find(Client.class,id);
    }
    public Client getOne(long id) {
        return entityManager.getReference(Client.class,id);
    }
}
