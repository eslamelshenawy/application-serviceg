package gov.saip.applicationservice.report.repository;

import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.report.dto.PatentLicenseJasperDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatentLicenseRepository extends CrudRepository<ApplicationInfo, Long> {

    @Query("SELECT new gov.saip.applicationservice.report.dto.PatentLicenseJasperDto(ai.id, ai.applicationNumber, " +
            "          ai.titleEn, ai.titleAr, ai.createdDate, ai.summeryNotes, ai.fullSummeryNotes, " +
            "          p.pctApplicationNo, p.filingDateGr, p.publishNo, p.internationalPublicationDate, " +
            "          c.nameAr, ai.filingDate) " +
            "FROM ApplicationInfo ai " +
            "LEFT JOIN PatentDetails pd ON ai.id = pd.applicationId " +
            "LEFT JOIN Pct p ON pd.id  = p.patentDetails.id " +
            "LEFT JOIN ApplicationNiceClassification anc ON ai.id = anc.application.id " +
            "LEFT JOIN Classification c ON anc.classification.id = c.id " +
            "WHERE (ai.id = :applicationId OR ai.idOld = :applicantionId) " +
            "GROUP BY ai.id, ai.applicationNumber, ai.titleEn, ai.titleAr, ai.createdDate, ai.summeryNotes, " +
            "         ai.fullSummeryNotes, p.pctApplicationNo, p.filingDateGr, p.publishNo, " +
            "         p.internationalPublicationDate, c.nameAr, ai.filingDate")
    List<PatentLicenseJasperDto> getPatentLicenseJasperDtoByApplicationId(@Param("applicationId") Long applicationId);

}
