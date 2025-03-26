package gov.saip.applicationservice.modules.plantvarieties.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.modules.plantvarieties.dto.TechnicalSurveyPlantVarietyDto;
import gov.saip.applicationservice.modules.plantvarieties.model.PlantVarietyDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface TechnicalSurveyPlantVarietyMapper extends BaseMapper<PlantVarietyDetails, TechnicalSurveyPlantVarietyDto> {
    @Mapping(target = "id", source = "id", defaultExpression = "java(plantVariety.getId())")
    PlantVarietyDetails unMap (@MappingTarget PlantVarietyDetails plantVariety , TechnicalSurveyPlantVarietyDto technicalSurveyPlantVarietyDto);
    TechnicalSurveyPlantVarietyDto map(PlantVarietyDetails plantVariety);
}
