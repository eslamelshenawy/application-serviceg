package gov.saip.applicationservice.common.repository.applicantModifications;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.model.TrademarkApplicationModification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TradeMarkApplicationModificationRepository extends BaseRepository<TrademarkApplicationModification, Long> {
Optional<List<TrademarkApplicationModification>> getByApplicationIdOrderByModificationFilingDateDesc(Long appId);

}
