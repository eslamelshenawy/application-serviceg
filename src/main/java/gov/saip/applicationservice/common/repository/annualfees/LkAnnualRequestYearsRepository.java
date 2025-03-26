package gov.saip.applicationservice.common.repository.annualfees;

import gov.saip.applicationservice.base.repository.BaseLkRepository;
import gov.saip.applicationservice.common.model.LkPublicationIssueStatus;
import gov.saip.applicationservice.common.model.annual_fees.LkAnnualRequestYears;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LkAnnualRequestYearsRepository extends BaseLkRepository<LkAnnualRequestYears, Long> {

    @Query("""
        select years from LkAnnualRequestYears years
        order by years.yearsCount asc
    """)
    List<LkAnnualRequestYears> getRemainingYears();
}
