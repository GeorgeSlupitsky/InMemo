package ua.slupitsky.in_memo.views;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.web.servlet.view.AbstractView;
import ua.slupitsky.in_memo.constants.DefaultAppConstants;
import ua.slupitsky.in_memo.constants.PdfDocumentMargin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

public abstract class AbstractPdfView extends AbstractView {

    AbstractPdfView() {
        setContentType(DefaultAppConstants.PDF_CONTENT_TYPE);
    }

    @Override
    protected boolean generatesDownloadContent() {
        return true;
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ByteArrayOutputStream baos = createTemporaryOutputStream();

        Document document = new Document(PageSize.A2, PdfDocumentMargin.MARGIN_LEFT, PdfDocumentMargin.MARGIN_RIGHT,
                PdfDocumentMargin.MARGIN_TOP, PdfDocumentMargin.MARGIN_BOTTOM);
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        prepareWriter(writer);
        buildPdfMetadata();

        document.open();
        buildPdfDocument(model, document, writer, request, response);
        document.close();

        writeToResponse(response, baos);
    }

    private void prepareWriter(PdfWriter writer) {
        writer.setViewerPreferences(getViewerPreferences());
    }

    private int getViewerPreferences() {
        return PdfWriter.ALLOW_PRINTING | PdfWriter.PageLayoutSinglePage;
    }

    private void buildPdfMetadata() {
    }

    protected abstract void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
                                             HttpServletRequest request, HttpServletResponse response) throws DocumentException, IOException;
}
