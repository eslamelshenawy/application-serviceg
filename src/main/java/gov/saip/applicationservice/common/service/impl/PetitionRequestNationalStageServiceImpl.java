package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.common.clients.rest.feigns.BPMCallerFeignClient;
import gov.saip.applicationservice.common.clients.rest.feigns.CustomerServiceFeignClient;
import gov.saip.applicationservice.common.dto.ApplicationNumberGenerationDto;
import gov.saip.applicationservice.common.dto.CustomerSampleInfoDto;
import gov.saip.applicationservice.common.dto.PctIValidateDto;
import gov.saip.applicationservice.common.dto.RequestTasksDto;
import gov.saip.applicationservice.common.dto.bpm.CompleteTaskRequestDto;
import gov.saip.applicationservice.common.dto.bpm.StartProcessDto;
import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import gov.saip.applicationservice.common.enums.RequestTypeEnum;
import gov.saip.applicationservice.common.enums.SupportServiceRequestStatusEnum;
import gov.saip.applicationservice.common.mapper.PetitionRequestNationalStageMapper;
import gov.saip.applicationservice.common.model.ApplicationSearch;
import gov.saip.applicationservice.common.model.PetitionRequestNationalStage;
import gov.saip.applicationservice.common.repository.PetitionRequestNationalStageRepository;
import gov.saip.applicationservice.common.repository.SupportServiceRequestRepository;
import gov.saip.applicationservice.common.service.PetitionRequestNationalStageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.Map;

import static gov.saip.applicationservice.common.enums.SupportServiceType.PETITION_REQUEST_NATIONAL_STAGE;
import static gov.saip.applicationservice.common.enums.SupportServiceType.TRADEMARK_APPLICATION_SEARCH;


@Service
@AllArgsConstructor
public class PetitionRequestNationalStageServiceImpl extends SupportServiceRequestServiceImpl<PetitionRequestNationalStage>
        implements PetitionRequestNationalStageService {


    private final PetitionRequestNationalStageRepository petitionRequestNationalStageRepository;
    private final CustomerServiceFeignClient customerServiceFeignClient;
    private final BPMCallerFeignClient bpmCallerFeignClient;


    @Override
    public SupportServiceRequestRepository getSupportServiceRequestRepository() {

        return petitionRequestNationalStageRepository;
    }



    @Override
    public PetitionRequestNationalStage insert(PetitionRequestNationalStage petitionRequestNationalStage) {
        return super.insert(PETITION_REQUEST_NATIONAL_STAGE, petitionRequestNationalStage);
    }

    @Override
    @Transactional
    public void paymentCallBackHandler(Long id , ApplicationNumberGenerationDto applicationNumberGenerationDto) {
        petitionRequestNationalStageStartProcess(id);
        super.paymentCallBackHandler(id , applicationNumberGenerationDto);
    }


    @Override
    public SupportServiceRequestStatusEnum getPaymentRequestStatus() {
        return SupportServiceRequestStatusEnum.UNDER_PROCEDURE;
    }


    @Override
    public PetitionRequestNationalStage update(PetitionRequestNationalStage entity) {
        PetitionRequestNationalStage petitionRequestNationalStage = findById(entity.getId());
        petitionRequestNationalStage.setGlobalApplicationNumber(entity.getGlobalApplicationNumber() != null ? entity.getGlobalApplicationNumber() : petitionRequestNationalStage.getGlobalApplicationNumber());
        petitionRequestNationalStage.setReason(entity.getReason() != null ? entity.getReason() : petitionRequestNationalStage.getReason());
        petitionRequestNationalStage.setPetitionRequestNationalStageDocuments(entity.getPetitionRequestNationalStageDocuments() != null ? entity.getPetitionRequestNationalStageDocuments() : petitionRequestNationalStage.getPetitionRequestNationalStageDocuments());
        PetitionRequestNationalStage updatedPetitionRequestNationalStage = super.update(petitionRequestNationalStage);
        completeUserTask(petitionRequestNationalStage);
        this.updateRequestStatusByCode(petitionRequestNationalStage.getId() , SupportServiceRequestStatusEnum.UNDER_PROCEDURE);
        return updatedPetitionRequestNationalStage;
    }


    public void completeUserTask(PetitionRequestNationalStage petitionRequestNationalStage){
        RequestTasksDto requestTasksDto = bpmCallerFeignClient.getTaskByRowIdAndType(RequestTypeEnum.PETITION_REQUEST_NATIONAL_STAGE, petitionRequestNationalStage.getId()).getPayload();
        Map<String, Object> approved = new LinkedHashMap();
        approved.put("value", "YES");
        Map<String, Object> processVars = new LinkedHashMap<>();
        processVars.put("approved", approved);
        CompleteTaskRequestDto completeTaskRequestDto = new CompleteTaskRequestDto();
        completeTaskRequestDto.setVariables(processVars);
        bpmCallerFeignClient.completeUserTask(requestTasksDto.getTaskId(), completeTaskRequestDto);
    }




    private String setCategoryToPetitionRequestNationalStage(String supportServiceType){
        StartProcessDto startProcessDto = new StartProcessDto();
        if (supportServiceType.equals(PETITION_REQUEST_NATIONAL_STAGE.name())){
            startProcessDto.setApplicationCategory(ApplicationCategoryEnum.PATENT.name());
        }
        return startProcessDto.getApplicationCategory();
    }








    protected void petitionRequestNationalStageStartProcess(Long id){
        PetitionRequestNationalStage petitionRequestNationalStage = findById(id);
        CustomerSampleInfoDto customerSampleInfoDto = customerServiceFeignClient.getAnyCustomerByCustomerCode(petitionRequestNationalStage.getCreatedByCustomerCode()).getPayload();
        StartProcessDto startProcessDto = StartProcessDto.builder()
                .id(petitionRequestNationalStage.getId().toString())
                .fullNameAr(customerSampleInfoDto.getNameAr())
                .fullNameEn(customerSampleInfoDto.getNameEn())
                .mobile(customerSampleInfoDto.getMobile())
                .email(customerSampleInfoDto.getEmail())
                .identifier(customerSampleInfoDto.getIdentifier())
                .applicationCategory(setCategoryToPetitionRequestNationalStage(petitionRequestNationalStage.getLkSupportServices().getCode().name()))
                .applicantUserName(petitionRequestNationalStage.getCreatedByUser())
                .processName("petition_request_national_stage_process")
                .requestTypeCode("PETITION_REQUEST_NATIONAL_STAGE")
                .supportServiceCode(petitionRequestNationalStage.getLkSupportServices().getCode().name())
                .requestNumber(petitionRequestNationalStage.getRequestNumber())
                .build();
        startSupportServiceProcess(petitionRequestNationalStage, startProcessDto);
    }


    @Override
    public boolean checkPetitionRequestNumberToUseInPct(String requestNumber) {
        return petitionRequestNationalStageRepository.checkPetitionRequestNumberToUseInPct(requestNumber);
    }


    public PctIValidateDto getPctForPetitionRequestNumber(String requestNumber) {
        return petitionRequestNationalStageRepository.checkPetitionRequestNumber(requestNumber);
    }

    @Override
    public Long getPetitionRequestNationalStageSupportServicesProcessRequestId(Long applicationId) {
        return petitionRequestNationalStageRepository.getSupportServicesProcessRequestId(applicationId);
    }
}
