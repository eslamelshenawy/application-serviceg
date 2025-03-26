package gov.saip.applicationservice.common.service.bpm.impl;

import gov.saip.applicationservice.common.clients.rest.feigns.CustomerServiceFeignClient;
import gov.saip.applicationservice.common.dto.CustomerSampleInfoDto;
import gov.saip.applicationservice.common.dto.ProcessRequestDto;
import gov.saip.applicationservice.common.dto.StartProcessResponseDto;
import gov.saip.applicationservice.common.dto.bpm.StartProcessDto;
import gov.saip.applicationservice.common.enums.RequestTypeEnum;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.LkApplicantCategory;
import gov.saip.applicationservice.common.model.LkApplicationCategory;
import gov.saip.applicationservice.common.service.ApplicationAcceleratedService;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.BPMCallerService;
import gov.saip.applicationservice.common.service.bpm.SupportServiceProcess;
import gov.saip.applicationservice.common.service.impl.ApplicationCustomerServiceImpl;
import gov.saip.applicationservice.common.service.lookup.LkApplicationCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class SupportServiceProcessImpl implements SupportServiceProcess {

    private final BPMCallerService bpmCallerService;
    private final ApplicationCustomerServiceImpl applicationCustomerService;
    private final ApplicationAcceleratedService applicationAcceleratedService;
    private final LkApplicationCategoryService lkApplicationCategoryService;
    private final CustomerServiceFeignClient customerServiceFeignClient;

    public void starSupportServiceProcess(StartProcessDto processDto) {
        ProcessRequestDto dto = new ProcessRequestDto();
        dto.setProcessId(processDto.getProcessName());
//        Map<String, Object> vars = new HashMap<>();
      //  ApplicantsDto applicantsDto = applicationInfoService.listMainApplicant(request.getApplicationSupportServicesType().getApplicationInfo());
        dto.addVariable("fullNameAr", processDto.getFullNameAr());
        dto.addVariable("fullNameEn", processDto.getFullNameEn());
        dto.addVariable("APPLICANT_USER_NAME", processDto.getApplicantUserName());
        dto.addVariable("APPLICANT_CUSTOMER_CODE", processDto.getApplicantCustomerCode());
        dto.addVariable("email", processDto.getEmail());
        dto.addVariable("mobile", processDto.getMobile());
        dto.addVariable("id", processDto.getId());
        dto.addVariable("requestTypeCode", processDto.getRequestTypeCode());
        dto.addVariable("identifier", processDto.getIdentifier());
        dto.addVariable("applicationCategory", processDto.getApplicationCategory());
        dto.addVariable("supportServiceCode", processDto.getSupportServiceCode());
        dto.addVariable("supportServiceTypeCode", processDto.getSupportServiceTypeCode());
        dto.addVariable("REQUESTS_APPLICATION_ID_COLUMN", processDto.getApplicationIdColumn());
        dto.addVariable("SUPPORT_SERVICE_REQUEST_NUMBER", processDto.getRequestNumber());
        
        
        if (processDto.getVariables() != null && !processDto.getVariables().isEmpty()){
           dto.getVariables().putAll(processDto.getVariables());
        }
        LkApplicationCategory lkApplicationCategory = lkApplicationCategoryService.findBySaipCode(processDto.getApplicationCategory());
        dto.setCategoryId(lkApplicationCategory.getId());

        bpmCallerService.startApplicationProcess(dto);
    }



    public StartProcessResponseDto startProcess(StartProcessDto processDto) {
        Map<String, Object> vars = new HashMap<>();
        vars.put("fullNameAr", processDto.getFullNameAr());
        vars.put("fullNameEn", processDto.getFullNameEn());
        vars.put("APPLICANT_USER_NAME", processDto.getApplicantUserName());
        vars.put("email", processDto.getEmail());
        vars.put("mobile", processDto.getMobile());
        vars.put("id", processDto.getId());
        vars.put("APPLICANT_CUSTOMER_CODE", processDto.getApplicantCustomerCode());
        vars.put("requestTypeCode", processDto.getRequestTypeCode());
        vars.put("identifier", processDto.getIdentifier());
        vars.put("applicationCategory", processDto.getApplicationCategory());
        vars.put("REQUESTS_APPLICATION_ID_COLUMN", processDto.getApplicationIdColumn());
        vars.put("REQUEST_NUMBER", processDto.getRequestNumber());
        vars.put("supportServiceCode",processDto.getSupportServiceCode());
        if (processDto.getVariables() != null && !processDto.getVariables().isEmpty()) {
            vars.putAll(processDto.getVariables());
        }
        ProcessRequestDto dto = new ProcessRequestDto();
        dto.setProcessId(processDto.getProcessName());
        dto.setVariables(vars);
        LkApplicationCategory lkApplicationCategory = lkApplicationCategoryService.findBySaipCode(processDto.getApplicationCategory());
        dto.setCategoryId(lkApplicationCategory.getId());
        return bpmCallerService.startApplicationProcess(dto);
    }


    public StartProcessResponseDto startProcessConfig(ApplicationInfo applicationInfo) {
        LkApplicationCategory lkApplicantCategory = applicationInfo.getCategory();
        return starProcessApplication(applicationInfo, getProcessType(lkApplicantCategory), getRequestType(lkApplicantCategory));
    }

    public StartProcessResponseDto starProcessApplication(ApplicationInfo applicationInfo, String processName, String requestType) {
        ProcessRequestDto dto = new ProcessRequestDto();
        dto.setProcessId(processName);
        Map<String, Object> vars = getStringObjectMap(applicationInfo, requestType);
        dto.setVariables(vars);
        if (applicationInfo.getCategory() != null){
            dto.setCategoryId(applicationInfo.getCategory().getId());
        }
        return bpmCallerService.startApplicationProcess(dto);
    }
    private String getProcessType(LkApplicationCategory lkApplicantCategory) {
        if (lkApplicantCategory == null)
            return null;
        String saipCode = lkApplicantCategory.getSaipCode();
        switch (saipCode) {
            case "PATENT":
                return "patent_application_process";
            case "INDUSTRIAL_DESIGN":
                return "industrial_design_application_process";
            case "TRADEMARK":
                return "trademark_application_process";
            default:
                return null;
        }
    }

    private String getRequestType(LkApplicationCategory lkApplicantCategory) {
        if (lkApplicantCategory == null)
            return null;
        String saipCode = lkApplicantCategory.getSaipCode();
        switch (saipCode) {
            case "PATENT":
                return RequestTypeEnum.PATENT.name();
            case "INDUSTRIAL_DESIGN":
                return RequestTypeEnum.INDUSTRIAL_DESIGN.name();
            case "TRADEMARK":
                return RequestTypeEnum.TRADEMARK.name();
            default:
                return null;
        }
    }

    private Map<String, Object> getStringObjectMap(ApplicationInfo applicationInfo, String requestType) {
        CustomerSampleInfoDto customerInfo = applicationCustomerService.getApplicationActiveCustomer(applicationInfo.getId());
        boolean applicationAccelerated = applicationAcceleratedService.checkIfApplicationAccelrated(applicationInfo.getId());
        Map<String, Object> vars = new HashMap<>();
        vars.put("fullNameAr", customerInfo.getNameAr());
        vars.put("fullNameEn", customerInfo.getNameEn());
        vars.put("APPLICANT_USER_NAME", applicationInfo.getCreatedByUser());
        String email = !Strings.isBlank(applicationInfo.getEmail())? applicationInfo.getEmail() : customerInfo.getEmail();
        vars.put("email", email);
        String mobileNumber = !Strings.isBlank(applicationInfo.getMobileNumber())? applicationInfo.getMobileCode() + applicationInfo.getMobileNumber() : customerInfo.getMobileCountryCode() + customerInfo.getMobile();
        vars.put("mobile", mobileNumber);
        vars.put("customerCode" , customerInfo.getCode());
        vars.put("id", applicationInfo.getId().toString());
        vars.put("requestTypeCode", requestType);
        vars.put("identifier", applicationInfo.getIpcNumber());
        vars.put("applicationCategory", applicationInfo.getCategory().getSaipCode());
        vars.put("applicationNumber", applicationInfo.getApplicationNumber());
        vars.put("applicationAccelerated", applicationAccelerated);
        vars.put("titleAr", applicationInfo.getTitleAr());
        vars.put("titleEn", applicationInfo.getTitleEn());
        vars.put("isPlt", applicationInfo.getPltRegisteration().toString().toUpperCase());
        vars.put("fillingDate",applicationInfo.getPltFilingDate());
        vars.put("APPLICANT_CUSTOMER_CODE",getApplicantCustomerCode(applicationInfo.getCreatedByUserId()));
        return vars;
    }

    private String getApplicantCustomerCode(Long userId){
        return customerServiceFeignClient.getCustomerCodeByUserId(userId).getPayload();
    }


}
