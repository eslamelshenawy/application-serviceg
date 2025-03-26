package gov.saip.applicationservice.common.service.industrial.impl;


import com.savoirtech.logging.slf4j.json.LoggerFactory;
import com.savoirtech.logging.slf4j.json.logger.Logger;
import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.dto.industrial.*;
import gov.saip.applicationservice.common.mapper.*;
import gov.saip.applicationservice.common.mapper.industrial.DesignSampleMapper;
import gov.saip.applicationservice.common.model.ApplicationRelevantType;
import gov.saip.applicationservice.common.model.Document;
import gov.saip.applicationservice.common.model.SubClassification;
import gov.saip.applicationservice.common.model.industrial.DesignSample;
import gov.saip.applicationservice.common.model.industrial.DesignSampleDrawings;
import gov.saip.applicationservice.common.model.industrial.IndustrialDesignDetail;
import gov.saip.applicationservice.common.repository.ApplicationRelevantTypeRepository;
import gov.saip.applicationservice.common.repository.DocumentRepository;
import gov.saip.applicationservice.common.repository.industrial.DesignSampleRepository;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.SubClassificationService;
import gov.saip.applicationservice.common.service.impl.ApplicationServiceImpl;
import gov.saip.applicationservice.common.service.industrial.DesignSampleDrawingsService;
import gov.saip.applicationservice.common.service.industrial.DesignSampleService;
import gov.saip.applicationservice.common.service.industrial.IndustrialDesignDetailService;
import gov.saip.applicationservice.common.validators.DesignSampleDrawingsValidator;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import gov.saip.applicationservice.util.TSIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service

public class DesignSampleServiceImpl extends BaseServiceImpl<DesignSample, Long> implements DesignSampleService {


    private final static Logger logger = LoggerFactory.getLogger(DesignSampleServiceImpl.class);

    private final DesignSampleRepository designSampleRepository;


    private final DesignSampleDrawingsService designSampleDrawingsService;


    private final IndustrialDesignDetailService industrialDesignDetailService;

    private final DesignSampleDrawingsValidator designSampleDrawingsValidator;
    private final DesignSampleMapper designSampleMapper;
    private final SubClassificationService subClassificationService;
    private final ClassificationMapper classificationMapper;
    private final SubClassificationMapper subClassificationMapper;
    private final LkClassificationUnitMapper lkClassificationUnitMapper;
    @Autowired
    DocumentRepository documentRepository;
    @Autowired
    DocumentMapper documentMapper;
    @Autowired
    ApplicationRelevantTypeRepository applicationRelevantTypeRepository;
    @Autowired
    ApplicationRelevantTypeMapper applicationRelevantTypeMapper;
    @Autowired
    ApplicationInfoService applicationService;
@Autowired
    public DesignSampleServiceImpl(DesignSampleRepository designSampleRepository, DesignSampleDrawingsService designSampleDrawingsService, @Lazy IndustrialDesignDetailService industrialDesignDetailService, DesignSampleDrawingsValidator designSampleDrawingsValidator, DesignSampleMapper designSampleMapper, SubClassificationService subClassificationService, ClassificationMapper classificationMapper, SubClassificationMapper subClassificationMapper, LkClassificationUnitMapper lkClassificationUnitMapper) {
        this.designSampleRepository = designSampleRepository;
        this.designSampleDrawingsService = designSampleDrawingsService;
        this.industrialDesignDetailService = industrialDesignDetailService;
        this.designSampleDrawingsValidator = designSampleDrawingsValidator;
        this.designSampleMapper = designSampleMapper;
        this.subClassificationService = subClassificationService;
        this.classificationMapper = classificationMapper;
        this.subClassificationMapper = subClassificationMapper;
        this.lkClassificationUnitMapper = lkClassificationUnitMapper;
}
    @Override
    protected BaseRepository<DesignSample, Long> getRepository() {
        return designSampleRepository;
    }

