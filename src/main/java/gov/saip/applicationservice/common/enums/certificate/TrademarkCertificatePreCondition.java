package gov.saip.applicationservice.common.enums.certificate;

import gov.saip.applicationservice.common.enums.ApplicationStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

import static gov.saip.applicationservice.common.enums.ApplicationStatusEnum.*;

@Getter
@AllArgsConstructor
public enum TrademarkCertificatePreCondition {

    ISSUE_CERTIFICATE(getIssueCertificateStatusList()),
    CERTIFIED_REGISTER_COPY(getCertificateRegisterCopyStatusList()),
    CERTIFIED_PRIORITY_COPY(getCertificatePriorityCopyStatusList()),
    PROOF_ISSUANCE_APPLICATION(getProofInstanceApplicationStatusList()),
    PROOF_FACTS_APPEAL(getProofFactsAppealStatusList()),
    FINAL_SPECIFICATION_DOCUMENT(getFinalSpecificationDocumentStatusList()),
    ALL_APPLICATION_RECORDS(getAllApplicationRecordStatusList()),
    DEPOSIT_CERTIFICATE(getDepositCertificateList()),
    EXACT_COPY(getExactCopyCertificateList()),
    REVOKE_VOLUNTARY_TRADEMARK_CERTIFICATE(getTrademarkRevokeVoluntaryCertificateList()),
    LICENSE_REGISTRATION_CERTIFICATE(getLicenseRegistrationStatusList()),
    LICENSE_CANCELLATION_CERTIFICATE(getLicenseCancellationStatusList());
    
    private List<ApplicationStatusEnum> appStatusList;
    
    private static List<ApplicationStatusEnum> getLicenseCancellationStatusList() {
        
        return List.of(
                ACCEPTANCE,
                THE_TRADEMARK_IS_REGISTERED
        );
    }
    
    private static List<ApplicationStatusEnum> getLicenseRegistrationStatusList() {
        return List.of(
                ACCEPTANCE,
                THE_TRADEMARK_IS_REGISTERED
        );
    }
    
    private static List<ApplicationStatusEnum> getExactCopyCertificateList() {
        return List.of(
                ACCEPTANCE,
                THE_TRADEMARK_IS_REGISTERED
        );
    }
    
    private static List<ApplicationStatusEnum> getIssueCertificateStatusList() {

        return List.of(
                ACCEPTANCE,
                THE_TRADEMARK_IS_REGISTERED
        );
    }

    private static List<ApplicationStatusEnum> getCertificateRegisterCopyStatusList() {

        return List.of(
                ACCEPTANCE,
                THE_TRADEMARK_IS_REGISTERED
        );
    }

    private static List<ApplicationStatusEnum> getCertificatePriorityCopyStatusList() {

        return List.of(
                ACCEPTANCE,
                THE_TRADEMARK_IS_REGISTERED
        );
    }

    private static List<ApplicationStatusEnum> getProofInstanceApplicationStatusList() {

        return List.of(
                ACCEPTANCE,
                THE_TRADEMARK_IS_REGISTERED
        );
    }

    private static List<ApplicationStatusEnum> getProofFactsAppealStatusList() {

        return List.of(
                ACCEPTANCE,
                THE_TRADEMARK_IS_REGISTERED
        );
    }

    private static List<ApplicationStatusEnum> getFinalSpecificationDocumentStatusList() {

        return List.of(
                ACCEPTANCE,
                THE_TRADEMARK_IS_REGISTERED
        );
    }

    private static List<ApplicationStatusEnum> getAllApplicationRecordStatusList() {

        return List.of(
                ACCEPTANCE,
                THE_TRADEMARK_IS_REGISTERED
        );
    }
    
    private static List<ApplicationStatusEnum> getDepositCertificateList() {
        
        return List.of(
                PUBLISHED_ELECTRONICALLY,
                OBJECTOR
        );
    }
    private static List<ApplicationStatusEnum> getTrademarkRevokeVoluntaryCertificateList() {

        return List.of(
                CROSSED_OUT_MARK, // TODO-REMOVE
                REVOKED_OF_THE_TRADEMARK_OWNER
        );
    }


}

