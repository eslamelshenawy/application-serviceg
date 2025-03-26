package gov.saip.applicationservice.common.facade.impl;

import gov.saip.applicationservice.common.dto.ApplicationInfoBaseDto;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.enums.ApplicationCustomerType;
import gov.saip.applicationservice.common.enums.ApplicationStatusEnum;
import gov.saip.applicationservice.common.enums.SupportServiceType;
import gov.saip.applicationservice.common.enums.certificate.CertificateRequestPreCondition;
import gov.saip.applicationservice.common.enums.certificate.CertificateTypeEnum;
import gov.saip.applicationservice.common.facade.CertificateRequestFacade;
import gov.saip.applicationservice.common.model.ApplicationSupportServicesType;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.ApplicationSupportServicesTypeService;
import gov.saip.applicationservice.util.Utilities;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static gov.saip.applicationservice.util.Constants.CUSTOMER_TYPES_VALID_FOR_CERTIFICATES_BY_TYPE;

@Service
@RequiredArgsConstructor
public class CertificateRequestFacadeImpl implements CertificateRequestFacade {
    
    private final ApplicationInfoService applicationInfoService;
    private final ApplicationSupportServicesTypeService applicationSupportServicesTypeService;

    
    public ApplicationInfoBaseDto getApplicationsAccordingCertificatesPreConditions(String type, Long categoryId, String applicationNumber) {
        
        Long customerId = Utilities.getCustomerIdFromHeadersAsLong();
        List<ApplicationCustomerType> customerTypes = CUSTOMER_TYPES_VALID_FOR_CERTIFICATES_BY_TYPE.get(CertificateTypeEnum.valueOf(type));
        List<ApplicationStatusEnum> preConditionAppStatusEnums = CertificateRequestPreCondition.getApplicationStatusListByCategoryIdAndType(categoryId, type);
        List<String> preConditionAppStatus = preConditionAppStatusEnums.isEmpty() ? null : preConditionAppStatusEnums.stream().map(Enum::toString).toList();
        
        PaginationDto<List<ApplicationInfoBaseDto>> applications = applicationInfoService.findApplicationsBaseInfoDto(categoryId, preConditionAppStatus, applicationNumber,
                customerId, customerTypes, 0, 10);
        
        if (applications == null || applications.getContent() == null || applications.getContent().isEmpty())
            return null;

        if(CertificateTypeEnum.PROOF_FACTS_APPEAL.name().equalsIgnoreCase(type)){
            ApplicationSupportServicesType applicationSupportServicesType = validateApplicationHasAppealRequest(applicationNumber);
            if(Objects.nonNull(applicationSupportServicesType))
                return null;
        }

        ApplicationInfoBaseDto applicationInfoBase = applications.getContent().get(0);
        applicationInfoService.assignDataOfCustomersCode(applicationInfoBase.getApplicationRelevantTypes());
        return applicationInfoBase;
        
    }

    public ApplicationSupportServicesType validateApplicationHasAppealRequest(String applicationNumber){
        Long appId = applicationInfoService.getApplicationIdByApplicationNumber(applicationNumber);
        List<ApplicationSupportServicesType> appSupportServices = applicationSupportServicesTypeService.getAllByApplicationId(appId);

        for (ApplicationSupportServicesType applicationSupportServicesType : appSupportServices) {
            if (applicationSupportServicesType.getLkSupportServices().getCode().name()
                    .equalsIgnoreCase(SupportServiceType.APPEAL_REQUEST.name())) {
                return applicationSupportServicesType;
            }
        }
        return null;
    }
    
}
