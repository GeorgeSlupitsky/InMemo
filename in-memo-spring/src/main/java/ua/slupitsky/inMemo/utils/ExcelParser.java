package ua.slupitsky.inMemo.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import ua.slupitsky.inMemo.models.constants.CDIntBandOrder;
import ua.slupitsky.inMemo.models.constants.ExcelColumnCD;
import ua.slupitsky.inMemo.models.constants.ExcelColumnDrumStick;
import ua.slupitsky.inMemo.models.mongo.CDBand;
import ua.slupitsky.inMemo.models.mongo.CDBandMainMember;
import ua.slupitsky.inMemo.models.enums.*;
import ua.slupitsky.inMemo.models.mongo.CD;
import ua.slupitsky.inMemo.models.mongo.DrumStick;
import ua.slupitsky.inMemo.sorting.CDComparator;
import ua.slupitsky.inMemo.sorting.SortingUtils;
import ua.slupitsky.inMemo.validation.exceptions.*;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ExcelParser {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private static int rowHeader = 0;

    private static Locale english = new Locale("en", "EN");
    private static Locale spanish = new Locale("es", "ES");
    private static Locale japanese = new Locale("jp", "JP");
    private static Locale russian = new Locale("ru", "RU");
    private static Locale ukrainian = new Locale("ua", "UA");
    private static ResourceBundle resourceBundleEnglish = ResourceBundle.getBundle("InMemo", english);
    private static ResourceBundle resourceBundleSpanish = ResourceBundle.getBundle("InMemo", spanish);
    private static ResourceBundle resourceBundleJapanese = ResourceBundle.getBundle("InMemo", japanese);
    private static ResourceBundle resourceBundleRussian = ResourceBundle.getBundle("InMemo", russian);
    private static ResourceBundle resourceBundleUkrainian = ResourceBundle.getBundle("InMemo", ukrainian);

    private static String foreignEnglishCDSheetName = resourceBundleEnglish.getString("cd.group.foreign");
    private static String domesticEnglishCDSheetName = resourceBundleEnglish.getString("cd.group.domestic");
    private static String englishDrumStickSheetName = resourceBundleEnglish.getString("drumstick.sheetName");

    private static String foreignSpanishCDSheetName = resourceBundleSpanish.getString("cd.group.foreign");
    private static String domesticSpanishCDSheetName = resourceBundleSpanish.getString("cd.group.domestic");
    private static String spanishDrumStickSheetName = resourceBundleSpanish.getString("drumstick.sheetName");

    private static String foreignJapaneseCDSheetName = resourceBundleJapanese.getString("cd.group.foreign");
    private static String domesticJapaneseCDSheetName = resourceBundleJapanese.getString("cd.group.domestic");
    private static String japaneseDrumStickSheetName = resourceBundleJapanese.getString("drumstick.sheetName");

    private static String foreignRussianCDSheetName = resourceBundleRussian.getString("cd.group.foreign");
    private static String domesticRussianCDSheetName = resourceBundleRussian.getString("cd.group.domestic");
    private static String russianDrumStickSheetName = resourceBundleRussian.getString("drumstick.sheetName");

    private static String foreignUkrainianCDSheetName = resourceBundleUkrainian.getString("cd.group.foreign");
    private static String domesticUkrainianCDSheetName = resourceBundleUkrainian.getString("cd.group.domestic");
    private static String ukrainianDrumStickSheetName = resourceBundleUkrainian.getString("drumstick.sheetName");


    public static List<CD> parseExcelForCDs(MultipartFile file, boolean isXlsx) throws WrongXlsFileException, WrongXlsxFileException, IOException, WrongParseCDTypeException, WrongParseCDCountryException, WrongParseCDBookletException {
        List<CD> cdsFromFile = new ArrayList<>();

        InputStream inputStream = file.getInputStream();

        Workbook wb = createWorkbook(inputStream, isXlsx);

        int numberOfSheets = wb.getNumberOfSheets();

        for (int i = 0; i < numberOfSheets; i++) {
            Sheet sheet = wb.getSheetAt(i);
            if (sheet.getSheetName().equals(foreignEnglishCDSheetName) || sheet.getSheetName().equals(domesticEnglishCDSheetName)){
                parseCDExcelFileWithLocal(sheet, i, resourceBundleEnglish, cdsFromFile);
            } else if (sheet.getSheetName().equals(foreignSpanishCDSheetName) || sheet.getSheetName().equals(domesticSpanishCDSheetName)){
                parseCDExcelFileWithLocal(sheet, i, resourceBundleSpanish, cdsFromFile);
            } else if (sheet.getSheetName().equals(foreignJapaneseCDSheetName) || sheet.getSheetName().equals(domesticJapaneseCDSheetName)){
                parseCDExcelFileWithLocal(sheet, i, resourceBundleJapanese, cdsFromFile);
            } else if (sheet.getSheetName().equals(foreignRussianCDSheetName) || sheet.getSheetName().equals(domesticRussianCDSheetName)){
                parseCDExcelFileWithLocal(sheet, i, resourceBundleRussian, cdsFromFile);
            } else if (sheet.getSheetName().equals(foreignUkrainianCDSheetName) || sheet.getSheetName().equals(domesticUkrainianCDSheetName)){
                parseCDExcelFileWithLocal(sheet, i, resourceBundleUkrainian, cdsFromFile);
            } else {
                if (isXlsx){
                    throw new WrongXlsxFileException("Xlsx file doesn't have right format");
                } else {
                    throw new WrongXlsFileException("Xls file doesn't have right format");
                }
            }

        }

        SortingUtils.setWeightForSorting(cdsFromFile);

        cdsFromFile.sort(new CDComparator());

        return cdsFromFile;
    }

    public static List<DrumStick> parseExcelForDrumSticks(MultipartFile file, boolean isXlsx) throws WrongXlsFileException, WrongXlsxFileException, WrongParseDrumStickCityException, WrongParseDrumStickTypeException, IOException {
        List<DrumStick> drumSticksFromFile = new ArrayList<>();

        InputStream inputStream = file.getInputStream();

        Workbook wb = createWorkbook(inputStream, isXlsx);

        Sheet sheet = wb.getSheetAt(0);
        if (sheet.getSheetName().equals(englishDrumStickSheetName)){
            parseDrumStickWithLocal(sheet, resourceBundleEnglish, drumSticksFromFile);
        } else if (sheet.getSheetName().equals(spanishDrumStickSheetName)){
            parseDrumStickWithLocal(sheet, resourceBundleSpanish, drumSticksFromFile);
        } else if (sheet.getSheetName().equals(japaneseDrumStickSheetName)){
            parseDrumStickWithLocal(sheet, resourceBundleJapanese, drumSticksFromFile);
        } else if (sheet.getSheetName().equals(russianDrumStickSheetName)){
            parseDrumStickWithLocal(sheet, resourceBundleRussian, drumSticksFromFile);
        } else if (sheet.getSheetName().equals(ukrainianDrumStickSheetName)){
            parseDrumStickWithLocal(sheet, resourceBundleUkrainian, drumSticksFromFile);
        } else {
            if (isXlsx){
                throw new WrongXlsxFileException("Xlsx file doesn't have right format");
            } else {
                throw new WrongXlsFileException("Xls file doesn't have right format");
            }
        }

        return drumSticksFromFile;
    }

    private static void parseCDExcelFileWithLocal(Sheet sheet, int i, ResourceBundle resourceBundle, List<CD> cdsFromFile) throws WrongParseCDBookletException, WrongParseCDCountryException, WrongParseCDTypeException {
        for (Row row : sheet) {

            if (row.getRowNum() != rowHeader) {

                CD cd = new CD();

                if (i == 0) {
                    cd.setCdGroup(CDGroup.FOREIGN);
                } else {
                    cd.setCdGroup(CDGroup.DOMESTIC);
                }

                String bandName = null;
                CDBandOrder cdBandOrder = null;

                for (Cell cell : row){
                    if (cell.getColumnIndex() != ExcelColumnCD.NUMBER) {
                        switch (cell.getColumnIndex()) {
                            case ExcelColumnCD.ORDER:
                                int intOrder = (int) cell.getNumericCellValue();
                                if (intOrder == CDIntBandOrder.MAIN){
                                    cdBandOrder = CDBandOrder.MAIN;
                                } else {
                                    cdBandOrder = CDBandOrder.SECONDARY;
                                }
                                break;

                            case ExcelColumnCD.BAND:
                                try {
                                    bandName = cell.getStringCellValue();
                                } catch (Exception e) {
                                    bandName = String.valueOf(cell.getNumericCellValue());
                                }
                                break;

                            case ExcelColumnCD.ALBUM:
                                try {
                                    cd.setAlbum(cell.getStringCellValue());
                                } catch (Exception e) {
                                    cd.setAlbum(String.valueOf(cell.getNumericCellValue()));
                                }
                                break;

                            case ExcelColumnCD.YEAR:
                                try {
                                    String year = String.valueOf(cell.getNumericCellValue()).replace(".0", "");
                                    cd.setYear(year);
                                } catch (Exception e) {
                                    cd.setYear(cell.getStringCellValue());
                                }
                                break;

                            case ExcelColumnCD.BOOKLET:
                                setCDBooklet(cd, cell.getStringCellValue(), resourceBundle);
                                break;

                            case ExcelColumnCD.COUNTRY:
                                setCDCountry(cd, getResourceBundleKey(cell.getStringCellValue(), resourceBundle));
                                break;

                            case ExcelColumnCD.NOTE:
                                setCDType(cd, getResourceBundleKey(cell.getStringCellValue(), resourceBundle));
                                break;

                            case ExcelColumnCD.MEMBERS:
                                String cellValue = cell.getStringCellValue();
                                CDBand cdBand;
                                if (cellValue.equals("-")){
                                    cdBand = new CDBand(bandName, cdBandOrder, null);
                                } else {
                                    String [] bandMembers = cell.getStringCellValue().split(", ");
                                    List<CDBandMainMember> cdBandMainMembers = new ArrayList<>();
                                    for (String bandMember: bandMembers){
                                        CDBandMainMember cdBandMainMember = new CDBandMainMember();
                                        cdBandMainMember.setName(bandMember);
                                        cdBandMainMembers.add(cdBandMainMember);
                                    }
                                    cdBand = new CDBand(bandName, cdBandOrder, cdBandMainMembers);
                                }
                                cd.setBand(cdBand);
                                break;
                            case ExcelColumnCD.DISCOGS_LINK:
                                cd.setDiscogsLink(cell.getStringCellValue());
                                break;
                            case ExcelColumnCD.AUTOGRAPH:
                                if (cell.getStringCellValue().equals("+")){
                                    cd.setAutograph(true);
                                } else {
                                    cd.setAutograph(false);
                                }
                        }
                    }
                }
                cdsFromFile.add(cd);
            }
        }
    }

    private static void parseDrumStickWithLocal(Sheet sheet, ResourceBundle resourceBundle, List<DrumStick> drumSticksFromFile) throws WrongParseDrumStickCityException, WrongParseDrumStickTypeException {
        for (Row row: sheet){

            if (row.getRowNum() != rowHeader) {

                DrumStick drumStick = new DrumStick();

                for (Cell cell: row){
                    if (cell.getColumnIndex() != ExcelColumnDrumStick.NUMBER){

                        switch (cell.getColumnIndex()){
                            case ExcelColumnDrumStick.BAND:
                                try {
                                    drumStick.setBand(cell.getStringCellValue());
                                } catch (Exception e) {
                                    drumStick.setBand(String.valueOf(cell.getNumericCellValue()));
                                }
                                break;
                            case ExcelColumnDrumStick.DRUMMER_NAME:
                                drumStick.setDrummerName(cell.getStringCellValue());
                                break;
                            case ExcelColumnDrumStick.DATE:
                                LocalDate date;
                                try {
                                    Date input = cell.getDateCellValue();
                                    date = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                                } catch (Exception e){
                                    String string = cell.getStringCellValue();
                                    date = LocalDate.parse(string, formatter);
                                }
                                drumStick.setDate(date);
                                break;
                            case ExcelColumnDrumStick.CITY:
                                setDrumStickCity(drumStick, getResourceBundleKey(cell.getStringCellValue(), resourceBundle));
                                break;
                            case ExcelColumnDrumStick.NOTE:
                                setDrumStickType(drumStick, getResourceBundleKey(cell.getStringCellValue(), resourceBundle));
                                break;
                            case ExcelColumnDrumStick.LINK_TO_PHOTO:
                                if (cell.getStringCellValue().equals("-")){
                                    drumStick.setLinkToPhoto(null);
                                } else {
                                    drumStick.setLinkToPhoto(cell.getStringCellValue());
                                }
                                break;
                        }
                    }
                }
                drumSticksFromFile.add(drumStick);
            }
        }
    }

    private static void setCDBooklet(CD cd, String booklet, ResourceBundle resourceBundle) throws WrongParseCDBookletException{
        String val = booklet.split(" ")[0];
        int quantity;
        try {
            quantity = Integer.parseInt(val);
        } catch (Exception e){
            quantity = 0;
        }

        String key = getResourceBundleKey(booklet, resourceBundle);
        CDBooklet cdBooklet = getEnum(CDBooklet.class, key, quantity);

        if (cdBooklet != null){
            cd.setBooklet(cdBooklet);
        } else {
            throw new WrongParseCDBookletException("Can't find right enum booklet for id = " + cd.getId() + " and key value = " + key);
        }
    }

    private static void setCDCountry(CD cd, String key) throws WrongParseCDCountryException{
        CDCountry country = getEnum(CDCountry.class, key);
        if (country != null){
            cd.setCountryEdition(country);
        } else {
            throw new WrongParseCDCountryException("Can't find right enum country for id = " + cd.getId() + " and key value = " + key);
        }
    }

    private static void setCDType(CD cd, String key) throws WrongParseCDTypeException{
        CDType cdType = getEnum(CDType.class, key);
        if (cdType != null){
            cd.setCdType(cdType);
        } else {
            throw new WrongParseCDTypeException("Can't find right enum type for id = " + cd.getId() + " and key value = " + key);
        }
    }

    private static void setDrumStickCity(DrumStick drumStick, String key) throws WrongParseDrumStickCityException{
        DrumStickCity drumStickCity = getEnum(DrumStickCity.class, key);
        if (drumStickCity != null){
            drumStick.setCity(drumStickCity);
        } else {
            throw new WrongParseDrumStickCityException("Can't find right enum city for id = " + drumStick.getId() + " and key value = " + key);
        }
    }

    private static void setDrumStickType(DrumStick drumStick, String key) throws WrongParseDrumStickTypeException{
        DrumStickType drumStickType = getEnum(DrumStickType.class, key);
        if (drumStickType != null){
            drumStick.setDescription(drumStickType);
        } else {
            throw new WrongParseDrumStickTypeException("Can't find right enum type for id = " + drumStick.getId() + " and key value = " + key);
        }
    }

    private static <E extends Enum <E>> E getEnum(Class<E> elemType, String key) {
        String name;
        for (E e : EnumSet.allOf(elemType)) {
            if (elemType.getName().contains("DrumStickType")){
                name = ((DrumStickType) e).getName();
                if (name.equalsIgnoreCase(key)){
                    return e;
                }
            } else if (elemType.getName().contains("DrumStickCity")){
                name = ((DrumStickCity) e).getName();
                if (name.equalsIgnoreCase(key)){
                    return e;
                }
            } else if (elemType.getName().contains("CDType")){
                name = ((CDType) e).getName();
                if (name.equalsIgnoreCase(key)){
                    return e;
                }
            } else if (elemType.getName().contains("CDCountry")){
                name = ((CDCountry) e).getName();
                if (name.equalsIgnoreCase(key)){
                    return e;
                }
            }
        }
        return null;
    }

    private static <E extends Enum <E>> E getEnum(Class<E> elemType, String key, int quantity){
        for (E e : EnumSet.allOf(elemType)) {
            if (elemType.getName().contains("CDBooklet")) {
                int i = ((CDBooklet) e).getQuantityOfPages();
                String name = ((CDBooklet) e).getName();
                if (i == 0) {
                    if (name.equalsIgnoreCase(key)) {
                        return e;
                    }
                } else {
                    if (i == quantity) {
                        return e;
                    }
                }
            }
        }
        return null;
    }

    private static String getResourceBundleKey(String name, ResourceBundle resourceBundle){
        for (String key: resourceBundle.keySet()){
            if (name.equalsIgnoreCase(resourceBundle.getString(key))){
                return key;
            }
        }
        return "";
    }

    private static Workbook createWorkbook(InputStream inputStream, boolean isXlsx) throws IOException {
        return (isXlsx) ? new XSSFWorkbook(inputStream) : new HSSFWorkbook(inputStream);
    }

}
