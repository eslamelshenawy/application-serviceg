package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApplicationNumberGenerationDto;
import gov.saip.applicationservice.common.dto.bpm.StartProcessDto;
import gov.saip.applicationservice.common.enums.SupportServicePaymentStatus;
import gov.saip.applicationservice.common.enums.SupportServiceRequestStatusEnum;
import gov.saip.applicationservice.common.enums.SupportServiceType;
import gov.saip.applicationservice.common.model.ApplicationSupportServicesType;
import gov.saip.applicationservice.common.model.LKSupportServiceRequestStatus;

import java.util.List;

public interface SupportServiceRequestService<E extends ApplicationSupportServicesType> extends BaseService<E, Long> {
    E insert(SupportServiceType supportServiceType, E applicationSupportServicesType);

    void updateRequestStatusById(Long id, Integer newStatusId);

    List<E> getAllByApplicationId(Long appId, SupportServiceType type);

    void updateRequestStatusByCode(Long id, SupportServiceRequestStatusEnum newStatusCode);

    void paymentCallBackHandler(Long id, ApplicationNumberGenerationDto applicationNumberGenerationDto);

    void paymentCallBackHandler(Long id);

    void updatePaymentStatusAndRequestStatus(SupportServicePaymentStatus paymentStatus, SupportServiceRequestStatusEnum requestStatus, Long id);
    void updateProcessRequestId(Long processRequestId, Long id);

    SupportServiceRequestStatusEnum getPaymentRequestStatus();

    void startSupportServiceProcess(E entity, StartProcessDto startProcessDto);
    LKSupportServiceRequestStatus getStatusBySupportServiceId(Long id);

    String getProcessRequestIdById(Long id);

    boolean isCustomerServiceApplicant(Long parentServiceId, String customerCode);
}
