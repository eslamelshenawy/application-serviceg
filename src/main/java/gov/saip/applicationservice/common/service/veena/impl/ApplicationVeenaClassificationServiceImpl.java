package gov.saip.applicationservice.common.service.veena.impl;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.dto.veena.ApplicationVeenaClassificationDto;
import gov.saip.applicationservice.common.dto.veena.ApplicationVeenaClassificationRequestDto;
import gov.saip.applicationservice.common.dto.veena.LKVeenaClassificationDto;
import gov.saip.applicationservice.common.mapper.veena.ApplicationVeenaClassificationMapper;
import gov.saip.applicationservice.common.mapper.veena.LKVeenaClassificationMapper;
import gov.saip.applicationservice.common.model.veena.ApplicationVeenaClassification;
import gov.saip.applicationservice.common.model.veena.LKVeenaClassification;
import gov.saip.applicationservice.common.repository.veena.ApplicationVeenaClassificationRepository;
import gov.saip.applicationservice.common.service.veena.ApplicationVeenaClassificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationVeenaClassificationServiceImpl extends BaseServiceImpl<ApplicationVeenaClassification, Long> implements ApplicationVeenaClassificationService {

    private final ApplicationVeenaClassificationRepository applicationVeenaClassificationRepository;
    private final LKVeenaClassificationMapper lkVeenaClassificationMapper;
    private final ApplicationVeenaClassificationMapper applicationVeenaClassificationMapper;

    @Override
    protected BaseRepository<ApplicationVeenaClassification, Long> getRepository() {
        return applicationVeenaClassificationRepository;
    }

    @Override
    public List<LKVeenaClassificationDto> getVeenaClassificationsByAppId(Long appId) {
        List<LKVeenaClassification> veenaClassifications = applicationVeenaClassificationRepository.getVeenaClassificationsByAppId(appId);
        return lkVeenaClassificationMapper.map(veenaClassifications);
    }

    @Override
    public List<ApplicationVeenaClassificationRequestDto> findByApplicationId(Long appId) {
        return applicationVeenaClassificationMapper.map(applicationVeenaClassificationRepository.findByApplicationId(appId));
    }

    @Override
    @Transactional
    public void deleteAllVeenaClassificationsByAppId(Long applicationId) {
        applicationVeenaClassificationRepository.updateIsDeletedByApplication_id(1, applicationId);
    }
}
