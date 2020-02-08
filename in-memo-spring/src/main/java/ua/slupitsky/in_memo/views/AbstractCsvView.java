package ua.slupitsky.in_memo.views;

import org.springframework.web.servlet.view.AbstractView;
import ua.slupitsky.in_memo.constants.DefaultAppConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public abstract class AbstractCsvView extends AbstractView {

    AbstractCsvView() {
        setContentType(DefaultAppConstants.CSV_CONTENT_TYPE);
    }

    @Override
    protected boolean generatesDownloadContent() {
        return true;
    }

    @Override
    protected final void renderMergedOutputModel(
            Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(getContentType());
        buildCsvDocument(model, request, response);
    }

    protected abstract void buildCsvDocument(
            Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws IOException;

}
