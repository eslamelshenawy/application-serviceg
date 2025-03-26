package gov.saip.applicationservice.common.dto.certs;

import gov.saip.applicationservice.common.dto.DocumentDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Builder
public class CertRequestDetailsDto {

    private String lastActionAr;
    private String lastActionEn;

   private  DocumentDto documents;



}
