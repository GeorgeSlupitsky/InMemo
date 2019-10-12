package ua.slupitsky.inMemo.models.enums;

public enum DrumStickCity {

    LVIV("drumstick.city.lviv"),
    LVIV_LEOPOLIS_JAZZ("drumstick.city.lviv.leopolisJazz"),
    KYIV("drumstick.city.kyiv"),
    KYIV_ATLAS_WEEKEND("drumstick.city.kyiv.atlasWeekend"),
    KHMELNITSKYI("drumstick.city.khmelnitskyi"),
    KHMELNITSKYI_ROCK_AND_BUKH("drumstick.city.khmelnitskyi.rockAndBuh"),
    KHMELNITSKYI_RESPUBLICA("drumstick.city.khmelnitskyi.respublica"),
    KAMIANETS_PODILSKYI("drumstick.city.kamianetsPodilskyi"),
    KAMIANETS_PODILSKYI_FORPOST("drumstick.city.kamianetsPodilskyi.forpost"),
    KAMIANETS_PODILSKYI_RESPUBLICA("drumstick.city.kamianetsPodilskyi.respublica"),
    KAMIANETS_PODILSKYI_RESPUBLICA_FAMILY("drumstick.city.kamianetsPodilskyi.respublicaFamily"),
    KRASYLIV("drumstick.city.krasyliv"),
    VOLOCHYSK("drumstick.city.volochysk"),
    CHORNYI_OSTRIV("drumstick.city.chornyi_ostriv"),
    VINNYTSYA("drumstick.city.vinnytsya");

    private String name;

    DrumStickCity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
