package gov.saip.applicationservice.common.repository.trademark;


import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.dto.trademark.ApplicationTMAttributesDto;
import gov.saip.applicationservice.common.dto.trademark.ApplicationTradeMarkProjection;
import gov.saip.applicationservice.common.dto.trademark.TrademarkApplicationInfoXmlDataDto;
import gov.saip.applicationservice.common.model.trademark.TrademarkDetail;
import gov.saip.applicationservice.common.projection.TradeMarkInfo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface TrademarkDetailRepository extends BaseRepository<TrademarkDetail, Long> {

    @Query(value = "SELECT td.id FROM TrademarkDetail td WHERE  td.applicationId= :applicationId")
    Long findIdTMByApplicationId(@Param("applicationId") Long applicationId);
    Optional<TrademarkDetail> findByApplicationId(Long applicationId);
    @Query(value = "select\n" +
            "app.id as appId,\n" +
            "app.title_ar as appTitleAr,\n" +
            "app.title_en as appTitleEn,\n" +
            "stat.ips_status_desc_ar as statusAr,\n" +
            "stat.ips_status_desc_en as statusEn,\n" +
            "clas.name_ar as classificationAr,\n" +
            "clas.name_en as classificationEn,\n" +
            "tm.word_mark as TmWorkMark,\n" +
            "tm.examiner_grant_condition as examinerGrantCondition,\n" +
            "tm.mark_description MarkDescription ,\n" +
            "tm.mark_claiming_color MarkClaimingColor,\n" +
            "lmt.name_ar TmTypeAr,\n" +
            "lmt.name_en TmTypeEn,\n" +
            "apptype.customer_code CustomerCode\n" +
            "from application.applications_info app\n" +
            "left join application.lk_application_status stat on app.application_status_id = stat.id\n" +
            "left join application.application_nice_classifications anc on anc.application_id = app.id \n" +
            "left join application.classifications clas on anc.classification_id  = clas.id\n" +
            "left join application.application_relevant_type art on app.id = art.application_relevant_id\n" +
            "left join application.trademark_details tm on app.id = tm.application_id\n" +
            "left join application.application_relevant_type apptype on apptype.application_info_id = app.id\n" +
            "left join application.lk_mark_types lmt on lmt.id = tm.mark_type_id\n" +
            "where\n" +
            "apptype.customer_code = :customerCode\t" +
            "And app.is_deleted ='0'" ,nativeQuery = true)
    List<TradeMarkInfo> getTradeMarkApplicationInfo(@Param("customerCode")String customerCode);

    @Query(value = """
SELECT
    app.applicationNumber AS applicationNumber,
    app.titleAr AS appTitleAr,
    app.titleEn AS appTitleEn
FROM
    ApplicationInfo app
inner JOIN   LkApplicationStatus las ON app.applicationStatus.id = las.id
inner JOIN   LkApplicationCategory lac ON lac.id = app.category.id
inner JOIN   ApplicationNiceClassification anc ON anc.application.id = app.id
inner JOIN   Classification clas ON anc.classification.id = clas.id
inner JOIN   ApplicationRelevantType apptype ON apptype.applicationInfo.id = app.id
WHERE
    apptype.customerCode = :customerCode
     AND app.isDeleted = 0 and lac.saipCode = 'TRADEMARK'
     AND app.applicationNumber IS NOT NULL
     AND las.code = 'THE_TRADEMARK_IS_REGISTERED'
""")
    List<TradeMarkInfo> getRegisteredTradeMarkInfoByApplicantCode(@Param("customerCode")String customerCode);


    @Query(value = """
SELECT
    app.id as appId
FROM
    ApplicationInfo app
inner JOIN   LkApplicationStatus las ON app.applicationStatus.id = las.id
inner JOIN   LkApplicationCategory lac ON lac.id = app.category.id
inner JOIN   ApplicationNiceClassification anc ON anc.application.id = app.id
inner JOIN   Classification clas ON anc.classification.id = clas.id
WHERE
    app.applicationNumber IS NOT NULL
     AND (app.applicationNumber = :applicationNumber OR app.idOld = :applicationNumber OR app.grantNumber = :applicationNumber )
     AND (:applicationId IS NULL OR app.id = :applicationId )
     AND app.isDeleted = 0 
     AND lac.saipCode = 'TRADEMARK'
     AND las.code = 'THE_TRADEMARK_IS_REGISTERED'
""")
    TradeMarkInfo getRegisteredTradeMarkInfoByApplicationNumber(@Param("applicationNumber")String applicationNumber,
                                                                @Param("applicationId")Long applicationId );

    @Query(value = "select\n" +
            "app.id as appId,\n" +
            "app.title_ar as appTitleAr,\n" +
            "app.title_en as appTitleEn,\n" +
            "stat.ips_status_desc_ar as statusAr,\n" +
            "stat.ips_status_desc_en as statusEn,\n" +
            "clas.name_ar as classificationAr,\n" +
            "clas.name_en as classificationEn,\n" +
            "tm.word_mark as TmWorkMark,\n" +
            "tm.mark_description MarkDescription,\n" +
            "tm.mark_claiming_color MarkClaimingColor,\n" +
            "lmt.name_ar TmTypeAr,\n" +
            "lmt.name_en TmTypeEn,\n" +
            "apptype.customer_code CustomerCode\n" +
            "from application.applications_info app\n" +
            "left join application.lk_application_status stat on app.application_status_id = stat.id\n" +
            "left join application.application_nice_classifications anc on anc.application_id = app.id \n" +
            "left join application.classifications clas on anc.classification_id  = clas.id\n" +
            "left join application.application_relevant_type art on app.id = art.application_relevant_id\n" +
            "left join application.trademark_details tm on app.id = tm.application_id\n" +
            "left join application.application_relevant_type apptype on apptype.application_info_id = app.id\n" +
            "left join application.lk_mark_types lmt on lmt.id = tm.mark_type_id\n" +
            "where app.id = :id " +
            "And app.is_deleted ='0'" ,nativeQuery = true)
    TradeMarkInfo getTradeMarkByApplicationId(@Param("id") Long id);
    
    @Query(value = "select\n" +
            "lmt.name_ar TmTypeAr,\n" +
            "lmt.name_en TmTypeEn,\n" +
            "tm.application_id appId " +
            "from application.trademark_details tm \n" +
            "left join application.lk_mark_types lmt on lmt.id = tm.mark_type_id\n" +
            "where tm.application_id in (:ids)" +
            "And appId.is_deleted ='0' " ,nativeQuery = true)
    Optional<List<TradeMarkInfo>> getTradeMarkByApplicationIds(@Param("ids") List<Long> applicationIds);

    @Query("SELECT new gov.saip.applicationservice.common.dto.trademark.TrademarkApplicationInfoXmlDataDto(ai.id, ai.email, " +
            "          td.markType , td.nameAr , td.nameEn) " +
            "FROM ApplicationInfo ai " +
            "JOIN TrademarkDetail td ON ai.id = td.applicationId " +
            "WHERE ai.id = :applicationId")
    Optional<TrademarkApplicationInfoXmlDataDto> getApplicationInfoXmlDataDto(@Param("applicationId") Long applicationId);

    @Query("select case when (count(t) > 0)  then true else false end from TrademarkDetail t where t.applicationId = :applicationId and t.tagTypeDesc.code = 'VERBAL'")
    boolean isTrademarkTypeVerbal (@Param("applicationId") Long applicationId);

    @Query("UPDATE TrademarkDetail td SET td.examinerGrantCondition = :examinerGrantCondition WHERE td.applicationId = :applicationId")
    @Modifying
    @Transactional
    Integer updateExaminerGrantCondition (@Param("examinerGrantCondition") String examinerGrantCondition, @Param("applicationId") Long applicationId);

/*
*  @Query(value = "select\n" +
            "app.id as appId,\n" +
            "app.title_ar as appTitleAr,\n" +
            "app.title_en as appTitleEn,\n" +
            "stat.ips_status_desc_ar as statusAr,\n" +
            "stat.ips_status_desc_en as statusEn,\n" +
            "clas.name_ar as classificationAr,\n" +
            "clas.name_en as classificationEn,\n" +
            "tm.word_mark as TmWorkMark,\n" +
            "tm.mark_description MarkDescription,\n" +
            "tm.mark_claiming_color MarkClaimingColor,\n" +
            "lmt.name_ar TmTypeAr,\n" +
            "lmt.name_en TmTypeEn,\n" +
            "apptype.customer_code CustomerCode\n" +
            "from application.applications_info app\n" +
            "left join application.lk_application_status stat on app.application_status_id = stat.id\n" +
            "left join application.application_nice_classifications anc on anc.application_id = app.id \n" +
            "left join application.classifications clas on anc.classification_id  = clas.id\n" +
            "left join application.application_relevant_type art on app.id = art.application_relevant_id\n" +
            "left join application.trademark_details tm on app.id = tm.application_id\n" +
            "left join application.application_relevant_type apptype on apptype.application_info_id = app.id\n" +
            "left join application.lk_mark_types lmt on lmt.id = tm.mark_type_id\n" +
            "where app.id = :id " +
            "And app.is_deleted ='0'" ,nativeQuery = true)*/
    @Query(value = """
     SELECT app.id as appId,
            app.title_ar as titleEn ,
            app.title_en as titleAr,
            app.application_number as applicationNumber,
            app.application_request_number as applicationRequestNumber,
            app.filing_date as filingDate, 
            app.owner_name_ar as ownerNameAr,
            app.owner_name_en as ownerNameEn,
            app.owner_address_ar as ownerAddressAr,
            app.owner_address_en  as ownerAddressEn,
            app.end_of_protection_date as endOfProtectionDate,
            app.created_by_customer_type as createdByCustomerType,
            app.created_by_customer_id as createdByCustomerId,
            app.grant_number as grantNumber,
            app.grant_date as grantDate ,
            stat.id as statusId,
            stat.ips_status_desc_ar_external as statusAr,
            stat.ips_status_desc_en_external as statusEn,
            stat.ips_status_desc_ar  as statusInternalAr,
            stat.ips_status_desc_en  as statusInternalEn,
            stat.code as statusCode,
            lac.id as categoryId,
            lac.application_category_desc_ar as categoryDescAr,
            lac.application_category_desc_en as categoryDescEn,
            lac.saip_code as categoryCode,
            tm.mark_claiming_color as MarkClaimingColor,
            lmt.name_ar as typeNameAr,
            lmt.name_en as typeNameEn,
            lang.name_ar as tagLanguageNameAr,
            lang.name_en as tagLanguageNameEn,
            tagdesc.name_ar  as tagTypeNameAr,
            tagdesc.name_en  as tagTypeNameEn,
            tm.mark_description as markDescription,
            tm.examiner_grant_condition  as examinerGrantCondition,
            tm.name_en as tmNameEn,
            tm.name_ar as tmNameAr
            
    
            
            FROM  application.applications_info app
            JOIN application.lk_application_category lac ON lac.id = app.lk_category_id
            LEFT JOIN application.lk_application_status stat on app.application_status_id = stat.id
            LEFT JOIN application.trademark_details tm on app.id = tm.application_id
            LEFT JOIN application.lk_mark_types lmt on lmt.id = tm.mark_type_id
            LEFT JOIN application.lk_tag_languages lang on lang.id = tm.tag_language_id
            LEFT JOIN application.lk_tag_type_desc tagdesc on tagdesc.id = tm.tag_type_desc_id
            
            WHERE app.id = :applicationId And app.is_deleted ='0'

            """, nativeQuery = true)
    ApplicationTradeMarkProjection getAllDetailsAboutTradeMarkDetailsSummary(@Param("applicationId") Long applicationId);


    @Query(value = """ 
            SELECT new gov.saip.applicationservice.common.dto.trademark.ApplicationTMAttributesDto(
                        ai.id, ai.applicationStatus.code, ai.createdByCustomerId,art.customerCode,ai.applicationNumber,ai.category.saipCode)  
                        FROM ApplicationInfo ai 
                        Left join ai.applicationRelevantTypes art
                         
                   WHERE ai.id = :id and art.type = 'Applicant_MAIN'
            """)
    ApplicationTMAttributesDto findNationalSecurityByAppId(@Param("id") Long id);

    @Query(value = """ 
           SELECT case when Count(tm) > 1 then true else false end FROM ApplicationInfo ai
           Left join TrademarkDetail tm on tm.applicationId=ai.id
""")
    Boolean checkIfApplicationHasTradeMarkDetails(@Param("id") Long id);
}
