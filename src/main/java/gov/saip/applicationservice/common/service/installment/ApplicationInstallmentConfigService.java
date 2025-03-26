package gov.saip.applicationservice.common.service.installment;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.model.installment.ApplicationInstallmentConfig;

public interface ApplicationInstallmentConfigService extends BaseService<ApplicationInstallmentConfig, Long> {

    ApplicationInstallmentConfig findByApplicationCategory(String category);
}
