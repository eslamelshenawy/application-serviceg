package gov.saip.applicationservice.common.repository.installment;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import gov.saip.applicationservice.common.model.installment.ApplicationInstallmentConfig;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

public interface ApplicationInstallmentConfigRepository extends BaseRepository<ApplicationInstallmentConfig, Long> {

    @Transactional
    @Modifying
    @Query("update ApplicationInstallmentConfig config set config.lastRunningDate = :time where config.applicationCategory = :category")
    void updateLastRunningDate(@Param("category") ApplicationCategoryEnum category, @Param("time")  LocalDateTime time);

    ApplicationInstallmentConfig findByApplicationCategory(@Param("category") ApplicationCategoryEnum category);
}
