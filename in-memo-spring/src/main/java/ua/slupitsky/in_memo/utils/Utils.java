package ua.slupitsky.in_memo.utils;

import ua.slupitsky.in_memo.constants.DefaultAppConstants;
import ua.slupitsky.in_memo.entities.CDBandMainMember;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class Utils {

    private Utils(){
        throw new IllegalStateException(DefaultAppConstants.UTIL_CLASS);
    }

    public static Locale getCorrectLocale(Locale locale){
        String language = locale.getLanguage();
        Locale correctLocale;
        switch (language){
            case DefaultAppConstants.LOCALE_BROWSER_UKRAINE:
                correctLocale = new Locale(DefaultAppConstants.LOCALE_LANGUAGE_UKRAINE,DefaultAppConstants.LOCALE_COUNTRY_UKRAINE);
                break;
            case DefaultAppConstants.LOCALE_BROWSER_SPAIN:
                correctLocale = new Locale(DefaultAppConstants.LOCALE_LANGUAGE_SPAIN, DefaultAppConstants.LOCALE_COUNTRY_SPAIN);
                break;
            case DefaultAppConstants.LOCALE_BROWSER_JAPAN:
                correctLocale = new Locale(DefaultAppConstants.LOCALE_LANGUAGE_JAPAN,DefaultAppConstants.LOCALE_COUNTRY_JAPAN);
                break;
            case DefaultAppConstants.LOCALE_BROWSER_RUSSIA:
                correctLocale = new Locale(DefaultAppConstants.LOCALE_LANGUAGE_RUSSIA, DefaultAppConstants.LOCALE_COUNTRY_RUSSIA);
                break;
            default:
                correctLocale = new Locale(DefaultAppConstants.LOCALE_LANGUAGE_DEFAULT, DefaultAppConstants.LOCALE_COUNTRY_DEFAULT);
                break;
        }

        return correctLocale;
    }

    public static StringBuilder iterateCDBandMembers(List<CDBandMainMember> bandMainMembers) {
        StringBuilder members = new StringBuilder();
        Iterator<CDBandMainMember> iterator = bandMainMembers.iterator();
        while (iterator.hasNext()){
            CDBandMainMember cdBandMainMember = iterator.next();
            if (iterator.hasNext()){
                members.append(cdBandMainMember.getName()).append(DefaultAppConstants.COMA + DefaultAppConstants.SPACE);
            } else {
                members.append(cdBandMainMember.getName());
            }
        }
        return members;
    }

}
