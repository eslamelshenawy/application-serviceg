package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.dto.publication.*;
import gov.saip.applicationservice.common.enums.ApplicationCustomerType;
import gov.saip.applicationservice.common.mapper.lookup.LkClassificationVersionMapper;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.Document;
import org.mapstruct.*;

import java.time.LocalDateTime;
import java.time.chrono.HijrahChronology;
import java.time.chrono.HijrahDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(uses = {ClassificationMapper.class, LkClassificationVersionMapper.class})
public interface ApplicationInfoMapper extends BaseMapper<Document, DocumentDto> {

    @Mapping(source = "appId", target = "id")
    ApplicationInfo mapRequestToEntity(ApplicantsRequestDto applicantsRequestDto);

//    ApplicationInfo mapRequestToEntity(@MappingTarget ApplicationInfo foreignUserExisting, AppCommunicationUpdateRequestsDto dto);

    @Mapping(source = "appId", target = "id")
    ApplicationInfo mapRequestToEntity(@MappingTarget ApplicationInfo foreignUserExisting, ApplicantsRequestDto dto);

    //    ApplicationInfo mapRequestToEntity(ApplicationInfoDto dto);
//    ApplicationInfo mapRequestToEntity(ApplicationInfoDto dto);\

    @Mapping(source = "createdByCustomerType.nameEn", target = "createdByCustomerTypeNameEn")
    @Mapping(source = "createdByCustomerType.nameAr", target = "createdByCustomerTypeNameAr")
    ApplicationInfoDto mapEntityToRequest(ApplicationInfo entity);
    List<ApplicationInfoListDto> mapAppInfoToAppInfoListDto(List<ApplicationInfo> entity);
    ApplicationInfoSummaryDto mapApplicationTSummary(ApplicationInfoDto dto);
    AllApplicationsDtoLight mapEntityToAllApplicationsDto(AllApplicationsDto allApplicationsDto);
    List<AllApplicationsDtoLight> mapListOfEntityToAllApplicationsDto(List<AllApplicationsDto > allApplicationsDtos);

    ApplicationSubstantiveExaminationRetrieveDto mapEntityToSubstantiveExaminationRequest(ApplicationInfo entity);

    ApplicationInfo mapToExamination(@MappingTarget ApplicationInfo entity , ApplicationSubstantiveExaminationDto dto);

    ApplicationClassificationLightDto mapClassLight(ApplicationInfo entity);
    List<ApplicationClassificationLightDto> mapClassLight(List<ApplicationInfo> entity);

    @Mapping(source = "entity.applicationStatus.code", target = "code")
    @Mapping(source = "entity.applicationStatus.ipsStatusDescEn", target = "ipsStatusDescEn")
    @Mapping(source = "entity.applicationStatus.ipsStatusDescAr", target = "ipsStatusDescAr")
    ApplicationStatusDto mapStatus(ApplicationInfo entity);

    List<ApplicationStatusDto> mapStatus(List<ApplicationInfo> entity);
    List<ApplicationInfoTaskDto> mapApplicationInfoTaskDto(List<ApplicationInfo> entity);

    ApplicationInfoPaymentDto mapApplicationInfoPayment(ApplicationInfo applicationInfo);
    
    PublicationDto mapApplicationPublication(PublicationProjection applicationInfo);
    
    List<PublicationDto> mapApplicationPublication(List<PublicationProjection> publicationProjection);
    
    TrademarkPublicationVerificationBatchViewDto mapTrademarkPublicationBatchView(PublicationBatchViewProjection publicationBatchView);
    
    List<TrademarkPublicationVerificationBatchViewDto> mapTrademarkPublicationBatchView(List<PublicationBatchViewProjection>
                                                                             publicationsBatchView);
    
    TrademarkGazettePublicationListDto mapTrademarkGazettePublicationBatchView(PublicationBatchViewProjection publicationBatchView);
    
    List<TrademarkGazettePublicationListDto> mapTrademarkGazettePublicationBatchView(List<PublicationBatchViewProjection>
                                                                                      publicationsBatchView);
    
    PatentPublicationVerificationBatchViewDto mapPatentPublicationBatchView(PublicationBatchViewProjection publicationBatchView);
    
