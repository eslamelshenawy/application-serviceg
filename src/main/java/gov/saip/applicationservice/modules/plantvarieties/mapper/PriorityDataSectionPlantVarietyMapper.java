package gov.saip.applicationservice.modules.plantvarieties.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.modules.plantvarieties.dto.PriorityDataSectionPlantVarietyDto;
import gov.saip.applicationservice.modules.plantvarieties.model.PlantVarietyDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface PriorityDataSectionPlantVarietyMapper extends BaseMapper<PlantVarietyDetails, PriorityDataSectionPlantVarietyDto> {
    @Mapping(target = "id", source = "id", defaultExpression = "java(plantVariety.getId())")
    PlantVarietyDetails unMap (@MappingTarget PlantVarietyDetails plantVariety , PriorityDataSectionPlantVarietyDto priorityDataSectionPlantVarietyDto);
}
