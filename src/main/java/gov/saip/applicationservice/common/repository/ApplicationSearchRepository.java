package gov.saip.applicationservice.common.repository;

import gov.saip.applicationservice.common.dto.ApplicationSearchSimilarsDto;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.ApplicationSearch;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApplicationSearchRepository extends SupportServiceRequestRepository<ApplicationSearch>{




    @Query("""
        select new gov.saip.applicationservice.common.dto.ApplicationSearchSimilarsDto(
            ass.brandNameAr,
            ass.brandNameEn,
            ass.filingNumber,
            ass.status,
            ass.filingDate
        ) 
        from ApplicationSearchSimilars ass 
        join ass.applicationSearch aps 
        where aps.id = :applicationSearchId
        """)
    List<ApplicationSearchSimilarsDto> getSavedApplicationSimilars(@Param("applicationSearchId")Long applicationSearchId);
}
