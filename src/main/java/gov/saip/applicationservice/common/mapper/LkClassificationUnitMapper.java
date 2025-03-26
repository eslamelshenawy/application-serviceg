package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseLightMapper;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.ClassificationUnitLightDto;
import gov.saip.applicationservice.common.dto.LkClassificationUnitDto;
import gov.saip.applicationservice.common.dto.consultation.ExaminerConsultationRequestDto;
import gov.saip.applicationservice.common.dto.lookup.LkAcceleratedTypeDto;
import gov.saip.applicationservice.common.dto.opposition.OppositionRequestDto;
import gov.saip.applicationservice.common.model.*;
import gov.saip.applicationservice.common.model.opposition.OppositionRequest;
import gov.saip.applicationservice.common.service.ClassificationService;
import io.jsonwebtoken.lang.Collections;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Mapper
public interface LkClassificationUnitMapper extends BaseMapper<LkClassificationUnit, LkClassificationUnitDto>,
        BaseLightMapper<LkClassificationUnit, ClassificationUnitLightDto> {


    @Override
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "version.id", target = "versionId")
    @Mapping(source = "category.applicationCategoryDescAr", target = "applicationCategoryDescAr")
    @Mapping(source = "category.applicationCategoryDescEn", target = "applicationCategoryDescEn")
    @Mapping(target = "classificationIds", source = "classifications", qualifiedByName = "classificationIdString")
    @Mapping(target = "classifications", source = "classifications")
    LkClassificationUnitDto map(LkClassificationUnit lkClassificationUnit);



    @Named("classificationIdString")
    default String toClassificationIdString(List<Classification> classifications) {
        if (classifications == null || classifications.isEmpty()) {
            return "";
        }
        return classifications.stream()
                .map(Classification::getId)
                .map(Object::toString)
                .collect(Collectors.joining(","));
    }





    @Override
    @Mapping( target = "category.id" , source = "categoryId")
    LkClassificationUnit unMap(LkClassificationUnitDto dto);


    List<LkClassificationUnitDto> mapRequestToEntity(List<LkClassificationUnit> lkClassificationUnits);

    @AfterMapping

    default void afterMapDtoToEntity(@MappingTarget LkClassificationUnit entity, LkClassificationUnitDto dto) {

        if (Objects.nonNull(dto.getVersionId())) {
            LkClassificationVersion lkClassificationVersion = new LkClassificationVersion(dto.getVersionId());
            entity.setVersion(lkClassificationVersion);

        }
    }
}
