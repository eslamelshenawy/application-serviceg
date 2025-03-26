package gov.saip.applicationservice.common.service.lookup.impl;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.lookup.LkApplicationStatusDto;
import gov.saip.applicationservice.common.dto.lookup.LkNotesDto;
import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import gov.saip.applicationservice.common.enums.NotesStepEnum;
import gov.saip.applicationservice.common.enums.NotesTypeEnum;
import gov.saip.applicationservice.common.mapper.lookup.LkNotesMapper;
import gov.saip.applicationservice.common.model.*;
import gov.saip.applicationservice.common.repository.ApplicationSectionNotesRepository;
import gov.saip.applicationservice.common.repository.lookup.LkNotesRepository;
import gov.saip.applicationservice.common.service.LkAttributeService;
import gov.saip.applicationservice.common.service.LkNoteCategoryService;
import gov.saip.applicationservice.common.service.lookup.LkApplicantCategoryService;
import gov.saip.applicationservice.common.service.lookup.LkApplicationCategoryService;
import gov.saip.applicationservice.common.service.lookup.LkNotesService;
import gov.saip.applicationservice.common.service.lookup.LkSectionService;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LkNotesServiceImpl extends BaseServiceImpl<LkNotes, Long> implements LkNotesService {

    private final LkNotesRepository lkNotesRepository;
    private final LkNotesMapper lkNotesMapper;
    private final LkApplicationCategoryService lkApplicationCategoryService;
    private final LkSectionService lkSectionService;
    private final LkAttributeService lkAttributeService;
    private final LkNoteCategoryService lkNoteCategoryService;
    @Autowired
    ApplicationSectionNotesRepository applicationSectionNotesRepository;
    @Override
    protected BaseRepository<LkNotes, Long> getRepository() {
        return lkNotesRepository;
    }
    @Override
    public List<LkNotesDto> getNotes(String categoryCode, String sectionCode, String attributeCode, NotesTypeEnum notesType,NotesStepEnum notesStep) {
        if (ApplicationCategoryEnum.PATENT.name().equals(categoryCode)){
            return lkNotesMapper.map(lkNotesRepository.findByCodes(categoryCode, sectionCode, attributeCode, notesType , NotesStepEnum.FORMAL_EXAMINER));
        }
        return lkNotesMapper.map(lkNotesRepository.findByCodes(categoryCode, sectionCode, attributeCode, notesType , notesStep));
    }

    @Override
    public List<LkNotesDto> getNotes(String categoryCode, String sectionCode, String attributeCode) {
        List<LkNotes> lkNotes = lkNotesRepository.findByCodes(categoryCode, sectionCode, attributeCode);
        return lkNotesMapper.map(lkNotes);
    }

    @Override
    public PaginationDto findAllPaginatedNotesByAppCategory(Integer page, Integer limit, String sortableColumn,  Long categoryId, NotesStepEnum notesStep, String sectionCode, String attributeCode, String noteCategoryCode , String search) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortableColumn));
        Page<LkNotes> lkNotes = lkNotesRepository.getAllPaginatedNotes( categoryId, notesStep, sectionCode, attributeCode, noteCategoryCode,search ,pageable);
        return PaginationDto.builder()
                    .content(lkNotesMapper.map(lkNotes.getContent()))
                    .totalElements(lkNotes.getTotalElements())
                    .totalPages(lkNotes.getTotalPages())
                    .build();

    }

    @Override
    public LkNotes update(LkNotes lkNotes){
        LkNotes currentLkNotes = findById(lkNotes.getId());
        LkNotesDto lkNotesDto = lkNotesMapper.map(lkNotes);
        LkApplicationCategory lkApplicationCategory = lkApplicationCategoryService.findBySaipCode(lkNotes.getCategory().getSaipCode());
        currentLkNotes.setCategory(lkApplicationCategory);
        currentLkNotes.setNameEn(lkNotes.getNameEn());
        currentLkNotes.setNameAr(lkNotes.getNameAr());
        currentLkNotes.setDescriptionEn(lkNotes.getDescriptionEn());
        currentLkNotes.setDescriptionAr(lkNotes.getDescriptionAr());
        currentLkNotes.setNotesTypeEnum(lkNotes.getNotesTypeEnum());
        currentLkNotes.setNotesStep(lkNotes.getNotesStep());
        if (lkNotesDto.getSectionCode() != null){
            LkSection lkSection = lkSectionService.findByCode(lkNotesDto.getSectionCode());
            currentLkNotes.setSection(lkSection);
        }
        if (lkNotesDto.getAttributeCode() != null){
            LkAttribute lkAttribute = lkAttributeService.findByCode(lkNotes.getAttribute().getCode());
            currentLkNotes.setAttribute(lkAttribute);
        }
        if (lkNotesDto.getNoteCategoryCode() != null){
            LkNoteCategory lkNoteCategory = lkNoteCategoryService.findByCode(lkNotesDto.getNoteCategoryCode());
            currentLkNotes.setNoteCategory(lkNoteCategory);
        }
        return super.update(currentLkNotes);
    }
    @Override
    public LkNotes insert(LkNotes lkNotes){
        LkApplicationCategory lkApplicationCategory = lkApplicationCategoryService.findBySaipCode(lkNotes.getCategory().getSaipCode());
        lkNotes.setCategory(lkApplicationCategory);
        setSectionCode(lkNotes);
        setAttributeCode(lkNotes);
        setNoteCategoryCode(lkNotes);
        return super.insert(lkNotes);
    }


    private void setSectionCode(LkNotes lkNotes){
        LkNotesDto lkNotesDto = lkNotesMapper.map(lkNotes);
        if (lkNotesDto.getSectionCode() != null){
            LkSection lkSection = lkSectionService.findByCode(lkNotesDto.getSectionCode());
            lkNotes.setSection(lkSection);
        } else {
            lkNotes.setAttribute(null);
        }
    }

    private void setAttributeCode(LkNotes lkNotes){
        LkNotesDto lkNotesDto = lkNotesMapper.map(lkNotes);
        if (lkNotesDto.getAttributeCode() != null){
            LkAttribute lkAttribute = lkAttributeService.findByCode(lkNotes.getAttribute().getCode());
            lkNotes.setAttribute(lkAttribute);
        } else {
            lkNotes.setAttribute(null);
        }
    }

    private void setNoteCategoryCode(LkNotes lkNotes){
        LkNotesDto lkNotesDto = lkNotesMapper.map(lkNotes);
        if (lkNotesDto.getNoteCategoryCode() != null){
            LkNoteCategory lkNoteCategory = lkNoteCategoryService.findByCode(lkNotesDto.getNoteCategoryCode());
            lkNotes.setNoteCategory(lkNoteCategory);
        } else {
            lkNotes.setNoteCategory(null);
        }
    }


    @Override
    public void softDeleteById(Long id) {

        Optional<LkNotes> optionalLkNotes = lkNotesRepository.findById(id);

        if (optionalLkNotes.isPresent()) {
            LkNotes lkNotes = optionalLkNotes.get();
            List<ApplicationSectionNotes> relatedNotes = applicationSectionNotesRepository.findByNote(lkNotes);

            if (relatedNotes.isEmpty()) {
                lkNotesRepository.updateIsDeleted(id, 1);
            } else {
                throw new BusinessException(Constants.ErrorKeys.LK_NOTES_ID, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
    }

}
