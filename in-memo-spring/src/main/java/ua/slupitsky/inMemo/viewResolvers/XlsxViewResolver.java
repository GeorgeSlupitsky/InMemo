package ua.slupitsky.inMemo.viewResolvers;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import ua.slupitsky.inMemo.views.XlsxView;

import java.util.Locale;

public class XlsxViewResolver implements ViewResolver {

    @Override
    public View resolveViewName(String s, Locale locale) throws Exception {
        return new XlsxView();
    }
}
