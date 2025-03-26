package gov.saip.applicationservice.common.repository.lookup;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.enums.NotesStepEnum;
import gov.saip.applicationservice.common.enums.NotesTypeEnum;
import gov.saip.applicationservice.common.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LkNotesRepository extends BaseRepository<LkNotes, Long> {

    @Query("select note from LkNotes note" +
            " left join note.category category" +
            " left join note.section section " +
            " left join note.attribute attribute" +
            " where" +
            " (:categoryCode is null or category.saipCode =:categoryCode)" +
            " and (:sectionCode is null or section.code = :sectionCode)" +
            " and (:attributeCode is null or attribute.code = :attributeCode)  " +
            " and (:notesType is null or note.notesTypeEnum = :notesType ) " +
            " and (:notesStep is null or note.notesStep = :notesStep)" )
    List<LkNotes> findByCodes(@Param("categoryCode") String categoryCode, @Param("sectionCode") String sectionCode
            , @Param("attributeCode") String attributeCode, @Param("notesType") NotesTypeEnum notesType
            , @Param("notesStep") NotesStepEnum notesStep);

    @Query("select note from LkNotes note" +
            " left join note.category category" +
            " left join note.section section " +
            " left join note.attribute attribute" +
            " where" +
            " (:categoryCode is null or category.saipCode =:categoryCode)" +
            " and (:sectionCode is null or section.code = :sectionCode)" +
            " and (:attributeCode is null or attribute.code = :attributeCode)")
    List<LkNotes> findByCodes(@Param("categoryCode") String categoryCode, @Param("sectionCode") String sectionCode,
                              @Param("attributeCode") String attributeCode);


    @Query("""
            SELECT DISTINCT n FROM LkNotes n 
            LEFT JOIN n.category c 
            LEFT JOIN n.section s 
            LEFT JOIN n.attribute a 
            LEFT JOIN n.noteCategory nc 
            WHERE (:categoryId IS NULL OR c.id = :categoryId) 
            AND (:notesStep IS NULL OR n.notesStep = :notesStep) 
            AND (:sectionCode IS NULL OR s.code = :sectionCode) 
            AND (:attributeCode IS NULL OR a.code = :attributeCode) 
            AND (:noteCategoryCode IS NULL OR nc.code = :noteCategoryCode)
            AND n.isDeleted != 1
            AND (:search IS NULL OR :search = ''
            OR UPPER(n.descriptionAr) LIKE UPPER(concat('%', :search, '%'))
            OR UPPER(n.descriptionEn) LIKE UPPER(concat('%', :search, '%'))
            OR UPPER(n.nameEn) LIKE UPPER(concat('%', :search, '%'))
            OR UPPER(n.nameAr) LIKE UPPER(concat('%', :search, '%')))
            """)
    Page<LkNotes> getAllPaginatedNotes(
                                       @Param("categoryId")Long categoryId,
                                       @Param("notesStep")NotesStepEnum notesStep,
                                       @Param("sectionCode")String sectionCode,
                                       @Param("attributeCode")String attributeCode,
                                       @Param("noteCategoryCode")String noteCategoryCode,
                                       @Param("search")String search,
                                       Pageable pageable);





//    AND (:search IS NULL OR :search = ''
//            OR UPPER(notes.descriptionAr) LIKE UPPER(concat('%', :search, '%'))
//    OR UPPER(notes.descriptionEn) LIKE UPPER(concat('%', :search, '%'))
//    OR UPPER(notes.nameEn) LIKE UPPER(concat('%', :search, '%'))
//    OR UPPER(notes.nameAr) LIKE UPPER(concat('%', :search, '%')))

}
