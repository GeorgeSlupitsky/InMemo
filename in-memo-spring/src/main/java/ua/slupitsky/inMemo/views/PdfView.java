package ua.slupitsky.inMemo.views;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.web.servlet.support.RequestContextUtils;
import ua.slupitsky.inMemo.models.mongo.CDBandMainMember;
import ua.slupitsky.inMemo.models.enums.CDBooklet;
import ua.slupitsky.inMemo.models.enums.ExportType;
import ua.slupitsky.inMemo.models.enums.Extentions;
import ua.slupitsky.inMemo.models.mongo.CD;
import ua.slupitsky.inMemo.models.mongo.DrumStick;
import ua.slupitsky.inMemo.utils.Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class PdfView extends AbstractPdfView {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final int NUM_COLUMNS_CD = 8;
    private static final int NUM_COLUMNS_DRUM_STICKS = 6;
    private static final int NUM_COLUMNS_DRUM_STICKS_LABELS = 6;

    private Font fontHeaderBold;
    private Font fontBold;
    private Font fontNormal;

    private PdfPCell cellCenterAlign;
    private PdfPCell cell;

    private ResourceBundle resourceBundle;

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Locale locale = RequestContextUtils.getLocale(request);
        Locale localeUA = new Locale("ua", "UA");
        Locale localeES = new Locale("es", "ES");
        Locale localeEN = new Locale("en", "US");
        Locale localeJP = new Locale("jp", "JP");
        resourceBundle = ResourceBundle.getBundle("InMemo", locale);

        initFonts();
        initCells();

        String type = (String) model.get("typeOfExport");

        String name = null;

        if (type.equals(ExportType.CD.getName())){
            name = resourceBundle.getString("cd.filename");
            buildPdfForCDs(model, document);
        } else if (type.equals(ExportType.DRUM_STICK.getName())){
            name = resourceBundle.getString("drumstick.filename");
            buildPdfForDrumSticks(model, document);
        } else if (type.equals(ExportType.DRUM_STICK_LABELS.getName())){
            name = resourceBundle.getString("drumstick.filename.labels");
            buildPdfForDrumSticksLabels(model, document);
        }

        Utils.setDownloadFileInfo(name, request, response, Extentions.PDF);
    }

    private void buildPdfForCDs (Map<String, Object> model, Document document) throws Exception{
        @SuppressWarnings("unchecked")
        List<CD> foreignCDs = (List<CD>) model.get("foreignCDs");

        @SuppressWarnings("unchecked")
        List<CD> domesticCDs = (List<CD>) model.get("domesticCDs");

        String tableName;

        if (!foreignCDs.isEmpty()){
            tableName = resourceBundle.getString("cd.group.foreign");
            createPdfTableForCDWithHeader(document, tableName, foreignCDs, false);
        }

        if (!domesticCDs.isEmpty()){
            tableName = resourceBundle.getString("cd.group.domestic");
            createPdfTableForCDWithHeader(document, tableName,domesticCDs, true);
        }
    }

    private void createPdfTableForCDWithHeader(Document document, String tableName, List<CD> cdList, boolean isDomestic) throws DocumentException {
        initParagraph(document, tableName, isDomestic);

        PdfPTable table = new PdfPTable(NUM_COLUMNS_CD);
        float[] columnWidths = new float[]{5f, 40f, 40f, 10f, 10f, 15f, 15f, 40f};
        table.setWidths(columnWidths);

        cellCenterAlign.setPhrase(new Phrase("№"));
        table.addCell(cellCenterAlign);

        cellCenterAlign.setPhrase(new Phrase(resourceBundle.getString("cd.band"), fontBold));
        table.addCell(cellCenterAlign);

        cellCenterAlign.setPhrase(new Phrase(resourceBundle.getString("cd.album"), fontBold));
        table.addCell(cellCenterAlign);

        cellCenterAlign.setPhrase(new Phrase(resourceBundle.getString("cd.year"), fontBold));
        table.addCell(cellCenterAlign);

        cellCenterAlign.setPhrase(new Phrase(resourceBundle.getString("cd.booklet"), fontBold));
        table.addCell(cellCenterAlign);

        cellCenterAlign.setPhrase(new Phrase(resourceBundle.getString("cd.country"), fontBold));
        table.addCell(cellCenterAlign);

        cellCenterAlign.setPhrase(new Phrase(resourceBundle.getString("cd.description"), fontBold));
        table.addCell(cellCenterAlign);

        cellCenterAlign.setPhrase(new Phrase(resourceBundle.getString("cd.members"), fontBold));
        table.addCell(cellCenterAlign);

        int rowCount = 1;

        for(CD cd : cdList){
            cellCenterAlign.setPhrase(new Phrase(String.valueOf(rowCount), fontNormal));
            table.addCell(cellCenterAlign);
            cell.setPhrase(new Phrase(cd.getBand().getName(), fontNormal));
            table.addCell(cell);
            cell.setPhrase(new Phrase(cd.getAlbum(), fontNormal));
            table.addCell(cell);
            cellCenterAlign.setPhrase(new Phrase(cd.getYear(), fontNormal));
            table.addCell(cellCenterAlign);
            if (cd.getBooklet().equals(CDBooklet.WITH_OUT) || cd.getBooklet().equals(CDBooklet.DIGIPACK) || cd.getBooklet().equals(CDBooklet.BOX) || cd.getBooklet().equals(CDBooklet.BOOK)){
                cellCenterAlign.setPhrase(new Phrase(resourceBundle.getString(cd.getBooklet().getName()), fontNormal));
                table.addCell(cellCenterAlign);
            } else {
                cellCenterAlign.setPhrase(new Phrase(cd.getBooklet().getQuantityOfPages() + " " + resourceBundle.getString(cd.getBooklet().getName()), fontNormal));
                table.addCell(cellCenterAlign);
            }
            cellCenterAlign.setPhrase(new Phrase(resourceBundle.getString(cd.getCountryEdition().getName()), fontNormal));
            table.addCell(cellCenterAlign);
            cell.setPhrase(new Phrase(resourceBundle.getString(cd.getCdType().getName()), fontNormal));
            table.addCell(cell);

            List<CDBandMainMember> bandMainMembers = cd.getBand().getBandMembers();
            if (bandMainMembers == null){
                cellCenterAlign.setPhrase(new Phrase("-", fontNormal));
                table.addCell(cellCenterAlign);
            } else {
                StringBuilder members = Utils.iterateCDBandMembers(bandMainMembers);
                cell.setPhrase(new Phrase(members.toString(), fontNormal));
                table.addCell(cell);
            }

            rowCount++;
        }


        document.add(table);
    }

    private void buildPdfForDrumSticks(Map<String, Object> model, Document document) throws Exception{
        @SuppressWarnings("unchecked")
        List<DrumStick> drumSticks = (List<DrumStick>) model.get("drumSticks");

        initParagraph(document, resourceBundle.getString("drumstick.sheetName"), false);

        PdfPTable table = new PdfPTable(NUM_COLUMNS_DRUM_STICKS);
        float[] columnWidths = new float[]{5f, 30f, 30f, 10f, 15f, 15f};
        table.setWidths(columnWidths);

        cellCenterAlign.setPhrase(new Phrase("№"));
        table.addCell(cellCenterAlign);

        cellCenterAlign.setPhrase(new Phrase(resourceBundle.getString("drumstick.band"), fontBold));
        table.addCell(cellCenterAlign);

        cellCenterAlign.setPhrase(new Phrase(resourceBundle.getString("drumstick.drummerName"), fontBold));
        table.addCell(cellCenterAlign);

        cellCenterAlign.setPhrase(new Phrase(resourceBundle.getString("drumstick.date"), fontBold));
        table.addCell(cellCenterAlign);

        cellCenterAlign.setPhrase(new Phrase(resourceBundle.getString("drumstick.city"), fontBold));
        table.addCell(cellCenterAlign);

        cellCenterAlign.setPhrase(new Phrase(resourceBundle.getString("drumstick.description"), fontBold));
        table.addCell(cellCenterAlign);

        int rowCount = 1;

        for(DrumStick drumStick : drumSticks){
            cellCenterAlign.setPhrase(new Phrase(String.valueOf(rowCount), fontNormal));
            table.addCell(cellCenterAlign);
            cell.setPhrase(new Phrase(drumStick.getBand(), fontNormal));
            table.addCell(cell);
            cell.setPhrase(new Phrase(drumStick.getDrummerName(), fontNormal));
            table.addCell(cell);
            cellCenterAlign.setPhrase(new Phrase(formatter.format(drumStick.getDate()), fontNormal));
            table.addCell(cellCenterAlign);
            cellCenterAlign.setPhrase(new Phrase(resourceBundle.getString(drumStick.getCity().getName()), fontNormal));
            table.addCell(cellCenterAlign);
            cell.setPhrase(new Phrase(resourceBundle.getString(drumStick.getDescription().getName()), fontNormal));
            table.addCell(cell);

            rowCount++;
        }


        document.add(table);
    }

    private void buildPdfForDrumSticksLabels(Map<String, Object> model, Document document) throws Exception{
        @SuppressWarnings("unchecked")
        List<DrumStick> drumSticks = (List<DrumStick>) model.get("drumSticks");

        @SuppressWarnings("unchecked")
        List<Integer> idsToPrint = (List<Integer>) model.get("idsToPrint");

        initParagraph(document, resourceBundle.getString("drumstick.sheetName"), false);

        PdfPTable table = new PdfPTable(NUM_COLUMNS_DRUM_STICKS_LABELS);
        float[] columnWidths = new float[]{15f, 15f, 15f, 15f, 15f, 15f};
        table.setWidths(columnWidths);

        int remainder = NUM_COLUMNS_DRUM_STICKS_LABELS - drumSticks.size() % NUM_COLUMNS_DRUM_STICKS_LABELS;

        for (DrumStick drumStick: drumSticks){
            for (Integer id: idsToPrint) {
                if (id.equals(drumStick.getId())) {
                    Chunk bandName = new Chunk(drumStick.getBand() + "\n", fontBold);
                    Chunk info = new Chunk(formatter.format(drumStick.getDate()) + "\n"
                            + resourceBundle.getString(drumStick.getCity().getName()) + "\n" + drumStick.getDrummerName(), fontNormal);
                    Phrase phrase = new Phrase();
                    phrase.add(bandName);
                    phrase.add(info);
                    cellCenterAlign.setPhrase(phrase);
                    cellCenterAlign.setPadding(5f);
                    table.addCell(cellCenterAlign);
                }
            }
        }

        for (int j = 0; j < remainder; j++){
            table.addCell(cell);
        }

        document.add(table);

    }

    private void initFonts() throws IOException, DocumentException {
        BaseFont baseFont = BaseFont.createFont("static/fonts/arial_universal.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

        fontHeaderBold = new Font(baseFont);
        fontHeaderBold.setStyle(Font.BOLD);
        fontHeaderBold.setSize(14);

        fontBold = new Font(baseFont);
        fontBold.setStyle(Font.BOLD);
        fontBold.setSize(10);

        fontNormal = new Font(baseFont);
        fontNormal.setSize(10);
    }

    private void initCells(){
        cellCenterAlign = new PdfPCell();
        cellCenterAlign.setBackgroundColor(BaseColor.WHITE);
        cellCenterAlign.setHorizontalAlignment(Element.ALIGN_CENTER);

        cell = new PdfPCell();
        cell.setBackgroundColor(BaseColor.WHITE);
        cell.setPadding(5);
    }

    private void initParagraph(Document document, String name, boolean isDomestic) throws DocumentException {
        Paragraph paragraph = new Paragraph(name, fontHeaderBold);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.setSpacingAfter(30f);
        if (isDomestic){
            paragraph.setSpacingBefore(20f);
        }
        document.add(paragraph);
    }
}
