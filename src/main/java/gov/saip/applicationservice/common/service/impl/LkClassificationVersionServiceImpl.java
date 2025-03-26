package gov.saip.applicationservice.common.service.impl;


import gov.saip.applicationservice.base.service.BaseLkServiceImpl;
import gov.saip.applicationservice.common.dto.LkClassificationVersionDto;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.mapper.lookup.LkClassificationVersionMapper;
import gov.saip.applicationservice.common.model.Classification;
import gov.saip.applicationservice.common.model.LkApplicationCategory;
import gov.saip.applicationservice.common.model.LkClassificationVersion;
import gov.saip.applicationservice.common.repository.LkClassificationVersionRepository;
import gov.saip.applicationservice.common.service.ClassificationService;
import gov.saip.applicationservice.common.service.LkClassificationVersionService;
import gov.saip.applicationservice.common.service.industrial.DesignSampleService;
import gov.saip.applicationservice.common.service.lookup.LkApplicationCategoryService;
import gov.saip.applicationservice.common.service.lookup.LkClassificationUnitService;
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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LkClassificationVersionServiceImpl extends BaseLkServiceImpl<LkClassificationVersion, Integer> implements LkClassificationVersionService {

    private final LkClassificationVersionRepository classificationVersionRepository;
    private final LkClassificationVersionMapper classificationVersionMapper;
    private final LkApplicationCategoryService lkApplicationCategoryService;
    private final LkClassificationVersionMapper lkClassificationVersionMapper;
    @Lazy
    @Autowired
    private DesignSampleService designSampleService;
    @Lazy
    @Autowired
    private LkClassificationUnitService lkClassificationUnitService;


    @Lazy
    @Autowired
    private ClassificationService classificationService;


    public List<LkClassificationVersionDto> getClassificationVersions(){
        return classificationVersionMapper.map(classificationVersionRepository.findAll());
    }
////////////////////////////////////////////////////
    @Override
//    @Cacheable(cacheNames = "LkClassificationVersion_getLatestVersionIdBySaipCode",key = "#saipCode", unless = "#result == null")
//    @CircuitBreaker(name = "default", fallbackMethod = "getLatestVersionIdBySaipCodeCacheFallBack")
    public Integer getLatestVersionIdBySaipCode(String saipCode) {
        return getLatestVersionIdBySaipCodeCacheHelper(saipCode);
    }

    private Integer getLatestVersionIdBySaipCodeCacheHelper(String saipCode) {
        Integer latestVersionId = classificationVersionRepository.getLatestVersionIdBySaipCode(saipCode);
        return latestVersionId;
    }
    public Integer getLatestVersionIdBySaipCodeCacheFallBack(String saipCode,Throwable throwable) {
        log.error("Circuit breaker opened for getLatestVersionIdBySaipCodeCacheFallBack: {}", throwable.getMessage());
        return  this.getLatestVersionIdBySaipCodeCacheHelper(saipCode);

    }
//////////////////////////////
    @Override
//    @Cacheable(cacheNames ="LkClassificationVersion_getLatestVersionIdByCategoryId" ,key = "#categoryId", unless = "#result == null")
//    @CircuitBreaker(name = "default", fallbackMethod = "getLatestVersionIdByCategoryIdCacheFallBack")
    public Integer getLatestVersionIdByCategoryId(Long categoryId) {
        return getLatestVersionIdByCategoryIdCacheHelper(categoryId);
    }

    private Integer getLatestVersionIdByCategoryIdCacheHelper(Long categoryId) {
        Integer latestVersionId = classificationVersionRepository.getLatestVersionIdByCategoryId(categoryId);
        return latestVersionId;
    }
    private Integer getLatestVersionIdByCategoryIdCacheFallBack(Long categoryId,Throwable throwable) {
        log.error("Circuit breaker opened for getLatestVersionIdByCategoryIdCacheFallBack: {}", throwable.getMessage());
        return this.getLatestVersionIdByCategoryIdCacheHelper(categoryId);

    }


    ///////////////////////////////////////////////////////////////////////
    @Override
    public PaginationDto findAllClassificationVersionsBySearch(Integer page, Integer limit, String sortableColumn, String search,Long categoryId) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortableColumn));
        Page<LkClassificationVersionDto> pageDto =  classificationVersionRepository.getClassificationVersionsBySearch(search, categoryId, pageable).map((classificationVersionMapper::map));
        return PaginationDto.builder()
                .content(pageDto.getContent())
                .totalElements(pageDto.getTotalElements())
                .totalPages(pageDto.getTotalPages())
                .build();
    }
