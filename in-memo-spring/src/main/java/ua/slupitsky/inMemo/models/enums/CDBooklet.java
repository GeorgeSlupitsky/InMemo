package ua.slupitsky.inMemo.models.enums;

public enum CDBooklet {
    WITH_OUT ("cd.booklet.withOut"),
    BOX("cd.booklet.box"),
    DIGIPACK("cd.booklet.digipack"),
    BOOK("cd.booklet.book"),
    PAGES_4( "cd.booklet.pages", 4),
    PAGES_6( "cd.booklet.pages", 6),
    PAGES_8( "cd.booklet.pages", 8),
    PAGES_10( "cd.booklet.pages", 10),
    PAGES_12( "cd.booklet.pages", 12),
    PAGES_14( "cd.booklet.pages", 14),
    PAGES_16( "cd.booklet.pages", 16),
    PAGES_18( "cd.booklet.pages", 18),
    PAGES_20( "cd.booklet.pages", 20),
    PAGES_22( "cd.booklet.pages", 22),
    PAGES_24( "cd.booklet.pages", 24),
    PAGES_26( "cd.booklet.pages", 26),
    PAGES_28( "cd.booklet.pages", 28),
    PAGES_30( "cd.booklet.pages", 30),
    PAGES_32( "cd.booklet.pages", 32),
    PAGES_34( "cd.booklet.pages", 34),
    PAGES_36( "cd.booklet.pages", 36),
    PAGES_38( "cd.booklet.pages", 38),
    PAGES_40( "cd.booklet.pages", 40);

    private String name;
    private int quantityOfPages;

    CDBooklet(String name) {
        this.name = name;
    }

    CDBooklet(String name, int quantityOfPages) {
        this.name = name;
        this.quantityOfPages = quantityOfPages;
    }

    public String getName() {
        return name;
    }

    public int getQuantityOfPages() {
        return quantityOfPages;
    }
}