    List<PatentPublicationVerificationBatchViewDto> mapPatentPublicationBatchView(List<PublicationBatchViewProjection>
                                                                                    publicationsBatchView);
    @Mapping(source = "entity.applicationInfo.id", target = "applicationId")
    @Mapping(source = "entity.applicationInfo.applicationNumber", target = "applicationNumber")
    @Mapping(source = "entity.applicationInfo.titleAr", target = "applicationTitleAr")
    @Mapping(source = "entity.applicationInfo.titleEn", target = "applicationTitleEn")
    @Mapping(source = "entity.applicationInfo.filingDate", target = "depositDate")
    @Mapping(source = "entity.applicationInfo.applicationStatus", target = "applicationStatus")
    @Mapping(source = "entity.applicationInfo.applicationRelevantTypes", target = "applicationRelevantTypes")
    PatentPublicationGazetteBatchViewDto mapPatentPublicationIssueAppBatchView(PublicationBatchViewProjection
                                                                                       entity);
    
    List<PatentPublicationGazetteBatchViewDto> mapPatentPublicationIssueAppBatchView(List<PublicationBatchViewProjection>
                                                                                             publicationIssueApplications);
    
    @Mapping(source = "entity.applicationInfo.id", target = "applicationId")
    @Mapping(source = "entity.applicationInfo.applicationNumber", target = "applicationNumber")
    @Mapping(source = "entity.applicationInfo.titleAr", target = "applicationTitleAr")
    @Mapping(source = "entity.applicationInfo.titleEn", target = "applicationTitleEn")
    @Mapping(source = "entity.applicationInfo.filingDate", target = "depositDate")
    @Mapping(source = "entity.applicationInfo.applicationStatus", target = "applicationStatus")
    @Mapping(source = "entity.applicationInfo.applicationRelevantTypes", target = "applicationRelevantTypes")
    IndustrialPublicationGazetteBatchViewDto mapIndustrialPublicationIssueAppBatchView(PublicationBatchViewProjection
                                                                                       entity);
    
    List<IndustrialPublicationGazetteBatchViewDto> mapIndustrialPublicationIssueAppBatchView(List<PublicationBatchViewProjection>
                                                                                             publicationIssueApplications);
    IndustrialPublicationVerificationBatchViewDto mapIndustrialPublicationBatchView(PublicationBatchViewProjection publicationBatchView);
    
    List<IndustrialPublicationVerificationBatchViewDto> mapIndustrialPublicationBatchView(List<PublicationBatchViewProjection>
                                                                              publicationsBatchView);
    
    public default List<PartialApplicationInfoDto> mapToPartialApplicationInfoList(List<PartialApplicationInfoProjection> projections) {
        return projections.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public default PartialApplicationInfoDto mapToDto(PartialApplicationInfoProjection projection) {
        PartialApplicationInfoDto dto = new PartialApplicationInfoDto();

        dto.setId(projection.getId());
        dto.setTitleAr(projection.getTitleAr());
        dto.setTitleEn(projection.getTitleEn());
        dto.setApplicationNumber(projection.getApplicationNumber());
        dto.setRequestId(projection.getRequestId());

        return dto;
    }
    
    
    @Mapping(source = "id", target = "applicationId")
    @Mapping(source = "titleAr", target = "applicationTitleAr")
    @Mapping(source = "titleEn", target = "applicationTitleEn")
    @Mapping(source = "category.applicationCategoryDescAr", target = "categoryDescAr")
    @Mapping(source = "category.applicationCategoryDescEn", target = "categoryDescEn")
    @Mapping(source = "category.saipCode", target = "categoryCode")
    ApplicationInfoBaseDto mapAppInfoToBaseInfoDto(ApplicationInfo entity);
    
    
    List<ApplicationInfoBaseDto> mapAppInfoToBaseInfoDto(List<ApplicationInfo> entity);

    @AfterMapping
    default void afterMapDtoToEntity(ApplicantsRequestDto dto, @MappingTarget ApplicationInfo entity) {
        if (dto.getPltRegisteration() == null) {
            entity.setPltRegisteration(Boolean.FALSE);
        }
    }
    @AfterMapping
    default void afterMapEntityToDto(@MappingTarget ApplicationInfoListDto dto,  ApplicationInfo entity) {
        if (entity.getApplicationRelevantTypes() != null && !entity.getApplicationRelevantTypes().isEmpty()) {
            dto.setCustomerCode(entity.getApplicationRelevantTypes().get(0).getCustomerCode());
        }
    }

    @AfterMapping
    default void afterMapRequestToEntity(@MappingTarget ApplicationInfo applicationInfo, ApplicantsRequestDto applicantsRequestDto) {
        ApplicationCustomerType applicationCustomerType = applicantsRequestDto.isByHimself() ? ApplicationCustomerType.MAIN_OWNER: ApplicationCustomerType.AGENT;
        applicationInfo.setCreatedByCustomerType(applicationCustomerType);
    }
}
