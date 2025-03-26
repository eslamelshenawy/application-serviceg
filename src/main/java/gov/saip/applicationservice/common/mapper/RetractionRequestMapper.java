package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.PetitionRecoveryRequestDto;
import gov.saip.applicationservice.common.dto.RetractionRequestDto;
import gov.saip.applicationservice.common.model.PetitionRecoveryRequest;
import gov.saip.applicationservice.common.model.RetractionRequest;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(uses = {DocumentMapper.class})
public interface RetractionRequestMapper extends BaseMapper<RetractionRequest, RetractionRequestDto> {

    @Override
    @Mapping(source = "applicationInfo.id", target = "applicationId")
    RetractionRequestDto map(RetractionRequest applicationDatabase);

    @Mapping(source = "applicationId", target = "applicationInfo.id")
    RetractionRequest unMap(RetractionRequestDto applicationDatabaseDto);

}
