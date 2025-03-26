package gov.saip.applicationservice.common.mapper.lookup;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.lookup.LkApplicantCategoryDto;
import gov.saip.applicationservice.common.dto.lookup.LkApplicationPriorityStatusDto;
import gov.saip.applicationservice.common.model.LkApplicantCategory;
import gov.saip.applicationservice.common.model.LkApplicationPriorityStatus;
import org.mapstruct.Mapper;

@Mapper
public interface LkApplicationPriorityStatusMapper extends BaseMapper<LkApplicationPriorityStatus, LkApplicationPriorityStatusDto> {

 }
