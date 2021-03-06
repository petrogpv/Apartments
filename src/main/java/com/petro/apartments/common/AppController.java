package com.petro.apartments.common;

import com.google.gson.Gson;
import com.petro.apartments.entity.*;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/")
@Scope("session")
@SessionAttributes({"cartApartmentsSet"})
public class AppController {

    private static Map<Integer, String> months = new HashMap<Integer, String>() {{
        put(1, "January ");
        put(2, "February");
        put(3, "March");
        put(4, "April");
        put(5, "May");
        put(6, "June");
        put(7, "July");
        put(8, "August");
        put(9, "September");
        put(10, "October");
        put(11, "November");
        put(12, "December");
    }};
    private static String photosRootDirectory = "apartments";

    @Autowired
    AppService appService;
    @Autowired
    Utility utility;

    @RequestMapping("/")
    public String index(HttpSession session,

                        Model model) throws ParseException {
        model.addAttribute("apartments", appService.listApartments());

        if(session.getAttribute("cartQuantity")==null)
            session.setAttribute("cartQuantity",0);

        model.addAttribute("districts", appService.listDistricts());
        model.addAttribute("apartments", appService.listApartments());

//        SimpleDateFormat sdf = new SimpleDateFormat("MM-yyyy");
//        Date date = sdf.parse("3-2016");
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(date);
//        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
//        model.addAttribute("date", cal.getTime().getTime());
        return "index";
    }
    @RequestMapping(value = "/login" , method = RequestMethod.GET)
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            HttpServletRequest request,
                            Model model){

        if (error != null) {
            model.addAttribute("error", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
        }

        if (logout != null) {
            model.addAttribute("message", "You've been logged out successfully.");
        }

        return "login";
    }
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accesssDenied(Principal  principal,
                                Model model) {
        model.addAttribute("username", principal.getName());
        return "403";

    }
    @RequestMapping("/{id}")
    public String indexDistrict (@PathVariable(value = "id") long districtId
            , Model model) {
        District district = appService.findDistrict(districtId);
        model.addAttribute("districts", appService.listDistricts());
        model.addAttribute("apartments", appService.listApartments(district));
        return "index";
    }

    @RequestMapping(value = "/apartment", method = RequestMethod.POST)
    public String apartment(@RequestParam(value = "apartmentId") long apartmentId,
                            @RequestParam(required = false, value = "date") Long dateMillis,
                            @RequestParam(required = false, value = "sub") String submit,
                            @RequestParam(required = false, value = "bookings") List <Integer> bookings,
                            HttpServletRequest request,
                            HttpSession session,
                            Model model) {

        if(submit !=null && submit.equals("Discard changes")){
            request.setAttribute("forwarded",true);
            return "forward:/cart_delete";
        }

        Apartment apartment = appService.findOneApartment(apartmentId);

        if(submit == null){
            Calendar calTo = Calendar.getInstance();
            if(dateMillis != null){
                calTo.setTime(new Date(dateMillis));
            }
            else {
                calTo.set(Calendar.DAY_OF_MONTH, calTo.getActualMaximum(Calendar.DAY_OF_MONTH));
            }
            Calendar calFrom = (Calendar)calTo.clone();
            calFrom.set(Calendar.DAY_OF_MONTH,1);
            List <Booking> bookingList =  appService.listBookings(apartment, calFrom.getTime(),calTo.getTime());
            if(bookings==null)
                bookings = new ArrayList<>();
            for (Booking b: bookingList) {
                Day day = b.getDay();
                calTo.setTime(day.getId());
                bookings.add(calTo.get(Calendar.DAY_OF_MONTH));
            }

        }

        Set<CartApartment> cartApartmentsSet = (TreeSet<CartApartment>)session.getAttribute("cartApartmentsSet");

        CartApartment cartApartment = null;
        if(cartApartmentsSet==null) {
            cartApartmentsSet = new TreeSet<>();
            cartApartment = new CartApartment(apartment);
        }
        else{
            for (CartApartment cha: cartApartmentsSet) {
                if(cha.getId()==apartmentId){
                    cartApartment = cha;
                    break;
                }
            }

            if(cartApartment==null)
                cartApartment = new CartApartment(apartment);
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(utility.getStartOfDay(cal.getTime()));

        Calendar calToday = (Calendar) cal.clone();

        int today = 0;
        int backward = 1;
        int forward = 1;
        int month;

        if(dateMillis==null){
            month = calToday.get(Calendar.MONTH);
            today = calToday.get(Calendar.DAY_OF_MONTH);

            backward = -1;
            cal.set(Calendar.DAY_OF_MONTH,1);
        }
        else{
            cal.setTimeInMillis(dateMillis);
            cal.set(Calendar.DAY_OF_MONTH,1);


            Calendar calToBook  = (Calendar)cal.clone();
            Calendar calMonthDays = (Calendar)cal.clone();

            Map<Date, Set<DayPrice>> monthDaysMap = cartApartment.getMonthDaysMap();

            if(bookings!=null) {

                Price [] daysPrices = utility.daysWhithPrices(apartment,cal.getTime());

                if(monthDaysMap==null)
                    monthDaysMap = new TreeMap<>();

                Set<DayPrice> daysToBookMap = new TreeSet<>();
                for (int i = 0; i < bookings.size(); i++) {
                    calToBook.set(Calendar.DAY_OF_MONTH, bookings.get(i));
                    daysToBookMap.add(new DayPrice(appService.findOneDay(calToBook.getTime()),daysPrices[bookings.get(i)]));
                }
                monthDaysMap.put(calMonthDays.getTime(),daysToBookMap);
                cartApartment.setMonthDaysMap(monthDaysMap);
                cartApartmentsSet.add(cartApartment);
            }
            else{
                if(monthDaysMap!=null) {
                    if (monthDaysMap.get(calMonthDays.getTime()) != null) {
                        monthDaysMap.remove(calMonthDays.getTime());
                        if (monthDaysMap.size() == 0) {
                            cartApartmentsSet.remove(cartApartment);

                        }
                    }
                }
            }

            session.setAttribute("cartApartmentsSet",cartApartmentsSet);

            month = cal.get(Calendar.MONTH);

            if(submit!=null && submit.equals("Save and go to other apartment")){
                return "redirect:/";
            }
            if(submit!=null && submit.equals("Save and go to cart")){
                return "redirect:/cart";
            }

            if(submit!=null && submit.equals(">"))
                month++;
            if(submit!=null && submit.equals("<"))
                month--;

            if(month==calToday.get(Calendar.MONTH)){
                today = calToday.get(Calendar.DAY_OF_MONTH);
                backward = -1;
            }

            cal.set(Calendar.MONTH,month);
        }

        Calendar calNext = Calendar.getInstance();
        calNext.set(Calendar.MONTH,month+1);
        if(appService.listMonthDays(calNext.getTime()).size()==0)
            forward = -1;

        if(appService.listMonthDays(cal.getTime()).size()!=0){
            Price [] daysPrices = utility.daysWhithPrices(apartment,cal.getTime());

            int [] book = new int[daysPrices.length];
            Calendar calBooks = (Calendar)cal.clone();

//            if(cartApartmentsSet.size()!=0){
                Map<Date, Set<DayPrice>> monthDaysMap = cartApartment.getMonthDaysMap();
                if(monthDaysMap != null) {
                    Set<DayPrice> daysToBook = monthDaysMap.get(cal.getTime());
                    if (daysToBook != null) {
                        for (DayPrice dayPrice: daysToBook) {
                            calBooks.setTime(dayPrice.getDay().getId());
                            book[calBooks.get(Calendar.DAY_OF_MONTH)] = -1;
                        }
                    }
                }
//            }

            Gson gson = new Gson();

            double [] daysPricesArray = new double[daysPrices.length];
            for (int i = 1; i<daysPricesArray.length; i++) {
                if(daysPrices[i] == null)
                    daysPricesArray[i]= -1;
                else
                daysPricesArray[i] = daysPrices[i].getPrice();
            }

            String prices = gson.toJson(daysPricesArray);
            String books = gson.toJson(book);

            int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            cal.set(Calendar.DAY_OF_MONTH,maxDay);

            model.addAttribute("prices", prices);
            model.addAttribute("bookings", books);
            model.addAttribute("availableMonth",1);
        }
        else{
            model.addAttribute("message", "There is no calendar uploaded for current month");
            model.addAttribute("availableMonth",-1);
        }

        model.addAttribute("date",cal.getTime().getTime());
        model.addAttribute("today",today);
        model.addAttribute("forward", forward);
        model.addAttribute("backward", backward);
        model.addAttribute("apartment",apartment);

        return "apartment_page";
    }

    @RequestMapping(value = "/cart")
    public String cart() {
        return "cart";
    }
    @RequestMapping(value = "/cart_clear")
    public String clearCart(HttpSession session,
                             Model model) {
        Set<CartApartment> cartApartmentsSet = (TreeSet<CartApartment>)session.getAttribute("cartApartmentsSet");
        cartApartmentsSet.clear();
        session.setAttribute("cartApartmentsSet", cartApartmentsSet);
        model.addAttribute("message", "Cart cleared!");
        return "forward:/";
    }
    @RequestMapping(value = "/cart_delete", method = RequestMethod.POST)
    public String deleteCart(@RequestParam Long apartmentId,
//                              @RequestParam (required = false) Boolean forwarded,
                              HttpServletRequest request,
                              HttpSession session,
                             Model model) {
        Set<CartApartment> cartApartmentsSet = (TreeSet<CartApartment>)session.getAttribute("cartApartmentsSet");
        if(cartApartmentsSet==null)
            return "forward:/";
        CartApartment cartApartment = null;
        for (CartApartment cha: cartApartmentsSet) {
            if(cha.getId()==apartmentId){
                cartApartment = cha;
                break;
            }
        }
        cartApartmentsSet.remove(cartApartment);
        session.setAttribute("cartApartmentsSet",cartApartmentsSet);
        model.addAttribute("message", "Cart changed!");
        Boolean forwarded = (Boolean)request.getAttribute("forwarded");
        if(forwarded!=null && forwarded) {
            return "redirect:/";
        }
        return "forward:/cart";
    }

    @RequestMapping (value = "/client_search")
    public String clientSearch(@RequestParam(value = "pattern") String patternToFind,
                               Model model){
        List<Client> clients = appService.listClients(patternToFind);

        model.addAttribute("found", clients.size());
        model.addAttribute("clients", clients);
        model.addAttribute("patternToFind",patternToFind);
        return "forward:/cart";
    }

    @RequestMapping("/admin/districts_page")
    public String districtsPage(Model model) {
        model.addAttribute("districts", appService.listDistricts());
        return "districts_page";
    }

    @RequestMapping(value = "/admin/district/add", method = RequestMethod.POST)
    public String districtAdd(@RequestParam String name, Model model) {
        appService.addDistrict(new District(name));
        model.addAttribute("districts", appService.listDistricts());
        model.addAttribute("message", "District added");
        return "districts_page";
    }

    @RequestMapping(value = "/admin/district_edit", method = RequestMethod.POST)
    public String districtEdit(@RequestParam(value = "districtId") long districtId,
                              @RequestParam(name = "districtName") String districtName,
                              Model model) {
        District district = appService.findDistrict(districtId);
        district.setName(districtName);
        appService.editDistrict(district);
        model.addAttribute("districts", appService.listDistricts());
        model.addAttribute("message", "District changed");
        return "forward:/admin/districts_page";
    }

    @RequestMapping(value = "/admin/district_delete", method = RequestMethod.POST)
    public String districtDelete(@RequestParam long districtId,
                              Model model) {

        appService.deleteDistrict(appService.getDistrict(districtId));
        model.addAttribute("districts", appService.listDistricts());
        model.addAttribute("message", "District deleted");
        return "forward:/admin/districts_page";
    }

    @RequestMapping("/admin/apartments_page")
    public String apartmentsPage(Model model) {
        model.addAttribute("districts", appService.listDistricts());
        model.addAttribute("apartments", appService.listApartments());
        return "apartments_page";
    }

    @RequestMapping("/admin/apartments_page/{id}")
    public String apartmentsPageistrict (@PathVariable(value = "id") long districtId
            , Model model) {
        District district = appService.findDistrict(districtId);
        model.addAttribute("districts", appService.listDistricts());
        model.addAttribute("apartments", appService.listApartments(district));
        return "apartments_page";
    }
    @RequestMapping("/admin/add_apartment")
    public String addApartment(Model model) {
        model.addAttribute("districts", appService.listDistricts());
        return "add_apartment";
    }

    @RequestMapping(value = "/admin/apartment/add", method = RequestMethod.POST)
    public String apartmentAdd(@RequestParam(value = "districtId") long districtId,
                               @RequestParam String street,
                               @RequestParam String building,
                               @RequestParam (required = false)  Integer aptNumber,
                               @RequestParam MultipartFile[] photos,
                               Model model) throws IOException {
        District district = appService.findDistrict(districtId);

        if(aptNumber==null){
            aptNumber=0;
        }
        Apartment apartment = new Apartment(district, street, building, aptNumber);
        appService.addApartment(apartment);

        savePhotos(apartment,photos);

        model.addAttribute("districts", appService.listDistricts());
        model.addAttribute("apartments", appService.listApartments());
        return "apartments_page";
    }

    @RequestMapping(value = "/admin/apartment_edit", method = RequestMethod.POST)
    public String apartmentEdit(@RequestParam long apartmentId,
                                 Model model) {
        model.addAttribute("apartment", appService.findOneApartment(apartmentId));
        model.addAttribute("districts", appService.listDistricts());
        return "apartment_edit_page";
    }
    @RequestMapping(value = "/admin/apartment/post_edit", method = RequestMethod.POST)
    public String apartmentPostEdit(@RequestParam long districtId,
                                    @RequestParam long apartmentId,
                               @RequestParam String street,
                               @RequestParam String building,
                               @RequestParam (required = false) int aptNumber,
                                    @RequestParam (required = false) long []  toDelete,
                               @RequestParam MultipartFile[] photos,
                               Model model) throws IOException {
        int changes = 0;
        Apartment apartment = appService.findOneApartment(apartmentId);

        if(apartment.getDistrict().getId()!=districtId){
            apartment.setDistrict(appService.findDistrict(districtId));
            changes = 1;
        }
        if(!apartment.getStreet().equals(street)){
            apartment.setStreet(street);
            changes = 1;
        }
        if(!apartment.getBuilding().equals(building)){
            apartment.setBuilding(building);
            changes = 1;
        }
        if(apartment.getAptNumber()!=aptNumber){
            apartment.setAptNumber(aptNumber);
            changes = 1;
        }

        if(changes==1)
        appService.editApartment(apartment);

        if(toDelete!=null){
            changes = 1;
            for (long photoId:toDelete) {
                Image photo = appService.getOneImage(photoId);
//                apartment.removeImage(photo);
                appService.deleteImage(photo);
                File photoToDelete = new File(photosRootDirectory+"/"+apartment.getId(),photo.getFilename());
                photoToDelete.delete();
            }
        }
        boolean savePhotosCheck = savePhotos(apartment,photos);
        model.addAttribute("message",(savePhotosCheck||changes==1)?"Changes saved!":"There is no changes!");
        model.addAttribute("districts", appService.listDistricts());
        model.addAttribute("apartments", appService.listApartments());
        return "apartments_page";
    }
    @RequestMapping(value = "/admin/apartment_delete", method = RequestMethod.POST)
    public String apartmentDelete(@RequestParam long apartmentId,
                                 Model model) throws IOException {
        Apartment apartment = appService.getOneApartment(apartmentId);
        File directoryToDel = new File(photosRootDirectory + "/" + String.valueOf(apartmentId));
        FileUtils.deleteDirectory(directoryToDel);
        appService.deleteApartment(apartment);
        model.addAttribute("districts", appService.listDistricts());
        model.addAttribute("message", "Apartment deleted");
        return "forward:/admin/apartments_page";
    }

    @RequestMapping("/admin/calendar_upload_page")
    public String calendarUploadPage(Model model) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int[] years = new int[]{currentYear, currentYear + 1};
        model.addAttribute("years", years);
        model.addAttribute("months", months);
        return "calendar_upload_page";
    }

    @RequestMapping(value = "/admin/calendar_template_upload_page", method = RequestMethod.POST)
    public String calendarCheck(@RequestParam int year,
                                @RequestParam int month,
                                Model model) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-yyyy");
        Date date = sdf.parse("" + month + "-" + year);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));

        List<Day> days = appService.listMonthDays(date);
        model.addAttribute("year", year);
        model.addAttribute("monthString", months.get(month));
        model.addAttribute("month", month);
        model.addAttribute("date", cal.getTime().getTime());
        model.addAttribute("check", days.size());
        return "calendar_template_upload_page";
    }

    @RequestMapping(value = "/admin/calendar_upload", method = RequestMethod.POST)
    public String calendarAdd(@RequestParam(required = false) Integer weekends,
                              @RequestParam(required = false) Integer[] holidays,
                              @RequestParam int month,
                              @RequestParam int year,
                              Model model) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        Date date = sdf.parse(month + "-1-" + year);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        Set<Integer> days = new TreeSet<>();
        for (int i = 1; i <= maxDay; i++) {
            days.add(i);
        }

        if (holidays != null) {

            Arrays.sort(holidays);


            for (int hday : holidays) {
                cal.set(Calendar.DAY_OF_MONTH, hday);
                appService.addDay(new Day(cal.getTime(), 3));
                days.remove(hday);
            }
        }

        if (weekends != null) {

            Iterator<Integer> iterator = days.iterator();
            while (iterator.hasNext()) {
                int d = iterator.next();
                cal.set(Calendar.DAY_OF_MONTH, d);
                int dow = cal.get(Calendar.DAY_OF_WEEK);
                if (dow == 7 || dow == 1) {
                    appService.addDay(new Day(cal.getTime(), 2));
                    iterator.remove();
                }
            }
        }
        for (int d : days) {
            cal.set(Calendar.DAY_OF_MONTH, d);
            appService.addDay(new Day(cal.getTime(), 1));
        }
        return "redirect:/admin/calendar_upload_page";
    }

    @RequestMapping("/admin/pricing_upload_page")
    public String pricingUploadPage(Model model) {
        model.addAttribute("districts", appService.listDistricts());
        model.addAttribute("apartments", appService.listApartments());
        return "pricing_upload_page";
    }

    @RequestMapping("/admin/pricing_upload_page/{id}")
    public String pricingUploadPageDistrict(@PathVariable(value = "id") long districtId
            , Model model) {
        District district = appService.findDistrict(districtId);
        model.addAttribute("districts", appService.listDistricts());
        model.addAttribute("apartments", appService.listApartments(district));
        return "pricing_upload_page";
    }

    @RequestMapping(value = "/admin/pricing_upload", method = RequestMethod.POST)
    public String priceUpload(@RequestParam long apartmentId,
                              Model model) {
        Apartment apartment = appService.findOneApartment(apartmentId);
        Set<Price> prices = apartment.getPrices();


        model.addAttribute("apartment", apartment);
        model.addAttribute("pricesActual", utility.getPricesByRelevance(prices, "actual"));
        model.addAttribute("pricesArchive", utility.getPricesByRelevance(prices,"archive"));

        return "apartment_pricing_upload_page";
    }

    @RequestMapping(value = "/admin/apartment_pricing_upload", method = RequestMethod.POST)
    public String apartmentPriceUpload(@RequestParam(name = "pricesByTypes", required = false) Double[] pricesByTypes,
                              @RequestParam(name = "dateFrom",required = false) String dateFromStr,
                              @RequestParam(name = "dateTo",required = false) String dateToStr,
                              @RequestParam(required = false) Long apartmentId,
                              Principal principal,
                              Model model) {
        String registrator = principal.getName();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Apartment apartment = appService.findOneApartment(apartmentId);
        Set<Price> prices = apartment.getPrices();
        try {
            Date dateFrom = sdf.parse(dateFromStr);
            Date dateTo = null;
            if (dateToStr.length() != 0) {
                dateTo = sdf.parse(dateToStr);
            }
            Date dateReg = new Date();
            Date todayStart = utility.getStartOfToday();
            Date now = new Date();
            if (dateFrom.before(todayStart)) {
                model.addAttribute("message", "Wrong \"Date from\" input! We can't change past ((( Try again...");
                return "forward:/admin/pricing_upload";
            }
            if (dateFrom.compareTo(todayStart) == 0) {
                dateFrom = dateReg;
            }

            mainCycle:
            for (int i = 0; i < 3; i++) {
                if (pricesByTypes[i] != null) {
                    List<Price> pricesByType = utility.getPricesByRelevanceAndType(prices,"actual", i + 1);
                    if (pricesByType.size() == 0) {
                        Price newPrice = new Price(apartment, i + 1, pricesByTypes[i], dateFrom, dateTo, dateReg, registrator);
                        appService.addPrice(newPrice);

                    } else {
                        Iterator<Price> iter = pricesByType.iterator();
                        while (iter.hasNext()) {
                            Price p = iter.next();
                            if (dateFrom.compareTo(p.getDate_from())==0 && pricesByTypes[i] == p.getPrice()) {
                                if(dateTo==null&&p.getDate_to()==null) {
                                    continue mainCycle;
                                }
                                    else if(dateTo!=null&&p.getDate_to()!=null)
                                    if(dateTo.compareTo(p.getDate_to())==0){
                                        continue mainCycle;
                                    }

                            }
                            if (dateFrom.getTime() <= p.getDate_from().getTime()) {
                                if (dateTo == null) {
                                    p.setArchive(now, registrator);
                                    appService.editPrice(p);
                                    continue;
                                }
                                if (dateTo.getTime() >= p.getDate_from().getTime()) {
                                    p.setArchive(now, registrator);
                                    appService.editPrice(p);
                                    if (p.getDate_to() == null) {
                                        Price newPrice = new Price(apartment, i + 1, p.getPrice(), dateTo, null, p.getDate_reg(), p.getRegistratorReg());
                                        appService.addPrice(newPrice);
                                        continue;
                                    }
                                    if (dateTo.getTime() <= p.getDate_to().getTime()) {
                                        Price newPrice = new Price(apartment, i + 1, p.getPrice(), dateTo, p.getDate_to(), p.getDate_reg(), p.getRegistratorReg());
                                        appService.addPrice(newPrice);
                                        continue;
                                    }
                                }

                            }
                            if (dateFrom.getTime() >= p.getDate_from().getTime()) {
                                if (p.getDate_to() == null) {
                                    p.setArchive(now, registrator);
                                    appService.editPrice(p);
                                    if (dateTo == null) {
                                        Price newPrice = new Price(apartment, i + 1, p.getPrice(), p.getDate_from(), dateFrom, p.getDate_reg(), p.getRegistratorReg());
                                        appService.addPrice(newPrice);
                                        continue;
                                    } else {
                                        Price newPrice = new Price(apartment, i + 1, p.getPrice(), p.getDate_from(), dateFrom, p.getDate_reg(), p.getRegistratorReg());
                                        appService.addPrice(newPrice);
                                        Price newPrice2 = new Price(apartment, i + 1, p.getPrice(), dateTo, null, p.getDate_reg(), p.getRegistratorReg());
                                        appService.addPrice(newPrice2);
                                        continue;
                                    }
                                }
                                if (dateFrom.getTime() >= p.getDate_to().getTime()) {
                                    continue;
                                }
                                if (dateFrom.getTime() <= p.getDate_to().getTime()) {
                                    p.setArchive(now, registrator);
                                    appService.editPrice(p);
                                    Price newPrice = new Price(apartment, i + 1, p.getPrice(), p.getDate_from(), dateFrom, p.getDate_reg(), p.getRegistratorReg());
                                    appService.addPrice(newPrice);
                                    if (dateTo != null) {
                                        if (dateTo.getTime() <= p.getDate_to().getTime()) {
                                            Price newPrice2 = new Price(apartment, i + 1, p.getPrice(), dateTo, p.getDate_to(), p.getDate_reg(), p.getRegistratorReg());
                                            appService.addPrice(newPrice2);
                                        }
                                    }
                                }
                            }

                        }
                        Price newPrice = new Price(apartment, i + 1, pricesByTypes[i], dateFrom, dateTo, dateReg, registrator);
                        appService.addPrice(newPrice);
                    }

                }
            }

            model.addAttribute("message", "Pricing saved!");
            return "forward:/admin/pricing_upload";
        } catch (ParseException e) {
            model.addAttribute("message", "Wrong \"Date from\" format input! Try again...");
            return "forward:/admin/pricing_upload";
        }
    }

    @RequestMapping(value = "/admin/price_edit", method = RequestMethod.POST)
    public String priceEdit(@RequestParam(value = "priceId") long priceId,
                              @RequestParam(name = "price") Double priceValue,
                            Principal principal,
                            Model model) {
        String registartor = principal.getName();
        Date today = Calendar.getInstance().getTime();
        Price price = appService.findOnePrice(priceId);
        Date dateFrom;
        if(price.getDate_from().before(today))
            dateFrom = today;
        else
            dateFrom = price.getDate_from();
        price.setArchive(today, registartor);
        appService.editPrice(price);

        Price newPrice = new Price(price.getApartment(), price.getType(), priceValue, dateFrom, price.getDate_to(), today, registartor);
        appService.addPrice(newPrice);
        model.addAttribute("message", "Price changed");
        return "forward:/admin/pricing_upload";
    }
    @RequestMapping(value = "/admin/price_delete", method = RequestMethod.POST)
    public String priceDelete(@RequestParam long priceId,
//                              @RequestParam(value = "toDelete[]") long [] pricesId,
                              Principal principal,
                              Model model) {
        String registrator = principal.getName();
        Date today = Calendar.getInstance().getTime();
//        for (long id:pricesId) {
//            Price price = appService.findOnePrice(id);
//            price.setArchive(today, "Admin");
//            appService.editPrice(price);
//        }
        Price price = appService.findOnePrice(priceId);
        price.setArchive(today, registrator);
        appService.editPrice(price);
        model.addAttribute("message", "Price deleted");
        return "forward:/admin/pricing_upload";
    }
    @RequestMapping("/photo/{apartmentId}/{filename:.+}")
    public ResponseEntity<byte[]> onPhoto(@PathVariable("apartmentId") String apartmentId,
                                          @PathVariable("filename") String filename) throws IOException {
        return photoById(apartmentId, filename);
    }


    @RequestMapping(value = "/client_select" , method = RequestMethod.POST)
    public String clientSelect(@RequestParam Long clientId,
            Model model){
        model.addAttribute("client", appService.findOneClient(clientId));
        return "/cart";
    }
    @RequestMapping(value = "/new_client" , method = RequestMethod.POST)
    public String newClient(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String address,
            @RequestParam String eMail,
            @RequestParam String phoneNumber,
            @RequestParam (required = false) Integer discount,
            Model model){
        Client client = new Client(firstName,lastName,address,eMail,phoneNumber,discount!=null?discount:0);
        appService.addClient(client);
        model.addAttribute("client", client);
        return "/cart";
    }
    @Transactional
    @RequestMapping(value = "/new_order" , method = RequestMethod.POST)
    public String newOrder(@RequestParam Long clientId,
                           Principal principal,
                           HttpSession session,
                           Model model){

        Client client  = appService.getOneClient(clientId);
        Order order = new Order(client, principal.getName());
        Set<CartApartment> cartApartments = (TreeSet<CartApartment>)session.getAttribute("cartApartmentsSet");
        double totalPrice = 0;
        for (CartApartment ca: cartApartments) {
            totalPrice += ca.getTotalPrice();
            order.setApartment(ca);
            Map<Date, Set<DayPrice>> monthDaysMap = ca.getMonthDaysMap();
            for (Map.Entry<Date, Set<DayPrice>> entry: monthDaysMap.entrySet()) {
                Set<DayPrice> dayPrice = entry.getValue();
                for (DayPrice dp: dayPrice) {
                    Day day = dp.getDay();
                    Price price = dp.getPrice();
                    Booking booking = new Booking(ca,price,day);
                    order.setBooking(booking);
                }
            }
        }

        order.setPrice(totalPrice);
        appService.addOrder(order);

        cartApartments.clear();
        session.setAttribute("cartApartmentsSet", cartApartments);
        model.addAttribute("message", "Order saved");
        return "forward:/";
    }

    @RequestMapping(value = "/orders_page")
    public String orders(Model model){
        model.addAttribute("orders", appService.listOrders());
        return "/orders_page";
    }
    @RequestMapping (value = "/orders_search")
    public String ordersSearch(@RequestParam(value = "pattern") String patternToFind,
                               Model model){


        return "forward:/cart";
    }
    @RequestMapping (value = "/client_orders_search")
    public String clientOrdersSearch(@RequestParam(value = "pattern") String patternToFind,
                               Model model){
        clientSearch(patternToFind,model);
        return "/orders_page";
    }
    @RequestMapping(value = "/client_orders_select" , method = RequestMethod.POST)
    public String cientOrdersSelect(@RequestParam Long clientId,
                            Model model) {
        Client client = appService.findOneClient(clientId);
        List <Order> orders = appService.listOrders(client);
        if (orders.size()==0){
            model.addAttribute("error", "Orders not found for client  " + client.getFirstName() +" " + client.getLastName());
        }
        else {
            model.addAttribute("message", "Orders for client  " + client.getFirstName() +" " + client.getLastName());
            model.addAttribute("orders", appService.listOrders(client));
        }
        return "/orders_page";
    }

    @RequestMapping(value = "/order_edit", method = RequestMethod.POST)
    public String orderEdit(@RequestParam long orderId,
                                Model model) {
        model.addAttribute("order", appService.findOneOrder(orderId));
        return "order_edit_page";
    }
    @Transactional
    @RequestMapping(value = "/order_delete", method = RequestMethod.POST)
    public String orderDelete(@RequestParam long orderId,
                              Model model) {
        Order order = appService.getOneOrder(orderId);
//        Iterator<Booking> i = order.getBookings().iterator();
//        while(i.hasNext()){
//            Booking b = i.next();
//            System.out.println("HASH: "+b.hashCode());
////            order.removeBooking(b,i);
//            appService.deleteBooking(b);
//        }

        order.removeBookings();
        order.removeApartments();

        appService.deleteOrder(order);
//        model.addAttribute("order", appService.findOneOrder(orderId));

        return "redirect:/";
    }
    private ResponseEntity<byte[]> photoById(String apartmentId, String filename) throws IOException {

        File photo = new File(photosRootDirectory +"/"+apartmentId+"/"+filename);
        byte [] bytes = new byte[(int)photo.length()];
        FileInputStream fileInputStream = new FileInputStream(photo);
        fileInputStream.read(bytes);
        fileInputStream.close();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
    }

    private boolean savePhotos (Apartment apartment, MultipartFile[] photos) throws IOException {
        long id = apartment.getId();
        File photosDirectory = new File(photosRootDirectory +"/"+id);
        photosDirectory.mkdirs();
        if (photos[0].getSize() != 0) {
            for (MultipartFile ph : photos) {
                long nameId = System.currentTimeMillis();
                String fileExt = ph.getOriginalFilename().substring(ph.getOriginalFilename().lastIndexOf("."));
                String filename = String.valueOf(nameId).concat(fileExt);
                appService.addImage( new  Image(nameId, filename, apartment));
                File image = new File(photosDirectory, filename);
                image.createNewFile();
                FileOutputStream fos = new FileOutputStream(image);
                fos.write(ph.getBytes());
                fos.close();
            }
            return  true;
        }
        return false;

    }
    private String getErrorMessage(HttpServletRequest request, String key) {

        Exception exception = (Exception) request.getSession().getAttribute(key);

        String error = "";
        if (exception instanceof BadCredentialsException) {
            error = "Invalid username/password!";
        } else if (exception instanceof LockedException) {
            error = exception.getMessage();
        } else {
            error = "Invalid username/password!";
        }

        return error;
    }
}




