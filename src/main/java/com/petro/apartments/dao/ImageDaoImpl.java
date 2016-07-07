package com.petro.apartments.dao;

import com.petro.apartments.entity.Apartment;
import com.petro.apartments.entity.Image;
import com.petro.apartments.entity.Price;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ImageDaoImpl implements ImageDao{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void add(Image image) {
        entityManager.persist(image);
    }

    @Override
    public void delete(Image image) {
        entityManager.remove(image);
    }

    @Override
    public List<Image> list(Apartment apartment) {
        Query query = entityManager.createQuery("SELECT i FROM Image i WHERE i.apartment = :apartment", Image.class);
        query.setParameter("apartment", apartment);
        return (List<Image>)query.getResultList();
    }

    @Override
    public Image findOne(long id) {
        return entityManager.find(Image.class,id);
    }
}
