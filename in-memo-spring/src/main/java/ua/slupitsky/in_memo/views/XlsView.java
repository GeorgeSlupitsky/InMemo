package ua.slupitsky.in_memo.views;

import org.apache.poi.ss.usermodel.*;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.document.AbstractXlsView;
import ua.slupitsky.in_memo.utils.ExcelGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Map;

public class XlsView extends AbstractXlsView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) {
        Locale locale = RequestContextUtils.getLocale(request);
        ExcelGenerator.buildExcelDocument(model, workbook, locale);
    }

}
