package gov.saip.applicationservice.common.service.lookup;

import gov.saip.applicationservice.base.service.BaseLkService;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.model.LkApplicationStatus;
import gov.saip.applicationservice.common.model.LkPublicationIssueStatus;
import org.springframework.stereotype.Service;

@Service
public interface LkPublicationIssueStatusService extends BaseLkService<LkPublicationIssueStatus, Integer> {
}
