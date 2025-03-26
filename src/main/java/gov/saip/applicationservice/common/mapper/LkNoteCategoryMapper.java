package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.LkAttributeDto;
import gov.saip.applicationservice.common.dto.LkNoteCategoryDto;
import gov.saip.applicationservice.common.model.LkAttribute;
import gov.saip.applicationservice.common.model.LkNoteCategory;
import org.mapstruct.Mapper;

@Mapper
public interface LkNoteCategoryMapper extends BaseMapper<LkNoteCategory, LkNoteCategoryDto> {
}
