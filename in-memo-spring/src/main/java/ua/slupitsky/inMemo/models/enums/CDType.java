package ua.slupitsky.inMemo.models.enums;

public enum CDType {

    NUMBER("cd.type.number"),
    LIVE("cd.type.live"),
    NUMBER_AND_LIVE("cd.type.numberAndLive"),
    NOT_NUMBER("cd.type.notNumber"),
    SOUNDTRACK("cd.type.soundtrack"),
    MINI_ALBUM("cd.type.miniAlbum"),
    SINGLE("cd.type.single"),
    MAXI_SINGLE("cd.type.maxiSingle"),
    COLLECTION("cd.type.collection"),
    OFFICIAL_COLLECTION("cd.type.officialCollection");

    private final String name;

    CDType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
