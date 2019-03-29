package ua.slupitsky.inMemo.models.enums;

public enum CDGroup {
    FOREIGN("cd.group.foreign"),
    DOMESTIC("cd.group.domestic");

    private String name;

    CDGroup(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
