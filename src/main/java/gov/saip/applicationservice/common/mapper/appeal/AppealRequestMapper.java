package gov.saip.applicationservice.common.mapper.appeal;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.appeal.AppealRequestDto;
import gov.saip.applicationservice.common.dto.appeal.AppealSupportServiceRequestDto;
import gov.saip.applicationservice.common.enums.SupportServicePaymentStatus;
import gov.saip.applicationservice.common.enums.appeal.AppealCheckerDecision;
import gov.saip.applicationservice.common.enums.appeal.AppealCommitteeDecision;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.Document;
import gov.saip.applicationservice.common.model.appeal.AppealRequest;
import io.jsonwebtoken.lang.Collections;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Mapper//(uses = {DocumentMapper.class})
public interface AppealRequestMapper extends BaseMapper<AppealRequest, AppealRequestDto> {


    @AfterMapping
    default void afterMapDtoToEntity(@MappingTarget AppealRequest entity, AppealRequestDto dto) {

        // map list of docs id to document objects
        if (!Collections.isEmpty(dto.getDocumentIds())) {
            List<Document> documents = dto.getDocumentIds().stream().map(id -> new Document(id)).collect(Collectors.toList());
            entity.setDocuments(documents);
        }

        entity.setPaymentStatus(SupportServicePaymentStatus.UNPAID);

        if (Objects.nonNull(dto.getApplicationId())) {
            entity.setApplicationInfo(new ApplicationInfo(dto.getApplicationId()));
        }
    }

    @Mapping(target = "applicationId", source = "applicationInfo.id")
    AppealSupportServiceRequestDto mapAppealSupportServiceRequestDto(AppealRequest entity);

    @AfterMapping
    default void afterMapDtoToEntity(@MappingTarget AppealSupportServiceRequestDto dto, AppealRequest entity) {
        if (AppealCommitteeDecision.REJECTED.equals(entity.getAppealCommitteeDecision())) {
            dto.setRejectionReason(entity.getAppealCommitteeDecisionComment());
        } else if (AppealCheckerDecision.REJECTED.equals(entity.getCheckerDecision())) {
            dto.setRejectionReason(entity.getCheckerFinalNotes());
        }
    }

}
