package gov.saip.applicationservice.common.service;


import gov.saip.applicationservice.common.dto.CustomerSampleInfoDto;
import gov.saip.applicationservice.common.dto.RevokeLicenceRequestApplicationSummaryDto;
import gov.saip.applicationservice.common.dto.RevokeLicenceRequestDto;
import gov.saip.applicationservice.common.model.RevokeLicenceRequest;

import java.util.List;

public interface RevokeLicenceRequestService extends SupportServiceRequestService<RevokeLicenceRequest> {
    List<CustomerSampleInfoDto> getLicenseRequestAllInvolvedUsersInfo(Long id);

    RevokeLicenceRequestApplicationSummaryDto getApplicationSummaryByRevokeLicenseRequestId(Long id);

    RevokeLicenceRequestDto findByServiceId(Long id);

    String getLicensedCustomerCodeByRevokeLicenseId(Long parentServiceId);

    Long getLicenseRequestIdByRevokeLicenseId(Long id);
}
