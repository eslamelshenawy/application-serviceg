package gov.saip.applicationservice.common.repository;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.enums.NotesTypeEnum;
import gov.saip.applicationservice.common.model.ApplicationNotes;
import gov.saip.applicationservice.common.model.ApplicationSectionNotes;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Repository
public interface ApplicationNotesRepository extends BaseRepository<ApplicationNotes, Long> {

    @Query("select appNotes from ApplicationNotes appNotes" +
            " join appNotes.applicationInfo appInfo" +
            " left join appNotes.section section" +
            " left join appNotes.attribute attribute " +
            " left join appNotes.applicationSectionNotes applicationSectionNotes" +
            " where" +
            " appInfo.id = :applicationId" +
            " and (:sectionId is null or section.id = :sectionId)" +
            " and (:attributeId is null or attribute.id = :attributeId)" +
            " and (:taskDefinitionKey is null or appNotes.taskDefinitionKey = :taskDefinitionKey) " +
            " and ( (:notesType is null or applicationSectionNotes.note.notesTypeEnum = :notesType ) )" +
            " and appNotes.isDeleted = 0 ")
    List<ApplicationNotes> findAllByApplicationInfoIdAndSectionId(@Param("applicationId") Long applicationId
            ,@Param("sectionId") Integer sectionId,@Param("attributeId") Integer attributeId
            ,@Param("taskDefinitionKey") String taskDefinitionKey, @Param("notesType") NotesTypeEnum notesType);

    @Query("""
            select distinct appNotes from ApplicationNotes appNotes
            join appNotes.applicationInfo appInfo
            left join appNotes.section section
            left join appNotes.attribute attribute
            left join appNotes.applicationSectionNotes applicationSectionNotes
            left join applicationSectionNotes.note lknote
            on lknote.id = applicationSectionNotes.note.id
             and (
             (:notesType is null and applicationSectionNotes.note.notesTypeEnum = 'APPLICANT')
             or
             (:notesType is not null and applicationSectionNotes.note.notesTypeEnum = :notesType )
             )
            where
            appInfo.id = :applicationId
            and (:sectionCode is null or section.code = :sectionCode)
            and (:attributeCode is null or attribute.code = :attributeCode)
            and (:taskDefinitionKey is null or appNotes.taskDefinitionKey = :taskDefinitionKey)
            and (:stageKey is null or appNotes.stageKey = :stageKey)
            and appNotes.isDone = false
            and appNotes.isDeleted = 0
            """)
    List<ApplicationNotes> findByAppCodes(@Param("applicationId") Long applicationId
            ,@Param("sectionCode") String sectionCode, @Param("attributeCode") String attributeCode
            ,@Param("taskDefinitionKey") String taskDefinitionKey,@Param("stageKey") String stageKey, @Param("notesType") NotesTypeEnum notesType);



    @Query("select appNotes from ApplicationNotes appNotes" +
            " join appNotes.applicationInfo appInfo" +
            " left join appNotes.section section" +
            " left join appNotes.attribute attribute" +
            " where" +
            " appInfo.id = :applicationId" +
            " and (:sectionCode is null or section.code = :sectionCode)" +
            " and (:taskDefinitionKey is null or appNotes.taskDefinitionKey = :taskDefinitionKey)" +
            " and (:stageKey is null or appNotes.stageKey = :stageKey)" +
            " and (:priorityId is null or appNotes.priorityId = :priorityId)" +
            " and (:attributeCode is null or attribute.code = :attributeCode) and appNotes.isDeleted = 0 ")
    Optional<ApplicationNotes> findByApplicationInfoIdAndSectionCodeAndattributeCodeAndtaskDefinitionKey(@Param("applicationId") Long applicationId
            ,@Param("sectionCode") String sectionCode, @Param("attributeCode") String attributeCode, @Param("priorityId") Long priorityId
            ,@Param("taskDefinitionKey") String taskDefinitionKey,@Param("stageKey") String stageKey);

    @Query("select appNotes from ApplicationNotes appNotes" +
            " join appNotes.applicationInfo appInfo" +
            " left join appNotes.section section" +
            " left join appNotes.attribute attribute" +
            " where" +
            " appInfo.id = :applicationId" +
            " and (:taskDefinitionKey is null or appNotes.taskDefinitionKey = :taskDefinitionKey)" +
            " and appNotes.isDeleted = 0")
    List<ApplicationNotes> findByApplicationInfoIdAndDefinitionKey(@Param("applicationId") Long applicationId,@Param("taskDefinitionKey") String taskDefinitionKey);

