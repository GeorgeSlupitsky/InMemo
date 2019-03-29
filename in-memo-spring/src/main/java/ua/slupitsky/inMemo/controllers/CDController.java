package ua.slupitsky.inMemo.controllers;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.slupitsky.inMemo.models.dto.*;
import ua.slupitsky.inMemo.models.enums.CDBooklet;
import ua.slupitsky.inMemo.models.enums.CDCountry;
import ua.slupitsky.inMemo.models.enums.CDGroup;
import ua.slupitsky.inMemo.models.enums.CDType;
import ua.slupitsky.inMemo.models.mongo.CD;
import ua.slupitsky.inMemo.services.CDService;
import ua.slupitsky.inMemo.utils.ExcelParser;
import ua.slupitsky.inMemo.validation.exceptions.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
@Api(value = "cd_collection", description = "Operations with CD in my collections")
public class CDController {

    private static Logger log = Logger.getLogger(CDController.class.getName());

    private final CDService cdService;

    @Autowired
    public CDController(CDService cdService) {
        this.cdService = cdService;
    }

    @ApiOperation(value = "View a list of CDs", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to views the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/cds")
    public Iterable<CDForm> getCDList(Locale locale){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("InMemo", locale);
        return cdService.findAllCDs(resourceBundle);
    }

    @ApiOperation(value = "View a foreign list of CDs", response = Iterable.class)
    @GetMapping("/cdsForeign")
    public Iterable<CDForm> getForeignCDList(Locale locale){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("InMemo", locale);
        return cdService.findByCDGroupWithResourceBundle(CDGroup.FOREIGN, resourceBundle);
    }

    @ApiOperation(value = "View a domestic list of CDs", response = Iterable.class)
    @GetMapping("/cdsDomestic")
    public Iterable<CDForm> getDomesticCDList(Locale locale){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("InMemo", locale);
        return cdService.findByCDGroupWithResourceBundle(CDGroup.DOMESTIC, resourceBundle);
    }

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

    @ApiOperation(value = "Search CD with an ID", response = CD.class)
    @RequestMapping(value = "/cd/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> showCD(@PathVariable Integer id){
        Optional<CD> cd = cdService.findCDById(id);
        return cd.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @ApiOperation(value = "Add CD")
    @PostMapping(value = "/cd")
    public ResponseEntity<String> saveCD (@RequestBody CD cd){
        cdService.addCD(cd);
        log.info("CD " + cd.getBand().getName() + " - " + cd.getAlbum() + " added");
        return new ResponseEntity<>("CD saved successfully", HttpStatus.OK);
    }

    @ApiOperation(value = "Update CD")
    @PutMapping(value = "/cd")
    public ResponseEntity<String> updateCD(@Valid @RequestBody CD cd){
        cdService.updateCD(cd.getId(), cd);
        log.info("CD with id: " + cd.getId() + " : " + cd.getBand().getName() + " - " + cd.getAlbum() + " updated");
        return new ResponseEntity<>("CD updated successfully", HttpStatus.OK);
    }

    @ApiOperation(value = "Delete CD")
    @DeleteMapping(value = "/cd/{id}")
    public ResponseEntity<String> deleteCD (@PathVariable Integer id){
        cdService.removeCD(id);
        log.info("CD with id: " + id + " deleted");
        return new ResponseEntity<>("CD deleted successfully", HttpStatus.OK);
    }

    @ApiOperation(value = "Make a POST request to upload the file",
            produces = "application/json", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The POST call is Successful"),
            @ApiResponse(code = 500, message = "The POST call is Failed"),
            @ApiResponse(code = 404, message = "The API could not be found")
    })
    @PostMapping("/cd/upload")
    public ResponseEntity<String> uploadExcelCD(
            @ApiParam(name = "file", value = "Select the file to Upload", required = true)
            @RequestPart("file") MultipartFile file,
            @ApiParam(name = "rewriteDB", value = "Checked if you want to Rewrite DB from Xls or Xlsx file", required = true)
            @RequestParam boolean rewriteDB) {
        List<CD> cds;

        try {
            if (Objects.requireNonNull(file.getOriginalFilename()).contains(".xlsx")){
                cds = ExcelParser.parseExcelForCDs(file, true);
            } else {
                cds = ExcelParser.parseExcelForCDs(file, false);
            }
        } catch (WrongXlsFileException e) {
            log.log(Level.SEVERE, "WrongXlsFileException: ", e);
            return new ResponseEntity<>("Failed. Wrong Xls file", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (WrongXlsxFileException e) {
            log.log(Level.SEVERE, "WrongXlsxFileException: ", e);
            return new ResponseEntity<>("Failed. Wrong Xlsx file", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (WrongParseCDTypeException e) {
            log.log(Level.SEVERE, "WrongParseCDTypeException: ", e);
            return new ResponseEntity<>("Failed. Wrong field in column \"type\"", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (WrongParseCDCountryException e) {
            log.log(Level.SEVERE, "WrongParseCDCountryException: ", e);
            return new ResponseEntity<>("Failed. Wrong field in column \"country\"", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (WrongParseCDBookletException e) {
            log.log(Level.SEVERE, "WrongParseCDBookletException: ", e);
            return new ResponseEntity<>("Failed. Wrong field in column \"booklet\"", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            log.log(Level.SEVERE, "IOException: ", e);
            return new ResponseEntity<>("Failed.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("File parsed without errors");

        if (rewriteDB){
            cdService.removeAllCDs();
            cdService.addCollectionCD(cds);

            log.info("Database is rewritten");
        }

        return new ResponseEntity<>("Done", HttpStatus.OK);
    }


}
