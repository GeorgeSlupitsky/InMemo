package ua.slupitsky.in_memo.enums;

import ua.slupitsky.in_memo.constants.ResourceBundleConstants;

public enum CDBooklet {

    WITH_OUT (ResourceBundleConstants.CD_BOOKLET_WITH_OUT),
    BOX(ResourceBundleConstants.CD_BOOKLET_BOX),
    DIGIPACK(ResourceBundleConstants.CD_BOOKLET_DIGIPACK),
    BOOK(ResourceBundleConstants.CD_BOOKLET_BOOK),
    ECOPACK(ResourceBundleConstants.CD_BOOKLET_ECOPACK),
    FLASH_DRIVE(ResourceBundleConstants.CD_BOOKLET_FLASH_DRIVE),
    PAGES_4(ResourceBundleConstants.CD_BOOKLET_PAGES, 4),
    PAGES_6(ResourceBundleConstants.CD_BOOKLET_PAGES, 6),
    PAGES_8(ResourceBundleConstants.CD_BOOKLET_PAGES, 8),
    PAGES_10( ResourceBundleConstants.CD_BOOKLET_PAGES, 10),
    PAGES_12( ResourceBundleConstants.CD_BOOKLET_PAGES, 12),
    PAGES_14( ResourceBundleConstants.CD_BOOKLET_PAGES, 14),
    PAGES_16( ResourceBundleConstants.CD_BOOKLET_PAGES, 16),
    PAGES_18( ResourceBundleConstants.CD_BOOKLET_PAGES, 18),
    PAGES_20( ResourceBundleConstants.CD_BOOKLET_PAGES, 20),
    PAGES_22( ResourceBundleConstants.CD_BOOKLET_PAGES, 22),
    PAGES_24( ResourceBundleConstants.CD_BOOKLET_PAGES, 24),
    PAGES_26( ResourceBundleConstants.CD_BOOKLET_PAGES, 26),
    PAGES_28( ResourceBundleConstants.CD_BOOKLET_PAGES, 28),
    PAGES_30( ResourceBundleConstants.CD_BOOKLET_PAGES, 30),
    PAGES_32( ResourceBundleConstants.CD_BOOKLET_PAGES, 32),
    PAGES_34( ResourceBundleConstants.CD_BOOKLET_PAGES, 34),
    PAGES_36( ResourceBundleConstants.CD_BOOKLET_PAGES, 36),
    PAGES_38( ResourceBundleConstants.CD_BOOKLET_PAGES, 38),
    PAGES_40( ResourceBundleConstants.CD_BOOKLET_PAGES, 40);

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
