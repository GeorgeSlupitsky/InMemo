package ua.slupitsky.inMemo.models.enums;

public enum CDBandOrder {

    MAIN ("cd.band.order.main"),
    SECONDARY("cd.band.order.secondary");

    private String name;

    CDBandOrder(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
