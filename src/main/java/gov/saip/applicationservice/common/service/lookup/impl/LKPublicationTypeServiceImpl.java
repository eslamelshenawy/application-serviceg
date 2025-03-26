package gov.saip.applicationservice.common.service.lookup.impl;

import gov.saip.applicationservice.base.service.BaseLkServiceImpl;
import gov.saip.applicationservice.common.model.LKPublicationType;
import gov.saip.applicationservice.common.repository.lookup.LKPublicationTypeRepository;
import gov.saip.applicationservice.common.service.lookup.LKPublicationTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LKPublicationTypeServiceImpl extends BaseLkServiceImpl<LKPublicationType , Integer>
        implements LKPublicationTypeService {


    @Autowired
    private LKPublicationTypeRepository lkPublicationTypeRepository;
    @Override
    public List<LKPublicationType> getPublicationTypes(Long id) {
        return lkPublicationTypeRepository.findByApplicationCategoryId(id);
    }
}
