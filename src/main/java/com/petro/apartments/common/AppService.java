package com.petro.apartments.common;


import com.petro.apartments.dao.*;
import com.petro.apartments.entity.*;
import com.petro.apartments.security.User;
import com.petro.apartments.security.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.*;

@Service
public class AppService {

    @Autowired
    private ApartmentDao apartmentDao;
    @Autowired
    private BookingDao bookingDao;
    @Autowired
    private ClientDao clientDao;
    @Autowired
    private DayDao dayDao;
    @Autowired
    private DistrictDao districtDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private PriceDao priceDao;
    @Autowired
    private ImageDao imageDao;
    @Autowired
    private UserDao userDao;


    @Transactional
    public void addApartment (Apartment apartment){
        apartmentDao.add(apartment);
    }
    @Transactional
    public void editApartment(Apartment apartment) {
        apartmentDao.edit(apartment);
    }
    @Transactional
    public void deleteApartment (Apartment apartment){
        apartmentDao.delete(apartment);
    }
    @Transactional
    public List<Apartment> listApartments (District district){
        return apartmentDao.list(district);
    }
    @Transactional
    public List<Apartment> listApartments (){
        return apartmentDao.list();
    }
    @Transactional
    public List<Apartment> findManyApartments(long [] ids){
        return apartmentDao.findMany(ids);
    }
    @Transactional
    public List<Apartment> getManyApartments(long [] ids){
        return apartmentDao.getMany(ids);
    }
    @Transactional
    public Apartment findOneApartment (long id){
        return apartmentDao.findOne(id) ;
    }
    @Transactional
    public Apartment getOneApartment (long id){
        return apartmentDao.getOne(id) ;
    }

    @Transactional
    public void addDistrict (District district){
        districtDao.add(district);
    }
    @Transactional
    public void editDistrict (District district) {
        districtDao.edit(district);
    }
    @Transactional
    public void deleteDistrict (District district){
        districtDao.delete(district);
    }
    @Transactional
    public District findDistrict (long id){
        return districtDao.findOne(id);
    }
    @Transactional
    public District getDistrict (long id){
        return districtDao.getOne(id);
    }
    @Transactional
    public List<District> listDistricts (){
        List<District> districts = districtDao.list();
        districts.sort(Comparator.comparing(District::getName));
        return districts;
    }


    @Transactional
    public void addDays (List<Day> days){
        dayDao.add(days);
    }
    @Transactional
    public void addDay (Day day){
        dayDao.add(day);
    }
    @Transactional
    public List<Day> listMonthDays(Date monthDate){
        return dayDao.listMonth(monthDate);
    }
    @Transactional
    public List<Day> listDays (Date dateFrom, Date dateTo){
        return dayDao.list(dateFrom,dateTo);
    }
    @Transactional
    public Day findOneDay (String id){
        return  dayDao.findOne(id);
    }

    @Transactional
    public void addPrice (Price price) {
        priceDao.add(price);
    }

    @Transactional
    public void editPrice (Price price) {
        priceDao.edit(price);
    }

    @Transactional
    public List<Price> listPrices(Apartment apartment) {
        return priceDao.list(apartment);
    }

    @Transactional
    public List<Price> listPrices() {
        return priceDao.list();
    }

    @Transactional
    public Price findOnePrice (long id) {
        return priceDao.findOne(id);
    }

    @Transactional
    public void delete(Price price) {
        priceDao.delete(price);
    }

    @Transactional
    public void addImage(Image image){
        imageDao.add(image);
    }

    @Transactional
    public void deleteImage(Image image) {
        imageDao.delete(image);
    }

    @Transactional
    public List<Image> listImages(Apartment apartment) {
        return imageDao.list(apartment);
    }

    @Transactional
    public Image findOneImage(long id) {
        return imageDao.findOne(id);
    }
    @Transactional
    public Image getOneImage(long id) {
        return imageDao.getOne(id);
    }

    @Transactional
    public List<Booking> listBookings(Order order) {
        return bookingDao.list(order);
    }

    @Transactional
    public List<Booking> listBookings(Apartment apartment) {
        return bookingDao.list(apartment);
    }

    @Transactional
    public List<Booking> listBookings(Day day) {
        return bookingDao.list(day);
    }

    @Transactional
    public List<Booking> listBookings(Apartment apartment, Date dateFrom, Date dateTo) {

        return bookingDao.list(apartment,dateFrom,dateTo);
    }

    @Transactional
    public List<Booking> listMonthBookings(Apartment apartment, Date monthDate) {
        return bookingDao.listMonth(apartment,monthDate);
    }

    @Transactional
    public List<Booking> findManyBookings(long[] ids) {
        return bookingDao.findMany(ids);
    }

    @Transactional
    public Booking findOneBooking(long id) {
        return bookingDao.findOne(id);
    }

    @Transactional
    public User findUserByUserName(String username) {
        return userDao.findByUserName(username);
    }


//    @Transactional
//    public List<Price> listActualPrices (Apartment apartment) {
//        return priceDao.listActual(apartment);
//    }
}
