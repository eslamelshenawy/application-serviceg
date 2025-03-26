package gov.saip.applicationservice.common.repository;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.model.ApplicationSectionNotes;
import gov.saip.applicationservice.common.model.LkNotes;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface ApplicationSectionNotesRepository extends BaseRepository<ApplicationSectionNotes, Long> {

    @Query("select applicationSectionNotes from ApplicationSectionNotes applicationSectionNotes" +
            " left join applicationSectionNotes.applicationNotes appNotes" +
            " join appNotes.applicationInfo appInfo" +
            " left join appNotes.section section" +
            " left join appNotes.attribute attribute" +
            " where" +
            " appInfo.id = :applicationId" +
            " and (:sectionCode is null or section.code = :sectionCode)" +
            " and (:attributeCode is null or attribute.code = :attributeCode)" +
            " and (:taskDefinitionKey is null or appNotes.taskDefinitionKey = :taskDefinitionKey)" +
            " and applicationSectionNotes.isDeleted = 0 ")
    List<ApplicationSectionNotes> findByAppCodes(@Param("applicationId") Long applicationId
            , @Param("sectionCode") String sectionCode, @Param("attributeCode") String attributeCode
            , @Param("taskDefinitionKey") String taskDefinitionKey);

    @Modifying
    @Transactional
    @Query("DELETE FROM ApplicationSectionNotes applicationSectionNotes" +
            " WHERE applicationSectionNotes.applicationNotes.id = :applicationNotesId")
    void deleteByApplicationNoteId(@Param("applicationNotesId") Long applicationNotesId);

    @Modifying
    @Transactional
    @Query("DELETE FROM ApplicationSectionNotes app" +
            " WHERE app.applicationNotes.id = :applicationNotesId AND (COALESCE(:noteIds) IS NULL OR app.note.id IN ( :noteIds ) )")
    void deleteByIdsHard(@Param("applicationNotesId") Long applicationNotesId,@Param("noteIds")  List<Long> noteIds);

    @Modifying
    @Transactional
    @Query("UPDATE ApplicationSectionNotes app" +
            " SET app.isDeleted = 1  WHERE app.applicationNotes.id = :applicationNotesId AND (COALESCE(:noteIds) IS NULL OR  app.id IN ( :noteIds ) )")
    void deleteByApplicationNoteIdSoft(@Param("applicationNotesId") Long applicationNotesId ,@Param("noteIds")  List<Long> noteIds);

    @Query("""
           select asn from ApplicationSectionNotes asn where asn.applicationNotes.id = :applicationNotesId and asn.isDeleted = 0
           """)
    List<ApplicationSectionNotes> findByApplicationNotes(@Param("applicationNotesId") Long applicationNotesId);


    List<ApplicationSectionNotes> findByNote(LkNotes note);

    @Modifying
    @Transactional
    @Query("DELETE FROM ApplicationSectionNotes app " +
            "WHERE app.applicationNotes.id in :applicationNotesIds")
    void deleteByApplicationNoteIdIn(@Param("applicationNotesIds") List<Long> applicationNotesIds);

}
