package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.SupportingEvidenceDto;
import gov.saip.applicationservice.common.model.*;
import org.mapstruct.*;

import java.util.List;

@Mapper(uses = { SubstantiveExaminationReport.class })
public interface SupportingEvidenceMapper extends BaseMapper<SupportingEvidence, SupportingEvidenceDto> {

    List<SupportingEvidenceDto> unmapToList(List<SupportingEvidence> entityList);

    List<SupportingEvidence> mapToList(List<SupportingEvidenceDto> dtoList);

    @Mapping(source = "entity.substantiveExaminationReports.id", target = "substantiveExaminationReportsId")
    @Mapping(source = "entity.applicationInfo.id", target = "applicationInfoId")
    SupportingEvidenceDto map(SupportingEvidence entity);

    @AfterMapping
    default void afterMapDtoToEntity(@MappingTarget SupportingEvidence entity, SupportingEvidenceDto dto) {

        if (dto.getSubstantiveExaminationReportsId() != null) {
            entity.setSubstantiveExaminationReports(new SubstantiveExaminationReport(dto.getSubstantiveExaminationReportsId()));
        }
        if (dto.getApplicationInfoId() != null) {
            entity.setApplicationInfo(new ApplicationInfo(dto.getApplicationInfoId()));
        }
    }

}
