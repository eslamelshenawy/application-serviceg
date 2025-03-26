package gov.saip.applicationservice.common.service.installment.impl;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import gov.saip.applicationservice.common.model.installment.ApplicationInstallmentConfig;
import gov.saip.applicationservice.common.repository.installment.ApplicationInstallmentConfigRepository;
import gov.saip.applicationservice.common.service.installment.ApplicationInstallmentConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationInstallmentConfigServiceImpl extends BaseServiceImpl<ApplicationInstallmentConfig, Long> implements ApplicationInstallmentConfigService {

    private final ApplicationInstallmentConfigRepository installmentConfigRepository;
    @Override
    protected BaseRepository<ApplicationInstallmentConfig, Long> getRepository() {
        return installmentConfigRepository;
    }
    @Override
    public ApplicationInstallmentConfig findByApplicationCategory(String category) {
        return installmentConfigRepository.findByApplicationCategory(ApplicationCategoryEnum.valueOf(category));
    }


    @Override
    public ApplicationInstallmentConfig update(ApplicationInstallmentConfig applicationInstallmentConfig){
        ApplicationInstallmentConfig currentApplicationInstallmentConfig = getReferenceById(applicationInstallmentConfig.getId());
        currentApplicationInstallmentConfig.setPaymentIntervalYears(applicationInstallmentConfig.getPaymentIntervalYears());
        currentApplicationInstallmentConfig.setPaymentDuration(applicationInstallmentConfig.getPaymentDuration());
        currentApplicationInstallmentConfig.setGraceDuration(applicationInstallmentConfig.getGraceDuration());
        currentApplicationInstallmentConfig.setOpenRenewalDuration(applicationInstallmentConfig.getOpenRenewalDuration());
        return super.update(currentApplicationInstallmentConfig);
    }

}
