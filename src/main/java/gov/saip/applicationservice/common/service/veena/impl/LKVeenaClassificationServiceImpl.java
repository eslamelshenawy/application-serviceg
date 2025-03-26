package gov.saip.applicationservice.common.service.veena.impl;

import gov.saip.applicationservice.base.service.BaseLkServiceImpl;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.veena.LKVeenaClassificationDto;
import gov.saip.applicationservice.common.mapper.veena.LKVeenaClassificationMapper;
import gov.saip.applicationservice.common.model.veena.LKVeenaClassification;
import gov.saip.applicationservice.common.repository.veena.LKVeenaClassificationRepository;
import gov.saip.applicationservice.common.service.veena.LKVeenaClassificationService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LKVeenaClassificationServiceImpl extends BaseLkServiceImpl<LKVeenaClassification, Long>
        implements LKVeenaClassificationService {
    private final LKVeenaClassificationRepository lkVeenaClassificationRepository;
    private final LKVeenaClassificationMapper lkVeenaClassificationMapper;

    @Override
    @Cacheable( cacheNames = "LKVeenaClassification_getVeenaClassificationsCodeByApplicationId",key = "#appId", unless = "#result == null")
    @CircuitBreaker(name = "default", fallbackMethod = "getVeenaClassificationsCodeByApplicationIdCacheFallBack")
    public List<LKVeenaClassificationDto> getVeenaClassificationsCodeByApplicationId(Long appId) {
        return getVeenaClassificationsCodeByApplicationIdCacheHelper(appId);
    }

    private List<LKVeenaClassificationDto> getVeenaClassificationsCodeByApplicationIdCacheHelper(Long appId) {
        return lkVeenaClassificationMapper.map(lkVeenaClassificationRepository.getVeenaClassificationsByApplicationId(appId));
    }
    private List<LKVeenaClassificationDto> getVeenaClassificationsCodeByApplicationIdCacheFallBack(Long appId,Throwable throwable) {
        log.error("Circuit breaker opened for getVeenaClassificationsCodeByApplicationIdCacheFallBack: {}", throwable.getMessage()) ;
        return this.getVeenaClassificationsCodeByApplicationIdCacheHelper(appId);
    }

    ////////////////////////////////////
    @Override
    @Cacheable( cacheNames = "LKVeenaClassification_getAllVeenaClassificationsBySearch",key = "{#page, #limit, #applicantCategoryId, #sortableColumn,#search}", unless = "#result == null")
    @CircuitBreaker(name = "default", fallbackMethod = "getAllVeenaClassificationsBySearchCacheFallBack")
    public PaginationDto getAllVeenaClassificationsBySearch(Integer page, Integer limit, String sortableColumn, String search, Long applicationCategoryId) {
        return getAllVeenaClassificationsBySearchCacheHelper(page, limit, sortableColumn, search);
    }

    private PaginationDto<Object> getAllVeenaClassificationsBySearchCacheHelper(Integer page, Integer limit, String sortableColumn, String search) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortableColumn));
        Page<LKVeenaClassificationDto> pageDto = lkVeenaClassificationRepository.getAllVeenaClassifications(search, pageable)
                .map(lkVeenaClassificationMapper::map);
        return PaginationDto
                .builder()
                .content(pageDto.getContent())
                .totalElements(pageDto.getTotalElements())
                .totalPages(pageDto.getTotalPages())
                .build();
    }
    private PaginationDto<Object> getAllVeenaClassificationsBySearchCacheFallBack(
            Integer page,
             Integer limit, String sortableColumn, String search,Throwable throwable) {
        log.error("Circuit breaker opened for getAllVeenaClassificationsBySearchCacheFallBack: {}", throwable.getMessage()) ;
       return getAllVeenaClassificationsBySearchCacheHelper(page,limit,sortableColumn,search);
    }
    ////////////////////////////////////
    @Override
    @CacheEvict(cacheNames = {
            "LKVeenaClassification_getAllVeenaClassificationsBySearch",
            "LKVeenaClassification_getVeenaClassificationsCodeByApplicationId"

    }, allEntries = true)
    @CircuitBreaker(name = "default", fallbackMethod = "LKVeenaClassificationUpdateCacheFallBack")

    public LKVeenaClassification update(LKVeenaClassification entity) {
        return updateCacheHelper(entity);
    }

    private LKVeenaClassification updateCacheHelper(LKVeenaClassification entity) {
        return super.update(entity);
    }
    public LKVeenaClassification LKVeenaClassificationUpdateCacheFallBack(LKVeenaClassification entity,Throwable throwable) {
        log.error("Circuit breaker opened for LKVeenaClassificationUpdateCacheFallBack: {}", throwable.getMessage()) ;
        return super.update(entity);
    }

    ////////////////////////////////////////////////////////////
    @Override
    @CacheEvict(cacheNames = {
            "LKVeenaClassification_getAllVeenaClassificationsBySearch",
            "LKVeenaClassification_getVeenaClassificationsCodeByApplicationId"
    }, allEntries = true)
    @CircuitBreaker(name = "default", fallbackMethod = "LKVeenaClassificationInsertCacheFallBack")

    public LKVeenaClassification insert(LKVeenaClassification entity) {
        return insertCacheHelper(entity);
    }

    private LKVeenaClassification insertCacheHelper(LKVeenaClassification entity) {
        return super.insert(entity);
    }
    public LKVeenaClassification LKVeenaClassificationInsertCacheFallBack(LKVeenaClassification entity) {
        return super.insert(entity);
    }
}
