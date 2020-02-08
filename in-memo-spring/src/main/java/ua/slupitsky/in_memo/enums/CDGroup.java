package ua.slupitsky.in_memo.enums;

import ua.slupitsky.in_memo.constants.ResourceBundleConstants;

public enum CDGroup {

    FOREIGN(ResourceBundleConstants.CD_GROUP_FOREIGN),
    DOMESTIC(ResourceBundleConstants.CD_GROUP_DOMESTIC);

    private String name;

    CDGroup(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
