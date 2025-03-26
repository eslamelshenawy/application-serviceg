package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.LicenceRequestDto;
import gov.saip.applicationservice.common.dto.LkRegionsDto;
import gov.saip.applicationservice.common.model.Document;
import gov.saip.applicationservice.common.model.LicenceRequest;
import gov.saip.applicationservice.common.model.LkRegions;
import gov.saip.applicationservice.util.CustomerServiceMapperUtil;
import io.jsonwebtoken.lang.Collections;
import org.mapstruct.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.IsoChronology;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = { CustomerServiceMapperUtil.class })
public interface LicenceRequestMapper extends BaseMapper<LicenceRequest, LicenceRequestDto> {

    @Override
    @Mapping(source = "applicationInfo.id", target = "applicationId")
    @Mapping(source = "contractDocument", target = "supportDocument")
    @Mapping(source = "fromDate", target = "fromDate", qualifiedByName = "dateToHijri")
    @Mapping(source = "toDate", target = "toDate", qualifiedByName = "dateToHijri")
    @Mapping(source = "customerId", target = "customer", qualifiedByName = "customerInfoFromCustomerId")
    LicenceRequestDto map(LicenceRequest applicationDatabase);

    @Mapping(source = "applicationId", target = "applicationInfo.id")
    @Mapping(source = "supportDocument", target = "contractDocument")
    @Mapping(source = "fromDate", target = "fromDate", qualifiedByName = "dateFromHijri")
    @Mapping(source = "toDate", target = "toDate", qualifiedByName = "dateFromHijri")
    @Mapping(source = "customerCode", target = "customerId", qualifiedByName = "customerIdFromCode")
    LicenceRequest unMap(LicenceRequestDto applicationDatabaseDto);

    @Named("dateFromHijri")
    default LocalDateTime convertDateFromHijriToGregorian(LocalDate localDate) {
        if(localDate == null)
            return null;
        return IsoChronology.INSTANCE.date(localDate).atStartOfDay();
    }

    @Named("dateToHijri")
    default LocalDate convertDateFromGregorianToHijri(LocalDateTime gregorianDate) {
        if(gregorianDate == null)
            return null;
        return gregorianDate.toLocalDate();
    }
    @AfterMapping
    default void afterMapEntityToDto(LicenceRequest entity, @MappingTarget LicenceRequestDto dto) {
        if (!Collections.isEmpty(entity.getRegions())) {
            List<LkRegionsDto> lkRegionsDtos = entity.getRegions().stream()
                    .map(region -> {
                        LkRegionsDto regionDto = new LkRegionsDto();
                        regionDto.setId(region.getId());
                        regionDto.setCode(region.getCode());
                        regionDto.setNameAr(region.getNameAr());
                        regionDto.setNameEn(region.getNameEn());
                        return regionDto;
                    })
                    .collect(Collectors.toList());
            dto.setRegions(lkRegionsDtos);
        }

        if (!Collections.isEmpty(entity.getDocuments())) {
            List<Long> documentIds = entity.getDocuments().stream()
                    .map(Document::getId)
                    .collect(Collectors.toList());
            dto.setDocumentIds(documentIds);
        }
    }

    @AfterMapping
    default void afterMapDtoToEntity(LicenceRequestDto dto, @MappingTarget LicenceRequest entity) {
        if (dto.getRegions() != null && !dto.getRegions().isEmpty()) {
            List<LkRegions> lkRegions = dto.getRegions().stream()
                    .map(regionDto -> {
                        LkRegions lkRegion = new LkRegions();
                        lkRegion.setId(regionDto.getId());
                        lkRegion.setCode(regionDto.getCode());
                        lkRegion.setNameAr(regionDto.getNameAr());
                        lkRegion.setNameEn(regionDto.getNameEn());
                        return lkRegion;
                    })
                    .collect(Collectors.toList());
            entity.setRegions(lkRegions);
        }

        if (dto.getDocumentIds() != null && !dto.getDocumentIds().isEmpty()) {
            List<Document> documents = dto.getDocumentIds().stream()
                    .map(id -> {
                        Document doc = new Document();
                        doc.setId(id);
                        return doc;
                    })
                    .collect(Collectors.toList());
            entity.setDocuments(documents);
        }
    }
}
