package gov.saip.applicationservice.common.repository;

import gov.saip.applicationservice.base.repository.BaseLkRepository;
import gov.saip.applicationservice.common.enums.SupportServiceRequestStatusEnum;
import gov.saip.applicationservice.common.model.LKSupportServiceRequestStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LKSupportServiceRequestStatusRepository extends BaseLkRepository<LKSupportServiceRequestStatus, Integer> {

    @Query(value = "SELECT lk.id FROM LKSupportServiceRequestStatus lk WHERE lk.code = :supportServiceRequestStatus")
    Integer findIdByCode(@Param(value = "supportServiceRequestStatus")String supportServiceRequestStatus);

    @Query(value = "SELECT lk FROM LKSupportServiceRequestStatus lk WHERE lk.code = :code")
    LKSupportServiceRequestStatus getStatusByCode(@Param(value = "code")String code);

    LKSupportServiceRequestStatus getStatusByCodeAndNameEn(@Param(value = "code")String code, @Param(value = "nameEn")String nameEn);
}
