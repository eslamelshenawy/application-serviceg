package gov.saip.applicationservice.common.repository.patent;


import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.model.patent.PatentAttributeChangeLog;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatentAttributeChangeLogRepository extends BaseRepository<PatentAttributeChangeLog, Long> {


    @Query("""
        select log from PatentAttributeChangeLog log
        where
        log.patentDetails.id = :patentId and log.attributeName = :name
        and log.id = (
        select max(log2.id) from PatentAttributeChangeLog  log2 where log2.patentDetails.id = :patentId and log2.attributeName = :name
        )
    """)
    Optional<PatentAttributeChangeLog> getMostRecentAttributeByPatentIdAndName(@Param("name") String name, @Param("patentId") Long patentId);

    @Query("SELECT log FROM PatentAttributeChangeLog log where log.patentDetails.id = :patentId ")
    List<PatentAttributeChangeLog> findAllByPatentId(@Param("patentId") Long patentId);
    @Query("""
    SELECT log FROM PatentAttributeChangeLog log where log.patentDetails.id = :patentId
    and log.version = (SELECT max(l.version) FROM PatentAttributeChangeLog l where l.patentDetails.id = :patentId and l.attributeName = log.attributeName)
    """)
    List<PatentAttributeChangeLog> findLatestByPatentId(@Param("patentId") Long patentId);

    @Query("""
        select log from PatentAttributeChangeLog log
        where
        log.patentDetails.id = :patentId and log.attributeName in (:names)
        and log.id = (
        select max(log2.id) from PatentAttributeChangeLog  log2 where log2.patentDetails.id = :patentId 
        and log2.attributeName = log.attributeName and log2.attributeName in (:names)
        )
    """)
    List<PatentAttributeChangeLog> getMostRecentAttributeByPatentIdAndNames(@Param("names") List<String> names, @Param("patentId") Long patentId);

    @Query(value = """            
            SELECT log.attributeValue
            FROM  PatentAttributeChangeLog log
            join log.patentDetails pd
            WHERE
            log.attributeName = :attributeName
            and
            pd.applicationId = :applicationId
            and log.version = (select max(log2.version) from PatentAttributeChangeLog log2 
             join log2.patentDetails pd2 
             where pd2.applicationId = :applicationId and  log2.attributeName = :attributeName )
            order by
            log.version desc
            """ )
    String getAttributeValueByApplicationId(@Param("applicationId")Long applicationId, @Param("attributeName") String attributeName);


    @Modifying
    @Query(value = """            
            delete from PatentAttributeChangeLog log
            where log.patentDetails.id = :id and log.attributeName not in (:updatedAttributes)
            """ )
    void removeMissingAttributes(@Param("updatedAttributes") List<String> updatedAttributes, @Param("id") Long id);
}