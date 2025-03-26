package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.annotation.CheckCustomerAccess;
import gov.saip.applicationservice.common.clients.rest.feigns.BPMCallerFeignClient;
import gov.saip.applicationservice.common.clients.rest.feigns.CustomerServiceFeignClient;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.dto.bpm.CompleteTaskRequestDto;
import gov.saip.applicationservice.common.dto.bpm.StartProcessDto;
import gov.saip.applicationservice.common.enums.RequestTypeEnum;
import gov.saip.applicationservice.common.enums.SupportServiceRequestStatusEnum;
import gov.saip.applicationservice.common.enums.ValidationType;
import gov.saip.applicationservice.common.mapper.RevokeProductsMapper;
import gov.saip.applicationservice.common.model.RevokeProducts;
import gov.saip.applicationservice.common.repository.RevokeProductsRepository;
import gov.saip.applicationservice.common.repository.SupportServiceRequestRepository;
import gov.saip.applicationservice.common.service.ApplicationSubClassificationService;
import gov.saip.applicationservice.common.service.LicenceRequestService;
import gov.saip.applicationservice.common.service.RevokeProductsService;
import gov.saip.applicationservice.common.service.agency.impl.SupportServiceCustomerImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static gov.saip.applicationservice.common.enums.SupportServiceType.REVOKE_PRODUCTS;


@Service
@RequiredArgsConstructor
@Transactional
public class RevokeProductsServiceImpl extends SupportServiceRequestServiceImpl<RevokeProducts> implements RevokeProductsService {

    private final RevokeProductsRepository revokeProductsRepository;
    private final RevokeProductsMapper revokeProductsMapper;
    private final BPMCallerFeignClient bpmCallerFeignClient;
    private final LicenceRequestService licenceRequestService;
    private final CustomerServiceFeignClient customerServiceFeignClient;
    private final SupportServiceCustomerImpl supportServiceCustomer;

    @Lazy
    @Autowired
    private ApplicationSubClassificationService applicationSubClassificationService;


    @Override
    public SupportServiceRequestRepository getSupportServiceRequestRepository() {
        return revokeProductsRepository;
    }


    @Override
    public RevokeProducts insert(RevokeProducts entity) {
        return super.insert(REVOKE_PRODUCTS, entity);
    }

    @Override
    public RevokeProducts update(RevokeProducts entity) {
        RevokeProducts revokeProducts = findById(entity.getId());
        revokeProducts.setNotes(entity.getNotes() != null ? entity.getNotes() : revokeProducts.getNotes());
        revokeProducts.setDocuments(entity.getDocuments());
        revokeProducts = super.update(revokeProducts);
        completeUserTask(revokeProducts);
        this.updateRequestStatusByCode(revokeProducts.getId(), SupportServiceRequestStatusEnum.UNDER_PROCEDURE);
        return revokeProducts;
    }


    public void completeUserTask(RevokeProducts revokeProducts) {
        RequestTasksDto requestTasksDto = bpmCallerFeignClient.getTaskByRowIdAndType(RequestTypeEnum.REVOKE_PRODUCTS, revokeProducts.getId()).getPayload();
        Map<String, Object> approved = new LinkedHashMap();
        approved.put("value", "YES");
        Map<String, Object> processVars = new LinkedHashMap<>();
        processVars.put("approved", approved);
        CompleteTaskRequestDto completeTaskRequestDto = new CompleteTaskRequestDto();
        completeTaskRequestDto.setVariables(processVars);
        bpmCallerFeignClient.completeUserTask(requestTasksDto.getTaskId(), completeTaskRequestDto);
    }


    @Override
    public SupportServiceRequestStatusEnum getPaymentRequestStatus() {
        return SupportServiceRequestStatusEnum.UNDER_PROCEDURE;
    }

    public List<Long> getRevokedSubClassificationsIdByApplicationId(Long appId) {
        return revokeProductsRepository.getRevokedSubClassificationsIdByApplicationId(appId);
    }
    
    @Override
    public List<Long> getRevokedSubClassificationsIdByApplicationIdAndSupportServiceId(Long appId, Long supportServiceId) {
        return revokeProductsRepository.getRevokedSubClassificationsIdByApplicationIdAndSupportServiceId(appId, supportServiceId);
    }
    
    @Override
    @CheckCustomerAccess(type = ValidationType.SUPPORT_SERVICES)
    public RevokeProductsDto findRevokeProductById(Long id) {
        RevokeProductsDto revokeProductsDto = revokeProductsMapper.map(this.findById(id));
        List<String> serviceAgentsCustomerCodes = supportServiceCustomer.getAgentsCustomerCodeByServiceId(id);
        if(serviceAgentsCustomerCodes.isEmpty()){
            return revokeProductsDto;
        }
        CustomerSampleInfoDto agentsDTo = customerServiceFeignClient.getAnyCustomerByCustomerCode(serviceAgentsCustomerCodes.get(0)).getPayload();
        revokeProductsDto.setAgentDetails(agentsDTo);
        return revokeProductsDto;
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public void paymentCallBackHandler(Long id, ApplicationNumberGenerationDto applicationNumberGenerationDto) {
        revokeProductsRequestPaymentCallBack(id);
        super.paymentCallBackHandler(id, applicationNumberGenerationDto);
    }


    private void revokeProductsRequestPaymentCallBack(Long id) {
        RevokeProducts entity = findById(id);
        CustomerSampleInfoDto customerSampleInfoDto = customerServiceFeignClient.getAnyCustomerByCustomerCode(entity.getCreatedByCustomerCode()).getPayload();
        StartProcessDto startProcessDto = StartProcessDto.builder()
                .id(entity.getId().toString())
                .applicantUserName(entity.getCreatedByUser())
                .fullNameAr(customerSampleInfoDto.getNameAr())
                .fullNameEn(customerSampleInfoDto.getNameEn())
                .mobile(customerSampleInfoDto.getMobile())
                .email(customerSampleInfoDto.getEmail())
                .applicationCategory(entity.getApplicationInfo().getCategory().getSaipCode())
                .processName("revoke_products_process")
                .applicationIdColumn(entity.getApplicationInfo().getId().toString())
                .requestTypeCode("REVOKE_PRODUCTS")
                .supportServiceCode(entity.getLkSupportServices().getCode().name())
                .identifier(id.toString())
                .requestNumber(entity.getRequestNumber())
                .build();
        if(licenceRequestService.checkApplicationHaveLicence(entity.getApplicationInfo().getId()))  // check application have licence
            startProcessDto.addVariable("approved", "YES");
        else
            startProcessDto.addVariable("approved", "NO");

        startSupportServiceProcess(entity, startProcessDto);
    }

    @Override
    public void deleteApplicationRevokedProductsByRevokeProductId(Long revokeProductId) {
        RevokeProducts RevokeProduct = this.findById(revokeProductId);
        Long applicationId = RevokeProduct.getApplicationInfo().getId();
        List<Long> subClassificationIds = RevokeProduct.getSubClassifications().stream().map(subClassification -> subClassification.getId()).collect(Collectors.toList());
        applicationSubClassificationService.deleteByAppIdAndSubClassificationIds(applicationId, subClassificationIds);
    }

}