    @Override
    @Transactional
    public IndustrialDesignDetailDto create(CreateDesignSampleDto createDesignSampleDto) {
        try {
            setDeleted(createDesignSampleDto.getDesignSamplesIdsToBeDeleted());
            List<DesignSampleReqDto> designSampleReqDtos = createDesignSampleDto.getDesignSamples();
            Optional<IndustrialDesignDetail> industrialDesignDetailOptional = industrialDesignDetailService.findIndustrialDesignById(createDesignSampleDto.getIndustrialDesignId());
            if (industrialDesignDetailOptional.isEmpty()) {
                throw new BusinessException(Constants.ErrorKeys.INDUSTRIAL_DESIGN_DETAILS_NOT_FOUND, HttpStatus.BAD_REQUEST, null);

            }
            IndustrialDesignDetail industrialDesignDetail = industrialDesignDetailOptional.get();
            industrialDesignDetail.setRequestType(createDesignSampleDto.getRequestType());
            industrialDesignDetailService.insert(industrialDesignDetail);

            for (DesignSampleReqDto designSampleReqDto : designSampleReqDtos) {
                designSampleDrawingsValidator.validateDesignSampleDrawings(designSampleReqDto);
                DesignSample designSample = createDesignSample(designSampleReqDto, industrialDesignDetail);
                DesignSample savedDesignSample = designSampleRepository.saveAndFlush(designSample);
                if (CollectionUtils.isEmpty(savedDesignSample.getDesignSampleDrawings()))
                    savedDesignSample.setDesignSampleDrawings(new LinkedList<>());
                for (DesignSampleDrawingsReqDto designSampleDrawingsReqDto : designSampleReqDto.getDesignSampleDrawings()) {
                    DesignSampleDrawings designSampleDrawing = designSampleDrawingsService.save(designSampleDrawingsReqDto, savedDesignSample);
                    if ((Objects.isNull(designSampleDrawingsReqDto.getId()))) {
                        savedDesignSample.getDesignSampleDrawings().add(designSampleDrawing);
                    }
                }
            }
            IndustrialDesignDetailDto result = industrialDesignDetailService.findDtoById(industrialDesignDetail.getId());
            industrialDesignDetailService.removeDeletedSample(result);
            industrialDesignDetailService.findDocumentFromIndustrialDesignDetail(result);
            designSampleRepository.reIndexDesignSampleCode(industrialDesignDetail.getId());
            return result;
        } catch (BusinessException businessException) {
            logger.error().message(businessException.toString()).log();
            throw businessException;
        } catch (Exception exception) {
            logger.error().exception("exception", exception).message(exception.getMessage()).log();
            throw exception;
        }
    }

    @Override
    public Long count(Long designId) {

        return designSampleRepository.countByIndustrialDesignId(designId) ;
    }

    private DesignSample createDesignSample(DesignSampleReqDto designSampleReqDto, IndustrialDesignDetail industrialDesignDetail) {
        DesignSample designSampleExists = null;
        boolean present = false;
        if (Objects.nonNull(designSampleReqDto.getId())) {
            designSampleExists = findById(designSampleReqDto.getId());
            present = true;
        }
        if (present) {
            designSampleExists.setIndustrialDesignId(industrialDesignDetail.getId());
            designSampleExists.setName(designSampleReqDto.getName());

            saveClassification(designSampleReqDto, designSampleExists);

            return saveDesigners(designSampleReqDto, designSampleExists);
        } else {
            DesignSample designSample = new DesignSample();
            designSample.setName(designSampleReqDto.getName());
            designSample.setId(TSIDUtils.next());
            designSample.setIndustrialDesignId(industrialDesignDetail.getId());

            saveClassification(designSampleReqDto, designSample);

            return saveDesigners(designSampleReqDto, designSample);
        }
    }

    private DesignSample saveDesigners(DesignSampleReqDto designSampleReqDto, DesignSample designSampleExists) {


        // Clear existing applicationRelevantTypes list
        designSampleExists.setApplicationRelevantTypes(new ArrayList<>());

        // Check if designers list is not null and not empty
        if (designSampleReqDto.getDesigners() != null && !designSampleReqDto.getDesigners().isEmpty()) {
            // Iterate through the list of designer IDs and add them as ApplicationRelevantType objects
            List<ApplicationRelevantType> applicationRelevantTypes = designSampleReqDto.getDesigners().stream()
                    .map(id -> {
                        ApplicationRelevantType applicationRelevantType = new ApplicationRelevantType();
                        applicationRelevantType.setId(id);
                        return applicationRelevantType;
                    })
                    .collect(Collectors.toList());

            // Set the new list of applicationRelevantTypes
            designSampleExists.setApplicationRelevantTypes(applicationRelevantTypes);
        }
        return designSampleExists;
    }

