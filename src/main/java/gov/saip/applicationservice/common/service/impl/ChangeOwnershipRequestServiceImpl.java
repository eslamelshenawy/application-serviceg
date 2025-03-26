package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.annotation.CheckCustomerAccess;
import gov.saip.applicationservice.common.clients.rest.callers.NotificationCaller;
import gov.saip.applicationservice.common.clients.rest.feigns.BPMCallerFeignClient;
import gov.saip.applicationservice.common.clients.rest.feigns.CustomerServiceFeignClient;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.dto.agency.TrademarkAgencyRequestDto;
import gov.saip.applicationservice.common.dto.bpm.StartProcessDto;
import gov.saip.applicationservice.common.dto.customer.AddressResponseDto;
import gov.saip.applicationservice.common.dto.notifications.AppNotificationDto;
import gov.saip.applicationservice.common.dto.notifications.NotificationLanguage;
import gov.saip.applicationservice.common.dto.notifications.NotificationRequest;
import gov.saip.applicationservice.common.dto.notifications.NotificationTemplateCode;
import gov.saip.applicationservice.common.enums.*;
import gov.saip.applicationservice.common.enums.agency.TrademarkAgencyType;
import gov.saip.applicationservice.common.enums.certificate.CertificateTypeEnum;
import gov.saip.applicationservice.common.facade.ApplicationInfoFacade;
import gov.saip.applicationservice.common.mapper.ChangeOwnershipRequestMapper;
import gov.saip.applicationservice.common.mapper.agency.TrademarkAgencyRequestMapper;
import gov.saip.applicationservice.common.model.*;
import gov.saip.applicationservice.common.model.agency.TrademarkAgencyRequest;
import gov.saip.applicationservice.common.repository.ApplicationRelevantTypeRepository;
import gov.saip.applicationservice.common.repository.ChangeOwnershipRequestRepository;
import gov.saip.applicationservice.common.repository.SupportServiceRequestRepository;
import gov.saip.applicationservice.common.service.ApplicationCustomerService;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.CertificateRequestService;
import gov.saip.applicationservice.common.service.ChangeOwnershipRequestService;
import gov.saip.applicationservice.common.service.agency.TrademarkAgencyRequestService;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import gov.saip.applicationservice.util.Utilities;
import io.jsonwebtoken.lang.Collections;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

import static gov.saip.applicationservice.common.dto.notifications.NotificationTemplateCode.TRADEMARK_CHANGE_OWNERSHIP_REPLY_TO_APPLICANT;
import static gov.saip.applicationservice.common.enums.SupportServiceType.OWNERSHIP_CHANGE;
import static gov.saip.applicationservice.common.enums.SupportServiceType.VOLUNTARY_REVOKE;

@Service
@Transactional
@AllArgsConstructor
public class ChangeOwnershipRequestServiceImpl extends SupportServiceRequestServiceImpl<ChangeOwnershipRequest> implements ChangeOwnershipRequestService {

    private final CustomerServiceFeignClient customerServiceFeignClient;
    private final ChangeOwnershipRequestRepository changeOwnershipRequestRepository;
    private final ChangeOwnershipRequestMapper changeOwnershipRequestMapper;
    private final BPMCallerFeignClient bpmCallerFeignClient;
    private final ApplicationCustomerService applicationCustomerService;
    private final TrademarkAgencyRequestService trademarkAgencyRequestService;
    private final TrademarkAgencyRequestMapper trademarkAgencyRequestMapper;
    private final ApplicationRelevantTypeRepository applicationRelevantTypeRepository;
    private final ApplicationInfoFacade applicationInfoFacade;
    private final CertificateRequestService certificateRequestService;
    private final NotificationCaller notificationCaller;
    @Value("${link.contactus}")
    String contactUs;

    @Override
    public SupportServiceRequestRepository getSupportServiceRequestRepository() {
        return changeOwnershipRequestRepository;
    }

