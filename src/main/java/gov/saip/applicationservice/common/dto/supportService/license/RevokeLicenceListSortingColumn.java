package gov.saip.applicationservice.common.dto.supportService.license;

public enum RevokeLicenceListSortingColumn {
    MODIFIED_DATE("modifiedDate"),
    LICENSE_NUMBER("licenceRequest.requestNumber"),
    REQUEST_STATUS("requestStatus.nameAr"),
    FROM_DATE("licenceRequest.fromDate"),
    TO_DATE("licenceRequest.toDate"),
    FILLING_DATE("applicationInfo.filingDate"),
    TRADEMARK_NAME_AR("applicationInfo.titleEn"),
    TRADEMARK_NAME_EN("applicationInfo.titleAr");

    String columnName;

    private RevokeLicenceListSortingColumn(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return this.columnName;
    }

}
