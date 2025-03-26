package gov.saip.applicationservice.common.mapper.patent;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.patent.ProtectionElementsDTO;
import gov.saip.applicationservice.common.model.patent.ProtectionElements;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Objects;

@Mapper
public interface ProtectionElementMapper extends BaseMapper<ProtectionElements, ProtectionElementsDTO> {
    @AfterMapping
    default void afterMapEntityToDto(ProtectionElements protectionElements, @MappingTarget ProtectionElementsDTO dto){
        if(Objects.nonNull(protectionElements.getParentElement())){
            dto.setParentId(protectionElements.getParentElement().getId());
        }
        dto.setApplicationId(protectionElements.getApplication().getId());
    }
}
