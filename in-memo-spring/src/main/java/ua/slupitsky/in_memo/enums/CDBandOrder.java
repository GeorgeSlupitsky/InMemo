package ua.slupitsky.in_memo.enums;

import ua.slupitsky.in_memo.constants.ResourceBundleConstants;

public enum CDBandOrder {

    MAIN (ResourceBundleConstants.CD_BAND_ORDER_MAIN),
    SECONDARY(ResourceBundleConstants.CD_BAND_ORDER_SECONDARY);

    private String name;

    CDBandOrder(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
