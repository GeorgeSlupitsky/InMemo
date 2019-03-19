package ua.slupitsky.inMemo.controllers;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.slupitsky.inMemo.models.dto.DrumStickForm;
import ua.slupitsky.inMemo.models.mongo.DrumStick;
import ua.slupitsky.inMemo.services.DrumStickService;
import ua.slupitsky.inMemo.utils.ExcelParser;
import ua.slupitsky.inMemo.validation.exceptions.WrongParseDrumStickCityException;
import ua.slupitsky.inMemo.validation.exceptions.WrongParseDrumStickTypeException;
import ua.slupitsky.inMemo.validation.exceptions.WrongXlsFileException;
import ua.slupitsky.inMemo.validation.exceptions.WrongXlsxFileException;

import javax.validation.Valid;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
@Api(value = "drumStick_collection", description = "Operations with Drum Stick in my collections")
public class DrumStickController {

    private static Logger log = Logger.getLogger(DrumStickController.class.getName());

    private final DrumStickService drumStickService;

    @Autowired
    public DrumStickController(DrumStickService drumStickService) {
        this.drumStickService = drumStickService;
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
        ResourceBundle resourceBundle = ResourceBundle.getBundle("InMemo", locale);
        return drumStickService.findAllDrumSticksWithBundle(resourceBundle);
    }

    @ApiOperation(value = "Search Drum Stick with an ID", response = DrumStick.class)
    @GetMapping(value = "/drumstick/{id}")
    public ResponseEntity<?> showDrumStick(@PathVariable Integer id){
        Optional<DrumStick> drumStick = drumStickService.findDrumStickById(id);
        return drumStick.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @ApiOperation(value = "Add Drum Stick")
    @PostMapping(value = "/drumstick")
    public ResponseEntity<String> saveDrumStick (@Valid @RequestBody DrumStick drumStick){
        drumStickService.addDrumStick(drumStick);
        log.info("DrumStick " + drumStick.getBand() + " - " + drumStick.getDrummerName() + " added");
        return new ResponseEntity<>("Drum Stick saved successfully", HttpStatus.OK);
    }

    @ApiOperation(value = "Update Drum Stick")
    @PutMapping(value = "/drumstick")
    public ResponseEntity<String> updateCD(@Valid @RequestBody DrumStick drumStick){
        drumStickService.updateDrumStick(drumStick.getId(), drumStick);
        log.info("DrumStick with id: " + drumStick.getId() + " : " + drumStick.getBand() + " - " + drumStick.getDrummerName() + " updated");
        return new ResponseEntity<>("Drum Stick updated successfully", HttpStatus.OK);
    }

    @ApiOperation(value = "Delete Drum Stick")
    @DeleteMapping(value = "/drumstick/{id}")
    public ResponseEntity<String> deleteCD (@PathVariable Integer id){
        drumStickService.removeDrumStick(id);
        log.info("DrumStick with id: " + id + " deleted");
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
        } catch (WrongXlsFileException e) {
            log.log(Level.SEVERE, "WrongXlsFileException: ", e);
            return new ResponseEntity<>("Failed. Wrong Xls file", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (WrongXlsxFileException e) {
            log.log(Level.SEVERE, "WrongXlsxFileException: ", e);
            return new ResponseEntity<>("Failed. Wrong Xlsx file", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (WrongParseDrumStickTypeException e) {
            log.log(Level.SEVERE, "WrongParseDrumStickTypeException: ", e);
            return new ResponseEntity<>("Failed. Wrong field in column \"description\"", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (WrongParseDrumStickCityException e) {
            log.log(Level.SEVERE, "WrongParseDrumStickCityException: ", e);
            return new ResponseEntity<>("Failed. Wrong field in column \"city\"", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            log.log(Level.SEVERE, "IOException: ", e);
            return new ResponseEntity<>("Failed.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("File parsed without errors");

        if (rewriteDB){
            drumStickService.removeAllDrumSticks();
            drumStickService.addCollectionDrumSticks(drumSticks);
            log.info("Database is rewritten");
        }

        return new ResponseEntity<>("Done", HttpStatus.OK);
    }
}
