package gov.saip.applicationservice.common.service.industrial;



import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.DocumentDto;
import gov.saip.applicationservice.common.dto.industrial.ApplicationIndustrialDesignDetailDto;
import gov.saip.applicationservice.common.dto.industrial.IndustrialDesignDetailDto;
import gov.saip.applicationservice.common.dto.industrial.IndustrialDesignDetailReqDto;
import gov.saip.applicationservice.common.dto.industrial.PublicationDetailsDto;
import gov.saip.applicationservice.common.dto.reports.ReportRequestDto;
import gov.saip.applicationservice.common.enums.DocumentTypeEnum;
import gov.saip.applicationservice.common.model.industrial.IndustrialDesignDetail;
import org.springframework.core.io.ByteArrayResource;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IndustrialDesignDetailService extends BaseService<IndustrialDesignDetail, Long> {

    Long create(IndustrialDesignDetailReqDto req, Long applicationId);

    IndustrialDesignDetail findByApplicationId(Long applicationId);
    IndustrialDesignDetail getByApplicationId(Long applicationId);

    IndustrialDesignDetailDto findDtoByApplicationId(Long applicationId);

    IndustrialDesignDetailDto findDtoById(Long id);

    Optional<IndustrialDesignDetail> findIndustrialDesignById(Long id);


    IndustrialDesignDetailDto findDocumentFromIndustrialDesignDetail(IndustrialDesignDetailDto industrialDesignDetailDto);

    public void removeDeletedSample(IndustrialDesignDetailDto result);

    ApplicationIndustrialDesignDetailDto getApplicationIndustrialDesignDetails(Long id);

    ByteArrayResource getApplicationInfoXml(Long applicationId);

    PublicationDetailsDto getPublicationDetails(Long applicationId, Long applicationPublicationId);
    
    void generateUploadSaveXmlForApplication(Long applicationId, String documentType);

     List<DocumentDto> generateJasperPdf(ReportRequestDto dto, String reportName, DocumentTypeEnum documentType) throws IOException;

}
