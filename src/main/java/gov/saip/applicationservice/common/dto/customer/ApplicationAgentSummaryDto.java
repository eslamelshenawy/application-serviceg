package gov.saip.applicationservice.common.dto.customer;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApplicationAgentSummaryDto {
    private AddressResponseDto address;
    private String nameAr;
    private String nameEn;
    private String poaDocumentUrl;
    private String identifier;
    private String code;
}
