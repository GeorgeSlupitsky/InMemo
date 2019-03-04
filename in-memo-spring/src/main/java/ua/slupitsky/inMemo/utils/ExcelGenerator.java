package ua.slupitsky.inMemo.utils;

import org.apache.poi.ss.usermodel.*;
import ua.slupitsky.inMemo.models.mongo.CDBandMainMember;
import ua.slupitsky.inMemo.models.enums.CDBooklet;
import ua.slupitsky.inMemo.models.enums.ExportType;
import ua.slupitsky.inMemo.models.enums.Extentions;
import ua.slupitsky.inMemo.models.mongo.CD;
import ua.slupitsky.inMemo.models.mongo.DrumStick;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URISyntaxException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class ExcelGenerator {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static CellStyle style;

    private static ResourceBundle resourceBundle;

    public static void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response, Locale locale, boolean isXLS){
        resourceBundle = ResourceBundle.getBundle("InMemo", locale);

        style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);

        String type = (String) model.get("typeOfExport");

        try {
            if (type.equals(ExportType.CD.getName())){
                buildExcelForCDs(model, workbook, request, response, isXLS);
            } else if (type.equals(ExportType.DRUM_STICK.getName())){
                buildExcelForDrumSticks(model, workbook, request, response, isXLS);
            }
        } catch (URISyntaxException e){
            e.getStackTrace();
        }
    }

    private static void buildExcelForCDs(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response, boolean isXLS) throws URISyntaxException {
        @SuppressWarnings("unchecked")
        List<CD> foreignCDs = (List<CD>) model.get("foreignCDs");

        @SuppressWarnings("unchecked")
        List<CD> domesticCDs = (List<CD>) model.get("domesticCDs");

        if (!foreignCDs.isEmpty()){
            String foreign = resourceBundle.getString("cd.sheetName.foreign");
            createExcelSheetForCD(workbook, foreignCDs, foreign);
        }

        if (!domesticCDs.isEmpty()){
            String domestic = resourceBundle.getString("cd.sheetName.domestic");
            createExcelSheetForCD(workbook, domesticCDs, domestic);
        }

        String name = resourceBundle.getString("cd.filename");
        if (isXLS){
            Utils.setDownloadFileInfo(name, request, response, Extentions.XLS);
        } else {
            Utils.setDownloadFileInfo(name, request, response, Extentions.XLSX);
        }
    }

    private static void createExcelSheetForCD(Workbook workbook, List<CD> cds, String sheetName){
        Sheet sheet = workbook.createSheet(sheetName);
        sheet.setDefaultColumnWidth(30);
        sheet.setColumnWidth(0, 1200);
        sheet.setColumnWidth(2, 10000);
        sheet.setColumnWidth(3, 3000);
        sheet.setColumnWidth(6, 5500);
        sheet.setColumnWidth(7, 15000);

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("№");
        header.getCell(0).setCellStyle(style);
        header.createCell(1).setCellValue(resourceBundle.getString("cd.band"));
        header.getCell(1).setCellStyle(style);
        header.createCell(2).setCellValue(resourceBundle.getString("cd.album"));
        header.getCell(2).setCellStyle(style);
        header.createCell(3).setCellValue(resourceBundle.getString("cd.year"));
        header.getCell(3).setCellStyle(style);
        header.createCell(4).setCellValue(resourceBundle.getString("cd.booklet"));
        header.getCell(4).setCellStyle(style);
        header.createCell(5).setCellValue(resourceBundle.getString("cd.country"));
        header.getCell(5).setCellStyle(style);
        header.createCell(6).setCellValue(resourceBundle.getString("cd.description"));
        header.getCell(6).setCellStyle(style);
        header.createCell(7).setCellValue(resourceBundle.getString("cd.members"));
        header.getCell(7).setCellStyle(style);

        int rowCount = 1;

        for(CD cd : cds){
            Row cdRow =  sheet.createRow(rowCount++);
            cdRow.createCell(0).setCellValue(rowCount - 1);
            cdRow.createCell(1).setCellValue(cd.getBand().getName());
            cdRow.createCell(2).setCellValue(cd.getAlbum());
            cdRow.createCell(3).setCellValue(cd.getYear());
            if (cd.getBooklet().equals(CDBooklet.WITH_OUT) || cd.getBooklet().equals(CDBooklet.DIGIPACK) || cd.getBooklet().equals(CDBooklet.BOX) || cd.getBooklet().equals(CDBooklet.BOOK)){
                cdRow.createCell(4).setCellValue(resourceBundle.getString(cd.getBooklet().getName()));
            } else {
                cdRow.createCell(4).setCellValue(cd.getBooklet().getQuantityOfPages() + " " + resourceBundle.getString(cd.getBooklet().getName()));
            }
            cdRow.createCell(5).setCellValue(resourceBundle.getString(cd.getCountryEdition().getName()));
            cdRow.createCell(6).setCellValue(resourceBundle.getString(cd.getCdType().getName()));

            List<CDBandMainMember> bandMainMembers = cd.getBand().getBandMembers();
            if (bandMainMembers == null){
                cdRow.createCell(7).setCellValue("-");
            } else {
                StringBuilder members = Utils.iterateCDBandMembers(bandMainMembers);
                cdRow.createCell(7).setCellValue(members.toString());
            }
        }
    }

    private static void buildExcelForDrumSticks(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response, boolean isXLS) throws URISyntaxException {
        @SuppressWarnings("unchecked")
        List<DrumStick> drumSticks = (List<DrumStick>) model.get("drumSticks");

        Sheet sheet = workbook.createSheet(resourceBundle.getString("drumstick.sheetName"));
        sheet.setDefaultColumnWidth(30);
        sheet.setColumnWidth(0, 1200);
        sheet.setColumnWidth(3, 3000);

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("№");
        header.getCell(0).setCellStyle(style);
        header.createCell(1).setCellValue(resourceBundle.getString("drumstick.band"));
        header.getCell(1).setCellStyle(style);
        header.createCell(2).setCellValue(resourceBundle.getString("drumstick.drummerName"));
        header.getCell(2).setCellStyle(style);
        header.createCell(3).setCellValue(resourceBundle.getString("drumstick.date"));
        header.getCell(3).setCellStyle(style);
        header.createCell(4).setCellValue(resourceBundle.getString("drumstick.city"));
        header.getCell(4).setCellStyle(style);
        header.createCell(5).setCellValue(resourceBundle.getString("drumstick.description"));
        header.getCell(5).setCellStyle(style);

        int rowCount = 1;

        for(DrumStick drumStick : drumSticks){
            Row drumStickRow =  sheet.createRow(rowCount++);
            drumStickRow.createCell(0).setCellValue(rowCount - 1);
            drumStickRow.createCell(1).setCellValue(drumStick.getBand());
            drumStickRow.createCell(2).setCellValue(drumStick.getDrummerName());
            if (drumStick.getDate() != null){
                drumStickRow.createCell(3).setCellValue(formatter.format(drumStick.getDate()));
            } else {
                drumStickRow.createCell(3).setCellValue("");
            }
            drumStickRow.createCell(4).setCellValue(resourceBundle.getString(drumStick.getCity().getName()));
            drumStickRow.createCell(5).setCellValue(resourceBundle.getString(drumStick.getDescription().getName()));
        }

        String name = resourceBundle.getString("drumstick.filename");
        if (isXLS){
            Utils.setDownloadFileInfo(name, request, response, Extentions.XLS);
        } else {
            Utils.setDownloadFileInfo(name, request, response, Extentions.XLSX);
        }
    }

}
