package gov.saip.applicationservice.common.repository.supportService;

import gov.saip.applicationservice.common.model.supportService.ApplicationPriorityRequest;
import gov.saip.applicationservice.common.repository.SupportServiceRequestRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ApplicationPriorityRequestRepository extends SupportServiceRequestRepository<ApplicationPriorityRequest> {

   @Query(value = " SELECT document_id FROM application.application_priority_request WHERE id = :id ", nativeQuery = true)
   Long findDocumentIdByPriorityRequestId(@Param("id") Long id);

   @Query(value = " update application.application_priority_request set document_id = null where id = :id ", nativeQuery = true)
   @Modifying
   @Transactional
   void deletePriorityDocument(@Param("id") Long id);

   @Query("select count(a) > 0 from ApplicationPriorityRequest a where a.applicationInfo.id = :appId and a.modifyType ='EDIT' ")
   boolean applicationSupportServicesTypePeriorityEditOnlyExists(@Param("appId") Long appId);
}