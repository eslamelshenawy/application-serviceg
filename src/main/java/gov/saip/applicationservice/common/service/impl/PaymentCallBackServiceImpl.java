package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.common.clients.rest.callers.NotificationCaller;
import gov.saip.applicationservice.common.clients.rest.feigns.BPMCallerFeignClient;
import gov.saip.applicationservice.common.clients.rest.feigns.CustomerServiceFeignClient;
import gov.saip.applicationservice.common.clients.rest.feigns.PaymentFeeCostFeignClient;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.dto.bpm.CompleteTaskRequestDto;
import gov.saip.applicationservice.common.dto.notifications.AppNotificationDto;
import gov.saip.applicationservice.common.dto.notifications.NotificationLanguage;
import gov.saip.applicationservice.common.dto.notifications.NotificationRequest;
import gov.saip.applicationservice.common.dto.notifications.NotificationTemplateCode;
import gov.saip.applicationservice.common.enums.*;
import gov.saip.applicationservice.common.facade.ApplicationAgentFacade;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.ApplicationRelevantType;
import gov.saip.applicationservice.common.model.ApplicationSupportServicesType;
import gov.saip.applicationservice.common.model.CertificateRequest;
import gov.saip.applicationservice.common.model.installment.ApplicationInstallment;
import gov.saip.applicationservice.common.model.opposition.Opposition;
import gov.saip.applicationservice.common.repository.ApplicationInfoRepository;
import gov.saip.applicationservice.common.repository.ApplicationRelevantTypeRepository;
import gov.saip.applicationservice.common.repository.ApplicationSupportServicesTypeRepository;
import gov.saip.applicationservice.common.service.*;
import gov.saip.applicationservice.common.service.activityLog.ActivityLogService;
import gov.saip.applicationservice.common.service.agency.SupportServiceCustomerService;
import gov.saip.applicationservice.common.service.annualfees.AnnualFeesService;
import gov.saip.applicationservice.common.service.appeal.AppealRequestService;
import gov.saip.applicationservice.common.service.appeal.TrademarkAppealRequestService;
import gov.saip.applicationservice.common.service.installment.ApplicationInstallmentService;
import gov.saip.applicationservice.common.service.opposition.OppositionRequestService;
import gov.saip.applicationservice.common.service.opposition.OppositionService;
import gov.saip.applicationservice.common.service.supportService.ApplicationEditNameAddressRequestService;
import gov.saip.applicationservice.common.service.supportService.ApplicationEditTrademarkImageRequestService;
import gov.saip.applicationservice.common.service.supportService.ApplicationPriorityModifyRequestService;
import gov.saip.applicationservice.common.service.supportService.ApplicationPriorityRequestService;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.modules.common.service.BaseApplicationInfoService;
import gov.saip.applicationservice.modules.ic.service.IntegratedCircuitService;
import gov.saip.applicationservice.util.Constants;
import gov.saip.applicationservice.util.Utilities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static gov.saip.applicationservice.common.enums.ApplicationPaymentMainRequestTypesEnum.LICENSING_MODIFICATION;
import static gov.saip.applicationservice.common.enums.ApplicationPaymentMainRequestTypesEnum.LICENSING_REGISTRATION;
import static gov.saip.applicationservice.common.enums.ApplicationPaymentMainRequestTypesEnum.*;
import static gov.saip.applicationservice.common.enums.RequestTypeEnum.EDIT_TRADEMARK_NAME_ADDRESS;
import static gov.saip.applicationservice.common.enums.RequestTypeEnum.OPPOSITION_REQUEST;
import static gov.saip.applicationservice.common.enums.RequestTypeEnum.REVOKE_LICENSE_REQUEST;
import static gov.saip.applicationservice.common.enums.RequestTypeEnum.REVOKE_PRODUCTS;
import static gov.saip.applicationservice.common.enums.RequestTypeEnum.*;
import static gov.saip.applicationservice.common.enums.SupportServiceType.RENEWAL_FEES_PAY;


