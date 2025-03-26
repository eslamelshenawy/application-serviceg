package gov.saip.applicationservice.common.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Sort;

import gov.saip.applicationservice.common.dto.AllApplicationsDtoLight;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.AppCommunicationUpdateRequestsDto;
import gov.saip.applicationservice.common.dto.ApplicantsDto;
import gov.saip.applicationservice.common.dto.ApplicantsRequestDto;
import gov.saip.applicationservice.common.dto.ApplicationAdditionalDetailsDto;
import gov.saip.applicationservice.common.dto.ApplicationClassificationDto;
import gov.saip.applicationservice.common.dto.ApplicationClassificationLightDto;
import gov.saip.applicationservice.common.dto.ApplicationDataUpdateDto;
import gov.saip.applicationservice.common.dto.ApplicationInfoBaseDto;
import gov.saip.applicationservice.common.dto.ApplicationInfoDto;
import gov.saip.applicationservice.common.dto.ApplicationInfoPaymentDto;
import gov.saip.applicationservice.common.dto.ApplicationInfoTaskDto;
import gov.saip.applicationservice.common.dto.ApplicationListDto;
import gov.saip.applicationservice.common.dto.ApplicationNumberGenerationDto;
import gov.saip.applicationservice.common.dto.ApplicationRelevantTypeDto;
import gov.saip.applicationservice.common.dto.ApplicationRelevantTypeLightDto;
import gov.saip.applicationservice.common.dto.ApplicationRequestBodyDto;
import gov.saip.applicationservice.common.dto.ApplicationSubstantiveExaminationDto;
import gov.saip.applicationservice.common.dto.ApplicationSubstantiveExaminationRetrieveDto;
import gov.saip.applicationservice.common.dto.BillApplicationInfoAttributesDto;
import gov.saip.applicationservice.common.dto.CustomerSampleInfoDto;
import gov.saip.applicationservice.common.dto.DepositReportDto;
import gov.saip.applicationservice.common.dto.InventorRequestsDto;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.PartialApplicationInfoDto;
import gov.saip.applicationservice.common.dto.PartialApplicationInfoProjection;
import gov.saip.applicationservice.common.dto.StartProcessResponseDto;
import gov.saip.applicationservice.common.dto.UpdateApplicationsStatusDto;
import gov.saip.applicationservice.common.dto.reports.ApplicationReportDetailsDto;
import gov.saip.applicationservice.common.dto.search.AdvancedSearchDto;
import gov.saip.applicationservice.common.dto.search.SearchDto;
import gov.saip.applicationservice.common.dto.trademark.RequestTrademarkDetailsDto;
import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import gov.saip.applicationservice.common.enums.ApplicationCustomerType;
import gov.saip.applicationservice.common.enums.ApplicationListSortingColumns;
import gov.saip.applicationservice.common.enums.ApplicationPaymentMainRequestTypesEnum;
import gov.saip.applicationservice.common.enums.ApplicationStatusEnum;
import gov.saip.applicationservice.common.enums.agency.TrademarkAgencyType;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.ApplicationRelevantType;
import gov.saip.applicationservice.common.model.LkApplicationStatus;
import gov.saip.applicationservice.common.projection.ApplicationReportInfo;
import gov.saip.applicationservice.common.projection.CountApplications;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.modules.common.service.BaseApplicationInfoService;

public interface ApplicationInfoService extends BaseApplicationInfoService {

    public Long saveApplicationPCT(ApplicantsRequestDto applicantsRequestDto);

    List<Long> getApplicationProcessInstanceIds(String query, LocalDate fromFilingDate, LocalDate toFilingDate, String applicationNumber);

    PaginationDto getTrademarksByCustomerCode(RequestTrademarkDetailsDto requestTrademarkDetailsDto, Integer page, Integer limit);

    Long updateApplicationMainData(ApplicationDataUpdateDto dto, Long id);

    Long  getUnPaidCountApplicationRelavantType(Long appId);

    ApplicantsDto listMainApplicant(Long appId);

    List<ApplicantsDto> listApplicants(Long appId);


    ApplicationInfoDto getApplication(Long id);

    ApplicationInfoDto getApplicationPatent(Long id);


    Long addInventor(InventorRequestsDto dto);

    void deleteAppRelvant(Long relvantTypeId);
    void deleteByApplicationId(Long appId);
    Object updateSubstantiveExamination(Boolean substantiveExamination, Long id);

    Object updateAccelerated(Boolean accelerated, Long id);
    ApplicationListDto createApplicationListDtoByApplicationInfo(ApplicationInfo applicationInfo);

