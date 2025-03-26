package gov.saip.applicationservice.util;

import gov.saip.applicationservice.common.clients.rest.feigns.BPMCallerFeignClient;
import gov.saip.applicationservice.common.clients.rest.feigns.CustomerConfigParameterClient;
import gov.saip.applicationservice.common.dto.CustomerSampleInfoDto;
import gov.saip.applicationservice.common.dto.DurationDto;
import gov.saip.applicationservice.common.dto.RequestTasksDto;
import gov.saip.applicationservice.common.enums.*;
import gov.saip.applicationservice.common.enums.installment.InstallmentStatus;
import gov.saip.applicationservice.common.facade.CustomerFacade;
import gov.saip.applicationservice.common.model.*;
import gov.saip.applicationservice.common.repository.ApplicationRelevantTypeRepository;
import gov.saip.applicationservice.common.repository.ChangeOwnershipRequestRepository;
import gov.saip.applicationservice.common.repository.PetitionRecoveryRequestRepository;
import gov.saip.applicationservice.common.repository.SupportServiceRequestRepository;
import gov.saip.applicationservice.common.service.*;
import gov.saip.applicationservice.common.service.agency.ApplicationAgentService;
import gov.saip.applicationservice.common.service.agency.SupportServiceCustomerService;
import gov.saip.applicationservice.common.service.agency.TrademarkAgencyRequestService;
import gov.saip.applicationservice.common.service.appeal.TrademarkAppealRequestService;
import gov.saip.applicationservice.common.service.impl.BPMCallerServiceImpl;
import gov.saip.applicationservice.common.service.installment.ApplicationInstallmentService;
import gov.saip.applicationservice.common.service.installment.impl.ApplicationInstallmentServiceImpl;
import gov.saip.applicationservice.common.service.opposition.OppositionRequestService;
import gov.saip.applicationservice.common.service.trademark.TrademarkDetailService;
import gov.saip.applicationservice.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static gov.saip.applicationservice.common.enums.ApplicationCategoryEnum.*;
import static gov.saip.applicationservice.common.enums.ApplicationCustomerType.AGENT;
import static gov.saip.applicationservice.common.enums.ApplicationCustomerType.MAIN_OWNER;
import static gov.saip.applicationservice.common.enums.ApplicationStatusEnum.*;
import static gov.saip.applicationservice.common.enums.SupportServiceActors.ALL;
import static gov.saip.applicationservice.common.enums.SupportServiceType.*;
import static gov.saip.applicationservice.util.Constants.ErrorKeys.*;
import static gov.saip.applicationservice.util.Constants.PASSED;
import static gov.saip.applicationservice.util.Constants.SERVICES_CAN_BE_APPLIED_BY_MANY_CUSTOMER_IN_THE_SAME_TIME;
import static gov.saip.applicationservice.util.Constants.SupportServiceValidationMapKeys.PARENT_SERVICE_VALID_STATUSES;

@Slf4j
@Component
@AllArgsConstructor
public class SupportServiceValidator {

    private final ConfigParameterService configParameterService;
    private final ApplicationInstallmentService applicationInstallmentService;
    private final ApplicationAgentService applicationAgentService;
    private final CustomerServiceCaller customerServiceCaller;
    private final ChangeOwnershipRequestRepository changeOwnershipRequestRepository;

    @Lazy
    @Autowired
    private ApplicationUserService applicationUserService;
    private final BPMCallerServiceImpl bpmCallerService;
    private final CustomerConfigParameterClient customerConfigParameterClient;
    private final ApplicationInfoService applicationInfoService;
    private final BPMCallerFeignClient bpmCallerFeignClient;
    private final PetitionRecoveryRequestRepository recoveryRequestRepository;
    private final SupportServiceRequestRepository supportServiceRequestRepository;
    private final TrademarkAgencyRequestService trademarkAgencyRequestService;
    private final CustomerFacade applicationCustomerFacade;
    private final TrademarkDetailService trademarkDetailService;
    private final ApplicationSupportServicesTypeService applicationSupportServicesTypeService;
    private final SupportServiceCustomerService supportServiceCustomerService;
    private final TrademarkAppealRequestService trademarkAppealRequestService;
    private final OppositionRequestService oppositionRequestService;
    private final LKSupportServiceTypeService supportServiceTypeService;
    public static final String REVOKED_STATUS_PREFIX = "REVOKED";
    @Lazy
    @Autowired
    private LicenceRequestService licenceRequestService;
    @Lazy
    @Autowired
    private RevokeLicenceRequestService revokeLicenceRequestService;
    @Lazy
    @Autowired
    private OppositionRevokeLicenceRequestService oppositionRevokeLicenceRequestService;
    @Autowired
    private ApplicationRelevantTypeRepository applicationRelevantTypeRepository;

    public String validateSupportServicePreConditions(ApplicationInfo app, String supportServiceCode, Long parentServiceId) {
            switch (SupportServiceType.valueOf(supportServiceCode)) {
            case RENEWAL_FEES_PAY:
                  return validateRenewalFeesPreConditions(app);
            case INITIAL_MODIFICATION:
                return validateInitialModificationPreConditions(app);
            case ANNUAL_FEES_PAY:
                return validateAnnualFeesPreConditions(app);
            case APPEAL_REQUEST:
                return validateAppealRequestPreConditions(app);
            case EXTENSION:
                return validateExtensionPreConditions(app);
            case REVOKE_LICENSE_REQUEST:
            case OPPOSITION_REVOKE_LICENCE_REQUEST:
                return validateSupportServiceBasicPreConditions(app, SupportServiceType.valueOf(supportServiceCode), parentServiceId);
            case TRADEMARK_APPEAL_REQUEST:
                trademarkAppealRequestService.getOpenedAppealTaskInMainProcess(app.getId(), parentServiceId);
                return validateSupportServiceBasicPreConditions(app, SupportServiceType.valueOf(supportServiceCode), parentServiceId);
            case OPPOSITION_REQUEST:
                return validateOppositionPreConditions(app);
            case PETITION_RECOVERY:
                return validatePetitionRecoveryPreConditions(app);
            case PROTECTION_PERIOD_EXTENSION_REQUEST:
            case LICENSING_MODIFICATION:
                return validateLicensingModificationPreConditions(app , parentServiceId);
            case RETRACTION:
                validateRetractionPreConditions(app);
                case REVOKE_BY_COURT_ORDER:
            case VOLUNTARY_REVOKE:
            case REVOKE_PRODUCTS:
            case OWNERSHIP_CHANGE:
            case EVICTION:
            case LICENSING_REGISTRATION:
                return validateSupportServiceBasicPreConditions(app, SupportServiceType.valueOf(supportServiceCode));
            case EDIT_TRADEMARK_NAME_ADDRESS:
                return validateTrademarkEditNameAddress(app);
            case EDIT_TRADEMARK_IMAGE:
                return validateTrademarkEditImage(app);
            case PATENT_PRIORITY_REQUEST:
                return validatePriorityRequestPreConditions(app);
            case PATENT_PRIORITY_MODIFY:
                return validatePriorityModifyPreConditions(app);
//            case AGENT_SUBSTITUTION:
//                return validationAgentSubstitutionPreConditions(app);

            default:
                return PASSED;
        }
    }

//    private String validationAgentSubstitutionPreConditions(ApplicationInfo applicationInfo) {
//        validateIfApplicationHasAgent(applicationInfo);
//        return PASSED;
//    }
//
//    private void validateIfApplicationHasAgent(ApplicationInfo applicationInfo) {
//        ApplicationAgent applicationAgent = applicationAgentService.getCurrentApplicationAgentEntity(applicationInfo.getId());
//        if(applicationAgent == null) {
//            throw new BusinessException(APPLICATION_HAS_NO_AGENT, HttpStatus.BAD_REQUEST, null);
//        }
//    }
    
