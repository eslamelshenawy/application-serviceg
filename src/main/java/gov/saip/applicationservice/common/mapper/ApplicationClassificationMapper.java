package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.ApplicationClassificationDto;
import gov.saip.applicationservice.common.dto.ClassificationDto;
import gov.saip.applicationservice.common.dto.ClassificationProjection;
import gov.saip.applicationservice.common.dto.ListApplicationClassificationDto;
import gov.saip.applicationservice.common.dto.veena.ApplicationClassificationProjection;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.ApplicationNiceClassification;
import gov.saip.applicationservice.common.model.Classification;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.stream.Stream;

@Mapper(uses = {ClassificationMapper.class})
public interface ApplicationClassificationMapper extends BaseMapper<ApplicationInfo, ApplicationClassificationDto> {

    @Override
    @Mapping(target = "classificationUnitId", source = "classificationUnit.id")
    ApplicationClassificationDto map(ApplicationInfo applicationInfo);
    @Override
    @Mapping(source = "classificationUnitId", target = "classificationUnit.id")
    ApplicationInfo unMap(ApplicationClassificationDto applicationClassificationDto);

    ApplicationClassificationDto mapToDTO(ApplicationClassificationProjection projection);

    @Mapping(source = "versionId" , target = "version.id")
    @Mapping(source = "versionCode" , target = "version.code")
    @Mapping(source = "versionNameAr" , target = "version.nameAr")
    @Mapping(source = "versionNameEn" , target = "version.nameEn")
    @Mapping(source = "versionCategoryId" , target = "version.categoryId")
    ListApplicationClassificationDto mapClassificationProjection(ClassificationProjection classificationProjection);

    default List<ListApplicationClassificationDto> mapClassificationProjectionList(List<ClassificationProjection> classificationProjectionList){
        return classificationProjectionList.isEmpty()? null :
                classificationProjectionList.stream().map(this::mapClassificationProjection).toList();
    }

    @AfterMapping
    default void afterUnMapDtoToEntity(@MappingTarget  ApplicationInfo applicationInfo, ApplicationClassificationDto applicationClassificationDto) {
        if (applicationClassificationDto.getClassificationUnitId() == null)
            applicationInfo.setClassificationUnit(null);

        if (applicationClassificationDto.getClassificationIds() != null && !applicationClassificationDto.getClassificationIds().isEmpty() ) {
            List<ApplicationNiceClassification> applicationNiceClassificationStream = applicationClassificationDto.getClassificationIds()
                    .stream()
                    .map(Classification::new)
                    .map(classification ->
                            new ApplicationNiceClassification(applicationInfo.getId(), classification.getId(), null))
                    .toList();
            applicationInfo.setNiceClassifications(applicationNiceClassificationStream);
        }
    }

    @AfterMapping
    default void afterMapEntityToDto(ApplicationInfo entity, @MappingTarget  ApplicationClassificationDto dto) {
        if(entity.getNiceClassifications() != null && !entity.getNiceClassifications().isEmpty()) {
            List<Classification> classifications = entity.getNiceClassifications().stream().map(ApplicationNiceClassification::getClassification).toList();
            dto.setClassifications(this.mapClassificationToClassificationDto(classifications));
        }
    }

    @AfterMapping
    default void afterMapProjectionToDto(ApplicationClassificationProjection projection, @MappingTarget  ApplicationClassificationDto dto) {
        if(projection.getNiceClassifications() != null && !projection.getNiceClassifications().isEmpty()) {
            List<Classification> classifications = projection.getNiceClassifications().stream().map(ApplicationNiceClassification::getClassification).toList();
            dto.setClassifications(this.mapClassificationToClassificationDto(classifications));
        }
    }

    List<ClassificationDto> mapClassificationToClassificationDto(List<Classification> classification);

}
