package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.dto.SupportServicesTypeDecisionsDto;
import gov.saip.applicationservice.common.enums.ApplicationCustomerType;
import gov.saip.applicationservice.common.enums.SupportServiceRequestStatusEnum;
import gov.saip.applicationservice.common.mapper.SupportServiceTypeDecisionsMapper;
import gov.saip.applicationservice.common.model.Document;
import gov.saip.applicationservice.common.model.SupportServicesTypeDecisions;
import gov.saip.applicationservice.common.repository.DocumentRepository;
import gov.saip.applicationservice.common.repository.SupportServiceRequestRepository;
import gov.saip.applicationservice.common.repository.SupportServicesTypeDecisionsRepository;
import gov.saip.applicationservice.common.service.LKSupportServiceRequestStatusService;
import gov.saip.applicationservice.common.service.SupportServiceTypeDecisionsService;
import gov.saip.applicationservice.common.service.agency.SupportServiceCustomerService;
import gov.saip.applicationservice.common.service.supportService.SupportServiceStatusChangeLogService;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.filters.JwtUtility;
import gov.saip.applicationservice.util.Constants;
import gov.saip.applicationservice.util.Utilities;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;


@Service
@AllArgsConstructor
public class SupportServiceTypeDecisionsServiceImpl extends BaseServiceImpl<SupportServicesTypeDecisions , Long> implements SupportServiceTypeDecisionsService {

    private final static String SERVICE_OWNER_CUSTOMERS = "SERVICE_OWNER_CUSTOMERS";
    private final static String SERVICE_OTHER_CUSTOMERS = "SERVICE_OTHER_CUSTOMERS";
    private static final String PDF_EXTENSION = "pdf";

    private final SupportServiceRequestRepository supportServiceRequestRepository;
    private final SupportServicesTypeDecisionsRepository supportServicesTypeDecisionsRepository;
    public final LKSupportServiceRequestStatusService lKSupportServiceRequestStatusService;
    private final SupportServiceTypeDecisionsMapper supportServiceTypeDecisionsMapper;
    private final SupportServiceCustomerService supportServiceCustomerService;
    private final DocumentRepository documentRepository;
    private final JwtUtility jwtUtility;
    private final SupportServiceStatusChangeLogService supportServiceStatusChangeLogService;


    @Override
    protected BaseRepository<SupportServicesTypeDecisions, Long> getRepository() {
        return supportServicesTypeDecisionsRepository;
    }
    @Override
    @Transactional
    public SupportServicesTypeDecisions insert(SupportServicesTypeDecisions entity) {
        changeSupportServiceStatusWithDeicision(entity);
        entity.setCustomers(getCustomersCodes(entity));
        getCustomersCodes(entity);
        return super.insert(entity);
    }

    private void changeSupportServiceStatusWithDeicision(SupportServicesTypeDecisions entity) {
        if (entity.isSkipChangeStatus()) {
            insertLogAndUpdateStatus(entity, null);
            return;
        }

        SupportServiceRequestStatusEnum decisionStatus = entity.getDecision().getStatus();
        if (entity.getApplicationSupportServicesType().getRequestStatus().getCode() != null) {
            decisionStatus = SupportServiceRequestStatusEnum.valueOf(entity.getApplicationSupportServicesType().getRequestStatus().getCode());
        }
        if (decisionStatus != null) {
            insertLogAndUpdateStatus(entity, decisionStatus.name());
        }
    }

    private void insertLogAndUpdateStatus(SupportServicesTypeDecisions entity, String status) {
        if (entity.getSupportServiceStatusChangeLogDto() != null) {
            entity.getSupportServiceStatusChangeLogDto().setNewStatusCode(status);
            supportServiceStatusChangeLogService.insert(entity.getSupportServiceStatusChangeLogDto());
        }
    }

    private String getCustomersCodes(SupportServicesTypeDecisions entity) {
        if (entity.getCustomers() != null && entity.getCustomers().equals(SERVICE_OWNER_CUSTOMERS)) {
            Map<ApplicationCustomerType, String> serviceCustomerCodes = supportServiceCustomerService.getServiceCustomerCodes(entity.getApplicationSupportServicesType().getId());
            return mapToCommaSeparatedString(serviceCustomerCodes);
        }
        return null;
    }


    private static String mapToCommaSeparatedString(Map<ApplicationCustomerType, String> serviceCustomerCodes) {
        StringBuilder result = new StringBuilder();
        for (ApplicationCustomerType key : serviceCustomerCodes.keySet()) {
            result.append(serviceCustomerCodes.get(key)).append(",");
        }
        return result.toString();
    }
    @Override
    @Transactional
    public SupportServicesTypeDecisions insertWithNoOtherApplyingLogic(SupportServicesTypeDecisionsDto supportServicesTypeDecisionsDto) {
        SupportServicesTypeDecisions entity = supportServiceTypeDecisionsMapper.unMap(supportServicesTypeDecisionsDto);
        return super.insert(entity);
    }


    @Override
    public SupportServicesTypeDecisions getLastDecisionsForLoggedIn(Long serviceId) {
        SupportServicesTypeDecisions supportServicesTypeDecisions;
        if (Utilities.isExternal()) {
            supportServicesTypeDecisions = supportServicesTypeDecisionsRepository.getLastDecisionForCurrentCustomer(serviceId, Utilities.getCustomerCodeFromHeaders());
        } else {
            supportServicesTypeDecisions = supportServicesTypeDecisionsRepository.getLastDecisionForCurrentUser(serviceId, jwtUtility.getInternalUserRoles());
        }
        return supportServicesTypeDecisions == null ? null : convertDocumentToPDF(supportServicesTypeDecisions);
    }

    private SupportServicesTypeDecisions convertDocumentToPDF(SupportServicesTypeDecisions supportServicesTypeDecisions){
        if(Objects.nonNull(supportServicesTypeDecisions.getDocument()))
            supportServicesTypeDecisions.getDocument().setFileName(Utilities.replaceFileExtension(supportServicesTypeDecisions.getDocument().getFileName(), PDF_EXTENSION));
        return supportServicesTypeDecisions;
    }

    @Override
    public void addDocumentToDecision(Long serviceId, Long documentId) {
        Document documentEntity = documentRepository.findById(documentId).orElseThrow(()->new BusinessException(Constants.ErrorKeys.EXCEPTION_RECORD_NOT_FOUND, HttpStatus.NOT_FOUND));
        SupportServicesTypeDecisions decision = supportServicesTypeDecisionsRepository.findFirstByApplicationSupportServicesTypeIdOrderByCreatedDateDesc(serviceId);
        decision.setDocument(documentEntity);
        supportServicesTypeDecisionsRepository.save(decision);
    }
}
