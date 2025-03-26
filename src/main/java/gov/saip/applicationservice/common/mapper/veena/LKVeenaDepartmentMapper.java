package gov.saip.applicationservice.common.mapper.veena;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.lookup.LkAcceleratedTypeDto;
import gov.saip.applicationservice.common.dto.veena.LKVeenaAssistantDepartmentDto;
import gov.saip.applicationservice.common.dto.veena.LKVeenaDepartmentDto;
import gov.saip.applicationservice.common.model.LkAcceleratedType;
import gov.saip.applicationservice.common.model.veena.LKVeenaAssistantDepartment;
import gov.saip.applicationservice.common.model.veena.LKVeenaDepartment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper()
public interface LKVeenaDepartmentMapper extends BaseMapper<LKVeenaDepartment, LKVeenaDepartmentDto> {


    @Override
    @Mapping(source = "veenaClassification.nameAr" , target = "nameVeenaClassificationAr")
    @Mapping(source = "veenaClassification.nameEn" , target = "nameVeenaClassificationEn")
    LKVeenaDepartmentDto map(LKVeenaDepartment lkVeenaDepartment);


    List<LKVeenaDepartmentDto> mapRequestToEntity(List<LKVeenaDepartment> lkVeenaDepartments);





}