    private String validateTrademarkEditImage(ApplicationInfo applicationInfo) {
        validateIsImage(applicationInfo.getId());
        validateSupportServiceBasicPreConditions(applicationInfo, EDIT_TRADEMARK_IMAGE);
        return PASSED;
    }

    private String validateTrademarkEditNameAddress(ApplicationInfo applicationInfo) {
        validateUserAccess(applicationInfo);
        validateSupportServiceBasicPreConditions(applicationInfo, EDIT_TRADEMARK_NAME_ADDRESS);
        return PASSED;
    }
    
    private void validateIsImage(Long appId) {
        boolean isImg = trademarkDetailService.isImage(appId);
        if (!isImg)
            throw new BusinessException(Constants.ErrorKeys.GENERAL_ERROR_MESSAGE, HttpStatus.BAD_REQUEST, null);
    }
    
    private void validateUserAccess(ApplicationInfo applicationInfo) {
        CustomerSampleInfoDto customerInfo = applicationCustomerFacade.getCustomerInfo(applicationInfo.getId(), MAIN_OWNER);
        List<String> excludedUsers = Constants.USERS_GROUPS_HAVENT_ACCESS_TO_EDIT_NAME_ADDRESS_AS_MAIN_OWNER;

        if (isUserExcluded(customerInfo, excludedUsers, Utilities.getCustomerIdFromHeadersAsLong())) {
            throw new BusinessException(Constants.ErrorKeys.YOU_DONT_HAVE_ACCESS_IN_THIS_SUPPORT_SERVICE, HttpStatus.BAD_REQUEST, null);
        }
    }
    
    private boolean isUserExcluded(CustomerSampleInfoDto customerInfo, List<String> excludedUsers, Long customerId) {
        return excludedUsers.contains(customerInfo.getUserGroupCode().name()) && customerId.equals(customerInfo.getId());
    }
    
    private String validatePetitionRecoveryPreConditions(ApplicationInfo app) {
        log.info("start validatePetitionRecoveryPreConditions id  =  " + app.getId() );
        validateIfApplicationHasRejectedRequest(app.getId());
        validateSupportServiceBasicPreConditions(app, PETITION_RECOVERY);
        validateTaskDueDatePetitionRecovery(app, PETITION_RECOVERY);
        log.info("result  validatePetitionRecoveryPreConditions    =  " +  PASSED  );
        return PASSED;
    }

    private void validateTaskDueDatePetitionRecovery(ApplicationInfo app, SupportServiceType supportServiceType) {
        log.info("start validateTaskDueDatePetitionRecovery id  =  " + app.getId()  + " supportServiceType: " + supportServiceType.name());
        RequestTasksDto requestTasksDto = getRequestTasksDtoWithValidationfromHistory(app);
        log.info("res  validateTaskDueDatePetitionRecovery    " + requestTasksDto);
        if(requestTasksDto != null ){
            log.info("res  validateTaskDueDatePetitionRecovery    " + requestTasksDto.getDue()   + " " + requestTasksDto.getTaskId() );
        }
        if (isPassedTaskDueDate(requestTasksDto, supportServiceType)) {
            String[] param = {app.getApplicationNumber(), supportServiceType.toString()};
            throw new BusinessException(APPLICATION_NOT_MEET_SERVICE_CRITERIA, HttpStatus.BAD_REQUEST, param);
        }
    }




