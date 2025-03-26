package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.PetitionRecoveryRequestDto;
import gov.saip.applicationservice.common.enums.SupportServiceRequestStatusEnum;
import gov.saip.applicationservice.common.model.PetitionRecoveryRequest;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(uses = {DocumentMapper.class})
public interface PetitionRecoveryRequestMapper extends BaseMapper<PetitionRecoveryRequest, PetitionRecoveryRequestDto> {

    @Override
    @Mapping(source = "applicationInfo.id", target = "applicationId")
    PetitionRecoveryRequestDto map(PetitionRecoveryRequest applicationDatabase);

    @Mapping(source = "applicationId", target = "applicationInfo.id")
    PetitionRecoveryRequest unMap(PetitionRecoveryRequestDto applicationDatabaseDto);

    @AfterMapping
    default void afterMapEntityToDto(@MappingTarget PetitionRecoveryRequestDto dto, PetitionRecoveryRequest entity) {
        if (entity.getRequestStatus().getCode() != null && entity.getRequestStatus().getCode().equals(SupportServiceRequestStatusEnum.REOPENED.toString())) {
            dto.setHideConditionalRejectionButton(true);
        }
    }


    }
