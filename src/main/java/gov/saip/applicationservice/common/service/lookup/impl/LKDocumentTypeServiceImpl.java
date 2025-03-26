package gov.saip.applicationservice.common.service.lookup.impl;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.model.LkAcceleratedType;
import gov.saip.applicationservice.common.model.LkDocumentType;
import gov.saip.applicationservice.common.repository.lookup.LkDocumentTypeRepository;
import gov.saip.applicationservice.common.service.lookup.LKDocumentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LKDocumentTypeServiceImpl extends BaseServiceImpl<LkDocumentType, Long> implements LKDocumentTypeService {

    @Autowired
    LkDocumentTypeRepository lkDocumentTypeRepository;
    @Override
    protected BaseRepository<LkDocumentType, Long> getRepository() {
        return lkDocumentTypeRepository;
    }
    @Override
    public LkDocumentType getDocumentTypeByName(String name) {
        return lkDocumentTypeRepository.findByNameOrCode(name);
    }


    @Override
    public LkDocumentType update(LkDocumentType lkDocumentType){
        LkDocumentType currentLkDocumentType = getReferenceById(lkDocumentType.getId());
        currentLkDocumentType.setCategory(lkDocumentType.getCategory());
        currentLkDocumentType.setDescription(lkDocumentType.getDescription());
        currentLkDocumentType.setDocOrder(lkDocumentType.getDocOrder());
        currentLkDocumentType.setName(lkDocumentType.getName());
        currentLkDocumentType.setNameAr(lkDocumentType.getNameAr());
        return super.update(currentLkDocumentType);
    }
}
