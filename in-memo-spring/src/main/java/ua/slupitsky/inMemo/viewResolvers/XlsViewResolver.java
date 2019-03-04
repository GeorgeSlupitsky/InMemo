package ua.slupitsky.inMemo.viewResolvers;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import ua.slupitsky.inMemo.views.XlsView;

import java.util.Locale;

public class XlsViewResolver implements ViewResolver {

    @Override
    public View resolveViewName(String s, Locale locale) throws Exception {
        return new XlsView();
    }
}
