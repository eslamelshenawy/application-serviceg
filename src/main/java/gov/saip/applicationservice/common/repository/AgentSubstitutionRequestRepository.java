package gov.saip.applicationservice.common.repository;

import gov.saip.applicationservice.common.enums.SupportServicePaymentStatus;
import gov.saip.applicationservice.common.enums.SupportServiceType;
import gov.saip.applicationservice.common.model.AgentSubstitutionRequest;
import gov.saip.applicationservice.common.model.ApplicationSupportServicesType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AgentSubstitutionRequestRepository extends SupportServiceRequestRepository<AgentSubstitutionRequest> {


    @Query("select req from AgentSubstitutionRequest req " +
            "join req.lkSupportServiceType serviceType " +
            "join req.applicationInfo app " +
            "where app.id = :appId and serviceType.type = :supportType and req.paymentStatus = :appRequestStatus")
    AgentSubstitutionRequest getPendingRequestByApplicationId(@Param("appId") Long appId, @Param("supportType")  SupportServiceType supportType, @Param("appRequestStatus") SupportServicePaymentStatus appRequestStatus);

}
