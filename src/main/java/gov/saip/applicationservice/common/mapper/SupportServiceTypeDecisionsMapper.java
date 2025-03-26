package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.SupportServicesTypeDecisionsDto;
import gov.saip.applicationservice.common.dto.SupportServicesTypeDecisionsResponse;
import gov.saip.applicationservice.common.enums.AssistiveSupportServiceSpecialistDecision;
import gov.saip.applicationservice.common.model.ApplicationSupportServicesType;
import gov.saip.applicationservice.common.model.LKSupportServiceRequestStatus;
import gov.saip.applicationservice.common.model.SupportServicesTypeDecisions;
import org.mapstruct.*;

import java.util.List;


@Mapper
public interface SupportServiceTypeDecisionsMapper extends BaseMapper<SupportServicesTypeDecisions ,
        SupportServicesTypeDecisionsDto> {


    SupportServicesTypeDecisionsResponse mapToSupportServicesTypeDecisionsResponse(SupportServicesTypeDecisions entity);

    @Override
    @Mapping(source = "applicationSupportServicesType.id", target = "supportServiceId")
    @Mapping(source = "decision", target = "decision", qualifiedByName = "decisionToString")
    SupportServicesTypeDecisionsDto map(SupportServicesTypeDecisions supportServicesTypeDecisions);
    @AfterMapping
    default void afterMapEntityToDto(@MappingTarget SupportServicesTypeDecisions entity, SupportServicesTypeDecisionsDto dto) {
        if (dto.getSupportServiceId() != null) {
            LKSupportServiceRequestStatus status = new LKSupportServiceRequestStatus();
            status.setCode(dto.getSupportServiceStatus());
            ApplicationSupportServicesType type = new ApplicationSupportServicesType(dto.getSupportServiceId());
            type.setRequestStatus(status);
            entity.setApplicationSupportServicesType(type);
        }
    }


    @Override
    @Mapping(source = "supportServiceId", target = "applicationSupportServicesType.id")
    @Mapping(source = "decision", target = "decision", qualifiedByName = "stringToDecision")
    SupportServicesTypeDecisions unMap(SupportServicesTypeDecisionsDto servicesTypeDecisionsDto);


    @Named("decisionToString")
    default String decisionToString(AssistiveSupportServiceSpecialistDecision decision) {
        if (decision == null) {
            return null;
        }
        return decision.name();
    }

    @Named("stringToDecision")
    default AssistiveSupportServiceSpecialistDecision stringToDecision(String decisionString) {
        if (decisionString == null) {
            return null;
        }
        return AssistiveSupportServiceSpecialistDecision.getDecisionByValue(decisionString);
    }

}
