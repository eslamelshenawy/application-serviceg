package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.PublicationSchedulingConfigCreateDto;
import gov.saip.applicationservice.common.dto.PublicationSchedulingConfigViewDto;
import gov.saip.applicationservice.common.dto.PublicationTimeCreateDto;
import gov.saip.applicationservice.common.model.PublicationSchedulingConfig;
import gov.saip.applicationservice.common.model.PublicationTime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = LkMapper.class)
public interface PublicationSchedulingConfigMapper extends BaseMapper<PublicationSchedulingConfig, PublicationSchedulingConfigViewDto> {
    @Mapping(target = "applicationCategory", source = "applicationCategorySaipCode")
    PublicationSchedulingConfig fromCreateDto(PublicationSchedulingConfigCreateDto dto);

    @Mapping(target = "dayOfWeek", source = "dayOfWeekCode")
    PublicationTime fromCreateDto(PublicationTimeCreateDto dto);
}