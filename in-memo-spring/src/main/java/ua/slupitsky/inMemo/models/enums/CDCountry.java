package ua.slupitsky.inMemo.models.enums;

public enum CDCountry {

    UKRAINE("cd.country.ukraine"),
    UKRAINE_FIRM("cd.country.ukraineFirm"),
    RUSSIA("cd.country.russia"),
    RUSSIA_FIRM("cd.country.russiaFirm"),
    EU("cd.country.eu"),
    USA("cd.country.usa"),
    SWEDEN("cd.country.sweden"),
    AUSTRALIA("cd.country.australia"),
    GERMANY("cd.country.germany"),
    JAPAN("cd.country.japan"),
    NETHERLAND("cd.country.netherlands");

    private String name;

    CDCountry(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
