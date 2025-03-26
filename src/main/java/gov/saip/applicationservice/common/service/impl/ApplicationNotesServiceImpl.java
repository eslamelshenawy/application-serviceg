package gov.saip.applicationservice.common.service.impl;


import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.clients.rest.feigns.BPMCallerFeignClient;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.dto.lookup.LkNotesDto;
import gov.saip.applicationservice.common.enums.NotesTypeEnum;
import gov.saip.applicationservice.common.enums.ReportDecisionEnum;
import gov.saip.applicationservice.common.enums.RequestTypeEnum;
import gov.saip.applicationservice.common.mapper.ApplicationNotesMapper;
import gov.saip.applicationservice.common.mapper.lookup.LkNotesMapper;
import gov.saip.applicationservice.common.model.*;
import gov.saip.applicationservice.common.repository.ApplicationNotesRepository;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.ApplicationNotesService;
import gov.saip.applicationservice.common.service.ApplicationSectionNotesService;
import gov.saip.applicationservice.common.service.LkAttributeService;
import gov.saip.applicationservice.common.service.lookup.LkNotesService;
import gov.saip.applicationservice.common.service.lookup.LkSectionService;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationNotesServiceImpl extends BaseServiceImpl<ApplicationNotes, Long> implements ApplicationNotesService {

    private final ApplicationNotesRepository applicationNotesRepository;
    private final ApplicationNotesMapper applicationNotesMapper;
    private final LkSectionService lkSectionService;
    private final LkAttributeService lkAttributeService;
    private ApplicationInfoService applicationInfoService;
    private final ApplicationSectionNotesService applicationSectionNotesService;

    private final LkNotesMapper lkNotesMapper;
    private final LkNotesService lkNotesService;
    private final BPMCallerFeignClient bpmCallerFeignClient;
    @Override
    protected BaseRepository<ApplicationNotes, Long> getRepository() {
        return applicationNotesRepository;
    }
    @Autowired
    public void setApplicationInfoService(ApplicationInfoService applicationInfoService) {
        this.applicationInfoService = applicationInfoService;
    }


    public List<SectionApplicationNotesResponseDto> getAllApplicationNotes(Long applicationId, Integer stepId,
                                                                           Integer attributeId, String taskDefinitionKey, NotesTypeEnum notesType) {
        List<LkSection> lkSections = lkSectionService.findAll();
        List<SectionApplicationNotesResponseDto> result = new ArrayList<>();

        for (LkSection section : lkSections) {
            List<ApplicationNotes> applicationNotesList = getApplicationNotes(applicationId, taskDefinitionKey, section);
            if (applicationNotesList != null && !applicationNotesList.isEmpty()) {
                SectionApplicationNotesResponseDto dto = buildSectionApplicationNotesDto(section, applicationNotesList);
                result.add(dto);
            }
        }

        return result;
    }

    private List<ApplicationNotes> getApplicationNotes(Long applicationId, String taskDefinitionKey, LkSection section) {
        return applicationNotesRepository.findByApplicationInfoIdAndDefinitionKeyWithSectionNotes(applicationId, taskDefinitionKey, section.getId());
    }


    private SectionApplicationNotesResponseDto buildSectionApplicationNotesDto(LkSection section, List<ApplicationNotes> applicationNotesList) {
        return SectionApplicationNotesResponseDto.builder()
                .nameAr(section.getNameAr())
                .id(section.getId())
                .code(section.getCode())
                .nameEn(section.getNameEn())
                .applicationNotes(applicationNotesMapper.mapToList(applicationNotesList))
                .build();
    }


    @Override
    public List<ApplicationNotesDto> findAppNotes(Long applicationId, String sectionCode, String attributeCode, String taskDefinitionKey, String stageKey, NotesTypeEnum notesType) {
        List<ApplicationNotes> applicationNotes = applicationNotesRepository.findByAppCodes(applicationId, sectionCode
                , attributeCode, taskDefinitionKey,stageKey,notesType);
        return applicationNotesMapper.map(applicationNotes);
    }


    @Override
    public void updateApplicationNotesDoneStatus(Long applicationNoteId) {
      ApplicationNotes applicationNotes =  findById(applicationNoteId);
      applicationNotes.setDone(applicationNotes.isDone() ? false : true);
      update(applicationNotes);
    }


    @Override
    @Transactional
    public Long addOrUpdateAppNotes(ApplicationNotesReqDto dto){
        if ((dto.getDescription() ==  null || dto.getDescription().isBlank()) &&
             (dto.getApplicationSectionNotesDtos() == null || dto.getApplicationSectionNotesDtos().isEmpty()) &&
             (dto.getSectionNotesIds() == null || dto.getSectionNotesIds().isEmpty())
        ) {
            return null;
        }

      ApplicationInfo applicationInfo = applicationInfoService.getReferenceById(dto.getApplicationId());
      LkSection section = null;
      if (dto.getSectionCode() != null)
      section = lkSectionService.findByCode(dto.getSectionCode());
      LkAttribute attribute =null;
      if (dto.getAttributeCode() != null)
          attribute = lkAttributeService.findByCode(dto.getAttributeCode());

      Long entityId = null;
      if(dto.getId() == null) {
          ApplicationNotes entity = insert(new ApplicationNotes(null, applicationInfo, section, attribute
                  , null, dto.getTaskDefinitionKey(), dto.getDescription(), false, dto.getStageKey(), dto.getPriorityId()));
          insetApplicationSectionNotes(dto, entity);
          entityId = entity.getId();

      }else{
          applicationNotesRepository.updateDescWithId(dto.getId(), dto.getDescription());
          applicationSectionNotesService.deleteByApplicationNoteId(dto.getId());
          applicationSectionNotesService.insertNotesList(dto.getSectionNotesIds(), dto.getId());
          entityId= dto.getId();
      }


        return entityId;
    }

    @Transactional
    @Override
    public void deleteAppNote(ApplicationNotesReqDto dto) {
        List<Long> applicationNoteIds = applicationNotesRepository
                .findIdsByApplicationInfoIdAndSectionCodeAndStageKey(dto.getApplicationId(), dto.getSectionCode(), dto.getStageKey());
        applicationNotesRepository.deleteByApplicationInfoIdAndSectionCodeAndStageKey(dto.getApplicationId(),
                dto.getSectionCode(), dto.getStageKey());
        applicationSectionNotesService.deleteByApplicationNoteIds(applicationNoteIds);
    }

    private void insetApplicationSectionNotes(ApplicationNotesReqDto dto, ApplicationNotes entity) {
        if(dto.getStageKey() != null && !dto.getStageKey().isEmpty()){
            if(dto.getSectionNotesIds() != null && !dto.getSectionNotesIds().isEmpty()){
                insertSectionNotesId(dto, entity);
            }else {
                List<Long> ids = new ArrayList<>();
                if (dto.getApplicationSectionNotesDtos() != null && !dto.getApplicationSectionNotesDtos().isEmpty()) {
                    ids = dto.getApplicationSectionNotesDtos().stream()
                            .map(ApplicationSectionNotesDto::getLkNotes)
                            .collect(Collectors.toList());
                }
                    applicationSectionNotesService.deleteByApplicationNoteIdSoft(entity.getId(), ids);
                    applicationSectionNotesService.deleteByIdsHard(entity.getId(), ids);
                    if (ids != null && !ids.isEmpty())
                        applicationSectionNotesService.insertList(dto.getApplicationSectionNotesDtos(), entity);
            }
        }else{
            insertSectionNotesId(dto, entity);
        }
    }

    private void insertSectionNotesId(ApplicationNotesReqDto dto, ApplicationNotes entity) {
        applicationSectionNotesService.deleteByApplicationNoteId(entity.getId());
        if(dto.getSectionNotesIds() != null && !dto.getSectionNotesIds().isEmpty()){
            applicationSectionNotesService.insertNotesList(dto.getSectionNotesIds(), entity);
        }
    }


    @Override
    public Long updateAppNote(ApplicationNotesReqDto dto){
        Optional<ApplicationNotes>  applicationNotes = applicationNotesRepository
                .findByApplicationInfoIdAndSectionCodeAndattributeCodeAndtaskDefinitionKey(dto.getApplicationId(), dto.getSectionCode(), dto.getAttributeCode() ,
                        dto.getPriorityId() , dto.getTaskDefinitionKey(),dto.getStageKey());
        if(applicationNotes.isPresent()) {
            ApplicationNotes appNote = applicationNotes.get();
            if ((dto.getDescription() == null || dto.getDescription().isEmpty()) && (dto.getSectionNotesIds() == null || dto.getSectionNotesIds().isEmpty())
                    && (dto.getApplicationSectionNotesDtos() == null || dto.getApplicationSectionNotesDtos().isEmpty())) {
                Long applicationNoteId = appNote.getId();
                List<Long> ids = null ;
                if(applicationNotes.get().getApplicationSectionNotes() != null && !applicationNotes.get().getApplicationSectionNotes().isEmpty()) {
                    ids = applicationNotes.get().getApplicationSectionNotes().stream()
                            .map(ApplicationSectionNotes::getId)
                            .collect(Collectors.toList());
                    applicationSectionNotesService.deleteByApplicationNoteIdSoft(applicationNoteId , ids);
                }
//             appNote.setDescription(null);
                appNote.setIsDeleted(1);
                applicationNotesRepository.save(appNote);
                return applicationNoteId;
            }
            return updateCurrentAppNotes(appNote, dto).getId();
        }
        return addOrUpdateAppNotes(dto);
    }

    private ApplicationNotes updateCurrentAppNotes (ApplicationNotes current, ApplicationNotesReqDto dto){
//        List<LkNotes> notes = dto.getSectionNotesIds()
//                .stream()
//                .map(id -> notesService.getReferenceById(id))
//                .collect(Collectors.toList());
//        current.setSectionNotes(notes);
        current.setDescription(dto.getDescription());
        update(current);
        insetApplicationSectionNotes(dto, current);
        return current;
    }

    @Override
    public List<LkNotesDto> findAppNotesByDecisionAndNotesType(SubstantiveReportDto dto, String decision, String notesType) { //here
        if ((dto.getDecision() != null && dto.getDecision().equals(ReportDecisionEnum.valueOf(decision)) || dto.getDecision() == null)) {
            List<ApplicationNotesDto> applicationNotesList = findAppNotes(
                    dto.getApplicationId(),
                    null,
                    null,
                    null,
                    null,
                    NotesTypeEnum.valueOf(notesType)
            );

            Set<Long> uniqueNoteIds = new HashSet<>();
            List<LkNotesDto> uniqueNotes = new ArrayList<>();

            if (applicationNotesList != null && !applicationNotesList.isEmpty()) {
                applicationNotesList
                        .stream()
                        .flatMap(notesDto -> notesDto.getSectionNotes().stream())
                        .filter(note -> uniqueNoteIds.add(note.getId()))
                        .forEach(uniqueNotes::add);
            }

            return uniqueNotes;
        }
        return Collections.emptyList();
    }


    @Override
    public List<ApplicationNotesResponseDto> findAllApplicationNotes(Long applicationId,String taskDefinitionKey) {
        List<ApplicationNotesResponseDto> applicationNotesResponseDtos = new ArrayList<>();
        ApplicationNotesResponseDto applicationNotesResponseDto;
        List<ApplicationNotes> applicationNotesList = applicationNotesRepository.findByApplicationInfoIdAndDefinitionKey(applicationId, taskDefinitionKey);

        if(applicationNotesList == null || applicationNotesList.isEmpty())
            return applicationNotesResponseDtos;

        for(ApplicationNotes applicationNotes : applicationNotesList) {
            applicationNotesResponseDto = new ApplicationNotesResponseDto();
            applicationNotesResponseDto.setDescription(applicationNotes.getDescription());
            applicationNotesResponseDto.setId(applicationNotes.getId());
            applicationNotesResponseDto.setDone(applicationNotes.isDone());
            applicationNotesResponseDto.setAttributeCode(applicationNotes.getAttribute() == null ? null : applicationNotes.getAttribute().getCode());

            List<ApplicationSectionNotes> applicationSectionNotesList = applicationSectionNotesService.findByApplicationNotes(applicationNotes.getId());

            if (applicationSectionNotesList != null && !applicationSectionNotesList.isEmpty()) {

                List<LkNotesDto> allList = fillNotes(applicationSectionNotesList);

                applicationNotesResponseDto.setSectionNotes(allList);
            }
            applicationNotesResponseDtos.add(applicationNotesResponseDto);
        }
        return applicationNotesResponseDtos;
    }

    public List<ApplicationNotesReportDto> getExaminerNotes(Long applicationId, String sectionCode, String attributeCode, String stageKey, String categoryCode) {
        List<ApplicationSectionNotes> applicationSectionNotesList = applicationNotesRepository
                .findApplicationSectionNotesByAppIdAndSectionAndAttributeAndStageAndCategory(
                        applicationId, sectionCode, attributeCode, stageKey, categoryCode);
        if (applicationSectionNotesList != null && !applicationSectionNotesList.isEmpty()) {
            List<ApplicationNotesReportDto> examinerNotes = new ArrayList<>();
            applicationSectionNotesList.forEach(applicationSectionNotes -> {
                if (Objects.nonNull(applicationSectionNotes.getDescription()))
                    examinerNotes.add(new ApplicationNotesReportDto(applicationSectionNotes.getDescription()));

            });
            return examinerNotes;
        }
        return new ArrayList<>();
    }

    private List<LkNotesDto> fillNotes(List<ApplicationSectionNotes> applicationNotesList) {
        return applicationNotesList.stream()
                .map(applicationSectionNotes -> {
                    LkNotesDto dto = lkNotesMapper.map(applicationSectionNotes.getNote());
                    dto.setDone(true);
                    dto.setDescriptionAr(applicationSectionNotes.getDescription());
                    dto.setDescriptionEn(applicationSectionNotes.getDescription());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public ApplicationNotesResponseDto findAllNotes(Long applicationId, String sectionCode, String attributeCode,Long priorityId, String taskDefinitionKey,String stageKey) {
        ApplicationNotesResponseDto applicationNotesResponseDto = new ApplicationNotesResponseDto();
        try {
            Optional<ApplicationNotes> applicationNotes = applicationNotesRepository.findByApplicationInfoIdAndSectionCodeAndattributeCodeAndtaskDefinitionKey(applicationId, sectionCode, attributeCode,priorityId, taskDefinitionKey,stageKey);

        if(applicationNotes.isPresent()){
            applicationNotesResponseDto.setDescription(applicationNotes.get().getDescription());
            applicationNotesResponseDto.setId(applicationNotes.get().getId());
            applicationNotesResponseDto.setDone(applicationNotes.get().isDone());

        List<ApplicationSectionNotes> applicationSectionNotesList = applicationSectionNotesService.findByAppCodes(applicationId, sectionCode, attributeCode, taskDefinitionKey);

            if (applicationSectionNotesList != null && !applicationSectionNotesList.isEmpty()) {
                List<LkNotesDto> allList = fillNotes(applicationSectionNotesList);


                List<Long> listIds = allList.stream()
                        .map(LkNotesDto::getId)
                        .collect(Collectors.toList());

                List<LkNotesDto> lkNotes = lkNotesService.getNotes(null, sectionCode, attributeCode, null,null);

                if (listIds != null) {
                    lkNotes.stream()
                            .filter(note -> !listIds.contains(note.getId()))
                            .forEach(note -> {
                                allList.add(note);
                                listIds.add(note.getId());
                            });
                } else {
                    allList.addAll(lkNotes);
                }

                applicationNotesResponseDto.setSectionNotes(allList);
            }else{
                applicationNotesResponseDto.setSectionNotes(lkNotesService.getNotes(null, sectionCode, attributeCode, null,null));
            }
        }else {
            applicationNotesResponseDto.setSectionNotes(lkNotesService.getNotes(null, sectionCode, attributeCode, null,null));
        }

        } catch (Exception e) {
                throw new BusinessException(Constants.ErrorKeys.COMMON_CODE_IS_DUPLICATED, HttpStatus.NOT_FOUND, null);
        }
        return applicationNotesResponseDto;
    }

    @Override
    @Transactional
    public void markAllNotesAsDoneByApplicationId(Long appId) {
        applicationNotesRepository.markAllNotesAsDoneByApplicationId(appId);
    }

    @Override
    public List<SectionApplicationNotesResponseDto> getAllApplicationNotesWithApplicationIdAndStageKey(Long appId, String stageKey,String sectionCode) {
        List<ApplicationNotes> applicationNotesList = applicationNotesRepository.findByApplicationIdAndStageKey(appId, stageKey,sectionCode);
        List<SectionApplicationNotesResponseDto>  sectionApplicationNotesResponseDtoList = new ArrayList<>();
        if (applicationNotesList != null && !applicationNotesList.isEmpty())
            sectionApplicationNotesResponseDtoList =  convertApplicationNotesListToSectionApplicationNotesResponseDtoList(applicationNotesList);
        return sectionApplicationNotesResponseDtoList;
    }

    private List<SectionApplicationNotesResponseDto> convertApplicationNotesListToSectionApplicationNotesResponseDtoList(List<ApplicationNotes> applicationNotesList) {
        Set<LkSection> sections = applicationNotesList.stream().map(appNote -> appNote.getSection()).collect(Collectors.toSet());
        List<SectionApplicationNotesResponseDto> sectionApplicationNotesResponseDtoList =  sections.stream().map(section -> SectionApplicationNotesResponseDto.builder()
                .nameAr(section.getNameAr())
                .id(section.getId())
                .code(section.getCode())
                .nameEn(section.getNameEn())
                .applicationNotes(applicationNotesMapper.mapToList(applicationNotesList.stream().filter(note -> note.getSection().getId().equals(section.getId())).collect(Collectors.toList())))
                .build()).collect(Collectors.toList());
        return sectionApplicationNotesResponseDtoList;
    }

    @Override
    public Boolean existsNotesByApplicationIdAndStageKey(Long applicationId, String stageKey) {
        return applicationNotesRepository.existsNotesByApplicationIdAndStageKey(applicationId,stageKey);
    }

    @Override
    public String getPvLastApplicantOppositionForInvitationCorrection(Long appId,String taskKey) {
        String oppositionNotes = applicationNotesRepository.getPvLastApplicantOppositionForInvitationCorrection(appId,taskKey);
        if (Objects.nonNull(oppositionNotes) && !oppositionNotes.isEmpty()){
            return applicationNotesRepository.getPvLastApplicantOppositionForInvitationCorrection(appId,taskKey);
        }
        return null;
    }

    @Override
    public String getPTLastApplicantOppositionForInvitationCorrection(Long appId, String taskKey) {
        String oppositionNotes = applicationNotesRepository.getPTLastApplicantOppositionForInvitationCorrection(appId,taskKey);
        if (Objects.nonNull(oppositionNotes) && !oppositionNotes.isEmpty()){
            return applicationNotesRepository.getPTLastApplicantOppositionForInvitationCorrection(appId,taskKey);
        }
        return null;
    }

    @Override
    public Boolean haveApplicantOppositionForInvitationCorrectionPAT(Long appId, String taskDefinitionKey) {
        return applicationNotesRepository.haveApplicantOppositionForInvitationCorrectionPAT(appId,taskDefinitionKey);
    }
}
