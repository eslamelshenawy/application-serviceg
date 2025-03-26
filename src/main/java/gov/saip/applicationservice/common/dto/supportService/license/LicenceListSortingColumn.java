package gov.saip.applicationservice.common.dto.supportService.license;

public enum LicenceListSortingColumn {
    MODIFIED_DATE("modifiedDate"),
    LICENSE_NUMBER("requestNumber"),
    REQUEST_STATUS("requestStatus.nameAr"),
    FROM_DATE("fromDate"),
    TO_DATE("toDate"),
    FILLING_DATE("applicationInfo.filingDate"),
    TRADEMARK_NAME_AR("applicationInfo.titleEn"),
    TRADEMARK_NAME_EN("applicationInfo.titleAr");

    String columnName;

    private LicenceListSortingColumn(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return this.columnName;
    }

}
