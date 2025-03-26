package gov.saip.applicationservice.util;

import gov.saip.applicationservice.common.dto.CustomerCodeListDto;
import gov.saip.applicationservice.common.dto.CustomerSampleInfoDto;
import gov.saip.applicationservice.common.dto.ListBodyDto;
import gov.saip.applicationservice.common.enums.ApplicantType;
import gov.saip.applicationservice.common.enums.SupportServiceType;
import gov.saip.applicationservice.common.model.LicenceRequest;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.CustomerServiceCaller;
import gov.saip.applicationservice.common.service.agency.TrademarkAgencyRequestService;
import gov.saip.applicationservice.exception.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class LicenceRequestUtil {

    private final TrademarkAgencyRequestService trademarkAgencyRequestService;
    private final CustomerServiceCaller customerServiceCaller;
    private final ApplicationInfoService applicationInfoService;


    public void validateLicenceRequestOwner(ApplicantType applicantType, String agencyRequestNumber, Long CustomerId, Long applicationId, SupportServiceType supportServiceType) {
        String supportServiceRequestApplicantCustomerCode = Utilities.getCustomerCodeFromHeaders();
        String mainApplicationApplicantCustomerCode = applicationInfoService.getMainApplicantCustomerCodeByApplicationInfoId(applicationId);
        String licensedCustomerCode = customerServiceCaller.getCustomerCodeByCustomerId(CustomerId);
        validateMainApplicationOwnerIsNotTheLicensedCustomer(mainApplicationApplicantCustomerCode, licensedCustomerCode);

        switch(applicantType) {
            case OWNER:
                validateSameUserCodes(supportServiceRequestApplicantCustomerCode, mainApplicationApplicantCustomerCode);
                break;
            case OWNERS_AGENT:
                validateAgencyWithAgentCodeAndCustomerCodeAndRequestNumber(supportServiceRequestApplicantCustomerCode, mainApplicationApplicantCustomerCode, agencyRequestNumber, supportServiceType);
                break;
            case LICENSED_CUSTOMER:
                validateSameUserCodes(supportServiceRequestApplicantCustomerCode, licensedCustomerCode);
                break;
            case LICENSED_CUSTOMER_AGENT:
                if(!supportServiceType.equals(SupportServiceType.REVOKE_LICENSE_REQUEST))
                    validateAgencyWithAgentCodeAndCustomerCodeAndRequestNumber(supportServiceRequestApplicantCustomerCode , licensedCustomerCode, agencyRequestNumber, supportServiceType);
                break;
        }
    }

    private void validateMainApplicationOwnerIsNotTheLicensedCustomer(String mainApplicationApplicantCustomerCode, String licensedCustomerCode) {
        if(mainApplicationApplicantCustomerCode.equals(licensedCustomerCode))
            throw new BusinessException(Constants.ErrorKeys.APPLICATION_OWNER_CANT_BE_LICENSED_CUSTOMER, HttpStatus.BAD_REQUEST);
    }

    private static void validateSameUserCodes(String supportServiceRequestApplicantCustomerCode, String mainApplicationApplicantCustomerCode) {
        if(!supportServiceRequestApplicantCustomerCode.equals(mainApplicationApplicantCustomerCode))
            throw new BusinessException(Constants.ErrorKeys.VALIDATION_APPLICANT_TYPE_IS_NOT_CORRECT, HttpStatus.BAD_REQUEST);
    }

    private void validateAgencyWithAgentCodeAndCustomerCodeAndRequestNumber(String supportServiceRequestApplicantCustomerCode, String mainApplicationApplicantCode, String agencyRequestNumber, SupportServiceType supportServiceType) {
        trademarkAgencyRequestService.validateAgencyExistAndActiveForSupportService(supportServiceRequestApplicantCustomerCode, mainApplicationApplicantCode, agencyRequestNumber, supportServiceType);
    }

    public List<CustomerSampleInfoDto> getLicenseRequestAllInvolvedUsersInfo(LicenceRequest licenceRequest, List<String> codes) {
        List<CustomerSampleInfoDto> CustomerSampleInfoDtos = getCustomersInfoByCodes(licenceRequest, codes);
        CustomerSampleInfoDtos.addAll(getCustomerInfo(licenceRequest.getCustomerId()));
        return CustomerSampleInfoDtos;
    }

    private List<CustomerSampleInfoDto> getCustomersInfoByCodes(LicenceRequest licenceRequest, List<String> codes) {
        CustomerCodeListDto customerCodeListDto = new CustomerCodeListDto();
        customerCodeListDto.setCustomerCode(codes);
        List<CustomerSampleInfoDto> CustomerSampleInfoDtos = customerServiceCaller.getCustomerByListOfCode(customerCodeListDto).getPayload();
        return CustomerSampleInfoDtos;
    }


    private List<CustomerSampleInfoDto> getCustomerInfo(Long customerId) {
        List<CustomerSampleInfoDto> CustomerSampleInfoDtos = new ArrayList<>();
        ListBodyDto listBodyDto = new ListBodyDto<>(Arrays.asList(customerId));
        Map<Long, CustomerSampleInfoDto> customerCodeListDtoMap = customerServiceCaller.getCustomersByIds(listBodyDto);
        if (customerCodeListDtoMap != null && !customerCodeListDtoMap.isEmpty())
            CustomerSampleInfoDtos = customerCodeListDtoMap.values().stream().toList();

        return CustomerSampleInfoDtos;
    }

    public List<String> getLicenseOwnerAndAgentCodes(LicenceRequest licenceRequest) {
        List<String> codes = new ArrayList<>();
        String mainApplicationApplicantCustomerCode = Utilities.extractCustomerCode(licenceRequest.getApplicationInfo());
        codes.add(mainApplicationApplicantCustomerCode);
        String agentCode = getAgentCodeByAgencyRequestNumber(licenceRequest.getAgencyRequestNumber(), licenceRequest.getApplicantType());
        if (agentCode != null)
            codes.add(agentCode);
        return codes;
    }

    public String getAgentCodeByAgencyRequestNumber(String agencyRequestNumber, ApplicantType applicantType) {
        String agentCode = null;
        if (applicantType.equals(ApplicantType.LICENSED_CUSTOMER_AGENT) ||
                applicantType.equals(ApplicantType.OWNERS_AGENT))
            agentCode = trademarkAgencyRequestService.getAgentCustomerCodeByRequestNumber(agencyRequestNumber);
        return agentCode;
    }
}
