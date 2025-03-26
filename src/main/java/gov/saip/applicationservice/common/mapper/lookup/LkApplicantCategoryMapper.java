package gov.saip.applicationservice.common.mapper.lookup;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.lookup.LkApplicantCategoryDto;
import gov.saip.applicationservice.common.model.LkApplicantCategory;
import org.mapstruct.Mapper;

@Mapper
public interface LkApplicantCategoryMapper extends BaseMapper<LkApplicantCategory, LkApplicantCategoryDto> {
 }