    PaginationDto<List<ApplicationListDto>> getApplicationListByApplicationCategoryAndUserId(String applicationCategory,Long userId, Integer page, Integer limit, ApplicationListSortingColumns sortingColumn, Sort.Direction sortDirection, AdvancedSearchDto advancedSearchDto);

    PaginationDto listApplicationsAgentId(Long categoryId, String applicationNumber,String appStatus, Long agentId, String customerCode, Integer page, Integer limit);

    Long generateApplicationNumber(ApplicationNumberGenerationDto applicationNumberGenerationDto, Long id);

    public Long saveApplicationInfoEntity(ApplicationInfo applicationInfo);

    public ApplicationInfoPaymentDto getApplicationInfoPayment(Long id);//Michael get application info payment
    public Long submitApplicationInfoPayment(Long id, ApplicationInfoPaymentDto applicationInfoPaymentDto);//Michael submit application info payment

    ApplicationClassificationDto getApplicationClassificationById(Long id);

    ApplicationClassificationDto updateApplicationClassification(ApplicationClassificationDto applicationClassificationDto);
    ApplicationInfoPaymentDto getApplicationPayment(Long applicationId);

    void updateExamination (ApplicationSubstantiveExaminationDto dto, Long applicationId);

    ApplicationSubstantiveExaminationRetrieveDto getApplicationSubstantiveExamination(Long id);
    List<PartialApplicationInfoDto> getApplicationByApplicationPartialNumber(String partialNumber);
    ApiResponse getUserRequestTypes ();

    Object getApplicationSummary(Long appId, String categoryCode);

    Double getSupportServiceCost(String code,String applicantCategorySaipCode,String applicationCategorySaipCode);

    void changeApplicationStatusId(Long applicationId, String statusCode);
    void updateStatusByCodeAndId(Long applicationId, String statusCode);

    public void changeApplicationStatusesByApplicationIds(List<Long> applicationIds,Long categoryId);
    
    
    List<ApplicationClassificationLightDto> getApplicationClassification(List<Long> ids);
    void updateApplicationNumberForAllOldData(String serviceCode, String saipCode);
    Map<Long, ApplicationInfoTaskDto> getApplicationStatusByApplicationIds(List<Long> ids);

    public ApplicationInfoPaymentDto getApplicationPaymentPreparationInfo(Long id);

    public List<Long> getApplicationClassificationUnitIdsByAppId(Long appId);

    boolean checkApplicationStatusIsNotInListOfStatus(Long appId, List<String> status);



    PaginationDto getNotAssignedToAgentApplications(Long categoryId, String applicationNumber, String appStatus, String customerCode, Integer page, Integer limit);

    PaginationDto getAssignedToAgentApplications(Long categoryId, String applicationNumber, String appStatus, String customerCode, Integer page, Integer limit);

    List<ApplicationInfo> getAllApplicationsByCustomerCodeAndAgentId(String customerCode, Long agentId);

    CustomerSampleInfoDto getCurrentAgentsInfoByApplicationId(Long appId);

    ApplicationInfo checkMainApplicationExists(String applicationNumber, Long partialAppId);

    List<ApplicationInfo> getApplicationsThatAreReadyToBeAddedToNextPublicationIssue(ApplicationCategoryEnum applicationCategoryEnum);
    
     void getDataOfCustomersByCode(List<ApplicationRelevantTypeLightDto> applicationRelevantTypeLightDtos);
    ApplicationReportDetailsDto getApplicationReportInfo(Long appId);

    void updateAppCommunicationDetails(AppCommunicationUpdateRequestsDto appCommunicationUpdateRequestsDto);
    
    String getApplicationStatus(Long applicationId);
    
    String getCreatedUserById(Long applicationId);

    PaginationDto<List<AllApplicationsDtoLight>> getAllApplications(String appNumber,String appStatus,String appCategory,String query,Integer firstResult, Integer maxResult);

    PaginationDto<List<ApplicationInfoBaseDto>> getExaminerApplications(ApplicationCategoryEnum categoryCode, String userName, String search, String statusCode, int page, int limit);

    PaginationDto<List<ApplicationInfoBaseDto>> getExaminerApplicationsByUnitCode(ApplicationCategoryEnum categoryCode, List<String> unitCodes,int page, int limit);

    void extendProtectionPeriodByAppId(Long id, Integer years);

    DepositReportDto findDepositCertificateDetailsById(Long aiId);


    String validateSupportServicePreConditions(Long appId,String supportServiceCode, Long parentServiceId);


    ApplicationReportInfo getApplicationReportData(Long certificateId);


    CountApplications sumOfApplicationsAccordingToStatus(String saipCode , String customerCode);

	void updateApplicationInfoEndOfProtectionDate(long appId, Long extensionPeriodInYears);



    void updateApplicationsStatusByIds(List<Long> applicationInfos, LkApplicationStatus lkApplicationStatus);

