package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.common.dto.CustomerSampleInfoDto;
import gov.saip.applicationservice.common.dto.customer.ApplicationAgentSummaryDto;
import gov.saip.applicationservice.common.enums.ApplicationCustomerType;
import gov.saip.applicationservice.common.enums.DocumentTypes;
import gov.saip.applicationservice.common.model.ApplicationCustomer;
import gov.saip.applicationservice.common.model.Document;
import gov.saip.applicationservice.common.model.agency.ApplicationAgent;
import gov.saip.applicationservice.common.service.ApplicationCustomerService;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.CustomerServiceCaller;
import gov.saip.applicationservice.common.service.agency.ApplicationAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

import static gov.saip.applicationservice.common.enums.ApplicationCategoryEnum.TRADEMARK;

@Service
public class ApplicationAgentFacadeService {

    private final ApplicationAgentService applicationAgentService;
    private final ApplicationInfoService applicationInfoService;
    private final ApplicationCustomerService applicationCustomerService;
    private final CustomerServiceCaller customerServiceCaller;

    @Autowired
    public ApplicationAgentFacadeService(@Lazy ApplicationAgentService applicationAgentService, @Lazy ApplicationInfoService applicationInfoService,
                                         @Lazy ApplicationCustomerService applicationCustomerService, @Lazy CustomerServiceCaller customerServiceCaller) {
        this.applicationAgentService = applicationAgentService;
        this.applicationInfoService = applicationInfoService;
        this.applicationCustomerService = applicationCustomerService;
        this.customerServiceCaller = customerServiceCaller;
    }

    public ApplicationAgentSummaryDto getApplicationCurrentAgentSummary(Long id) {
        if (TRADEMARK.name().equals(applicationInfoService.getApplicationCategoryById(id))) {
            return getTrademarkAgentSummary(id);
        }

        return getApplicationAgentSummary(id);
    }

    private ApplicationAgentSummaryDto getTrademarkAgentSummary(Long id) {
        ApplicationCustomer appCustomerByAppIdAndType = applicationCustomerService.getAppCustomerByAppIdAndType(id, ApplicationCustomerType.AGENT);
        if (Objects.isNull(appCustomerByAppIdAndType)) {
            return null;
        }

        return getAgentDetails(appCustomerByAppIdAndType.getCustomerId());
    }

    private ApplicationAgentSummaryDto getApplicationAgentSummary(Long applicationId) {
        ApplicationAgent currentActiveApplicationAgent = applicationAgentService.getCurrentApplicationAgentEntity(applicationId);
        if (Objects.isNull(currentActiveApplicationAgent)) {
            return null;
        }

        Optional<Document> poaOptional = currentActiveApplicationAgent.getApplicationAgentDocuments().stream().filter(doc -> Objects.nonNull(doc.getDocumentType()) && DocumentTypes.POA.getCode().equals(doc.getDocumentType().getCode())).findFirst();

        ApplicationAgentSummaryDto agentSummaryDto = getAgentDetails(currentActiveApplicationAgent.getCustomerId());
        agentSummaryDto.setPoaDocumentUrl(getPOAURL(poaOptional));
        return agentSummaryDto;
    }

    private ApplicationAgentSummaryDto getAgentDetails(Long currentActiveApplicationAgent) {
        ApplicationAgentSummaryDto agentSummaryDto = new ApplicationAgentSummaryDto();
        CustomerSampleInfoDto agent = customerServiceCaller.getAnyCustomerDetails(currentActiveApplicationAgent);
        if(agent.getAddress().getCountryObject() == null){
            agent.getAddress().setCountryObject(agent.getNationality());
        }
        agentSummaryDto.setAddress(agent.getAddress());
        agentSummaryDto.setNameAr(agent.getNameAr());
        agentSummaryDto.setNameEn(agent.getNameEn());
        agentSummaryDto.setCode(agent.getCode());
        agentSummaryDto.setIdentifier(agent.getIdentifier());
        return agentSummaryDto;
    }


    private static String getPOAURL(Optional<Document> poaOptional) {
        if (poaOptional.isEmpty()) {
            return null;
        }

        Document document = poaOptional.get();
        if (document == null || document.getNexuoId() == null || document.getNexuoId().isBlank()) {
            return null;
        }

        return document.getFileReviewUrl();
    }

}
