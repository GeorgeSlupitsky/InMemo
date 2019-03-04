package ua.slupitsky.inMemo.views;

import org.apache.poi.ss.usermodel.*;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.document.AbstractXlsView;
import ua.slupitsky.inMemo.utils.ExcelGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Map;

public class XlsView extends AbstractXlsView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) {
        Locale locale = RequestContextUtils.getLocale(request);
//        Locale localeUA = new Locale("ua", "UA");
//        Locale localeES = new Locale("es", "ES");
//        Locale localeEN = new Locale("en", "US");
//        Locale localeJP = new Locale("jp", "JP");

        ExcelGenerator.buildExcelDocument(model, workbook, request, response, locale, true);
    }


}
