package gov.saip.applicationservice.common.service.veena.impl;

import gov.saip.applicationservice.base.service.BaseLkServiceImpl;
import gov.saip.applicationservice.common.dto.veena.LKVeenaDepartmentDto;
import gov.saip.applicationservice.common.mapper.veena.LKVeenaDepartmentMapper;
import gov.saip.applicationservice.common.model.veena.LKVeenaDepartment;
import gov.saip.applicationservice.common.repository.veena.LKVeenaDepartmentRepository;
import gov.saip.applicationservice.common.service.veena.LKVeenaDepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LKVeenaDepartmentServiceImpl extends BaseLkServiceImpl<LKVeenaDepartment, Long>
    implements  LKVeenaDepartmentService {

    private final LKVeenaDepartmentRepository lkVeenaDepartmentRepository;
    private final LKVeenaDepartmentMapper lkVeenaDepartmentMapper;

    @Override
    public List<LKVeenaDepartmentDto> getByVeenaClassificationId(Long classificationId) {
        List<LKVeenaDepartment>  departments = lkVeenaDepartmentRepository.getByVeenaClassificationId(classificationId);
        return lkVeenaDepartmentMapper.map(departments);
    }
}
