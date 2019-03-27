package ua.slupitsky.inMemo.utils;

import org.apache.poi.ss.usermodel.*;
import ua.slupitsky.inMemo.models.constants.ExcelColumnCD;
import ua.slupitsky.inMemo.models.constants.ExcelColumnDrumStick;
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


        if (type.equals(ExportType.CD.getName())){
            buildExcelForCDs(model, workbook, request, response, isXLS);
        } else if (type.equals(ExportType.DRUM_STICK.getName())){
            buildExcelForDrumSticks(model, workbook, request, response, isXLS);
        }

    }

    private static void buildExcelForCDs(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response, boolean isXLS) {
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
        sheet.setColumnWidth(ExcelColumnCD.NUMBER, 1200);
        sheet.setColumnWidth(ExcelColumnCD.ORDER, 1200);
        sheet.setColumnWidth(ExcelColumnCD.ALBUM, 10000);
        sheet.setColumnWidth(ExcelColumnCD.YEAR, 3000);
        sheet.setColumnWidth(ExcelColumnCD.NOTE, 5500);
        sheet.setColumnWidth(ExcelColumnCD.MEMBERS, 15000);

        Row header = sheet.createRow(0);
        header.createCell(ExcelColumnCD.NUMBER).setCellValue("№");
        header.getCell(ExcelColumnCD.NUMBER).setCellStyle(style);
        header.createCell(ExcelColumnCD.ORDER).setCellValue("<>");
        header.getCell(ExcelColumnCD.ORDER).setCellStyle(style);
        header.createCell(ExcelColumnCD.BAND).setCellValue(resourceBundle.getString("cd.band"));
        header.getCell(ExcelColumnCD.BAND).setCellStyle(style);
        header.createCell(ExcelColumnCD.ALBUM).setCellValue(resourceBundle.getString("cd.album"));
        header.getCell(ExcelColumnCD.ALBUM).setCellStyle(style);
        header.createCell(ExcelColumnCD.YEAR).setCellValue(resourceBundle.getString("cd.year"));
        header.getCell(ExcelColumnCD.YEAR).setCellStyle(style);
        header.createCell(ExcelColumnCD.BOOKLET).setCellValue(resourceBundle.getString("cd.booklet"));
        header.getCell(ExcelColumnCD.BOOKLET).setCellStyle(style);
        header.createCell(ExcelColumnCD.COUNTRY).setCellValue(resourceBundle.getString("cd.country"));
        header.getCell(ExcelColumnCD.COUNTRY).setCellStyle(style);
        header.createCell(ExcelColumnCD.NOTE).setCellValue(resourceBundle.getString("cd.description"));
        header.getCell(ExcelColumnCD.NOTE).setCellStyle(style);
        header.createCell(ExcelColumnCD.MEMBERS).setCellValue(resourceBundle.getString("cd.members"));
        header.getCell(ExcelColumnCD.MEMBERS).setCellStyle(style);

        int rowCount = 1;

        for(CD cd : cds){
            Row cdRow =  sheet.createRow(rowCount++);
            cdRow.createCell(ExcelColumnCD.NUMBER).setCellValue(rowCount - 1);
            cdRow.createCell(ExcelColumnCD.ORDER).setCellValue(cd.getBand().getOrder());
            cdRow.createCell(ExcelColumnCD.BAND).setCellValue(cd.getBand().getName());
            cdRow.createCell(ExcelColumnCD.ALBUM).setCellValue(cd.getAlbum());
            cdRow.createCell(ExcelColumnCD.YEAR).setCellValue(cd.getYear());
            if (cd.getBooklet().equals(CDBooklet.WITH_OUT) || cd.getBooklet().equals(CDBooklet.DIGIPACK) || cd.getBooklet().equals(CDBooklet.BOX) || cd.getBooklet().equals(CDBooklet.BOOK)){
                cdRow.createCell(ExcelColumnCD.BOOKLET).setCellValue(resourceBundle.getString(cd.getBooklet().getName()));
            } else {
                cdRow.createCell(ExcelColumnCD.BOOKLET).setCellValue(cd.getBooklet().getQuantityOfPages() + " " + resourceBundle.getString(cd.getBooklet().getName()));
            }
            cdRow.createCell(ExcelColumnCD.COUNTRY).setCellValue(resourceBundle.getString(cd.getCountryEdition().getName()));
            cdRow.createCell(ExcelColumnCD.NOTE).setCellValue(resourceBundle.getString(cd.getCdType().getName()));

            List<CDBandMainMember> bandMainMembers = cd.getBand().getBandMembers();
            if (bandMainMembers == null){
                cdRow.createCell(ExcelColumnCD.MEMBERS).setCellValue("-");
            } else {
                StringBuilder members = Utils.iterateCDBandMembers(bandMainMembers);
                cdRow.createCell(ExcelColumnCD.MEMBERS).setCellValue(members.toString());
            }
        }
    }

    private static void buildExcelForDrumSticks(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response, boolean isXLS) {
        @SuppressWarnings("unchecked")
        List<DrumStick> drumSticks = (List<DrumStick>) model.get("drumSticks");

        Sheet sheet = workbook.createSheet(resourceBundle.getString("drumstick.sheetName"));
        sheet.setDefaultColumnWidth(30);
        sheet.setColumnWidth(ExcelColumnDrumStick.NUMBER, 1200);
        sheet.setColumnWidth(ExcelColumnDrumStick.DATE, 3000);

        Row header = sheet.createRow(0);
        header.createCell(ExcelColumnDrumStick.NUMBER).setCellValue("№");
        header.getCell(ExcelColumnDrumStick.NUMBER).setCellStyle(style);
        header.createCell(ExcelColumnDrumStick.BAND).setCellValue(resourceBundle.getString("drumstick.band"));
        header.getCell(ExcelColumnDrumStick.BAND).setCellStyle(style);
        header.createCell(ExcelColumnDrumStick.DRUMMER_NAME).setCellValue(resourceBundle.getString("drumstick.drummerName"));
        header.getCell(ExcelColumnDrumStick.DRUMMER_NAME).setCellStyle(style);
        header.createCell(ExcelColumnDrumStick.DATE).setCellValue(resourceBundle.getString("drumstick.date"));
        header.getCell(ExcelColumnDrumStick.DATE).setCellStyle(style);
        header.createCell(ExcelColumnDrumStick.CITY).setCellValue(resourceBundle.getString("drumstick.city"));
        header.getCell(ExcelColumnDrumStick.CITY).setCellStyle(style);
        header.createCell(ExcelColumnDrumStick.NOTE).setCellValue(resourceBundle.getString("drumstick.description"));
        header.getCell(ExcelColumnDrumStick.NOTE).setCellStyle(style);

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
