package gov.saip.applicationservice.common.service.supportService.impl;

import gov.saip.applicationservice.common.clients.rest.callers.NotificationCaller;
import gov.saip.applicationservice.common.clients.rest.feigns.BPMCallerFeignClient;
import gov.saip.applicationservice.common.clients.rest.feigns.CustomerServiceFeignClient;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.dto.bpm.CompleteTaskRequestDto;
import gov.saip.applicationservice.common.dto.bpm.StartProcessDto;
import gov.saip.applicationservice.common.dto.notifications.AppNotificationDto;
import gov.saip.applicationservice.common.dto.notifications.NotificationLanguage;
import gov.saip.applicationservice.common.dto.notifications.NotificationRequest;
import gov.saip.applicationservice.common.dto.notifications.NotificationTemplateCode;
import gov.saip.applicationservice.common.enums.ApplicationCustomerType;
import gov.saip.applicationservice.common.enums.RequestTypeEnum;
import gov.saip.applicationservice.common.enums.SupportServiceRequestStatusEnum;
import gov.saip.applicationservice.common.model.ApplicationCustomer;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.supportService.application_edit_name_address.ApplicationEditNameAddressRequest;
import gov.saip.applicationservice.common.repository.SupportServiceRequestRepository;
import gov.saip.applicationservice.common.repository.supportService.ApplicationEditNameAddressRequestRepository;
import gov.saip.applicationservice.common.service.ApplicationCustomerService;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.bpm.SupportServiceProcess;
import gov.saip.applicationservice.common.service.impl.SupportServiceRequestServiceImpl;
import gov.saip.applicationservice.common.service.supportService.ApplicationEditNameAddressRequestService;
import gov.saip.applicationservice.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static gov.saip.applicationservice.common.enums.SupportServiceType.EDIT_TRADEMARK_NAME_ADDRESS;


@Service
@RequiredArgsConstructor
public class ApplicationEditNameAddressRequestServiceImpl extends SupportServiceRequestServiceImpl<ApplicationEditNameAddressRequest> implements ApplicationEditNameAddressRequestService {

    private final SupportServiceProcess supportServiceProcess;

    private final ApplicationEditNameAddressRequestRepository applicationEditNameAddressRequestRepository;

    private final ApplicationInfoService applicationInfoService;

    private final ApplicationEditNameAddressRequestRepository repository;
    
    private final ApplicationCustomerService applicationCustomerService;

    private final BPMCallerFeignClient bpmCallerFeignClient;
    private final NotificationCaller notificationCaller;
    private final CustomerServiceFeignClient customerServiceFeignClient;
    
    @Value("${link.contactus}")
    String contactUs;


    @Override
    public SupportServiceRequestRepository getSupportServiceRequestRepository() {
        return applicationEditNameAddressRequestRepository;
    }


    @Override
    public ApplicationEditNameAddressRequest insert(ApplicationEditNameAddressRequest entity) {
        ApplicationInfo applicationInfo = applicationInfoService.findById(entity.getApplicationInfo().getId());
        entity.setOldOwnerNameAr(applicationInfo.getOwnerNameAr());
        entity.setOldOwnerNameEn(applicationInfo.getOwnerNameEn());
        entity.setOldOwnerAddressAr(applicationInfo.getOwnerAddressAr());
        entity.setOldOwnerAddressEn(applicationInfo.getOwnerAddressEn());
        return super.insert(EDIT_TRADEMARK_NAME_ADDRESS, entity);
    }

    @Override
    @Transactional
    public void paymentCallBackHandler(Long id, ApplicationNumberGenerationDto applicationNumberGenerationDto) {
        ApplicationEditNameAddressRequestPaymentCallBack(id);
        super.paymentCallBackHandler(id, applicationNumberGenerationDto);
    }

    private void ApplicationEditNameAddressRequestPaymentCallBack(Long id) {
        ApplicationEditNameAddressRequest entity = findById(id);
        CustomerSampleInfoDto clientCustomerSampleInfoDto = customerServiceFeignClient.getCustomerInfoByCode(entity.getCreatedByCustomerCode()).getPayload();
        ApplicationCustomer mainApplicationCustomer = applicationCustomerService.getAppCustomerByAppIdAndType(entity.getApplicationInfo().getId(), ApplicationCustomerType.MAIN_OWNER);
        Map<String, Object> variable = new HashMap<>();
        if (mainApplicationCustomer != null && clientCustomerSampleInfoDto != null && !mainApplicationCustomer.getCustomerCode().equals(clientCustomerSampleInfoDto.getCode())) {
            variable.put("OLD_OWNER_MAIL", clientCustomerSampleInfoDto.getEmail());
            variable.put("OLD_OWNER_MOBILE", clientCustomerSampleInfoDto.getMobile());
            variable.put("OLD_OWNER_NAME", clientCustomerSampleInfoDto.getNameAr());
            variable.put("OLD_OWNER_NAME_EN", clientCustomerSampleInfoDto.getNameEn());
            variable.put("OLD_CUSTOMER_CODE", clientCustomerSampleInfoDto.getCode());
        }
        StartProcessDto startProcessDto = StartProcessDto.builder()
                .id(entity.getId().toString())
                .applicantUserName(entity.getCreatedByUser())
                .fullNameAr(clientCustomerSampleInfoDto.getNameAr())
                .fullNameEn(clientCustomerSampleInfoDto.getNameEn())
                .mobile(clientCustomerSampleInfoDto.getMobile())
                .email(clientCustomerSampleInfoDto.getEmail())
                .applicationCategory(entity.getApplicationInfo().getCategory().getSaipCode())
                .processName("application_edit_name_address_process")
                .requestTypeCode("EDIT_TRADEMARK_NAME_ADDRESS")
                .supportServiceTypeCode(entity.getEditType() == null ? null : entity.getEditType().name())
                .supportServiceCode(entity.getLkSupportServices().getCode().name())
                .applicationIdColumn(entity.getApplicationInfo().getId().toString())
                .identifier(id.toString())
                .requestNumber(entity.getRequestNumber())
                .variables(variable)
                .build();
        startSupportServiceProcess(entity, startProcessDto);
    }

