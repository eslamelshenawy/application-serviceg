package gov.saip.applicationservice.common.repository;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.model.Document;
import gov.saip.applicationservice.common.model.SupportServicesTypeDecisions;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SupportServicesTypeDecisionsRepository extends BaseRepository<SupportServicesTypeDecisions , Long> {

        @Query("""
       SELECT sstd
       FROM SupportServicesTypeDecisions sstd
       JOIN fetch sstd.applicationSupportServicesType ast
       JOIN fetch ast.lkSupportServices lkss
       WHERE
       sstd.id = (
              SELECT max(sstd2.id)
              FROM SupportServicesTypeDecisions sstd2
              JOIN sstd2.applicationSupportServicesType ast2
              where ast2.id = :serviceId
              and (
              (sstd2.customers is null and sstd2.role is null) or
              sstd2.customers like concat('%', :customerCode, ',%')
              )
       )
       """)
       SupportServicesTypeDecisions getLastDecisionForCurrentCustomer(@Param("serviceId") Long serviceId, @Param("customerCode") String customerCode);


       @Query("""
       SELECT sstd
       FROM SupportServicesTypeDecisions sstd
       JOIN sstd.applicationSupportServicesType ast
       JOIN ast.lkSupportServices lkss
       WHERE
       sstd.id = (
              SELECT max(sstd2.id)
              FROM SupportServicesTypeDecisions sstd2
              JOIN sstd2.applicationSupportServicesType ast2
              where ast2.id = :serviceId
              and (
              (sstd2.customers is null and sstd2.role is null) or
              sstd2.role in (:roles)
              )
       )
       """)
       SupportServicesTypeDecisions getLastDecisionForCurrentUser(@Param("serviceId") Long serviceId, @Param("roles") List<String> roles);

       SupportServicesTypeDecisions findFirstByApplicationSupportServicesTypeIdOrderByCreatedDateDesc(Long serviceID);

}
