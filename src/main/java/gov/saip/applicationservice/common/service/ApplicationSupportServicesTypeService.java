package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.common.dto.ChangeOwnerShipReportDTO;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.lookup.LkSupportServiceRequestStatusDto;
import gov.saip.applicationservice.common.dto.search.SearchDto;
import gov.saip.applicationservice.common.dto.supportService.SupportServiceHelperInfoDto;
import gov.saip.applicationservice.common.enums.SupportServiceRequestStatusEnum;
import gov.saip.applicationservice.common.enums.SupportServiceType;
import gov.saip.applicationservice.common.model.ApplicationSupportServicesType;

import java.time.LocalDateTime;
import java.util.List;

public interface ApplicationSupportServicesTypeService extends SupportServiceRequestService<ApplicationSupportServicesType> {

    List<ApplicationSupportServicesType> getAllByApplicationId(Long appId);

    List<Long> findProcessesRequestsIdsByFilters(SearchDto searchDto);

    PaginationDto getPreviousRequestsByFilter(Integer page, Integer limit, String query, SearchDto searchDto, boolean isInternal);

    void validateApplicationSupportServicesTypeExists(Long id);
    List<Long>getApplicationIdsForSpecificService(SupportServiceType serviceCode,Long customerId);
    List<Long> getProcessRequestIdsBySearchCriteria(String query, String requestNumber);

    List<ChangeOwnerShipReportDTO> getOwnerShipChangedData(LocalDateTime startDate, LocalDateTime endDate);

    Long getLastSupportServiceByTypeAndApplicationِAndStatus(SupportServiceType type, Long appId, List<String> status);


    void initiateExamination(Long id);
    String getSupportServicesRequestNumber(Long supportServiceId);

    Long insertRenewalFeesRequest(ApplicationSupportServicesType dto);
    Long getLastSupportServiceRequestServiceId(Long appId, SupportServiceType type);

    Long getApplicationIdByServiceNumber(String serviceNumber);

    ApplicationSupportServicesType getSupportServiceTypeById(Long id);

    boolean applicationSupportServicesTypeLicencedExists(Long id);

    SupportServiceType getServiceTypeByServiceId(Long id);
    List<ApplicationSupportServicesType> getSupportServiceByAppIdAndStatusAndTypeAndCustomerCode(Long appId, SupportServiceRequestStatusEnum status, SupportServiceType type, String customerCode);
    List<ApplicationSupportServicesType> hasLicensingModificationAndOwnerShipPermission(Long appId, SupportServiceRequestStatusEnum status, SupportServiceType type, String customerCode);

    LkSupportServiceRequestStatusDto getSupportServiceStatus(Long id);

    String getSupportServiceStatusCode(Long serviceId);

    SupportServiceHelperInfoDto getCreatedByCustomerCodeAndApplicationIdById(Long serviceId);
}
