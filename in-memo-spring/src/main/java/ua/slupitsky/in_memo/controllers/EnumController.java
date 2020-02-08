package ua.slupitsky.in_memo.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.slupitsky.in_memo.dto.*;
import ua.slupitsky.in_memo.enums.*;
import ua.slupitsky.in_memo.utils.Utils;

import java.util.*;

import static ua.slupitsky.in_memo.constants.DefaultAppConstants.BASE_NAME;

@RestController
@RequestMapping("/api/enums")
@Api(value = "enum")
public class EnumController {

    @ApiOperation(value = "Get all CD's Booklets Enum", response = Iterable.class)
    @GetMapping("/cds/booklets")
    public Iterable<CDBookletForm> getAllCDBooklets(Locale locale){
        ResourceBundle resourceBundle = ResourceBundle.getBundle(BASE_NAME, Utils.getCorrectLocale(locale));
        List<CDBookletForm> booklets = new ArrayList<>();
        int id = 0;
        for (CDBooklet booklet: EnumSet.allOf(CDBooklet.class) ){
            CDBookletForm form = new CDBookletForm();
            form.setId(id++);
            if (booklet.getQuantityOfPages() == 0){
                form.setName(resourceBundle.getString(booklet.getName()));
            } else {
                form.setName(booklet.getQuantityOfPages() + " " + resourceBundle.getString(booklet.getName()));
            }
            form.setCdBookletEnum(booklet);
            booklets.add(form);
        }
        return booklets;
    }

    @ApiOperation(value = "Get all CD's Countries Enum", response = Iterable.class)
    @GetMapping("/cds/countries")
    public Iterable<CDCountryForm> getAllCDCountries(Locale locale){
        ResourceBundle resourceBundle = ResourceBundle.getBundle(BASE_NAME, Utils.getCorrectLocale(locale));
        List<CDCountryForm> countries = new ArrayList<>();
        int id = 0;
        for (CDCountry country: EnumSet.allOf(CDCountry.class) ){
            CDCountryForm form = new CDCountryForm();
            form.setId(id++);
            form.setName(resourceBundle.getString(country.getName()));
            form.setCdCountryEnum(country);
            countries.add(form);
        }
        return countries;
    }

    @ApiOperation(value = "Get all CD's Types Enum", response = Iterable.class)
    @GetMapping("/cds/types")
    public Iterable<CDTypeForm> getAllCDTypes(Locale locale){
        ResourceBundle resourceBundle = ResourceBundle.getBundle(BASE_NAME, Utils.getCorrectLocale(locale));
        List<CDTypeForm> types = new ArrayList<>();
        int id = 0;
        for (CDType type: EnumSet.allOf(CDType.class) ){
            CDTypeForm form = new CDTypeForm();
            form.setId(id++);
            form.setName(resourceBundle.getString(type.getName()));
            form.setCdTypeEnum(type);
            types.add(form);
        }
        return types;
    }

    @ApiOperation(value = "Get all CD's Groups Enum", response = Iterable.class)
    @GetMapping("/cds/groups")
    public Iterable<CDGroupForm> getAllCDGroups(Locale locale){
        ResourceBundle resourceBundle = ResourceBundle.getBundle(BASE_NAME, Utils.getCorrectLocale(locale));
        List<CDGroupForm> groups = new ArrayList<>();
        int id = 0;
        for (CDGroup group: EnumSet.allOf(CDGroup.class) ){
            CDGroupForm form = new CDGroupForm();
            form.setId(id++);
            form.setName(resourceBundle.getString(group.getName()));
            form.setCdGroupEnum(group);
            groups.add(form);
        }
        return groups;
    }

    @ApiOperation(value = "Get all CD's Band Order Enum", response = Iterable.class)
    @GetMapping("/cds/band/orders")
    public Iterable<CDBandOrderForm> getAllCDBandOrders(Locale locale){
        ResourceBundle resourceBundle = ResourceBundle.getBundle(BASE_NAME, Utils.getCorrectLocale(locale));
        List<CDBandOrderForm> orders = new ArrayList<>();
        int id = 0;
        for (CDBandOrder bandOrder: EnumSet.allOf(CDBandOrder.class) ){
            CDBandOrderForm form = new CDBandOrderForm();
            form.setId(id++);
            form.setName(resourceBundle.getString(bandOrder.getName()));
            form.setCdBandOrder(bandOrder);
            orders.add(form);
        }
        return orders;
    }

    @ApiOperation(value = "Get all DrumStick's Cities Enum", response = Iterable.class)
    @GetMapping("/drumsticks/cities")
    public Iterable<DrumStickCityForm> getAllDrumStickCity(Locale locale){
        ResourceBundle resourceBundle = ResourceBundle.getBundle(BASE_NAME, Utils.getCorrectLocale(locale));
        List<DrumStickCityForm> cities = new ArrayList<>();
        int id = 0;
        for (DrumStickCity city: EnumSet.allOf(DrumStickCity.class) ){
            DrumStickCityForm form = new DrumStickCityForm();
            form.setId(id++);
            form.setName(resourceBundle.getString(city.getName()));
            form.setDrumStickCityEnum(city);
            cities.add(form);
        }
        return cities;
    }

    @ApiOperation(value = "Get all DrumStick's Types Enum", response = Iterable.class)
    @GetMapping("/drumstick/types")
    public Iterable<DrumStickTypeForm> getAllDrumStickType(Locale locale){
        ResourceBundle resourceBundle = ResourceBundle.getBundle(BASE_NAME, Utils.getCorrectLocale(locale));
        List<DrumStickTypeForm> types = new ArrayList<>();
        int id = 0;
        for (DrumStickType type: EnumSet.allOf(DrumStickType.class) ){
            DrumStickTypeForm form = new DrumStickTypeForm();
            form.setId(id++);
            form.setName(resourceBundle.getString(type.getName()));
            form.setDrumStickTypeEnum(type);
            types.add(form);
        }
        return types;
    }
}
