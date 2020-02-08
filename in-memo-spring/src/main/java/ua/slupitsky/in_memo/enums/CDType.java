package ua.slupitsky.in_memo.enums;

import ua.slupitsky.in_memo.constants.ResourceBundleConstants;

public enum CDType {

    NUMBER(ResourceBundleConstants.CD_TYPE_NUMBER),
    LIVE(ResourceBundleConstants.CD_TYPE_LIVE),
    NUMBER_AND_LIVE(ResourceBundleConstants.CD_TYPE_NUMBER_AND_LIVE),
    NOT_NUMBER(ResourceBundleConstants.CD_TYPE_NOT_NUMBER),
    SOUNDTRACK(ResourceBundleConstants.CD_TYPE_SOUNDTRACK),
    MINI_ALBUM(ResourceBundleConstants.CD_TYPE_MINI_ALBUM),
    SINGLE(ResourceBundleConstants.CD_TYPE_SINGLE),
    MAXI_SINGLE(ResourceBundleConstants.CD_TYPE_MAXI_SINGLE),
    COLLECTION(ResourceBundleConstants.CD_TYPE_COLLECTION),
    OFFICIAL_COLLECTION(ResourceBundleConstants.CD_TYPE_OFFICIAL_COLLECTION);

    private final String name;

    CDType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
