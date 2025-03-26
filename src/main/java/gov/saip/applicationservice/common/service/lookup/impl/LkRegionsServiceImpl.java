package gov.saip.applicationservice.common.service.lookup.impl;

import gov.saip.applicationservice.base.service.BaseLkServiceImpl;
import gov.saip.applicationservice.common.model.LkRegions;
import gov.saip.applicationservice.common.repository.lookup.LkRegionsRepository;
import gov.saip.applicationservice.common.service.lookup.LkRegionsService;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LkRegionsServiceImpl extends  BaseLkServiceImpl<LkRegions,Long> implements LkRegionsService{

    private final LkRegionsRepository lkRegionsRepository;

    @Override
    public LkRegions update(LkRegions entity) {
        Optional<LkRegions> entityOpt = lkRegionsRepository.findById(entity.getId());
        if (entityOpt.isEmpty()) {
            // validation message
            throw new BusinessException(Constants.ErrorKeys.APP_ID_DOESNT_EXIST);
        }

        LkRegions dbEntity = entityOpt.get();
        dbEntity.setNameAr(entity.getNameAr() == null ? dbEntity.getNameAr() : entity.getNameAr());
        dbEntity.setNameEn(entity.getNameEn() == null ? dbEntity.getNameEn() : entity.getNameEn());
        return super.update(dbEntity);
    }
}

