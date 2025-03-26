package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.AgentSubstitutionRequestDto;
import gov.saip.applicationservice.common.dto.agency.AgentSubstitutionServiceDto;
import gov.saip.applicationservice.common.enums.SupportServicePaymentStatus;
import gov.saip.applicationservice.common.model.AgentSubstitutionRequest;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.ApplicationSupportServicesType;
import gov.saip.applicationservice.common.model.LkApplicationStatus;
import io.jsonwebtoken.lang.Collections;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(uses = {DocumentMapper.class, ApplicationInfoMapper.class})
public interface AgentSubstitutionRequestMapper extends BaseMapper<AgentSubstitutionRequest, AgentSubstitutionRequestDto> {

    @AfterMapping
    default void afterMapDtoToEntity(@MappingTarget AgentSubstitutionRequest entity, AgentSubstitutionRequestDto dto) {

        entity.setPaymentStatus(SupportServicePaymentStatus.UNPAID);

        if (!Collections.isEmpty(dto.getApplicationIds())) {
            List<ApplicationInfo> applications = dto.getApplicationIds().stream().map(id -> new ApplicationInfo(id)).collect(Collectors.toList());
            entity.setApplications(applications);
        }
    }

    AgentSubstitutionServiceDto mapToAgentSubstitutionServiceDto(AgentSubstitutionRequest agentSubstitutionRequest);

    default String mapStatus(LkApplicationStatus status) {
        return status.getCode();
    }
}
