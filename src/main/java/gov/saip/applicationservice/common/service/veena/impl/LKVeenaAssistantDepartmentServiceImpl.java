package gov.saip.applicationservice.common.service.veena.impl;

import gov.saip.applicationservice.base.service.BaseLkServiceImpl;
import gov.saip.applicationservice.common.dto.veena.LKVeenaAssistantDepartmentDto;
import gov.saip.applicationservice.common.mapper.veena.LKVeenaAssistantDepartmentMapper;
import gov.saip.applicationservice.common.model.veena.LKVeenaAssistantDepartment;
import gov.saip.applicationservice.common.repository.veena.LKVeenaAssistantDepartmentRepository;
import gov.saip.applicationservice.common.service.veena.LKVeenaAssistantDepartmentService;
import gov.saip.applicationservice.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static gov.saip.applicationservice.util.Constants.ErrorKeys.APPLICATION_NOT_MEET_SERVICE_CRITERIA;

@Service
@RequiredArgsConstructor
public class LKVeenaAssistantDepartmentServiceImpl extends BaseLkServiceImpl<LKVeenaAssistantDepartment, Long>
    implements LKVeenaAssistantDepartmentService {

    private final LKVeenaAssistantDepartmentRepository lkVeenaAssistantDepartmentRepository;
    private final LKVeenaAssistantDepartmentMapper lkVeenaAssistantDepartmentMapper;
    private final LKVeenaAssistantDepartmentRepository veenaAssistantDepartmentRepository;

    @Override
    public List<LKVeenaAssistantDepartmentDto> getByVeenaDepartmentId(Long departmentId) {
        List<LKVeenaAssistantDepartment> byVeenaDepartmentId = lkVeenaAssistantDepartmentRepository.getByVeenaDepartmentId(departmentId);
        return lkVeenaAssistantDepartmentMapper.map(byVeenaDepartmentId);
    }

    @Override
    public List<LKVeenaAssistantDepartmentDto> getVeenaAssistantDepartmentCodeByApplicationId(Long appId) {
        return lkVeenaAssistantDepartmentMapper.map(veenaAssistantDepartmentRepository.getVeenaAssistantDepartmentsByApplicationId(appId));
    }

    @Override
    public List<LKVeenaAssistantDepartmentDto> searchByVeenaAssistantDepartment(String search) {
        return lkVeenaAssistantDepartmentMapper.map(lkVeenaAssistantDepartmentRepository.searchVeenaAssistantDepartment(search));
    }
}
