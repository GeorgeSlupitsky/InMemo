package ua.slupitsky.in_memo.constants;

public class ExcelColumnWidth {

    public static final int DEFAULT = 30;
    public static final int CD_NUMBER = 1200;
    public static final int CD_ORDER = 1200;
    public static final int CD_ALBUM = 10000;
    public static final int CD_YEAR = 3000;
    public static final int CD_NOTE = 5500;
    public static final int CD_MEMBERS = 15000;
    public static final int CD_DISCOGS_LINK = 18000;
    public static final int CD_AUTOGRAPH = 1200;
    public static final int DRUMSTICK_NUMBER = 1200;
    public static final int DRUMSTICK_DATE = 3000;
    public static final int DRUMSTICK_LINK_TO_PHOTO = 20000;

    private ExcelColumnWidth(){
        throw new IllegalStateException(DefaultAppConstants.CONSTANT_CLASS);
    }

}
