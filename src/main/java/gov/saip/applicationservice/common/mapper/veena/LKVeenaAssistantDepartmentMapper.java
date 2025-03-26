package gov.saip.applicationservice.common.mapper.veena;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.DocumentsTemplateDto;
import gov.saip.applicationservice.common.dto.lookup.LkAcceleratedTypeDto;
import gov.saip.applicationservice.common.dto.veena.LKVeenaAssistantDepartmentDto;
import gov.saip.applicationservice.common.model.DocumentsTemplate;
import gov.saip.applicationservice.common.model.LkAcceleratedType;
import gov.saip.applicationservice.common.model.veena.LKVeenaAssistantDepartment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper()
public interface LKVeenaAssistantDepartmentMapper extends BaseMapper<LKVeenaAssistantDepartment, LKVeenaAssistantDepartmentDto> {

    @Override
    @Mapping(source = "veenaDepartment.nameEn" , target = "nameVeenaDepartmentEn")
    @Mapping(source = "veenaDepartment.nameAr" , target = "nameVeenaDepartmentAr")
    @Mapping(source = "veenaDepartment.veenaClassification.nameEn" , target = "nameVeenaClassificationEn")
    @Mapping(source = "veenaDepartment.veenaClassification.nameAr" , target = "nameVeenaClassificationAr")
    @Mapping(source = "veenaDepartment.veenaClassification.code" , target = "nameVeenaClassificationCode")
    LKVeenaAssistantDepartmentDto map(LKVeenaAssistantDepartment lkVeenaAssistantDepartment);


}
