package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.ApplicationSectionNotesDto;
import gov.saip.applicationservice.common.mapper.lookup.LkNotesMapper;
import gov.saip.applicationservice.common.model.ApplicationNotes;
import gov.saip.applicationservice.common.model.ApplicationSectionNotes;
import gov.saip.applicationservice.common.model.LkNotes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(uses = { LkNotesMapper.class, ApplicationNotesMapper.class })
public interface ApplicationSectionNotesMapper extends BaseMapper<ApplicationSectionNotes, ApplicationSectionNotesDto> {

    List<ApplicationSectionNotesDto> unmapToList(List<ApplicationSectionNotes> applicationSectionNotes);

    List<ApplicationSectionNotes> mapToList(List<ApplicationSectionNotesDto> applicationSectionNotesDtoList);

    @Mapping(source = "entity.applicationNotes.id", target = "applicationNotes")
    @Mapping(source = "entity.note.id", target = "lkNotes")
    ApplicationSectionNotesDto map(ApplicationSectionNotes entity);

    @Mapping(source = "dto.applicationNotes", target = "applicationNotes", qualifiedByName = "mapApplicationNotes")
    @Mapping(source = "dto.lkNotes", target = "note", qualifiedByName = "mapLkNotes")
    ApplicationSectionNotes unMap(ApplicationSectionNotesDto dto);

    @Named("mapLkNotes")
    default LkNotes mapLkNotes(Long value) {
        if (value == null) {
            return null;
        }
        LkNotes lkNotes = new LkNotes();
        lkNotes.setId(value);
        return lkNotes;
    }

    // Additional mapping method to handle mapping Long to ApplicationNotes
    default ApplicationNotes map(Long value) {
        if (value == null) {
            return null;
        }
        ApplicationNotes applicationNotes = new ApplicationNotes();
        applicationNotes.setId(value);
        return applicationNotes;
    }

    @Named("mapApplicationNotes")
    default ApplicationNotes mapApplicationNotes(Long value) {
        if (value == null) {
            return null;
        }
        ApplicationNotes applicationNotes = new ApplicationNotes();
        applicationNotes.setId(value);
        return applicationNotes;
    }

}
