package ua.slupitsky.in_memo.views;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.web.servlet.support.RequestContextUtils;
import ua.slupitsky.in_memo.constants.*;
import ua.slupitsky.in_memo.entities.CDBandMainMember;
import ua.slupitsky.in_memo.enums.CDBooklet;
import ua.slupitsky.in_memo.enums.ExportType;
import ua.slupitsky.in_memo.entities.CD;
import ua.slupitsky.in_memo.entities.DrumStick;
import ua.slupitsky.in_memo.utils.Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class PdfView extends AbstractPdfView {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DefaultAppConstants.DATE_TIME_FORMATTER_PATTERN_DEFAULT);

    private Font fontHeaderBold;
    private Font fontBold;
    private Font fontNormal;

    private PdfPCell cellCenterAlign;
    private PdfPCell cell;

    private ResourceBundle resourceBundle;

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws DocumentException, IOException {
        Locale locale = RequestContextUtils.getLocale(request);
        resourceBundle = ResourceBundle.getBundle(DefaultAppConstants.BASE_NAME, Utils.getCorrectLocale(locale));

        initFonts();
        initCells();

        String type = (String) model.get(DefaultAppConstants.TYPE_OF_EXPORT);

        if (type.equals(ExportType.CD.getName())){
            buildPdfForCDs(model, document);
        } else if (type.equals(ExportType.DRUM_STICK.getName())){
            buildPdfForDrumSticks(model, document);
        } else if (type.equals(ExportType.DRUM_STICK_LABELS.getName())){
            buildPdfForDrumSticksLabels(model, document);
        }

    }

    private void buildPdfForCDs (Map<String, Object> model, Document document) throws DocumentException {
        @SuppressWarnings("unchecked")
        List<CD> foreignCDs = (List<CD>) model.get(DefaultAppConstants.FOREIGN_CDS);

        @SuppressWarnings("unchecked")
        List<CD> domesticCDs = (List<CD>) model.get(DefaultAppConstants.DOMESTIC_CDS);

        String tableName;

        if (!foreignCDs.isEmpty()){
            tableName = resourceBundle.getString(ResourceBundleConstants.CD_GROUP_FOREIGN);
            createPdfTableForCDWithHeader(document, tableName, foreignCDs, false);
        }

        if (!domesticCDs.isEmpty()){
            tableName = resourceBundle.getString(ResourceBundleConstants.CD_GROUP_DOMESTIC);
            createPdfTableForCDWithHeader(document, tableName,domesticCDs, true);
        }

    }

    private void createPdfTableForCDWithHeader(Document document, String tableName, List<CD> cdList, boolean isDomestic) throws DocumentException {
        initParagraph(document, tableName, isDomestic);

        PdfPTable table = new PdfPTable(PdfColumn.NUM_COLUMNS_CD);
        float[] columnWidths = new float[]{PdfColumnWidth.COLUMN_CD_NUMBER,
                PdfColumnWidth.COLUMN_CD_BAND, PdfColumnWidth.COLUMN_CD_ALBUM,
                PdfColumnWidth.COLUMN_CD_YEAR, PdfColumnWidth.COLUMN_CD_BOOKLET,
                PdfColumnWidth.COLUMN_CD_COUNTRY, PdfColumnWidth.COLUMN_CD_DESCRIPTION,
                PdfColumnWidth.COLUMN_CD_MEMBERS, PdfColumnWidth.COLUMN_CD_AUTOGRAPH
        };
        table.setWidths(columnWidths);

        cellCenterAlign.setPhrase(new Phrase(DefaultAppConstants.NUMBER_SIGN));
        table.addCell(cellCenterAlign);

        cellCenterAlign.setPhrase(new Phrase(resourceBundle.getString(ResourceBundleConstants.CD_BAND), fontBold));
        table.addCell(cellCenterAlign);

        cellCenterAlign.setPhrase(new Phrase(resourceBundle.getString(ResourceBundleConstants.CD_ALBUM), fontBold));
        table.addCell(cellCenterAlign);

        cellCenterAlign.setPhrase(new Phrase(resourceBundle.getString(ResourceBundleConstants.CD_YEAR), fontBold));
        table.addCell(cellCenterAlign);

        cellCenterAlign.setPhrase(new Phrase(resourceBundle.getString(ResourceBundleConstants.CD_BOOKLET), fontBold));
        table.addCell(cellCenterAlign);

        cellCenterAlign.setPhrase(new Phrase(resourceBundle.getString(ResourceBundleConstants.CD_COUNTRY), fontBold));
        table.addCell(cellCenterAlign);

        cellCenterAlign.setPhrase(new Phrase(resourceBundle.getString(ResourceBundleConstants.CD_DESCRIPTION), fontBold));
        table.addCell(cellCenterAlign);

        cellCenterAlign.setPhrase(new Phrase(resourceBundle.getString(ResourceBundleConstants.CD_MEMBERS), fontBold));
        table.addCell(cellCenterAlign);

        cellCenterAlign.setPhrase(new Phrase(DefaultAppConstants.AT_AUTOGRAPH_SIGN, fontBold));
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
            if (cd.getBooklet().equals(CDBooklet.WITH_OUT) || cd.getBooklet().equals(CDBooklet.DIGIPACK) || cd.getBooklet().equals(CDBooklet.BOX)
                    || cd.getBooklet().equals(CDBooklet.BOOK) || cd.getBooklet().equals(CDBooklet.ECOPACK)){
                cellCenterAlign.setPhrase(new Phrase(resourceBundle.getString(cd.getBooklet().getName()), fontNormal));
                table.addCell(cellCenterAlign);
            } else {
                cellCenterAlign.setPhrase(new Phrase(cd.getBooklet().getQuantityOfPages() + DefaultAppConstants.SPACE + resourceBundle.getString(cd.getBooklet().getName()), fontNormal));
                table.addCell(cellCenterAlign);
            }
            cellCenterAlign.setPhrase(new Phrase(resourceBundle.getString(cd.getCountryEdition().getName()), fontNormal));
            table.addCell(cellCenterAlign);
            cell.setPhrase(new Phrase(resourceBundle.getString(cd.getCdType().getName()), fontNormal));
            table.addCell(cell);

            List<CDBandMainMember> bandMainMembers = cd.getBand().getBandMembers();
            if (bandMainMembers == null){
                cellCenterAlign.setPhrase(new Phrase(DefaultAppConstants.HYPHEN, fontNormal));
                table.addCell(cellCenterAlign);
            } else {
                StringBuilder members = Utils.iterateCDBandMembers(bandMainMembers);
                cell.setPhrase(new Phrase(members.toString(), fontNormal));
                table.addCell(cell);
            }

            if (cd.getAutograph() != null && cd.getAutograph()){
                cellCenterAlign.setPhrase(new Phrase((DefaultAppConstants.PLUS), fontNormal));
                table.addCell(cellCenterAlign);
            } else {
                cellCenterAlign.setPhrase(new Phrase((DefaultAppConstants.HYPHEN), fontNormal));
                table.addCell(cellCenterAlign);
            }

            rowCount++;
        }


        document.add(table);
    }

    private void buildPdfForDrumSticks(Map<String, Object> model, Document document) throws DocumentException {
        @SuppressWarnings("unchecked")
        List<DrumStick> drumSticks = (List<DrumStick>) model.get(DefaultAppConstants.DRUMSTICKS);

        initParagraph(document, resourceBundle.getString(ResourceBundleConstants.DRUMSTICK_SHEET_NAME), false);

        PdfPTable table = new PdfPTable(PdfColumn.NUM_COLUMNS_DRUM_STICKS);
        float[] columnWidths = new float[]{PdfColumnWidth.COLUMN_DRUMSTICK_NUMBER,
                PdfColumnWidth.COLUMN_DRUMSTICK_BAND, PdfColumnWidth.COLUMN_DRUMSTICK_DRUMMER_NAME,
                PdfColumnWidth.COLUMN_DRUMSTICK_DATE, PdfColumnWidth.COLUMN_DRUMSTICK_CITY,
                PdfColumnWidth.COLUMN_DRUMSTICK_DESCRIPTION, PdfColumnWidth.COLUMN_DRUMSTICK_PHOTO};
        table.setWidths(columnWidths);

        cellCenterAlign.setPhrase(new Phrase(DefaultAppConstants.NUMBER_SIGN));
        table.addCell(cellCenterAlign);

        cellCenterAlign.setPhrase(new Phrase(resourceBundle.getString(ResourceBundleConstants.DRUMSTICK_BAND), fontBold));
        table.addCell(cellCenterAlign);

        cellCenterAlign.setPhrase(new Phrase(resourceBundle.getString(ResourceBundleConstants.DRUMSTICK_DRUMMER_NAME), fontBold));
        table.addCell(cellCenterAlign);

        cellCenterAlign.setPhrase(new Phrase(resourceBundle.getString(ResourceBundleConstants.DRUMSTICK_DATE), fontBold));
        table.addCell(cellCenterAlign);

        cellCenterAlign.setPhrase(new Phrase(resourceBundle.getString(ResourceBundleConstants.DRUMSTICK_CITY), fontBold));
        table.addCell(cellCenterAlign);

        cellCenterAlign.setPhrase(new Phrase(resourceBundle.getString(ResourceBundleConstants.DRUMSTICK_DESCRIPTION), fontBold));
        table.addCell(cellCenterAlign);

        cellCenterAlign.setPhrase(new Phrase(resourceBundle.getString(ResourceBundleConstants.DRUMSTICK_PHOTO), fontBold));
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
            if (drumStick.getLinkToPhoto() != null){
                cellCenterAlign.setPhrase(new Phrase(DefaultAppConstants.PLUS, fontNormal));
            } else {
                cellCenterAlign.setPhrase(new Phrase(DefaultAppConstants.HYPHEN, fontNormal));
            }
            table.addCell(cellCenterAlign);
            rowCount++;
        }

        document.add(table);
    }

    private void buildPdfForDrumSticksLabels(Map<String, Object> model, Document document) throws DocumentException {
        @SuppressWarnings("unchecked")
        List<DrumStick> drumSticks = (List<DrumStick>) model.get(DefaultAppConstants.DRUMSTICKS);

        @SuppressWarnings("unchecked")
        List<Integer> idsToPrint = (List<Integer>) model.get(DefaultAppConstants.IDS_TO_PRINT);

        initParagraph(document, resourceBundle.getString(ResourceBundleConstants.DRUMSTICK_SHEET_NAME), false);

        PdfPTable table = new PdfPTable(PdfColumn.NUM_COLUMNS_DRUM_STICKS_LABELS);
        float[] columnWidths = new float[]{PdfColumnWidth.COLUMN_DRUMSTICK_LABEL, PdfColumnWidth.COLUMN_DRUMSTICK_LABEL,
                PdfColumnWidth.COLUMN_DRUMSTICK_LABEL, PdfColumnWidth.COLUMN_DRUMSTICK_LABEL,
                PdfColumnWidth.COLUMN_DRUMSTICK_LABEL, PdfColumnWidth.COLUMN_DRUMSTICK_LABEL};
        table.setWidths(columnWidths);

        int remainder = PdfColumn.NUM_COLUMNS_DRUM_STICKS_LABELS - idsToPrint.size() % PdfColumn.NUM_COLUMNS_DRUM_STICKS_LABELS;

        for (DrumStick drumStick: drumSticks){
            for (Integer id: idsToPrint) {
                if (id.equals(drumStick.getId())) {
                    Chunk bandName = new Chunk(drumStick.getBand() + DefaultAppConstants.NEW_ROW, fontBold);
                    Chunk info = new Chunk(formatter.format(drumStick.getDate()) + DefaultAppConstants.NEW_ROW
                            + resourceBundle.getString(drumStick.getCity().getName()) + DefaultAppConstants.NEW_ROW
                            + drumStick.getDrummerName(), fontNormal);
                    Phrase phrase = new Phrase();
                    phrase.add(bandName);
                    phrase.add(info);
                    cellCenterAlign.setPhrase(phrase);
                    cellCenterAlign.setPadding(PdfCellPadding.DRUMSTICK_LABEL_CELL_PADDING);
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
        BaseFont baseFont = BaseFont.createFont(DefaultAppConstants.BASE_FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

        fontHeaderBold = new Font(baseFont);
        fontHeaderBold.setStyle(Font.BOLD);
        fontHeaderBold.setSize(PdfFontSize.HEADER_SIZE);

        fontBold = new Font(baseFont);
        fontBold.setStyle(Font.BOLD);
        fontBold.setSize(PdfFontSize.DEFAULT_SIZE);

        fontNormal = new Font(baseFont);
        fontNormal.setSize(PdfFontSize.DEFAULT_SIZE);
    }

    private void initCells(){
        cellCenterAlign = new PdfPCell();
        cellCenterAlign.setBackgroundColor(BaseColor.WHITE);
        cellCenterAlign.setHorizontalAlignment(Element.ALIGN_CENTER);

        cell = new PdfPCell();
        cell.setBackgroundColor(BaseColor.WHITE);
        cell.setPadding(PdfCellPadding.DEFAULT_LABEL_CELL_PADDING);
    }

    private void initParagraph(Document document, String name, boolean isDomestic) throws DocumentException {
        Paragraph paragraph = new Paragraph(name, fontHeaderBold);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.setSpacingAfter(PdfParagraphSpacing.SPACING_AFTER);
        if (isDomestic){
            paragraph.setSpacingBefore(PdfParagraphSpacing.SPACING_BEFORE);
        }
        document.add(paragraph);
    }

}
