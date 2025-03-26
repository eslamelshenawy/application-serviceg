package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.AgentSubstitutionRequestDto;
import gov.saip.applicationservice.common.dto.ProtectionExtendRequestDto;
import gov.saip.applicationservice.common.enums.SupportServicePaymentStatus;
import gov.saip.applicationservice.common.model.AgentSubstitutionRequest;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.ProtectionExtendRequest;
import io.jsonwebtoken.lang.Collections;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(uses = {DocumentMapper.class})
public interface ProtectionExtendRequestMapper extends BaseMapper<ProtectionExtendRequest, ProtectionExtendRequestDto>  {
    @Override
    @Mapping(source = "applicationInfo.id", target = "applicationId")
    @Mapping(source = "protectionExtendType", target = "type")
    ProtectionExtendRequestDto map(ProtectionExtendRequest applicationDatabase);

    @Mapping(source = "applicationId", target = "applicationInfo.id")
    @Mapping(source = "type", target = "protectionExtendType")
    ProtectionExtendRequest unMap(ProtectionExtendRequestDto applicationDatabaseDto);

    @AfterMapping
    default void afterMapDtoToEntity(@MappingTarget ProtectionExtendRequest entity, ProtectionExtendRequestDto dto) {
        if(entity.getSupportDocument() == null || entity.getSupportDocument().getId() == null || entity.getSupportDocument().getId() == 0) {
            entity.setSupportDocument(null);
        }

        if(entity.getPoaDocument() == null || entity.getPoaDocument().getId() == null || entity.getPoaDocument().getId() == 0) {
            entity.setPoaDocument(null);
        }

        if(entity.getWaiveDocument() == null || entity.getWaiveDocument().getId() == null || entity.getWaiveDocument().getId() == 0) {
            entity.setWaiveDocument(null);
        }

    }

}
