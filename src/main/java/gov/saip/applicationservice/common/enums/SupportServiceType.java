package gov.saip.applicationservice.common.enums;

import java.util.List;

public enum SupportServiceType {
    EVICTION,
    EXTENSION,
    RETRACTION,
    PETITION_RECOVERY,
    INITIAL_MODIFICATION,
    AGENT_SUBSTITUTION,
    OPPOSITION_REQUEST,
    PROTECTION_PERIOD_EXTENSION_REQUEST,
    APPEAL_REQUEST,
    LICENSING_REGISTRATION,
    OWNERSHIP_CHANGE,
    LICENSING_MODIFICATION,
    ANNUAL_REGISTRATION,
    ANNUAL_FEES_PAY,
    RENEWAL_FEES_PAY,
    DOCUMENTS_AND_CERTIFICATES_MANAGEMENT,
    VOLUNTARY_REVOKE,
    REVOKE_BY_COURT_ORDER,
    REVOKE_PRODUCTS,
    TRADEMARK_APPLICATION_SEARCH,
    EDIT_TRADEMARK_NAME_ADDRESS,
    REVOKE_LICENSE_REQUEST,
    OPPOSITION_REVOKE_LICENCE_REQUEST,
    TRADEMARK_APPEAL_REQUEST,
    EDIT_TRADEMARK_IMAGE,
    PETITION_REQUEST_NATIONAL_STAGE,
    PATENT_PRIORITY_REQUEST,
    PATENT_PRIORITY_MODIFY
    ;


    public final static List<SupportServiceType> SERVICES_CAN_REQUESTED_BY_NON_OWNERS = List.of(OPPOSITION_REVOKE_LICENCE_REQUEST,
            REVOKE_BY_COURT_ORDER);

    public final static List<SupportServiceType> SERVICES_CANNOT_REQUESTED_BY_OWNERS = List.of(REVOKE_BY_COURT_ORDER);

    public boolean isSupportServicesCanRequestedByNonOwner(List<SupportServiceType> services) {
        List<SupportServiceType> filteredServices = services.stream().filter(service -> SERVICES_CAN_REQUESTED_BY_NON_OWNERS.contains(service)).toList();
        return services.size() == filteredServices.size();
    }

    public static boolean isServicePermittedToCreateWithOtherServices(SupportServiceType serviceType) {
        return OPPOSITION_REQUEST.equals(serviceType) || RENEWAL_FEES_PAY.equals(serviceType) || ANNUAL_FEES_PAY.equals(serviceType) || REVOKE_LICENSE_REQUEST.equals(serviceType);
    }

    public boolean isSupportServicesCannotRequestedByOwner(List<SupportServiceType> services) {
        List<SupportServiceType> filteredServices = services.stream().filter(service -> SERVICES_CANNOT_REQUESTED_BY_OWNERS.contains(service)).toList();
        return filteredServices.isEmpty();
    }

}
