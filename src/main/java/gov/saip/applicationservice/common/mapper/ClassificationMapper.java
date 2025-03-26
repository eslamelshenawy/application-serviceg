package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseLightMapper;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.ClassificationDto;
import gov.saip.applicationservice.common.dto.ClassificationLightDto;
import gov.saip.applicationservice.common.dto.ListApplicationClassificationDto;
import gov.saip.applicationservice.common.model.ApplicationNiceClassification;
import gov.saip.applicationservice.common.model.Classification;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface ClassificationMapper extends BaseMapper<Classification, ClassificationDto>,
                                        BaseLightMapper<Classification, ClassificationLightDto> {


    @Named("customClassificationMap")
    @Mapping(source = "category.applicationCategoryDescAr", target="applicationCategoryDescAr")
    @Mapping(source = "category.applicationCategoryDescEn", target="applicationCategoryDescEn")
    @Mapping(source = "unit.nameAr", target="nameUnitAr")
    @Mapping(source = "unit.nameEn", target="nameUnitEn")
    @Mapping(source = "version.nameAr", target="nameVersionAr")
    @Mapping(source = "version.nameEn", target="nameVersionEn")
    @Mapping(source = "unit.id", target="unitId")
    ClassificationDto mapClassificationToDto(Classification classification);


    @IterableMapping(qualifiedByName = "customClassificationMap")
    List<ClassificationDto> mapListOfClassificationsToListOfDtos(List<Classification> classifications);



    @Mapping(source = "application.id",                                     target="applicationId")
    @Mapping(source = "classification.category.id",                         target="categoryId")
    @Mapping(source = "classification.category.applicationCategoryDescAr",  target="applicationCategoryDescAr")
    @Mapping(source = "classification.category.applicationCategoryDescEn",  target="applicationCategoryDescEn")
    @Mapping(source = "classification.unit.id",                             target="unitId")
    @Mapping(source = "classification.unit.nameAr",                         target="nameUnitAr")
    @Mapping(source = "classification.unit.nameEn",                         target="nameUnitEn")
    @Mapping(source = "classification.version.id",                          target="niceVersion")
    @Mapping(source = "classification.version.nameAr",                      target="nameVersionAr")
    @Mapping(source = "classification.version.nameEn",                      target="nameVersionEn")
    @Mapping(source = "classification.nameAr",                              target="nameAr")
    @Mapping(source = "classification.nameEn",                              target="nameEn")
    @Mapping(source = "classification.descriptionEn",                       target="descriptionEn")
    @Mapping(source = "classification.descriptionAr",                       target="descriptionAr")
    @Mapping(source = "classification.code",                                target="code")
    @Mapping(source = "classification.id",                                  target="classificationId")
    ClassificationDto mapApplicationNiceClassificationtoClassificationDto(ApplicationNiceClassification applicationNiceClassification);


    @Mapping(source = "classification.id", target="id")
    List<ClassificationDto> mapApplicationNiceClassificationtoClassificationDto(List<ApplicationNiceClassification> applicationNiceClassification);

    List<ListApplicationClassificationDto> mapClassificationToListApplicationClassificationDto(List<Classification> classifications);
}
