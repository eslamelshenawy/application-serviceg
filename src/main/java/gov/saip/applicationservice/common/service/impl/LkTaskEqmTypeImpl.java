package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.base.service.BaseLkServiceImpl;
import gov.saip.applicationservice.common.model.LkTaskEqmItem;
import gov.saip.applicationservice.common.model.LkTaskEqmType;
import gov.saip.applicationservice.common.repository.LkTaskEqmTypeRepository;
import gov.saip.applicationservice.common.service.LkTaskEqmItemService;
import gov.saip.applicationservice.common.service.LkTaskEqmTypeService;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LkTaskEqmTypeImpl extends BaseLkServiceImpl<LkTaskEqmType,Integer> implements LkTaskEqmTypeService {

    private final LkTaskEqmTypeRepository taskEqmTypeRepository;
    private final LkTaskEqmItemService lkTaskEqmItemService;

    @Override
    public LkTaskEqmType insert(LkTaskEqmType entity) {
        validateNewTypeCode(entity);
        setSelectedItems(entity);
        return super.insert(entity);
    }

    private void validateNewTypeCode(LkTaskEqmType entity) {
        taskEqmTypeRepository.findByCode(entity.getCode()).ifPresent(
                (type) -> {
                    throw new BusinessException(Constants.ErrorKeys.COMMON_CODE_IS_DUPLICATED, HttpStatus.BAD_REQUEST, new String[]{entity.getCode()});
                }
        );
    }

    private void setSelectedItems(LkTaskEqmType entity) {
        if (entity.getItems() != null && !entity.getItems().isEmpty()) {
            List<LkTaskEqmItem> items = new ArrayList<>();
            entity.getItems().forEach(item -> {
                items.add(lkTaskEqmItemService.getReferenceById(item.getId()));
            });
            entity.setItems(items);
        } else {
            entity.setItems(null);
        }
    }

    @Override
    public LkTaskEqmType update(LkTaskEqmType entity) {
        LkTaskEqmType entityToUpdate = fillTypeEntityToUpdate(entity);
        setSelectedItems(entityToUpdate);
        return super.update(entityToUpdate);
    }

    private LkTaskEqmType fillTypeEntityToUpdate(LkTaskEqmType entity) {
        Optional<LkTaskEqmType> savedEntityOpt = taskEqmTypeRepository.findById(entity.getId());
        if (savedEntityOpt.isPresent()) {
            LkTaskEqmType savedEntity = savedEntityOpt.get();
            savedEntity.setNameAr(entity.getNameAr());
            savedEntity.setNameEn(entity.getNameEn());
            savedEntity.setItems(entity.getItems());
            savedEntity.setCode(entity.getCode());
            return savedEntity;
        }
        return entity;
    }
}
