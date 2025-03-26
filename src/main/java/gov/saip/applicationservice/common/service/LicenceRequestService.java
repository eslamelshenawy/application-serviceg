package gov.saip.applicationservice.common.service;


import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.dto.supportService.license.LicenceListSortingColumn;
import gov.saip.applicationservice.common.dto.supportService.license.OppositionRevokeLicenceListSortingColumn;
import gov.saip.applicationservice.common.dto.supportService.license.RevokeLicenceListSortingColumn;
import gov.saip.applicationservice.common.enums.LicenceTypeEnum;
import gov.saip.applicationservice.common.enums.SupportServiceRequestStatusEnum;
import gov.saip.applicationservice.common.model.LicenceRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface LicenceRequestService extends SupportServiceRequestService<LicenceRequest> {
    LicenceRequestDto getLicenceRequest(Long id);

    CustomerCodeAndApplicationIdDTO getLicenceRequestCustomerCodeAndApplicationId(Long id);

    String getLicensingRequestType(Long applicationSupportServiceId);

    List<CustomerSampleInfoDto> getLicensedCustomersDetails(Long applicationId);

    boolean checkApplicationHaveLicence(Long applicationId);

    boolean checkApplicationCancelLicence(Long id);

 	List<CustomerSampleInfoDto> getLicenseRequestAllInvolvedUsersInfo(Long id);

    LicenceRequestApplicationSummaryDto getApplicationSummaryByRequestLicenseId(Long id);

    PaginationDto<List<LicenceRequestListDto>> getAllApprovedLicenseRequests(String query, int page, int limit, LicenceListSortingColumn sortOrder, Sort.Direction sortDirection, SupportServiceRequestStatusEnum status);

    PaginationDto<List<LicenceRequestListDto>> getAllRevokedLicenseRequests(String query, int page, int limit, RevokeLicenceListSortingColumn sortOrder, Sort.Direction sortDirection, SupportServiceRequestStatusEnum status);

    PaginationDto<List<LicenceRequestListDto>> getAllOppositionLicenseRequests(String query, int page, int limit, OppositionRevokeLicenceListSortingColumn sortOrder, Sort.Direction sortDirection, SupportServiceRequestStatusEnum status);

    String getLicensedCustomerCodeByLicenseId(Long id);

    List<LicenceRequestListDto> getAllApprovedLicensedRequests(Long appId, LicenceTypeEnum licenceType);
    void changeLicenceValidityNumber(Long applicationId,Integer licenceValidityNumber,LicenceTypeEnum licenceType, Long mainRequestId);
    void makeCancelLicenceRequest(Long applicationId, Long mainRequestId);
    LicenceRequestDto updateLicenceRequest( LicenceRequestDto licenceRequestDto)  ;

}
