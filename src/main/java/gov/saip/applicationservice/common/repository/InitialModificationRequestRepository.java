package gov.saip.applicationservice.common.repository;

import gov.saip.applicationservice.common.enums.SupportedServiceCode;
import gov.saip.applicationservice.common.model.InitialModificationRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InitialModificationRequestRepository extends SupportServiceRequestRepository<InitialModificationRequest> {

    @Query("select i.lkSupportServiceType.code from InitialModificationRequest i where i.applicationInfo.id = :appId AND i.paymentStatus= 'PAID'")
    List<SupportedServiceCode> findApplicationSupportedServiceType(@Param("appId") long appId, Pageable pageable);

    @Query("select i.id from InitialModificationRequest i where i.applicationInfo.id = :appId AND i.requestStatus.code = 'UNDER_PROCEDURE'")
    Long findUnderProcedureByApplicationId(@Param("appId") long appId);

}
