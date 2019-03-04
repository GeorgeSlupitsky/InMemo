package ua.slupitsky.inMemo.views;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.document.AbstractXlsxView;
import ua.slupitsky.inMemo.utils.ExcelGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Map;

public class XlsxView extends AbstractXlsxView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) {
        Locale locale = RequestContextUtils.getLocale(request);
//        Locale localeUA = new Locale("ua", "UA");
//        Locale localeES = new Locale("es", "ES");
//        Locale localeEN = new Locale("en", "US");
//        Locale localeJP = new Locale("jp", "JP");

        ExcelGenerator.buildExcelDocument(model, workbook, request, response, locale, false);
    }
}
