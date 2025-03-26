package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.annotation.CheckCustomerAccess;
import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.dto.ClassificationDto;
import gov.saip.applicationservice.common.dto.ClassificationLightDto;
import gov.saip.applicationservice.common.dto.ListApplicationClassificationDto;
import gov.saip.applicationservice.common.dto.trademark.ApplicationTradeMarkListClassificationsDto;
import gov.saip.applicationservice.common.mapper.ClassificationMapper;
import gov.saip.applicationservice.common.model.ApplicationNiceClassification;
import gov.saip.applicationservice.common.model.Classification;
import gov.saip.applicationservice.common.repository.ApplicationNiceClassificationRepository;
import gov.saip.applicationservice.common.service.ApplicationNiceClassificationService;
import gov.saip.applicationservice.common.service.ClassificationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ApplicationNiceClassificationsImpl extends BaseServiceImpl<ApplicationNiceClassification, Long> implements ApplicationNiceClassificationService {


    private final ApplicationNiceClassificationRepository applicationNiceClassificationRepository;
    private final ClassificationMapper  classificationMapper ;
    private final ClassificationService classificationService;
    @Override
    protected BaseRepository<ApplicationNiceClassification, Long> getRepository() {
        return applicationNiceClassificationRepository;
    }

    @Override
    public List<ClassificationDto> listSelectedApplicationNiceClassifications(Long id) {
        List<ApplicationNiceClassification> classifications =    applicationNiceClassificationRepository.getSelectedApplicationClassifications(id);
        return classificationMapper.map(classifications.stream().map(classification->classification.getClassification()).toList());
    }

    @Override
    public List<ClassificationLightDto> getLightNiceClassificationsByAppId(Long id) {
        List<Classification> appNiceClassification = applicationNiceClassificationRepository.getLightNiceClassificationsByAppId(id);
        Collection<ClassificationLightDto> classificationLightDtos = classificationMapper.mapLight(appNiceClassification);
        return classificationLightDtos.stream().toList();
    }

    @Override
    @Transactional
    public void deleteAllByApplicationId(Long id) {
        applicationNiceClassificationRepository.deleteAllByApplicationId(id);
    }

    @Override
    @CheckCustomerAccess(categoryCodeParamIndex = 1)
    public ApplicationTradeMarkListClassificationsDto getApplicantsAndClassifications(Long appId){
        ApplicationTradeMarkListClassificationsDto result = new ApplicationTradeMarkListClassificationsDto();
        List<ListApplicationClassificationDto> listApplicationClassifications = classificationService.listApplicationClassification(appId);
        if(Objects.nonNull(listApplicationClassifications))
            result.setClassifications(listApplicationClassifications);

        return result;
    }
}
