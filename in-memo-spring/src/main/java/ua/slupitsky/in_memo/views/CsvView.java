package ua.slupitsky.in_memo.views;

import org.springframework.web.servlet.support.RequestContextUtils;
import org.supercsv.io.CsvListWriter;
import org.supercsv.io.ICsvListWriter;
import org.supercsv.prefs.CsvPreference;
import ua.slupitsky.in_memo.constants.DefaultAppConstants;
import ua.slupitsky.in_memo.constants.ResourceBundleConstants;
import ua.slupitsky.in_memo.entities.CDBandMainMember;
import ua.slupitsky.in_memo.enums.ExportType;
import ua.slupitsky.in_memo.entities.CD;
import ua.slupitsky.in_memo.entities.DrumStick;
import ua.slupitsky.in_memo.utils.Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CsvView extends AbstractCsvView{

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DefaultAppConstants.DATE_TIME_FORMATTER_PATTERN_DEFAULT);

    private ResourceBundle resourceBundle;

    private ICsvListWriter csvWriter;

    @Override
    protected void buildCsvDocument(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Locale locale = RequestContextUtils.getLocale(request);

        resourceBundle = ResourceBundle.getBundle(DefaultAppConstants.BASE_NAME, locale);

        csvWriter = new CsvListWriter(response.getWriter(), CsvPreference.EXCEL_PREFERENCE);

        String type = (String) model.get(DefaultAppConstants.TYPE_OF_EXPORT);

        if (type != null){
            if (type.equals(ExportType.CD.getName())){
                buildCsvForCDs(model);
            } else if (type.equals(ExportType.DRUM_STICK.getName())){
                buildCsvForDrumSticks(model);
            }
        }

    }

    private void buildCsvForCDs (Map<String, Object> model) throws IOException {
        @SuppressWarnings("unchecked")
        List<CD> foreignCDs = (List<CD>) model.get(DefaultAppConstants.FOREIGN_CDS);

        @SuppressWarnings("unchecked")
        List<CD> domesticCDs = (List<CD>) model.get(DefaultAppConstants.DOMESTIC_CDS);

        String[] headers = {DefaultAppConstants.NUMBER_SIGN, resourceBundle.getString(ResourceBundleConstants.CD_BAND),
                resourceBundle.getString(ResourceBundleConstants.CD_ALBUM), resourceBundle.getString(ResourceBundleConstants.CD_YEAR),
                resourceBundle.getString(ResourceBundleConstants.CD_BOOKLET), resourceBundle.getString(ResourceBundleConstants.CD_COUNTRY),
                resourceBundle.getString(ResourceBundleConstants.CD_DESCRIPTION), resourceBundle.getString(ResourceBundleConstants.CD_MEMBERS)};

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
            cdString.append(cd.getId()).append(DefaultAppConstants.COMA);
            cdString.append(cd.getBand().getName()).append(DefaultAppConstants.COMA);
            cdString.append(cd.getAlbum()).append(DefaultAppConstants.COMA);
            cdString.append(cd.getYear()).append(DefaultAppConstants.COMA);
            cdString.append(resourceBundle.getString(cd.getBooklet().getName())).append(DefaultAppConstants.COMA);
            cdString.append(resourceBundle.getString(cd.getCountryEdition().getName())).append(DefaultAppConstants.COMA);
            cdString.append(resourceBundle.getString(cd.getCdType().getName())).append(DefaultAppConstants.COMA);

            List<CDBandMainMember> bandMainMembers = cd.getBand().getBandMembers();
            if (bandMainMembers == null){
                cdString.append(DefaultAppConstants.HYPHEN);
            } else {
                StringBuilder members = Utils.iterateCDBandMembers(bandMainMembers);
                cdString.append(members.toString());
            }

            list.add(cdString.toString());
        }

        return list;
    }

    private void buildCsvForDrumSticks (Map<String, Object> model) throws IOException {
        @SuppressWarnings("unchecked")
        List<DrumStick> drumSticks = (List<DrumStick>) model.get(DefaultAppConstants.DRUMSTICKS);

        String[] headers = {DefaultAppConstants.NUMBER_SIGN, resourceBundle.getString(ResourceBundleConstants.DRUMSTICK_BAND),
                resourceBundle.getString(ResourceBundleConstants.DRUMSTICK_DRUMMER_NAME), resourceBundle.getString(ResourceBundleConstants.DRUMSTICK_DATE),
                resourceBundle.getString(ResourceBundleConstants.DRUMSTICK_CITY), resourceBundle.getString(ResourceBundleConstants.DRUMSTICK_DESCRIPTION)};

        csvWriter.writeHeader(headers);

        List<String> list = new ArrayList<>();

        if (!drumSticks.isEmpty()){
            List<String> drumSticksStr = new ArrayList<>();

            for (DrumStick drumStick: drumSticks){
                String drumStickString = drumStick.getId() + DefaultAppConstants.COMA +
                        drumStick.getBand() + DefaultAppConstants.COMA +
                        drumStick.getDrummerName() + DefaultAppConstants.COMA +
                        formatter.format(drumStick.getDate()) + DefaultAppConstants.COMA +
                        resourceBundle.getString(drumStick.getCity().getName()) + DefaultAppConstants.COMA +
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
