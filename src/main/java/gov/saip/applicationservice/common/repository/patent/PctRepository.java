package gov.saip.applicationservice.common.repository.patent;


import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.dto.patent.PatentFillingDateDto;
import gov.saip.applicationservice.common.model.patent.Pct;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface PctRepository extends BaseRepository<Pct, Long> {

       Pct findByPatentDetailsId(Long patentDetailsId);

       @Query("select pct from Pct pct " +
               " join pct.patentDetails det" +
               " where det.applicationId = :applicationId")
       Optional<Pct> findPctByApplicationId(@Param("applicationId") Long applicationId);


       @Query("""
               SELECT
                new gov.saip.applicationservice.common.dto.patent.PatentFillingDateDto(
                 pct.internationalPublicationDate
                ,app.filingDate) 
               FROM ApplicationInfo app
               LEFT JOIN  PatentDetails pt ON app.id = pt.applicationId
                              LEFT JOIN Pct pct  ON pt.id =pct.patentDetails.id
                               WHERE app.id = :applicationId
                                
               """)
       Optional<PatentFillingDateDto> findApplicationFilinggDateAndPctPublicationDate(@Param("applicationId") Long applicationId);

       @Query("SELECT CASE WHEN COUNT(pct) > 0 THEN true ELSE false END FROM Pct pct WHERE pct.petitionNumber = :petitionNumber")
       boolean validatePetitionNumber(@Param("petitionNumber") String petitionNumber);

       @Query("select CASE WHEN COUNT(pct) > 0 THEN true ELSE false END from Pct pct " +
               " join pct.patentDetails det" +
               " where det.applicationId = :applicationId")
       boolean validatePCT(@Param("applicationId") Long applicationId);


       @Query("""
              select pct.filingDateGr from Pct pct
              join pct.patentDetails pat
              join ApplicationInfo ai on ai.id = pat.applicationId
              where ai.applicationNumber = :applicationNumber
       """)
       LocalDate getFilingDateByApplicationNumber(@Param("applicationNumber") String applicationNumber);

}

