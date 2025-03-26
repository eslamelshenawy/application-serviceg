package gov.saip.applicationservice.common.enums;

public enum ApplicationCustomerType {
    AGENT("Agent", "وكيل"),
    MAIN_OWNER("Owner", "مالك"),
    SECONDARY_OWNER("", ""),
    PREVIOUS_MAIN_OWNER("", ""),
    PREVIOUS_SECONDARY_OWNER("", ""),
    APPLICANT("", ""),
    INVENTOR("", ""),
    RELEVANT("", ""),
    LICENSED_CUSTOMER("", ""),
    LICENSED_CUSTOMER_AGENT("", ""),
    OTHER("", "");

    public final String nameAr;
    public final String nameEn;
    private ApplicationCustomerType(String nameEn, String nameAr) {
        this.nameEn = nameEn;
        this.nameAr = nameAr;
    }
}
