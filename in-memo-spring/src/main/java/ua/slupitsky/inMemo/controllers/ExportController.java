package ua.slupitsky.inMemo.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.slupitsky.inMemo.models.enums.CDGroup;
import ua.slupitsky.inMemo.models.enums.ExportType;
import ua.slupitsky.inMemo.models.mongo.DrumStick;
import ua.slupitsky.inMemo.services.CDService;
import ua.slupitsky.inMemo.services.DrumStickService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/export")
@Api(value = "export", description = "Export files")
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
        model.addAttribute("foreignCDs", cdService.findByCDGroup(CDGroup.FOREIGN));
        model.addAttribute("domesticCDs", cdService.findByCDGroup(CDGroup.DOMESTIC));
        model.addAttribute("typeOfExport", ExportType.CD.getName());
        return "";
    }

    @ApiOperation(value = "Download Drum Stick files")
    @ApiParam(value = "ext", required = true, allowableValues = "pdf, xls, xlsx, csv")
    @GetMapping("/downloadDrumStick.{ext}")
    public String downloadDrumStick(Model model, @PathVariable String ext) {
        model.addAttribute("drumSticks", drumStickService.findAllDrumSticks());
        model.addAttribute("typeOfExport", ExportType.DRUM_STICK.getName());
        return "";
    }

    @ApiOperation(value = "Download Drum Stick Labels file")
    @ApiParam(value = "ext", required = true, allowableValues = "pdf")
    @PostMapping("/downloadDrumSticksLabels.{ext}")
    public String downloadDrumSticksLabels(Model model, @PathVariable String ext, @RequestBody List<Integer> idsToPrint) {
        List<DrumStick> drumSticks = drumStickService.findAllDrumSticks();
        model.addAttribute("drumSticks", drumSticks);
        model.addAttribute("typeOfExport", ExportType.DRUM_STICK_LABELS.getName());
        model.addAttribute("idsToPrint", idsToPrint);
        return "";
    }

}
