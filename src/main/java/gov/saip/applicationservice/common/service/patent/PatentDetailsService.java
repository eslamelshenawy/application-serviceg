package gov.saip.applicationservice.common.service.patent;


import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApplicationNotesReqDto;
import gov.saip.applicationservice.common.dto.DocumentDto;
import gov.saip.applicationservice.common.dto.patent.*;
import gov.saip.applicationservice.common.dto.pltDurationPaymentInfo.PltDurationPaymentInfoDto;
import gov.saip.applicationservice.common.dto.reports.ReportRequestDto;
import gov.saip.applicationservice.common.enums.ReportsType;
import gov.saip.applicationservice.common.model.patent.PatentDetails;
import org.springframework.core.io.ByteArrayResource;

import java.io.IOException;
import java.util.List;

public interface PatentDetailsService extends BaseService<PatentDetails, Long> {

    public PatentDetails savePatent(PatentDetailsRequestDto dto, Long id);

    public PatentDetails findByPatentId(Long id);

    public ApplicationPatentDetailsDto retrieveSubstantiveExaminationDetails(Long id);
   
    PublicationDetailDto getPublicationDetails(Long applicationId, Long applicationPublicationId,String publicationCode);

    ByteArrayResource getApplicationInfoXml(Long applicationId);
    
    void generateUploadSaveXmlForApplication(Long applicationId, String documentType);
    List<DocumentDto> generateJasperPdf(ReportRequestDto dto, String documentType) throws IOException;
    PatentDetails findPatentDetailsByApplicationId(Long id);

    PatentDetailDtoWithChangeLog findDtoByApplicationId(Long id);

    PatentDetailDtoWithChangeLog findDtoByApplicationIdWithOnlyLatestVersionOfLogs(Long id);
    PltDurationPaymentInfoDto getApplicationPaymentInfoAndDurationConfigured(Long appId, String mainRequestType);

  DocumentDto generateReportByDocumentType(Long applicationId, String documentType) throws IOException;
    ByteArrayResource generatePatentExaminerReports(Long applicationId,String documentType) throws IOException;
    List<DocumentDto> generateJasperPdfForSupportServicesWithNoApplicationId(ReportRequestDto dto, String documentType);
    void setPatentOppositionFlag(Long appId);
    void setPatentOppositionFlagWithTrue(Long appId);

    Boolean getPatentOpposition(Long appId);
    Long applicantOppositionForCorrectionInvitations(ApplicationNotesReqDto applicationNotesReqDto);
    String getPTLastApplicantOppositionForInvitationCorrection(Long appId, String taskKey);



}