    @Override
    public ChangeOwnershipRequest insert(ChangeOwnershipRequest entity){
        entity.getChangeOwnershipCustomers().forEach(customer->{
            customer.setChangeOwnershipRequest(entity);
        });
        entity.setOldOwnerId(getOldCustomerId(entity.getApplicationInfo().getId()));
        return insert(OWNERSHIP_CHANGE, entity);
    }

    private Long getOldCustomerId(Long applicationId) {
        List<ApplicationCustomer> oldCustomer = applicationCustomerService.getAppCustomersByTypeOrCodeOrCustomerId(ApplicationCustomerType.MAIN_OWNER, applicationId, null, null);
        if (oldCustomer == null || oldCustomer.isEmpty()) {
            throw new BusinessException(Constants.ErrorKeys.EXCEPTION_RECORD_NOT_FOUND, HttpStatus.NOT_FOUND, null);
        }
        return oldCustomer.get(0).getCustomerId();
    }

    private final ApplicationInfoService applicationInfoService;

    private void changeOwnershipPaymentCallBack(Long id){


        ChangeOwnershipRequest entity = findById(id);



        // start process
        CustomerSampleInfoDto clientCustomerSampleInfoDto = customerServiceFeignClient.getAnyCustomerByCustomerCode(entity.getCreatedByCustomerCode()).getPayload();

        CustomerSampleInfoDto oldCustomer = customerServiceFeignClient.getAnyCustomerById(entity.getOldOwnerId()).getPayload();
        Map<String, Object> variable = new HashMap<>();
        variable.put("OLD_OWNER_MAIL", oldCustomer == null ? null : oldCustomer.getEmail());
        variable.put("OLD_OWNER_MOBILE", oldCustomer == null ? null : oldCustomer.getMobile());
        variable.put("OLD_OWNER_NAME", oldCustomer == null ? null : oldCustomer.getCreatedByUser());
        variable.put("OLD_CUSTOMER_CODE", oldCustomer == null ? null : oldCustomer.getCode());
        StartProcessDto  startProcessDto =  StartProcessDto.builder()
                .id(entity.getId().toString())
                .applicantUserName(entity.getCreatedByUser())
                .fullNameAr(clientCustomerSampleInfoDto.getNameAr())
                .fullNameEn(clientCustomerSampleInfoDto.getNameEn())
                .mobile(clientCustomerSampleInfoDto.getMobile())
                .email(clientCustomerSampleInfoDto.getEmail())
                .applicationCategory(entity.getApplicationInfo().getCategory().getSaipCode())
                .processName("change_ownership_process")
                .requestTypeCode("CHANGE_OWNERSHIP")
                .supportServiceTypeCode(entity.getChangeOwnerShipType() == null ? null : entity.getChangeOwnerShipType().name())
                .supportServiceCode(entity.getLkSupportServices().getCode().name())
                .applicationIdColumn(entity.getApplicationInfo().getId().toString())
                .identifier(id.toString())
                .requestNumber(entity.getRequestNumber())
                .variables(variable)
                .build();
        startSupportServiceProcess(entity, startProcessDto);
    }

