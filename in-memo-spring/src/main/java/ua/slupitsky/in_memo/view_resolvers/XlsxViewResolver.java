package ua.slupitsky.in_memo.view_resolvers;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import ua.slupitsky.in_memo.views.XlsxView;

import java.util.Locale;

public class XlsxViewResolver implements ViewResolver {

    @Override
    public View resolveViewName(String s, Locale locale) {
        return new XlsxView();
    }

}
