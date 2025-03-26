package gov.saip.applicationservice.common.mapper.annualfees;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.annualfees.AnnualFeesRequestDto;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.annual_fees.AnnualFeesRequest;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Arrays;
import java.util.Objects;

@Mapper(uses = {LkPostRequestReasonsMapper.class, LkAnnualRequestYearsMapper.class})
public interface AnnualFeesMapper extends BaseMapper<AnnualFeesRequest, AnnualFeesRequestDto> {


    @AfterMapping
    default void afterMapDtoToEntity(@MappingTarget AnnualFeesRequest entity, AnnualFeesRequestDto dto) {
        if (Objects.nonNull(dto.getApplicationId())) {
            entity.setApplicationInfo(new ApplicationInfo(dto.getApplicationId()));
        }

        if (dto.getCostCodesList() != null && !dto.getCostCodesList().isEmpty()) {
            entity.setCostCodes(String.join(",", dto.getCostCodesList()));
        }
    }


    @AfterMapping
    default void afterMapEntityToDto(AnnualFeesRequest entity, @MappingTarget  AnnualFeesRequestDto dto) {
        if (entity.getCostCodes() != null) {
            dto.setCostCodesList(Arrays.asList(entity.getCostCodes().split(",")));
        }
    }

}
