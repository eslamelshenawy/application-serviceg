package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.common.clients.rest.feigns.BPMCallerFeignClient;
import gov.saip.applicationservice.common.clients.rest.feigns.CustomerServiceFeignClient;
import gov.saip.applicationservice.common.clients.rest.feigns.IpsearchFeignClient;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.dto.bpm.CompleteTaskRequestDto;
import gov.saip.applicationservice.common.dto.bpm.StartProcessDto;
import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import gov.saip.applicationservice.common.enums.RequestTypeEnum;
import gov.saip.applicationservice.common.enums.SupportServiceRequestStatusEnum;
import gov.saip.applicationservice.common.mapper.ApplicationSearchMapper;
import gov.saip.applicationservice.common.mapper.ApplicationSearchSimilarsMapper;
import gov.saip.applicationservice.common.model.ApplicationSearch;
import gov.saip.applicationservice.common.model.ApplicationSearchSimilars;
import gov.saip.applicationservice.common.repository.ApplicationSearchRepository;
import gov.saip.applicationservice.common.repository.ApplicationSearchSimilarRepository;
import gov.saip.applicationservice.common.repository.SupportServiceRequestRepository;
import gov.saip.applicationservice.common.service.ApplicationSearchService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static gov.saip.applicationservice.common.enums.SupportServiceType.TRADEMARK_APPLICATION_SEARCH;


