package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.common.clients.rest.feigns.CustomerServiceFeignClient;
import gov.saip.applicationservice.common.clients.rest.feigns.PaymentFeeCostFeignClient;
import gov.saip.applicationservice.common.dto.ApplicationNumberGenerationDto;
import gov.saip.applicationservice.common.dto.CustomerSampleInfoDto;
import gov.saip.applicationservice.common.dto.EvictionRequestDto;
import gov.saip.applicationservice.common.dto.supportService.SupportServiceStatusChangeLogDto;
import gov.saip.applicationservice.common.enums.*;
import gov.saip.applicationservice.common.mapper.EvictionRequestMapper;
import gov.saip.applicationservice.common.model.EvictionRequest;
import gov.saip.applicationservice.common.repository.EvictionRequestRepository;
import gov.saip.applicationservice.common.repository.SupportServiceRequestRepository;
import gov.saip.applicationservice.common.service.ApplicationStatusChangeLogService;
import gov.saip.applicationservice.common.service.EvictionRequestService;
import gov.saip.applicationservice.common.service.LKSupportServiceRequestStatusService;
import gov.saip.applicationservice.common.service.supportService.SupportServiceStatusChangeLogService;
import gov.saip.applicationservice.common.util.SupportServiceActivityLogHelper;
import gov.saip.applicationservice.util.Utilities;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;

import static gov.saip.applicationservice.common.enums.SupportServiceType.EVICTION;


@Service
@RequiredArgsConstructor
@Transactional
public class EvictionRequestServiceImpl extends SupportServiceRequestServiceImpl<EvictionRequest> implements EvictionRequestService {

    private final EvictionRequestRepository evictionRequestRepository;
    private final ApplicationStatusChangeLogService applicationStatusChangeLogService;
    private final SupportServiceActivityLogHelper supportServiceActivityLogHelper;
    private final SupportServiceStatusChangeLogService supportServiceStatusChangeLogService;
    private final EvictionRequestMapper evictionRequestMapper;
    private final LKSupportServiceRequestStatusService lkSupportServiceRequestStatusService;
    private final CustomerServiceFeignClient customerServiceFeignClient;
    private final ApplicationServiceImpl applicationService;
    public static final String EVICTION_LOG_TASK_NAME_AR = "تخلى عن الطلب";
    public static final String EVICTION_LOG_TASK_NAME_EN = "ُApplication Eviction";


    @Override
    public SupportServiceRequestRepository getSupportServiceRequestRepository() {
        return evictionRequestRepository;
    }

    @Override
    public EvictionRequest insert(EvictionRequest entity){
      return super.insert(EVICTION, entity);

    }

    @Override
    public EvictionRequest update(EvictionRequest entity) {
        EvictionRequest evictionRequest = findById(entity.getId());
        evictionRequest.setEvictionDocument(entity.getEvictionDocument() != null ? entity.getEvictionDocument() : evictionRequest.getEvictionDocument());
        evictionRequest.setDescDocument(entity.getDescDocument() != null ? entity.getDescDocument() : evictionRequest.getDescDocument());
        evictionRequest.setComment(entity.getComment() != null ? entity.getComment() : evictionRequest.getComment());
        return super.update(evictionRequest);
    }

    @Override
    public void paymentCallBackHandler(Long id, ApplicationNumberGenerationDto applicationNumberGenerationDto) {
        Long applicationId = super.findById(id).getApplicationInfo().getId();
        updateRequestStatus(id);
        String taskId = supportServiceActivityLogHelper.addActivityLogForSupportService(id, RequestActivityLogEnum.EVICTION, "DONE", EVICTION_LOG_TASK_NAME_AR, EVICTION_LOG_TASK_NAME_EN, applicationId );
        applicationStatusChangeLogService.changeApplicationStatusAndLog(ApplicationStatusEnum.ABANDONED.name(), null, applicationId, RequestActivityLogEnum.valueOf(SupportServiceType.EVICTION.name()).name() ,taskId);

        super.paymentCallBackHandler(id, applicationNumberGenerationDto);
    }



    private void updateRequestStatus(Long serviceId) {
        SupportServiceStatusChangeLogDto statusChangeLogDto = new SupportServiceStatusChangeLogDto();
        statusChangeLogDto.setSupportServicesTypeId(serviceId);
        statusChangeLogDto.setNewStatusCode(SupportServiceRequestStatusEnum.COMPLETED.name());
        supportServiceStatusChangeLogService.insert(statusChangeLogDto);
    }

    @Override
    public SupportServiceRequestStatusEnum getPaymentRequestStatus() {
        return SupportServiceRequestStatusEnum.COMPLETED;
    }


    @Override
    public EvictionRequest createEvictionRequest(EvictionRequestDto evictionRequestDto) {
        EvictionRequest evictionRequest = createAndInsertEvictionRequest(evictionRequestDto);
        CustomerSampleInfoDto customerSampleInfoDto = getCustomerInfo();
        Double serviceCost = calculateServiceCost(customerSampleInfoDto);

        if (serviceCost == 0) {
            updateEvictionRequest(evictionRequest, evictionRequestDto.getApplicationId());
        }
        return evictionRequest;
    }

    private EvictionRequest createAndInsertEvictionRequest(EvictionRequestDto evictionRequestDto) {
        return super.insert(EVICTION, evictionRequestMapper.unMap(evictionRequestDto));
    }

    private CustomerSampleInfoDto getCustomerInfo() {
        String customerId = Utilities.getCustomerIdFromHeaders();
        return customerServiceFeignClient.getAnyCustomerById(Long.valueOf(customerId)).getPayload();
    }

    private Double calculateServiceCost(CustomerSampleInfoDto customerSampleInfoDto) {
        return applicationService.getSupportServiceCost(
                ApplicationPaymentMainRequestTypesEnum.RELINQUISHMENT.name(),
                customerSampleInfoDto.getUserGroupCode().getValue(),
                ApplicationCategoryEnum.PATENT.getSaipCode()
        );
    }

    private void updateEvictionRequest(EvictionRequest evictionRequest, Long applicationId) {
        evictionRequest.setRequestStatus(
                lkSupportServiceRequestStatusService.getStatusByCode(SupportServiceRequestStatusEnum.COMPLETED.name())
        );
        evictionRequest.setPaymentStatus(SupportServicePaymentStatus.PAID);
        applicationStatusChangeLogService.changeApplicationStatusAndLog(
                ApplicationStatusEnum.ABANDONED.name(),
                null,
                applicationId
        );
        update(evictionRequest);
    }

}