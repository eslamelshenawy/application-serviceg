package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.common.dto.ApplicationNumberGenerationDto;
import gov.saip.applicationservice.common.dto.bpm.StartProcessDto;
import gov.saip.applicationservice.common.enums.SupportServiceRequestStatusEnum;
import gov.saip.applicationservice.common.enums.SupportServiceType;
import gov.saip.applicationservice.common.model.ProtectionExtendRequest;
import gov.saip.applicationservice.common.repository.ProtectionExtendRequestRepository;
import gov.saip.applicationservice.common.repository.SupportServiceRequestRepository;
import gov.saip.applicationservice.common.service.ProtectionExtendRequestService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class ProtectionExtendRequestServiceImpl extends SupportServiceRequestServiceImpl<ProtectionExtendRequest> implements ProtectionExtendRequestService {

    private final ProtectionExtendRequestRepository protectionExtendsRequestRepository;

    @Override
    public SupportServiceRequestRepository getSupportServiceRequestRepository() {
        return protectionExtendsRequestRepository;
    }

    @Override
    public ProtectionExtendRequest insert(ProtectionExtendRequest entity) {
        return super.insert(SupportServiceType.PROTECTION_PERIOD_EXTENSION_REQUEST, entity);
    }

    @Override
    public ProtectionExtendRequest update(ProtectionExtendRequest entity) {
        ProtectionExtendRequest protectionExtendRequest = findById(entity.getId());
        protectionExtendRequest.setProtectionExtendType(entity.getProtectionExtendType() != null ? entity.getProtectionExtendType() : protectionExtendRequest.getProtectionExtendType());
        protectionExtendRequest.setClaimCount(entity.getClaimCount() != 0 ? entity.getClaimCount() : protectionExtendRequest.getClaimCount());
        protectionExtendRequest.setClaimNumber(entity.getClaimNumber() != null ? entity.getClaimNumber() : protectionExtendRequest.getClaimNumber());
        protectionExtendRequest.setSupportDocument(entity.getSupportDocument() != null ? entity.getSupportDocument() : protectionExtendRequest.getSupportDocument());
        protectionExtendRequest.setPoaDocument(entity.getPoaDocument() != null ? entity.getPoaDocument() : protectionExtendRequest.getPoaDocument());
        return super.update(protectionExtendRequest);
    }

    @Override
    public SupportServiceRequestStatusEnum getPaymentRequestStatus() {
        return SupportServiceRequestStatusEnum.COMPLETED;
    }

    @Override
    @Transactional
    public void paymentCallBackHandler(Long id, ApplicationNumberGenerationDto applicationNumberGenerationDto) {
        protectionExtendRequestPaymentCallBack(id);
        super.paymentCallBackHandler(id, applicationNumberGenerationDto);
    }


    private void protectionExtendRequestPaymentCallBack(Long id) {
        ProtectionExtendRequest entity = findById(id);
        StartProcessDto startProcessDto = StartProcessDto.builder()
                .id(entity.getId().toString())
                .applicantUserName(entity.getCreatedByUser())
                .fullNameAr(entity.getCreatedByUser())
                .fullNameEn(entity.getCreatedByUser())
                .mobile(entity.getApplicationInfo().getMobileNumber())
                .email(entity.getApplicationInfo().getEmail())
                .applicationCategory(entity.getApplicationInfo().getCategory().getSaipCode())
                .processName("extension_of_protection_process")
                .applicationIdColumn(entity.getApplicationInfo().getId().toString())
                .requestTypeCode("PROTECTION_PERIOD_EXTENSION_REQUEST")
                .supportServiceTypeCode(entity.getProtectionExtendType().name())
                .supportServiceCode(entity.getLkSupportServices().getCode().name())
                .identifier(id.toString())
                .requestNumber(entity.getRequestNumber())
                .build();
        startSupportServiceProcess(entity, startProcessDto);
    }

}
