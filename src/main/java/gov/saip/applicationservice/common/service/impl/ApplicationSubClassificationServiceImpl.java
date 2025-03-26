package gov.saip.applicationservice.common.service.impl;


import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.dto.ApplicationSubClassificationDto;
import gov.saip.applicationservice.common.dto.SubClassificationDto;
import gov.saip.applicationservice.common.enums.SubClassificationType;
import gov.saip.applicationservice.common.mapper.SubClassificationMapper;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.ApplicationNiceClassification;
import gov.saip.applicationservice.common.model.SubClassification;
import gov.saip.applicationservice.common.model.ApplicationSubClassification;
import gov.saip.applicationservice.common.repository.ApplicationNiceClassificationRepository;
import gov.saip.applicationservice.common.repository.ApplicationSubClassificationRepository;
import gov.saip.applicationservice.common.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ApplicationSubClassificationServiceImpl extends BaseServiceImpl<ApplicationSubClassification, Long> implements ApplicationSubClassificationService {

    private final SubClassificationService subClassificationService;
    private final ApplicationSubClassificationRepository applicationSubClassificationsRepository;
    private final ApplicationInfoService applicationInfoService;
    private final LkClassificationVersionService classificationVersionService;
    private final SubClassificationMapper subClassificationMapper;
    private final ApplicationNiceClassificationService applicationNiceClassificationService;
    private final ApplicationNiceClassificationRepository applicationNiceClassificationRepository;
    @Override
    protected BaseRepository<ApplicationSubClassification, Long> getRepository() {
        return applicationSubClassificationsRepository;
    }

    private void addDetailList(ApplicationSubClassificationDto applicationSubClassificationDto, Long applicationId){
        List<SubClassification> selectedProducts = subClassificationService.findByIdIn(applicationSubClassificationDto.getSelectedSubClassifications());
        List<ApplicationSubClassification> addedList = new ArrayList<>();
        for (SubClassification selectedProduct : selectedProducts) {
            ApplicationSubClassification applicationSubClassifications = new ApplicationSubClassification();
            applicationSubClassifications.setSubClassification(selectedProduct);
            applicationSubClassifications.setApplicationInfo(new ApplicationInfo(applicationId));
            addedList.add(applicationSubClassifications);
        }
        if(!addedList.isEmpty())
              saveAll(addedList);
    }

    private void addShortcutList(ApplicationSubClassificationDto applicationSubClassificationDto, Long applicationId){
        List<SubClassification> shortcutList = subClassificationService.findSubClassByIsShortcutAndCategoryId(applicationSubClassificationDto.getClassificationId());
        List<ApplicationSubClassification> addedList = new ArrayList<>();
        if (applicationSubClassificationDto.getUnSelectedSubClassifications() != null) {
            List<SubClassification> unselectedProducts = subClassificationService.findByIdIn(applicationSubClassificationDto.getUnSelectedSubClassifications());
            shortcutList.removeAll(unselectedProducts);
        }

        for (SubClassification shortcutProduct : shortcutList) {
            ApplicationSubClassification applicationSubClassifications = new ApplicationSubClassification();
            applicationSubClassifications.setSubClassification(shortcutProduct);
            applicationSubClassifications.setApplicationInfo(new ApplicationInfo(applicationId));
            addedList.add(applicationSubClassifications);
        }
        if(!addedList.isEmpty())
            saveAll(addedList);
    }

    @Override
    @Transactional
    public Long createApplicationSubClassification(ApplicationSubClassificationDto applicationSubClassificationDto, Long applicationId) {
        // delete old classification and sub classification
        this.deleteAllByApplicationId(applicationId);
        applicationNiceClassificationService.deleteAllByApplicationId(applicationId);
        // insert new classification
        ApplicationNiceClassification applicationNiceClassification = new ApplicationNiceClassification(
                applicationId, applicationSubClassificationDto.getClassificationId(), applicationSubClassificationDto.getSubClassificationType());
        if(applicationSubClassificationDto.getVersionId() != null) {
            applicationNiceClassification.setVersion(classificationVersionService.getReferenceById(applicationSubClassificationDto.getVersionId()));
        }
        applicationNiceClassificationService.insert(applicationNiceClassification);
        // insert new sub classification
        if (applicationSubClassificationDto.getSubClassificationType().equals(SubClassificationType.DETAIL_LIST)) {
            addDetailList(applicationSubClassificationDto, applicationId);
        } else if (applicationSubClassificationDto.getSubClassificationType().equals(SubClassificationType.SHORTCUT_LIST)) {
            addShortcutList(applicationSubClassificationDto, applicationId);
        }
        return applicationId;
    }


    @Override
    @Transactional
    public Long updateApplicationSubClassification(ApplicationSubClassificationDto applicationSubClassificationDto, Long applicationId) {
        ApplicationInfo applicationInfo = applicationInfoService.findById(applicationId);
        ApplicationNiceClassification applicationNiceClassification = applicationNiceClassificationRepository.getByApplicationIdAndCategory(applicationId, applicationSubClassificationDto.getClassificationId());
        
        // if classification type or classification id are changed we will remove all and re-insert again [it's an add operation]
        if ((applicationNiceClassification == null) ||
           (applicationNiceClassification.getSubClassificationType() != null &&
           !applicationNiceClassification.getSubClassificationType().equals(applicationSubClassificationDto.getSubClassificationType())
           ) ||
           (applicationNiceClassification.getClassification().getId() != applicationSubClassificationDto.getClassificationId()))
        {
            createApplicationSubClassification(applicationSubClassificationDto, applicationId);
            return applicationInfo.getId();
        }

        // if user does not change classification type or classification id that mean he has changed in items, so we will insert the selected and delete the unselected
        if (!applicationSubClassificationDto.getSelectedSubClassifications().isEmpty()) {
            // get saved ids
            Set<Long> persistedSubClassifications = applicationSubClassificationsRepository.getPersistedSubClassificationsByAppIdAndClassificationId(applicationId, applicationSubClassificationDto.getClassificationId());
            // get
            Set<Long> newProducts = new HashSet<Long>(applicationSubClassificationDto.getSelectedSubClassifications());
            newProducts.removeAll(persistedSubClassifications);
            if (Objects.nonNull(newProducts) && !newProducts.isEmpty()) {
                applicationSubClassificationDto.setSelectedSubClassifications(new ArrayList<Long>(newProducts));
                addDetailList(applicationSubClassificationDto, applicationInfo.getId());
            }
        }

        if (!applicationSubClassificationDto.getUnSelectedSubClassifications().isEmpty())
            deleteBySubClassIdInAndTrademarkDetailId(applicationSubClassificationDto.getUnSelectedSubClassifications(), applicationInfo.getId(), applicationSubClassificationDto.getClassificationId());

       return applicationInfo.getId();
    }

    @Override
    public void deleteBySubClassIdInAndTrademarkDetailId(List<Long> ids, Long trademarkDetailId, Long categoryId) {
        applicationSubClassificationsRepository.deleteBySubClassIdInAndApplicationId(ids, trademarkDetailId, categoryId);
    }

    @Override
    public List<ApplicationSubClassification> findByApplicationInfoId(Long applicationId) {
        return applicationSubClassificationsRepository.findByApplicationInfoId(applicationId);
    }

    @Override
    public long countByApplicationInfoId(Long applicationId) {
        return applicationSubClassificationsRepository.countByApplicationInfoId(applicationId);
    }


    @Override
    @Transactional
    public void deleteByAppIdAndClassId(Long appId, Long classId) {
        this.applicationSubClassificationsRepository.deleteByApplicationIdAndClassificationId(appId, classId);
        this.applicationNiceClassificationRepository.deleteByApplicationIdAndClassificationId(appId, classId);
    }

    @Override
    public List<SubClassificationDto> listApplicationSubClassifications(Long id,int page,int limit) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by("id"));
        Page<ApplicationSubClassification> applicationSubClassifications = applicationSubClassificationsRepository.findByApplicationInfoId(id,pageable);
        List<SubClassificationDto> subClassificationDtos = new ArrayList<>();
        for (ApplicationSubClassification applicationSubClassification : applicationSubClassifications.getContent()) {
            subClassificationDtos.add(subClassificationMapper.map(applicationSubClassification.getSubClassification()));
        }
        return subClassificationDtos;
    }

    @Override
    public List<SubClassificationDto> listApplicationSubClassifications(Long id) {
        List<ApplicationSubClassification> applicationSubClassifications = applicationSubClassificationsRepository.findByApplicationInfoId(id);
        List<SubClassificationDto> subClassificationDtos = new ArrayList<>();
        for (ApplicationSubClassification applicationSubClassification : applicationSubClassifications) {
            subClassificationDtos.add(subClassificationMapper.map(applicationSubClassification.getSubClassification()));
        }
        return subClassificationDtos;
    }

    @Override
    @Transactional
    public void deleteByAppIdAndSubClassificationIds(Long appId, List<Long> subClassificationIds) {
        applicationSubClassificationsRepository.deleteByApplicationIdAndSubClassificationIds(appId, subClassificationIds);

    }

    @Override
    public void deleteAllByApplicationId(Long applicationId) {
        applicationSubClassificationsRepository.deleteAllByApplicationId(applicationId);
    }

}
