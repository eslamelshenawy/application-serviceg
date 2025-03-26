package gov.saip.applicationservice.common.service.lookup;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.lookup.LkNotesDto;
import gov.saip.applicationservice.common.enums.NotesStepEnum;
import gov.saip.applicationservice.common.enums.NotesTypeEnum;
import gov.saip.applicationservice.common.model.LkNotes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface LkNotesService extends BaseService<LkNotes, Long> {
    List<LkNotesDto> getNotes(String categoryCode, String sectionCode, String attributeCode, NotesTypeEnum notesType,NotesStepEnum notesStep);

    List<LkNotesDto> getNotes(String categoryCode, String sectionCode, String attributeCode);

    PaginationDto findAllPaginatedNotesByAppCategory(Integer page, Integer limit, String sortableColumn, Long categoryId, NotesStepEnum notesStep, String sectionCode, String attributeCode, String noteCategoryCode , String  search);

    void softDeleteById(Long id);





}
