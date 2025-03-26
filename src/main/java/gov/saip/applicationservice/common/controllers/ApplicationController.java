package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.annotation.CheckCustomerAccess;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.dto.reports.ApplicationReportDetailsDto;
import gov.saip.applicationservice.common.dto.search.AdvancedSearchDto;
import gov.saip.applicationservice.common.dto.search.SearchDto;
import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import gov.saip.applicationservice.common.enums.ApplicationListSortingColumns;
import gov.saip.applicationservice.common.enums.ApplicationRelevantEnum;
import gov.saip.applicationservice.common.enums.ValidationType;
import gov.saip.applicationservice.common.enums.agency.TrademarkAgencyType;
import gov.saip.applicationservice.common.facade.ApplicationInfoFacade;
import gov.saip.applicationservice.common.mapper.ApplicationInfoMapper;
import gov.saip.applicationservice.common.projection.CountApplications;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.ApplicationRelevantTypeService;
import gov.saip.applicationservice.common.validators.InventorsValidator;
import gov.saip.applicationservice.common.validators.PctValidator;
import gov.saip.applicationservice.common.validators.TradeMarkApplicationValidator;
import gov.saip.applicationservice.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

import static gov.saip.applicationservice.util.Constants.AppRequestHeaders.CUSTOMER_CODE;

@RestController
@RequestMapping(value = {"/kc/applications", "/internal-calling/application"})
@RequiredArgsConstructor
@Slf4j
public class ApplicationController {
    private final ApplicationInfoService applicationInfoService;
    private final TradeMarkApplicationValidator tradMarkApplicationValidator;
    private final ApplicationInfoFacade applicationInfoFacade;
    private final ApplicationInfoMapper applicationInfoMapper;
    private final PctValidator pctValidator;
    private final InventorsValidator inventorsValidator;
    private final ApplicationRelevantTypeService applicationRelevantTypeService;


    @PostMapping(value = "/{id}")
    public ApiResponse<Long> updateApplicationsData(@Valid @RequestBody ApplicationDataUpdateDto applicationDataUpdateDto, @PathVariable Long id) {
        return ApiResponse.ok(applicationInfoService.updateApplicationMainData(applicationDataUpdateDto, id));
    }
    @DeleteMapping("/delete/draft-app/{appId}")
    ApiResponse<String> deleteDraftAppById(@PathVariable(name="appId")String appId){
        applicationInfoService.deleteByApplicationId(Long.valueOf(appId));
       return ApiResponse.ok("Deleted");
    }

    @PostMapping("/inventor")
    public ApiResponse<Long> addInventor(@RequestBody InventorRequestsDto inventorRequestsDto) {
        inventorsValidator.validate(inventorRequestsDto,null);
        return ApiResponse.ok(applicationInfoService.addInventor(inventorRequestsDto));
    }


    @DeleteMapping("/relvantType/{id}")  // typ
    public ApiResponse<?> deleteRelvant(@PathVariable(name = "id") Long id) {
        applicationInfoService.deleteAppRelvant(id);
        return ApiResponse.ok("");
    }

    @GetMapping("/{id}")
    public ApiResponse<ApplicationInfoDto> getApplication(@PathVariable(name = "id") Long id) {
        ApplicationInfoDto appInfo = applicationInfoService.getApplication(id);
        // the below line will be removed once front end finish hist work and application dto will be removed also from dto
        appInfo.setClassification((appInfo.getClassifications() != null && !appInfo.getClassifications().isEmpty()) ? appInfo.getClassifications().get(0) : null);
        return ApiResponse.ok(appInfo);
    }
    @GetMapping("/patent/{id}")
    public ApiResponse<ApplicationInfoDto> getApplicationPatent(@PathVariable(name = "id") Long id) {
        ApplicationInfoDto appInfo = applicationInfoService.getApplicationPatent(id);
        return ApiResponse.ok(appInfo);
    }

