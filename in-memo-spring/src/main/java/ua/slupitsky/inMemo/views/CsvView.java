package ua.slupitsky.inMemo.views;

import org.springframework.web.servlet.support.RequestContextUtils;
import org.supercsv.io.CsvListWriter;
import org.supercsv.io.ICsvListWriter;
import org.supercsv.prefs.CsvPreference;
import ua.slupitsky.inMemo.models.mongo.CDBandMainMember;
import ua.slupitsky.inMemo.models.enums.ExportType;
import ua.slupitsky.inMemo.models.enums.Extentions;
import ua.slupitsky.inMemo.models.mongo.CD;
import ua.slupitsky.inMemo.models.mongo.DrumStick;
import ua.slupitsky.inMemo.utils.Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CsvView extends AbstractCsvView{

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private ResourceBundle resourceBundle;

    private ICsvListWriter csvWriter;

    @Override
    protected void buildCsvDocument(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Locale locale = RequestContextUtils.getLocale(request);
//        Locale localeUA = new Locale("ua", "UA");
//        Locale localeES = new Locale("es", "ES");
//        Locale localeEN = new Locale("en", "US");
        Locale localeJP = new Locale("jp", "JP");
        resourceBundle = ResourceBundle.getBundle("InMemo", localeJP);

        csvWriter = new CsvListWriter(response.getWriter(), CsvPreference.EXCEL_PREFERENCE);

        String type = (String) model.get("typeOfExport");

        if (type != null){
            if (type.equals(ExportType.CD.getName())){
                buildCsvForCDs(model);
            } else if (type.equals(ExportType.DRUM_STICK.getName())){
                buildCsvForDrumSticks(model);
            }
        }
    }

    private void buildCsvForCDs (Map<String, Object> model) throws Exception{
        @SuppressWarnings("unchecked")
        List<CD> foreignCDs = (List<CD>) model.get("foreignCDs");

        @SuppressWarnings("unchecked")
        List<CD> domesticCDs = (List<CD>) model.get("domesticCDs");

        String[] headers = {"№", resourceBundle.getString("cd.band"), resourceBundle.getString("cd.album"), resourceBundle.getString("cd.year"),
                resourceBundle.getString("cd.booklet"), resourceBundle.getString("cd.country"), resourceBundle.getString("cd.description"),
                resourceBundle.getString("cd.members")};

        csvWriter.writeHeader(headers);

        List<String> list = new ArrayList<>();

        if (!foreignCDs.isEmpty()){
            List<String> fCDs = iterateCDCollectionAndGetListOfStrings(foreignCDs);
            list.addAll(fCDs);
        }

        if (!domesticCDs.isEmpty()){
            List<String> dCDs = iterateCDCollectionAndGetListOfStrings(domesticCDs);
            list.addAll(dCDs);
        }

        if (!list.isEmpty()){
            for (String cd: list){
                csvWriter.write(cd, headers);
            }
        }

        csvWriter.close();
    }

    private List<String> iterateCDCollectionAndGetListOfStrings(List<CD> cds){
        List<String> list = new ArrayList<>();

        for (CD cd : cds) {
            StringBuilder cdString = new StringBuilder();
            cdString.append(cd.getId()).append(",");
            cdString.append(cd.getBand().getName()).append(",");
            cdString.append(cd.getAlbum()).append(",");
            cdString.append(cd.getYear()).append(",");
            cdString.append(resourceBundle.getString(cd.getBooklet().getName())).append(",");
            cdString.append(resourceBundle.getString(cd.getCountryEdition().getName())).append(",");
            cdString.append(resourceBundle.getString(cd.getCdType().getName())).append(",");

            List<CDBandMainMember> bandMainMembers = cd.getBand().getBandMembers();
            if (bandMainMembers == null){
                cdString.append("-");
            } else {
                StringBuilder members = Utils.iterateCDBandMembers(bandMainMembers);
                cdString.append(members.toString());
            }

            list.add(cdString.toString());
        }

        return list;
    }

    private void buildCsvForDrumSticks (Map<String, Object> model) throws Exception{
        @SuppressWarnings("unchecked")
        List<DrumStick> drumSticks = (List<DrumStick>) model.get("drumSticks");

        String[] headers = {"№", resourceBundle.getString("drumstick.band"), resourceBundle.getString("drumstick.drummerName"), resourceBundle.getString("drumstick.date"),
                resourceBundle.getString("drumstick.city"), resourceBundle.getString("drumstick.description")};

        csvWriter.writeHeader(headers);

        List<String> list = new ArrayList<>();

        if (!drumSticks.isEmpty()){
            List<String> drumSticksStr = new ArrayList<>();

            for (DrumStick drumStick: drumSticks){
                String drumStickString = drumStick.getId() + "," +
                        drumStick.getBand() + "," +
                        drumStick.getDrummerName() + "," +
                        formatter.format(drumStick.getDate()) + "," +
                        resourceBundle.getString(drumStick.getCity().getName()) + "," +
                        resourceBundle.getString(drumStick.getDescription().getName());
                drumSticksStr.add(drumStickString);
            }

            list.addAll(drumSticksStr);
        }

        if (!list.isEmpty()){
            for (String drumStick: list){
                csvWriter.write(drumStick, headers);
            }
        }

        csvWriter.close();
    }
}
