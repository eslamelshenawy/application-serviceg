package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.annotation.CheckCustomerAccess;
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
import gov.saip.applicationservice.common.enums.*;
import gov.saip.applicationservice.common.mapper.RevokeByCourtOrderMapper;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.RevokeByCourtOrder;
import gov.saip.applicationservice.common.model.SupportServiceCustomer;
import gov.saip.applicationservice.common.repository.RevokeByCourtOrderRepository;
import gov.saip.applicationservice.common.repository.SupportServiceRequestRepository;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.RevokeByCourtOrderService;
import gov.saip.applicationservice.common.service.activityLog.ActivityLogService;
import gov.saip.applicationservice.common.service.agency.SupportServiceCustomerService;
import gov.saip.applicationservice.common.service.bpm.SupportServiceProcess;
import gov.saip.applicationservice.util.Constants;
import gov.saip.applicationservice.util.Utilities;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

import static gov.saip.applicationservice.common.enums.SupportServiceType.REVOKE_BY_COURT_ORDER;


@Service
@Transactional
@AllArgsConstructor
public class RevokeByCourtOrderServiceImpl extends SupportServiceRequestServiceImpl<RevokeByCourtOrder> implements RevokeByCourtOrderService {

    private final RevokeByCourtOrderRepository revokeByCourtOrderRepository;
    private final ApplicationInfoService applicationInfoService;
    private final RevokeByCourtOrderMapper revokeByCourtOrderMapper;
    private final BPMCallerFeignClient bpmCallerFeignClient;
    private final CustomerServiceFeignClient customerServiceFeignClient;
    private final SupportServiceCustomerService supportServiceCustomerService;
    private final NotificationCaller notificationCaller;
    private SupportServiceProcess supportServiceProcess;
    private ActivityLogService activityLogService;



    @Value("${link.portal}")
    String portalLink;
    
    @Value("${link.contactus}")
    String contactUs;


    @Override
    public SupportServiceRequestRepository getSupportServiceRequestRepository() {
        return revokeByCourtOrderRepository;
    }

    @Override
    public RevokeByCourtOrder insert(RevokeByCourtOrder entity) {
        entity.setPaymentStatus(SupportServicePaymentStatus.FREE);
        entity = getRevokeByCourtOrder(entity);
        startRevokeByCourtOrderProcess(entity);
        return entity;
    }

    public RevokeByCourtOrder getRevokeByCourtOrder(RevokeByCourtOrder entity) {
        entity = super.insert(REVOKE_BY_COURT_ORDER, entity);
        return entity;
    }

    public void startRevokeByCourtOrderProcess(RevokeByCourtOrder entity) {
        CustomerSampleInfoDto customerSampleInfoDto = customerServiceFeignClient.getAnyCustomerByCustomerCode(Utilities.getCustomerCodeFromHeaders()).getPayload();
        ApplicationInfo applicationInfo = applicationInfoService.findById(entity.getApplicationInfo().getId());
        StartProcessDto startProcessDto = StartProcessDto.builder()
                .id(entity.getId().toString())
                .applicantUserName(entity.getCreatedByUser())
                .fullNameAr(customerSampleInfoDto.getNameAr())
                .fullNameEn(customerSampleInfoDto.getNameEn())
                .mobile(customerSampleInfoDto.getMobile())
                .email(customerSampleInfoDto.getEmail())
                .applicationCategory(applicationInfo.getCategory().getSaipCode())
                .processName("revoke_by_court_order_process")
                .applicationIdColumn(applicationInfo.getId().toString())
                .requestTypeCode(RequestTypeEnum.REVOKE_BY_COURT_ORDER.name())
                .supportServiceCode(entity.getLkSupportServices().getCode().name())
                .identifier(entity.getId().toString())
                .requestNumber(entity.getRequestNumber())
                .build();
//        if(licenceRequestService.checkApplicationHaveLicence(entity.getApplicationInfo().getId()))  // check application have licence
//            startProcessDto.addVariable("approved", "YES");
//        else
//            startProcessDto.addVariable("approved", "NO");
        startSupportServiceProcess(entity, startProcessDto);
    }
    @Override
    public void startSupportServiceProcess(RevokeByCourtOrder entity, StartProcessDto startProcessDto) {
        if (entity.getProcessRequestId() != null) {
            return;
        }
        addApplicantsCustomerCodes(entity, startProcessDto);
        StartProcessResponseDto startProcessResponseDto = supportServiceProcess.startProcess(startProcessDto);
        entity.setProcessRequestId(startProcessResponseDto.getBusinessKey());
        revokeByCourtOrderRepository.save(entity);
        activityLogService.insertSupportServicesActivityLogStatus(startProcessResponseDto.getTaskHistoryUIDto(), entity);
    }

