package gov.saip.applicationservice.modules.plantvarieties.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.modules.plantvarieties.dto.SummaryPlantVarietyDto;
import gov.saip.applicationservice.modules.plantvarieties.model.PlantVarietyDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface SummaryPlantVarietyMapper extends BaseMapper<PlantVarietyDetails, SummaryPlantVarietyDto> {
    @Mapping(target = "id", source = "id", defaultExpression = "java(plantVariety.getId())")
    PlantVarietyDetails unMap (@MappingTarget PlantVarietyDetails plantVariety , SummaryPlantVarietyDto summaryPlantVarietyDto);
}
