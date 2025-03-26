package gov.saip.applicationservice.common.dto.agency;

import gov.saip.applicationservice.common.enums.agency.TrademarkAgencyType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TMAgencyRequestDataDto {
    private String requestNumber;
    private TrademarkAgencyType agencyType;
    private String agencyTypeNameAr;
    private String agencyTypeNameEn;

    public TMAgencyRequestDataDto(String requestNumber, TrademarkAgencyType agencyType) {
        this.requestNumber = requestNumber;
        this.agencyType = agencyType;
    }
}
