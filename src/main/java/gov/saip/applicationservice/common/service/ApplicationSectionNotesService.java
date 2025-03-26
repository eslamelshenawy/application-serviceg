package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.model.ApplicationNotes;
import gov.saip.applicationservice.common.model.ApplicationSectionNotes;

import java.util.List;


public interface ApplicationSectionNotesService extends BaseService<ApplicationSectionNotes, Long>{

    public void insertList(List<ApplicationSectionNotesDto> applicationSectionNotesDtoList,ApplicationNotes applicationNotes);

    void insertNotesList(List<Long> sectionNotesIds, Long applicationNoteId);

    public void insertNotesList(List<Long> sectionNotesIds, ApplicationNotes applicationNotes);

    public void deleteByApplicationNoteId(Long applicationNotesId);

    public void deleteByIdsHard(Long applicationNotesId , List<Long> noteIds);

    public void deleteByApplicationNoteIdSoft(Long applicationNotesId , List<Long> ids);

    public List<ApplicationSectionNotes> findByApplicationNotes(Long applicationNotesId);

    public List<ApplicationSectionNotes> findByAppCodes(Long applicationId,String sectionCode,  String attributeCode, String taskDefinitionKey);

    void deleteByApplicationNoteIds(List<Long> applicationNotesIds);
}
