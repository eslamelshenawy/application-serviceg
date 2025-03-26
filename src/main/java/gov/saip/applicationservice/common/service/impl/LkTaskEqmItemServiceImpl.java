package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.base.service.BaseLkServiceImpl;
import gov.saip.applicationservice.common.dto.LkTaskEqmItemDto;
import gov.saip.applicationservice.common.dto.LkTaskEqmTypeDto;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.mapper.LkTaskEqmItemMapper;
import gov.saip.applicationservice.common.model.LkTaskEqmItem;
import gov.saip.applicationservice.common.model.LkTaskEqmType;
import gov.saip.applicationservice.common.repository.LkTaskEqmItemRepository;
import gov.saip.applicationservice.common.service.LkTaskEqmItemService;
import gov.saip.applicationservice.common.service.LkTaskEqmTypeService;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LkTaskEqmItemServiceImpl extends BaseLkServiceImpl<LkTaskEqmItem,Integer> implements LkTaskEqmItemService {

    private final LkTaskEqmItemRepository lkTaskEqmItemRepository;
    private LkTaskEqmTypeService lkTaskEqmTypeService;
    private final LkTaskEqmItemMapper lkTaskEqmItemMapper;

    @Autowired
    public void setLkTaskEqmTypeService(@Lazy LkTaskEqmTypeService lkTaskEqmTypeService) {
        this.lkTaskEqmTypeService = lkTaskEqmTypeService;
    }

    @Override
    public List<LkTaskEqmItem> findByTypeCode(String typeCode) {
        return lkTaskEqmItemRepository.getLkTaskEqmItemsByTypesCode(typeCode).orElseThrow(() -> new BusinessException(Constants.ErrorKeys.COMMON_DATA_NOT_FOUND));
    }

    @Override
    public LkTaskEqmItem insert(LkTaskEqmItem entity) {
        if (entity.getCode() == null) {
            entity.setCode(entity.getNameEn().replaceAll(" ", "_").toUpperCase());
        }
        assignTaskEqmTypes(entity);
        return super.insert(entity);
    }

    private void assignTaskEqmTypes(LkTaskEqmItem entity){
        String[] codes = entity.getTypes().get(0).getCode().split(",");
        List<LkTaskEqmType> entityTypes = new ArrayList<>();
        for(String code : codes){
            validateNewTypeCode(entity, code);
            LkTaskEqmType type = lkTaskEqmTypeService.findByCode(code);
            entityTypes.add(type);
        }
        entity.setTypes(entityTypes);
    }


    @Override
    public LkTaskEqmItem updateDto(LkTaskEqmItem entity) {
        LkTaskEqmItemDto dto = lkTaskEqmItemMapper.map(entity);
        LkTaskEqmItem entityDB =  findById(dto.getId());
        if(entityDB == null)
            throw new BusinessException(Constants.ErrorKeys.COMMON_CODE_IS_DUPLICATED, HttpStatus.BAD_REQUEST);

        entityDB= lkTaskEqmItemMapper.unMap(dto);
        entityDB.setCode(dto.getCode());
        entityDB.setNameAr(dto.getNameAr());
        entityDB.setNameEn(dto.getNameEn());
        entityDB.setShown(dto.getShown());
        entityDB.setRatingValueType(dto.getRatingValueType());
        List<LkTaskEqmType> entityTypes = new ArrayList<>();
        if(Objects.nonNull(dto.getTypes())){
            for (LkTaskEqmTypeDto typeDto : dto.getTypes()){
                String[] codes = typeDto.getCode().split(",");
                for(String code : codes){
                    LkTaskEqmType entityType = lkTaskEqmTypeService.findByCode(code);
                    entityTypes.add(entityType);
                }
            }
        }
        entityDB.setTypes(entityTypes);

        return super.update(entityDB);
    }

    @Override
    public PaginationDto getAllPaginatedLKTaskEqm(String search, int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<LkTaskEqmItem> lkTaskEqmItems = lkTaskEqmItemRepository.getTaskEqmBySearch(search , pageable);
        return PaginationDto.builder()
                .content(lkTaskEqmItemMapper.map(lkTaskEqmItems.getContent()))
                .totalPages(lkTaskEqmItems.getTotalPages())
                .totalElements(lkTaskEqmItems.getTotalElements())
                .build();
    }



    private void validateNewTypeCode(LkTaskEqmItem entity, String code) {

        boolean count =lkTaskEqmItemRepository.checkHaveCodeAndType(entity.getCode(),code);
        if(count)
            throw new BusinessException(Constants.ErrorKeys.COMMON_CODE_IS_DUPLICATED, HttpStatus.BAD_REQUEST, new String[]{entity.getCode()});
    }
}
