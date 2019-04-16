package ua.slupitsky.inMemo.utils;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import ua.slupitsky.inMemo.models.mongo.CDBandMainMember;
import ua.slupitsky.inMemo.models.enums.Extentions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class Utils {

    public static Locale getCorrectLocale(Locale locale){
        String language = locale.getLanguage();
        Locale correctLocale;
        switch (language){
            case "uk":
                correctLocale = new Locale("ua","UA");
                break;
            case "es":
                correctLocale = new Locale("es", "ES");
                break;
            case "ja":
                correctLocale = new Locale("jp","JP");
                break;
            case "ru":
                correctLocale = new Locale("ru", "RU");
                break;
            default:
                correctLocale = new Locale("en", "EN");
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
                members.append(cdBandMainMember.getName()).append(", ");
            } else {
                members.append(cdBandMainMember.getName());
            }
        }
        return members;
    }

}
