package com.petro.apartments.common;

import com.petro.apartments.entity.*;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/")
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
//    @Autowired
//    PricesActualityCheck check;

//    Boolean timerStart = false;

    @RequestMapping("/")
    public String index(Model model) throws ParseException {
//        model.addAttribute("apartments", appService.listApartments());

        model.addAttribute("districts", appService.listDistricts());
        model.addAttribute("apartments", appService.listApartments());

        SimpleDateFormat sdf = new SimpleDateFormat("MM-yyyy");
        Date date = sdf.parse("3-2016");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        model.addAttribute("date", cal.getTime().getTime());
        return "index";
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
    public String apartment(@RequestParam(value = "aptartmentId") long apartmentId,
                              Model model) {
        Apartment apartment = appService.findOneApartment(apartmentId);

        model.addAttribute("apartment", apartment);


        return "apartment_page";
    }
    @RequestMapping("/districts_page")
    public String districtsPage(Model model) {
        model.addAttribute("districts", appService.listDistricts());
        return "districts_page";
    }

    @RequestMapping(value = "/district/add", method = RequestMethod.POST)
    public String districtAdd(@RequestParam String name, Model model) {
        appService.addDistrict(new District(name));
        model.addAttribute("districts", appService.listDistricts());
        model.addAttribute("message", "District added");
        return "districts_page";
    }

    @RequestMapping(value = "/district_edit", method = RequestMethod.POST)
    public String districtEdit(@RequestParam(value = "districtId") long districtId,
                              @RequestParam(name = "districtName") String districtName,
                              Model model) {
        District district = appService.findDistrict(districtId);
        district.setName(districtName);
        appService.editDistrict(district);
        model.addAttribute("districts", appService.listDistricts());
        model.addAttribute("message", "District changed");
        return "forward:/districts_page";
    }

    @RequestMapping(value = "/district_delete", method = RequestMethod.POST)
    public String districtDelete(@RequestParam long districtId,
                              Model model) {

        appService.deleteDistrict(appService.getDistrict(districtId));
        model.addAttribute("districts", appService.listDistricts());
        model.addAttribute("message", "District deleted");
        return "forward:/districts_page";
    }

    @RequestMapping("/apartments_page")
    public String apartmentsPage(Model model) {
        model.addAttribute("districts", appService.listDistricts());
        model.addAttribute("apartments", appService.listApartments());
        return "apartments_page";
    }

    @RequestMapping("/apartments_page/{id}")
    public String apartmentsPageistrict (@PathVariable(value = "id") long districtId
            , Model model) {
        District district = appService.findDistrict(districtId);
        model.addAttribute("districts", appService.listDistricts());
        model.addAttribute("apartments", appService.listApartments(district));
        return "apartments_page";
    }
    @RequestMapping("/add_apartment")
    public String addApartment(Model model) {
        model.addAttribute("districts", appService.listDistricts());
        return "add_apartment";
    }

    @RequestMapping(value = "/apartment/add", method = RequestMethod.POST)
    public String apartmentAdd(@RequestParam(value = "districtId") long districtId,
                               @RequestParam String street,
                               @RequestParam String building,
                               @RequestParam int aptNumber,
                               @RequestParam MultipartFile[] photos,
                               Model model) throws IOException {
        District district = appService.findDistrict(districtId);

        Apartment apartment = new Apartment(district, street, building, aptNumber, 0, 0);
        appService.addApartment(apartment);
        long id = apartment.getId();
        File photosDirectory = new File(photosRootDirectory +"/"+id);
        photosDirectory.mkdirs();

        if(photos.length != 1 && photos[0].getSize()!=0) {
            for (MultipartFile ph : photos) {
                long nameId = System.currentTimeMillis();
                String fileExt = ph.getOriginalFilename().substring(ph.getOriginalFilename().lastIndexOf("."));
                String filename = String.valueOf(nameId).concat(fileExt);
                appService.addImage(new Image(nameId, filename, apartment));

                File image = new File(photosDirectory, filename);
                image.createNewFile();
                FileOutputStream fos = new FileOutputStream(image);
                fos.write(ph.getBytes());
                fos.close();
            }
        }
        model.addAttribute("districts", appService.listDistricts());
        model.addAttribute("apartments", appService.listApartments());
        return "apartments_page";
    }

    @RequestMapping(value = "/apartment_edit", method = RequestMethod.POST)
    public String apartmentEdit(@RequestParam(value = "apartmentId") long apartmentId,
                                 Model model) {
        model.addAttribute("districts", appService.listDistricts());
        model.addAttribute("message", "District changed");
        return "apartment_edit_page";
    }

    @RequestMapping(value = "/apartment_delete", method = RequestMethod.POST)
    public String apartmentDelete(@RequestParam long apartmentId,
                                 Model model) throws IOException {
        Apartment apartment = appService.getOneApartment(apartmentId);
        File directoryToDel = new File(photosRootDirectory + "/" + String.valueOf(apartmentId));
        FileUtils.deleteDirectory(directoryToDel);
        appService.deleteApartment(apartment);
        model.addAttribute("districts", appService.listDistricts());
        model.addAttribute("message", "Apartment deleted");
        return "forward:/apartments_page";
    }

    @RequestMapping("/calendar_upload_page")
    public String calendarUploadPage(Model model) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int[] years = new int[]{currentYear, currentYear + 1};
        model.addAttribute("years", years);
        model.addAttribute("months", months);
        return "calendar_upload_page";
    }

    @RequestMapping(value = "/calendar_template_upload_page", method = RequestMethod.POST)
    public String calendarCheck(@RequestParam int year,
                                @RequestParam int month,
                                Model model) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-yyyy");
        Date date = sdf.parse("" + month + "-" + year);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));

        List<Day> days = appService.listDays(date);
        model.addAttribute("year", year);
        model.addAttribute("monthString", months.get(month));
        model.addAttribute("month", month);
        model.addAttribute("date", cal.getTime().getTime());
        model.addAttribute("check", days.size());
        return "calendar_template_upload_page";
    }

    @RequestMapping(value = "/calendar_upload", method = RequestMethod.POST)
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
        return "redirect:/calendar_upload_page";
    }

    @RequestMapping("/pricing_upload_page")
    public String pricingUploadPage(Model model) {
        model.addAttribute("districts", appService.listDistricts());
        model.addAttribute("apartments", appService.listApartments());
        return "pricing_upload_page";
    }

    @RequestMapping("/pricing_upload_page/{id}")
    public String pricingUploadPageDistrict(@PathVariable(value = "id") long districtId
            , Model model) {
        District district = appService.findDistrict(districtId);
        model.addAttribute("districts", appService.listDistricts());
        model.addAttribute("apartments", appService.listApartments(district));
        return "pricing_upload_page";
    }

    @RequestMapping(value = "/pricing_upload", method = RequestMethod.POST)
    public String priceUpload(@RequestParam(value = "aptartmentId") long apartmentId,
                              Model model) {
        Apartment apartment = appService.findOneApartment(apartmentId);
        List<Price> prices = apartment.getPrices();


        model.addAttribute("apartment", apartment);
        model.addAttribute("pricesActual", Utility.getPricesByRevelance(prices, "actual"));
        model.addAttribute("pricesArchive", Utility.getPricesByRevelance(prices,"archive"));

        return "apartment_pricing_upload_page";
    }

    @RequestMapping(value = "apartment_pricing_upload", method = RequestMethod.POST)
    public String apartmentPriceUpload(@RequestParam(required = false) Double[] types,
                              @RequestParam(name = "dateFrom",required = false) String dateFromStr,
                              @RequestParam(name = "dateTo",required = false) String dateToStr,
                              @RequestParam(required = false) Long aptId,
                              Model model) {

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Apartment apartment = appService.findOneApartment(aptId);
        List<Price> prices = apartment.getPrices();
        try {
            Date dateFrom = sdf.parse(dateFromStr);
            Date dateTo = null;
            if (dateToStr.length() != 0) {
                dateTo = sdf.parse(dateToStr);
            }
            Date dateReg = new Date();
            Date todayStart = Utility.getStartOfToday();
            Date now = new Date();
            if (dateFrom.before(todayStart)) {
                model.addAttribute("message", "Wrong \"Date from\" input! We can't change past ((( Try again...");
                return "forward:/pricing_upload";
            }
            if (dateFrom.compareTo(todayStart) == 0) {
                dateFrom = dateReg;
            }

            mainCycle:
            for (int i = 0; i < 3; i++) {
                if (types[i] != null) {
                    List<Price> pricesByType = Utility.getPricesByRevelanceAndType(prices,"actual", i + 1);
                    if (pricesByType.size() == 0) {
                        Price newPrice = new Price(apartment, i + 1, types[i], dateFrom, dateTo, dateReg, "Admin");
                        appService.addPrice(newPrice);

                    } else {
                        Iterator<Price> iter = pricesByType.iterator();
                        while (iter.hasNext()) {
                            Price p = iter.next();
                            if (dateFrom.compareTo(p.getDate_from())==0 && types[i] == p.getPrice()) {
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
                                    p.setArchive(now, "Admin");
                                    appService.editPrice(p);
                                    continue;
                                }
                                if (dateTo.getTime() >= p.getDate_from().getTime()) {
                                    p.setArchive(now, "Admin");
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
                                    p.setArchive(now, "Admin");
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
                                    p.setArchive(now, "Admin");
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
                        Price newPrice = new Price(apartment, i + 1, types[i], dateFrom, dateTo, dateReg, "Admin");
                        appService.addPrice(newPrice);
                    }

                }
            }

            model.addAttribute("message", "Pricing saved!");
            return "forward:/pricing_upload";
        } catch (ParseException e) {
            model.addAttribute("message", "Wrong \"Date from\" format input! Try again...");
            return "forward:/pricing_upload";
        }
    }

    @RequestMapping(value = "/price_edit", method = RequestMethod.POST)
    public String priceEdit(@RequestParam(value = "priceId") long priceId,
                              @RequestParam(name = "price") Double priceValue,
                              Model model) {
        Date today = Calendar.getInstance().getTime();
        Price price = appService.findOnePrice(priceId);
        Date dateFrom;
        if(price.getDate_from().before(today))
            dateFrom = today;
        else
            dateFrom = price.getDate_from();
        price.setArchive(today, "Admin");
        appService.editPrice(price);

        Price newPrice = new Price(price.getApartment(), price.getType(), priceValue, dateFrom, price.getDate_to(), today, "Admin");
        appService.addPrice(newPrice);
        model.addAttribute("message", "Price changed");
        return "forward:/pricing_upload";
    }
    @RequestMapping(value = "/price_delete", method = RequestMethod.POST)
    public String priceDelete(@RequestParam long priceId,
//                              @RequestParam(value = "toDelete[]") long [] pricesId,
                              Model model) {
        Date today = Calendar.getInstance().getTime();
//        for (long id:pricesId) {
//            Price price = appService.findOnePrice(id);
//            price.setArchive(today, "Admin");
//            appService.editPrice(price);
//        }
        Price price = appService.findOnePrice(priceId);
        price.setArchive(today, "Admin");
        appService.editPrice(price);
        model.addAttribute("message", "Price deleted");
        return "forward:/pricing_upload";
    }
    @RequestMapping("/photo/{apartmentId}/{filename:.+}")
    public ResponseEntity<byte[]> onPhoto(@PathVariable("apartmentId") String apartmentId,
                                          @PathVariable("filename") String filename) throws IOException {
        return photoById(apartmentId, filename);
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
}

