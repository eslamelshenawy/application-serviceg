package gov.saip.applicationservice.common.mapper.supportService;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.supportService.SupportServiceReviewDto;
import gov.saip.applicationservice.common.model.ApplicationSupportServicesType;
import gov.saip.applicationservice.common.model.supportService.SupportServiceReview;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface SupportServiceReviewMapper extends BaseMapper<SupportServiceReview, SupportServiceReviewDto> {

    @AfterMapping
    default void afterMapDtoToEntity(SupportServiceReviewDto dto, @MappingTarget SupportServiceReview entity) {
        if (dto.getSupportServicesId() != null) {
            entity.setSupportServicesType(new ApplicationSupportServicesType(dto.getSupportServicesId()));
        }
    }

    @AfterMapping
    default void afterMapEntityToDto(@MappingTarget SupportServiceReviewDto dto, SupportServiceReview entity) {
        if (entity.getSupportServicesType() != null) {
            dto.setSupportServicesId(entity.getSupportServicesType().getId());
        }
    }


}