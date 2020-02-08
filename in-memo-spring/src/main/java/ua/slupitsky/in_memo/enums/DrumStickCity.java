package ua.slupitsky.in_memo.enums;

import ua.slupitsky.in_memo.constants.ResourceBundleConstants;

public enum DrumStickCity {

    LVIV(ResourceBundleConstants.DRUMSTICK_CITY_LVIV),
    LVIV_LEOPOLIS_JAZZ(ResourceBundleConstants.DRUMSTICK_CITY_LVIV_LEOPOLIS_JAZZ),
    KYIV(ResourceBundleConstants.DRUMSTICK_CITY_KYIV),
    KYIV_ATLAS_WEEKEND(ResourceBundleConstants.DRUMSTICK_CITY_KYIV_ATLAS_WEEKEND),
    KHMELNITSKYI(ResourceBundleConstants.DRUMSTICK_CITY_KHMELNITSKYI),
    KHMELNITSKYI_ROCK_AND_BUKH(ResourceBundleConstants.DRUMSTICK_CITY_KHMELNITSKYI_ROCK_AND_BUKH),
    KHMELNITSKYI_RESPUBLICA(ResourceBundleConstants.DRUMSTICK_CITY_KHMELNITSKYI_RESPUBLICA),
    KAMIANETS_PODILSKYI(ResourceBundleConstants.DRUMSTICK_CITY_KAMIANETS_PODILSKYI),
    KAMIANETS_PODILSKYI_FORPOST(ResourceBundleConstants.DRUMSTICK_CITY_KAMIANETS_PODILSKYI_FORPOST),
    KAMIANETS_PODILSKYI_RESPUBLICA(ResourceBundleConstants.DRUMSTICK_CITY_KAMIANETS_PODILSKYI_RESPUBLICA),
    KAMIANETS_PODILSKYI_RESPUBLICA_FAMILY(ResourceBundleConstants.DRUMSTICK_CITY_KAMIANETS_PODILSKYI_RESPUBLICA_FAMILY),
    KRASYLIV(ResourceBundleConstants.DRUMSTICK_CITY_KRASYLIV),
    VOLOCHYSK(ResourceBundleConstants.DRUMSTICK_CITY_VOLOCHYSK),
    CHORNYI_OSTRIV(ResourceBundleConstants.DRUMSTICK_CITY_CHORNYI_OSTRIV),
    VINNYTSYA(ResourceBundleConstants.DRUMSTICK_CITY_VINNYTSYA);

    private String name;

    DrumStickCity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