    // TODO: --support-service-notes
    @Override
    public ChangeOwnershipRequest update(ChangeOwnershipRequest entity){
        ChangeOwnershipRequest changeOwnershipRequest = findById(entity.getId());
        changeOwnershipRequestRepository.deleteCustomersByRequestId(entity.getId());
        changeOwnershipRequest.setCustomerId(entity.getCustomerId());
        changeOwnershipRequest.setSupportDocument(entity.getSupportDocument());
        changeOwnershipRequest.setWaiveDocument(entity.getWaiveDocument());
        changeOwnershipRequest.setPoaDocument(entity.getPoaDocument());
        changeOwnershipRequest.setChangeOwnerShipType(entity.getChangeOwnerShipType());
        changeOwnershipRequest.setChangeOwnershipCustomers(entity.getChangeOwnershipCustomers());
        entity.getChangeOwnershipCustomers().forEach(cust -> cust.setChangeOwnershipRequest(changeOwnershipRequest));

        changeOwnershipRequest.setAgencyRequestNumber(entity.getAgencyRequestNumber());
        changeOwnershipRequest.setApplicantType(entity.getApplicantType());
        changeOwnershipRequest.setChangeOwnershipDocuments(entity.getChangeOwnershipDocuments());
        changeOwnershipRequest.setLicensesWaiveDocuments(entity.getLicensesWaiveDocuments());
        changeOwnershipRequest.setNotes(entity.getNotes());
        changeOwnershipRequest.setPercentageDocPart(entity.getPercentageDocPart());
        // update status after correction
        changeOwnershipRequest.setRequestStatus(new LKSupportServiceRequestStatus(lKSupportServiceRequestStatusService.findIdByCode(SupportServiceRequestStatusEnum.UNDER_PROCEDURE)));
        super.update(changeOwnershipRequest);

        sendNotificationAfterCorrection(changeOwnershipRequest);
        return changeOwnershipRequest;
    }

    private void sendNotificationAfterCorrection(ChangeOwnershipRequest request){
        if (request.getApplicationInfo().getCategory().getSaipCode().equals(ApplicationCategoryEnum.TRADEMARK)) {
            buildNotificationRequest(request , TRADEMARK_CHANGE_OWNERSHIP_REPLY_TO_APPLICANT);
        }
    }



