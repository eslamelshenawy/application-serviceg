package gov.saip.applicationservice.common.enums.certificate;

import gov.saip.applicationservice.common.enums.ApplicationStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

import static gov.saip.applicationservice.common.enums.ApplicationStatusEnum.ACCEPTANCE;

@Getter
@AllArgsConstructor
public enum PatentCertificatePreCondition {

    PATENT_ISSUE_CERTIFICATE(getIssueCertificateStatusList()),
    CERTIFIED_REGISTER_COPY(getCertificateRegisterCopyStatusList()),
    CERTIFIED_PRIORITY_COPY(getCertificatePriorityCopyStatusList()),
    PROOF_ISSUANCE_APPLICATION(getProofInstanceApplicationStatusList()),
    PROOF_FACTS_APPEAL(getProofFactsAppealStatusList()),
    FINAL_SPECIFICATION_DOCUMENT(getFinalSpecificationDocumentStatusList()),
    ALL_APPLICATION_RECORDS(getAllApplicationRecordStatusList());

    private List<ApplicationStatusEnum> appStatusList;

    private static List<ApplicationStatusEnum> getIssueCertificateStatusList() {
        
        return List.of(
                ACCEPTANCE
        );
    }

    private static List<ApplicationStatusEnum> getCertificateRegisterCopyStatusList() {
        
        return List.of(
                ACCEPTANCE
        );
    }

    private static List<ApplicationStatusEnum> getCertificatePriorityCopyStatusList() {
        
        return List.of(
                ACCEPTANCE
        );
    }

    private static List<ApplicationStatusEnum> getProofInstanceApplicationStatusList() {
        
        return List.of(
                ACCEPTANCE
        );
    }

    private static List<ApplicationStatusEnum> getProofFactsAppealStatusList() {
        
        return List.of(
                ACCEPTANCE
        );
    }

    private static List<ApplicationStatusEnum> getFinalSpecificationDocumentStatusList() {
        
        return List.of(
                ACCEPTANCE
        );
    }

    private static List<ApplicationStatusEnum> getAllApplicationRecordStatusList() {
        
        return List.of(
                ACCEPTANCE
        );
    }

}
