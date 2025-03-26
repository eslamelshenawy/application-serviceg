//package gov.saip.applicationservice.modules.common.repository;
//
//import gov.saip.applicationservice.base.repository.BaseRepository;
//import gov.saip.applicationservice.common.model.ApplicationInfo;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//@Repository
////@JaversSpringDataAuditable
//public interface BaseApplicationInfoRepository extends BaseRepository<ApplicationInfo, Long> {
//    @Modifying
//    @Query("""
//        update ApplicationInfo ai set ai.processRequestId = :processRequestId where ai.id = :id
//    """)
//    void updateApplicationWithProcessRequestId(@Param("id") Long id, @Param("processRequestId") Long processRequestId);
//}
//
