package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.base.service.BaseService;

import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.SupportingEvidenceDto;
import gov.saip.applicationservice.common.model.SupportingEvidence;

import java.util.List;


public interface SupportingEvidenceService extends BaseService<SupportingEvidence, Long> {

    public void deleteBySupportEvIdId(Long appId);

    public void updateSupportingEvidence(SupportingEvidenceDto supportingEvidence);

    PaginationDto<List<SupportingEvidenceDto>> getSupportingEvidenceForApplicationsInfo(Long appId, Integer page, Integer limit);

}
