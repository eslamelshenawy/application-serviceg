package gov.saip.applicationservice.common.controllers.agency;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.dto.agency.TMAgencyRequestDataDto;
import gov.saip.applicationservice.common.dto.agency.TrademarkAgencyRequestDto;
import gov.saip.applicationservice.common.dto.agency.TrademarkAgencyRequestListDto;
import gov.saip.applicationservice.common.enums.ApplicantType;
import gov.saip.applicationservice.common.enums.SupportServiceType;
import gov.saip.applicationservice.common.mapper.agency.TrademarkAgencyRequestMapper;
import gov.saip.applicationservice.common.model.agency.TrademarkAgencyRequest;
import gov.saip.applicationservice.common.service.agency.TrademarkAgencyRequestService;
import gov.saip.applicationservice.util.Utilities;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( value = {"/kc/tm-agency", "/internal-calling/tm-agency"})
@RequiredArgsConstructor
public class TrademarkAgencyRequestController extends BaseController<TrademarkAgencyRequest, TrademarkAgencyRequestDto, Long> {

    private final TrademarkAgencyRequestService trademarkAgencyRequestService;
    private final TrademarkAgencyRequestMapper trademarkAgencyRequestMapper;

    @Override
    protected BaseService<TrademarkAgencyRequest, Long> getService() {
        return  trademarkAgencyRequestService;
    }

    @Override
    protected BaseMapper<TrademarkAgencyRequest, TrademarkAgencyRequestDto> getMapper() {
        return trademarkAgencyRequestMapper;
    }

    @PutMapping("/checker-decision")
    public ApiResponse<Void> updateCheckerDecision(@RequestBody TrademarkAgencyRequestDto dto) {
        trademarkAgencyRequestService.updateAgencyCheckerDecision(dto);
        return ApiResponse.noContent();
    }

    @GetMapping("/{id}/id")
    public ApiResponse<TrademarkAgencyRequestDto> getRequestDetails(@PathVariable Long id) {
        TrademarkAgencyRequestDto trademarkAgencyRequestDto = trademarkAgencyRequestService.getRequestDetailsById(id);
        return ApiResponse.ok(trademarkAgencyRequestDto);
    }


    @GetMapping("/paginated")
    public ApiResponse<PaginationDto<List<TrademarkAgencyRequestListDto>>>  getAgencyRequestList(@RequestParam(value = "prev", required = false) boolean prev,
                                                                                                 @RequestParam(value = "query", required = false) String query,
                                                                                                 @RequestParam(value = "status", required = false) Integer statusId,
                                                                                                 @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                                                                 @RequestParam(value = "limit", defaultValue = "10") Integer limit
                                                  ) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by("id").descending());
        return ApiResponse.ok(trademarkAgencyRequestService.filterAndListAgenciesRequests(prev, query, statusId, Utilities.getCustomerCodeFromHeaders(), pageable));
    }


    @PostMapping("/update-checker")
    public ApiResponse<Void>  updateCheckerUsername(@RequestBody TrademarkAgencyRequestDto dto) {
        trademarkAgencyRequestService.updateCheckerUsername(dto.getId(), dto.getCheckerUsername());
        return ApiResponse.noContent();
    }

    @GetMapping(value = "/search-in-agent-customers/{code}")
    public ApiResponse<List<CustomerSearchResultDto>> searchInAgentActiveCustomers(@PathVariable String code) {
        return ApiResponse.ok(trademarkAgencyRequestService.searchInActiveRegistrationAgenciesByAgentAndClientCodes(code));
    }

    @GetMapping(value = "/search-change-ownership-accepted")
    public ApiResponse<PaginationDto<List<TrademarkAgencyRequestListDto>>> searchChangeOwnershipAccepted(
                                                                                                         @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                                                                         @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by("id").descending());

        return ApiResponse.ok(trademarkAgencyRequestService.searchChangeOwnershipAccepted(pageable));
    }

    @PostMapping("/approval-actions/{id}")
    public ApiResponse<Void> applyAgencyAfterRequestApproval(@PathVariable(name = "id") Long id) {
        trademarkAgencyRequestService.applyAgencyAfterRequestApproval(id);
        return ApiResponse.noContent();
    }

    @GetMapping("/validate/parentService/{parentSupportServiceId}")
    public ApiResponse validateAgencyExistAndActiveForSupportServiceByItsParentId( @RequestParam String agencyNumber, @RequestParam SupportServiceType serviceCode, @RequestParam ApplicantType applicantType, @PathVariable Long parentSupportServiceId){
        trademarkAgencyRequestService.validateAgencyExistAndActiveForSupportServiceBySupportServiceParentId(agencyNumber, serviceCode, applicantType, parentSupportServiceId);
        return ApiResponse.ok();
    }

    @GetMapping("/validate/application/{applicationId}")
    public ApiResponse validateAgencyExistAndActiveForSupportServiceByApplicationId (@RequestParam String agencyNumber, @RequestParam SupportServiceType serviceCode, @RequestParam ApplicantType applicantType, @PathVariable Long applicationId){
        trademarkAgencyRequestService.validateAgencyExistAndActiveForSupportServiceByAgencyRequestNumberAndAgentCodeWithGettingMainOwnerIfNeeded(agencyNumber, serviceCode, applicantType, applicationId);
        return ApiResponse.ok();
    }

    @GetMapping("/agentInfo")
    ApiResponse<CustomerSampleInfoDto> getAgentInfo(@RequestParam(name = "serviceCode") SupportServiceType serviceCode, @RequestParam(name = "customerCode") String customerCode, @RequestParam(name = "applicationId") Long applicationId){
        return ApiResponse.ok(trademarkAgencyRequestService.getAgentInfo(serviceCode, customerCode, applicationId));
    }

    @GetMapping("/{id}/status-log")
    ApiResponse<List<BaseStatusChangeLogDto>> getByTrademarkAgencyStatusChangeLogById(@PathVariable(name = "id") Long id) {
        return ApiResponse.ok(trademarkAgencyRequestService.getByTrademarkAgencyStatusChangeLogById(id));
    }

    @GetMapping("/{id}/agency-request-number")
    ApiResponse<String> getTrademarkAgencyRequestNumberById(@PathVariable(name = "id") Long id) {
        return ApiResponse.ok(trademarkAgencyRequestService.getTrademarkAgencyRequestNumberById(id));
    }

    @GetMapping("/{id}/agency-data")
    ApiResponse<TMAgencyRequestDataDto> getTMAgencyRequestDataById(@PathVariable(name = "id") Long id) {
        return ApiResponse.ok(trademarkAgencyRequestService.getTMAgencyRequestDataById(id));
    }

    @GetMapping("/check-existence")
    ApiResponse<List<TrademarkAgencyRequestDto>> getByClientCodeAndStatusAndAgencyTypeForInvestigation(@RequestParam(name = "clientCustomerCode") String clientCustomerCode,
                                                                                              @RequestParam(name = "agentCustomerCode") String agentCustomerCode) {
        return ApiResponse.ok(trademarkAgencyRequestService.getRequestByStatusAndAppAndClientAndTypeForInvestigation(clientCustomerCode, agentCustomerCode));
    }
}
