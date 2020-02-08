package ua.slupitsky.in_memo.sorting;

import ua.slupitsky.in_memo.enums.CDGroup;
import ua.slupitsky.in_memo.enums.CDType;
import ua.slupitsky.in_memo.entities.CD;

import java.util.Comparator;

public class CDComparator implements Comparator<CD> {

    @Override
    public int compare(CD o1, CD o2) {
        if (o1.getCdGroup().compareTo(o2.getCdGroup()) == 0){
            if (o1.getBand().getName().compareTo(o2.getBand().getName()) == 0){
                Integer indexCDTypeO1 = getCompareIndexForCDType(o1);
                Integer indexCDTypeO2 = getCompareIndexForCDType(o2);
                if (!indexCDTypeO1.equals(indexCDTypeO2)){
                    return indexCDTypeO1.compareTo(indexCDTypeO2);
                } else {
                    return o1.getYear().compareTo(o2.getYear());
                }
            } else {
                if (o1.getIndexWeight().compareTo(o2.getIndexWeight()) == 0){
                    return o1.getBand().getOrder().compareTo(o2.getBand().getOrder());
                }
                return o1.getIndexWeight().compareTo(o2.getIndexWeight());
            }
        } else {
            Integer indexCDGroupO1 = getCompareIndexForCDGroup(o1);
            Integer indexCDGroupO2 = getCompareIndexForCDGroup(o2);
            return indexCDGroupO1.compareTo(indexCDGroupO2);
        }
    }

    private int getCompareIndexForCDType(CD cd) {
        if (cd.getCdType().equals(CDType.NUMBER) || cd.getCdType().equals(CDType.NOT_NUMBER)
            || cd.getCdType().equals(CDType.SINGLE) || cd.getCdType().equals(CDType.MAXI_SINGLE)
            || cd.getCdType().equals(CDType.NUMBER_AND_LIVE) || cd.getCdType().equals(CDType.MINI_ALBUM)){
            return 1;
        } else if (cd.getCdType().equals(CDType.LIVE)){
            return 2;
        } else if (cd.getCdType().equals(CDType.SOUNDTRACK)){
            return 3;
        } else if (cd.getCdType().equals(CDType.OFFICIAL_COLLECTION)){
            return 4;
        } else if (cd.getCdType().equals(CDType.COLLECTION)){
            return 5;
        }
        return 0;
    }

    private int getCompareIndexForCDGroup(CD cd) {
        if (cd.getCdGroup().equals(CDGroup.FOREIGN)){
            return 1;
        } else if (cd.getCdGroup().equals(CDGroup.DOMESTIC)){
            return 2;
        }
        return 0;
    }

}
