package gov.saip.applicationservice.common.enums.customers;

public enum UserGroup {
    LEGAL_ENTITY("LEGAL_ENTITY"),
    GOVERNMENT_AGENCY("GOVERNMENT_AGENCY"),
    NATURAL_PERSON_WITH_NATIONALITY("NATURAL_PERSON_WITH_NATIONALITY"),
    FOREIGN_CORPORATION("FOREIGN_CORPORATION"),
    NATURAL_PERSON_WITH_FOREIGN_NATIONALITY("NATURAL_PERSON_WITH_FOREIGN_NATIONALITY"),

    AGENT("AGENT"),

    LEGAL_REPRESENTATIVE("LEGAL_REPRESENTATIVE"),

    UNIVERSITY("  UNIVERSITY");

    private String value; // the value

    // Constructor
    UserGroup(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public String toString() {
        return this.value;
    }

    public static UserGroup valueOfItem(String item){

        UserGroup userGroup = null;

        for(UserGroup ug : UserGroup.values()){
            if(ug.getValue().equalsIgnoreCase(item)) {
                userGroup = ug;
                break;
            }
        }

        return  userGroup;
    }


}
