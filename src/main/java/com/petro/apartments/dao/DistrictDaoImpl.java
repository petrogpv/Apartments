package com.petro.apartments.dao;

import com.petro.apartments.entity.Apartment;
import com.petro.apartments.entity.District;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class DistrictDaoImpl implements DistrictDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void add(District district) {
        entityManager.merge(district);
    }

    @Override
    public void delete(District district) {
        entityManager.remove(district);
    }

    @Override
    public District findOne(long id) {
        return entityManager.getReference(District.class,id);
    }

    @Override
    public List<District> list() {
        Query query = entityManager.createQuery("SELECT d FROM District d", District.class);
        return (List<District>)query.getResultList();
    }
}
