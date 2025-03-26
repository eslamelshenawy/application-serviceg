package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.ApplicationOfficeReportDto;
import gov.saip.applicationservice.common.mapper.lookup.LkExaminationOfficeMapper;
import gov.saip.applicationservice.common.model.ApplicationOfficeReport;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(uses = {LkExaminationOfficeMapper.class, DocumentMapper.class})
public interface ApplicationOfficeReportMapper extends BaseMapper<ApplicationOfficeReport, ApplicationOfficeReportDto> {

    @Override
    @Mapping(source = "applicationInfo.id", target = "applicationId")
    ApplicationOfficeReportDto map(ApplicationOfficeReport applicationDatabase);

    @Mapping(source = "applicationId", target = "applicationInfo.id")
    ApplicationOfficeReport unMap(ApplicationOfficeReportDto applicationDatabaseDto);


    @AfterMapping
    default void afterUnMapDtoToEntity(@MappingTarget ApplicationOfficeReport applicationDatabase, ApplicationOfficeReportDto applicationDatabaseDto) {
        if (applicationDatabaseDto.getApplicationId() == null) {
            applicationDatabase.setApplicationInfo(null);
        }

    }

}