    private DesignSample saveClassification(DesignSampleReqDto designSampleReqDto, DesignSample designSampleExists) {
        // Clear existing applicationRelevantTypes list
        designSampleExists.setApplicationRelevantTypes(new ArrayList<>());

        // Check if designers list is not null and not empty
        if (designSampleReqDto.getClassification() != null && !designSampleReqDto.getClassification().isEmpty()) {
            // Iterate through the list of designer IDs and add them as ApplicationRelevantType objects
            List<SubClassification> subClassifications = designSampleReqDto.getClassification().stream()
                    .map(id -> {
                        SubClassification subClassification = new SubClassification();
                        subClassification.setId(id);
                        return subClassification;
                    })
                    .collect(Collectors.toList());

            // Set the new list of applicationRelevantTypes
            designSampleExists.setSubClassifications(subClassifications);
        }else {
            designSampleExists.setSubClassifications(null);
        }

        return designSampleExists;
    }

    private void setDeleted(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        for (String idStr : ids) {
            DesignSample sample = findById(Long.valueOf(idStr));
            sample.setIsDeleted(1);
            designSampleRepository.save(sample);
        }
    }

    public DesignSampleReqDto updateDesigners(DesignSampleReqDto designSampleReqDto) {
        // Retrieve the DesignSample from the repository
        DesignSample designSampleExists = findById(designSampleReqDto.getId());

        // Clear existing applicationRelevantTypes list
        designSampleExists.setApplicationRelevantTypes(new ArrayList<>());

        // Check if designers list is not null and not empty
        if (designSampleReqDto.getDesigners() != null && !designSampleReqDto.getDesigners().isEmpty()) {
            // Iterate through the list of designer IDs and add them as ApplicationRelevantType objects
            List<ApplicationRelevantType> applicationRelevantTypes = designSampleReqDto.getDesigners().stream()
                    .map(id -> {
                        ApplicationRelevantType applicationRelevantType = new ApplicationRelevantType();
                        applicationRelevantType.setId(id);
                        return applicationRelevantType;
                    })
                    .collect(Collectors.toList());

            // Set the new list of applicationRelevantTypes
            designSampleExists.setApplicationRelevantTypes(applicationRelevantTypes);
        }

        // Save the updated DesignSample
        designSampleRepository.save(designSampleExists);

        return designSampleReqDto;
    }

    public DesignSampleReqDto updateClassifications(DesignSampleReqDto designSampleReqDto) {
        // Retrieve the DesignSample from the repository
        DesignSample designSampleExists = findById(designSampleReqDto.getId());

        // Clear existing subClassifications list
        designSampleExists.setSubClassifications(new ArrayList<>());

        // Check if designers list is not null and not empty
        if (designSampleReqDto.getClassification() != null && !designSampleReqDto.getClassification().isEmpty()) {
            // Iterate through the list of designer IDs and add them as ApplicationRelevantType objects
            List<SubClassification> subClassifications = designSampleReqDto.getClassification().stream()
                    .map(id -> {
                        SubClassification subClassification = new SubClassification();
                        subClassification.setId(id);
                        return subClassification;
                    })
                    .collect(Collectors.toList());

            // Set the new list of subClassifications
            designSampleExists.setSubClassifications(subClassifications);
        }

        // Save the updated DesignSample
        designSampleRepository.save(designSampleExists);

        return designSampleReqDto;
    }

    public DesignSampleReqDto updateName(DesignSampleReqDto designSampleReqDto) {
        // Retrieve the DesignSample from the repository
        DesignSample designSampleExists = findById(designSampleReqDto.getId());

        designSampleExists.setName(designSampleReqDto.getName());

        // Save the updated DesignSample
        designSampleRepository.save(designSampleExists);

        return designSampleReqDto;
    }

    @Transactional
    public void deleteSample(Long id) {
        DesignSample sample = findById(id);
        designSampleRepository.updateIsDeleted(id, 1);
        designSampleRepository.reIndexDesignSampleCode(sample.getIndustrialDesignId());
    }

