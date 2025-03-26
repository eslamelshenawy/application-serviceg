package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.certs.*;
import gov.saip.applicationservice.common.dto.veena.CertificateRequestDto;
import gov.saip.applicationservice.common.model.CertificateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(uses = {DocumentMapper.class})
public interface CertificateRequestMapper extends BaseMapper<CertificateRequest, CertificateRequestDto> {


    @Override
    @Mapping(source = "applicationInfoId", target = "applicationInfo.id")
    CertificateRequest unMap(CertificateRequestDto certificateRequestDto);
    
    CertificateRequestViewDto mapToCertificateRequestViewDto(CertificateRequestProjection entity);
    
    List<CertificateRequestViewDto> mapToCertificateRequestViewDto(List<CertificateRequestProjection> entity);
    
    PreviousCertificateRequestDto mapToCertificateRequestDto(CertificateRequestProjection entity);
    
    List<PreviousCertificateRequestDto> mapToCertificateRequestDto(List<CertificateRequestProjection> entity);

    List<CertificateRequestProjectionDto> mapToBaseCertificateRequestProjectionDto(List<CertificateRequestProjection> entity);

    
}
