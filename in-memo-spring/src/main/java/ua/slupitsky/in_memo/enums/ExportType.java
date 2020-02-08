package ua.slupitsky.in_memo.enums;

public enum ExportType {

    CD ("CD"),
    DRUM_STICK ("Drum Stick"),
    DRUM_STICK_LABELS("Drum Stick Labels");

    private final String name;

    ExportType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
