package com.petro.apartments.common;

import com.petro.apartments.entity.Apartment;
import com.petro.apartments.entity.Day;
import com.petro.apartments.entity.District;
import com.petro.apartments.entity.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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

    @RequestMapping("/district_add_page")
    public String districtAddPage() {
        return "district_add_page";
    }

    @RequestMapping(value = "/district/add", method = RequestMethod.POST)
    public String districtAdd(@RequestParam String name, Model model) {
        appService.addDistrict(new District(name));
        return "district_add_page";
    }

    @RequestMapping("/apartment_add_page")
    public String apartmentAddPage(Model model) {
        model.addAttribute("districts", appService.listDistricts());
        return "apartment_add_page";
    }

    @RequestMapping(value = "/apartment/add", method = RequestMethod.POST)
    public String apartmentAdd(@RequestParam(value = "districtId") long districtId,
                               @RequestParam String street,
                               @RequestParam String building,
                               @RequestParam int aptNumber,
                               Model model) {
        District district = appService.findDistrict(districtId);

        Apartment apartment = new Apartment(district, street, building, aptNumber, 0, 0);
        appService.addApartment(apartment);

        model.addAttribute("districts", appService.listDistricts());
        model.addAttribute("apartments", appService.listApartments());
        return "index";
    }

    @RequestMapping("/calendar_upload_page")
    public String calendarUploadPage(Model model) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int[] years = new int[]{currentYear, currentYear + 1};
        model.addAttribute("years", years);
        model.addAttribute("months", months);
        return "calendar_upload_page";
    }

    @RequestMapping(value = "calendar_template_upload_page", method = RequestMethod.POST)
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

        if (holidays.length != 0) {

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
    public String pricingUploadPageGroup(@PathVariable(value = "id") long districtId
            , Model model) {
        District district = appService.findDistrict(districtId);
        model.addAttribute("districts", appService.listDistricts());
        model.addAttribute("apartments", appService.listApartments(district));
        return "pricing_upload_page";
    }

    @RequestMapping(value = "/pricing_upload", method = RequestMethod.POST)
    public String priceUpload(@RequestParam(value = "aptId") long aptId,
                              Model model) {
        Apartment apartment = appService.findOneApartment(aptId);
        List<Price> prices = apartment.getPrices();


        model.addAttribute("apartment", apartment);
        model.addAttribute("pricesActual", Utility.getActualPrices(prices));

        return "apartment_pricing_upload_page";
    }

    @RequestMapping(value = "apartment_pricing_upload", method = RequestMethod.POST)
    public String apartmentPriceUpload(@RequestParam(required = false) Double[] types,
                              @RequestParam(required = false) String dateFrom,
                              @RequestParam(required = false) String dateTo,
                              @RequestParam(required = false) Long aptId,
                              Model model) {

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Apartment apartment = appService.findOneApartment(aptId);
        List<Price> prices = apartment.getPrices();
        try {
            Date dateF = sdf.parse(dateFrom);
            Date dateT = null;
            if (dateTo.length() != 0) {
                dateT = sdf.parse(dateTo);
            }
            Date dateReg = new Date();
            Date today = Utility.getStartOfToday();
            if (dateF.before(today)) {
                model.addAttribute("message", "Wrong \"Date from\" input! We can't change past ((( Try again...");
                return "forward:/pricing_upload";
            }
            if (dateF.compareTo(today) == 0) {
                dateF = dateReg;
            }

            mainCycle:
            for (int i = 0; i < 3; i++) {
                if (types[i] != null) {
                    List<Price> pricesByType = Utility.getActualPricesByType(prices, i + 1);
                    if (pricesByType.size() == 0) {
                        Price newPrice = new Price(apartment, i + 1, types[i], dateF, dateT, dateReg, "Admin");
                        appService.addPrice(newPrice);

                    } else {
                        Iterator<Price> iter = pricesByType.iterator();
                        while (iter.hasNext()) {
                            Price p = iter.next();
                            if (dateF.compareTo(p.getDate_from())==0 && types[i] == p.getPrice()) {
                                if(dateT==null&&p.getDate_to()==null) {
                                    continue mainCycle;
                                }
                                    else if(dateT!=null&&p.getDate_to()!=null)
                                    if(dateT.compareTo(p.getDate_to())==0){
                                        continue mainCycle;
                                    }

                            }
                            if (dateF.getTime() <= p.getDate_from().getTime()) {
                                if (dateT == null) {
                                    p.setArchive(today, "Admin");
                                    appService.changePrice(p);
                                    continue;
                                }
                                if (dateT.getTime() >= p.getDate_from().getTime()) {
                                    p.setArchive(today, "Admin");
                                    appService.changePrice(p);
                                    if (p.getDate_to() == null) {
                                        Price newPrice = new Price(apartment, i + 1, p.getPrice(), dateT, null, p.getDate_reg(), p.getRegistratorReg());
                                        appService.addPrice(newPrice);
                                        continue;
                                    }
                                    if (dateT.getTime() <= p.getDate_to().getTime()) {
                                        Price newPrice = new Price(apartment, i + 1, p.getPrice(), dateT, p.getDate_to(), p.getDate_reg(), p.getRegistratorReg());
                                        appService.addPrice(newPrice);
                                        continue;
                                    }
                                }

                            }
                            if (dateF.getTime() >= p.getDate_from().getTime()) {
                                if (p.getDate_to() == null) {
                                    p.setArchive(today, "Admin");
                                    appService.changePrice(p);
                                    if (dateT == null) {
                                        Price newPrice = new Price(apartment, i + 1, p.getPrice(), p.getDate_from(), dateF, p.getDate_reg(), p.getRegistratorReg());
                                        appService.addPrice(newPrice);
                                        continue;
                                    } else {
                                        Price newPrice = new Price(apartment, i + 1, p.getPrice(), p.getDate_from(), dateF, p.getDate_reg(), p.getRegistratorReg());
                                        appService.addPrice(newPrice);
                                        Price newPrice2 = new Price(apartment, i + 1, p.getPrice(), dateT, null, p.getDate_reg(), p.getRegistratorReg());
                                        appService.addPrice(newPrice2);
                                        continue;
                                    }
                                }
                                if (dateF.getTime() >= p.getDate_to().getTime()) {
                                    continue;
                                }
                                if (dateF.getTime() <= p.getDate_to().getTime()) {
                                    p.setArchive(today, "Admin");
                                    appService.changePrice(p);
                                    Price newPrice = new Price(apartment, i + 1, p.getPrice(), p.getDate_from(), dateF, p.getDate_reg(), p.getRegistratorReg());
                                    appService.addPrice(newPrice);
                                    if (dateT != null) {
                                        if (dateT.getTime() <= p.getDate_to().getTime()) {
                                            Price newPrice2 = new Price(apartment, i + 1, p.getPrice(), dateT, p.getDate_to(), p.getDate_reg(), p.getRegistratorReg());
                                            appService.addPrice(newPrice2);
                                        }
                                    }
                                }
                            }

                        }
                        Price newPrice = new Price(apartment, i + 1, types[i], dateF, dateT, dateReg, "Admin");
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

    @RequestMapping(value = "/price_hange", method = RequestMethod.POST)
    public String priceChange(@RequestParam(value = "priceId") long priceId,
                              Model model) {



        return "apartment_pricing_upload_page";
    }
}

