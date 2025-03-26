package gov.saip.applicationservice.common.service.agency.impl;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.model.agency.LkClientType;
import gov.saip.applicationservice.common.repository.agency.LkClientTypeRepository;
import gov.saip.applicationservice.common.service.agency.LkClientTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LkClientTypeServiceImpl extends BaseServiceImpl<LkClientType,Integer> implements LkClientTypeService {

    private final LkClientTypeRepository lkClientTypeRepository;
    @Override
    protected BaseRepository<LkClientType, Integer> getRepository() {
        return lkClientTypeRepository;
    }
    @Override
    public List<LkClientType> findAll() {
        return lkClientTypeRepository.findAll();
    }
}
