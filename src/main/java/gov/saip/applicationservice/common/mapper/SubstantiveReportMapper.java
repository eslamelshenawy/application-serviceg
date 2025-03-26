package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.SubstantiveReportDto;
import gov.saip.applicationservice.common.enums.ApplicationUserRoleEnum;
import gov.saip.applicationservice.common.enums.ReportDecisionEnum;
import gov.saip.applicationservice.common.model.Document;
import gov.saip.applicationservice.common.model.SubstantiveExaminationReport;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mapper(uses = {DocumentMapper.class})
public interface SubstantiveReportMapper extends BaseMapper<SubstantiveExaminationReport, SubstantiveReportDto> {

    @Override
    @Mapping(source = "applicationInfo.id", target = "applicationId")
    @Mapping(source = "decision", target = "decision", qualifiedByName = "enumToString")
    SubstantiveReportDto map(SubstantiveExaminationReport applicationDatabase);

    @Mapping(source = "applicationId", target = "applicationInfo.id")
    @Mapping(source = "decision", target = "decision", qualifiedByName = "stringToEnum")
    SubstantiveExaminationReport unMap(SubstantiveReportDto applicationDatabaseDto);


    @Named("enumToString")
    default String enumToString(ReportDecisionEnum reportDecisionEnum) {
        if (reportDecisionEnum == null) {
            return null;
        }
        return reportDecisionEnum.name();
    }

    @Named("stringToEnum")
    default ReportDecisionEnum stringToEnum(String reportDecisionEnum) {
        if (reportDecisionEnum == null || reportDecisionEnum.isEmpty()) {
            return null;
        }

        return ReportDecisionEnum.valueOf(reportDecisionEnum);

    }


    @AfterMapping
    default void afterUnMapDtoToEntity(@MappingTarget SubstantiveExaminationReport t, SubstantiveReportDto dto) {
        if (dto.getApplicationId() == null) {
            t.setApplicationInfo(null);
        }
        if (dto.getDocumentIds() != null && !dto.getDocumentIds().isEmpty()) {
            List<Document> documents = dto.getDocumentIds()
                    .stream()
                    .map(Document::new)
                    .collect(Collectors.toList());
            t.setDocuments(documents);
        }
    }

    default String mapList(List<String> links) {
        return links != null ? String.join(",", links) : null;
    }

    default List<String> mapList(String links) {
        return links != null ? Stream.of(links.split(","))
                .collect(Collectors.toList()) : null;
    }

}
