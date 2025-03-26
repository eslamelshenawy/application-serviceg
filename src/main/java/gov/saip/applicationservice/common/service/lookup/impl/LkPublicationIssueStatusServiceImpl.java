package gov.saip.applicationservice.common.service.lookup.impl;

import gov.saip.applicationservice.base.service.BaseLkServiceImpl;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.model.LkApplicationStatus;
import gov.saip.applicationservice.common.model.LkPublicationIssueStatus;
import gov.saip.applicationservice.common.repository.lookup.LkApplicationStatusRepository;
import gov.saip.applicationservice.common.repository.lookup.LkPublicationIssueStatusRepository;
import gov.saip.applicationservice.common.service.lookup.LkApplicationStatusService;
import gov.saip.applicationservice.common.service.lookup.LkPublicationIssueStatusService;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LkPublicationIssueStatusServiceImpl extends BaseLkServiceImpl<LkPublicationIssueStatus, Integer> implements LkPublicationIssueStatusService {

}