    ApplicationListDto getApplicationListDtoByApplicationNumber(String applicationNumber, String supportServiceCode, String category);
    ApplicationInfo getApplicationInfoByApplicationNumber(String applicationNumber);
    PaginationDto<List<ApplicationInfoBaseDto>>  findApplicationsBaseInfoDto(Long categoryId, List<String> appStatuses, String applicationNumber,
                                                                            Long customerId, List<ApplicationCustomerType> customerTypes, int page, int limit);

    void getApplicationsClassifications(List<ApplicationInfoBaseDto> content, List<Long> appIds);


    public void assignDataOfCustomersCode(List<ApplicationRelevantTypeDto> applicationRelevantTypes);

    ApplicationListDto getApplicationByNumberAndAgencyType(String applicationNumber, TrademarkAgencyType trademarkAgencyType);

    void validateGivenDateIsNotPastApplicationEndOfProtectionDate(Long applicationId, LocalDateTime date);

    String getMainApplicantCustomerCodeByApplicationInfoId(Long applicationId);

    void updateStatusAndProtectionDate(Long applicationId, ApplicationStatusEnum applicationStatusEnum, LocalDateTime endOfProtection);

    Boolean checkPltRegister(Long applicationId);
    String checkApplicationCategory(Long applicationId);

    public void generateApplicationNumberPatent(Long applicationInfoId);
    public void generateGrantNumberPatent(Long applicationInfoId);

    void generateTrademarkApplicationNumber(Long applicationInfoId);

    void disableAddingApplicationPriority(Long applicationInfoId);

    boolean checkIsPriorityConfirmed(Long applicationInfoId);

    ApplicationInfoDto getGrantedApplicationDetailsByApplicationNumber(String applicationNumber);

    List<PartialApplicationInfoProjection> listCustomerMainApplications(String applicationNumber, Long customerId, String category);

    String getApplicationCategoryById(Long id);

    void initiateExamination(Long id);
    
    Long updateApplicationInfo(Long appId, ApplicationRequestBodyDto requestDto);
    
    Boolean getNationalSecurity(Long appId);

    void updateFillingDatePartialApplication(Long id);
    List<ApplicationRelevantType> getApplicationRelevantTypes(Long appId);

    void updateApplicationWithProcessRequestId(StartProcessResponseDto startProcessResponseDto, Long id);

    String getProcessRequestIdById(Long applicationId);
    Long getApplicationIdByApplicationNumber(String applicationNumber);
	 void updateAppTotalNumberOfPagesAndCalculateClaimPages(Long applicationId, Long totalNumberOfPages);

    ApplicationAdditionalDetailsDto getApplicationAdditionalDetailsById(Long id);
    Long getApplicationUnitClassificationIdByApplicationId(Long applicationId);

    String getApplicationRequestNumber(Long id);

    Long getAppIdByAppNumber(String applicationNumber) throws BusinessException;

    Long getApplicationAgentCountByAppId(Long applicationId);

    void checkIfApplicationAgentExists(Long applicationId) throws BusinessException;

    void checkPatentAndIndustrialPattern(String applicationNumber) throws BusinessException;
    List<Long> getProcessesRequestsIdsByFilters(SearchDto searchDto);
    ApplicationInfoBaseDto getAppBasicInfo(Long appId);
    ApplicationInfoBaseDto getAppBasicInfoIds(List<Long> appIds);
    List<Long> findPatentApplicationWithNoPdfDocument();
    void generateTrademarkMissingApplicationNumbers();
    void updateApplicationBatchByProcessRequestId(UpdateApplicationsStatusDto dto);
    void updateApplicationInfoEnterpriseSize(Long enterpriseSizeId,Long appId);
    List<ApplicationRelevantTypeDto> listIndustrailApplicants(Long appId,  Integer isAttachDocument );
    List<String> retrieveInventorRelatedToDesignSample(Long applicationRelevantTypeId );
    String getProcessRequestTypeCodeByApplicationId(Long applicationId);

    boolean isCustomerApplicationApplicant(Long appId, Long customerId);

    void generateGrantNumberDateIC(Long applicationInfoId);

    void deleteLastUserModifiedDateValueByApplicationId(Long applicationId);
    BillApplicationInfoAttributesDto getBillApplicationAttributes(Long applicationId);

    Double calculateServiceCost(CustomerSampleInfoDto customerSampleInfoDto, ApplicationPaymentMainRequestTypesEnum applicationPaymentMainRequestTypesEnum, ApplicationCategoryEnum applicationCategoryEnum);
    void updateApplicationByHimSelfAfterAddingAgent(List<Long> appIds);

    Optional<ApplicationInfo> findByApplicationNumber(String applicationNumber);


}
