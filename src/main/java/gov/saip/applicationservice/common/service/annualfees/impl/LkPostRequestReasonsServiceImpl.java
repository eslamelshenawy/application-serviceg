package gov.saip.applicationservice.common.service.annualfees.impl;

import gov.saip.applicationservice.base.service.BaseLkServiceImpl;
import gov.saip.applicationservice.common.clients.rest.feigns.BPMCallerFeignClient;
import gov.saip.applicationservice.common.clients.rest.feigns.CustomerServiceFeignClient;
import gov.saip.applicationservice.common.mapper.ClassificationMapper;
import gov.saip.applicationservice.common.mapper.DocumentCommentMapper;
import gov.saip.applicationservice.common.mapper.DocumentMapper;
import gov.saip.applicationservice.common.mapper.opposition.OppositionMapper;
import gov.saip.applicationservice.common.model.annual_fees.LkAnnualRequestYears;
import gov.saip.applicationservice.common.model.annual_fees.LkPostRequestReasons;
import gov.saip.applicationservice.common.repository.opposition.OppositionRepository;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.DocumentsService;
import gov.saip.applicationservice.common.service.agency.ApplicationAgentService;
import gov.saip.applicationservice.common.service.annualfees.LkAnnualRequestYearsService;
import gov.saip.applicationservice.common.service.annualfees.LkPostRequestReasonsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class LkPostRequestReasonsServiceImpl extends BaseLkServiceImpl<LkPostRequestReasons, Long> implements LkPostRequestReasonsService {

    private final OppositionRepository oppositionRepository;
    private  final CustomerServiceFeignClient customerServiceFeignClient;
    private final ApplicationAgentService applicationAgentService;
    private final ApplicationInfoService applicationInfoService;
    private final ClassificationMapper classificationMapper;
    private final DocumentMapper documentMapper;
    private final OppositionMapper oppositionMapper;
    private final DocumentsService documentsService;
    private final DocumentCommentMapper documentCommentMapper;
    private final BPMCallerFeignClient bpmCallerFeignClient;





}
