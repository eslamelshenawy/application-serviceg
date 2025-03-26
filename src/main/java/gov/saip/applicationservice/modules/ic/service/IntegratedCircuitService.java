package gov.saip.applicationservice.modules.ic.service;

import gov.saip.applicationservice.common.dto.ApplicationNameDto;
import gov.saip.applicationservice.common.dto.DocumentDto;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.reports.ReportRequestDto;
import gov.saip.applicationservice.common.enums.ApplicationStatusEnum;
import gov.saip.applicationservice.common.enums.DocumentTypeEnum;
import gov.saip.applicationservice.modules.common.service.BaseApplicationInfoService;
import gov.saip.applicationservice.modules.ic.dto.IntegratedCircuitDto;
import gov.saip.applicationservice.modules.ic.dto.IntegratedCircuitListDto;
import gov.saip.applicationservice.modules.ic.dto.IntegratedCircuitRequestDto;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

public interface IntegratedCircuitService extends BaseApplicationInfoService {

    PaginationDto<List<IntegratedCircuitListDto>> listIntegratedCircuitsApplication(Integer page, Integer limit, String query, String status, Sort.Direction sortDirection);
    Long updateWithApplication (IntegratedCircuitDto integratedCircuitDto);

    IntegratedCircuitRequestDto getIntegratedCircuitsApplicationInfo(Long applicationId);

    void updateIntegratedCircuitApprovedNames(ApplicationNameDto applicationNameDto);
    void deleteByAppId(Long appId);

    List<DocumentDto> generateJasperPdf(ReportRequestDto dto, String reportName, DocumentTypeEnum documentType) throws IOException;

    void mergeFrontAndBackShapesDocumentsByApplicationId(Long applicationId);

    void updateStatusAndSetFirstAssignationDateByApplicationId(Long applicationId, ApplicationStatusEnum code);

    void updateFirstAssignationDateByApplicationId(Long applicationId);

    void deleteInternalUserDateValuesByApplicationId(Long applicationId);
}