    @GetMapping("/application-classification/{id}")
    public ApiResponse<ApplicationClassificationDto> getApplicationClassificationById(@PathVariable(name = "id") Long id) {
        return ApiResponse.ok(applicationInfoService.getApplicationClassificationById(id));
    }

    @PutMapping("/application-classification")
    public ApiResponse<Long> updateApplicationClassification(@RequestBody ApplicationClassificationDto applicationClassificationDto) {
        applicationInfoService.updateApplicationClassification(applicationClassificationDto);
        return ApiResponse.ok(applicationClassificationDto.getId());
    }
    @PutMapping("/communication")
    public ApiResponse<Long> updateAppCommunicationDetails(@RequestBody AppCommunicationUpdateRequestsDto appCommunicationUpdateRequestsDto) {
        applicationInfoService.updateAppCommunicationDetails(appCommunicationUpdateRequestsDto);
        return ApiResponse.ok(appCommunicationUpdateRequestsDto.getId());
    }

    @GetMapping("/{id}/applicants")
    public ApiResponse<List<ApplicantsDto>> listApplicants(@PathVariable(name = "id") Long id) {
        return ApiResponse.ok(applicationInfoService.listApplicants(id));
    }
    @GetMapping("/{id}/applicants/industerial")//here
    public ApiResponse<List<ApplicationRelevantTypeDto>> listApplicantsIndusterial(@PathVariable(name = "id") Long id) {
        return ApiResponse.ok(applicationInfoService.listIndustrailApplicants(id , null));
    }
    @GetMapping("/{applicationRelevantTypeId}/applicants/design-sample")
    public ApiResponse<List<String>> retrieveInventorRelatedToDesignSample(@PathVariable(name = "applicationRelevantTypeId") Long applicationRelevantTypeId) {
        return ApiResponse.ok(applicationInfoService.retrieveInventorRelatedToDesignSample(applicationRelevantTypeId));
    }
    @GetMapping("/{id}/applicants/industerial/attach-document")
    public ApiResponse<List<ApplicationRelevantTypeDto>> listApplicantsIndusterialAttach(@PathVariable(name = "id") Long id) {
        return ApiResponse.ok(applicationInfoService.listIndustrailApplicants(id , 1));
    }



    @GetMapping("/{id}/additional/applicants")
    public ApiResponse<Long> getAdditionalApplicants(@PathVariable(name = "id") Long id) {
        return ApiResponse.ok(applicationInfoService.listApplicants(id).stream().filter(applicantsDto -> applicantsDto.getType().equals(ApplicationRelevantEnum.Applicant_SECONDARY)).count());
    }

    @PostMapping("")
    public ApiResponse<Long> saveApplication(@RequestBody ApplicantsRequestDto applicantsRequestDto) {
        tradMarkApplicationValidator.validate(applicantsRequestDto , null);

        return ApiResponse.ok(applicationInfoService.saveApplication(applicantsRequestDto));
    }
    @PostMapping("/save-patent")
    public ApiResponse<Long> saveApplicationPct(@RequestBody ApplicantsRequestDto applicantsRequestDto) {
        pctValidator.validate(applicantsRequestDto,null);
        return ApiResponse.ok(applicationInfoService.saveApplicationPCT(applicantsRequestDto));
    }

    @PostMapping(value = "/{id}/substantive-examination/{substantiveExamination}")
    public ApiResponse<Object> updateSubstantiveExamination(@PathVariable Long id, @PathVariable Boolean substantiveExamination) {
        return ApiResponse.ok(applicationInfoService.updateSubstantiveExamination(substantiveExamination, id));
    }

    @PostMapping(value = "/{id}/accelerated/{accelerated}")
    public ApiResponse<Object> updateAccelerated(@PathVariable Long id, @PathVariable Boolean accelerated) {
        return ApiResponse.ok(applicationInfoService.updateAccelerated(accelerated, id));
    }

