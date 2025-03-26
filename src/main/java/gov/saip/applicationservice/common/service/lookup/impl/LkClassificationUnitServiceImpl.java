package gov.saip.applicationservice.common.service.lookup.impl;


import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.dto.ClassificationUnitLightDto;
import gov.saip.applicationservice.common.dto.LkClassificationUnitDto;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import gov.saip.applicationservice.common.mapper.LkClassificationUnitMapper;
import gov.saip.applicationservice.common.model.Classification;
import gov.saip.applicationservice.common.model.LkApplicationCategory;
import gov.saip.applicationservice.common.model.LkClassificationUnit;
import gov.saip.applicationservice.common.model.LkClassificationVersion;
import gov.saip.applicationservice.common.repository.lookup.LkClassificationUnitRepository;
import gov.saip.applicationservice.common.service.ClassificationService;
import gov.saip.applicationservice.common.service.LkClassificationVersionService;
import gov.saip.applicationservice.common.service.lookup.LkApplicationCategoryService;
import gov.saip.applicationservice.common.service.lookup.LkClassificationUnitService;
import gov.saip.applicationservice.exception.BusinessException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

import static gov.saip.applicationservice.util.Constants.ErrorKeys.CLASSIFICATION_UNIT_CANNOT_BE_DELETED;

@Service
@RequiredArgsConstructor
@Slf4j
public class LkClassificationUnitServiceImpl extends BaseServiceImpl<LkClassificationUnit, Long> implements LkClassificationUnitService {

    private final LkClassificationUnitRepository classificationUnitRepository;
    private final LkClassificationVersionService lkClassificationVersionService;
    private final LkClassificationUnitMapper classificationUnitMapper;
    private final LkApplicationCategoryService lkApplicationCategoryService;
    private final ClassificationService classificationService;
    @Override
    protected BaseRepository<LkClassificationUnit, Long> getRepository() {
        return classificationUnitRepository;
    }

    public List<LkClassificationUnitDto> getClassificationVersions(){
        return classificationUnitMapper.map(findAll());
    }
///////////////////////////////////
    @Override
//    @Cacheable(cacheNames = "LkClassificationUnit_categoryUnitsCache", key = "#categories", unless = "#result == null")
//    @CircuitBreaker(name = "default", fallbackMethod = "getCategoryUnitsCacheFallBack")
    public List<LkClassificationUnitDto> getCategoryUnits(List<String> categories) {
        return getCategoryUnitsCacheHelper(categories);
    }
    private List<LkClassificationUnitDto> getCategoryUnitsCacheHelper(List<String> categories) {
        return classificationUnitMapper.map(classificationUnitRepository.getClassificationCategoryUnits(categories));
    }
    public List<LkClassificationUnitDto> getCategoryUnitsCacheFallBack(List<String> categories, Throwable throwable) {
        log.error("Circuit breaker opened for getCategoryUnitsCacheFallBack: {}", throwable.getMessage());
        return this.getCategoryUnitsCacheHelper(categories);
    }

    //////////////////////////////////////////////////////
    @Override
//    @Cacheable(cacheNames = "LkClassificationUnit_filter", key = "{#page,#limit,#search,#categoryId}", unless = "#result == null")
//    @CircuitBreaker(name = "default", fallbackMethod = "LkClassificationUnitFilterCacheFallBack")
    public PaginationDto filter(int page, int limit, String search , Long categoryId, Integer versionId) {
        return filterCacheHelper(page, limit, search, categoryId,versionId);
    }
    @Override
//    @Cacheable(cacheNames = "LkClassificationUnit_filter", key = "{#page,#limit,#search,#categoryId}", unless = "#result == null")
//    @CircuitBreaker(name = "default", fallbackMethod = "LkClassificationUnitFilterCacheFallBack")
    public PaginationDto getIndustrialCategoryWithLastLocarnoVersion(int page, int limit, String search , Long categoryId) {
        Integer versionId = lkClassificationVersionService.getLatestVersionIdByCategoryId(2L);
        return filterCacheHelper(page, limit, search, categoryId,versionId);
    }


