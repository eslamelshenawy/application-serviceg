package gov.saip.applicationservice.common.service.applicantModifications;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.applicantModifications.TradeMarkApplicationModificationDto;
import gov.saip.applicationservice.common.model.TrademarkApplicationModification;

import java.util.Map;

public interface TradeMarkApplicationModificationService extends BaseService<TrademarkApplicationModification, Long> {

    TrademarkApplicationModification addApplicantModifications(TradeMarkApplicationModificationDto dto, Long applicationId);
    Map<String, Object> getAuditModificationsDifferences(Long appId);
     void persistNewTradeMarkModificationsAndCompleteApplicantTask(Long appId);

}