    //Michael
    @GetMapping
    public ApiResponse<PaginationDto<List<ApplicationListDto>>> listApplications(
            @RequestParam(value = "applicationCategory",   required = false)       String applicationCategory,
            @RequestParam(value ="userId",                 required = false)       Long userId,
            @RequestParam(value = "page",                  defaultValue = "1")     Integer page,
            @RequestParam(value = "limit",                 defaultValue = "10")    Integer limit,
            @RequestParam(defaultValue = "id") ApplicationListSortingColumns sortOrder,
            @ModelAttribute AdvancedSearchDto advancedSearchDto,
            @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection) {
        if (advancedSearchDto != null && advancedSearchDto.isAllFieldsNull()) {
            advancedSearchDto = null;
        }
        PaginationDto<List<ApplicationListDto>> applicationListByApplicationCategoryAndUserId =
                applicationInfoService.getApplicationListByApplicationCategoryAndUserId(applicationCategory, userId, page, limit, sortOrder, sortDirection,advancedSearchDto);
        return ApiResponse.ok(applicationListByApplicationCategoryAndUserId);
    }

    @GetMapping("/process-request-ids")
    public List<Long> getApplicationProcessInstanceIds(
            @RequestParam(value = "query", required = false) String query,
            @RequestParam(value = "fromFilingDate", required = false) LocalDate fromFilingDate,
            @RequestParam(value = "toFilingDate", required = false) LocalDate toFilingDate,
            @RequestParam(value = "applicationNumber", required = false) String applicationNumber
    ){
        return applicationInfoService.getApplicationProcessInstanceIds(query, fromFilingDate, toFilingDate, applicationNumber);
    }


    @PostMapping(value = "/{id}/application-number")
    public ApiResponse<Long> generateApplicationNumber(@Valid @RequestBody ApplicationNumberGenerationDto applicationNumberGenerationDto, @PathVariable Long id) {
        log.info("payment call back request with id {}, request type {} and payment date {} ", id,  applicationNumberGenerationDto.getMainRequestType(), applicationNumberGenerationDto.getPaymentDate());
        return ApiResponse.ok(applicationInfoService.generateApplicationNumber(applicationNumberGenerationDto, id));
    }

