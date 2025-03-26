package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.ListSubClassificationDto;
import gov.saip.applicationservice.common.dto.SubClassificationDto;
import gov.saip.applicationservice.common.model.Classification;
import gov.saip.applicationservice.common.model.SubClassification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface SubClassificationMapper extends BaseMapper<SubClassification, SubClassificationDto> {



    @Override
    @Mapping(source="classification.code" , target = "classificationCode")
    @Mapping(source="classification.nameEn" , target = "classificationNameEn")
    @Mapping(source="classification.nameAr" , target = "classificationNameAr")
    @Mapping(source="classification.descriptionEn" , target = "classificationDescriptionEn")
    @Mapping(source="classification.descriptionAr" , target = "classificationDescriptionAr")
    @Mapping(source="classification.notesEn" , target = "classificationNotesEn")
    @Mapping(source="classification.notesAr" , target = "classificationNotesAr")
    @Mapping(source="classification.enabled" , target = "classificationEnabled")
    @Mapping(source="classification.niceVersion" , target = "classificationNiceVersion")
    @Mapping(source="classification.id" , target = "classificationId")
    SubClassificationDto map(SubClassification subClassification);

    List<SubClassificationDto> mapRequestToEntity(List<Classification> classifications);

    @Override
    @Mapping(target = "classification.id " , source = "classificationId")
    SubClassification unMap(SubClassificationDto subClassificationDto);

    List<ListSubClassificationDto> mapList (List<SubClassification> list);

}