    @Query("""
           select asn from ApplicationSectionNotes asn 
           left join asn.applicationNotes appNotes 
           join appNotes.applicationInfo app
           left join asn.note lknote
           left join lknote.noteCategory cat 
           left join appNotes.attribute attr
           left join appNotes.section sec
           where (:appId is null or app.id = :appId)
           And(:sectionCode is null or sec.code=:sectionCode)
           And(:stage is null or appNotes.stageKey=:stage)
           And(:attributeCode is null or attr.code=:attributeCode)
           AND (:categoryCode IS NULL or cat.code = :categoryCode )
           AND asn.isDeleted = 0
           """)
    List<ApplicationSectionNotes> findApplicationSectionNotesByAppIdAndSectionAndAttributeAndStageAndCategory(
            @Param("appId")Long appId
            ,@Param("sectionCode") String sectionCode
            ,@Param("attributeCode") String attributeCode,
            @Param("stage") String stage,
            @Param("categoryCode") String categoryCode);
    @Modifying
    @Query("""
        update ApplicationNotes an set an.isDone = true where an.applicationInfo.id = :appId
    """)
    void markAllNotesAsDoneByApplicationId(@Param("appId") Long appId);

    @Query("SELECT DISTINCT appNotes FROM ApplicationNotes appNotes " +
            "LEFT JOIN FETCH appNotes.applicationSectionNotes asn " +
            "WHERE appNotes.applicationInfo.id = :applicationId " +
            "AND (:sectionId IS NULL OR appNotes.section.id = :sectionId) " +
            "AND (:taskDefinitionKey IS NULL OR appNotes.taskDefinitionKey = :taskDefinitionKey) " +
            "AND appNotes.isDeleted = 0 ")
    List<ApplicationNotes> findByApplicationInfoIdAndDefinitionKeyWithSectionNotes(
            @Param("applicationId") Long applicationId,
            @Param("taskDefinitionKey") String taskDefinitionKey,
            @Param("sectionId") Integer sectionId);

    @Modifying
    @Transactional
    @Query("update ApplicationNotes appNotes set appNotes.isDeleted = 1" +
            " where appNotes.applicationInfo.id = :applicationId" +
            " and (:sectionCode is null or appNotes.section.id = (SELECT sec.id FROM LkSection sec WHERE sec.code = :sectionCode))" +
            " and (:stageKey is null or appNotes.stageKey = :stageKey)")
    void deleteByApplicationInfoIdAndSectionCodeAndStageKey(@Param("applicationId") Long applicationId
            ,@Param("sectionCode") String sectionCode ,@Param("stageKey") String stageKey);


    @Query("""
            select distinct appNotes from ApplicationNotes appNotes
            where
            appNotes.applicationInfo.id = :applicationId
            and  appNotes.stageKey = :stageKey
            and appNotes.isDone = false
            and appNotes.isDeleted = 0
            and (:sectionCode is null or appNotes.section.code = :sectionCode)
            """)
    List<ApplicationNotes> findByApplicationIdAndStageKey(@Param("applicationId") Long applicationId, @Param("stageKey") String stageKey,@Param("sectionCode")String sectionCode);

    @Query("select appNotes.id from ApplicationNotes appNotes" +
            " join appNotes.applicationInfo appInfo" +
            " left join appNotes.section section" +
            " left join appNotes.attribute attribute" +
            " where" +
            " appInfo.id = :applicationId" +
            " and (:sectionCode is null or section.code = :sectionCode)" +
            " and (:stageKey is null or appNotes.stageKey = :stageKey) and appNotes.isDeleted = 0 ")
    List<Long> findIdsByApplicationInfoIdAndSectionCodeAndStageKey(@Param("applicationId") Long applicationId
            ,@Param("sectionCode") String sectionCode
            ,@Param("stageKey") String stageKey);

    @Modifying
    @Query("update ApplicationNotes appNotes set appNotes.description = :description" +
            " where appNotes.id = :id")
    void updateDescWithId(@Param("id") Long id, @Param("description") String description);


    @Query("""
        select count(appNotes) > 0 from ApplicationNotes appNotes
        where
        appNotes.applicationInfo.id = :applicationId
        and appNotes.stageKey = :stageKey
        and appNotes.isDone = false
        and appNotes.isDeleted = 0
        and appNotes.section is not null
        """)
    Boolean existsNotesByApplicationIdAndStageKey(@Param("applicationId") Long applicationId, @Param("stageKey") String stageKey);

    @Query("""
            select an.description from ApplicationNotes an where an.applicationInfo.id= :appId
            and an.section.code= 'PV_APPLICANT_OPPOSITION' and taskDefinitionKey= :taskKey
            """)
    String getPvLastApplicantOppositionForInvitationCorrection(@Param("appId")Long appId,@Param("taskKey")String taskKey);
    @Query("""
            select an.description from ApplicationNotes an where an.applicationInfo.id= :appId
            and an.section.code= 'PT_APPLICANT_OPPOSITION' and taskDefinitionKey= :taskKey
            """)
    String getPTLastApplicantOppositionForInvitationCorrection(@Param("appId")Long appId,@Param("taskKey")String taskKey);
    @Query("""
            select count(an.description)>0  from ApplicationNotes an where an.applicationInfo.id= :appId
            and an.section.code= 'PT_APPLICANT_OPPOSITION' and taskDefinitionKey= :taskDefinitionKey
            """)
    Boolean haveApplicantOppositionForInvitationCorrectionPAT(@Param("appId")Long appId,@Param("taskDefinitionKey")String taskDefinitionKey);
}
