package ua.slupitsky.in_memo.enums;

import ua.slupitsky.in_memo.constants.ResourceBundleConstants;

public enum DrumStickType {

    ONE(ResourceBundleConstants.DRUMSTICK_TYPE_ONE),
    ONE_WITHOUT_SIGNATURE(ResourceBundleConstants.DRUMSTICK_TYPE_ONE_WITHOUT_SIGNATURE),
    ONE_NEW(ResourceBundleConstants.DRUMSTICK_TYPE_ONE_NEW),
    ONE_HANDMADE(ResourceBundleConstants.DRUMSTICK_TYPE_ONE_HANDMADE),
    ONE_HANDMADE_PERCUSSION(ResourceBundleConstants.DRUMSTICK_TYPE_ONE_HANDMADE_PERCUSSION),
    TWO(ResourceBundleConstants.DRUMSTICK_TYPE_TWO),
    TWO_WITHOUT_SIGNATURE(ResourceBundleConstants.DRUMSTICK_TYPE_TWO_WITHOUT_SIGNATURE),
    TWO_NEW(ResourceBundleConstants.DRUMSTICK_TYPE_TWO_NEW),
    TWO_HANDMADE(ResourceBundleConstants.DRUMSTICK_TYPE_TWO_HANDMADE),
    BRUSH(ResourceBundleConstants.DRUMSTICK_TYPE_BRUSH),
    SHAKER(ResourceBundleConstants.DRUMSTICK_TYPE_SHAKER),
    MARACA(ResourceBundleConstants.DRUMSTICK_TYPE_MARACA);

    private String name;

    DrumStickType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
