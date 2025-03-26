package gov.saip.applicationservice.common.service.impl;


import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import gov.saip.applicationservice.common.mapper.ApplicationClassificationMapper;
import gov.saip.applicationservice.common.mapper.ClassificationMapper;
import gov.saip.applicationservice.common.mapper.SubClassificationMapper;
import gov.saip.applicationservice.common.mapper.lookup.LkClassificationVersionMapper;
import gov.saip.applicationservice.common.model.Classification;
import gov.saip.applicationservice.common.model.LkApplicationCategory;
import gov.saip.applicationservice.common.model.LkClassificationUnit;
import gov.saip.applicationservice.common.model.LkClassificationVersion;
import gov.saip.applicationservice.common.repository.ClassificationRepository;
import gov.saip.applicationservice.common.repository.LkClassificationVersionRepository;
import gov.saip.applicationservice.common.service.ClassificationService;
import gov.saip.applicationservice.common.service.LkClassificationVersionService;
import gov.saip.applicationservice.common.service.SubClassificationService;
import gov.saip.applicationservice.common.service.lookup.LkApplicationCategoryService;
import gov.saip.applicationservice.common.service.lookup.LkClassificationUnitService;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import joptsimple.internal.Strings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class ClassificationServiceImpl extends BaseServiceImpl<Classification, Long> implements ClassificationService {

    private final ClassificationRepository classificationRepository;
    @Lazy
    @Autowired
    private  SubClassificationService subClassificationService;
    private final ClassificationMapper classificationMapper;
    private final SubClassificationMapper subClassificationMapper;
    private final LkClassificationVersionService lkClassificationVersionService;
    private final LkApplicationCategoryService lkApplicationCategoryService;
    private final LkClassificationVersionRepository lkClassificationVersionRepository;
    private final LkClassificationVersionMapper lkClassificationVersionMapper;
    private final ApplicationClassificationMapper applicationClassificationMapper;
    @Override
    protected BaseRepository<Classification, Long> getRepository() {
        return classificationRepository;
    }
    @Lazy
    @Autowired
    private LkClassificationUnitService lkClassificationUnitService;
////////////////////////////////////////

    @Override
//    @Cacheable( cacheNames = "Classification_getAllClassifications",key = "{#page, #limit, #search,#versionId, #saipCode, #categoryId}", unless = "#result == null")
//    @CircuitBreaker(name = "default", fallbackMethod = "getAllClassificationsCacheFallBack")
    public PaginationDto getAllClassifications(int page, int limit, String search, Integer versionId, String saipCode, Long categoryId, Long unitId) {
        return getAllClassificationsCacheHelper(page, limit, search, versionId, saipCode, categoryId , unitId);
    }

    private PaginationDto<Object> getAllClassificationsCacheHelper(int page, int limit, String search, Integer versionId, String saipCode, Long categoryId, Long unitId) {
        Pageable pageable = PageRequest.of(page, limit);
        if (Strings.isNullOrEmpty(saipCode)) {
            switch (categoryId.intValue()) {
                case 1 -> saipCode = ApplicationCategoryEnum.PATENT.name();
                case 2 -> saipCode = ApplicationCategoryEnum.INDUSTRIAL_DESIGN.name();
                case 5 -> saipCode = ApplicationCategoryEnum.TRADEMARK.name();
            }
        }
        Page<Classification> categories = null;
        if(categoryId != null && categoryId == 2) {
            categories = classificationRepository.findByUnitId(unitId,pageable);
        }else{
            versionId = versionId == null ? lkClassificationVersionService.getLatestVersionIdBySaipCode(saipCode) : versionId;
            categories = classificationRepository.findBySearchAndCategory(search, versionId, saipCode, categoryId,pageable);
        }

        return PaginationDto.builder()
                .content(classificationMapper.mapListOfClassificationsToListOfDtos(categories.getContent()))
                .totalPages(categories.getTotalPages())
                .totalElements(categories.getTotalElements())
                .build();
    }
    public PaginationDto getAllClassificationsCacheFallBack(int page, int limit, String search, Integer versionId, String saipCode, Long categoryId,Throwable throwable) {
        log.error("Circuit breaker opened for getAllClassificationsCacheFallBack: {}", throwable.getMessage());

        return getAllClassificationsCacheHelper(page, limit, search, versionId, saipCode, categoryId , null);
    }

    /////////////////////////////////////////////////////////////
    @Override
//    @Cacheable( cacheNames = "Classification_findByCategoryId",key = "#categoryId", unless = "#result == null")
//    @CircuitBreaker(name = "default", fallbackMethod = "findByCategoryIdCacheFallBack")
    public List<ClassificationDto> findByCategoryId(Long categoryId) {
        return findByCategoryIdCacheHelper(categoryId);
    }
    private List<ClassificationDto> findByCategoryIdCacheHelper(Long categoryId) {
        Integer latestVersionId = lkClassificationVersionService.getLatestVersionIdByCategoryId(categoryId);
        return classificationMapper.map(classificationRepository.findByCategoryId(categoryId, latestVersionId));
    }

    public List<ClassificationDto> findByCategoryIdCacheFallBack(Long categoryId,Throwable throwable) {
        log.error("Circuit breaker opened for findByCategoryIdCacheFallBack: {}", throwable.getMessage());
        return findByCategoryIdCacheHelper(categoryId);
    }
    ///////////////////////////////////////////////////////
    @Override
//    @Cacheable( cacheNames = "Classification_findByUnitIdAndCategory",key = "{#categorySaipCode,#unitId}", unless = "#result == null")
//    @CircuitBreaker(name = "default", fallbackMethod = "findByUnitIdAndCategoryCacheFallBack")
    public List<ClassificationDto> findByUnitIdAndCategory(String categorySaipCode, Long unitId) {
        return findByUnitIdAndCategoryCacheHelper(categorySaipCode, unitId);
    }


    private List<ClassificationDto> findByUnitIdAndCategoryCacheHelper(String categorySaipCode, Long unitId) {
        Integer latestVersionId = lkClassificationVersionService.getLatestVersionIdBySaipCode(categorySaipCode);
        return classificationMapper.map(classificationRepository.findByUnitIdAndCategory(latestVersionId, categorySaipCode, unitId));
    }
    public List<ClassificationDto> findByUnitIdAndCategoryCacheFallBack(String categorySaipCode, Long unitId,Throwable throwable) {
        log.error("Circuit breaker opened for findByCategoryIdCacheFallBack: {}", throwable.getMessage());
        return findByUnitIdAndCategoryCacheHelper(categorySaipCode, unitId);
    }
    ///////////////////////////////////////////////
    @Override
//    @Cacheable( cacheNames = "Classification_findBySaipCode",key = "{#categorySaipCode,#unitId}", unless = "#result == null")
//    @CircuitBreaker(name = "default", fallbackMethod = "findBySaipCodeCacheFallBack")
    public List<ClassificationDto> findBySaipCode(String categorySaipCode) {
        return findBySaipCodeCacheHelper(categorySaipCode);
    }

    private List<ClassificationDto> findBySaipCodeCacheHelper(String categorySaipCode) {
        Integer latestVersionId = lkClassificationVersionService.getLatestVersionIdBySaipCode(categorySaipCode);
        return classificationMapper.map(classificationRepository.findByCategorySaipCode(categorySaipCode, latestVersionId));
    }
    public List<ClassificationDto> findBySaipCodeCacheFallBack(String categorySaipCode,Throwable throwable) {
        log.error("Circuit breaker opened for findBySaipCodeCacheFallBack: {}", throwable.getMessage());

        return findBySaipCodeCacheHelper(categorySaipCode);
    }
    /////////////////////////////////////////////
    @Override
//    @Cacheable( cacheNames = "Classification_findByUnitIdIn",key = "#unitId", unless = "#result == null")
//    @CircuitBreaker(name = "default", fallbackMethod = "findByUnitIdInCacheFallBack")
    public List<ClassificationDto> findByUnitIdIn(List<Long> unitId) {
        return findByUnitIdInCacheHelper(unitId);
    }

    private List<ClassificationDto> findByUnitIdInCacheHelper(List<Long> unitId) {
        List<Classification> classification = classificationRepository.findByUnitIdIn(unitId);
        if (!classification.isEmpty()) {
            return classificationMapper.map(classification);
        } else {
            throw new BusinessException(Constants.ErrorKeys.CLASSIFICATION_NOT_FOUND, HttpStatus.NOT_FOUND, null);
        }
    }
    public List<ClassificationDto> findByUnitIdInCacheFallBack(List<Long> unitId,Throwable throwable) {
        log.error("Circuit breaker opened for findByUnitIdInCacheFallBack: {}", throwable.getMessage());

        return findByUnitIdInCacheHelper(unitId);
    }
    ///////////////////////////////////////////

    @Override
//    @Cacheable( cacheNames = "Classification_findByIdIn",key = "#classificationsIds", unless = "#result == null")
//    @CircuitBreaker(name = "default", fallbackMethod = "findByIdInCacheFallBack")
    public List<Classification> findByIdIn(List<Long> classificationsIds) {
        return findByIdInCacheHelper(classificationsIds);
    }

    private List<Classification> findByIdInCacheHelper(List<Long> classificationsIds) {
        List<Classification> classification = classificationRepository.findByIdIn(classificationsIds);
        if (!classification.isEmpty()) {
            return classification;
        } else {
            throw new BusinessException(Constants.ErrorKeys.CLASSIFICATION_NOT_FOUND, HttpStatus.NOT_FOUND, null);
        }
    }
    public List<Classification> findByIdInCacheFallBack(List<Long> classificationsIds,Throwable throwable) {
        log.error("Circuit breaker opened for findByIdInCacheFallBack: {}", throwable.getMessage());

        return findByIdInCacheHelper(classificationsIds);
    }

    /////////////////////////////////////////
    @Override
//    @Cacheable( cacheNames = "Classification_getAllClassifications",key = "{#saipCode,#versionId}", unless = "#result == null")
//    @CircuitBreaker(name = "default", fallbackMethod = "getAllClassificationsCacheFallBack")
    public List<ClassificationLightDto> getClassificationsByUnit(Long unitId) {
        return classificationRepository.getClassificationsByUnit(unitId).stream().map(classificationMapper::mapLight).collect(Collectors.toList());
    }
    @Override
//    @Cacheable( cacheNames = "Classification_getAllClassifications",key = "{#saipCode,#versionId}", unless = "#result == null")
//    @CircuitBreaker(name = "default", fallbackMethod = "getAllClassificationsCacheFallBack")
    public List<ClassificationLightDto> getAllClassifications(String saipCode, Integer versionId) {
        return getAllClassificationCacheHelper(saipCode, versionId);
    }

    private List<ClassificationLightDto> getAllClassificationCacheHelper(String saipCode, Integer versionId) {
        saipCode = Strings.isNullOrEmpty(saipCode) ? "TRADEMARK" : saipCode;
        if (versionId == null) {
            Integer latestVersionId = lkClassificationVersionService.getLatestVersionIdBySaipCode(saipCode);
            return classificationRepository.getAllClassifications(saipCode, latestVersionId).stream().map(classificationMapper::mapLight).collect(Collectors.toList());
        }
        return classificationRepository.getAllClassifications(saipCode, versionId).stream().map(classificationMapper::mapLight).collect(Collectors.toList());
    }
    public List<ClassificationLightDto> getAllClassificationsCacheFallBack(String saipCode, Integer versionId,Throwable throwable) {
        log.error("Circuit breaker opened for getAllClassificationsCacheFallBack: {}", throwable.getMessage());
        return getAllClassificationCacheHelper(saipCode, versionId);
    }

    ///////////////////////////////////////////////
    @Override
//    @Cacheable( cacheNames = "Classification_listApplicationClassification",key = "#id", unless = "#result == null")
//    @CircuitBreaker(name = "default", fallbackMethod = "listApplicationClassificationCacheFallBack")
    public List<ListApplicationClassificationDto> listApplicationClassification(Long id) {
        return listApplicationClassificationCacheHelper(id);
    }

    private List<ListApplicationClassificationDto> listApplicationClassificationCacheHelper(Long id) {
        
        List<ClassificationProjection> classificationProjections = classificationRepository.getClassificationList(id);
        
        if(classificationProjections == null || classificationProjections.isEmpty())
            return new ArrayList<>();
        
        List<ListApplicationClassificationDto> applicationClassificationDtos  =
                applicationClassificationMapper.mapClassificationProjectionList(classificationProjections);
        /*
        List<ClassificationProjection> classificationList = classificationRepository.getClassificationList(id);
        List<ListApplicationClassificationDto> applicationClassificationDtos = new ArrayList<>();

        for (ClassificationProjection classification : classificationList){
            ListApplicationClassificationDto applicationClassificationDto = new ListApplicationClassificationDto();
            applicationClassificationDto.setId(classification.getId());
            applicationClassificationDto.setVersion(lkClassificationVersionMapper.mapClassificationProjection(classification));
            applicationClassificationDto.setCode(classification.getCode());
            applicationClassificationDto.setNameAr(classification.getNameAr());
            applicationClassificationDto.setNameEn(classification.getNameEn());
            applicationClassificationDtos.add(applicationClassificationDto);
        }
         */
        applicationClassificationDtos.forEach(classification -> {
            classification.setSubClassifications(subClassificationMapper.mapList(classificationRepository.getSubClassificationList(id, classification.getId())));
        });
        return applicationClassificationDtos;
    }

    public List<ListApplicationClassificationDto> listApplicationClassificationCacheFallBack(Long id,Throwable throwable) {
        log.error("Circuit breaker opened for listApplicationClassificationCacheFallBack: {}", throwable.getMessage());
        return listApplicationClassificationCacheHelper(id);
    }
////////////////////////////////////////////////////

    @Override
//    @CacheEvict(cacheNames = {
//            "Classification_getAllClassifications","Classification_findByCategoryId",
//            "Classification_findByUnitIdAndCategory","Classification_findBySaipCode","Classification_findByUnitIdIn",
//            "Classification_findByIdIn","Classification_getAllClassifications","Classification_listApplicationClassification",
//            "Classification_getAllClassificationByVersionIdAndCategoryCode","Classification_hasClassificationsByVersionId"
//
//    }, allEntries = true)
//    @CircuitBreaker(name = "default", fallbackMethod = "addClassificationCacheFallBack")
    public Long addClassification(ClassificationDto classificationDto) {
        long ss =evictionAddClassification(classificationDto);
        return addClassificationCacheHelper(classificationDto);
    }

//    @CacheEvict(cacheNames = {
//            "Classification_getAllClassifications","Classification_findByCategoryId",
//            "Classification_findByUnitIdAndCategory","Classification_findBySaipCode","Classification_findByUnitIdIn",
//            "Classification_findByIdIn","Classification_getAllClassifications","Classification_listApplicationClassification",
//            "Classification_getAllClassificationByVersionIdAndCategoryCode","Classification_hasClassificationsByVersionId"
//
//    }, allEntries = true)
//    @CircuitBreaker(name = "default", fallbackMethod = "addClassificationCacheFallBack")
    public Long evictionAddClassification(ClassificationDto classificationDto){
        return 0L;
    }
    private Long addClassificationCacheHelper(ClassificationDto classificationDto) {
        validateClassificationArabicData(classificationDto);
        Classification entity = classificationMapper.unMap(classificationDto);
        getClassificationCategoryAndVersion(entity, classificationDto.getCategoryId());
        if (classificationDto.getUnitId() != null){
            LkClassificationUnit lkClassificationUnit = lkClassificationUnitService.findById(classificationDto.getUnitId());
            entity.setUnit(lkClassificationUnit);
        }
        entity.setEnabled(Boolean.TRUE);
        Classification savedClassification = classificationRepository.save(entity);
        return savedClassification.getId();
    }
    public Long addClassificationCacheFallBack(ClassificationDto classificationDto,Throwable throwable) {
        log.error("Circuit breaker opened for addClassificationCacheFallBack: {}", throwable.getMessage());
        return -1L;
    }

    /*
    *   public Long addClassification(ClassificationDto classificationDto) {
        evictionAddClassification(classificationDto);
        return addClassificationCacheHelper(classificationDto);
    }
        @CacheEvict(cacheNames = {
            "Classification_getAllClassifications","Classification_findByCategoryId",
            "Classification_findByUnitIdAndCategory","Classification_findBySaipCode","Classification_findByUnitIdIn",
            "Classification_findByIdIn","Classification_getAllClassifications","Classification_listApplicationClassification",
            "Classification_getAllClassificationByVersionIdAndCategoryCode","Classification_hasClassificationsByVersionId"

    }, allEntries = true)
          @CircuitBreaker(name = "default", fallbackMethod = "addClassificationCacheFallBack")

        public Long evictionAddClassification(ClassificationDto classificationDto){
        return 0L;
    }
    private Long addClassificationCacheHelper(ClassificationDto classificationDto) {
    return -1L;
    }
    public Long addClassificationCacheFallBack(ClassificationDto classificationDto,Throwable throwable) {
        log.error("Circuit breaker opened for addClassificationCacheFallBack: {}", throwable.getMessage());
        return addClassificationCacheHelper(classificationDto);
    }
*/
    ////////////////////////////////////////
    @Override
//    @CacheEvict(cacheNames = {
//            "Classification_getAllClassifications","Classification_findByCategoryId",
//             "Classification_findByUnitIdAndCategory","Classification_findBySaipCode","Classification_findByUnitIdIn",
//             "Classification_findByIdIn","Classification_getAllClassifications","Classification_listApplicationClassification",
//            "Classification_getAllClassificationByVersionIdAndCategoryCode","Classification_hasClassificationsByVersionId"
//
//    }, allEntries = true)
//    @CircuitBreaker(name = "default", fallbackMethod = "updateClassificationCacheFallBack")
    public Long updateClassification(ClassificationDto classificationDto) {
        return updateClassificationCacheHelper(classificationDto);
    }

    private Long updateClassificationCacheHelper(ClassificationDto classificationDto) {
        Optional<Classification> classificationOptional = classificationRepository.findById(classificationDto.getId());
        if (classificationOptional.isEmpty()) {
            throw new BusinessException(Constants.ErrorKeys.CLASSIFICATION_NOT_FOUND, HttpStatus.NOT_FOUND, null);
        }
        validateClassificationArabicData(classificationDto);
        Classification updatedClassification = classificationOptional.get();
        updatedClassification.setCode(classificationDto.getCode());
        updatedClassification.setNameAr(classificationDto.getNameAr());
        updatedClassification.setNameEn(classificationDto.getNameEn());
        updatedClassification.setDescriptionAr(classificationDto.getDescriptionAr());
        updatedClassification.setDescriptionEn(classificationDto.getDescriptionEn());

        //this is for old classifications to set category and version.
        if (updatedClassification.getCategory() == null)
            getClassificationCategoryAndVersion(updatedClassification, classificationDto.getCategoryId());

        if (classificationDto.getUnitId() != null){
            LkClassificationUnit lkClassificationUnit = lkClassificationUnitService.findById(classificationDto.getUnitId());
            updatedClassification.setUnit(lkClassificationUnit);
        }
        classificationRepository.save(updatedClassification);
        return updatedClassification.getId();
    }

    public Long updateClassificationCacheFallBack(ClassificationDto classificationDto,Throwable throwable) {
        log.error("Circuit breaker opened for updateClassificationCacheFallBack: {}", throwable.getMessage());
        return updateClassificationCacheHelper(classificationDto);
    }
    ///////////////////////////////////////////////////////
    private void getClassificationCategoryAndVersion(Classification classification, Long categoryId) {
        LkApplicationCategory category = lkApplicationCategoryService.findById(categoryId);
        classification.setCategory(category);

        LkClassificationVersion latestVersion = lkClassificationVersionRepository.getLatestVersionByCategoryId(categoryId);
        classification.setVersion(latestVersion);
    }

    private void validateClassificationArabicData(ClassificationDto dto) {
        if (Strings.isNullOrEmpty(dto.getCode()) || Strings.isNullOrEmpty(dto.getNameAr()) || Strings.isNullOrEmpty(dto.getDescriptionAr()))
            throw new BusinessException(Constants.ErrorKeys.INVALID_CLASSIFICATION_ARABIC_DATA, HttpStatus.NOT_FOUND, null);
    }

////////////////////////////////
    @Override
//    @Cacheable( cacheNames = "Classification_getAllClassificationByVersionIdAndCategoryCode",key = "{#saipCode,#versionId}", unless = "#result == null")
//    @CircuitBreaker(name = "default", fallbackMethod = "getAllClassificationByVersionIdAndCategoryCodeCacheFallBack")
    public List<Classification> getAllClassificationByVersionIdAndCategoryCode(String saipCode , Integer versionId) {
        return getAllClassificationByVersionIdAndCategoryCodeCacheHelper(saipCode, versionId);
    }

    private List<Classification> getAllClassificationByVersionIdAndCategoryCodeCacheHelper(String saipCode, Integer versionId) {
        return classificationRepository.getAllClassifications(saipCode, versionId);
    }
    public List<Classification> getAllClassificationByVersionIdAndCategoryCodeCacheFallBack(String saipCode , Integer versionId,Throwable throwable) {
        log.error("Circuit breaker opened for getAllClassificationByVersionIdAndCategoryCodeCacheFallBack: {}", throwable.getMessage());
        return getAllClassificationByVersionIdAndCategoryCodeCacheHelper(saipCode, versionId);
    }

    /////////////////////////
    @Override
//    @Cacheable( cacheNames = "Classification_hasClassificationsByVersionId",key = "#unitId", unless = "#result == null")
//    @CircuitBreaker(name = "default", fallbackMethod = "hasClassificationsByVersionIdCacheFallBack")
    public boolean hasClassificationsByVersionId(Long unitId){
        return hasClassificationsByVersionIdCacheHelper(unitId);
    }

    private boolean hasClassificationsByVersionIdCacheHelper(Long unitId) {
        return classificationRepository.hasClassificationsByVersionId(unitId);
    }
    public boolean hasClassificationsByVersionIdCacheFallBack(Long unitId,Throwable throwable){
        log.error("Circuit breaker opened for hasClassificationsByVersionIdCacheFallBack: {}", throwable.getMessage());
        return hasClassificationsByVersionIdCacheHelper(unitId);
    }

    @Override
    public void reedLocarnoSheet(MultipartFile file , Map<Long, LkClassificationUnit> classificationUnitMap,Long categoryId , Integer versionId){


        try (InputStream inputStream = file.getInputStream();
             XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
            // Read data from Sheet1 (sheet at index 0)
            XSSFSheet sheet2 = ((XSSFWorkbook) workbook).getSheetAt(2);

            XSSFRow row = null;

            List<Classification> classificationList = new ArrayList<>() ;
            Classification classification = null ;

            LkApplicationCategory category = new LkApplicationCategory(categoryId);
            LkClassificationVersion version = new LkClassificationVersion(versionId);

            for (int i = 1; i <= sheet2.getLastRowNum(); i++) {
                row = sheet2.getRow(i);
                String oldId = row.getCell(0).toString();
                String code = row.getCell(2).toString();
                String nameEn = row.getCell(3).toString();
                String nameAr = row.getCell(4).toString();
                String discription = row.getCell(11).toString();
                String lkClassificationUnitId = row.getCell(7).toString();
                Double lkClassificationUnitValue = Double.parseDouble(lkClassificationUnitId); // old Id 11001.0
                long lkClassificationUnitLong = lkClassificationUnitValue.longValue();
                if(classificationUnitMap.containsKey(lkClassificationUnitLong)) {
                    classification = new Classification();
                    classification.setCode(code);
                    Double doubleValue = Double.parseDouble(oldId); // old Id 11001.0
                    classification.setIdOld(doubleValue.longValue());
                    classification.setNameEn(nameEn);
                    classification.setNameAr(nameAr);
                    classification.setEnabled(true);
                    classification.setUnit(classificationUnitMap.get(lkClassificationUnitLong));
                    classification.setNiceVersion(0);
                    classification.setDescriptionAr(discription);
                    classification.setDescriptionEn(discription);

                    classification.setCategory(category);
                    classification.setVersion(version);

                    classificationList.add(classification);
                }
            }

            classificationList = classificationRepository.saveAll(classificationList);

            // Convert classificationUnitList to a Map with key as idOld and value as LkClassificationUnit object
            Map<Long, Classification> classificationMap = classificationList.stream()
                    .collect(Collectors.toMap(Classification::getIdOld, classification1 -> classification1));


            // Read data from Sheet1 (sheet at index 0)
            subClassificationService.reedLocarnoSheet(file, classificationMap);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
