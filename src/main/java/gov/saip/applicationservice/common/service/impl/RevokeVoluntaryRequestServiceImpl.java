package gov.saip.applicationservice.common.service.impl;

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
import gov.saip.applicationservice.common.enums.RequestTypeEnum;
import gov.saip.applicationservice.common.enums.SupportServiceRequestStatusEnum;
import gov.saip.applicationservice.common.model.ApplicationSupportServicesType;
import gov.saip.applicationservice.common.model.RevokeVoluntaryRequest;
import gov.saip.applicationservice.common.repository.RevokeVoluntryRequestRepository;
import gov.saip.applicationservice.common.repository.SupportServiceRequestRepository;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.ApplicationSupportServicesTypeService;
import gov.saip.applicationservice.common.service.LicenceRequestService;
import gov.saip.applicationservice.common.service.RevokeVoluntaryRequestService;
import gov.saip.applicationservice.util.Constants;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

import static gov.saip.applicationservice.common.enums.SupportServiceType.VOLUNTARY_REVOKE;


@Service
@Transactional
@AllArgsConstructor
public class RevokeVoluntaryRequestServiceImpl extends SupportServiceRequestServiceImpl<RevokeVoluntaryRequest>
  implements RevokeVoluntaryRequestService {

    private final RevokeVoluntryRequestRepository revokeVoluntryRequestRepository;
    private final BPMCallerFeignClient bpmCallerFeignClient;
    private final LicenceRequestService licenceRequestService;
    private final ApplicationInfoService applicationInfoService;
    private final NotificationCaller notificationCaller;
    private final CustomerServiceFeignClient customerServiceFeignClient;
    private final ApplicationSupportServicesTypeService applicationSupportServicesTypeService;
    @Value("${link.contactus}")
    String contactUs;


    @Override
    public SupportServiceRequestRepository getSupportServiceRequestRepository() {
        return revokeVoluntryRequestRepository;
    }

    @Override
    public RevokeVoluntaryRequest insert(RevokeVoluntaryRequest entity) {
     return super.insert(VOLUNTARY_REVOKE, entity);
    }

    @Override
    public RevokeVoluntaryRequest update(RevokeVoluntaryRequest entity) {
        RevokeVoluntaryRequest revokeVoluntaryRequest = findById(entity.getId());
        revokeVoluntaryRequest.setNotes(entity.getNotes() != null ? entity.getNotes() : revokeVoluntaryRequest.getNotes());
        revokeVoluntaryRequest.setDocuments(entity.getDocuments());
        RevokeVoluntaryRequest voluntaryRequest = super.update(revokeVoluntaryRequest);
        completeUserTask(revokeVoluntaryRequest);
        this.updateRequestStatusByCode(revokeVoluntaryRequest.getId(), SupportServiceRequestStatusEnum.UNDER_PROCEDURE);
        buildNotificationRequest(revokeVoluntaryRequest , NotificationTemplateCode.REVOKE_VOLUNTARY_REPLY_TO_APPLICANT);
        return voluntaryRequest;
    }


    public void completeUserTask(RevokeVoluntaryRequest revokeVoluntaryRequest){
        RequestTasksDto requestTasksDto = bpmCallerFeignClient.getTaskByRowIdAndType(RequestTypeEnum.VOLUNTARY_REVOKE, revokeVoluntaryRequest.getId()).getPayload();
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


    @Override
    @Transactional
    public void paymentCallBackHandler(Long id, ApplicationNumberGenerationDto applicationNumberGenerationDto) {
        revokeVoluntryRequestPaymentCallBack(id);
        super.paymentCallBackHandler(id, applicationNumberGenerationDto);
    }

    private void revokeVoluntryRequestPaymentCallBack(Long id) {
        RevokeVoluntaryRequest entity = findById(id);
        CustomerSampleInfoDto customerSampleInfoDto = customerServiceFeignClient.getAnyCustomerByCustomerCode(entity.getCreatedByCustomerCode()).getPayload();
        StartProcessDto startProcessDto = StartProcessDto.builder()
                .id(entity.getId().toString())
                .applicantUserName(entity.getCreatedByUser())
                .fullNameAr(entity.getCreatedByUser())
                .fullNameEn(entity.getCreatedByUser())
                .mobile(customerSampleInfoDto.getMobile())
                .email(customerSampleInfoDto.getEmail())
                .applicationCategory(entity.getApplicationInfo().getCategory().getSaipCode())
                .processName("revoke_voluntary_process")
                .applicationIdColumn(entity.getApplicationInfo().getId().toString())
                .requestTypeCode("VOLUNTARY_REVOKE")
                .supportServiceCode(entity.getLkSupportServices().getCode().name())
                .identifier(id.toString())
                .requestNumber(entity.getRequestNumber())
                .build();

        overrideApplicantNames(id, startProcessDto);
        if(licenceRequestService.checkApplicationHaveLicence(entity.getApplicationInfo().getId()))  // check application have licence
           startProcessDto.addVariable("approved", "YES");
        else
            startProcessDto.addVariable("approved", "NO");

        startSupportServiceProcess(entity, startProcessDto);
    }


    private void overrideApplicantNames(Long id, StartProcessDto startProcessDto) {
        ApplicationSupportServicesType applicationSupportServicesType =applicationSupportServicesTypeService.findById(id);
        CustomerSampleInfoDto customerSampleInfoDto = customerServiceFeignClient.getAnyCustomerByCustomerCode(applicationSupportServicesType.getCreatedByCustomerCode()).getPayload();
        if(Objects.nonNull(customerSampleInfoDto)){
            startProcessDto.setFullNameAr(Objects.nonNull(customerSampleInfoDto.getNameAr())?customerSampleInfoDto.getNameAr():null);
            startProcessDto.setFullNameEn(Objects.nonNull(customerSampleInfoDto.getNameEn())?customerSampleInfoDto.getNameEn():null);
        }
    }

    @Override
    public RevokeVoluntaryRequest findByAppId(Long appId) {
        return revokeVoluntryRequestRepository.findByAppId(appId);
    }

    private void buildNotificationRequest(RevokeVoluntaryRequest request, NotificationTemplateCode notificationTemplateCode){
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
                .serviceCode(VOLUNTARY_REVOKE.name())
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
