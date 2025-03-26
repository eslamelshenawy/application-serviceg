package gov.saip.applicationservice.common.dto.pltDurationPaymentInfo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PltDurationPaymentInfoDto {
   private Boolean isAppPaid;
   private Long applicationModificationDays;
   private Long applicationPaymentDays;



}
