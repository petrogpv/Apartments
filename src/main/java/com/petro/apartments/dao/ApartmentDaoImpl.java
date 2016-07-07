package com.petro.apartments.dao;

import com.petro.apartments.common.Utility;
import com.petro.apartments.entity.Apartment;
import com.petro.apartments.entity.District;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ApartmentDaoImpl implements ApartmentDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(Apartment apartment) {
        entityManager.persist(apartment);
    }

    @Override
    public void delete(Apartment apartment) {
        entityManager.remove(apartment);
    }

    @Override
    public List<Apartment> list(District district) {
        Query query = entityManager.createQuery("SELECT a FROM Apartment a WHERE a.district = :district", Apartment.class);
        query.setParameter("district", district);
        return Utility.getListApartmentsWithActualPrices((List<Apartment>)query.getResultList());
    }

    @Override
    public List<Apartment> list() {
        Query query = entityManager.createQuery("SELECT a FROM Apartment a", Apartment.class);
        return Utility.getListApartmentsWithActualPrices((List<Apartment>)query.getResultList());
    }

    @Override
    public List<Apartment> getMany(long[] ids) {
        List<Apartment> list = new ArrayList<>();
        for (long id:ids) {
            list.add(getOne(id));
        }
        return list;
    }

    @Override
    public List<Apartment> findMany(long[] ids) {
        List<Apartment> list = new ArrayList<>();
        for (long id:ids) {
            list.add(findOne(id));
        }
        return null;
    }

    @Override
    public Apartment getOne(long id) {
//        Query query = entityManager.createQuery("SELECT a FROM Apartment a WHERE a.id = :id", Apartment.class);
//        query.setParameter("id", id);
//        (Apartment)query.getSingleResult();
        return entityManager.getReference(Apartment.class,id);
    }

    @Override
    public Apartment findOne(long id) {
        return entityManager.find(Apartment.class,id);
    }
}
