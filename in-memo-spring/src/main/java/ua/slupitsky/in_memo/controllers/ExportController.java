package ua.slupitsky.in_memo.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.slupitsky.in_memo.constants.DefaultAppConstants;
import ua.slupitsky.in_memo.enums.CDGroup;
import ua.slupitsky.in_memo.enums.ExportType;
import ua.slupitsky.in_memo.entities.DrumStick;
import ua.slupitsky.in_memo.services.CDService;
import ua.slupitsky.in_memo.services.DrumStickService;

import java.util.List;

@Controller
@RequestMapping("/api/export")
@Api(value = "export")
public class ExportController {

    private final CDService cdService;
    private final DrumStickService drumStickService;

    @Autowired
    public ExportController(CDService cdService, DrumStickService drumStickService) {
        this.cdService = cdService;
        this.drumStickService = drumStickService;
    }

    @ApiOperation(value = "Download CD files")
    @ApiParam(value = "ext", required = true, allowableValues = "pdf, xls, xlsx, csv")
    @GetMapping("/downloadCD.{ext}")
    public String downloadCD(Model model, @PathVariable String ext) {
        model.addAttribute(DefaultAppConstants.FOREIGN_CDS, cdService.findByCDGroup(CDGroup.FOREIGN));
        model.addAttribute(DefaultAppConstants.DOMESTIC_CDS, cdService.findByCDGroup(CDGroup.DOMESTIC));
        model.addAttribute(DefaultAppConstants.TYPE_OF_EXPORT, ExportType.CD.getName());
        return "";
    }

    @ApiOperation(value = "Download Drum Stick files")
    @ApiParam(value = "ext", required = true, allowableValues = "pdf, xls, xlsx, csv")
    @GetMapping("/downloadDrumStick.{ext}")
    public String downloadDrumStick(Model model, @PathVariable String ext) {
        model.addAttribute(DefaultAppConstants.DRUMSTICKS, drumStickService.findAllDrumSticks());
        model.addAttribute(DefaultAppConstants.TYPE_OF_EXPORT, ExportType.DRUM_STICK.getName());
        return "";
    }

    @ApiOperation(value = "Download Drum Stick Labels file")
    @ApiParam(value = "ext", required = true, allowableValues = "pdf")
    @PostMapping("/downloadDrumSticksLabels.{ext}")
    public String downloadDrumSticksLabels(Model model, @PathVariable String ext, @RequestBody List<Integer> idsToPrint) {
        List<DrumStick> drumSticks = drumStickService.findAllDrumSticks();
        model.addAttribute(DefaultAppConstants.DRUMSTICKS, drumSticks);
        model.addAttribute(DefaultAppConstants.TYPE_OF_EXPORT, ExportType.DRUM_STICK_LABELS.getName());
        model.addAttribute(DefaultAppConstants.IDS_TO_PRINT, idsToPrint);
        return "";
    }

}
