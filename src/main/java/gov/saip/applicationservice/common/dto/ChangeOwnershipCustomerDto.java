package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.enums.ChangeOwnershipTypeEnum;
import gov.saip.applicationservice.common.enums.DocumentTransferTypeEnum;
import gov.saip.applicationservice.common.enums.OwnerShipTransferTypeEnum;
import gov.saip.applicationservice.common.model.ChangeOwnershipCustomer;
import gov.saip.applicationservice.common.model.ChangeOwnershipRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ChangeOwnershipCustomerDto extends BaseDto<Long> {

    private Long customerId;
    private String customerCode;
    private int ownershipPercentage;
    private String customerNameAr;
    private String customerNameEn;


}
