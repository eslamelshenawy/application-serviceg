package gov.saip.applicationservice.modules.ic.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.modules.ic.dto.IntegratedCircuitDto;
import gov.saip.applicationservice.modules.ic.model.IntegratedCircuit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface IntegratedCircuitMapper extends BaseMapper<IntegratedCircuit, IntegratedCircuitDto> {

    @Mapping(target = "id", source = "id", defaultExpression = "java(integratedCircuit.getId())")
    IntegratedCircuit unMap (@MappingTarget IntegratedCircuit integratedCircuit ,IntegratedCircuitDto integratedCircuitDto);

}