    private PaginationDto<Object> filterCacheHelper(int page, int limit, String search, Long categoryId, Integer versionId) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<LkClassificationUnit> units = classificationUnitRepository.filter(search, categoryId, versionId, pageable);
        return PaginationDto.builder()
                .content(classificationUnitMapper.map(units.getContent()))
                .totalPages(units.getTotalPages())
                .totalElements(units.getTotalElements())
                .build();
    }
    public PaginationDto LkClassificationUnitFilterCacheFallBack(int page, int limit, String search , Long categoryId , Integer versionId,Throwable throwable) {
        log.error("Circuit breaker opened for LkClassificationUnitFilterCacheFallBack: {}", throwable.getMessage());
        return this.filterCacheHelper(page, limit, search, categoryId, versionId);
    }
    ///////////////////////////////////////////////////////////////////////////
    @Override
//    @Cacheable(cacheNames = "LkClassificationUnit_getAll", unless = "#result == null")
//    @CircuitBreaker(name = "default", fallbackMethod = "LkClassificationGetAllCacheFallBack")
    public List<LkClassificationUnitDto> getAll() {
        return getAllCacheHelper();
    }


    private List<LkClassificationUnitDto> getAllCacheHelper() {
        return classificationUnitMapper.map(findAll());
    }
    public List<LkClassificationUnitDto> LkClassificationGetAllCacheFallBack(Throwable throwable) {
        log.error("Circuit breaker opened for LkClassificationGetAllCacheFallBack: {}", throwable.getMessage());
        return getAllCacheHelper();
    }

    //////////////////////////////
    @Override
