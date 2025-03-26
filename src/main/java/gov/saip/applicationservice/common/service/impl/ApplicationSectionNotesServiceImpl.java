package gov.saip.applicationservice.common.service.impl;


import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.mapper.ApplicationSectionNotesMapper;
import gov.saip.applicationservice.common.model.*;
import gov.saip.applicationservice.common.repository.ApplicationSectionNotesRepository;
import gov.saip.applicationservice.common.service.ApplicationSectionNotesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ApplicationSectionNotesServiceImpl extends BaseServiceImpl<ApplicationSectionNotes, Long> implements ApplicationSectionNotesService {

    private final ApplicationSectionNotesRepository applicationSectionNotesRepository;
    private final ApplicationSectionNotesMapper applicationSectionNotesMapper;
    @Override
    protected BaseRepository<ApplicationSectionNotes, Long> getRepository() {
        return applicationSectionNotesRepository;
    }
    @Override
    public void insertList(List<ApplicationSectionNotesDto> applicationSectionNotesDtoList, ApplicationNotes applicationNotes) {
        deleteByApplicationNoteId(applicationNotes.getId());
        List<ApplicationSectionNotes> existingSectionNotes = new ArrayList<>();

        for (ApplicationSectionNotesDto dto : applicationSectionNotesDtoList) {
            ApplicationSectionNotes sectionNotes = new ApplicationSectionNotes();
            sectionNotes.setNote(new LkNotes(dto.getLkNotes()));
            sectionNotes.setDescription(dto.getDescription());
            sectionNotes.setApplicationNotes(applicationNotes);

            existingSectionNotes.add(sectionNotes);
        }

        applicationSectionNotesRepository.saveAll(existingSectionNotes);
    }



    @Override
    public void insertNotesList(List<Long> sectionNotesIds, Long applicationNoteId) {
        ApplicationNotes applicationNotes = new ApplicationNotes();
        applicationNotes.setId(applicationNoteId);
        insertNotesList(sectionNotesIds, applicationNotes);
    }

    @Override
    public void insertNotesList(List<Long> sectionNotesIds, ApplicationNotes applicationNotes) {
        List<ApplicationSectionNotes> applicationSectionNotesList = new ArrayList<>();

        sectionNotesIds.forEach(sectionNotesId -> {
            ApplicationSectionNotes applicationSectionNotes = new ApplicationSectionNotes();
            applicationSectionNotes.setApplicationNotes(applicationNotes);
            applicationSectionNotes.setNote(new LkNotes(sectionNotesId));
            applicationSectionNotesList.add(applicationSectionNotes);
        });

        applicationSectionNotesRepository.saveAll(applicationSectionNotesList);
    }

    public void deleteByApplicationNoteId(Long applicationNotesId){
        applicationSectionNotesRepository.deleteByApplicationNoteId(applicationNotesId);
    }

    public void deleteByApplicationNoteIdSoft(Long applicationNotesId , List<Long> noteIds){
        applicationSectionNotesRepository.deleteByApplicationNoteIdSoft(applicationNotesId , noteIds);
    }

    public void deleteByIdsHard(Long applicationNotesId , List<Long> noteIds){
        applicationSectionNotesRepository.deleteByIdsHard(applicationNotesId , noteIds);
    }

    @Override
    public List<ApplicationSectionNotes> findByApplicationNotes(Long applicationNotesId){
       return  applicationSectionNotesRepository.findByApplicationNotes(applicationNotesId);
    }

    @Override
    public List<ApplicationSectionNotes> findByAppCodes(Long applicationId,String sectionCode,  String attributeCode, String taskDefinitionKey){
        return applicationSectionNotesRepository.findByAppCodes(applicationId,sectionCode,attributeCode,taskDefinitionKey);
    }

    public void deleteByApplicationNoteIds(List<Long> applicationNotesIds){
        applicationSectionNotesRepository.deleteByApplicationNoteIdIn(applicationNotesIds);
    }
}