/////////////////////////////////////////////////////////
    @Override
//    @Cacheable(cacheNames ="LkClassificationVersion_findAllClassificationVersionsByCategory" ,key = "#categoryId", unless = "#result == null")
//    @CircuitBreaker(name = "default", fallbackMethod = "findAllClassificationVersionsByCategoryCacheFallBack")
    public List<LkClassificationVersionDto> findAllClassificationVersionsByCategory(Long categoryId) {
        return findAllClassificationVersionsByCategoryCacheHelper(categoryId);
    }

    private List<LkClassificationVersionDto> findAllClassificationVersionsByCategoryCacheHelper(Long categoryId) {
        return classificationVersionMapper.map(classificationVersionRepository.findAllByCategoryId(categoryId));
    }
    public List<LkClassificationVersionDto> findAllClassificationVersionsByCategoryCacheFallBack(Long categoryId,Throwable throwable) {
        log.error("Circuit breaker opened for findAllClassificationVersionsByCategoryCacheFallBack: {}", throwable.getMessage());
        return this.findAllClassificationVersionsByCategoryCacheHelper(categoryId);
    }

    /////////////////////////////////////////////
    @Override
//    @Cacheable(cacheNames ="LkClassificationVersion_findLatestClassificationVersionsWithClassificationByCategory" ,key = "#categoryId", unless = "#result == null")
//    @CircuitBreaker(name = "default", fallbackMethod = "findLatestClassificationVersionsWithClassificationByCategoryCacheFallBack")
    public LkClassificationVersionDto findLatestClassificationVersionsWithClassificationByCategory(Long categoryId) {
        return findLatestClassificationVersionsWithClassificationByCategoryCacheHelper(categoryId);
    }

    private LkClassificationVersionDto findLatestClassificationVersionsWithClassificationByCategoryCacheHelper(Long categoryId) {
        return classificationVersionMapper.map(classificationVersionRepository.findLatestClassificationVersionsWithClassificationByCategory(categoryId));
    }
    public LkClassificationVersionDto findLatestClassificationVersionsWithClassificationByCategoryCacheFallBack(Long categoryId,Throwable throwable) {
        log.error("Circuit breaker opened for findLatestClassificationVersionsWithClassificationByCategoryCacheFallBack: {}", throwable.getMessage());
        return this.findLatestClassificationVersionsWithClassificationByCategoryCacheHelper(categoryId);
    }

    ////////////////////////////////////////////////////////////////////////
    @Override
//    @CacheEvict(cacheNames = {
//            "LkClassificationVersion_getLatestVersionIdBySaipCode",
//            "LkClassificationVersion_getLatestVersionIdByCategoryId",
//            "LkClassificationVersion_findAllClassificationVersionsByCategory",
//            "LkClassificationVersion_findLatestClassificationVersionsWithClassificationByCategory"
//    }, allEntries = true)
//    @CircuitBreaker(name = "default", fallbackMethod = "LkClassificationVersionUpdateCacheFallBack")
    public LkClassificationVersion update(LkClassificationVersion lkClassificationVersion){
        return updateCacheHelper(lkClassificationVersion);
    }

    private LkClassificationVersion updateCacheHelper(LkClassificationVersion lkClassificationVersion) {
        LkClassificationVersion currentLkClassificationVersion = getReferenceById(lkClassificationVersion.getId());
        LkClassificationVersionDto lkClassificationVersionDto = lkClassificationVersionMapper.map(lkClassificationVersion);
        LkApplicationCategory lkApplicationCategory = lkApplicationCategoryService.findById(lkClassificationVersionDto.getCategoryId());
        lkClassificationVersion.setCategory(lkApplicationCategory);
        currentLkClassificationVersion.setCode(lkClassificationVersion.getCode());
        currentLkClassificationVersion.setNameEn(lkClassificationVersion.getNameEn());
        currentLkClassificationVersion.setNameAr(lkClassificationVersion.getNameAr());
        return super.update(lkClassificationVersionMapper.unMap(lkClassificationVersionDto));
    }
    private LkClassificationVersion LkClassificationVersionUpdateCacheFallBack(LkClassificationVersion lkClassificationVersion ,Throwable throwable)
    {
        log.error("Circuit breaker opened for LkClassificationVersionUpdateCacheFallBack: {}", throwable.getMessage());
        return updateCacheHelper(lkClassificationVersion);
    }

    ////////////////////////////////////////////
    @Override
