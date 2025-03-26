package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.base.service.BaseLkServiceImpl;
import gov.saip.applicationservice.common.dto.LkCertificateTypeDto;
import gov.saip.applicationservice.common.mapper.LkCertificateTypeMapper;
import gov.saip.applicationservice.common.model.LkCertificateType;
import gov.saip.applicationservice.common.repository.LkCertificateTypeRepository;
import gov.saip.applicationservice.common.service.LkCertificateTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LkCertificateTypeServiceImpl extends BaseLkServiceImpl<LkCertificateType,Integer> implements LkCertificateTypeService {
    private final LkCertificateTypeRepository lkCertificateTypeRepository;
    private final LkCertificateTypeMapper mapper;


    @Override
    public List<LkCertificateTypeDto> findCertificateTypesByCategoryId(Long categoryId) {
        List<LkCertificateType> certificateTypes =  lkCertificateTypeRepository.findCertificateTypesByCategoryId(categoryId);
        return mapper.map(certificateTypes);
    }
    
    @Override
    public LkCertificateType findByCode(String code) {
        List<LkCertificateType> certificateTypes = lkCertificateTypeRepository.findByCertificateCode(code);
        return certificateTypes.isEmpty() ? null : certificateTypes.get(0);
    }
}
