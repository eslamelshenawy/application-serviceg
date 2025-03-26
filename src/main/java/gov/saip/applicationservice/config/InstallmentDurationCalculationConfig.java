package gov.saip.applicationservice.config;

import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.support.CronSequenceGenerator;

import javax.sql.DataSource;

@Configuration
@Slf4j
public class InstallmentDurationCalculationConfig {

//    @Bean
//    @ConditionalOnProperty(name = "installments.test.mode", havingValue = "false")
//    public InstallmentDateCalculator installmentDurationProd() {
//        return new InstallmentDateCalculatorProdImpl();
//    }
//
//    @Bean
//    @ConditionalOnProperty(name = "installments.test.mode", havingValue = "true")
//    public InstallmentDateCalculator installmentDurationTest() {
//        return new InstallmentDateCalculatorTestImpl();
//    }

    @Autowired
    private Environment environment;

    public boolean isLocalProfileActive() {
        String[] activeProfiles = environment.getActiveProfiles();
        if (activeProfiles.length > 0) {
            String activeProfile = activeProfiles[0];
            return  "local".equals(activeProfile);
        }

        return false;
    }

    @Bean
    public LockProvider lockProvider(DataSource dataSource) {
        return new JdbcTemplateLockProvider(dataSource);
    }



//    @Autowired
//    private ApplicationInstallmentConfigRepository applicationInstallmentConfigRepository;

    @Value("${scheduler.installment.cron}")
    private String installmentJobConfig;

    @Value("${installments.test.mode}")
    private Boolean installmentTestMode;

    @Bean
    public String getInstallmentJobExpressionBean() {
//        String config = applicationInstallmentConfigRepository.findById(1L).get().getCreatedByUser();
//        boolean isProductionModeActive = (config == null || config.isEmpty() || config.isBlank() ||
//                !validateCronExpression(config) || isLocalProfileActive());

        String[] activeProfiles = environment.getActiveProfiles();
        for (String s : activeProfiles) {
            log.info("this is the active profiles ==>  " + s);
        }

        log.info("installment job expression is ==> " + installmentJobConfig);
        return installmentJobConfig; //isProductionModeActive ? installmentJobConfig : config;
    }

    public static boolean validateCronExpression(String cronExpression) {
        try {
            new CronSequenceGenerator(cronExpression);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}