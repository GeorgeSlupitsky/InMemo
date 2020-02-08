package ua.slupitsky.in_memo.enums;

import ua.slupitsky.in_memo.constants.ResourceBundleConstants;

public enum CDCountry {

    UKRAINE(ResourceBundleConstants.CD_COUNTRY_UKRAINE),
    UKRAINE_FIRM(ResourceBundleConstants.CD_COUNTRY_UKRAINE_FIRM),
    RUSSIA(ResourceBundleConstants.CD_COUNTRY_RUSSIA),
    RUSSIA_FIRM(ResourceBundleConstants.CD_COUNTRY_RUSSIA_FIRM),
    EU(ResourceBundleConstants.CD_COUNTRY_EU),
    USA(ResourceBundleConstants.CD_COUNTRY_USA),
    SWEDEN(ResourceBundleConstants.CD_COUNTRY_SWEDEN),
    AUSTRALIA(ResourceBundleConstants.CD_COUNTRY_AUSTRALIA),
    GERMANY(ResourceBundleConstants.CD_COUNTRY_GERMANY),
    JAPAN(ResourceBundleConstants.CD_COUNTRY_JAPAN),
    NETHERLANDS(ResourceBundleConstants.CD_COUNTRY_NETHERLANDS),
    BELARUS(ResourceBundleConstants.CD_COUNTRY_BELARUS);

    private String name;

    CDCountry(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
