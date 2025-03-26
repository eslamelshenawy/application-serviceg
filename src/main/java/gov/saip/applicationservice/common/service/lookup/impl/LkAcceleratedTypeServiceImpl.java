package gov.saip.applicationservice.common.service.lookup.impl;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.dto.lookup.LkAcceleratedTypeDto;
import gov.saip.applicationservice.common.mapper.LkAcceleratedTypeMapper;
import gov.saip.applicationservice.common.model.LkAcceleratedType;
import gov.saip.applicationservice.common.repository.lookup.LkAcceleratedTypeRepository;
import gov.saip.applicationservice.common.service.lookup.LkAcceleratedTypeService;
import gov.saip.applicationservice.common.service.lookup.LkApplicationCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LkAcceleratedTypeServiceImpl extends BaseServiceImpl<LkAcceleratedType, Long> implements LkAcceleratedTypeService {

    private final LkAcceleratedTypeRepository acceleratedTypeRepository;
    private final LkAcceleratedTypeMapper lkAcceleratedTypeMapper;
    private final LkApplicationCategoryService lkApplicationCategoryService;
    @Override
    protected BaseRepository<LkAcceleratedType, Long> getRepository() {
        return acceleratedTypeRepository;
    }
    public List<LkAcceleratedTypeDto> getAllAcceleratedTypes() {
        List<LkAcceleratedType> lkAcceleratedType = acceleratedTypeRepository.findAll();
        return lkAcceleratedTypeMapper.mapRequestToEntity(lkAcceleratedType);
    }

    @Override
    public List<LkAcceleratedTypeDto> filterByApplicationCategory(String appCategoryDescEn) {
        Long appCategoryId = lkApplicationCategoryService.getApplicationCategoryByDescEn(appCategoryDescEn).getId();
        List<LkAcceleratedType> lkAcceleratedType = acceleratedTypeRepository.findByApplicationCategory_Id(appCategoryId);
        return lkAcceleratedTypeMapper.mapRequestToEntity(lkAcceleratedType);
    }


    @Override
    public LkAcceleratedType update(LkAcceleratedType lkAcceleratedType){

       LkAcceleratedType currentLkAcceleratedType = getReferenceById(lkAcceleratedType.getId());
       currentLkAcceleratedType.setNameAr(lkAcceleratedType.getNameAr());
       currentLkAcceleratedType.setNameEn(lkAcceleratedType.getNameEn());
       currentLkAcceleratedType.setShow(lkAcceleratedType.getShow());
       return super.update(currentLkAcceleratedType);

    }

}
