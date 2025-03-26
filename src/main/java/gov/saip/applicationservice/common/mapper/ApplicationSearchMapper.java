package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.ApplicationSearchDto;
import gov.saip.applicationservice.common.dto.BibliographicData;
import gov.saip.applicationservice.common.enums.installment.InstallmentType;
import gov.saip.applicationservice.common.model.ApplicationSearch;
import gov.saip.applicationservice.common.model.ApplicationSearchSimilars;
import org.mapstruct.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Mapper
public interface ApplicationSearchMapper extends BaseMapper<ApplicationSearch , ApplicationSearchDto> {


    @Override
    @Mapping(source = "classificationId" , target = "classification.id")
    @Mapping(source = "classificationNameAr" , target = "classification.nameAr")
    ApplicationSearch unMap(ApplicationSearchDto applicationSearchDto);

    @Override
    @Mapping(source = "classification.id" , target = "classificationId")
    @Mapping(source = "classification.nameAr" , target = "classificationNameAr")
    ApplicationSearchDto map(ApplicationSearch applicationSearch);



    @Mapping(source = "brandNameAr", target = "brandNameAr")
    @Mapping(source = "brandNameEn", target = "brandNameEn")
    @Mapping(source = "filingNumber", target = "filingNumber")
    @Mapping(source = "trademarkLastStatus", target = "status")
    @Mapping(source = "filingDate", target = "filingDate", qualifiedByName = "convertFilingDateFromStringToLocalDateTime")
    ApplicationSearchSimilars ipSearchDocumentToSimilar(BibliographicData bibliographicData);

    @AfterMapping
    default void setApplicationSearch(@MappingTarget ApplicationSearchSimilars similars, ApplicationSearch applicationSearch) {
        similars.setApplicationSearch(applicationSearch);
    }
    @Named("convertFilingDateFromStringToLocalDateTime")
    default LocalDateTime convertFilingDateFromStringToLocalDateTime(String filingDate) {
        return LocalDate.parse(filingDate).atStartOfDay();
    }



}