    @Override
    public RevokeByCourtOrder update(RevokeByCourtOrder entity) {
        RevokeByCourtOrder revokeByCourtOrder = findById(entity.getId());
        revokeByCourtOrder.setNotes(entity.getNotes() != null ? entity.getNotes() : revokeByCourtOrder.getNotes());
        revokeByCourtOrder.setDocuments(entity.getDocuments() != null ? entity.getDocuments() : revokeByCourtOrder.getDocuments());
        revokeByCourtOrder = super.update(revokeByCourtOrder);
        completeUserTask(entity);
        this.updateRequestStatusByCode(revokeByCourtOrder.getId(), SupportServiceRequestStatusEnum.UNDER_PROCEDURE);
        sendResponseAfterReplyingToRevokeProducts(entity);
        return revokeByCourtOrder;
    }

    private void completeUserTask(RevokeByCourtOrder entity){
        RequestTasksDto requestTasksDto = bpmCallerFeignClient.getTaskByRowIdAndType(RequestTypeEnum.REVOKE_BY_COURT_ORDER, entity.getId()).getPayload();
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
    public void updateRevokeByCourtOrderRequestWithInternalData(RevokeByCourtOrderInternalDto revokeByCourtOrderInternalDto) {
        RevokeByCourtOrder revokeByCourtOrder = this.findById(revokeByCourtOrderInternalDto.getId());
        revokeByCourtOrder = revokeByCourtOrderMapper.unMapFromRevokeByCourtOrderInternalDto(revokeByCourtOrder, revokeByCourtOrderInternalDto);
        super.update(revokeByCourtOrder);
    }


    @Override
    @CheckCustomerAccess(type = ValidationType.SUPPORT_SERVICES)
    public RevokeByCourtOrderRequestDto findByServiceId(Long id){
        RevokeByCourtOrder revokeByCourtOrderRequest = getReferenceById(id);
        List<ApplicationCustomerType> supportServiceCustomerType = supportServiceCustomerService.findSupportServiceCustomerTypeByServiceId(revokeByCourtOrderRequest.getId());
        RevokeByCourtOrderRequestDto revokeByCourtOrderRequestDto = revokeByCourtOrderMapper.map(revokeByCourtOrderRequest);
        if(supportServiceCustomerType.isEmpty()){
            return revokeByCourtOrderRequestDto;
        }
        SupportServiceCustomer supportServiceCustomer = supportServiceCustomerService.findByApplicationSupportServicesId(revokeByCourtOrderRequest.getId(), supportServiceCustomerType.get(0));

        if (supportServiceCustomer == null) {
            return revokeByCourtOrderRequestDto;
        }

        CustomerSampleInfoDto customerSampleInfoDto = customerServiceFeignClient.getAnyCustomerByCustomerCode(supportServiceCustomer.getCustomerCode()).getPayload();
        if (customerSampleInfoDto == null) {
            return revokeByCourtOrderRequestDto;
        }

        revokeByCourtOrderRequestDto.setApplicantNameAr(customerSampleInfoDto.getNameAr());
        revokeByCourtOrderRequestDto.setApplicantNameEn(customerSampleInfoDto.getNameEn());
        return revokeByCourtOrderRequestDto;
    }

    private void sendResponseAfterReplyingToRevokeProducts(RevokeByCourtOrder revokeByCourtOrder) {
        ApplicationInfoDto applicationInfoDto = applicationInfoService.getApplication(revokeByCourtOrder.getApplicationInfo().getId());
        Map<String, Object> notificationParams = new HashMap<>();
        notificationParams.put("appNumber", applicationInfoDto.getApplicationNumber());
        notificationParams.put("titleAr", applicationInfoDto.getTitleAr());
        notificationParams.put("link", contactUs);
        notificationParams.put("pageLink", portalLink);
        List<ApplicationCustomerType> supportServiceCustomerType = supportServiceCustomerService.findSupportServiceCustomerTypeByServiceId(revokeByCourtOrder.getId());
        SupportServiceCustomer supportServiceCustomer = supportServiceCustomerService.findByApplicationSupportServicesId(revokeByCourtOrder.getId(), supportServiceCustomerType.get(0));
        CustomerSampleInfoDto customerSampleInfoDto = customerServiceFeignClient.getAnyCustomerByCustomerCode(supportServiceCustomer.getCustomerCode()).getPayload();
        buildNotificationRequest(customerSampleInfoDto.getEmail(), customerSampleInfoDto.getMobile(), String.valueOf(applicationInfoDto.getId()), ApplicationCategoryEnum.TRADEMARK.name(), supportServiceCustomer.getCustomerCode(), NotificationTemplateCode.RESPONSE_TO_THE_REQUEST_OF_REVOKE_PRODUCTS, notificationParams);
    }


    private void buildNotificationRequest(String email, String mobile, String rowId, String applicationCategory, String customerCode, NotificationTemplateCode notificationTemplateCode, Map<String, Object> notificationParams) {
        NotificationDto emailDto = NotificationDto
                .builder()
                .to(email)
                .messageType(Constants.MessageType.EMAIL_TYPE_MESSAGE)
                .build();
        NotificationDto smsDto = NotificationDto
                .builder()
                .to(mobile)
                .build();
        AppNotificationDto appDto = AppNotificationDto.builder()
                .rowId(rowId)
                .routing(applicationCategory)
                .date(LocalDateTime.now())
                .userNames(Arrays.asList(customerCode)).build();

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
