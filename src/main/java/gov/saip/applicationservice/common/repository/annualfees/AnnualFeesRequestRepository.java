package gov.saip.applicationservice.common.repository.annualfees;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.model.annual_fees.AnnualFeesRequest;
import gov.saip.applicationservice.common.model.appeal.AppealCommitteeOpinion;
import gov.saip.applicationservice.common.repository.SupportServiceRequestRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnnualFeesRequestRepository extends SupportServiceRequestRepository<AnnualFeesRequest> {

}
