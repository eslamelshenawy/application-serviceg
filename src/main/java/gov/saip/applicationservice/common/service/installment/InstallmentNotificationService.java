package gov.saip.applicationservice.common.service.installment;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.model.installment.InstallmentNotification;

public interface InstallmentNotificationService extends BaseService<InstallmentNotification, Long> {

    void resendNotification(Long id, boolean sendAll);
    void resetLastNotification(Long id);
}
