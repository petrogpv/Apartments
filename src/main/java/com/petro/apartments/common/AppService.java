package com.petro.apartments.common;


import com.petro.apartments.dao.*;
import com.petro.apartments.entity.*;
import com.petro.apartments.security.User;
import com.petro.apartments.security.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class AppService {

    @Autowired
    private ApartmentDao apartmentDao;
    @Autowired
    private BookingDao bookingDao;
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
    @Autowired
    private ClientDao clientDao;


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
//        districts.sort(Comparator.comparing(District::getName));
//        districts.sort(new Comparator<District>() {
//            @Override
//            public int compare(District o1, District o2) {
//                return o1.getName().compareTo(o2.getName());
//            }
//        });
        districts.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
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
    public Day findOneDay (Date id){
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
    public void deletePrice(Price price) {
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

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteBooking(Booking booking) {
        bookingDao.delete(booking);
    }

    @Transactional
    public void addBooking(Booking booking) {
        bookingDao.add(booking);
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

    @Transactional
    public void addClient(Client client) {
        clientDao.add(client);
    }

    @Transactional
    public void deleteClient(Client client) {
        clientDao.delete(client);
    }

    @Transactional
    public List<Client> listClients() {
        return clientDao.list();
    }

    @Transactional
    public List<Client> listClients(String pattern) {
        return clientDao.list(pattern);
    }

    @Transactional
    public Client findOneClient(long id) {
        return clientDao.findOne(id);
    }
    @Transactional
    public Client getOneClient(long id) {
        return clientDao.getOne(id);
    }

    @Transactional
    public void addOrder(Order order) {
        orderDao.add(order);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteOrder(Order order) {
        orderDao.delete(order);

    }
    @Transactional
    public List<Order> listOrders() {
        return orderDao.list();
    }
    @Transactional
    public List<Order> listOrders(Client client) {
        return orderDao.list(client);
    }

    @Transactional
    public List<Order> listOrders(Date monthDate) {
        return orderDao.list(monthDate);
    }

    @Transactional
    public List<Order> listOrders(Date dateFrom, Date dateTo) {
        return orderDao.list(dateFrom, dateTo);
    }

    @Transactional
    public Order findOneOrder (Long id) {
        return orderDao.findOne(id);
    }
    @Transactional
    public Order getOneOrder (Long id) {
        return orderDao.getOne(id);
    }

//    @Transactional
//    public List<Price> listActualPrices (Apartment apartment) {
//        return priceDao.listActual(apartment);
//    }
}