//    @CacheEvict(cacheNames = {
//            "LkClassificationUnit_categoryUnitsCache","LkClassificationUnit_filter"
//            ,"LkClassificationUnit_getAll","LkClassificationUnit_getAllClassificationUnits",
//            "LkClassificationUnit_getAllClassificationUnitsWithClassificationIds"
//    }, allEntries = true)
//    @CircuitBreaker(name = "default", fallbackMethod = "LkClassificationUnitInsertCacheFallBack")
    public Long insert(LkClassificationUnitDto dto) {
        return insertCacheHelper(dto);
    }
    @Transactional
    protected Long insertCacheHelper(LkClassificationUnitDto dto) {
        LkClassificationUnit entity = classificationUnitMapper.unMap(dto);
        LkApplicationCategory category = lkApplicationCategoryService.getReferenceById(dto.getCategoryId());
        entity.setCategory(category);
        List<Long> classificationIdList = null ;
        if(category != null && !ApplicationCategoryEnum.INDUSTRIAL_DESIGN.toString().equals(category.getSaipCode())) {
            parseClassificationIds(dto.getClassificationIds());
            List<Classification> classifications = classificationService.findByIdIn(classificationIdList);
            entity.setClassifications(classifications);
        }
        LkClassificationUnit res = insert(entity);
        if(classificationIdList != null && !classificationIdList.isEmpty())
            updateClassifications(classificationIdList, entity);
        return res.getId();
    }

    public Long LkClassificationUnitInsertCacheFallBack(LkClassificationUnitDto dto,Throwable throwable) {
        log.error("Circuit breaker opened for LkClassificationUnitSoftDeleteByIdCacheFallBack: {}", throwable.getMessage());
        return insertCacheHelper(dto);
    }
    ////////////////////////////////////////////////////////////
    private List<Long> parseClassificationIds(String classificationIds) {
        if (classificationIds == null || classificationIds.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.stream(classificationIds.split(","))
                .map(String::trim)
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }










    private void updateClassifications(List<Long> ids, LkClassificationUnit unit){
        List<Classification> oldClassifications = unit.getClassifications();
        if (oldClassifications != null) {
            oldClassifications.forEach(classification -> classification.setUnit(null));
            classificationService.saveAll(oldClassifications);
        }
        List<Classification> newClassifications = new ArrayList<>();
        ids.forEach(id -> {
            Classification classification = classificationService.getReferenceById(id);
            classification.setNameEn(classification.getNameEn() != null ? classification.getNameEn() : classification.getNameAr());
            classification.setUnit(unit);
            newClassifications.add(classification);
            classificationService.update(classification);
        });

        if(!newClassifications.isEmpty())
            classificationService.saveAll(newClassifications);
    }

    private List<Classification> getClassifications(List<Long> ids){
       if(ids.isEmpty())
           return null;
        List<Classification> classifications = new ArrayList<>();
        ids.forEach(id -> {
            classifications.add(classificationService.getReferenceById(id));
        });
        return classifications;
    }

    @Override
    @CacheEvict(cacheNames = {
            "LkClassificationUnit_categoryUnitsCache","LkClassificationUnit_filter"
            ,"LkClassificationUnit_getAll","LkClassificationUnit_getAllClassificationUnits",
            "LkClassificationUnit_getAllClassificationUnitsWithClassificationIds"
    }, allEntries = true)
    @CircuitBreaker(name = "default", fallbackMethod = "LkClassificationUnitUpdateCacheFallBack")
    public Long update(LkClassificationUnitDto dto) {
        return UpdateCacheHelper(dto);
    }
    @Transactional
    protected Long UpdateCacheHelper(LkClassificationUnitDto dto) {
        LkClassificationUnit current = findById(dto.getId());
        current.setCategory(lkApplicationCategoryService.getReferenceById(dto.getCategoryId()));
        current.setNameEn(dto.getNameEn());
        current.setNameAr(dto.getNameAr());
        updateClassifications(parseClassificationIds(dto.getClassificationIds()) , current);
        update(current);
        return current.getId();
    }
    public Long LkClassificationUnitUpdateCacheFallBack(LkClassificationUnitDto dto,Throwable throwable) {
        log.error("Circuit breaker opened for LkClassificationUnitUpdateCacheFallBack: {}", throwable.getMessage());
        return UpdateCacheHelper(dto);
    }

    /////////////////////////
    @Override
//    @Cacheable(cacheNames = "LkClassificationUnit_getAllClassificationUnits", unless = "#result == null")
//    @CircuitBreaker(name = "default", fallbackMethod = "getAllClassificationUnitsCacheFallBack")
    public List<ClassificationUnitLightDto> getAllClassificationUnits() {
        return getAllClassificationUnitsCacheHelper();
    }

    private List<ClassificationUnitLightDto> getAllClassificationUnitsCacheHelper() {
        List<LkClassificationUnit> allActiveLkClassificationUnit = classificationUnitRepository.getAllClassificationUnits();
        return allActiveLkClassificationUnit.stream().map(classificationUnitMapper::mapLight).collect(Collectors.toList());
    }
    public List<ClassificationUnitLightDto> getAllClassificationUnitsCacheFallBack(Throwable throwable) {
        log.error("Circuit breaker opened for getAllClassificationUnitsCacheFallBack: {}", throwable.getMessage());
        return this.getAllClassificationUnitsCacheHelper();
    }

    ////////////////////////////////////////////////////////////
    @Override
//    @Cacheable(cacheNames = "LkClassificationUnit_getAllClassificationUnitsWithClassificationIds", key = "{#page,#limit}", unless = "#result == null")
//    @CircuitBreaker(name = "default", fallbackMethod = "getAllClassificationUnitsWithClassificationIdsCacheFallBack")
    public PaginationDto getAllClassificationUnitsWithClassificationIds(int page, int limit) {
        return getAllClassificationUnitsWithClassificationIdsCacheHelper(page, limit);
    }

    private PaginationDto<Object> getAllClassificationUnitsWithClassificationIdsCacheHelper(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<LkClassificationUnit> units = classificationUnitRepository.getAllClassificationUnitsWithIds(pageable);
        List<LkClassificationUnitDto> dtos = units.stream().map(classificationUnitMapper::map).collect(Collectors.toList());
        return PaginationDto.builder()
                .content(dtos)
                .totalElements(units.getTotalElements())
                .totalPages(units.getTotalPages())
                .build();
    }
    public PaginationDto getAllClassificationUnitsWithClassificationIdsCacheFallBack(int page, int limit,Throwable throwable) {
        log.error("Circuit breaker opened for getAllClassificationUnitsWithClassificationIdsCacheFallBack: {}", throwable.getMessage());
        return getAllClassificationUnitsWithClassificationIdsCacheHelper(page, limit);
    }


    /////////////////////////////////////////////////////////////
    @Override
    @CacheEvict(cacheNames = {
             "LkClassificationUnit_categoryUnitsCache","LkClassificationUnit_filter"
             ,"LkClassificationUnit_getAll","LkClassificationUnit_getAllClassificationUnits",
             "LkClassificationUnit_getAllClassificationUnitsWithClassificationIds"
    }, allEntries = true)
    @CircuitBreaker(name = "default", fallbackMethod = "LkClassificationUnitSoftDeleteByIdCacheFallBack")
    public void softDeleteById(Long id) {
        softDeleteByIdCacheHelper(id);
    }

    private void softDeleteByIdCacheHelper(Long id) {
        boolean hasClassification = classificationService.hasClassificationsByVersionId(id);
        if (hasClassification)
            throw new BusinessException(CLASSIFICATION_UNIT_CANNOT_BE_DELETED, HttpStatus.BAD_REQUEST);

        classificationUnitRepository.updateIsDeleted(id, 1);
    }
    public void LkClassificationUnitSoftDeleteByIdCacheFallBack(Long id ,Throwable throwable) {
        log.error("Circuit breaker opened for LkClassificationUnitSoftDeleteByIdCacheFallBack: {}", throwable.getMessage());
        softDeleteByIdCacheHelper(id);
    }

    public void reedLocarnoSheet(MultipartFile file,Long categoryId , Integer versionId){


        try (InputStream inputStream = file.getInputStream();
             XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
            // Read data from Sheet1 (sheet at index 0)
            XSSFSheet sheet1 = ((XSSFWorkbook) workbook).getSheetAt(1);

            XSSFRow row = null;

            List<LkClassificationUnit> classificationUnitList = new ArrayList<>() ;
            LkClassificationUnit lkClassificationUnit = null ;
            LkApplicationCategory category = new LkApplicationCategory(categoryId);
            LkClassificationVersion version = new LkClassificationVersion(versionId);

            for (int i = 1; i <= sheet1.getLastRowNum(); i++) {
                row = sheet1.getRow(i);
                    String oldId = row.getCell(0).toString();
                    String code = row.getCell(2).toString();
                    String nameEn = row.getCell(3).toString();
                    String nameAr = row.getCell(4).toString();
                lkClassificationUnit = new LkClassificationUnit();
                lkClassificationUnit.setVersion(version);
                lkClassificationUnit.setCode(code);
                Double doubleValue = Double.parseDouble(oldId); // old Id 11001.0
                lkClassificationUnit.setIdOld(doubleValue.longValue());
                lkClassificationUnit.setNameEn(nameEn);
                lkClassificationUnit.setNameAr(nameAr);
                lkClassificationUnit.setCategory(category);

                classificationUnitList.add(lkClassificationUnit);
            }

            classificationUnitList = classificationUnitRepository.saveAll(classificationUnitList);

            // Convert classificationUnitList to a Map with key as idOld and value as LkClassificationUnit object
            Map<Long, LkClassificationUnit> classificationUnitMap = classificationUnitList.stream()
                    .collect(Collectors.toMap(LkClassificationUnit::getIdOld, unit -> unit));


            // Read data from Sheet1 (sheet at index 1)
            classificationService.reedLocarnoSheet(file , classificationUnitMap,categoryId , versionId);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<LkClassificationUnitDto> findByVersion(Integer id) {
        List<LkClassificationUnit>  units = classificationUnitRepository.findByVersion(new LkClassificationVersion(id));
        return classificationUnitMapper.map(units);
    }

}