    private void validateIfApplicationHasRejectedRequest(Long appId) {
        List<PetitionRecoveryRequest> allByApplicationId = recoveryRequestRepository.findByApplicationInfoIdAndLkSupportServicesCodeOrderByCreatedDateDesc(appId, PETITION_RECOVERY);
        if(!allByApplicationId.isEmpty() && allByApplicationId.get(0).getRequestStatus().getCode().equals("PETITION_RECOVERY_REJECTION")){
            throw new BusinessException(Constants.ErrorKeys.SSR_REJECTED,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String validateOppositionPreConditions(ApplicationInfo app) {
        validateSupportServiceBasicPreConditions(app, OPPOSITION_REQUEST);
        checkApplicationOwnerShip(app.getId());
        return PASSED;
    }
    private String validateLicensingModificationPreConditions(ApplicationInfo app, Long parentServiceId) {
        validateSupportServiceBasicPreConditions(app, LICENSING_MODIFICATION);
        checkApplicationLicensed(app.getId());
        checkApplicationCancelLicensed(parentServiceId);
        return PASSED;
    }

    private boolean checkAppliedOwnerShipLicenceTransfer(Long id) {
        List<ApplicationSupportServicesType> supportServiceIds = supportServiceRequestRepository.getChangeOwnerShipServiceId(id);
        for (ApplicationSupportServicesType supportServicerviceId : supportServiceIds) {
            Optional<ChangeOwnershipRequest> changeOwnerShip = changeOwnershipRequestRepository.findById(supportServicerviceId.getId());
            if (changeOwnerShip.get().getChangeOwnerShipType().equals(ChangeOwnershipTypeEnum.LICENSE_TRANSFER))
                if (!changeOwnerShip.get().getCustomerId().equals(Utilities.getCustomerIdFromHeadersAsLong())) {
                    //throw new BusinessException(SUPPORT_SERVICE_SEARCH_APPLICATION_VALIDATION_SUPPORT_SERVICE_ACTOR_DEFAULT, HttpStatus.INTERNAL_SERVER_ERROR);
                    return false;
                }
        }
        return true;

    }

    private void validateRetractionTypes(Long appId){
        List<LKSupportServiceType> lkSupportServiceTypes = supportServiceTypeService.getAllByRequestType(RETRACTION.name(), appId);
        if(lkSupportServiceTypes.isEmpty())
            throw new BusinessException(Constants.ErrorKeys.EXCEPTION_CANNOT_APPLY_FOR_RETRACTION_SERVICE, HttpStatus.NOT_FOUND);
    }

    private String validateRetractionPreConditions(ApplicationInfo app) {
        validateSupportServiceBasicPreConditions(app, RETRACTION);
        validateRetractionTypes(app.getId());
        return PASSED;
    }

    void checkApplicationOwnerShip(Long appId) {
        String creatorCustomerCode = getCreatorCustomerCode();
        checkIfCreatorCodeEqualMainApplicantCode(appId, creatorCustomerCode);
    }
    void checkApplicationLicensed(Long appId) {
        boolean checkApplicationHaveLicence = applicationSupportServicesTypeService.applicationSupportServicesTypeLicencedExists(appId);
        if(!checkApplicationHaveLicence)
            throw new BusinessException(Constants.ErrorKeys.LICENSE_NUMBER_NOT_FOUND, HttpStatus.NOT_FOUND);
    }
    void checkApplicationCancelLicensed(Long serviceId) {
        boolean checkApplicationHaveLicence = licenceRequestService.checkApplicationCancelLicence(serviceId);
        if(checkApplicationHaveLicence)
            throw new BusinessException(Constants.ErrorKeys.LICENSE_NUMBER_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    private void checkIfCreatorCodeEqualMainApplicantCode(Long appId, String creatorCustomerCode) {
        if (Objects.nonNull(creatorCustomerCode)) {
            boolean isMainApplicant = applicationInfoService.listMainApplicant(appId).getIdentifier().equals(creatorCustomerCode);
            if (isMainApplicant == true) {
                throw new BusinessException("EXCEPTION_CANNOT_APPLY_FOR_APPLICATION", HttpStatus.BAD_REQUEST);
            }
        }
    }

    private String getCreatorCustomerCode() {
        return Utilities.getCustomerCodeFromHeaders() == null ? null : Utilities.getCustomerCodeFromHeaders();
    }


    private void validateTaskDueDate(ApplicationInfo app, SupportServiceType supportServiceType) {
        RequestTasksDto requestTasksDto = getRequestTasksDtoWithValidation(app);
        if (isPassedTaskDueDate(requestTasksDto, supportServiceType)) {
            String[] param = {app.getApplicationNumber(), supportServiceType.toString()};
            throw new BusinessException(APPLICATION_NOT_MEET_SERVICE_CRITERIA, HttpStatus.BAD_REQUEST, param);
        }
    }

    private boolean isPassedTaskDueDate(RequestTasksDto requestTasksDto, SupportServiceType supportServiceType) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime taskDueDate = parseDateTime(requestTasksDto.getDue());

        log.info( "isPassedTaskDueDate : currentDateTime:  " +currentDateTime + " ,  taskDueDate : " + taskDueDate + "  ,  calculateDuration :  " +   calculateDuration(currentDateTime, taskDueDate).toDays()  +
                " , supportServiceType : "+supportServiceType.name() + " DURATION_OF_PETENTION  " + Long.valueOf(customerConfigParameterClient.getConfig("DURATION_OF_PETENTION").getPayload().getValue()) );

        switch (supportServiceType) {
            case PETITION_RECOVERY:
                return calculateDuration(currentDateTime, taskDueDate).toDays() > Long.valueOf(customerConfigParameterClient.getConfig("DURATION_OF_PETENTION").getPayload().getValue());
            case EXTENSION:
                return currentDateTime.isAfter(taskDueDate);
            case APPEAL_REQUEST:
                return !requestTasksDto.getTaskDefinitionKey().contains("PENDING_FOR_APPEAL_REQUEST") || calculateDuration(currentDateTime, taskDueDate).toDays() <= 0;
            default:
                return false;
        }
    }

    private RequestTasksDto getRequestTasksDtoWithValidationfromHistory(ApplicationInfo app) {
        RequestTasksDto requestTasksDto = bpmCallerService.getTaskByRowIdAndTypefromHistory(getRequestTypeEnum(app.getCategory().getSaipCode()), app.getId()).getPayload();
        if (Objects.isNull(requestTasksDto) || Objects.isNull(requestTasksDto.getDue())) {
            String[] param = {app.getApplicationNumber(), SupportServiceType.PETITION_RECOVERY.toString()};
            throw new BusinessException(APPLICATION_NOT_MEET_SERVICE_CRITERIA, HttpStatus.BAD_REQUEST, param);
        }
        return requestTasksDto;
    }

    private RequestTasksDto getRequestTasksDtoWithValidation(ApplicationInfo app) {
        RequestTasksDto requestTasksDto = bpmCallerService.getTaskByRowIdAndType(getRequestTypeEnum(app.getCategory().getSaipCode()), app.getId()).getPayload();
        if (Objects.isNull(requestTasksDto) || Objects.isNull(requestTasksDto.getDue())) {
            String[] param = {app.getApplicationNumber(), SupportServiceType.PETITION_RECOVERY.toString()};
            throw new BusinessException(APPLICATION_NOT_MEET_SERVICE_CRITERIA, HttpStatus.BAD_REQUEST, param);
        }
        return requestTasksDto;
    }

    private RequestTypeEnum getRequestTypeEnum(String code) {

        if (code.equals("PATENT"))
            return RequestTypeEnum.PATENT;
        if (code.equals("TRADEMARK"))
            return RequestTypeEnum.TRADEMARK;
        if (code.equals("INDUSTRIAL_DESIGN"))
            return RequestTypeEnum.INDUSTRIAL_DESIGN;
        return null;
    }

    private LocalDateTime parseDateTime(String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        return LocalDateTime.parse(dateTimeString, formatter);
    }

    private Duration calculateDuration(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return Duration.between(startDateTime, endDateTime); // end - start
    }

    private String validateExtensionPreConditions(ApplicationInfo app) {
        validateSupportServiceBasicPreConditions(app, EXTENSION);
        validateTaskDueDate(app, EXTENSION);
        return PASSED;
    }

    private String validateSupportServiceBasicPreConditions(ApplicationInfo app, SupportServiceType SupportServiceType) {
        if(SupportServiceType.equals(REVOKE_BY_COURT_ORDER)){
            validateRevokeByCourtByAgent(app);
        }
        validateApplicationHasAnySupportServiceTypePermittedCategoryAndStatus(app, SupportServiceType, null);
        return PASSED;
    }

    private static void validateRevokeByCourtByAgent(ApplicationInfo app) {
        List<String> applicationCustomerCodes = app.getApplicationCustomers().stream().map(ApplicationCustomer::getCustomerCode).collect(Collectors.toList());
        String supportServiceRequestApplicantCustomerCode = Utilities.getCustomerCodeFromHeaders();
        if(supportServiceRequestApplicantCustomerCode != null && applicationCustomerCodes.contains(supportServiceRequestApplicantCustomerCode))
            throw new BusinessException(SUPPORT_SERVICE_SEARCH_APPLICATION_VALIDATION_SUPPORT_SERVICE_ACTOR_DEFAULT, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String validateSupportServiceBasicPreConditions(ApplicationInfo app, SupportServiceType SupportServiceType, Long parentServiceId) {
        validateApplicationHasAnySupportServiceTypePermittedCategoryAndStatus(app, SupportServiceType, parentServiceId);
        return PASSED;
    }

    private String validateInitialModificationPreConditions(ApplicationInfo applicationInfo) {
        validateSupportServiceBasicPreConditions(applicationInfo, INITIAL_MODIFICATION);
        validateApplicationDoesntHaveHeadOfCheckerUserRole(applicationInfo.getId());
        return PASSED;
    }

    private String validatePriorityRequestPreConditions(ApplicationInfo applicationInfo){
        /*check status, owner, category*/
        validateSupportServiceBasicPreConditions(applicationInfo, PATENT_PRIORITY_REQUEST);
        validatePriorityRequest(applicationInfo,PATENT_PRIORITY_REQUEST);
        return PASSED;
    }

    private String validatePriorityModifyPreConditions(ApplicationInfo applicationInfo){
        /*check status, owner, category*/
        validateSupportServiceBasicPreConditions(applicationInfo, PATENT_PRIORITY_MODIFY);
        validatePriorityRequest(applicationInfo,PATENT_PRIORITY_MODIFY);
        validatePriorityModifyRequest(applicationInfo);
        return PASSED;
    }

    private void validatePriorityModifyRequest(ApplicationInfo applicationInfo) {
        /*
         * check on SSR status
         * if status is accepted then check allowance days
         * */
        Map<String, Object> supportServiceRequest = getPrioritySupportServiceStatus(applicationInfo.getId(), PATENT_PRIORITY_REQUEST);
        handleSupportServiceRequestStatus(supportServiceRequest, PATENT_PRIORITY_REQUEST);
        validatePreviousPriorityModifyRequestStatus(applicationInfo);
    }

    private void validatePreviousPriorityModifyRequestStatus(ApplicationInfo applicationInfo) {
        Map<String, Object> supportServiceRequest = getPrioritySupportServiceStatus(applicationInfo.getId(), PATENT_PRIORITY_MODIFY);
        handleSupportServiceRequestStatus(supportServiceRequest, PATENT_PRIORITY_MODIFY);
    }

    private void handleSupportServiceRequestStatus(Map<String, Object> supportServiceRequest, SupportServiceType supportServiceType) {
        Object requestStatusCodeObj = supportServiceRequest.get("requestStatusCode");
        if (requestStatusCodeObj != null) {
            String requestStatusCode = requestStatusCodeObj.toString();
            SupportServiceRequestStatusEnum statusEnum = SupportServiceRequestStatusEnum.valueOf(requestStatusCode);
            switch (statusEnum) {
                case ACCEPTED:
                    validateAllowanceDaysNotExpired(supportServiceRequest);
                    break;
                case UNDER_PROCEDURE:
                    throw new BusinessException(SSR_UNDER_REVIEW, HttpStatus.BAD_REQUEST, null);
                case REJECTED:
                case CONDITIONAL_REJECTION:
                    throw new BusinessException(supportServiceType.equals(SupportServiceType.PATENT_PRIORITY_REQUEST) ? SSR_REJECTED : SSR_MODIFICATION_REJECTED, HttpStatus.BAD_REQUEST, null);
                default:
                    throw new BusinessException(REQUEST_STATUS_IS_NOT_VALID, HttpStatus.BAD_REQUEST, null);
            }
        }
    }


    private void validateAllowanceDaysNotExpired(Map<String, Object> supportServiceRequest){
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime acceptanceDate = (LocalDateTime) supportServiceRequest.get("decisionDate");
        if(currentDate.isAfter(acceptanceDate.plus(Long.valueOf(bpmCallerFeignClient.getRequestTypeConfigValue("PMR_ALLOWANCE_DAYS_AFTER_PPR_ACCEPTANCE")), ChronoUnit.DAYS))){
            throw new BusinessException(TIME_OUT, HttpStatus.BAD_REQUEST, null);
        }
    }

    private Map<String, Object> getPrioritySupportServiceStatus(Long appId, SupportServiceType supportServiceCode){
        Map<String, Object> supportService = supportServiceRequestRepository.getLastSupportServiceRequestStatusAndModifiedDate(appId, supportServiceCode);
        if(supportService != null){
            return supportService;
        }
        else{
            throw new BusinessException(SSR_NOT_FOUND, HttpStatus.BAD_REQUEST, null);
        }
    }

    private void validatePriorityRequest(ApplicationInfo applicationInfo,SupportServiceType SupportServiceType){
        /*
         * check priority normal allownce time
         * check sysdate - priority expiration date <= 2 Months
         * check priority expiration date - application filling date = 2 Months
         * */
        if(applicationUserService.checkApplicationHasSpecificUserRole(applicationInfo.getId(), ApplicationUserRoleEnum.EXAMINER)){
            throw new BusinessException(SSR_STATUS_NOT_VALID, HttpStatus.BAD_REQUEST, null);
        }
        if (Objects.nonNull(applicationInfo.getFilingDate())) {

            ApplicationAccelerated applicationAccelerated = applicationInfo.getApplicationAccelerated();
            Long priorityRequestServiceId = applicationSupportServicesTypeService.getLastSupportServiceByTypeAndApplicationِAndStatus(PATENT_PRIORITY_REQUEST, applicationInfo.getId(), List.of(SupportServiceRequestStatusEnum.ACCEPTED.name()));
            if((applicationAccelerated != null && !applicationAccelerated.getPphExamination()) || (priorityRequestServiceId == null&&SupportServiceType.equals(PATENT_PRIORITY_MODIFY))){
                throw new BusinessException(APPLICATION_STATUS_IS_NOT_VALID, HttpStatus.BAD_REQUEST, null);
            }

            LocalDateTime currentDate = LocalDateTime.now();
            LocalDateTime expiry = applicationInfo.getFilingDate();
            expiry = expiry.plus(configParameterService.getLongByKey("PRIORITY_DOCUMENTS_MAX_ALLOWANCE_DAYS"), ChronoUnit.DAYS);
            DurationDto duration = Utilities.calculateDuration(LocalDateTime.now(), expiry);
            Long supportServiceNumber = supportServiceRequestRepository.countServicesByApplicationId(applicationInfo.getId());

            if(duration.getDays() > 0 || duration.getHours() > 0){
                throw new BusinessException(ALLOWANCE_DAYS_FOUND, HttpStatus.BAD_REQUEST, null);
            }

            if(currentDate.isAfter(expiry.plus(configParameterService.getLongByKey("PRIORITY_PETITION_REQUEST_ALLOWANCE_DAYS"), ChronoUnit.DAYS))){
                throw new BusinessException(TIME_OUT, HttpStatus.BAD_REQUEST, null);
            }
        }else{
            throw new BusinessException(FILLING_DATE_NOT_FOUND, HttpStatus.BAD_REQUEST, null);
        }
    }

    private void validateApplicationHasNotSupportServicesInProcedures(Long appId, SupportServiceType supportServiceType, Long parentServiceId) {
        if (SupportServiceType.isServicePermittedToCreateWithOtherServices(supportServiceType)) {
            validateIfCurrentCustomerHasUnderProcedureRequest(appId);
            return;
        }

        boolean applicationHasUnderProcedureRequest = isApplicationOrParentServiceHasUnderProcedureRequest(appId, supportServiceType, parentServiceId);

        if (applicationHasUnderProcedureRequest)
            throw new BusinessException(Constants.ErrorKeys.APPLICATION_HAS_UNDER_PROCEDURE_REQUEST,
                    HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void validateIfCurrentCustomerHasUnderProcedureRequest(Long appId) {
        String currentApplicantCustomerCode = Utilities.getCustomerCodeFromHeaders();
        boolean thereUnderProcedureRequestsForCurrentApplicant = supportServiceRequestRepository.isThereUnderProcedureRequestsForCurrentApplicant(appId,
                Constants.VALIDATE_SUPPORT_SERVICE_REQUEST_STATUSES,
                currentApplicantCustomerCode,
                SERVICES_CAN_BE_APPLIED_BY_MANY_CUSTOMER_IN_THE_SAME_TIME);
        if(thereUnderProcedureRequestsForCurrentApplicant){
            throw new BusinessException(Constants.ErrorKeys.APPLICATION_HAS_UNDER_PROCEDURE_REQUEST, HttpStatus.NOT_FOUND);
        }
    }

    private boolean isApplicationOrParentServiceHasUnderProcedureRequest(Long appId, SupportServiceType supportServiceType, Long parentServiceId) {
        boolean applicationHasUnderProcedureRequest = false;
        if (supportServiceType == OPPOSITION_REVOKE_LICENCE_REQUEST) {
            applicationHasUnderProcedureRequest = oppositionRevokeLicenceRequestService.checkRevokeLicenseRequestHasUnderProcedureOppositionRevokeLicenseRequest(parentServiceId);
        } else {
            applicationHasUnderProcedureRequest = supportServiceRequestRepository.checkApplicationHasAnotherUnderProcedureRequest(appId, Constants.VALIDATE_SUPPORT_SERVICE_REQUEST_STATUSES,
                    Constants.SERVICES_EXCLUDED_FROM_SUPPORT_SERVICES_UNDER_PROCEDURE);
        }
        return applicationHasUnderProcedureRequest;
    }
    private String validateAnnualFeesPreConditions(ApplicationInfo applicationInfo) {
        validateSupportServiceBasicPreConditions(applicationInfo, ANNUAL_FEES_PAY);
        if (!ApplicationInstallmentServiceImpl.IGNORE_ANNUAL_MONTHS_VALIDATION) {
            checkAnnualFeesPayConditions(applicationInfo);
        }
        return PASSED;
    }
    private String validateRenewalFeesPreConditions(ApplicationInfo applicationInfo) {
        validateSupportServiceBasicPreConditions(applicationInfo, RENEWAL_FEES_PAY);
        applicationInstallmentService.isRenewalOpenedForGivenApplication(applicationInfo);
        return PASSED;
    }

    private String checkAnnualFeesPayConditions(ApplicationInfo app) {
        LocalDateTime currentDate = LocalDateTime.now();

        LocalDateTime appFilingDate = app.getFilingDate();

        LocalDateTime startRange = LocalDateTime.of(currentDate.getYear(),
                Month.JANUARY, 1, 0, 0);

        LocalDateTime endRange = LocalDateTime.of(currentDate.getYear(),
                Month.MARCH, 30, 23, 59, 59);

        long differenceInYears = ChronoUnit.YEARS.between(appFilingDate, currentDate);

        long differenceInDays = ChronoUnit.DAYS.between(appFilingDate, currentDate);

        long differenceInHours = ChronoUnit.HOURS.between(appFilingDate, currentDate);

        if (differenceInYears >= 1 && currentDate.isAfter(startRange) && currentDate.isBefore(endRange)) {

            return "Passed";
        }
        if (differenceInYears < 1) {
            String[] params = {String.valueOf(differenceInDays), String.valueOf(differenceInHours)};

            throw new BusinessException("ANNUAL_VALIDATION_MESSAGE", HttpStatus.BAD_REQUEST, params);
        }
        throw new BusinessException("system error in filing date supported with wrong value");

    }

    private String validateAppealRequestPreConditions(ApplicationInfo applicationInfo) {
        validateSupportServiceBasicPreConditions(applicationInfo, APPEAL_REQUEST);
        validateTaskDueDate(applicationInfo, APPEAL_REQUEST);
        return PASSED;
    }


    private void validateApplicationHasAnySupportServiceTypePermittedCategoryAndStatus(ApplicationInfo applicationInfo, SupportServiceType SupportServiceType, Long parentServiceId) {
            Map<String, Map<String, List<String>>> supportServiceCodesApplicationCategoriesStatus =
                    Constants.SupportServicesApplicationListing.supportServiceCodesApplicationCategoriesStatus;
            validateApplicationHasNotSupportServicesInProcedures(applicationInfo.getId() , SupportServiceType, parentServiceId);
            isSupportServiceApplicantOneOfActors(applicationInfo, SupportServiceType, supportServiceCodesApplicationCategoriesStatus, parentServiceId);
            validateApplicationHasAnySupportServiceTypePermittedCategory(applicationInfo, SupportServiceType, supportServiceCodesApplicationCategoriesStatus);
            validateStatusForRequestedService(applicationInfo, SupportServiceType, parentServiceId, supportServiceCodesApplicationCategoriesStatus);
    }

    public Boolean validateAppealButtonOnPreviousSupportServiceList(Long parentSupportServiceID, Long applicationId, SupportServiceType SupportServiceType) {
        Boolean appealButtonActivation = true;
        RequestTypeEnum type =  RequestTypeEnum.EDIT_TRADEMARK_IMAGE ;
        RequestTasksDto taskByRowIdAndTypeIfExists = bpmCallerService.getTaskByRowIdAndTypeIfExists(type, parentSupportServiceID);
        if (taskByRowIdAndTypeIfExists == null || !taskByRowIdAndTypeIfExists.getTaskDefinitionKey().equals("APPLICANT_PENDING_FOR_APPEAL_REQUEST_AETIM"))
            return false;
        Map<String, Map<String, List<String>>> supportServiceCodesApplicationCategoriesStatus =
                Constants.SupportServicesApplicationListing.supportServiceCodesApplicationCategoriesStatus;
        if( isApplicationOrParentServiceHasUnderProcedureRequest(applicationId, TRADEMARK_APPEAL_REQUEST, parentSupportServiceID)) return false;
        String parentServiceCurrentStatusCode = supportServiceRequestRepository.getSupportServiceStatusCodeByServiceId(parentSupportServiceID);
        String applicationStatus = applicationSupportServicesTypeService.getSupportServiceTypeById(parentSupportServiceID).getApplicationInfo().getApplicationStatus().getCode();
        List<String> validCodes = supportServiceCodesApplicationCategoriesStatus.get(TRADEMARK_APPEAL_REQUEST.name()).get(PARENT_SERVICE_VALID_STATUSES.name());
        if (validCodes != null && !validCodes.contains(parentServiceCurrentStatusCode) || applicationStatus.contains(REVOKED_STATUS_PREFIX))
            appealButtonActivation = false;
        return appealButtonActivation;
    }

    private void validateStatusForRequestedService(ApplicationInfo applicationInfo, SupportServiceType SupportServiceType, Long parentServiceId, Map<String, Map<String, List<String>>> supportServiceCodesApplicationCategoriesStatus) {
        if(parentServiceId != null) { // validate support services status
            validateParentServiceInValidStatus(parentServiceId, SupportServiceType, supportServiceCodesApplicationCategoriesStatus);
        } else { // validate application info status
            List<String> statuses = getSupportServiceAlLowedStatuses(SupportServiceType, supportServiceCodesApplicationCategoriesStatus, applicationInfo.getCategory().getSaipCode());
            validateAppealRequestStatus(applicationInfo.getApplicationStatus(), SupportServiceType);
            validateApplicationSupportServiceTypePermittedStatus(applicationInfo, SupportServiceType, statuses);
        }
    }

    private void validateAppealRequestStatus(LkApplicationStatus applicationStatus, SupportServiceType supportServiceType){
        if(supportServiceType.equals(APPEAL_REQUEST) &&
                applicationStatus.getCode().equalsIgnoreCase(ApplicationStatusEnum.APPEAL_FORMAL_REJECTION.name())){
            throw new BusinessException(APPEAL_REQUEST_VALIDATION, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void validateParentServiceInValidStatus(Long parentServiceId, SupportServiceType supportServiceType, Map<String, Map<String, List<String>>> supportServiceCodesApplicationCategoriesStatus) {
        if (parentServiceId == null)
            return;
        String parentServiceCurrentStatusCode = supportServiceRequestRepository.getSupportServiceStatusCodeByServiceId(parentServiceId);
        validateRevokeLicenceRequestInValidState(parentServiceCurrentStatusCode, supportServiceCodesApplicationCategoriesStatus.get(supportServiceType.name()).get(PARENT_SERVICE_VALID_STATUSES.name()), parentServiceId);
    }

    private void validateRevokeLicenceRequestInValidState(String parentServiceCurrentStatusCode, List<String> validCodes, Long serviceId) {
        String applicationStatus = applicationSupportServicesTypeService.getSupportServiceTypeById(serviceId).getApplicationInfo().getApplicationStatus().getCode();
        if(validCodes != null && !validCodes.contains(parentServiceCurrentStatusCode) || applicationStatus.contains(REVOKED_STATUS_PREFIX))
            throw new BusinessException(Constants.ErrorKeys.REQUEST_STATUS_IS_NOT_VALID, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private List<String> getSupportServiceAlLowedStatuses(SupportServiceType SupportServiceType, Map<String, Map<String, List<String>>> supportServiceCodesApplicationCategoriesStatus, String applicationCategory) {
        List<String> statuses   ;
        if (SupportServiceType.OWNERSHIP_CHANGE.equals(SupportServiceType)  && (TRADEMARK.name().equals(applicationCategory) || PATENT.name().equals(applicationCategory))){
            statuses =  Arrays.asList(ACCEPTANCE.toString(),  THE_TRADEMARK_IS_REGISTERED.name()) ;
        }else if ( SupportServiceType.INITIAL_MODIFICATION.equals(SupportServiceType) && (TRADEMARK.name().equals(applicationCategory) || PATENT.name().equals(applicationCategory))){
            statuses =  Arrays.asList(ACCEPTANCE.toString(), NEW.toString(), THE_TRADEMARK_IS_REGISTERED.name()) ;
        }else {
            statuses =  supportServiceCodesApplicationCategoriesStatus.get(SupportServiceType.toString()).get("STATUS") ;
        }
        return statuses;
    }

    private void isSupportServiceApplicantOneOfActors(ApplicationInfo applicationInfo, SupportServiceType serviceType, Map<String, Map<String, List<String>>> supportServiceCodesApplicationCategoriesStatus, Long parentServiceId) {
        List<String> actors = supportServiceCodesApplicationCategoriesStatus.get(serviceType.toString()).get(Constants.SupportServiceValidationMapKeys.ACTORS.name());
        if (actors == null || actors.isEmpty() || actors.contains(ALL.name())) {
            return;
        }

        String mainApplicationApplicantCustomerCode = Utilities.extractCustomerCode(applicationInfo);
        String supportServiceRequestApplicantCustomerCode = Utilities.getCustomerCodeFromHeaders();
        String customerCode = getLicensedCustomerCode(serviceType, parentServiceId);
        boolean hasPermission = isRightActorAndHasPermission(applicationInfo, serviceType, parentServiceId, actors, customerCode, supportServiceRequestApplicantCustomerCode, mainApplicationApplicantCustomerCode);
        if (!hasPermission) {
            throw new BusinessException(SUPPORT_SERVICE_SEARCH_APPLICATION_VALIDATION_SUPPORT_SERVICE_ACTOR_DEFAULT, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private boolean isRightActorAndHasPermission(ApplicationInfo applicationInfo, SupportServiceType serviceType, Long parentServiceId, List<String> actors, String customerCode, String supportServiceRequestApplicantCustomerCode, String mainApplicationApplicantCustomerCode) {
        boolean hasPermission = false;
        if (!hasPermission && actors.contains(SupportServiceActors.LICENSED_CUSTOMER.name()) && parentServiceId != null){
            hasPermission = userHasPermission(customerCode, supportServiceRequestApplicantCustomerCode, applicationInfo, serviceType);
        }
        if (!hasPermission && actors.contains(SupportServiceActors.LICENSED_CUSTOMER_AGENT.name()) && parentServiceId != null){
            hasPermission = userAgentHasPermission(customerCode, supportServiceRequestApplicantCustomerCode, applicationInfo, serviceType, parentServiceId);
        }
        if (!hasPermission && actors.contains(SupportServiceActors.MAIN_OWNER.name())) {
            hasPermission = userHasPermission(mainApplicationApplicantCustomerCode, supportServiceRequestApplicantCustomerCode, applicationInfo, serviceType);

        }
        if (!hasPermission && actors.contains(SupportServiceActors.AGENT.name())) {
            hasPermission = userAgentHasPermission(mainApplicationApplicantCustomerCode, supportServiceRequestApplicantCustomerCode, applicationInfo, serviceType, parentServiceId);
        }
        if (!hasPermission && actors.contains(SupportServiceActors.OTHER.name())) {
            hasPermission = checkSupportServiceApplicantIsApplicationIsNotOwner(mainApplicationApplicantCustomerCode, supportServiceRequestApplicantCustomerCode);
        }
        return hasPermission;
    }

    private Long getCustomerIdByCustomerCode(String supportServiceRequestApplicantCustomerCode){
        return customerServiceCaller.getCustomerIdByCustomerCode(supportServiceRequestApplicantCustomerCode);
    }
    private Long getCustomerIdFromChangeOwnerShipByAppId(ApplicationInfo applicationInfo){
        return changeOwnershipRequestRepository.getCustomerIdFromChangeOwnerShipByAppId(applicationInfo.getId());
    }
    private boolean hasLicensingModificationPermission(Long applicationId, String applicantCustomerCode) {
        List<ApplicationSupportServicesType> applicationSupportServicesTypes = applicationSupportServicesTypeService.getSupportServiceByAppIdAndStatusAndTypeAndCustomerCode(
                applicationId,
                SupportServiceRequestStatusEnum.LICENSED,
                LICENSING_REGISTRATION,
                applicantCustomerCode
        );
        return !applicationSupportServicesTypes.isEmpty();
    }
    private boolean  hasLicensingModificationAndOwnerShipPermission(Long applicationId, String applicantCustomerCode) {
        List<ApplicationSupportServicesType> applicationSupportServicesTypes = applicationSupportServicesTypeService.hasLicensingModificationAndOwnerShipPermission(applicationId,
                SupportServiceRequestStatusEnum.TRANSFERRED_OWNERSHIP,
                OWNERSHIP_CHANGE,
                applicantCustomerCode);
        return !applicationSupportServicesTypes.isEmpty();
    }

    private String getLicensedCustomerCode(SupportServiceType serviceType, Long parentServiceId) {
        String licensedCustomerCode =null;
        if(serviceType.equals(REVOKE_LICENSE_REQUEST))
            licensedCustomerCode = licenceRequestService.getLicensedCustomerCodeByLicenseId(parentServiceId);
        else if(serviceType.equals(OPPOSITION_REVOKE_LICENCE_REQUEST))
            licensedCustomerCode = revokeLicenceRequestService.getLicensedCustomerCodeByRevokeLicenseId(parentServiceId);
        return licensedCustomerCode;
    }

    private boolean checkSupportServiceApplicantIsApplicationIsNotOwner(String mainApplicationApplicantCustomerCode, String supportServiceRequestApplicantCustomerCode) {
        return !mainApplicationApplicantCustomerCode.equalsIgnoreCase(supportServiceRequestApplicantCustomerCode);
    }

    private boolean userHasPermission(String mainApplicationApplicantCustomerCode, String supportServiceRequestApplicantCustomerCode,  ApplicationInfo applicationInfo, SupportServiceType serviceType) {
      return isSupportServiceApplicantApplicationOwner(mainApplicationApplicantCustomerCode, supportServiceRequestApplicantCustomerCode);
    }

    private boolean userAgentHasPermission(String mainApplicationApplicantCustomerCode, String supportServiceRequestApplicantCustomerCode,  ApplicationInfo applicationInfo, SupportServiceType serviceType, Long parentServiceId) {
        boolean hasPermission = false;
        String licensedCustomerCode = applicationRelevantTypeRepository.getLicensedApplicantCustomerCodeByApplicationInfoId(applicationInfo.getId());
        if (TRADEMARK.toString().equals(applicationInfo.getCategory().getSaipCode())) {
            hasPermission = trademarkAgencyRequestService.isAgentHasPermissionForApplicationSupportService(supportServiceRequestApplicantCustomerCode, mainApplicationApplicantCustomerCode, applicationInfo.getId(), serviceType, parentServiceId);
        }

        if(PATENT.toString().equals(applicationInfo.getCategory().getSaipCode())) {
            List<String> agents = applicationInfo.getApplicationCustomers().stream()
                    .filter(applicationCustomer -> applicationCustomer.getCustomerType().equals(AGENT))
                    .map(ApplicationCustomer::getCustomerCode)
                    .collect(Collectors.toList());
            if(agents.contains(supportServiceRequestApplicantCustomerCode))
                hasPermission = true;
            else if(supportServiceRequestApplicantCustomerCode.equalsIgnoreCase(licensedCustomerCode)){
                hasPermission = true;
            }
        }

        return hasPermission;

    }

    private boolean isSupportServiceApplicantApplicationOwner(String mainApplicationApplicantCustomerCode, String supportServiceRequestApplicantCustomerCode) {
        return mainApplicationApplicantCustomerCode.equalsIgnoreCase(supportServiceRequestApplicantCustomerCode);
    }


    private void validateApplicationHasAnySupportServiceTypePermittedCategory(ApplicationInfo applicationInfo, SupportServiceType SupportServiceType, Map<String, Map<String, List<String>>> supportServiceCodesApplicationCategoriesStatus) {
        List<String> categories = supportServiceCodesApplicationCategoriesStatus.get(SupportServiceType.toString()).get("CATEGORY");
        if (!categories.contains(applicationInfo.getCategory().getSaipCode()))
            throw new BusinessException(Constants.ErrorKeys.APPLICATION_CATEGORY_IS_NOT_VALID, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void validateApplicationSupportServiceTypePermittedStatus(ApplicationInfo applicationInfo, SupportServiceType supportServiceType, List<String> statuses) {
        if (!statuses.contains(applicationInfo.getApplicationStatus().getCode())) {
            String[] param = {applicationInfo.getApplicationStatus().getIpsStatusDescEn(), applicationInfo.getApplicationStatus().getIpsStatusDescAr()};
            throw new BusinessException(Constants.ErrorKeys.SERVICE_CANNOT_BE_USED, HttpStatus.INTERNAL_SERVER_ERROR, param);
        }
    }

    private void validateApplicationDoesntHaveHeadOfCheckerUserRole(Long appId) {
        applicationUserService.checkApplicationHasUserRole(appId, ApplicationUserRoleEnum.HEAD_OF_CHECKER);
    }

    protected String checkRenewalFeesPayConditions(ApplicationInfo app) {
        String status = checkRenewalStatus(app);
        LocalDateTime date = applicationInstallmentService.getLastDueDate(app.getId(), InstallmentStatus.NEW);
        LocalDateTime appFilingDate = date == null ? app.getFilingDate() : date;
        LocalDateTime currentDate = LocalDateTime.now();
        if (app.getCategory().getSaipCode().equals(TRADEMARK.name())) {
            LocalDateTime endDate = appFilingDate.plusYears(configParameterService.getLongByKey("RENEWAL_TM_END_YEAR"));
            endDate.plusMonths(configParameterService.getLongByKey("RENEWAL_TM_END_MONTHS"));
            LocalDateTime startDate = appFilingDate.plusYears(configParameterService.getLongByKey("RENEWAL_TM_START_YEAR"));

            if (currentDate.isAfter(startDate) && currentDate.isBefore(endDate)) {
                return PASSED;
            }
            if (currentDate.isAfter(endDate)) {
                throw new BusinessException(EXCEPTION_EXPLAIN_BLOCK_ِRENEWAL);
            }
            if (currentDate.isBefore(startDate)) {


                long differenceInDaysTm = ChronoUnit.DAYS.between(currentDate, startDate);

                String[] params = {String.valueOf(differenceInDaysTm),
                };

                throw new BusinessException(EXCEPTION_EXPLAIN_BLOCK_REASON_ِRENEWAL, HttpStatus.BAD_REQUEST, params);
            }


        }
        if (app.getCategory().getSaipCode().equals(INDUSTRIAL_DESIGN.name())) {
            LocalDateTime endDate = appFilingDate.plusYears(configParameterService.getLongByKey("RENEWAL_INDUSTRIAL_END_YEARS"));
            LocalDateTime startDate = appFilingDate.plusYears(configParameterService.getLongByKey("RENEWAL_INDUSTRIAL_START_YEARS"));

            if (currentDate.isAfter(startDate) && currentDate.isBefore(endDate)) {
                return PASSED;
            }
            if (currentDate.isBefore(startDate)) {


                long differenceInDaysIN = ChronoUnit.DAYS.between(currentDate, startDate);

                String[] params = {

                        String.valueOf(differenceInDaysIN)
                };

                throw new BusinessException(EXCEPTION_EXPLAIN_BLOCK_REASON_ِRENEWAL, HttpStatus.BAD_REQUEST, params);


            }
            if (currentDate.isAfter(endDate)) {
                throw new BusinessException(EXCEPTION_EXPLAIN_BLOCK_RENEWAL_INDUSTRIAL);

            }

        }


        throw new BusinessException("system error in filing date supported with wrong value");

    }

    protected String checkRenewalStatus(ApplicationInfo app) {
        String appCategoryCode = app.getCategory().getSaipCode();
        String appStatusCode = app.getApplicationStatus().getCode();
        Map<String, Map<String, List<String>>> supportServiceCodesApplicationCategoriesStatus =
                Constants.SupportServicesApplicationListing.supportServiceCodesApplicationCategoriesStatus;
        Map<String, List<String>> statusMap = supportServiceCodesApplicationCategoriesStatus.get(RENEWAL_FEES_PAY.toString());

        if (supportServiceCodesApplicationCategoriesStatus.containsKey(RENEWAL_FEES_PAY.toString())) {

            if (statusMap.get("CATEGORY").contains(appCategoryCode)) {

                if (statusMap.get("STATUS").contains(appStatusCode)) {

                    return PASSED;
                } else {
                    String[] params = {app.getApplicationStatus().getIpsStatusDescEn()};
                    throw new BusinessException(EXCEPTION_APPLICATION_STATUS_CHANGED, HttpStatus.BAD_REQUEST, params);

                }


            } else {
                String[] params = {app.getCategory().getApplicationCategoryDescEn()};
                throw new BusinessException(EXCEPTION_APPLICATION_CATEGORIES_CHANGED, HttpStatus.BAD_REQUEST, params);
            }
        }
        return null;
    }
    public void checkAccessForCurrentCustomerCode(Long serviceId){
        String currentUserCustomerCode = Utilities.getCustomerCodeFromHeaders();
        if(currentUserCustomerCode == null) return;
        List<String> serviceVerifiedCustomerCodes = getVerifiedCustomerCodes(serviceId);
        if(!serviceVerifiedCustomerCodes.contains(currentUserCustomerCode)){
            throw new BusinessException(Constants.ErrorKeys.GENERAL_DO_NOT_HAVE_PERMISSION, HttpStatus.FORBIDDEN, null);
        }
    }

    private List<String> getVerifiedCustomerCodes(Long serviceId) {
        List<String> serviceVerifiedCustomerCodes = supportServiceCustomerService.getCustomerCodesByServiceId(serviceId);
        switch (applicationSupportServicesTypeService.getServiceTypeByServiceId(serviceId)){
            case LICENSING_REGISTRATION: serviceVerifiedCustomerCodes.add(licenceRequestService.getLicensedCustomerCodeByLicenseId(serviceId));
        }
        return serviceVerifiedCustomerCodes;
    }

}