    @GetMapping("/user/{customerCode}/agent/{agentId}")
    public ApiResponse<PaginationDto> getApplicationsByAgentIdAndCustomerCode(
            @PathVariable("agentId") Long agentId,
            @PathVariable("customerCode") String customerCode,
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "applicationNumber", required = false) String applicationNumber,
            @RequestParam(value = "appStatus", required = false) String appStatus,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit) {

        return ApiResponse.ok(applicationInfoService.listApplicationsAgentId(categoryId, applicationNumber, appStatus, agentId, customerCode, page, limit));
    }


    @GetMapping("/without-agent/user/{customerCode}")
    public ApiResponse<PaginationDto> getNotAssignedToAgentApplications (
            @PathVariable("customerCode") String customerCode,
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "applicationNumber", required = false) String applicationNumber,
            @RequestParam(value = "appStatus", required = false) String appStatus,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit) {

        return ApiResponse.ok(applicationInfoService.getNotAssignedToAgentApplications(categoryId, applicationNumber, appStatus, customerCode, page, limit));
    }

    @GetMapping("/with-agent/user/{customerCode}")
    public ApiResponse<PaginationDto> getAssignedToAgentApplications(
            @PathVariable("customerCode") String customerCode,
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "applicationNumber", required = false) String applicationNumber,
            @RequestParam(value = "appStatus", required = false) String appStatus,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit) {

        return ApiResponse.ok(applicationInfoService.getAssignedToAgentApplications(categoryId, applicationNumber, appStatus, customerCode, page, limit));
    }


    //Michael get application info payment
    @GetMapping(value = "/{id}/payment")
    public ApiResponse<ApplicationInfoPaymentDto> getApplicationInfoPayment(@PathVariable Long id) {
        return ApiResponse.ok(applicationInfoService.getApplicationInfoPayment(id));
    }

    //Michael submit application info payment
    @PostMapping(value = "/{id}/payment")
    public ApiResponse<Long> submitApplicationInfoPayment(@PathVariable Long id, @RequestBody ApplicationInfoPaymentDto applicationInfoPaymentDto) {
        return ApiResponse.ok(applicationInfoService.submitApplicationInfoPayment(id, applicationInfoPaymentDto));
    }

    @PutMapping("/substantive-examination/{applicationId}")
    public void updateExamination(@RequestBody ApplicationSubstantiveExaminationDto dto, @PathVariable("applicationId") Long applicationId) {
        applicationInfoService.updateExamination(dto, applicationId);
    }

    @GetMapping("/substantive-examination/{applicationId}")
    public ApplicationSubstantiveExaminationRetrieveDto getApplicationSubstantiveExamination(@PathVariable(name = "applicationId") Long applicationId) {
        return applicationInfoService.getApplicationSubstantiveExamination(applicationId);
    }

    @GetMapping("/task/request-type")
    public ApiResponse getUserRequestTypes() {

        return applicationInfoService.getUserRequestTypes();
    }

    @GetMapping("/summary/{appId}")
    public ApiResponse getRequestApplication(@PathVariable(name = "appId") Long appId,
                                             @RequestParam(value = "categoryCode", required = false) String categoryCode) {

        return ApiResponse.ok(applicationInfoService.getApplicationSummary(appId, categoryCode));
    }

    @GetMapping("/support-service/cost")
    public ApiResponse<Double> getSupportServiceCostBySaipCode(@RequestParam(value = "code") String code,
                                                                @RequestParam(value = "applicantCategorySaipCode", required = false) String applicantCategorySaipCode,
                                                                @RequestParam(value = "applicationCategorySaipCode", required = false) String applicationCategorySaipCode ) {

        return ApiResponse.ok(applicationInfoService.getSupportServiceCost(code,applicantCategorySaipCode,applicationCategorySaipCode==null?"PATENT":applicationCategorySaipCode));
    }

    @PutMapping("/{id}/status/{code}")
    public void changeStatus(@PathVariable (name="id") Long id
            , @PathVariable (name="code") String code){
        applicationInfoService.changeApplicationStatusId(id, code);
    }

    @GetMapping(value = "/{id}/payment-preperation-info")
    public ApiResponse<ApplicationInfoPaymentDto> getApplicationPaymentPreparationInfo(@PathVariable Long id) {
        return ApiResponse.ok(applicationInfoService.getApplicationPaymentPreparationInfo(id));
    }

    @GetMapping("/{id}/main-applicants")
    public ApiResponse<ApplicantsDto> listMainApplicants(@PathVariable(name = "id") Long id) {
        return ApiResponse.ok(applicationInfoService.listMainApplicant(id));
    }
    @GetMapping("/{appId}/current-agent")
    public ApiResponse<CustomerSampleInfoDto> getAgentsInfoByApplicationId(
            @PathVariable("appId") Long appId){

        return ApiResponse.ok(applicationInfoService.getCurrentAgentsInfoByApplicationId(appId));
    }

    @GetMapping("/check-exists/{appNumber}")
    public ApiResponse<ApplicationInfoBaseDto> checkMainApplicationNumberExists(@PathVariable("appNumber") String appNumber, @RequestParam(value = "partialAppId") Long partialAppId){
        return ApiResponse.ok(applicationInfoMapper.mapAppInfoToBaseInfoDto(applicationInfoService.checkMainApplicationExists(appNumber, partialAppId)));
    }

    @GetMapping("/{appId}/reports")
    public ApiResponse<ApplicationReportDetailsDto> getApplicationReportDetails(@PathVariable(name="appId") String appId){

        return ApiResponse.ok(applicationInfoService.getApplicationReportInfo(Long.valueOf(appId)));
    }
    
    
    @GetMapping("/{category-code}/examiner")
    ApiResponse<PaginationDto<List<ApplicationInfoBaseDto>>> getExaminerApplications(@PathVariable(name = "category-code") ApplicationCategoryEnum categoryCode,
                                                                                     @RequestParam(value = "userName") String userName,
                                                                                     @RequestParam(value = "search", required = false) String search,
                                                                                     @RequestParam(value = "statusCode", required = false) String statusCode,
                                                                                     @RequestParam(required = false, defaultValue = Constants.DEFAULT_PAGE_NUMBER, value = "page") int page,
                                                                                     @RequestParam(required = false, defaultValue = Constants.DEFAULT_PAGE_SIZE, value = "limit") int limit) {
        return ApiResponse.ok(applicationInfoService.getExaminerApplications(categoryCode, userName, search, statusCode,page, limit));
    }
    @GetMapping("/{category-code}/examiner/unit-code")
    ApiResponse<PaginationDto<List<ApplicationInfoBaseDto>>> getExaminerApplicationsByUnitCode(@PathVariable(name = "category-code") ApplicationCategoryEnum categoryCode,
                                                                                               @RequestParam(value = "unitCodes", required = false) List<String> unitCodes,
                                                                                     @RequestParam(required = false, defaultValue = Constants.DEFAULT_PAGE_NUMBER, value = "page") int page,
                                                                                     @RequestParam(required = false, defaultValue = Constants.DEFAULT_PAGE_SIZE, value = "limit") int limit) {
        return ApiResponse.ok(applicationInfoService.getExaminerApplicationsByUnitCode(categoryCode, unitCodes,page, limit));
    }

    @GetMapping("/retrieve/all")
    public ApiResponse<PaginationDto<List<AllApplicationsDtoLight>>> getAllApplications(@RequestParam(name="appNumber",required = false) String appNumber,
                                                           @RequestParam(name="appStatus",required = false) String appStatus,
                                                           @RequestParam(name="appCategory",required = false) String appCategory,
                                                           @RequestParam(name="query",required = false) String query,
                                                            @RequestParam(name="page") Integer page,
                                                            @RequestParam(name="limit") Integer limit
    ){

        return ApiResponse.ok(applicationInfoService.getAllApplications(appNumber,appStatus,appCategory,query,page,limit));
    }


    @GetMapping("/support-service/validate")
    public ApiResponse<String> validateSupportService( @RequestParam(name="appId") String appId,
                                                       @RequestParam(name="supportServiceCode") String supportServiceCode,@RequestParam(name="parentServiceId", required = false) Long parentServiceId
    ){

        return ApiResponse.ok(applicationInfoService.validateSupportServicePreConditions(Long.valueOf(appId),supportServiceCode, parentServiceId));
    }

    @GetMapping("/update-all-app-number/{serviceCode}/{saipCode}")
    public void updateApplicationNumberForAllOldData(@PathVariable String serviceCode,
                                                     @PathVariable String saipCode){
        applicationInfoService.updateApplicationNumberForAllOldData(serviceCode, saipCode);
    }



    @GetMapping("/sum-of-applications")
    public ApiResponse<CountApplications> sumOfApplications(@RequestParam(name = "saipCode") String saipCode ,HttpServletRequest request){
        String customerCode = request.getHeader(CUSTOMER_CODE.getKey());
        return ApiResponse.ok(applicationInfoService.sumOfApplicationsAccordingToStatus(saipCode , customerCode));
    }

	 @PutMapping("/{id}/extendProtectionPeriod")
    public void updateApplicationInfoEndOfProtectionDate(@PathVariable(name = "id") Long id, @RequestParam(name="extensionPeriodInYears") Long extensionPeriodInYears) {
        applicationInfoService.updateApplicationInfoEndOfProtectionDate(id, extensionPeriodInYears);
    }

    @GetMapping("/applicationNumber/{applicationNumber}")
    public ApiResponse<ApplicationListDto> getApplicationListDtoByApplicationNumber(@PathVariable(name = "applicationNumber") String applicationNumber, @RequestParam(name="supportServiceCode") String supportServiceCode, @RequestParam(name="category") String category){
        return ApiResponse.ok(applicationInfoService.getApplicationListDtoByApplicationNumber(applicationNumber, supportServiceCode, category));
    }

    @GetMapping("/application/{applicationNumber}")
    public ApiResponse<ApplicationListDto> getApplicationListDtoByApplicationNumber(@PathVariable(name = "applicationNumber") String applicationNumber, @RequestParam(name="type") TrademarkAgencyType trademarkAgencyType){
        return ApiResponse.ok((ApplicationListDto) applicationInfoService.getApplicationByNumberAndAgencyType(applicationNumber, trademarkAgencyType));
    }

    @GetMapping("/checkPltRegister/{applicationId}")
    public ApiResponse<Boolean> checkPltRegister(@PathVariable(name = "applicationId") Long applicationId){
        return ApiResponse.ok(applicationInfoService.checkPltRegister(applicationId));
    }
    @GetMapping("/check-application-category/{applicationId}")
    public ApiResponse<String> checkApplicationCategory(@PathVariable(name = "applicationId") Long applicationId){
        return ApiResponse.ok(applicationInfoService.checkApplicationCategory(applicationId));
    }

    @PutMapping("/edit-name-address")
    public void updateApplicationNameAddressInfo(@RequestBody ApplicationInfoRequestLightDto request) {
        applicationInfoFacade.updateApplicationNameAddressInfo(request);
    }
    
    @PutMapping("/edit-new-name-address")
    public void updateApplicationNewNameAddressInfo(@RequestBody ApplicationInfoRequestLightDto request) {
        applicationInfoFacade.updateApplicationNewNameAddressInfo(request);
    }
    @PostMapping(value = "/{id}/application-number-patent")
    public void generateApplicationNumberPatent(@PathVariable Long id) {
        applicationInfoService.generateApplicationNumberPatent(id);
    }
    @PostMapping(value = "/{id}/grant-number-patent")
    public void generateGrantNumberPatent(@PathVariable Long id) {
        applicationInfoService.generateGrantNumberPatent(id);
    }

    @PostMapping(value = "/{id}/application-number-trademark")
    public void generateTrademarkApplicationNumber(@PathVariable Long id) {
        applicationInfoService.generateTrademarkApplicationNumber(id);
    }

    @GetMapping("/similar-application/{applicationNumber}")
    public ApiResponse<ApplicationInfoDto> getSimilarApplicationByApplicationNumber(@PathVariable(value = "applicationNumber") String applicationNumber){
        return ApiResponse.ok(applicationInfoService.getGrantedApplicationDetailsByApplicationNumber(applicationNumber));
    }

    @GetMapping("/customer/main-applications")
    public ApiResponse<List<PartialApplicationInfoProjection>> listCustomerMainApplications(@RequestParam(name="applicationNumber", required = false) String applicationNumber){
        return ApiResponse.ok(applicationInfoService.listCustomerMainApplications(applicationNumber, null, null));
    }


    @PutMapping("/initiate-examination/{id}")
    public ApiResponse<Void> initiateExamination(@PathVariable(name = "id") Long id){
        applicationInfoService.initiateExamination(id);
        return ApiResponse.noContent();
    }
    
    @PutMapping("/{id}")
    public ApiResponse<Long> updateApplicationInfo(@PathVariable(name = "id") Long appId,
                                                  @RequestBody ApplicationRequestBodyDto requestDto) {
        return ApiResponse.ok(applicationInfoService.updateApplicationInfo(appId, requestDto));
    }
    
    @GetMapping("/{id}/national-security")
    ApiResponse<Boolean> getNationalSecurity(@PathVariable(name = "id") Long appId){
        return ApiResponse.ok(applicationInfoService.getNationalSecurity(appId));
    }


