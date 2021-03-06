package ua.slupitsky.in_memo.sorting;

import ua.slupitsky.in_memo.enums.CDBandOrder;
import ua.slupitsky.in_memo.enums.CDGroup;
import ua.slupitsky.in_memo.entities.CD;
import ua.slupitsky.in_memo.entities.CDBandMainMember;

import java.util.*;
import java.util.stream.Collectors;

public class SortingUtils {

    private SortingUtils(){
        throw new IllegalStateException("Utils class");
    }

    public static void setWeightForSorting(List<CD> cdList){
        Map<String, List<CD>> cdMap = new LinkedHashMap<>();

        List<CD> cdWithMainOrderForeign = cdList.stream()
                .filter(cd -> cd.getBand().getOrder().equals(CDBandOrder.MAIN) && cd.getCdGroup().equals(CDGroup.FOREIGN))
                .sorted(Comparator.comparing(cd -> cd.getBand().getName().toLowerCase()))
                .collect(Collectors.toList());

        List<CD> cdWithMainOrderDomestic = cdList.stream()
                .filter(cd -> cd.getBand().getOrder().equals(CDBandOrder.MAIN) && cd.getCdGroup().equals(CDGroup.DOMESTIC))
                .sorted(Comparator.comparing(cd -> cd.getBand().getName().toLowerCase()))
                .collect(Collectors.toList());

        List<CD> cdWithSecondaryOrderForeign = cdList.stream()
                .filter(cd -> cd.getBand().getOrder().equals(CDBandOrder.SECONDARY) && cd.getCdGroup().equals(CDGroup.FOREIGN))
                .sorted(Comparator.comparing(cd -> cd.getBand().getName().toLowerCase()))
                .collect(Collectors.toList());

        List<CD> cdWithSecondaryOrderDomestic = cdList.stream()
                .filter(cd -> cd.getBand().getOrder().equals(CDBandOrder.SECONDARY) && cd.getCdGroup().equals(CDGroup.DOMESTIC))
                .sorted(Comparator.comparing(cd -> cd.getBand().getName().toLowerCase()))
                .collect(Collectors.toList());

        fillMapForMainOrder(cdWithMainOrderForeign, cdMap);
        fillMapForMainOrder(cdWithMainOrderDomestic, cdMap);

        setWeightIndexToCDs(cdMap, cdWithSecondaryOrderForeign);
        setWeightIndexToCDs(cdMap, cdWithSecondaryOrderDomestic);

    }

    public static void clearWeight(List<CD> cdList){
        for (CD cd: cdList){
            cd.setIndexWeight(null);
        }
    }

    private static void setWeightIndexToCDs(Map<String, List<CD>> cdMap, List<CD> cdWithSecondaryOrder){
        int index = 1;
        for (Map.Entry<String, List<CD>> entry: cdMap.entrySet()){
            String key = entry.getKey();
            List<CD> cdBands = cdMap.get(key);
            for (CD cd: cdBands){
                cd.setIndexWeight(index);
                List <CDBandMainMember> cdBandMainMembers = cd.getBand().getBandMembers();
                if (cdBandMainMembers != null){
                    for (CDBandMainMember cdBandMainMember: cdBandMainMembers){
                        for (CD cdSecondary: cdWithSecondaryOrder){
                            if (cdSecondary.getBand().getBandMembers().contains(cdBandMainMember)){
                                cdSecondary.setIndexWeight(index);
                            } else {
                                //TODO add exception that this is impossible
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
