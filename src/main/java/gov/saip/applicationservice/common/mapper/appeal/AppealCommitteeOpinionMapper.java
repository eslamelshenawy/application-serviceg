package gov.saip.applicationservice.common.mapper.appeal;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.DocumentDto;
import gov.saip.applicationservice.common.dto.appeal.AppealCommitteeOpinionDto;
import gov.saip.applicationservice.common.mapper.ApplicationInfoMapper;
import gov.saip.applicationservice.common.mapper.DocumentMapper;
import gov.saip.applicationservice.common.model.Document;
import gov.saip.applicationservice.common.model.appeal.AppealCommitteeOpinion;
import gov.saip.applicationservice.common.model.appeal.AppealRequest;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Objects;
@Mapper()
public interface AppealCommitteeOpinionMapper extends BaseMapper<AppealCommitteeOpinion, AppealCommitteeOpinionDto> {


    @AfterMapping
    default void afterMapDtoToEntity(@MappingTarget AppealCommitteeOpinion entity, AppealCommitteeOpinionDto dto) {


        if (Objects.nonNull(dto.getDocumentId())) {
            entity.setDocument(new Document(dto.getDocumentId()));
        }

        if (Objects.nonNull(dto.getAppealRequestId())) {
            entity.setAppealRequest(new AppealRequest(dto.getAppealRequestId()));
        }
    }

    @AfterMapping
    default void afterUnMapEntityToDto( AppealCommitteeOpinion entity, @MappingTarget AppealCommitteeOpinionDto dto) {


        if (Objects.nonNull(entity.getDocument())) {
            dto.setDocumentId(entity.getDocument().getId());

        }


    }

}
