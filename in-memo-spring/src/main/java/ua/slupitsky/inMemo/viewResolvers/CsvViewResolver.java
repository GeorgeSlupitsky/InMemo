package ua.slupitsky.inMemo.viewResolvers;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import ua.slupitsky.inMemo.views.CsvView;

import java.util.Locale;

public class CsvViewResolver implements ViewResolver {

    @Override
    public View resolveViewName(String s, Locale locale) throws Exception {
        return new CsvView();
    }
}