    public PaginationDto<List<DesignSampleResDto>> findDesignSamplesByIndustrialDesignId(Long appId,String query ,Boolean withDescription,  Integer page, Integer limit){
        Pageable pageable = PageRequest.of(page, limit, Sort.by("id").ascending());
        withDescription = (withDescription != null && !withDescription) ? null : true;
        Page<DesignSample> designSampleList = designSampleRepository.findDesignSamplesByIndustrialDesignId(appId,query , withDescription , pageable);
         List<DesignSampleResDto> designSampleResDtos = designSampleMapper.map(designSampleList.stream().toList());

        for (DesignSampleResDto sample : designSampleResDtos) {
            List<ApplicationRelevantTypeDto> applicationRelevantTypeDtoList = sample.getApplicationRelevantTypes();
            applicationService.assignDataOfCustomersCode(applicationRelevantTypeDtoList);
            sample.setApplicationRelevantTypes(applicationRelevantTypeDtoList);
             processSample(sample);
        }
        return PaginationDto.<List<DesignSampleResDto>>builder()
                .content(designSampleResDtos)
                .totalPages(designSampleList.getTotalPages())
                .totalElements(designSampleList.getTotalElements())
                .build();
    }
    public DesignSampleResDto processSample(DesignSampleResDto sample) {
        List<Long> ids = extractSubClassificationIds(sample);
        List<SubClassification> subClassifications = subClassificationService.findByIdIn(ids);
        Map<Long, LkClassificationUnitDto> unitMap = buildUnitMap(subClassifications);
        sample.setUnitList(new ArrayList<>(unitMap.values()));
        TreeSet<DesignSampleDrawingsResDto> designSampleDrawings = sample.getDesignSampleDrawings();
        if (designSampleDrawings != null) {
            for (DesignSampleDrawingsResDto drawing : designSampleDrawings) {
                Long docId = drawing.getDocId();
                if (docId != null) {
                    Document document = documentRepository.findById(docId).orElseThrow(() -> new RuntimeException("no founded id "));
                    DocumentDto documentDto = documentMapper.mapToDto(document);
                    drawing.setDocument(documentDto);
                }
            }
        }

        return sample;
    }
    private Map<Long, LkClassificationUnitDto> buildUnitMap(List<SubClassification> subClassifications) {
        Map<Long, LkClassificationUnitDto> unitMap = new HashMap<>();
        for (SubClassification subClassification : subClassifications) {
            var unitId = subClassification.getClassification().getUnit().getId();
            var classification = classificationMapper.mapLight(subClassification.getClassification());
            var subClassificationDto = subClassificationMapper.map(subClassification);

            unitMap.computeIfAbsent(unitId, key -> {
                        var unitDto = lkClassificationUnitMapper.map(subClassification.getClassification().getUnit());
                        unitDto.setClassifications(new ArrayList<>());
                        return unitDto;
                    }).getClassifications().stream()
                    .filter(c -> c.getId() == classification.getId())
                    .findFirst()
                    .ifPresentOrElse(
                            c -> c.getSubClassificationDtos().add(subClassificationDto),
                            () -> {
                                classification.setSubClassificationDtos(new ArrayList<>());
                                classification.getSubClassificationDtos().add(subClassificationDto);
                                unitMap.get(unitId).getClassifications().add(classification);
                            }
                    );
        }
        return unitMap;
    }

    private List<Long> extractSubClassificationIds(DesignSampleResDto sample) {
        List<Long> ids = new ArrayList<>();
        if (sample.getSubClassifications() != null && !sample.getSubClassifications().isEmpty()) {
            for (SubClassificationDto subClassificationDto : sample.getSubClassifications()) {
                ids.add(subClassificationDto.getId());
            }
        }
        return ids;
    }

    public DesignSampleReqDto updateDescription(DesignSampleReqDto designSampleReqDto) {
        // Retrieve the DesignSample from the repository
        DesignSample designSampleExists = findById(designSampleReqDto.getId());

        designSampleExists.setDescription(designSampleReqDto.getDescription());

        // Save the updated DesignSample
        designSampleRepository.save(designSampleExists);

        return designSampleReqDto;
    }
    public List<DesignSample> findDesignSamplesByAppId(Long appId){
        return designSampleRepository.findDesignSamplesByAppId(appId);
    }

    public void deleteWithOldLocarno() {
        designSampleRepository.deleteWithOldLocarno();
    }


}
