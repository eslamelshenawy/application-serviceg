package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.dto.customer.ApplicationAgentSummaryDto;
import gov.saip.applicationservice.common.dto.lookup.LKApplicationCategoryDto;
import gov.saip.applicationservice.common.dto.trademark.TradeMarkLightDto;
import gov.saip.applicationservice.common.enums.ApplicantType;
import gov.saip.applicationservice.common.enums.LicencePurposeEnum;
import gov.saip.applicationservice.common.enums.LicenceTypeEnum;
import gov.saip.applicationservice.common.enums.SupportedServiceCode;
import gov.saip.applicationservice.common.model.LKSupportServiceRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LicenceRequestApplicationSummaryDto extends BaseDto<Long> {

    private  ApplicationInfoSummaryDto applicationInfoSummaryDto;
    private  LicenceRequestDto licenceRequestDto;

}
