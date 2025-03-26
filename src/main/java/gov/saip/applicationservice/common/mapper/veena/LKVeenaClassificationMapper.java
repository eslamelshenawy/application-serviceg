package gov.saip.applicationservice.common.mapper.veena;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.veena.LKVeenaClassificationDto;
import gov.saip.applicationservice.common.model.veena.LKVeenaClassification;
import org.mapstruct.Mapper;

@Mapper()
public interface LKVeenaClassificationMapper extends BaseMapper<LKVeenaClassification, LKVeenaClassificationDto> {
}
