package ua.slupitsky.in_memo.utils;

import org.apache.poi.ss.usermodel.*;
import ua.slupitsky.in_memo.constants.*;
import ua.slupitsky.in_memo.enums.CDBandOrder;
import ua.slupitsky.in_memo.entities.CDBandMainMember;
import ua.slupitsky.in_memo.enums.CDBooklet;
import ua.slupitsky.in_memo.enums.ExportType;
import ua.slupitsky.in_memo.entities.CD;
import ua.slupitsky.in_memo.entities.DrumStick;
import ua.slupitsky.in_memo.sorting.CDComparator;
import ua.slupitsky.in_memo.sorting.SortingUtils;

import java.time.format.DateTimeFormatter;
import java.util.*;

public class ExcelGenerator {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DefaultAppConstants.DATE_TIME_FORMATTER_PATTERN_DEFAULT);
    private static CellStyle style;
    private static ResourceBundle resourceBundle;

    private ExcelGenerator(){
        throw new IllegalStateException(DefaultAppConstants.UTIL_CLASS);
    }

    public static void buildExcelDocument(Map<String, Object> model, Workbook workbook, Locale locale){
        resourceBundle = ResourceBundle.getBundle(DefaultAppConstants.BASE_NAME, Utils.getCorrectLocale(locale));

        style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);

        String type = (String) model.get(DefaultAppConstants.TYPE_OF_EXPORT);

        if (type.equals(ExportType.CD.getName())){
            buildExcelForCDs(model, workbook);
        } else if (type.equals(ExportType.DRUM_STICK.getName())){
            buildExcelForDrumSticks(model, workbook);
        }
    }

    private static void buildExcelForCDs(Map<String, Object> model, Workbook workbook) {
        @SuppressWarnings("unchecked")
        List<CD> foreignCDs = (List<CD>) model.get(DefaultAppConstants.FOREIGN_CDS);

        @SuppressWarnings("unchecked")
        List<CD> domesticCDs = (List<CD>) model.get(DefaultAppConstants.DOMESTIC_CDS);

        if (!foreignCDs.isEmpty()){
            String foreign = resourceBundle.getString(ResourceBundleConstants.CD_GROUP_FOREIGN);
            createExcelSheetForCD(workbook, foreignCDs, foreign);
        }

        if (!domesticCDs.isEmpty()){
            String domestic = resourceBundle.getString(ResourceBundleConstants.CD_GROUP_DOMESTIC);
            createExcelSheetForCD(workbook, domesticCDs, domestic);
        }
    }

    private static void createExcelSheetForCD(Workbook workbook, List<CD> cds, String sheetName){
        Sheet sheet = workbook.createSheet(sheetName);
        sheet.setDefaultColumnWidth(ExcelColumnWidth.DEFAULT);
        sheet.setColumnWidth(ExcelColumnCD.NUMBER, ExcelColumnWidth.CD_NUMBER);
        sheet.setColumnWidth(ExcelColumnCD.ORDER, ExcelColumnWidth.CD_ORDER);
        sheet.setColumnWidth(ExcelColumnCD.ALBUM, ExcelColumnWidth.CD_ALBUM);
        sheet.setColumnWidth(ExcelColumnCD.YEAR, ExcelColumnWidth.CD_YEAR);
        sheet.setColumnWidth(ExcelColumnCD.NOTE, ExcelColumnWidth.CD_NOTE);
        sheet.setColumnWidth(ExcelColumnCD.MEMBERS, ExcelColumnWidth.CD_MEMBERS);
        sheet.setColumnWidth(ExcelColumnCD.DISCOGS_LINK, ExcelColumnWidth.CD_DISCOGS_LINK);
        sheet.setColumnWidth(ExcelColumnCD.AUTOGRAPH, ExcelColumnWidth.CD_AUTOGRAPH);

        Row header = sheet.createRow(0);
        header.createCell(ExcelColumnCD.NUMBER).setCellValue(DefaultAppConstants.NUMBER_SIGN);
        header.getCell(ExcelColumnCD.NUMBER).setCellStyle(style);
        header.createCell(ExcelColumnCD.ORDER).setCellValue(DefaultAppConstants.ORDER_SIGN);
        header.getCell(ExcelColumnCD.ORDER).setCellStyle(style);
        header.createCell(ExcelColumnCD.BAND).setCellValue(resourceBundle.getString(ResourceBundleConstants.CD_BAND));
        header.getCell(ExcelColumnCD.BAND).setCellStyle(style);
        header.createCell(ExcelColumnCD.ALBUM).setCellValue(resourceBundle.getString(ResourceBundleConstants.CD_ALBUM));
        header.getCell(ExcelColumnCD.ALBUM).setCellStyle(style);
        header.createCell(ExcelColumnCD.YEAR).setCellValue(resourceBundle.getString(ResourceBundleConstants.CD_YEAR));
        header.getCell(ExcelColumnCD.YEAR).setCellStyle(style);
        header.createCell(ExcelColumnCD.BOOKLET).setCellValue(resourceBundle.getString(ResourceBundleConstants.CD_BOOKLET));
        header.getCell(ExcelColumnCD.BOOKLET).setCellStyle(style);
        header.createCell(ExcelColumnCD.COUNTRY).setCellValue(resourceBundle.getString(ResourceBundleConstants.CD_COUNTRY));
        header.getCell(ExcelColumnCD.COUNTRY).setCellStyle(style);
        header.createCell(ExcelColumnCD.NOTE).setCellValue(resourceBundle.getString(ResourceBundleConstants.CD_DESCRIPTION));
        header.getCell(ExcelColumnCD.NOTE).setCellStyle(style);
        header.createCell(ExcelColumnCD.MEMBERS).setCellValue(resourceBundle.getString(ResourceBundleConstants.CD_MEMBERS));
        header.getCell(ExcelColumnCD.MEMBERS).setCellStyle(style);
        header.createCell(ExcelColumnCD.DISCOGS_LINK).setCellValue(resourceBundle.getString(ResourceBundleConstants.CD_DISCOGS_LINK));
        header.getCell(ExcelColumnCD.DISCOGS_LINK).setCellStyle(style);
        header.createCell(ExcelColumnCD.AUTOGRAPH).setCellValue(DefaultAppConstants.AT_AUTOGRAPH_SIGN);
        header.getCell(ExcelColumnCD.AUTOGRAPH).setCellStyle(style);

        int rowCount = 1;

        SortingUtils.clearWeight(cds);
        SortingUtils.setWeightForSorting(cds);
        cds.sort(new CDComparator());

        for(CD cd : cds){
            Row cdRow =  sheet.createRow(rowCount++);
            cdRow.createCell(ExcelColumnCD.NUMBER).setCellValue(rowCount - 1);
            CDBandOrder cdBandOrder = cd.getBand().getOrder();
            if (cdBandOrder.equals(CDBandOrder.MAIN)){
                cdRow.createCell(ExcelColumnCD.ORDER).setCellValue(CDIntBandOrder.MAIN);
            } else {
                cdRow.createCell(ExcelColumnCD.ORDER).setCellValue(CDIntBandOrder.SECONDARY);
            }
            cdRow.createCell(ExcelColumnCD.BAND).setCellValue(cd.getBand().getName());
            cdRow.createCell(ExcelColumnCD.ALBUM).setCellValue(cd.getAlbum());
            cdRow.createCell(ExcelColumnCD.YEAR).setCellValue(cd.getYear());
            if (cd.getBooklet().equals(CDBooklet.WITH_OUT) || cd.getBooklet().equals(CDBooklet.DIGIPACK) || cd.getBooklet().equals(CDBooklet.BOX)
                    || cd.getBooklet().equals(CDBooklet.BOOK) || cd.getBooklet().equals(CDBooklet.ECOPACK)){
                cdRow.createCell(ExcelColumnCD.BOOKLET).setCellValue(resourceBundle.getString(cd.getBooklet().getName()));
            } else {
                cdRow.createCell(ExcelColumnCD.BOOKLET).setCellValue(cd.getBooklet().getQuantityOfPages() + DefaultAppConstants.SPACE
                        + resourceBundle.getString(cd.getBooklet().getName()));
            }
            cdRow.createCell(ExcelColumnCD.COUNTRY).setCellValue(resourceBundle.getString(cd.getCountryEdition().getName()));
            cdRow.createCell(ExcelColumnCD.NOTE).setCellValue(resourceBundle.getString(cd.getCdType().getName()));

            if (cd.getAutograph() != null && cd.getAutograph()){
                cdRow.createCell(ExcelColumnCD.AUTOGRAPH).setCellValue(DefaultAppConstants.PLUS);
            } else {
                cdRow.createCell(ExcelColumnCD.AUTOGRAPH).setCellValue(DefaultAppConstants.HYPHEN);
            }

            List<CDBandMainMember> bandMainMembers = cd.getBand().getBandMembers();
            if (bandMainMembers == null){
                cdRow.createCell(ExcelColumnCD.MEMBERS).setCellValue(DefaultAppConstants.HYPHEN);
            } else {
                StringBuilder members = Utils.iterateCDBandMembers(bandMainMembers);
                cdRow.createCell(ExcelColumnCD.MEMBERS).setCellValue(members.toString());
            }

            if (cd.getDiscogsLink() == null){
                cdRow.createCell(ExcelColumnCD.DISCOGS_LINK).setCellValue(DefaultAppConstants.HYPHEN);
            } else {
                cdRow.createCell(ExcelColumnCD.DISCOGS_LINK).setCellValue(cd.getDiscogsLink());
            }

        }
    }

    private static void buildExcelForDrumSticks(Map<String, Object> model, Workbook workbook) {
        @SuppressWarnings("unchecked")
        List<DrumStick> drumSticks = (List<DrumStick>) model.get(DefaultAppConstants.DRUMSTICKS);

        Sheet sheet = workbook.createSheet(resourceBundle.getString(ResourceBundleConstants.DRUMSTICK_SHEET_NAME));
        sheet.setDefaultColumnWidth(ExcelColumnWidth.DEFAULT);
        sheet.setColumnWidth(ExcelColumnDrumStick.NUMBER, ExcelColumnWidth.DRUMSTICK_NUMBER);
        sheet.setColumnWidth(ExcelColumnDrumStick.DATE, ExcelColumnWidth.DRUMSTICK_DATE);
        sheet.setColumnWidth(ExcelColumnDrumStick.LINK_TO_PHOTO, ExcelColumnWidth.DRUMSTICK_LINK_TO_PHOTO);

        Row header = sheet.createRow(0);
        header.createCell(ExcelColumnDrumStick.NUMBER).setCellValue(DefaultAppConstants.NUMBER_SIGN);
        header.getCell(ExcelColumnDrumStick.NUMBER).setCellStyle(style);
        header.createCell(ExcelColumnDrumStick.BAND).setCellValue(resourceBundle.getString(ResourceBundleConstants.DRUMSTICK_BAND));
        header.getCell(ExcelColumnDrumStick.BAND).setCellStyle(style);
        header.createCell(ExcelColumnDrumStick.DRUMMER_NAME).setCellValue(resourceBundle.getString(ResourceBundleConstants.DRUMSTICK_DRUMMER_NAME));
        header.getCell(ExcelColumnDrumStick.DRUMMER_NAME).setCellStyle(style);
        header.createCell(ExcelColumnDrumStick.DATE).setCellValue(resourceBundle.getString(ResourceBundleConstants.DRUMSTICK_DATE));
        header.getCell(ExcelColumnDrumStick.DATE).setCellStyle(style);
        header.createCell(ExcelColumnDrumStick.CITY).setCellValue(resourceBundle.getString(ResourceBundleConstants.DRUMSTICK_CITY));
        header.getCell(ExcelColumnDrumStick.CITY).setCellStyle(style);
        header.createCell(ExcelColumnDrumStick.NOTE).setCellValue(resourceBundle.getString(ResourceBundleConstants.CD_DESCRIPTION));
        header.getCell(ExcelColumnDrumStick.NOTE).setCellStyle(style);
        header.createCell(ExcelColumnDrumStick.LINK_TO_PHOTO).setCellValue(resourceBundle.getString(ResourceBundleConstants.DRUMSTICK_LINK_TO_PHOTO));
        header.getCell(ExcelColumnDrumStick.LINK_TO_PHOTO).setCellStyle(style);

        int rowCount = 1;

        for(DrumStick drumStick : drumSticks){
            Row drumStickRow =  sheet.createRow(rowCount++);
            drumStickRow.createCell(ExcelColumnDrumStick.NUMBER).setCellValue(rowCount - 1);
            drumStickRow.createCell(ExcelColumnDrumStick.BAND).setCellValue(drumStick.getBand());
            drumStickRow.createCell(ExcelColumnDrumStick.DRUMMER_NAME).setCellValue(drumStick.getDrummerName());
            if (drumStick.getDate() != null){
                drumStickRow.createCell(ExcelColumnDrumStick.DATE).setCellValue(formatter.format(drumStick.getDate()));
            } else {
                drumStickRow.createCell(ExcelColumnDrumStick.DATE).setCellValue(DefaultAppConstants.EMPTY_VALUE);
            }
            drumStickRow.createCell(ExcelColumnDrumStick.CITY).setCellValue(resourceBundle.getString(drumStick.getCity().getName()));
            drumStickRow.createCell(ExcelColumnDrumStick.NOTE).setCellValue(resourceBundle.getString(drumStick.getDescription().getName()));
            if (drumStick.getLinkToPhoto() != null){
                drumStickRow.createCell(ExcelColumnDrumStick.LINK_TO_PHOTO).setCellValue(drumStick.getLinkToPhoto());
            } else {
                drumStickRow.createCell(ExcelColumnDrumStick.LINK_TO_PHOTO).setCellValue(DefaultAppConstants.HYPHEN);
            }
        }
    }

}
