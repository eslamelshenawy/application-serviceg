package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.ApplicationNotesListDto;
import gov.saip.applicationservice.common.dto.ApplicationNotesResponseDto;
import gov.saip.applicationservice.common.model.ApplicationNotes;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ApplicationNotesListMapper extends BaseMapper<ApplicationNotes, ApplicationNotesListDto> {
    List<ApplicationNotesListDto> map(List<ApplicationNotes> applicationNotes);
}