@Service
@AllArgsConstructor
@Transactional
public class ApplicationSearchServiceImpl extends SupportServiceRequestServiceImpl<ApplicationSearch>
 implements ApplicationSearchService {

    @Value("${api.key.value}")
    private String apiKey;

    private final ApplicationSearchRepository applicationSearchRepository;
    private final CustomerServiceFeignClient customerServiceFeignClient;
    private final ApplicationSearchMapper applicationSearchMapper;
    private final BPMCallerFeignClient bpmCallerFeignClient;
    private final IpsearchFeignClient ipsearchFeignClient;
    private final ApplicationSearchSimilarRepository applicationSearchSimilarRepository;
    private final ApplicationSearchSimilarsMapper applicationSearchSimilarsMapper;

    @Override
    public SupportServiceRequestRepository getSupportServiceRequestRepository() {
        return applicationSearchRepository;
    }


    @Override
    public ApplicationSearch insert(ApplicationSearch applicationSearch) {
        ApplicationSearchDto applicationSearchDto = applicationSearchMapper.map(applicationSearch);
        return super.insert(TRADEMARK_APPLICATION_SEARCH, applicationSearchMapper.unMap(applicationSearchDto));
    }

    @Override
    @Transactional
    public void paymentCallBackHandler(Long id , ApplicationNumberGenerationDto applicationNumberGenerationDto) {
        applicationSearchStartProcess(id);
        super.paymentCallBackHandler(id , applicationNumberGenerationDto);
    }


    @Override
    public SupportServiceRequestStatusEnum getPaymentRequestStatus() {
        return SupportServiceRequestStatusEnum.UNDER_PROCEDURE;
    }



    @Override
    public List<ApplicationSearchSimilarsDto> fetchSimilarApplicationsFromIpSearch(Long applicationSearchId){
        ApplicationSearch applicationSearch = findById(applicationSearchId);
        IpSearchDto ipSearchDto = ipsearchFeignClient.getSimilarApplications(String.valueOf(applicationSearch.getRequestNumber()),apiKey);
        List<ApplicationSearchSimilars> applicationSearchSimilars = mapAndSaveSimilarApplications(ipSearchDto, applicationSearch);
        return applicationSearchSimilars.stream()
                .map(applicationSearchSimilarsMapper::map)
                .collect(Collectors.toList());
    }


    private List<ApplicationSearchSimilars> mapAndSaveSimilarApplications(IpSearchDto ipSearchDto, ApplicationSearch applicationSearch) {
        List<ApplicationSearchSimilars> applicationSearchSimilars = ipSearchDto.getData().stream()
                .map(IpsearchDataDto::getData)
                .map(IpsearchDetailDataDto::getBibliographicData)
                .map(doc -> {
                    ApplicationSearchSimilars similars = applicationSearchMapper.ipSearchDocumentToSimilar(doc);
                    similars.setApplicationSearch(applicationSearch);
                    return similars;
                })
                .collect(Collectors.toList());

        return applicationSearchSimilarRepository.saveAll(applicationSearchSimilars);
    }


    @Override
    public List<ApplicationSearchSimilarsDto> getSavedApplicationSimilars(Long applicationSearchId) {
        return applicationSearchRepository.getSavedApplicationSimilars(applicationSearchId);
    }
    @Override
    public ApplicationSearch update(ApplicationSearch entity) {
        ApplicationSearch applicationSearch = findById(entity.getId());
        applicationSearch.setNotes(entity.getNotes() != null ? entity.getNotes() : applicationSearch.getNotes());
        applicationSearch.setApplicationSearchDocument(entity.getApplicationSearchDocument() != null ? entity.getApplicationSearchDocument() : applicationSearch.getApplicationSearchDocument());
        applicationSearch.setTitle(entity.getTitle());
        applicationSearch.setClassification(entity.getClassification());
        applicationSearch.setDescription(entity.getDescription());
        ApplicationSearch updatedApplicationSearch = super.update(applicationSearch);
        completeUserTask(updatedApplicationSearch);
        this.updateRequestStatusByCode(applicationSearch.getId(), SupportServiceRequestStatusEnum.UNDER_PROCEDURE);
        return updatedApplicationSearch;
    }
    public void completeUserTask(ApplicationSearch applicationSearch){
        RequestTasksDto requestTasksDto = bpmCallerFeignClient.getTaskByRowIdAndType(RequestTypeEnum.TRADEMARK_APPLICATION_SEARCH, applicationSearch.getId()).getPayload();
        Map<String, Object> approved = new LinkedHashMap();
        approved.put("value", "NOT_SIMILAR");
        Map<String, Object> processVars = new LinkedHashMap<>();
        processVars.put("approved", approved);
        CompleteTaskRequestDto completeTaskRequestDto = new CompleteTaskRequestDto();
        completeTaskRequestDto.setVariables(processVars);
        bpmCallerFeignClient.completeUserTask(requestTasksDto.getTaskId(), completeTaskRequestDto);
    }


    private String setCategoryToApplicationSearch(String supportServiceType){
        StartProcessDto startProcessDto = new StartProcessDto();
        if (supportServiceType.equals(TRADEMARK_APPLICATION_SEARCH.name())){
            startProcessDto.setApplicationCategory(ApplicationCategoryEnum.TRADEMARK.name());
        }
        return startProcessDto.getApplicationCategory();
    }




    protected void applicationSearchStartProcess(Long id){
        ApplicationSearch applicationSearch = findById(id);
        CustomerSampleInfoDto customerSampleInfoDto = customerServiceFeignClient.getAnyCustomerByCustomerCode(applicationSearch.getCreatedByCustomerCode()).getPayload();
        StartProcessDto startProcessDto = StartProcessDto.builder()
                .id(applicationSearch.getId().toString())
                .fullNameAr(customerSampleInfoDto.getNameAr())
                .fullNameEn(customerSampleInfoDto.getNameEn())
                .mobile(customerSampleInfoDto.getMobile())
                .email(customerSampleInfoDto.getEmail())
                .identifier(customerSampleInfoDto.getIdentifier())
                .applicationCategory(setCategoryToApplicationSearch(applicationSearch.getLkSupportServices().getCode().name()))
                .applicantUserName(applicationSearch.getCreatedByUser())
                .processName("application_search_process")
                .requestTypeCode("TRADEMARK_APPLICATION_SEARCH")
                .supportServiceCode(applicationSearch.getLkSupportServices().getCode().name())
                .build();
        // todo add request number?!
        toStartSupportServiceProcess(applicationSearch, startProcessDto);
    }

    public void toStartSupportServiceProcess(ApplicationSearch entity, StartProcessDto startProcessDto){
        startSupportServiceProcess(entity, startProcessDto);
    }
}