//    @CircuitBreaker(name = "default", fallbackMethod = "LkClassificationVersionInsertCacheFallBack")
    public LkClassificationVersion insert(LkClassificationVersion lkClassificationVersion){
        return insertCacheHelper(lkClassificationVersion);
    }

    private LkClassificationVersion insertCacheHelper(LkClassificationVersion lkClassificationVersion) {
        LkClassificationVersionDto lkClassificationVersionDto = lkClassificationVersionMapper.map(lkClassificationVersion);
        LkApplicationCategory lkApplicationCategory = lkApplicationCategoryService.findById(lkClassificationVersionDto.getCategoryId());
        if (lkApplicationCategory != null){
            lkClassificationVersion.setCategory(lkApplicationCategory);
        }
        return super.insert(lkClassificationVersionMapper.unMap(lkClassificationVersionDto));
    }
    private LkClassificationVersion LkClassificationVersionInsertCacheFallBack(LkClassificationVersion lkClassificationVersion,Throwable throwable) {
        log.error("Circuit breaker opened for LkClassificationVersionInsertCacheFallBack: {}", throwable.getMessage());
        return this.insertCacheHelper(lkClassificationVersion);
    }

    /////////////////////////////////////////////
    @Override
//    @CacheEvict(cacheNames = {
//            "LkClassificationVersion_getLatestVersionIdBySaipCode",
//            "LkClassificationVersion_getLatestVersionIdByCategoryId",
//            "LkClassificationVersion_findAllClassificationVersionsByCategory",
//            "LkClassificationVersion_findLatestClassificationVersionsWithClassificationByCategory"
//    }, allEntries = true)
//    @CircuitBreaker(name = "default", fallbackMethod = "LkClassificationVersionDeleteByIdCacheFallBack")
    public void deleteById(Integer id) {
        deleteByIdCacheHelper(id);
    }

    private void deleteByIdCacheHelper(Integer id) {
        LkClassificationVersion lkClassificationVersion = super.findById(id);
        List<Classification> classifications = null;
        if (lkClassificationVersion.getCategory().getSaipCode() != null){
            classifications = classificationService.getAllClassificationByVersionIdAndCategoryCode(lkClassificationVersion.getCategory().getSaipCode() , lkClassificationVersion.getId());
        }
        if (classifications != null || !classifications.isEmpty()){
            classifications.stream().forEach(c -> {
                c.setVersion(null);
                classificationService.update(c);
            });
        }
        super.deleteById(lkClassificationVersion.getId());
    }
    public void LkClassificationVersionDeleteByIdCacheFallBack(Integer id,Throwable throwable) {
        log.error("Circuit breaker opened for LkClassificationVersionDeleteByIdCacheFallBack: {}", throwable.getMessage());
        this.deleteByIdCacheHelper(id);
    }

    @Transactional
    public void reedLocarnoSheet(MultipartFile file, Long categoryId){

        // remove industrial design unit
        designSampleService.deleteWithOldLocarno();

        try (InputStream inputStream = file.getInputStream();
             XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {

            XSSFRow row = null;

            // Read data from Sheet1 (sheet at index 0)
            XSSFSheet sheet0 = ((XSSFWorkbook) workbook).getSheetAt(0);
            row = sheet0.getRow(1);
            String data = row.getCell(0).toString();

            LkClassificationVersion version = new LkClassificationVersion();

            version.setCode(data);
            version.setNameAr(data);
            version.setNameEn(data);

            version.setCategory(new LkApplicationCategory(categoryId));

            version = classificationVersionRepository.save(version);

            // Read data from Sheet1 (sheet at index 1)
            lkClassificationUnitService.reedLocarnoSheet(file , categoryId , version.getId());


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public Integer saveLocarno(LkClassificationVersionDto dto){
        // remove industrial design unit
        designSampleService.deleteWithOldLocarno();
        LkClassificationVersion entity = lkClassificationVersionMapper.unMap(dto);
        insertCacheHelper(entity);
        return entity.getId();
    }
    @Override
    @Transactional
    public void deleteLocarno(Integer id){
        classificationVersionRepository.updateIsDeleted(id);
    }


}
