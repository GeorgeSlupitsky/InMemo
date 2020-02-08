package ua.slupitsky.in_memo.controllers;

import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.slupitsky.in_memo.dto.CDForm;
import ua.slupitsky.in_memo.enums.CDGroup;
import ua.slupitsky.in_memo.entities.CD;
import ua.slupitsky.in_memo.services.CDService;
import ua.slupitsky.in_memo.services.implementation.NextSequenceService;
import ua.slupitsky.in_memo.utils.ExcelParser;
import ua.slupitsky.in_memo.utils.Utils;
import ua.slupitsky.in_memo.validation.exceptions.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

import static ua.slupitsky.in_memo.constants.DefaultAppConstants.BASE_NAME;

@RestController
@RequestMapping("/api")
@Api(value = "cd_collection")
public class CDController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CDController.class);

    private final CDService cdService;

    private final NextSequenceService nextSequenceService;

    @Autowired
    public CDController(CDService cdService, NextSequenceService nextSequenceService) {
        this.cdService = cdService;
        this.nextSequenceService = nextSequenceService;
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
        ResourceBundle resourceBundle = ResourceBundle.getBundle(BASE_NAME, Utils.getCorrectLocale(locale));
        return cdService.findAllCDs(resourceBundle);
    }

    @ApiOperation(value = "View a foreign list of CDs", response = Iterable.class)
    @GetMapping("/cdsForeign")
    public Iterable<CDForm> getForeignCDList(Locale locale){
        ResourceBundle resourceBundle = ResourceBundle.getBundle(BASE_NAME, Utils.getCorrectLocale(locale));
        return cdService.findByCDGroupWithResourceBundle(CDGroup.FOREIGN, resourceBundle);
    }

    @ApiOperation(value = "View a domestic list of CDs", response = Iterable.class)
    @GetMapping("/cdsDomestic")
    public Iterable<CDForm> getDomesticCDList(Locale locale){
        ResourceBundle resourceBundle = ResourceBundle.getBundle(BASE_NAME, Utils.getCorrectLocale(locale));
        return cdService.findByCDGroupWithResourceBundle(CDGroup.DOMESTIC, resourceBundle);
    }

    @ApiOperation(value = "Search CD with an ID", response = CD.class)
    @GetMapping("/cd/{id}")
    public ResponseEntity<CD> showCD(@PathVariable Integer id){
        Optional<CD> cd = cdService.findCDById(id);
        return cd.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @ApiOperation(value = "Add CD")
    @PostMapping(value = "/cd")
    public ResponseEntity<String> saveCD (@Valid @RequestBody CDForm cdForm){
        cdForm.setId(nextSequenceService.getNextSequence("cds"));
        cdService.addCD(cdForm);
        LOGGER.info("CD {} - {} added", cdForm.getBand().getName(), cdForm.getAlbum());
        return new ResponseEntity<>("CD saved successfully", HttpStatus.OK);
    }

    @ApiOperation(value = "Update CD")
    @PutMapping(value = "/cd")
    public ResponseEntity<String> updateCD(@Valid @RequestBody CDForm cdForm){
        cdService.updateCD(cdForm);
        LOGGER.info("CD with id: {} : {} - {} updated", cdForm.getId(), cdForm.getBand().getName(), cdForm.getAlbum());
        return new ResponseEntity<>("CD updated successfully", HttpStatus.OK);
    }

    @ApiOperation(value = "Delete CD")
    @DeleteMapping(value = "/cd/{id}")
    public ResponseEntity<String> deleteCD (@PathVariable Integer id){
        cdService.removeCD(id);
        LOGGER.info("CD with id: {} deleted", id);
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
            for (CD cd: cds){
                cd.setId(nextSequenceService.getNextSequence("cds"));
            }
        } catch (WrongXlsFileException e) {
            LOGGER.error("WrongXlsFileException: ", e);
            return new ResponseEntity<>("Failed. Wrong Xls file", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (WrongXlsxFileException e) {
            LOGGER.error("WrongXlsxFileException: ", e);
            return new ResponseEntity<>("Failed. Wrong Xlsx file", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (WrongParseCDTypeException e) {
            LOGGER.error("WrongParseCDTypeException: ", e);
            return new ResponseEntity<>("Failed. Wrong field in column \"type\"", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (WrongParseCDCountryException e) {
            LOGGER.error("WrongParseCDCountryException: ", e);
            return new ResponseEntity<>("Failed. Wrong field in column \"country\"", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (WrongParseCDBookletException e) {
            LOGGER.error("WrongParseCDBookletException: ", e);
            return new ResponseEntity<>("Failed. Wrong field in column \"booklet\"", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            LOGGER.error( "IOException: ", e);
            return new ResponseEntity<>("Failed.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        LOGGER.info("File parsed without errors");

        if (rewriteDB){
            cdService.removeAllCDs();
            cdService.addCollectionCD(cds);

            LOGGER.info("Database is rewritten");
        }

        return new ResponseEntity<>("Done", HttpStatus.OK);
    }


}
