package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.dto.lookup.LkNotesDto;
import gov.saip.applicationservice.common.mapper.lookup.LkNotesMapper;
import gov.saip.applicationservice.common.model.*;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(uses = {LkNotesMapper.class})
public interface ApplicationNotesMapper extends BaseMapper<ApplicationNotes, ApplicationNotesDto> {
    List<ApplicationNotesListDto> mapToList(List<ApplicationNotes> applicationNotes);
    ApplicationNotesListDto mapToList(ApplicationNotes applicationNotes);
    ApplicationNotes mapToRequest(ApplicationNotesRequestDto applicationNotesDto);
    ApplicationNotesResponseDto mapToResponse(ApplicationNotes applicationNotes);
    List<ApplicationNotesResponseDto> mapToResponse(List<ApplicationNotes> applicationNotes);

    List<LkNotesDto> mapSectionNotes(List<ApplicationSectionNotes> applicationSectionNotes);

    @AfterMapping
    default void mapSectionNotes(@MappingTarget ApplicationNotesDto applicationNotesDto, ApplicationNotes applicationNotes) {
        applicationNotesDto.setSectionNotes(mapSectionNotes(applicationNotes.getApplicationSectionNotes()));
    }

    @AfterMapping
    default void mapSectionNotes(@MappingTarget ApplicationNotesListDto applicationNotesListDto, ApplicationNotes applicationNotes) {
        applicationNotesListDto.setSectionNotes(mapSectionNotes(applicationNotes.getApplicationSectionNotes()));
    }

    @AfterMapping
    default void mapSectionNotes(@MappingTarget ApplicationNotesResponseDto applicationNotesResponseDto, ApplicationNotes applicationNotes) {
        applicationNotesResponseDto.setSectionNotes(mapSectionNotes(applicationNotes.getApplicationSectionNotes()));
    }

}