//    @GetMapping("/patent-change-log/{appId}")
//    public ApiResponse<String> getPatentLogs(@PathVariable Long appId){
//        return ApiResponse.ok(applicationInfoService.getPatentAttributeChangeLogs(appId));
//    }

    @GetMapping("/applicant-relevant-type-unpaid/{id}")
    ApiResponse<Long> getUnPaidCountApplicationRelavantType(@PathVariable(name = "id") Long appId){
        return ApiResponse.ok(applicationInfoService.getUnPaidCountApplicationRelavantType(appId));
    }

    @PostMapping(value = "/update-partial-application/{id}")
    public void updateFillingDatePartialApplication(@PathVariable Long id) {
        applicationInfoService.updateFillingDatePartialApplication(id);
    }



    @GetMapping("/application-details/{id}")
    public ApiResponse<ApplicationAdditionalDetailsDto> getApplicationAdditionalDetailsById(@PathVariable(name = "id") Long id){
        return ApiResponse.ok(applicationInfoService.getApplicationAdditionalDetailsById(id));
    }

    @GetMapping("/application-request-number/{id}")
    public ApiResponse<String> getApplicationRequestNumber(@PathVariable(name = "id") Long id) {
        return ApiResponse.ok(applicationInfoService.getApplicationRequestNumber(id));
    }
    
    @GetMapping("/processes-requests-ids")
    ApiResponse<List<Long>> getProcessesRequestsIdsByFilters(@ModelAttribute SearchDto searchDto){
        return ApiResponse.ok(applicationInfoService.getProcessesRequestsIdsByFilters(searchDto));
    }

    @GetMapping("/basic-info")
    ApiResponse<ApplicationInfoBaseDto> getApplicationBasicInfo(@RequestParam(name = "appId") Long appId){
        return ApiResponse.ok(applicationInfoService.getAppBasicInfo(appId));
    }

    @GetMapping("/applicant-relevant-type-waiver-document/{id}")
    ApiResponse<Boolean> hasApplicationRelavantTypeWithOutWaiverDocument(@PathVariable(name = "id") Long appId){
        return ApiResponse.ok(applicationRelevantTypeService.hasApplicationRelavantTypeWithOutWaiverDocument(appId));
    }



    @PutMapping("/update-status/batch")
    ApiResponse<Void> updateApplicationBatchByProcessRequestId(@RequestBody UpdateApplicationsStatusDto dto) {
        applicationInfoService.updateApplicationBatchByProcessRequestId(dto);
        return ApiResponse.noContent();
    }

    @GetMapping("/is-authorized/{applicationId}")
    @CheckCustomerAccess
    public void authorizeApplicationCustomer(@PathVariable(name = "applicationId") Long applicationId){
    }

    @PutMapping("/update-status/application/{applicationId}")
    ApiResponse<Void> updateApplicationStatusByIdAndStatusCode(@PathVariable(name = "applicationId") Long applicationId,
                                                               @RequestBody UpdateApplicationsStatusDto applicationsStatusDto) {
        applicationInfoService.updateApplicationStatusByIdAndStatusCode(applicationId, applicationsStatusDto.getStatusCode(), applicationsStatusDto.getCategoryCode());
        return ApiResponse.ok();
    }

    @PutMapping("/update-status/applications")
    ApiResponse<Void> updateApplicationsStatusByIdsAndStatusCode(@RequestBody UpdateApplicationsStatusDto applicationsStatusDto) {
        applicationInfoService.updateApplicationsStatusByIdsAndStatusCode(applicationsStatusDto.getIds(),
                applicationsStatusDto.getStatusCode(), applicationsStatusDto.getCategoryCode());
        return ApiResponse.ok();
    }

    @PutMapping(value = "/{applicationId}/grant-number-ic")
    public void generateGrantNumberDateIC(@PathVariable Long applicationId) {
        applicationInfoService.generateGrantNumberDateIC(applicationId);
    }
}

