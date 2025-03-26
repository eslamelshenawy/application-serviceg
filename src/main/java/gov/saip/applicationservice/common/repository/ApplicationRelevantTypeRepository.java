package gov.saip.applicationservice.common.repository;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.dto.ApplicantsDto;
import gov.saip.applicationservice.common.dto.ApplicationRelevantTypeDto;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.ApplicationRelevant;
import gov.saip.applicationservice.common.model.ApplicationRelevantType;
import gov.saip.applicationservice.modules.ic.dto.ApplicationApplicantDto;
import gov.saip.applicationservice.modules.ic.dto.InventorDto;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRelevantTypeRepository extends BaseRepository<ApplicationRelevantType, Long> {

    Optional<ApplicationRelevantType> findByApplicationRelevant(ApplicationRelevant applicationRelevant);
    Optional<List<ApplicationRelevantType>> findByApplicationInfo(ApplicationInfo applicationInfo);

    @Query("""
            select
            new gov.saip.applicationservice.common.dto.ApplicantsDto(appRelType.id,appRelvant.fullNameAr,appRelvant.fullNameEn,app.ownerNameAr,app.ownerNameEn,
            appRelvant.identifier,appRelType.inventor,appRelvant.identifierType,appRelType.type,appRelType.customerCode,app.ownerAddressAr,app.ownerAddressEn
            )
            from ApplicationInfo app
            left join ApplicationRelevantType appRelType On app.id =appRelType.applicationInfo.id
            left join ApplicationRelevant appRelvant on appRelvant.id = appRelType.applicationRelevant.id
            where app.id = :appId and appRelType.isDeleted = 0
            """)
    List<ApplicantsDto> retrieveApplicantInfosByAppId(@Param("appId") Long appId);






    @Query(value = """
select art.customerCode 
from ApplicationRelevantType art
where art.applicationInfo.id = :applicationId
""")
    List<String> findCustomerCodesByApplicationId(@Param("applicationId") Long applicationId);

    @Modifying
    @Query(value = "update application.application_relevant_type set is_deleted=1 where id = :relvantTypeId",nativeQuery = true)
    void deleteRelvant (@Param("relvantTypeId") Long relvantTypeId);


    @Query(value = "SELECT a FROM ApplicationRelevantType a  " +
            " join a.applicationInfo info" +
            " where " +
            " info.id = :infoId  AND a.type = 'Applicant_MAIN'")
            ApplicationRelevantType getMainApplicant(@Param("infoId") Long infoId);

    @Query(value = "SELECT a.customerCode FROM ApplicationRelevantType a  " +
            " join a.applicationInfo info" +
            " where " +
            " info.id = :infoId  AND a.type = 'Applicant_MAIN'")
    String getMainApplicantCustomerCodeByApplicationInfoId(@Param("infoId") Long infoId);

    @Query(value = "SELECT a.customerCode FROM ApplicationRelevantType a  " +
            " join a.applicationInfo info" +
            " where " +
            " info.id = :infoId  AND a.type = 'LICENSED_CUSTOMER'")
    String getLicensedApplicantCustomerCodeByApplicationInfoId(@Param("infoId") Long infoId);

    @Modifying
    @Query(value = """
    update ApplicationRelevantType art
    set art.isDeleted = 1
    where art.applicationInfo.id = :appId and art.type = 'Applicant_MAIN'
    """)
    void deleteByApplicationInfoId (@Param("appId") Long appId);

    @Query(value = """
    select count(art) from ApplicationRelevantType art
    where art.applicationInfo.id = :appId and art.type='Applicant_SECONDARY' and art.isPaid = false
    """)
    Long getUnPaidCountApplicationRelavantType(@Param("appId") Long appId);
    @Query(value = """
    select art from ApplicationRelevantType art
    where art.applicationInfo.id = :appId 
    """)
    List<ApplicationRelevantType> getApplicationRelevantTypes(@Param("appId") Long appId);

    @Query(value = """
    select case when count(art) > 0 then true else false end 
    from ApplicationRelevantType art
    where art.applicationInfo.id = :appId 
    and art.type='INVENTOR' 
    and art.waiverDocumentId IS NULL
""")
    Boolean hasApplicationRelavantTypeWithOutWaiverDocument(@Param("appId") Long appId);

    @Query("""
        select appRelType
        from ApplicationRelevantType appRelType 
        left join ApplicationRelevant appRelvant on appRelvant.id = appRelType.applicationRelevant.id
        where appRelType.applicationInfo.id = :appId AND  appRelType.inventor IS TRUE 
        """)
    List<ApplicationRelevantType> retrieveApplicantInfo(@Param("appId") Long appId );


    @Query("""
        select appRelType
        from ApplicationRelevantType appRelType 
        left join ApplicationRelevant appRelvant on appRelvant.id = appRelType.applicationRelevant.id
        where appRelType.applicationInfo.id = :appId AND  appRelType.inventor IS TRUE and appRelType.waiverDocumentId IS NULL and appRelType.type='INVENTOR'
        """)
    List<ApplicationRelevantType> retrieveApplicantWithAttachmentsInfo(@Param("appId") Long appId );


    @Query(value = "select  CONCAT(ds.code, ' ', ds.name) AS sampleCode  from  application.design_sample_relevants dsr\n" +
            "left join application.design_sample ds  on ds.id = dsr.design_sample_id\n" +
            "where dsr.application_relevant_type_id = :applicationRelevantTypeId ", nativeQuery = true)
    List<String> retrieveInventorRelatedToDesignSample(@Param("applicationRelevantTypeId") Long applicationRelevantTypeId );




    @Query("""
            select
            new gov.saip.applicationservice.modules.ic.dto.InventorDto(appRelType.id,appRelevant.fullNameAr,appRelevant.fullNameEn,
            appRelevant.identifier,appRelType.inventor,appRelevant.identifierType,appRelType.type,appRelType.customerCode, appRelType.waiverDocumentId.id, 
            appRelevant.nationalCountryId, appRelevant.countryId, appRelevant.address, appRelevant.city, appRelevant.pobox, appRelevant.gender
            )
            from ApplicationInfo app
            left join ApplicationRelevantType appRelType On app.id = appRelType.applicationInfo.id
            left join ApplicationRelevant appRelevant on appRelevant.id = appRelType.applicationRelevant.id
            where appRelType.isDeleted = 0 and app.id = :appId AND appRelType.inventor IS TRUE and appRelType.type='INVENTOR' and appRelType.type!='Applicant_MAIN'
            """)
    List<InventorDto> getInventorsInfoByAppId(@Param("appId") Long appId);

    @Query("""
            select
            new gov.saip.applicationservice.modules.ic.dto.InventorDto(appRelType.id,appRelevant.fullNameAr,appRelevant.fullNameEn,
            appRelevant.identifier,appRelType.inventor,appRelevant.identifierType,appRelType.type,appRelType.customerCode, appRelType.waiverDocumentId.id, 
            appRelevant.nationalCountryId, appRelevant.countryId, appRelevant.address, appRelevant.city, appRelevant.pobox, appRelevant.gender
            )
            from ApplicationInfo app
            left join ApplicationRelevantType appRelType On app.id = appRelType.applicationInfo.id
            left join ApplicationRelevant appRelevant on appRelevant.id = appRelType.applicationRelevant.id
            where appRelType.isDeleted = 0 and app.id = :appId and appRelType.type in ('Applicant_SECONDARY', 'Applicant_MAIN')
            AND (:inventor is null or appRelType.inventor = :inventor)
            """)
    List<InventorDto> getApplicantsIfInventorsByApplicationId(@Param("appId") Long appId, @Param("inventor") Boolean inventor);

    @Modifying
    @Query(value = """
            update ApplicationRelevantType art
            set art.isDeleted = 1
            where art.id = :id
            """)
    void softDeleteAppById(@Param("id") Long id);
    @Modifying
    @Query(value = """
            delete from application.design_sample_relevants where application_relevant_type_id in (:tobeDeleted)
            """, nativeQuery = true)
    void deleteByInventorIsFalse(@Param("tobeDeleted") List<Long> tobeDeleted);


    @Query("""
            select
            new gov.saip.applicationservice.modules.ic.dto.ApplicationApplicantDto(appRelType.id,appRelevant.fullNameAr,appRelevant.fullNameEn,
            appRelevant.identifier,app.byHimself,appRelevant.identifierType,appRelType.type,appRelType.customerCode, appRelType.waiverDocumentId.id, appRelevant.nationalCountryId
            )
            from ApplicationInfo app
            left join ApplicationRelevantType appRelType On app.id = appRelType.applicationInfo.id
            left join ApplicationRelevant appRelevant on appRelevant.id = appRelType.applicationRelevant.id
            where appRelType.isDeleted = 0 and app.id = :appId and appRelType.type ='Applicant_MAIN'
            """)
    ApplicationApplicantDto getMainApplicantInfoByApplicationId(@Param("appId") Long appId);


    @Modifying
    @Query(value = """
            update ApplicationRelevantType art
            set art.customerCode = :customerCode
            where art.applicationInfo.id = :applicationId 
                and art.type ='Applicant_MAIN'
            """)
    void updateCustomerCodeForMainApplicationRelevant(@Param("customerCode") String customerCode, @Param("applicationId") Long applicationId);

    @Query(value = """
            select art from ApplicationRelevantType art
            where art.applicationInfo.id = :appId and art.type in ('Applicant_SECONDARY', 'Applicant_MAIN') and art.inventor IS TRUE
            and art.isDeleted = 0
            """)
    List<ApplicationRelevantType> getApplicationRelevantTypesMainAndSecondaryApplicantInventors(@Param("appId") Long appId);

    @Query(value = """
                select case when count(art) > 0 then true else false end 
                from ApplicationRelevantType art
                where art.applicationInfo.id = :appId 
                and UPPER(art.customerCode) = UPPER(:customerCode)
            """)
    Boolean checkCustomerCodeExistsOnApplication(@Param("appId") Long appId, @Param("customerCode") String customerCode);

}

