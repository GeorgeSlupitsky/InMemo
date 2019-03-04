package ua.slupitsky.inMemo.utils;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import ua.slupitsky.inMemo.models.mongo.CDBandMainMember;
import ua.slupitsky.inMemo.models.enums.Extentions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.List;

public class Utils {

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

    public static void setDownloadFileInfo(String name, HttpServletRequest request, HttpServletResponse response, Extentions extentions) {
        String browserName = getBrowserName(request);

        if(browserName.equals("Chrome")) {
            response.setHeader("Content-Disposition", "attachment; filename=" + name);
        } else {
            if (extentions.equals(Extentions.XLS)){
                response.setHeader("Content-Disposition","attachment; filename=" + name + ".xls");
            } else if (extentions.equals(Extentions.XLSX)) {
                response.setHeader("Content-Disposition", "attachment; filename=" + name + ".xlsx");
            } else if (extentions.equals(Extentions.PDF)){
                response.setHeader("Content-Disposition","attachment; filename=" + name + ".pdf");
            } else if (extentions.equals(Extentions.CSV)){
                response.setHeader("Content-Disposition","attachment; filename=" + name + ".csv");
            }
        }
    }

    private static String getBrowserName(HttpServletRequest request){
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        Browser browser = userAgent.getBrowser();

        return browser.getName();
    }

}
