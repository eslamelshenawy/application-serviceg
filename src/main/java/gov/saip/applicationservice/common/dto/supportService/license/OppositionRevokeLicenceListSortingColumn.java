package gov.saip.applicationservice.common.dto.supportService.license;

public enum OppositionRevokeLicenceListSortingColumn {
    MODIFIED_DATE("modifiedDate"),
    LICENSE_NUMBER("revokeLicenceRequest.licenceRequest.requestNumber"),
    REQUEST_STATUS("requestStatus.nameAr"),
    FROM_DATE("revokeLicenceRequest.licenceRequest.fromDate"),
    TO_DATE("revokeLicenceRequest.licenceRequest.toDate"),
    FILLING_DATE("applicationInfo.filingDate"),
    TRADEMARK_NAME_AR("applicationInfo.titleEn"),
    TRADEMARK_NAME_EN("applicationInfo.titleAr");

    String columnName;

    private OppositionRevokeLicenceListSortingColumn(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return this.columnName;
    }

}
