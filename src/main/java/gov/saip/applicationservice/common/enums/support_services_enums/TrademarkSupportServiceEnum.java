package gov.saip.applicationservice.common.enums.support_services_enums;

import gov.saip.applicationservice.common.enums.PublicationType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum TrademarkSupportServiceEnum {
    
    LICENSING_REGISTRATION(getLicenseRegistration()),
    LICENSING_MODIFICATION(getLicenseModification()),
    VOLUNTARY_REVOKE(getVoluntaryRevoke()),
    OWNERSHIP_CHANGE(getOwnerShipChange()),
    AGENT_SUBSTITUTION(getAgentSubstitution()),
    REVOKE_BY_COURT_ORDER(getRevokeByCourtOrder()),
    REVOKE_PRODUCTS(getRevokeProducts()),
    REVOKE_LICENSE_REQUEST(getRevokeLicenseRequest()),
    EDIT_TRADEMARK_NAME_ADDRESS(getEditTrademarkNameAddress()),
    EDIT_TRADEMARK_IMAGE(getEditTrademarkImage());
    
    
    
    private List<PublicationType> publicationTypes;

    private static List<PublicationType> getEditTrademarkImage() {
        return List.of(PublicationType.EDIT_TRADEMARK_IMAGE);
    }
    
    private static List<PublicationType> getEditTrademarkNameAddress() {
    return List.of(PublicationType.EDIT_NAME_OR_ADDRESS);}

    private static List<PublicationType> getRevokeProducts() {
        return List.of(PublicationType.PRODUCTS_LIMIT);
    }
    
    private static List<PublicationType> getRevokeByCourtOrder() {
        return List.of(PublicationType.COURT_DELETION_ORDER);
    }
    
    private static List<PublicationType> getLicenseRegistration() {
        return List.of(PublicationType.LICENCE_CANCELLATION, PublicationType.TRADEMARK_LICENCE_USE);
    }
    
    private static List<PublicationType> getLicenseModification() {
        return List.of(PublicationType.LICENCE_CANCELLATION, PublicationType.TRADEMARK_LICENCE_USE);
    }
    
    private static List<PublicationType> getVoluntaryRevoke() {
        return List.of(PublicationType.VOLUNTARY_TRADEMARK_DELETION);
    }

    private static List<PublicationType> getTrademarkRegistration() {
        return List.of(PublicationType.TRADEMARK_REGISTERATION);
    }
    
    private static List<PublicationType> getOwnerShipChange() {
        return List.of(PublicationType.OWNERSHIP_TRANSFER);
    }
    
    private static List<PublicationType> getAgentSubstitution() {
        return List.of(PublicationType.AGENT_SUBSTITUTION);
    }

    private static List<PublicationType> getRevokeLicenseRequest() {
        return List.of(PublicationType.LICENCE_REVOKE);
    }
}
