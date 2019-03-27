package ua.slupitsky.inMemo.sorting;

import ua.slupitsky.inMemo.models.enums.CDGroup;
import ua.slupitsky.inMemo.models.mongo.CD;
import ua.slupitsky.inMemo.models.mongo.CDBandMainMember;

import java.util.*;
import java.util.stream.Collectors;

public class SortingUtils {

    public static void setWeightForSorting(List<CD> cdList){
        Map<String, List<CD>> cdMap = new LinkedHashMap<>();

        List<CD> cdWithMainOrderForeign = cdList.stream()
                .filter(cd -> cd.getBand().getOrder() == 1 && cd.getCdGroup().equals(CDGroup.FOREIGN))
                .sorted(Comparator.comparing(cd -> cd.getBand().getName()))
                .collect(Collectors.toList());

        List<CD> cdWithMainOrderDomestic = cdList.stream()
                .filter(cd -> cd.getBand().getOrder() == 1 && cd.getCdGroup().equals(CDGroup.DOMESTIC))
                .sorted(Comparator.comparing(cd -> cd.getBand().getName()))
                .collect(Collectors.toList());

        List<CD> cdWithSecondaryOrderForeign = cdList.stream()
                .filter(cd -> cd.getBand().getOrder() == 2 && cd.getCdGroup().equals(CDGroup.FOREIGN))
                .sorted(Comparator.comparing(cd -> cd.getBand().getName()))
                .collect(Collectors.toList());

        List<CD> cdWithSecondaryOrderDomestic = cdList.stream()
                .filter(cd -> cd.getBand().getOrder() == 2 && cd.getCdGroup().equals(CDGroup.DOMESTIC))
                .sorted(Comparator.comparing(cd -> cd.getBand().getName()))
                .collect(Collectors.toList());

        fillMapForMainOrder(cdWithMainOrderForeign, cdMap);
        fillMapForMainOrder(cdWithMainOrderDomestic, cdMap);

        setWeightToCDs(cdMap, cdWithSecondaryOrderForeign);
        setWeightToCDs(cdMap, cdWithSecondaryOrderDomestic);

    }

    private static void setWeightToCDs(Map<String, List<CD>> cdMap, List<CD> cdWithSecondaryOrder){
        int index = 1;
        for (String key: cdMap.keySet()){
            List<CD> cdBands = cdMap.get(key);
            for (CD cd: cdBands){
                cd.setIndexWeight(index);
                List <CDBandMainMember> cdBandMainMembers = cd.getBand().getBandMembers();
                if (cdBandMainMembers != null){
                    for (CDBandMainMember cdBandMainMember: cdBandMainMembers){
                        for (CD cdSecondary: cdWithSecondaryOrder){
                            if (cdSecondary.getBand().getBandMembers().contains(cdBandMainMember)){
                                cdSecondary.setIndexWeight(index);
                            }
                        }
                    }
                }
            }
            index++;
        }
    }

    private static void fillMapForMainOrder(List<CD> cdList, Map<String, List<CD>> cdMap){
        List<CD> cds = new ArrayList<>();
        String bandName = null;
        for (CD cd: cdList){
            if (bandName == null){
                bandName = cd.getBand().getName();
            } else {
                if (!bandName.equals(cd.getBand().getName())){
                    cds = new ArrayList<>();
                    bandName = cd.getBand().getName();
                }
            }
            cds.add(cd);
            cdMap.put(cd.getBand().getName(), cds);
        }
    }

}
