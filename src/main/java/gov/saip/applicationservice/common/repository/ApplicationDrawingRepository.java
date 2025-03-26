package gov.saip.applicationservice.common.repository;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.model.ApplicationDrawing;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationDrawingRepository extends BaseRepository<ApplicationDrawing, Long>{

    @Query("""
        select 
               appDrawing
        from 
             ApplicationDrawing appDrawing
        where appDrawing.applicationInfo.id = :appId
        order by appDrawing.document.id asc

    """)
    List<ApplicationDrawing> getAppDrawing(@Param("appId") Long appId);

    @Query("""
        select 
               appDrawing
        from 
             ApplicationDrawing appDrawing
        where appDrawing.applicationInfo.id = :appId
          and appDrawing.isDefault = true
    """)
    List<ApplicationDrawing> checkMainDrawExists(@Param("appId") Long appId);

    @Modifying
    @Query("delete from ApplicationDrawing appDrawing where appDrawing.document.id = :documentId and appDrawing.applicationInfo.id = :appId")
    void deleteByAppAndDocumentId(@Param("appId") Long appId, @Param("documentId") Long documentId);

    @Modifying
    @Query("delete from ApplicationDrawing appDrawing where appDrawing.document.id in :appId and appDrawing.isDefault=false")
    void deleteById(@Param("appId") List<Long> appId);

}