    @Override
    public ApplicationEditNameAddressRequest update(ApplicationEditNameAddressRequest request) {
        ApplicationEditNameAddressRequest entity = findById(request.getId());

        updateEntityFields(entity, request);

        ApplicationEditNameAddressRequest updatedRequest = super.update(entity);

        if (!isDraftStatus(entity)) {
            completeUserTask(entity);
            updateRequestStatus(entity);
            buildNotificationRequest(updatedRequest , NotificationTemplateCode.TRADEMARK_APPLICATION_EDIT_NAME_ADDRESS_REPLY_TO_APPLICANT);
        }

        return updatedRequest;
    }

    private void updateEntityFields(ApplicationEditNameAddressRequest entity, ApplicationEditNameAddressRequest request) {
        entity.setEditType(request.getEditType());
        entity.setNewOwnerNameAr(request.getNewOwnerNameAr());
        entity.setNewOwnerNameEn(request.getNewOwnerNameEn());
        entity.setNewOwnerAddressAr(request.getNewOwnerAddressAr());
        entity.setNewOwnerAddressEn(request.getNewOwnerAddressEn());
        entity.setNotes(request.getNotes());
    }

    private boolean isDraftStatus(ApplicationEditNameAddressRequest entity) {
        return SupportServiceRequestStatusEnum.DRAFT.name()
                .equals(entity.getRequestStatus().getCode());
    }

    private void updateRequestStatus(ApplicationEditNameAddressRequest entity) {
        this.updateRequestStatusByCode(entity.getId(), SupportServiceRequestStatusEnum.UNDER_PROCEDURE);
    }


    public void completeUserTask(ApplicationEditNameAddressRequest request){
        RequestTasksDto requestTasksDto = bpmCallerFeignClient
                .getTaskByRowIdAndType(RequestTypeEnum.EDIT_TRADEMARK_NAME_ADDRESS, request.getId()).getPayload();
        Map<String, Object> approved = new LinkedHashMap();
        approved.put("value", "YES");
        Map<String, Object> processVars = new LinkedHashMap<>();
        processVars.put("approved", approved);
        CompleteTaskRequestDto completeTaskRequestDto = new CompleteTaskRequestDto();
        completeTaskRequestDto.setVariables(processVars);
        bpmCallerFeignClient.completeUserTask(requestTasksDto.getTaskId(), completeTaskRequestDto);
    }

    private void buildNotificationRequest(ApplicationEditNameAddressRequest request, NotificationTemplateCode notificationTemplateCode){
        ApplicationInfoDto applicationInfoDto = applicationInfoService.getApplication(request.getApplicationInfo().getId());
        CustomerSampleInfoDto customerSampleInfoDto = customerServiceFeignClient.getAnyCustomerByCustomerCode(request.getCreatedByCustomerCode()).getPayload();
        Map<String , Object> notificationParams = new HashMap<>();
        notificationParams.put("titleAr" , applicationInfoDto.getTitleAr());
        notificationParams.put("appNumber",applicationInfoDto.getApplicationNumber());
        notificationParams.put("link", contactUs);
        NotificationDto emailDto = NotificationDto
                .builder()
                .to(customerSampleInfoDto.getEmail())
                .messageType(Constants.MessageType.EMAIL_TYPE_MESSAGE)
                .build();
        NotificationDto smsDto = NotificationDto
                .builder()
                .to(customerSampleInfoDto.getMobile())
                .build();
        AppNotificationDto appDto = AppNotificationDto.builder()
                .rowId(String.valueOf(request.getApplicationInfo().getId()))
                .serviceId(String.valueOf(request.getId()))
                .serviceCode(EDIT_TRADEMARK_NAME_ADDRESS.name())
                .routing(request.getApplicationInfo().getCategory().getSaipCode()).date(LocalDateTime.now())
                .userNames(List.of((String)request.getCreatedByCustomerCode())).build();

        NotificationRequest notificationRequest = NotificationRequest.builder()
                .lang(NotificationLanguage.AR)
                .code(notificationTemplateCode)
                .templateParams(notificationParams)
                .email(emailDto)
                .sms(smsDto)
                .app(appDto)
                .build();
        notificationCaller.sendAllToSpecificUser(notificationRequest);
    }


}