    private void buildNotificationRequest(ChangeOwnershipRequest request, NotificationTemplateCode notificationTemplateCode){
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


    @Override
    @CheckCustomerAccess(type = ValidationType.SUPPORT_SERVICES)
    public ChangeOwnershipRequestDto getChangeOwnershipRequestByApplicationSupportServiceId(Long applicationSupportServicesId) {
        ChangeOwnershipRequest changeOwnershipRequest = changeOwnershipRequestRepository.findById(applicationSupportServicesId).
                orElseThrow(() -> new BusinessException(Constants.ErrorKeys.EXCEPTION_RECORD_NOT_FOUND, HttpStatus.NOT_FOUND, new String[]{applicationSupportServicesId.toString()}));

        ChangeOwnershipRequestDto changeOwnershipRequestDto = changeOwnershipRequestMapper.map(changeOwnershipRequest);
        setCustomerNames(changeOwnershipRequestDto.getChangeOwnershipCustomers());

        changeOwnershipRequestDto.setAgent(getAgentForChangeOwnershipRequest(changeOwnershipRequest.getCreatedByCustomerCode(), changeOwnershipRequestDto.getApplicantType()));

        Map<Long, CustomerSampleInfoDto> changeOwnershipRequestCustomers = getChangeOwnershipRequestCustomers(changeOwnershipRequest);
        changeOwnershipRequestDto.setCustomer(changeOwnershipRequestCustomers.get(changeOwnershipRequest.getCustomerId()));
        changeOwnershipRequestDto.setOldOwner(changeOwnershipRequestCustomers.get(changeOwnershipRequest.getOldOwnerId()));
        changeOwnershipRequestDto.setCustomerCode(changeOwnershipRequestCustomers.get(changeOwnershipRequest.getCustomerId()).getCode());

        addCustomerCodeToOwnershipRequestCustomers(changeOwnershipRequest, changeOwnershipRequestDto, changeOwnershipRequestCustomers);
        changeOwnershipRequestDto.setTask(getCurrentTaskIfExists(changeOwnershipRequest));
        return changeOwnershipRequestDto;

    }

    private void setCustomerNames(List<ChangeOwnershipCustomerDto> changeOwnershipCustomerDtoList){
        for(ChangeOwnershipCustomerDto changeOwnershipCustomerDto : changeOwnershipCustomerDtoList){
            CustomerSampleInfoDto customerSampleInfoDto = customerServiceFeignClient.getAnyCustomerById(changeOwnershipCustomerDto.getCustomerId()).getPayload();
            changeOwnershipCustomerDto.setCustomerNameAr(customerSampleInfoDto.getNameAr());
            changeOwnershipCustomerDto.setCustomerNameEn(customerSampleInfoDto.getNameEn());
        }
    }

    private RequestTasksDto getCurrentTaskIfExists(ChangeOwnershipRequest changeOwnershipRequest) {
        boolean isRequestProcessNotCompleted = SupportServiceRequestStatusEnum.REQUEST_CORRECTION.equals(changeOwnershipRequest.getRequestStatus())
                || SupportServiceRequestStatusEnum.REQUEST_CORRECTION.equals(changeOwnershipRequest.getRequestStatus());

        if (isRequestProcessNotCompleted) {
            try {
                return bpmCallerFeignClient.getTaskByRowIdAndType(RequestTypeEnum.CHANGE_OWNERSHIP, changeOwnershipRequest.getId()).getPayload();
            } catch(Exception ex) {
                return null;
            }
        }

        return null;
    }

    private static void addCustomerCodeToOwnershipRequestCustomers(ChangeOwnershipRequest changeOwnershipRequest, ChangeOwnershipRequestDto changeOwnershipRequestDto, Map<Long, CustomerSampleInfoDto> changeOwnershipRequestCustomers) {
        if(!Collections.isEmpty(changeOwnershipRequest.getChangeOwnershipCustomers())) {
            changeOwnershipRequestDto.getChangeOwnershipCustomers().forEach(cst -> cst.setCustomerCode(changeOwnershipRequestCustomers.get(cst.getCustomerId()).getCode()));
        }
    }

    private Map<Long, CustomerSampleInfoDto> getChangeOwnershipRequestCustomers(ChangeOwnershipRequest changeOwnershipRequest) {
        Set<Long> ids = new HashSet<>();

        if (!Collections.isEmpty(changeOwnershipRequest.getChangeOwnershipCustomers())) {
            List<Long> cstIds = changeOwnershipRequest.getChangeOwnershipCustomers().stream().map(ChangeOwnershipCustomer::getCustomerId).toList();
            ids.addAll(cstIds);
        }

        ids.add(changeOwnershipRequest.getCustomerId());
        ids.add(changeOwnershipRequest.getOldOwnerId());

        return customerServiceFeignClient.getCustomersByIds(new ListBodyDto<Long>(ids.stream().map(i -> i).toList())).getPayload();
    }

    private CustomerSampleInfoDto getAgentForChangeOwnershipRequest(String createdByCustomerCode, ChangeOwnershipApplicantTypeEnum applicantType) {
        if(ChangeOwnershipApplicantTypeEnum.AGENT_OF_NEW_OWNER.equals(applicantType)  && createdByCustomerCode != null) {
            return customerServiceFeignClient.getAnyCustomerByCustomerCode(createdByCustomerCode).getPayload();
        }
        return null;
    }

    @Override
    @Transactional
    public void processApprovedChangeOwnershipRequest(Long id) {
        ChangeOwnershipRequest changeOwnershipRequest = changeOwnershipRequestRepository.findById(id).orElseThrow(() -> new BusinessException(Constants.ErrorKeys.EXCEPTION_RECORD_NOT_FOUND));
        List<ChangeOwnershipCustomer> changeOwnershipCustomers = changeOwnershipRequest.getChangeOwnershipCustomers();
        Long mainOwnerCustomerId = changeOwnershipRequest.getCustomerId();
        ApplicationInfo application = changeOwnershipRequest.getApplicationInfo();
        if (changeOwnershipCustomers.size() == 1) {
            handleChangeSingleOwner(changeOwnershipCustomers,mainOwnerCustomerId, application);
        }  else {
            handleChangeMultipleOwners(changeOwnershipCustomers, mainOwnerCustomerId, application);
        }
    }

    @Override
    public TrademarkAgencyRequestDto getTrademarkAgencyRequestByRequestNumber(String requestNumber, Long applicationId) {
        TrademarkAgencyRequest trademarkAgencyRequest = trademarkAgencyRequestService.getActiveAgentAgnecyRequestOnApplication(Utilities.getCustomerCodeFromHeaders(), requestNumber, applicationId, TrademarkAgencyType.CHANGE_OWNERSHIP);
        if(trademarkAgencyRequest == null || trademarkAgencyRequest.getEndAgency() == null || trademarkAgencyRequest.getEndAgency().isBefore(LocalDate.now())) {
            throw new BusinessException(Constants.ErrorKeys.TRADEMARK_TRANSFER_OWNERSHIP_FAIL,HttpStatus.EXPECTATION_FAILED);
        }
        TrademarkAgencyRequestDto trademarkAgencyRequestDto= trademarkAgencyRequestMapper.map(trademarkAgencyRequest);
        CustomerSampleInfoDto clientCustomerSampleInfoDto = customerServiceFeignClient.getAnyCustomerByCustomerCode(trademarkAgencyRequest.getClientCustomerCode()).getPayload();
        trademarkAgencyRequestDto.setClientInfo(clientCustomerSampleInfoDto);
        return trademarkAgencyRequestDto;
    }

    private void handleChangeSingleOwner(List<ChangeOwnershipCustomer> changeOwnershipCustomers,Long mainOwnerCustomerId, ApplicationInfo application) {
        if(!ChangeOwnershipTypeEnum.LICENSE_TRANSFER.equals(changeOwnershipCustomers.get(0).getChangeOwnershipRequest().getChangeOwnerShipType())){
            ApplicationCustomer applicationCustomer = new ApplicationCustomer(application, ApplicationCustomerType.MAIN_OWNER, mainOwnerCustomerId, getCustomerCodeById(mainOwnerCustomerId));
            applicationCustomerService.deleteByApplicationId(application.getId());
            applicationCustomerService.insert(applicationCustomer);
        }
        deleteOldApplicantAndAddTheNewMainOne(changeOwnershipCustomers,mainOwnerCustomerId, application);
    }

    private void handleChangeMultipleOwners(List<ChangeOwnershipCustomer> changeOwnershipCustomers, Long mainOwnerCustomerId, ApplicationInfo application) {
        for (ChangeOwnershipCustomer changeOwnershipCustomer:changeOwnershipCustomers){
            if(!ChangeOwnershipTypeEnum.LICENSE_TRANSFER.equals(changeOwnershipCustomer.getChangeOwnershipRequest().getChangeOwnerShipType())){
                List<ApplicationCustomer> applicationCustomers = mapSecondaryOwnersToApplicationCustomers(changeOwnershipCustomers, mainOwnerCustomerId, application);
                ApplicationCustomer newMainApplicationCustomer = new ApplicationCustomer(application, ApplicationCustomerType.MAIN_OWNER, mainOwnerCustomerId, getCustomerCodeById(mainOwnerCustomerId));
                applicationCustomerService.deleteByApplicationId(application.getId());
                applicationCustomerService.saveAll(Stream.concat(applicationCustomers.stream(), Stream.of(newMainApplicationCustomer)).toList());
            }
        }
        deleteOldApplicantAndAddTheNewMainOne(changeOwnershipCustomers,mainOwnerCustomerId, application);
    }

    private void deleteOldApplicantAndAddTheNewMainOne(List<ChangeOwnershipCustomer> changeOwnershipCustomers,Long mainOwnerCustomerId, ApplicationInfo application) {
        for(ChangeOwnershipCustomer changeOwnershipCustomer:changeOwnershipCustomers){
            if (!ChangeOwnershipTypeEnum.LICENSE_TRANSFER.equals(changeOwnershipCustomer.getChangeOwnershipRequest().getChangeOwnerShipType())){
                applicationRelevantTypeRepository.deleteByApplicationInfoId(application.getId());
                CustomerSampleInfoDto customerSampleInfoDto = customerServiceFeignClient.getAnyCustomerById(mainOwnerCustomerId).getPayload();
                ApplicationRelevantType newMainApplicant = new ApplicationRelevantType();
                newMainApplicant.setApplicationInfo(application);
                newMainApplicant.setType(ApplicationRelevantEnum.Applicant_MAIN);
                newMainApplicant.setCustomerCode(customerSampleInfoDto.getCode());
                newMainApplicant.getApplicationInfo().setMobileNumber(customerSampleInfoDto.getMobile());
                newMainApplicant.getApplicationInfo().setEmail(customerSampleInfoDto.getEmail());
                newMainApplicant.getApplicationInfo().setAddress(getNewOwnerAddress(customerSampleInfoDto));
                applicationInfoFacade.setOwnerNameAndAddress(application, customerSampleInfoDto);
                applicationRelevantTypeRepository.save(newMainApplicant);
            }else {
                CustomerSampleInfoDto customerSampleInfoDto = customerServiceFeignClient.getAnyCustomerById(changeOwnershipCustomer.getCustomerId()).getPayload();
                ApplicationRelevantType newMainApplicant = new ApplicationRelevantType();
                newMainApplicant.setApplicationInfo(application);
                newMainApplicant.setType(ApplicationRelevantEnum.LICENSED_CUSTOMER);
                newMainApplicant.setCustomerCode(customerSampleInfoDto.getCode());
                newMainApplicant.getApplicationInfo().setMobileNumber(customerSampleInfoDto.getMobile());
                newMainApplicant.getApplicationInfo().setEmail(customerSampleInfoDto.getEmail());
                newMainApplicant.getApplicationInfo().setAddress(getNewOwnerAddress(customerSampleInfoDto));
                applicationRelevantTypeRepository.save(newMainApplicant);
            }
        }

        if (ApplicationCategoryEnum.valueOf(application.getCategory().getSaipCode()).equals(ApplicationCategoryEnum.TRADEMARK)) {
            certificateRequestService.deleteOldCertificateForOldOwner(application.getId(), CertificateTypeEnum.ISSUE_CERTIFICATE.name());
        }

    }

    private static String getNewOwnerAddress(CustomerSampleInfoDto customerSampleInfoDto) {
        AddressResponseDto address = customerSampleInfoDto.getAddress();
        if (address == null) {
            return "";
        }

        return new StringBuilder()
                .append(address.getCity() == null ? "" : address.getCity())
                .append(" ")
                .append(address.getDistrict() == null ? "" : address.getDistrict())
                .append(" ")
                .append(address.getStreetName() == null ? "" : address.getStreetName())
                .append(" ")
                .append(address.getBuildingNumber() == null ? "" : address.getBuildingNumber())
                .append(" ")
                .append(address.getUnitNumber() == null ? "" : address.getUnitNumber())
                .toString();
    }

    private List<ApplicationCustomer> mapSecondaryOwnersToApplicationCustomers(List<ChangeOwnershipCustomer> changeOwnershipCustomers, Long mainOwnerCustomerId, ApplicationInfo application) {
        List<ApplicationCustomer> applicationCustomers = changeOwnershipCustomers.stream()
                .filter(ownerCustomer -> !ownerCustomer.getCustomerId().equals(mainOwnerCustomerId))
                .map(ownerCustomer ->
                        new ApplicationCustomer(application, ApplicationCustomerType.SECONDARY_OWNER, ownerCustomer.getCustomerId(), getCustomerCodeById(ownerCustomer.getCustomerId()))
                ).toList();
        return applicationCustomers;
    }


    private String getCustomerCodeById(Long id) {
        return customerServiceFeignClient.getCustomerCodeByCustomerId(String.valueOf(id)).getPayload();
    }

    @Override
    @Transactional
    public void paymentCallBackHandler(Long id, ApplicationNumberGenerationDto applicationNumberGenerationDto) {
        changeOwnershipPaymentCallBack(id);
        super.paymentCallBackHandler(id, applicationNumberGenerationDto);
    }

}
