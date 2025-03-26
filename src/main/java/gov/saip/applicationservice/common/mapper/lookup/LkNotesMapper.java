package gov.saip.applicationservice.common.mapper.lookup;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.lookup.LkNotesDto;
import gov.saip.applicationservice.common.enums.NotesStepEnum;
import gov.saip.applicationservice.common.enums.NotesTypeEnum;
import gov.saip.applicationservice.common.mapper.LkAttributeMapper;
import gov.saip.applicationservice.common.model.ApplicationSectionNotes;
import gov.saip.applicationservice.common.model.LkAttribute;
import gov.saip.applicationservice.common.model.LkNotes;
import gov.saip.applicationservice.common.service.LkAttributeService;
import org.mapstruct.*;

import java.util.List;

@Mapper(uses = {LkAttributeMapper.class,LkNotesMapper.class})
public interface LkNotesMapper extends BaseMapper<LkNotes, LkNotesDto> {



    @Override
    @Mapping(source = "category.saipCode" , target = "saipCode")
    @Mapping(source = "section.code" , target = "sectionCode")
    @Mapping(source = "section.nameAr" , target = "sectionNameAr")
    @Mapping(source = "section.nameEn" , target = "sectionNameEn")
    @Mapping(source = "notesTypeEnum", target = "notesTypeEnum", qualifiedByName = "notesTypeEnumToString")
    @Mapping(source = "notesStep", target = "notesStep", qualifiedByName = "notesStepToString")
    @Mapping(source = "noteCategory.code" , target = "noteCategoryCode")
    @Mapping(source = "noteCategory.nameAr" , target = "noteCategoryAr")
    @Mapping(source = "noteCategory.nameEn" , target = "noteCategoryEn")
    @Mapping(source = "attribute.code" , target = "attributeCode")
    LkNotesDto map(LkNotes lkNotes);

    @Override
    @Mapping(source = "saipCode" , target = "category.saipCode")
    @Mapping(source = "sectionCode" , target = "section.code")
    @Mapping(source = "notesTypeEnum", target = "notesTypeEnum", qualifiedByName = "StringToNotesTypeEnum")
    @Mapping(source = "notesStep", target = "notesStep", qualifiedByName = "StringToNotesStep")
    @Mapping(source = "attributeCode" , target = "attribute.code")
    @Mapping(source = "noteCategoryCode" , target = "noteCategory.code")
    @Mapping(source = "noteCategoryAr" , target = "noteCategory.nameAr")
    @Mapping(source = "noteCategoryEn" , target = "noteCategory.nameEn")
    LkNotes unMap(LkNotesDto lkNotesDto);








    List<LkNotesDto> map(List<LkNotes> lkNotes);


    @Named("notesTypeEnumToString")
    default String notesTypeToString(NotesTypeEnum notesTypeEnum) {
        if (notesTypeEnum == null) {
            return null;
        }
        return notesTypeEnum.name();
    }


    @Named("StringToNotesStep")
    default NotesStepEnum stringToNotesStep(String notesStep){
        if (notesStep == null){
            return null;
        }
        return NotesStepEnum.valueOf(notesStep);
    }

    @Named("notesStepToString")
    default String notesStepToString(NotesStepEnum notesStepEnum) {
        if (notesStepEnum == null) {
            return null;
        }
        return notesStepEnum.name();
    }


    @Named("StringToNotesTypeEnum")
    default NotesTypeEnum stringToNotesTypeEnum(String notesType){
        if (notesType == null){
            return null;
        }
        return NotesTypeEnum.valueOf(notesType);
    }
    @Mapping(source = "entity.description" , target = "descriptionEn")
    @Mapping(source = "entity.description" , target = "descriptionAr")
    @Mapping(source = "entity.note.id" , target = "id")
    @Mapping(source = "entity.note.noteCategory.code" , target = "noteCategoryCode")
    @Mapping(source = "entity.note.noteCategory.nameAr" , target = "noteCategoryAr")
    @Mapping(source = "entity.note.noteCategory.nameEn" , target = "noteCategoryEn")
    LkNotesDto map(ApplicationSectionNotes entity);

    @AfterMapping
    default void setDescriptionArIfNull(@MappingTarget LkNotesDto dto, ApplicationSectionNotes entity) {
        dto.setDone(Boolean.TRUE);
        if(entity.getNote() != null) {
            if (dto.getDescriptionAr() == null) {
                dto.setDescriptionAr(entity.getNote().getDescriptionAr());
            }
            if (dto.getDescriptionEn() == null) {
                dto.setDescriptionEn(entity.getNote().getDescriptionEn());
            }
            if (dto.getDescriptionEn() == null) {
                dto.setDescriptionEn(entity.getNote().getDescriptionEn());
            }
            if (entity.getNote().getNoteCategory() != null) {
                dto.setNoteCategoryCode(entity.getNote().getNoteCategory().getCode());
                dto.setNoteCategoryAr(entity.getNote().getNoteCategory().getNameAr());
                dto.setNoteCategoryEn(entity.getNote().getNoteCategory().getNameEn());
            }
        }
    }

}