@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentCallBackServiceImpl implements PaymentCallBackService {

    @Autowired
    @Lazy
    private ApplicationInfoService applicationInfoService;

    private final BPMCallerServiceImpl bpmCallerService;
    private final ApplicationInfoRepository applicationInfoRepository;
    private final BPMCallerFeignClient bpmCallerFeignClient;
    private final LicenceRequestService licenceRequestService;
    private final RevokeLicenceRequestService revokeLicenceRequestService;
    private final ApplicationAgentFacade applicationAgentFacade;
    private final OppositionService oppositionService;
    private final AppealRequestService appealRequestService;
    private final ApplicationUserService applicationUserService;
    private final CustomerServiceCaller customerServiceCaller;
    private final CertificateRequestService certificateRequestService;
    private final ChangeOwnershipRequestService changeOwnershipRequestService;
    private final AnnualFeesService annualFeesService;
    private final ApplicationSupportServicesTypeService supportServicesTypeService;
    private final ApplicationCustomerServiceImpl applicationCustomerService;
    private final ApplicationAcceleratedService applicationAcceleratedService;
    private final ApplicationInstallmentService applicationInstallmentService;
    private final RevokeVoluntaryRequestService revokeVoluntryRequestService;
    private final RevokeProductsService revokeProductsService;
    private final ApplicationEditNameAddressRequestService applicationEditNameAddressRequestService;
    private final ApplicationEditTrademarkImageRequestService applicationEditTrademarkImageRequestService;
    private final ApplicationSearchService applicationSearchService;
    private final ProtectionExtendRequestService protectionExtendRequestService;
    private final RetractionRequestService retractionRequestService;
    private final EvictionRequestService evictionRequestService;
    private final InitialModificationRequestService initialModificationRequestService;
    private final PetitionRecoveryRequestService petitionRecoveryRequestService;
    private final ExtensionRequestService extensionRequestService;
    private final TrademarkAppealRequestService trademarkAppealRequestService;
    private final OppositionRequestService oppositionRequestService;
    private final PetitionRequestNationalStageService petitionRequestNationalStageService;
    private final ApplicationPriorityRequestService applicationPriorityRequestService;
    private final ApplicationPriorityModifyRequestService applicationPriorityModifyRequestService;
    private final ApplicationRelevantTypeRepository applicationRelevantTypeRepository;
    private final CustomerServiceFeignClient customerServiceFeignClient;
    private final NotificationCaller notificationCaller;
    private final PaymentFeeCostFeignClient paymentFeeCostFeignClient;
    private final ActivityLogService activityLogService;
    private final ApplicationPriorityService applicationPriorityService;
    private final ApplicationStatusChangeLogService applicationStatusChangeLogService;
    private final ApplicationSupportServicesTypeRepository applicationSupportServicesTypeRepository;
    private final SupportServiceCustomerService  supportServiceCustomerService;
    private final IntegratedCircuitService integratedCircuitService;
    
    @Value("${link.portal}")
    String portalLink;
    
    @Value("${link.contactus}")
    String contactUs;

    @Value("${link.publication}")
    String publicationLink;


    @Override
    @Transactional
    public void paymentCallBack(ApplicationNumberGenerationDto applicationNumberGenerationDto, Long id) {

        if (Objects.isNull(applicationNumberGenerationDto.getMainRequestType())) {
            return;
        }
        log.info("payment call back application service: request with id {}, request type {} ",
                id, applicationNumberGenerationDto.getMainRequestType());
        String applicationStatus = null;

        switch (ApplicationPaymentMainRequestTypesEnum.valueOf(applicationNumberGenerationDto.getMainRequestType())) {
            case FILE_NEW_APPLICATION:
                fileNewApplication(applicationNumberGenerationDto, id);
                break;
            case INTEGRATED_CIRCUITS_REGISTRATION:
                generalNewApplication(applicationNumberGenerationDto, id);
                break;
                case PLANT_VARIETIES_REGISTRATION:
                generalNewApplication(applicationNumberGenerationDto, id);
                break;
            case FORMALITY_UPDATE_1:
                firstApplicationUpdatePayment(applicationNumberGenerationDto, id);
                if (ApplicationCategoryEnum.PATENT.name().equals(applicationInfoRepository.findCategoryByApplicationId(id)))
                    applicationStatus = ApplicationStatusEnum.UNDER_FORMAL_PROCESS.name();
                break;
            case FORMALITY_UPDATE_2:
                SecondApplicationUpdatePayment(applicationNumberGenerationDto, id);
                if (ApplicationCategoryEnum.PATENT.name().equals(applicationInfoRepository.findCategoryByApplicationId(id)))
                    applicationStatus = ApplicationStatusEnum.UNDER_FORMAL_PROCESS.name();
                break;
            case SUBS_UPDATE_1:
                firstSubsUpdate(applicationNumberGenerationDto, id);
                break;
            case SUBS_UPDATE_2:
                secondSubsUpdate(applicationNumberGenerationDto, id);
                break;
            case SUBS_EXAM_PAYMENT:
                substansiveExaminationPayment(applicationNumberGenerationDto, id);
                if (ApplicationCategoryEnum.TRADEMARK.name().equals(applicationInfoRepository.findCategoryByApplicationId(id)))
                    applicationStatus = ApplicationStatusEnum.ACCEPTANCE_OF_THE_REGISTRATION_APPLICATION.name();
                break;
            case PUBLICATION_PAYMENT:
                publicationPayment(applicationNumberGenerationDto, id, null);
                applicationStatus = getAfterPublicationPaymentStatus(id);
                applicationStatusChangeLogService.changeApplicationStatusAndLog(applicationStatus, null, id);
                publicationPayment(applicationNumberGenerationDto, id,null);
                break;
            case PAY_PUBLICATION_GRANT_FEES_IC:
                payPublicationGrantFeesIC(applicationNumberGenerationDto, id, null);
                applicationStatus = getAfterPublicationPaymentStatus(id);
                break;
            case PAY_FORMALITY_CORRECTION_FEES_IC:
                firstPayFormalityCorrectionFeesIC(id, null);
                resetFirstAssignationDate(id);
                applicationStatus = ApplicationStatusEnum.UNDER_FORMAL_PROCESS.name();
                break;
            case SECOND_FORMALITY_CORRECTION_FEES_IC:
                secondPayFormalityCorrectionFeesIC(id, null);
                resetFirstAssignationDate(id);
                applicationStatus = ApplicationStatusEnum.UNDER_FORMAL_PROCESS.name();
                break;
            case WITHDRAW_APPLICATION:
                withdrawApplicationPayment(applicationNumberGenerationDto, id);
                break;
            case PRIORITY_WITHDRAW:
                withdrawPriorityPayment(applicationNumberGenerationDto, id);
                break;
            case CHANGE_AGENT_REQUEST:
            case ADD_AGENT_REQUEST:
            case DELETE_AGENT_REQUEST:
                changeAgentRequestHandler(applicationNumberGenerationDto, id);
                break;
            case PETITION_FOR_RECOVERY:
                PetitionRecoveryPayment(applicationNumberGenerationDto, id);
                break;
            case RELINQUISHMENT:
                relinquishmentRequestPayment(applicationNumberGenerationDto, id);
                break;
            case EXTEND_PERIOD_FORMAL_EXAMINATION:
            case EXTEND_PERIOD_EXAMINER_SUBSTINTIVE:
            case EXTEND_PERIOD_BILL_GRANT:
            case EXTEND_PERIOD_EXAMINER_SUBSTINTIVE_FIRST:
            case EXTEND_PERIOD_EXAMINER_SUBSTINTIVE_SECOND:
                log.info(" extend test   {} ------ --- {}   ", id, applicationNumberGenerationDto);
                extendDueDate(applicationNumberGenerationDto, id);
                log.info(" extend test   {} ------ --- {}   ", id, applicationNumberGenerationDto);
                break;
            case INITIAL_UPDATE:
                intialUpdatePayment(applicationNumberGenerationDto, id);
                break;

            case OPPOSITION_REQUEST:
                oppositionRequestPaymentCallBack(id, applicationNumberGenerationDto);
                break;

            case APPLICANT_OPPOSITION_HEARING_REQ:
                log.info("Call back APPLICANT_OPPOSITION_HEARING_REQ with id ==>> {} ", id);
                applicantOppositionRequestHearingRequest(id);
                break;

            case APPEAL_REQUEST:
                processAppealRequestPayment(applicationNumberGenerationDto, id);
                break;

            case ISSUE_CERTIFICATE:
                issueCertificatePayment(applicationNumberGenerationDto, id);
                break;

            case PATENT_ISSUE_CERTIFICATE:
                patentIssueCertificatePayment(applicationNumberGenerationDto, id);
                break;

            case CERTIFIED_REGISTER_COPY:
                certificateRegisterCopyPayment(applicationNumberGenerationDto, id);
                break;

            case CERTIFIED_PRIORITY_COPY:
                certificatePriorityCopyPayment(applicationNumberGenerationDto, id);
                break;

            case PROOF_ISSUANCE_APPLICATION:
                proofIssuanceApplicationPayment(applicationNumberGenerationDto, id);
                break;

            case PROOF_FACTS_APPEAL:
                proofFactsAppealPayment(applicationNumberGenerationDto, id);
                break;

            case SECRET_DESIGN_DOCUMENT:
                secretDesignDocumentPayment(applicationNumberGenerationDto, id);
                break;

            case FINAL_SPECIFICATION_DOCUMENT:
                finalSpecificationDocumentPayment(applicationNumberGenerationDto, id);
                break;

            case ALL_APPLICATION_RECORDS:
                allApplicationRecordsPayment(applicationNumberGenerationDto, id);
                break;

            case EXACT_COPY:
                exactCopyPayment(applicationNumberGenerationDto, id);
                sendTrademarkExactCopyCertificateNotificationRequest(id);
                break;

            case LICENSE_REGISTRATION_CERTIFICATE:
                licenseRegistrationPayment(applicationNumberGenerationDto, id);
                break;

            case LICENSE_CANCELLATION_CERTIFICATE:
                licenseCancellationPayment(applicationNumberGenerationDto, id);
                break;

            case RENEWAL_FEES_PAY:
                renewalFeesPaymentCallBackHandler(id, applicationNumberGenerationDto);
                break;
            case ANNUAL_FEES_PAY:
                annualFeesPaymentCallBackHandler(id, applicationNumberGenerationDto);
                break;
            case INSTALLMENT_PROTECTION_RENEWAL:
                log.info("Call back INSTALLMENT_PROTECTION_RENEWAL with id ==>> {} ", id);
                installmentPaymentCallBackHandler(id, applicationNumberGenerationDto);
                sendTrademarkRenewalNotification(id, applicationNumberGenerationDto);
                break;
            case OWNERSHIP_CHANGE_REQUEST:
                ownershipChangeRequest(id, applicationNumberGenerationDto);
                break;
            case LICENSING_REGISTRATION:
                applicationNumberGenerationDto.setApplicationPaymentMainRequestTypesEnum(LICENSING_REGISTRATION);
                LicencingRegistrationRequest(id, applicationNumberGenerationDto);
                break;
            case REVOKE_LICENSE_REQUEST:
                RevokeLicenceRequest(id, applicationNumberGenerationDto);
                break;

            case LICENSE_CANCELLATION:
            case LICENSING_MODIFICATION:
            case LICENSE_MODIFICATION:
                applicationNumberGenerationDto.setApplicationPaymentMainRequestTypesEnum(LICENSING_MODIFICATION);
                LicencingModificationsRequest(id, applicationNumberGenerationDto);
                break;
            case GRANTS_FEE:
                if(ApplicationCategoryEnum.TRADEMARK.name().equals(applicationInfoRepository.findCategoryByApplicationId(id)))
                    applicationStatusChangeLogService.changeApplicationStatusAndLog(ApplicationStatusEnum.THE_TRADEMARK_IS_REGISTERED.name(), null, id);
                grantFeeRequest(id);
                break;
            case PROTECTION_PERIOD_EXTENSION_REQUEST:
                extendProtectionPeriod(id, applicationNumberGenerationDto);
                break;
            case DEPOSIT_CERTIFICATE:
                depositCertificatePayment(applicationNumberGenerationDto, id);
                sendDepositCertificateNotificationRequest(id);
                break;
            case REVOKE_VOLUNTARY:
                revokeVoluntryPaymentCallBackHandler(id, applicationNumberGenerationDto);
                break;
            case REVOKE_PRODUCTS:
                revokeProductsPaymentCallBackHandler(id, applicationNumberGenerationDto);
                break;
            case REVOKE_VOLUNTARY_PUBLICATION_PAYMENT:
                publicationPayment(applicationNumberGenerationDto, id, VOLUNTARY_REVOKE.name());
                break;
            case REVOKE_PRODUCTS_PUBLICATION_PAYMENT:
                publicationPayment(applicationNumberGenerationDto, id, REVOKE_PRODUCTS.name());
                break;
            case LICENSING_GRANT_FEE_PAYMENT:
                publicationPayment(applicationNumberGenerationDto, id, LICENSING_REGISTRATION.name());
                break;
            case REVOKE_LICENSING_FEE_PAYMENT:
                publicationPayment(applicationNumberGenerationDto, id, REVOKE_LICENSE_REQUEST.name());
                break;
            case CHANGE_OWNERSHIP_GRANT_FEE_PAYMENT:
                publicationPayment(applicationNumberGenerationDto, id, CHANGE_OWNERSHIP.name());
                break;
            case TRADEMARK_APPLICATION_SEARCH:
                applicationSearchPaymentCallBackHandler(id, applicationNumberGenerationDto);
                break;
            case EDIT_TRADEMARK_NAME_ADDRESS:
                editTrademarkDataPaymentCallBackHandler(id, applicationNumberGenerationDto);
                break;
            case EDIT_TRADEMARK_NAME_ADDRESS_PUBLICATION_PAYMENT:
                publicationPayment(applicationNumberGenerationDto, id, EDIT_TRADEMARK_NAME_ADDRESS.name());
                break;
            case EDIT_TRADEMARK_IMAGE_PAYMENT:
                editTrademarkImagePaymentCallBackHandler(id, applicationNumberGenerationDto);
                break;
            case EDIT_TRADEMARK_IMAGE_PUBLICATION_PAYMENT:
                publicationPaymentForEditTrademarkImage(applicationNumberGenerationDto, id);
                break;
            case TRADEMARK_REGISTRATION_APPEAL_REQUEST:
            case TRADEMARK_CHANGE_IMG_APPEAL_REQUEST:
                trademarkAppealRequestService.paymentCallBackHandler(id, applicationNumberGenerationDto);
                break;
            case PATENT_FAST_TRACK_BILL:
                completeFastTrackPaymentTasks(id);
                break;
            case PUBLICATION_A:
                completePublicationA(id);
                break;
            case PATENT_PRIORITY_REQUEST:
                patentPriorityRequestPaymentCallBackHandler(id, applicationNumberGenerationDto);
                break;
            case PATENT_PRIORITY_MODIFY:
                patentPriorityModifyRequestPaymentCallBackHandler(id, applicationNumberGenerationDto);
                break;
            case PETITION_REQUEST_NATIONAL_STAGE:
                petitionRequestNationalStage(id, applicationNumberGenerationDto);

            default:
                break;

        }

        if (applicationStatus != null) {
            applicationInfoService.changeApplicationStatusId(id, applicationStatus);
        }

        log.info("payment call back application service: is done with request id {}, request type {} ",
                id, applicationNumberGenerationDto.getMainRequestType());

    }

    private String getAfterPublicationPaymentStatus(Long id) {
        String applicationStatus;
        if (ApplicationCategoryEnum.INTEGRATED_CIRCUITS.name().
                equals(applicationInfoRepository.findCategoryByApplicationId(id)))
            applicationStatus = ApplicationStatusEnum.ACCEPTANCE.name();
        else
            applicationStatus = ApplicationStatusEnum.AWAITING_VERIFICATION.name();
        return applicationStatus;
    }

    //TODO app -> process   ->  true
    private void publicationPaymentForEditTrademarkImage(ApplicationNumberGenerationDto applicationNumberGenerationDto, Long id) {
        String publicationTaskKey = "APPLICANT_PAY_PUBLICATION_INVOICE%";
//        RequestTypeEnum.EDIT_TRADEMARK_IMAGE
        completePaymentTasks(publicationTaskKey, id, EDIT_TRADEMARK_IMAGE.name());
    }

    private void oppositionRequestPaymentCallBack(Long id, ApplicationNumberGenerationDto applicationNumberGenerationDto) {
        oppositionRequestService.paymentCallBackHandler(id, applicationNumberGenerationDto);
    }

    private void applicantOppositionRequestHearingRequest(Long id) {
        String applicationOwnerHearingSessionPayment = "OPPOSITION_SESSION_PAYMENT_FROM_OWNER_%";
        completePaymentTasks(applicationOwnerHearingSessionPayment, id, OPPOSITION_REQUEST.name());
    }

    private void completePublicationA(Long id) {
        String publicationTaskKey = "PAY_PUBLICATION_A_INVOICE_2%";
        completePaymentTasks(publicationTaskKey, id, null); // null in 3rd parameter means that it's main app (Tm,Ind,Ic,Pt)
    }

    private void completeFastTrackPaymentTasks(Long id) {
        List<String> fastTrackAndPublicationATasksDefinationsKes = new ArrayList<>(List.of(
                "FAST_PUB_A_PAYMENT_PAT",
                "PAY_PUBLICATION_A_INVOICE_PAT",
                "FAST_TRACK_PAYMENT_PAT",
                "PAY_FAST_TRACK_PUBLICATION_A_PAT"
        ));
        CompletePaymentDto completePaymentDto = new CompletePaymentDto();
        completePaymentDto.setTaskKeys(fastTrackAndPublicationATasksDefinationsKes);
        completePaymentDto.setRowId(id);
        completePaymentDto.setProcessRequestTypesCode(ApplicationCategoryEnum.valueOf(applicationInfoRepository.findCategoryByApplicationId(id)).getProcessTypeCode());
        bpmCallerFeignClient.activatePaymentsAccount(completePaymentDto);
    }


    public void petitionRequestNationalStage(Long id, ApplicationNumberGenerationDto applicationNumberGenerationDto) {
        petitionRequestNationalStageService.paymentCallBackHandler(id, applicationNumberGenerationDto);
    }

    private void extendProtectionPeriod(Long id, ApplicationNumberGenerationDto applicationNumberGenerationDto) {
        protectionExtendRequestService.paymentCallBackHandler(id, applicationNumberGenerationDto);
    }

    private void ownershipChangeRequest(Long id, ApplicationNumberGenerationDto applicationNumberGenerationDto) {
        changeOwnershipRequestService.paymentCallBackHandler(id, applicationNumberGenerationDto);
    }

    private void LicencingRegistrationRequest(Long id, ApplicationNumberGenerationDto applicationNumberGenerationDto) {
        licenceRequestService.paymentCallBackHandler(id, applicationNumberGenerationDto);
    }

    private void RevokeLicenceRequest(Long id, ApplicationNumberGenerationDto applicationNumberGenerationDto) {
        revokeLicenceRequestService.paymentCallBackHandler(id, applicationNumberGenerationDto);
    }

    private void LicencingModificationsRequest(Long id, ApplicationNumberGenerationDto applicationNumberGenerationDto) {
        licenceRequestService.paymentCallBackHandler(id, applicationNumberGenerationDto);
    }

    private void LicencingCancellationRequest(Long id, ApplicationNumberGenerationDto applicationNumberGenerationDto) {
        licenceRequestService.paymentCallBackHandler(id, applicationNumberGenerationDto);
    }

    private void grantFeeRequest(Long id) {
        String grantTaskFee = "GRANTS_FEE_%";
        completePaymentTasks(grantTaskFee, id, null);

    }

    public void annualFeesPaymentCallBackHandler(Long id, ApplicationNumberGenerationDto applicationNumberGenerationDto) {
        annualFeesService.paymentCallBackHandler(id, applicationNumberGenerationDto);
    }

    public void revokeVoluntryPaymentCallBackHandler(Long id, ApplicationNumberGenerationDto applicationNumberGenerationDto) {
        revokeVoluntryRequestService.paymentCallBackHandler(id, applicationNumberGenerationDto);
    }

    public void applicationSearchPaymentCallBackHandler(Long id, ApplicationNumberGenerationDto applicationNumberGenerationDto) {
        applicationSearchService.paymentCallBackHandler(id, applicationNumberGenerationDto);
    }

    public void revokeProductsPaymentCallBackHandler(Long id, ApplicationNumberGenerationDto applicationNumberGenerationDto) {
        revokeProductsService.paymentCallBackHandler(id, applicationNumberGenerationDto);
    }

    public void installmentPaymentCallBackHandler(Long id, ApplicationNumberGenerationDto applicationNumberGenerationDto) {
        supportServicesTypeService.paymentCallBackHandler(id, applicationNumberGenerationDto);
        //applicationInstallmentService.installmentPaymentCallBackHandler(id, applicationNumberGenerationDto);
    }

    public void renewalFeesPaymentCallBackHandler(Long id, ApplicationNumberGenerationDto applicationNumberGenerationDto) {
        supportServicesTypeService.paymentCallBackHandler(id, applicationNumberGenerationDto);
    }

    public void editTrademarkDataPaymentCallBackHandler(Long id, ApplicationNumberGenerationDto applicationNumberGenerationDto) {
        applicationEditNameAddressRequestService.paymentCallBackHandler(id, applicationNumberGenerationDto);
    }

    public void editTrademarkImagePaymentCallBackHandler(Long id, ApplicationNumberGenerationDto applicationNumberGenerationDto) {
        applicationEditTrademarkImageRequestService.paymentCallBackHandler(id, applicationNumberGenerationDto);
    }

    public void patentPriorityRequestPaymentCallBackHandler(Long id, ApplicationNumberGenerationDto applicationNumberGenerationDto) {
        applicationPriorityRequestService.paymentCallBackHandler(id, applicationNumberGenerationDto);
    }

    public void patentPriorityModifyRequestPaymentCallBackHandler(Long id, ApplicationNumberGenerationDto applicationNumberGenerationDto) {
        applicationPriorityModifyRequestService.paymentCallBackHandler(id, applicationNumberGenerationDto);
    }

    private void allApplicationRecordsPayment(ApplicationNumberGenerationDto applicationNumberGenerationDto, Long id) {
        certificateRequestService.processCertificateRequest(id, "All Application Records");
    }

    private void finalSpecificationDocumentPayment(ApplicationNumberGenerationDto applicationNumberGenerationDto, Long id) {
        certificateRequestService.processCertificateRequest(id, "Final Specification Document");

    }

    private void secretDesignDocumentPayment(ApplicationNumberGenerationDto applicationNumberGenerationDto, Long id) {
        certificateRequestService.processCertificateRequest(id, "Secret Design Document");

    }

    private void proofFactsAppealPayment(ApplicationNumberGenerationDto applicationNumberGenerationDto, Long id) {
        certificateRequestService.processCertificateRequest(id, "Proof Facts Appeal");

    }

    private void proofIssuanceApplicationPayment(ApplicationNumberGenerationDto applicationNumberGenerationDto, Long id) {
        certificateRequestService.processCertificateRequest(id, "Proof Issuance Application");

    }

    private void certificatePriorityCopyPayment(ApplicationNumberGenerationDto applicationNumberGenerationDto, Long id) {
        certificateRequestService.processCertificateRequest(id, "Certified Priority Copy");

    }

    private void certificateRegisterCopyPayment(ApplicationNumberGenerationDto applicationNumberGenerationDto, Long id) {
        certificateRequestService.processCertificateRequest(id, "Certified Register Copy");

    }

    private void issueCertificatePayment(ApplicationNumberGenerationDto applicationNumberGenerationDto, Long id) {
        certificateRequestService.processCertificateRequest(id, "Issue Certificate");
    }

    private void patentIssueCertificatePayment(ApplicationNumberGenerationDto applicationNumberGenerationDto, Long id) {
        certificateRequestService.processCertificateRequest(id, "PatentIssueCertificate");
    }

    private void exactCopyPayment(ApplicationNumberGenerationDto applicationNumberGenerationDto, Long id) {
        certificateRequestService.processCertificateRequest(id, "Exact Copy");
    }

    private void licenseRegistrationPayment(ApplicationNumberGenerationDto applicationNumberGenerationDto, Long id) {
        certificateRequestService.processCertificateRequest(id, "Exact Copy");
    }

    private void licenseCancellationPayment(ApplicationNumberGenerationDto applicationNumberGenerationDto, Long id) {
        certificateRequestService.processCertificateRequest(id, "Exact Copy");
    }

    private void depositCertificatePayment(ApplicationNumberGenerationDto applicationNumberGenerationDto, Long id) {
        certificateRequestService.processCertificateRequest(id, "Deposit Certificate");
    }

    private void firstApplicationUpdatePayment(ApplicationNumberGenerationDto applicationNumberGenerationDto, Long id) {
        String formaltyUpdat1TaskKey = "APP_REJ_AMED_APP_1%";
        completePaymentTasks(formaltyUpdat1TaskKey, id, null);

    }

    private void SecondApplicationUpdatePayment(ApplicationNumberGenerationDto applicationNumberGenerationDto, Long id) {

        String formaltyUpdat2TaskKey = "APP_REJ_AMED_APP_2%";
        completePaymentTasks(formaltyUpdat2TaskKey, id, null);
    }

    private void firstSubsUpdate(ApplicationNumberGenerationDto applicationNumberGenerationDto, Long id) {
        applicationInfoService.updateStatusByCodeAndId(id, ApplicationStatusEnum.UNDER_OBJECTIVE_PROCESS.name());
        String subUpdat1TaskKey = "SUBMIT_SUPPLEMENT_STATMENT%";
        completePaymentTasks(subUpdat1TaskKey, id, null);
    }

    private void secondSubsUpdate(ApplicationNumberGenerationDto applicationNumberGenerationDto, Long id) {
        applicationInfoService.updateStatusByCodeAndId(id, ApplicationStatusEnum.UNDER_OBJECTIVE_PROCESS.name());
        String subUpdat2TaskKey = "SUBMIT_SECOND_SUPPLEMENT_STATMENT%";
        completePaymentTasks(subUpdat2TaskKey, id, null);
    }

    private void withdrawApplicationPayment(ApplicationNumberGenerationDto applicationNumberGenerationDto, Long id) {
        retractionRequestService.paymentCallBackHandler(id, applicationNumberGenerationDto);
    }

    private void withdrawPriorityPayment(ApplicationNumberGenerationDto applicationNumberGenerationDto, Long id) {
        retractionRequestService.paymentCallBackHandlerPriority(id, applicationNumberGenerationDto);
        // soft delete for application priorities
        applicationPriorityService.softDeleteByAppId(id);
    }

    private void relinquishmentRequestPayment(ApplicationNumberGenerationDto applicationNumberGenerationDto, Long id) {
        evictionRequestService.paymentCallBackHandler(id, applicationNumberGenerationDto);
    }

    private void substansiveExaminationPayment(ApplicationNumberGenerationDto applicationNumberGenerationDto, Long id) {
        String subExamTaskKey = "SUBS_EXAM_PAYMENT%";
        ApplicationInfo applicationInfo = applicationInfoRepository.getApplicationInfoWithMainApplicant(id);
        if (isPatentApplication(applicationInfo)) {
            applicationRelevantTypePaid(applicationInfo.getId());
        }
        completePaymentTasks(subExamTaskKey, id, null);
    }

    private void publicationPayment(ApplicationNumberGenerationDto applicationNumberGenerationDto, Long id, String processRequestType) {
        String publicationTaskKey = "PAY_PUBLICATION_INVOICE%";
        completePaymentTasks(publicationTaskKey, id, Objects.nonNull(processRequestType) ? processRequestType : null);
    }

    private void payPublicationGrantFeesIC(ApplicationNumberGenerationDto applicationNumberGenerationDto, Long id, String processRequestType) {
        String publicationTaskKey = "PAY_PUBLICATION_GRANT_FEES%";
        completePaymentTasks(publicationTaskKey, id, Objects.nonNull(processRequestType) ? processRequestType : null);
    }
    private void firstPayFormalityCorrectionFeesIC(Long id, String processRequestType) {
        String correctionTaskKey = "APPLICATION_CORRECTION_EXTERNALUSERTASK_IC%";
        completePaymentTasks(correctionTaskKey, id, Objects.nonNull(processRequestType) ? processRequestType : null);
    }

    private void secondPayFormalityCorrectionFeesIC(Long id, String processRequestType) {
        String correctionTaskKey = "SECOND_APPLICATION_CORRECTION_EXTERNALUSERTASK_IC%";
        completePaymentTasks(correctionTaskKey, id, Objects.nonNull(processRequestType) ? processRequestType : null);
    }

    private void resetFirstAssignationDate(Long applicationId) {
        integratedCircuitService.updateFirstAssignationDateByApplicationId(applicationId);
    }

    private void intialUpdatePayment(ApplicationNumberGenerationDto applicationNumberGenerationDto, Long id) {
        initialModificationRequestService.paymentCallBackHandler(id, applicationNumberGenerationDto);
    }

    private void PetitionRecoveryPayment(ApplicationNumberGenerationDto applicationNumberGenerationDto, Long id) {
        petitionRecoveryRequestService.paymentCallBackHandler(id, applicationNumberGenerationDto);
    }

    private void changeAgentRequestHandler(ApplicationNumberGenerationDto applicationNumberGenerationDto, Long id) {
        applicationAgentFacade.startSupportServiceProcess(id, SupportServicePaymentStatus.PAID, null);
    }


    private void fileNewApplication(ApplicationNumberGenerationDto applicationNumberGenerationDto, Long id) {
        ApplicationInfo applicationInfo = applicationInfoRepository.getApplicationInfoWithMainApplicant(id);
        if (applicationInfo == null) {
            return;
        }

        if (isPatentApplication(applicationInfo)) {
            applicationInfoService.generateApplicationNumberPatent(id);
            applicationRelevantTypePaid(applicationInfo.getId());
            if (applicationInfo.getApplicationStatus().getCode().equals(ApplicationStatusEnum.WAITING_FOR_APPLICATION_FEE_PAYMENT.name())) {
                getCurrentTaskAndCompleteIt(applicationInfo, "UPDATE");
            } else {
                sendNotification(applicationInfo.getId(), applicationInfo.getFilingDate().toLocalDate());
            }
        } else {

            if (applicationInfo.getApplicationNumber() != null) {
                return;
            }

            BaseApplicationInfoService serviceByStringCode = applicationInfoService.getServiceByStringCode(applicationInfo.getCategory().getSaipCode());
            ApplicationInfo parialApp = null;
            if (Objects.nonNull(applicationInfo.getCategory()) &&
                    ApplicationCategoryEnum.INDUSTRIAL_DESIGN.toString().equals(applicationInfo.getCategory().getSaipCode().toString())
                    && Objects.nonNull(applicationInfo.getPartialApplication()) && applicationInfo.getPartialApplication()) {
                parialApp = applicationInfoService.getApplicationInfoByApplicationNumber(applicationInfo.getPartialApplicationNumber());
            }
            serviceByStringCode.paymentCallBackHandler(applicationNumberGenerationDto, applicationInfo, parialApp);

//            applicationInfoService.generateApplicationNumber(applicationNumberGenerationDto, applicationInfo);
//            StartProcessResponseDto startProcessResponseDto = applicationInfoService.startProcessConfig(applicationInfo);
//            activityLogService.insertFileNewApplicationActivityLogStatus(startProcessResponseDto.getTaskHistoryUIDto(), id, ApplicationStatusEnum.NEW.name());
//            applicationInfoService.updateApplicationWithProcessRequestId(startProcessResponseDto, id);
        }
    }

    private void generalNewApplication(ApplicationNumberGenerationDto applicationNumberGenerationDto, Long id) {
        ApplicationInfo applicationInfo = applicationInfoRepository.getApplicationInfoWithMainApplicant(id);
        if (applicationInfo == null || applicationInfo.getApplicationNumber() != null)
            return;
        BaseApplicationInfoService serviceByStringCode = applicationInfoService.getServiceByStringCode(applicationInfo.getCategory().getSaipCode());
        serviceByStringCode.paymentCallBackHandler(applicationNumberGenerationDto, applicationInfo, null);
    }

    private void sendNotification(Long applicationInfoId, LocalDate fillingDate) {
        String billNumber = getBillNumber(applicationInfoId, ApplicationPaymentMainRequestTypesEnum.FILE_NEW_APPLICATION.name());

        String periodInDays = bpmCallerFeignClient.getRequestTypeConfigValue("BILL_DURATION_OF_PATENT_PLT");


        // Calculate the remaining days
        LocalDate currentDate = LocalDate.now();
        LocalDate endDate = fillingDate.plusDays(Utilities.returnDays(periodInDays));
        long paymentRemainDays = ChronoUnit.DAYS.between(currentDate, endDate);


        Map<String, Object> notificationParams = new HashMap<>();
        notificationParams.put("id", applicationInfoId);
        notificationParams.put("billNumber", billNumber);
        notificationParams.put("remainPeriod", paymentRemainDays);

        ApplicationNotificationData notificationDate = applicationCustomerService.findapplicationNotificationData(applicationInfoId);
        String customerName = customerServiceFeignClient.getCustomerCodeByCustomerId(String.valueOf(notificationDate.getCustomerId())).getPayload();
        buildNotificationRequest(notificationDate.getEmail(), notificationDate.getMobileNumber(), applicationInfoId.toString(), ApplicationCategoryEnum.PATENT.name(), customerName, NotificationTemplateCode.PATENT_PAY_BEFORE_COMPLETE, notificationParams);
    }


    private void sendDepositCertificateNotificationRequest(Long certificateRequestId) {
        Long applicationId = certificateRequestService.findApplicationInfoIdById(certificateRequestId);
        ApplicationInfoDto applicationInfoDto = applicationInfoService.getApplication(applicationId);
        String billNumber = getBillNumber(applicationId, ApplicationPaymentMainRequestTypesEnum.DEPOSIT_CERTIFICATE.name());
        Map<String, Object> notificationParams = new HashMap<>();
        notificationParams.put("billNumber", billNumber);
        notificationParams.put("nameAr", applicationInfoDto.getTitleAr());
        notificationParams.put("link", contactUs);
        notificationParams.put("pageLink", portalLink);
        ApplicationNotificationData notificationDate = applicationCustomerService.findapplicationNotificationData(applicationId);
        String customerCode = customerServiceFeignClient.getCustomerCodeByCustomerId(String.valueOf(notificationDate.getCustomerId())).getPayload();
        buildNotificationRequest(notificationDate.getEmail(), notificationDate.getMobileNumber(), applicationId.toString(), ApplicationCategoryEnum.TRADEMARK.name(), customerCode, NotificationTemplateCode.DEPOSIT_CERTIFICATE, notificationParams);
    }

    private void sendTrademarkExactCopyCertificateNotificationRequest(Long certificateRequestId) {
        CertificateRequest certificateRequest = certificateRequestService.findById(certificateRequestId);
        ApplicationInfoDto applicationInfoDto = applicationInfoService.getApplication(certificateRequest.getApplicationInfo().getId());
        String billNumber = getBillNumber(applicationInfoDto.getId(), ApplicationPaymentMainRequestTypesEnum.EXACT_COPY.name());
        Map<String, Object> notificationParams = new HashMap<>();
        notificationParams.put("billNumber", billNumber);
        notificationParams.put("titleAr", applicationInfoDto.getTitleAr());
        notificationParams.put("link", contactUs);
        notificationParams.put("appNumber", applicationInfoDto.getApplicationNumber());
        notificationParams.put("regNumber", certificateRequest.getRequestNumber());
        ApplicationNotificationData notificationDate = applicationCustomerService.findapplicationNotificationData(applicationInfoDto.getId());
        String customerCode = customerServiceFeignClient.getCustomerCodeByCustomerId(String.valueOf(notificationDate.getCustomerId())).getPayload();
        buildNotificationRequest(notificationDate.getEmail(), notificationDate.getMobileNumber(), String.valueOf(applicationInfoDto.getId()), ApplicationCategoryEnum.TRADEMARK.name(), customerCode, NotificationTemplateCode.TRADEMARK_EXACT_COPY, notificationParams);
    }


    void sendTrademarkRenewalNotification(Long id, ApplicationNumberGenerationDto applicationNumberGenerationDto) {
        log.info(" Start Notification with id ==>> {} ", id);
        ApplicationInstallment applicationInstallment = applicationInstallmentService.findById(id);
        Long applicationId = applicationInstallment.getApplication().getId();

        ApplicationInfo applicationInfo = applicationInfoService.getById(applicationId).get();
        if (ApplicationCategoryEnum.TRADEMARK.name().equalsIgnoreCase(applicationInfo.getCategory().getSaipCode())
                && applicationNumberGenerationDto.getServiceCode().equalsIgnoreCase("installment-service-code") &&
                applicationNumberGenerationDto.getMainRequestType().equalsIgnoreCase(ApplicationPaymentMainRequestTypesEnum.INSTALLMENT_PROTECTION_RENEWAL.name())) {

            ApplicationNotificationData mainOwnerNotificationData = applicationCustomerService.findMainOwnerNotificationData(applicationId);

            sendNotificationToApplicationOwnerOrAgent(applicationId, applicationInfo, mainOwnerNotificationData);
            ApplicationSupportServicesType renewalSupportServiceRequest = applicationSupportServicesTypeRepository.getLastServiceForApplicationByServiceCode(applicationId, RENEWAL_FEES_PAY);
            if(renewalSupportServiceRequest != null){
                List<String> serviceAgentCustomerCodes = supportServiceCustomerService.getAgentsCustomerCodeByServiceId(renewalSupportServiceRequest.getId());
                if (Objects.nonNull(serviceAgentCustomerCodes) && !serviceAgentCustomerCodes.isEmpty()) {

                    CustomerSampleInfoDto agentInfo = getCustomerInfoByCodes(serviceAgentCustomerCodes.get(0));
                    if (Objects.nonNull(agentInfo)) {
                        sendNotificationToApplicationOwnerOrAgent(applicationId, applicationInfo, mapCustomerSampleInfoDtoIntoApplicationNotificationData(agentInfo));
                    }

                }
            }


        }
        log.info(" End Notification with id ==>> {} ", id);
    }
    private  CustomerSampleInfoDto getCustomerInfoByCodes(String customerCode) {
        CustomerCodeListDto customerCodeListDto = new CustomerCodeListDto();
        customerCodeListDto.setCustomerCode(List.of(customerCode));
        Map<String, CustomerSampleInfoDto> customerSampleInfoDtoMap = customerServiceCaller.getCustomerMapByListOfCode(customerCodeListDto);
        return customerSampleInfoDtoMap.containsKey(customerCode.toLowerCase()) ? customerSampleInfoDtoMap.get(customerCode.toLowerCase()): null;
    }
    private ApplicationNotificationData  mapCustomerSampleInfoDtoIntoApplicationNotificationData (CustomerSampleInfoDto dto){
        ApplicationNotificationData applicationNotificationData = new ApplicationNotificationData();
         applicationNotificationData.setEmail(dto.getEmail());
         applicationNotificationData.setMobileNumber(dto.getMobile());
         applicationNotificationData.setCustomerCode(dto.getCode());
         return applicationNotificationData;
    }
    private void sendNotificationToApplicationOwnerOrAgent(Long applicationId, ApplicationInfo applicationInfo, ApplicationNotificationData mainOwnerNotificationData) {
        String billNumber = getBillNumber(applicationId, ApplicationPaymentMainRequestTypesEnum.INSTALLMENT_PROTECTION_RENEWAL.name());
//            String customerCode = customerServiceFeignClient.getCustomerCodeByCustomerId(String.valueOf(mainOwnerNotificationData.getCustomerId())).getPayload();


        Map<String, Object> notificationParams = new HashMap<>();

        notificationParams.put("billNumber", billNumber);
        notificationParams.put("applicationName", applicationInfo.getTitleAr());
        notificationParams.put("appRequestNumber", applicationInfo.getApplicationRequestNumber());
        notificationParams.put("appNumber", applicationInfo.getApplicationNumber());
        notificationParams.put("portalLink", portalLink);
        notificationParams.put("publicationLink", publicationLink);
        buildNotificationRequest(mainOwnerNotificationData.getEmail(), mainOwnerNotificationData.getMobileNumber(), applicationId.toString(), ApplicationCategoryEnum.TRADEMARK.name(), mainOwnerNotificationData.getCustomerCode(), NotificationTemplateCode.TRADEMARK_RENEWED, notificationParams);
    }


    private void buildNotificationRequest(String email, String mobile, String rowId, String applicationCategory, String customerCode, NotificationTemplateCode notificationTemplateCode, Map<String, Object> notificationParams) {
        NotificationDto emailDto = NotificationDto
                .builder()
                .to(email)
                .messageType(Constants.MessageType.EMAIL_TYPE_MESSAGE)
                .build();
        NotificationDto smsDto = NotificationDto
                .builder()
                .to(mobile)
                .build();
        AppNotificationDto appDto = AppNotificationDto.builder()
                .rowId(rowId)
                .routing(applicationCategory)
                .date(LocalDateTime.now())
                .userNames(Arrays.asList(customerCode)).build();

        NotificationRequest notificationRequest = NotificationRequest.builder()
                .lang(NotificationLanguage.AR)
                .code(notificationTemplateCode)
                .templateParams(notificationParams)
                .email(emailDto)
                .sms(smsDto)
                .app(appDto)
                .build();
        notificationCaller.sendAllToSpecificUser(notificationRequest);
    }

    private String getBillNumber(Long applicationId, String applicationPaymentMainRequestTypesEnum) {
        return paymentFeeCostFeignClient.findLastBillByRequestTypeAndAppOrServiceId(
                applicationId,
                null,
                applicationPaymentMainRequestTypesEnum).getPayload();
    }


    private static boolean isPatentApplication(ApplicationInfo applicationInfo) {
        return applicationInfo != null && applicationInfo.getCategory() != null
                && applicationInfo.getCategory().getSaipCode().equals(ApplicationCategoryEnum.PATENT.name());
    }

    private void applicationRelevantTypePaid(Long appId) {
        List<ApplicationRelevantType> list = applicationInfoService.getApplicationRelevantTypes(appId);
        list.forEach(applicationRelevantType -> applicationRelevantType.setPaid(true));
        applicationRelevantTypeRepository.saveAll(list);

    }


    public void extendDueDate(ApplicationNumberGenerationDto applicationNumberGenerationDto, Long id) {
        extensionRequestService.paymentCallBackHandler(id, applicationNumberGenerationDto);
    }

    private void completePaymentTasks(String taskKey, Long appId, String supportServiceProcessRequestType) {
        CompletePaymentDto completePaymentDto = new CompletePaymentDto();
        completePaymentDto.setTaskKey(taskKey);
        completePaymentDto.setRowId(appId);
        getProcessRequestType(appId, supportServiceProcessRequestType, completePaymentDto);

        bpmCallerFeignClient.activatePaymentsAccount(completePaymentDto);
    }

    private void getProcessRequestType(Long appId, String supportServiceProcessRequestType, CompletePaymentDto completePaymentDto) {
        if (Objects.isNull(supportServiceProcessRequestType)) {
            completePaymentDto.setProcessRequestTypesCode(ApplicationCategoryEnum.valueOf(applicationInfoRepository.findCategoryByApplicationId(appId)).getProcessTypeCode());
        } else {
            completePaymentDto.setProcessRequestTypesCode(supportServiceProcessRequestType);
        }
    }


    private void oppositionRequest(ApplicationNumberGenerationDto applicationNumberGenerationDto, Long id) {

        //applicationInfoService.changeApplicationStatusId(id, ApplicationStatusEnum.OBJECTOR.name());


        Optional<Opposition> oppositionOptional = oppositionService.getById(id);
        if (oppositionOptional.isEmpty())
            return;

        Opposition opposition = oppositionOptional.get();
        if (opposition.getHearingSession() != null && opposition.getHearingSession().getIsPaid() != null && opposition.getHearingSession().getIsPaid())
            throw new BusinessException(Constants.ErrorKeys.GENERAL_ERROR_MESSAGE, HttpStatus.BAD_REQUEST, null);


        // customers applicant & complainer id in (728,674)
        oppositionService.paymentCallBackHandler(id, applicationNumberGenerationDto);


        ApplicationInfo applicationInfo = opposition.getApplication();
        ApplicantsDto applicantsDto = applicationInfoService.listMainApplicant(applicationInfo.getId());
        CustomerSampleInfoDto complainerDetails = customerServiceCaller.getAnyCustomerDetails(opposition.getComplainerCustomerId());

        // start the process
        ProcessRequestDto dto = new ProcessRequestDto();
        dto.setProcessId("application_opposition_process");

        Map<String, Object> vars = new HashMap<>();

        // set complainer details
        vars.put("fullNameAr", complainerDetails.getNameAr());
        vars.put("fullNameEn", complainerDetails.getNameEn());
        vars.put("email", complainerDetails.getEmail());
        vars.put("mobile", complainerDetails.getMobile());
        vars.put("COMPLAINER_USER_NAME", opposition.getCreatedByUser());

        // set app applicant details
        vars.put("APPLICANT_NAME_AR", applicantsDto.getNameAr());
        vars.put("APPLICANT_NAME_EN", applicantsDto.getNameEn());
        vars.put("APPLICANT_EMAIL", applicantsDto.getEmail());
        vars.put("APPLICANT_MOBILE", applicantsDto.getMobile());
        vars.put("APPLICANT_USER_NAME", applicantsDto.getUsername());

        // application
        vars.put("identifier", applicationInfo.getIpcNumber());
        vars.put("REQUESTS_APPLICATION_ID_COLUMN", String.valueOf(applicationInfo.getId()));
        vars.put("applicationCategory", applicationInfo.getCategory().getSaipCode());
        vars.put("APPLICATION_TITLE_AR", applicationInfo.getTitleAr());
        vars.put("APPLICATION_TITLE_EN", applicationInfo.getTitleEn());
        vars.put("APPLICATION_NUMBER", applicationInfo.getApplicationNumber());
        vars.put("customerCode", applicantsDto.getCustomerCode());


        // opposition
        vars.put("id", String.valueOf(id));
        vars.put("requestTypeCode", OPPOSITION_REQUEST.name());
        vars.put("OPPOSITION_NUMBER", id.toString()); // TILL WE KNOW HOW TO GENERATE IT

        // Opposition Legal Representative
        if (Objects.nonNull(opposition.getOppositionLegalRepresentative())) {
            vars.put("OPPOSITION_LEGAL_REPRESENTATIVE_NAME", opposition.getOppositionLegalRepresentative().getName());
            vars.put("OPPOSITION_LEGAL_REPRESENTATIVE_EMAIL", opposition.getOppositionLegalRepresentative().getEmail());
            vars.put("OPPOSITION_LEGAL_REPRESENTATIVE_PHONE", opposition.getOppositionLegalRepresentative().getPhone());
        }


        // get last head examiner and examiner to assign
        String headExaminerUserName = applicationUserService.getUsernameByAppAndRole(applicationInfo.getId(), ApplicationUserRoleEnum.HEAD_OF_EXAMINER);
        String examinerUsername = applicationUserService.getUsernameByAppAndRole(applicationInfo.getId(), ApplicationUserRoleEnum.EXAMINER);

        String headExaminerGroupName = "";
        String examinerGroupName = "";

        switch (ApplicationCategoryEnum.valueOf(applicationInfo.getCategory().getSaipCode())) {
            case PATENT:
                headExaminerGroupName = "PATENT_HEAD_OF_EXAMINER";
                examinerGroupName = "PATENT_EXAMINER";
                break;
            case INDUSTRIAL_DESIGN:
                headExaminerGroupName = "INDUSTRIAL_DESIGN_HEAD_OF_EXAMINER";
                examinerGroupName = "INDUSTRIAL_DESIGN_EXAMINER";
                break;
            case TRADEMARK:
                headExaminerGroupName = "TRADEMARK_HEAD_OF_EXAMINER";
                examinerGroupName = "TRADEMARK_EXAMINER";
                break;
            default:
                break;
        }

        vars.put("HEAD_EXAMINER_USER_NAME", headExaminerUserName);
        vars.put("HEAD_EXAMINER_GROUP", headExaminerGroupName);
        vars.put("EXAMINER_USER_NAME", examinerUsername);
        vars.put("EXAMINER_GROUP", examinerGroupName);

        dto.setVariables(vars);
        bpmCallerService.startApplicationProcess(dto);

    }


    private void processAppealRequestPayment(ApplicationNumberGenerationDto applicationNumberGenerationDto, Long id) {
        appealRequestService.processAppealRequestPayment(applicationNumberGenerationDto, id);
    }

    private void getCurrentTaskAndCompleteIt(ApplicationInfo applicationInfo, String approvedValue) {
        RequestTasksDto requestTasksDto = bpmCallerService.getTaskByRowIdAndType(RequestTypeEnum.valueOf(applicationInfo.getCategory().getSaipCode()), applicationInfo.getId()).getPayload();
        completeTask(requestTasksDto, approvedValue);
    }

    private void completeTask(RequestTasksDto requestTasksDto, String approvedValue) {
        CompleteTaskRequestDto completeTaskRequestDto = buildCompleteTaskRequestDto(approvedValue);
        bpmCallerService.completeTaskToUser(requestTasksDto.getTaskId(), completeTaskRequestDto);
    }

    private static CompleteTaskRequestDto buildCompleteTaskRequestDto(String approvedValue) {
        Map<String, Object> variables = buildTaskVariableMap(approvedValue);
        CompleteTaskRequestDto completeTaskRequestDto = new CompleteTaskRequestDto();
        completeTaskRequestDto.setVariables(variables);
        return completeTaskRequestDto;
    }

    private static Map<String, Object> buildTaskVariableMap(String approvedValue) {
        Map<String, Object> variables = new HashMap<>();
        Map<String, Object> completedPetitionRecovery = new HashMap<>();
        completedPetitionRecovery.put("value", approvedValue);
        variables.put("approved", completedPetitionRecovery);
        return variables;
    }


}
