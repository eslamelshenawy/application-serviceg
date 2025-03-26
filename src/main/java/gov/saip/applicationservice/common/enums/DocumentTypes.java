package gov.saip.applicationservice.common.enums;

public enum DocumentTypes {
    POA("POA");
    private String code;

    DocumentTypes(String code) {
        this.code = code;
    }
    public String getCode() {
        return this.code;
    }
}
