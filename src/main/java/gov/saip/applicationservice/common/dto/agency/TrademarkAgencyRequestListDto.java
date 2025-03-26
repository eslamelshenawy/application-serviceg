package gov.saip.applicationservice.common.dto.agency;

import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.dto.CustomerSampleInfoDto;
import gov.saip.applicationservice.common.dto.RequestTasksDto;
import gov.saip.applicationservice.common.enums.agency.TrademarkAgencyType;
import gov.saip.applicationservice.report.dto.UserLightDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TrademarkAgencyRequestListDto extends BaseDto<Long> {
    private LkClientTypeDto clientType;
    private String requestNumber;
    private String agencyNumber;
    private LKTrademarkAgencyRequestStatusDto requestStatus;
    private TrademarkAgencyType agencyType;
    private CustomerSampleInfoDto clientInfo;
    private String clientCustomerCode;
    private String processRequestId;
    private RequestTasksDto task;
    private UserLightDto assigneeInfo;

}
