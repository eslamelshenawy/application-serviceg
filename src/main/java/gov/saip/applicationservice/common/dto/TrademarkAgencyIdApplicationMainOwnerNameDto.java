package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.common.model.agency.TrademarkAgencyRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class TrademarkAgencyIdApplicationMainOwnerNameDto {
    private Long id;
    private String applicationMainOwner;
}
