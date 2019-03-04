package ua.slupitsky.inMemo.models.enums;

public enum DrumStickType {

    ONE("drumstick.type.one"),
    ONE_WITHOUT_SIGNATURE("drumstick.type.one.withoutSignature"),
    ONE_NEW("drumstick.type.one.newOne"),
    ONE_HANDMADE("drumstick.type.one.handmade"),
    ONE_HANDMADE_PERCUSSION("drumstick.type.one.handmade.percussion"),
    TWO("drumstick.type.two"),
    TWO_WITHOUT_SIGNATURE("drumstick.type.two.withoutSignature"),
    TWO_NEW("drumstick.type.two.newOne"),
    TWO_HANDMADE("drumstick.type.two.handmade"),
    BRUSH("drumstick.type.brush"),
    SHAKER("drumstick.type.shaker"),
    MARACA("drumstick.type.maraca");

    private String name;

    DrumStickType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
