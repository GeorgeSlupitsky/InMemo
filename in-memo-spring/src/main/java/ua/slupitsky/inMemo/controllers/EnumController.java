package ua.slupitsky.inMemo.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.slupitsky.inMemo.models.dto.*;
import ua.slupitsky.inMemo.models.enums.*;

import java.util.*;

@RestController
@RequestMapping("/api/enums")
@Api(value = "enum", description = "Operations with Enums")
public class EnumController {

    @ApiOperation(value = "Get all CD's Booklets Enum", response = Iterable.class)
    @GetMapping("/cds/booklets")
    public Iterable<CDBookletForm> getAllCDBooklets(Locale locale){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("InMemo", locale);
        List<CDBookletForm> booklets = new ArrayList<>();
        int id = 0;
        for (Enum e: EnumSet.allOf(CDBooklet.class) ){
            CDBookletForm form = new CDBookletForm();
            CDBooklet booklet = (CDBooklet) e;
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
        ResourceBundle resourceBundle = ResourceBundle.getBundle("InMemo", locale);
        List<CDCountryForm> countries = new ArrayList<>();
        int id = 0;
        for (Enum e: EnumSet.allOf(CDCountry.class) ){
            CDCountryForm form = new CDCountryForm();
            CDCountry country = (CDCountry) e;
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
        ResourceBundle resourceBundle = ResourceBundle.getBundle("InMemo", locale);
        List<CDTypeForm> types = new ArrayList<>();
        int id = 0;
        for (Enum e: EnumSet.allOf(CDType.class) ){
            CDTypeForm form = new CDTypeForm();
            CDType type = (CDType) e;
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
        ResourceBundle resourceBundle = ResourceBundle.getBundle("InMemo", locale);
        List<CDGroupForm> groups = new ArrayList<>();
        int id = 0;
        for (Enum e: EnumSet.allOf(CDGroup.class) ){
            CDGroupForm form = new CDGroupForm();
            CDGroup group = (CDGroup) e;
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
        ResourceBundle resourceBundle = ResourceBundle.getBundle("InMemo", locale);
        List<CDBandOrderForm> orders = new ArrayList<>();
        int id = 0;
        for (Enum e: EnumSet.allOf(CDBandOrder.class) ){
            CDBandOrderForm form = new CDBandOrderForm();
            CDBandOrder bandOrder = (CDBandOrder) e;
            form.setId(id++);
            form.setName(resourceBundle.getString(bandOrder.getName()));
            form.setCdBandOrder(bandOrder);
            orders.add(form);
        }
        return orders;
    }

}
