package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.common.dto.PatentPdfRequest;
import gov.saip.applicationservice.common.dto.reports.PatentReportProjection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper()
public interface PdfRequestMapper{
    PdfRequestMapper INSTANCE = Mappers.getMapper(PdfRequestMapper.class);

    @Mapping(source = "grantDate", target = "grantDate")
    @Mapping(source = "grantDateHijri", target = "grantDateHijri")
    @Mapping(source = "titleAr", target = "titleAr")
    @Mapping(source = "titleEn", target = "titleEn")
    @Mapping(source = "applicationNumber", target = "applicationNumber")
    @Mapping(source = "filingDate", target = "filingDate")
    @Mapping(source = "pctApplicationNo", target = "pctApplicationNo")
    @Mapping(source = "publishNo", target = "publishNo")
    @Mapping(source = "internationalPublicationDate", target = "internationalPublicationDate")
    @Mapping(source = "filingDateGr", target = "filingDateGr")
    PatentPdfRequest mapToPdfRequest(PatentReportProjection projection);
}