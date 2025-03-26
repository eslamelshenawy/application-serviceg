package gov.saip.applicationservice.common.repository;

import gov.saip.applicationservice.common.enums.ChangeOwnershipTypeEnum;
import gov.saip.applicationservice.common.model.ChangeOwnershipRequest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChangeOwnershipRequestRepository extends SupportServiceRequestRepository<ChangeOwnershipRequest> {

    Optional<ChangeOwnershipTypeEnum> findChangeOwnershipTypeById(Long applicationSupportServiceId);

    @Modifying
    @Query("delete from ChangeOwnershipCustomer coc where coc.changeOwnershipRequest.id = :id")
    void deleteCustomersByRequestId(@Param("id") Long id);
    @Query("select cor.customerId from ChangeOwnershipRequest cor where cor.applicationInfo.id = :id")
    Long getCustomerIdFromChangeOwnerShipByAppId(@Param("id") Long id);
}
