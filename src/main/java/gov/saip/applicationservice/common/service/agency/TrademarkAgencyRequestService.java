package gov.saip.applicationservice.common.service.agency;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.BaseStatusChangeLogDto;
import gov.saip.applicationservice.common.dto.CustomerSampleInfoDto;
import gov.saip.applicationservice.common.dto.CustomerSearchResultDto;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.agency.TMAgencyRequestDataDto;
import gov.saip.applicationservice.common.dto.agency.TrademarkAgencyRequestDto;
import gov.saip.applicationservice.common.dto.agency.TrademarkAgencyRequestListDto;
import gov.saip.applicationservice.common.enums.ApplicantType;
import gov.saip.applicationservice.common.enums.SupportServiceType;
import gov.saip.applicationservice.common.enums.agency.TrademarkAgencyType;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.agency.TrademarkAgencyRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface TrademarkAgencyRequestService extends BaseService<TrademarkAgencyRequest, Long> {

    void updateAgencyCheckerDecision(TrademarkAgencyRequestDto dto);
    TrademarkAgencyRequestDto getRequestDetailsById(Long id);
    PaginationDto<List<TrademarkAgencyRequestListDto>> filterAndListAgenciesRequests(boolean prev, String query, Integer statusId, String customerCode, Pageable pageable);
    void updateCheckerUsername(Long id, String checkerUsername);

    void validateAgencySearchResult(ApplicationInfo application, TrademarkAgencyType trademarkAgencyType);

 	CustomerSampleInfoDto getAgentCustomerSampleInfoDtoByRequestNumberOfChangeOwnershipRequest(String applicationId);

    String getAgentCustomerCodeByRequestNumber(String requestNumber);

    List<CustomerSearchResultDto> searchInActiveRegistrationAgenciesByAgentAndClientCodes(String agentCode);
    PaginationDto<List<TrademarkAgencyRequestListDto>> searchChangeOwnershipAccepted(Pageable pageable);

    List<TrademarkAgencyRequest> getActiveAgenciesByAgentAndClientCodes(String agentCode, String clientCode, TrademarkAgencyType agencyType);

    void applyAgencyAfterRequestApproval(Long id);

    boolean isAgentHasPermissionForApplicationSupportService(String agentCode, String customerCode, Long appId, SupportServiceType serviceCode, Long parentServiceId);

//    TrademarkAgencyRequest getAgencyWithPermissionForTrademarkAgencyType(String agentCode, String customerCode, TrademarkAgencyType type);

    TrademarkAgencyRequest getAgencyForServicesByAgentAndAppAndServiceType(String agentCode, String customerCode, Long appId, SupportServiceType serviceCode);

    void validateAgencyExistAndActiveForSupportServiceBySupportServiceParentId(String agencyRequestNumber, SupportServiceType serviceCode, ApplicantType applicantType, Long parentSupportServiceId);

    void validateAgencyExistAndActiveForSupportServiceByAgencyRequestNumberAndAgentCodeWithGettingMainOwnerIfNeeded(String agencyRequestNumber, SupportServiceType serviceCode, ApplicantType applicantType, Long applicationId);

    void validateAgencyExistAndActiveForSupportService(String agentCode, String customerCode, String agencyRequestNumber, SupportServiceType serviceCode);
 	TrademarkAgencyRequest getActiveAgentAgnecyRequestOnApplication(String agentCode, String agencyRequestNumber, Long applicationId, TrademarkAgencyType agencyType);

    String getAgencyCustomerCodeByRequestNumber(String agencyRequestNumber);
    List<Long> getProcessRequestIdsByAgencyRequestNumber(String agencyRequestNumber, LocalDate fromFilingDate, LocalDate toFilingDate);

    CustomerSampleInfoDto getAgentInfo(SupportServiceType serviceCode, String customerCode, Long applicationId);

    List<String> getAgentCodesByApplicationIdAndSupportServiceCode(SupportServiceType serviceCode, Long applicationId);

    List<BaseStatusChangeLogDto> getByTrademarkAgencyStatusChangeLogById(Long id);

    String getTrademarkAgencyRequestNumberById(Long id);
    TMAgencyRequestDataDto getTMAgencyRequestDataById(Long id);

    public void updateTrademarkAgencyToExpiredStatus();
    
    public List<Long> getExpiredTrademarkAgencyProcessRequestIds();
    List<TrademarkAgencyRequestDto> getRequestByStatusAndAppAndClientAndTypeForInvestigation(String clientCustomerCode, String agentCustomerCode);
}
