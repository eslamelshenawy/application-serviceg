package gov.saip.applicationservice.common.dto.installment;

import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.enums.installment.InstallmentNotificationStatus;
import gov.saip.applicationservice.common.model.Document;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApplicationInstallmentNotificationDto extends BaseDto<Long> {

    private Document document;
    private InstallmentNotificationStatus notificationStatus;

}
