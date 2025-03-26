package gov.saip.applicationservice.common.repository;

import gov.saip.applicationservice.common.model.RevokeLicenceRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RevokeLicenceRequestRepository extends SupportServiceRequestRepository<RevokeLicenceRequest>{

    @Query("""
            select req.licenceRequest.customerId from RevokeLicenceRequest req where req.id = :id 
           """)
    Long findLicenceCustomerId(Long id);

    @Query("""
            select req.licenceRequest.id from RevokeLicenceRequest req where req.id = :id 
           """)
    Long findLicenceIdByRevokeLicenseId(Long id);

}
