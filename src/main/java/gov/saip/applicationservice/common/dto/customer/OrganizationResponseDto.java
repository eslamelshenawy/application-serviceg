package gov.saip.applicationservice.common.dto.customer;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrganizationResponseDto {

    private String nameAr;

    private String nameEn;

    private String logoAttachmentId;

    private String phoneCountryCode;

    private String phone;

    private String licensedToPractise;

    private String companyNationality;

    private String DNS;
    private AddressResponseDto address;

}
