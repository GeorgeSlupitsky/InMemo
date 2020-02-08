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
import ua.slupitsky.in_memo.dto.DrumStickForm;
import ua.slupitsky.in_memo.entities.DrumStick;
import ua.slupitsky.in_memo.services.DrumStickService;
import ua.slupitsky.in_memo.services.implementation.NextSequenceService;
import ua.slupitsky.in_memo.utils.ExcelParser;
import ua.slupitsky.in_memo.utils.Utils;
import ua.slupitsky.in_memo.validation.exceptions.WrongParseDrumStickCityException;
import ua.slupitsky.in_memo.validation.exceptions.WrongParseDrumStickTypeException;
import ua.slupitsky.in_memo.validation.exceptions.WrongXlsFileException;
import ua.slupitsky.in_memo.validation.exceptions.WrongXlsxFileException;

import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

import static ua.slupitsky.in_memo.constants.DefaultAppConstants.BASE_NAME;

@RestController
@RequestMapping("/api")
@Api(value = "drumStick_collection")
public class DrumStickController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DrumStickController.class);

    private final DrumStickService drumStickService;

    private final NextSequenceService nextSequenceService;

    @Autowired
    public DrumStickController(DrumStickService drumStickService, NextSequenceService nextSequenceService) {
        this.drumStickService = drumStickService;
        this.nextSequenceService = nextSequenceService;
    }

    @ApiOperation(value = "View a list of Drum Sticks", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to views the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(value = "/drumsticks")
    public Iterable<DrumStickForm> getDrumStickList(Locale locale){
        ResourceBundle resourceBundle = ResourceBundle.getBundle(BASE_NAME, Utils.getCorrectLocale(locale));
        return drumStickService.findAllDrumSticksWithResourceBundle(resourceBundle);
    }

    @ApiOperation(value = "Search Drum Stick with an ID", response = DrumStick.class)
    @GetMapping(value = "/drumstick/{id}")
    public ResponseEntity<DrumStick> showDrumStick(@PathVariable Integer id){
        Optional<DrumStick> drumStick = drumStickService.findDrumStickById(id);
        return drumStick.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @ApiOperation(value = "Add Drum Stick")
    @PostMapping(value = "/drumstick")
    public ResponseEntity<String> saveDrumStick (@Valid @RequestBody DrumStickForm drumStickForm){
        drumStickForm.setId(nextSequenceService.getNextSequence("drumsticks"));
        drumStickService.addDrumStick(drumStickForm);
        LOGGER.info("DrumStick {} - {} added", drumStickForm.getBand(), drumStickForm.getDrummerName());
        return new ResponseEntity<>("Drum Stick saved successfully", HttpStatus.OK);
    }

    @ApiOperation(value = "Update Drum Stick")
    @PutMapping(value = "/drumstick")
    public ResponseEntity<String> updateDrumStick(@Valid @RequestBody DrumStickForm drumStickForm){
        drumStickService.updateDrumStick(drumStickForm);
        LOGGER.info("DrumStick with id: {} : {} - {} updated", drumStickForm.getId(), drumStickForm.getBand(), drumStickForm.getDrummerName());
        return new ResponseEntity<>("Drum Stick updated successfully", HttpStatus.OK);
    }

    @ApiOperation(value = "Delete Drum Stick")
    @DeleteMapping(value = "/drumstick/{id}")
    public ResponseEntity<String> deleteCD (@PathVariable Integer id){
        drumStickService.removeDrumStick(id);
        LOGGER.info("DrumStick with id: {} deleted", id);
        return new ResponseEntity<>("Drum Stick deleted successfully", HttpStatus.OK);
    }


    @ApiOperation(value = "Make a POST request to upload the file",
            produces = "application/json", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The POST call is Successful"),
            @ApiResponse(code = 500, message = "The POST call is Failed"),
            @ApiResponse(code = 404, message = "The API could not be found")
    })
    @PostMapping("/drumstick/upload")
    public ResponseEntity<String> uploadExcelDrumStick(
            @ApiParam(name = "file", value = "Select the file to Upload", required = true)
            @RequestPart("file") MultipartFile file,
            @ApiParam(name = "rewriteDB", value = "Checked if you want to Rewrite DB from Xls or Xlsx file", required = true)
            @RequestParam boolean rewriteDB) {
        List<DrumStick> drumSticks;

        try {
            if (Objects.requireNonNull(file.getOriginalFilename()).contains(".xlsx")){
                drumSticks = ExcelParser.parseExcelForDrumSticks(file, true);
            } else {
                drumSticks = ExcelParser.parseExcelForDrumSticks(file, false);
            }
            for (DrumStick drumStick: drumSticks){
                drumStick.setId(nextSequenceService.getNextSequence("drumsticks"));
            }
        } catch (WrongXlsFileException e) {
            LOGGER.error("WrongXlsFileException: ", e);
            return new ResponseEntity<>("Failed. Wrong Xls file", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (WrongXlsxFileException e) {
            LOGGER.error("WrongXlsxFileException: ", e);
            return new ResponseEntity<>("Failed. Wrong Xlsx file", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (WrongParseDrumStickTypeException e) {
            LOGGER.error("WrongParseDrumStickTypeException: ", e);
            return new ResponseEntity<>("Failed. Wrong field in column \"description\"", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (WrongParseDrumStickCityException e) {
            LOGGER.error("WrongParseDrumStickCityException: ", e);
            return new ResponseEntity<>("Failed. Wrong field in column \"city\"", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            LOGGER.error("IOException: ", e);
            return new ResponseEntity<>("Failed.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        LOGGER.info("File parsed without errors");

        if (rewriteDB){
            drumStickService.removeAllDrumSticks();
            drumStickService.addCollectionDrumSticks(drumSticks);
            LOGGER.info("Database is rewritten");
        }

        return new ResponseEntity<>("Done", HttpStatus.OK);
    }

    @PostMapping("drumstick/uploadPhoto")
    public ResponseEntity<String> uploadPhoto(
            @ApiParam(name = "photo", value = "Select the photo to Upload", required = true)
            @RequestPart("photo") MultipartFile photo,
            @ApiParam(name = "drumstickId", value = "Id of Drumstick", required = true)
            @RequestParam String drumstickId){
            try {
                drumStickService.uploadPhoto(photo, Integer.parseInt(drumstickId));
            } catch (IllegalStateException | IOException e) {
                LOGGER.error("IOException: ", e);
                return new ResponseEntity<>("Failed.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        return new ResponseEntity<>("Done", HttpStatus.OK);
    }
}
