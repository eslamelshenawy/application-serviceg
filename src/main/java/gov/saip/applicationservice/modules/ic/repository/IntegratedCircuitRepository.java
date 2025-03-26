package gov.saip.applicationservice.modules.ic.repository;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.modules.ic.dto.IntegratedCircuitListDto;
import gov.saip.applicationservice.modules.ic.dto.IntegratedCircuitRequestDto;
import gov.saip.applicationservice.modules.ic.model.IntegratedCircuit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface IntegratedCircuitRepository extends BaseRepository<IntegratedCircuit, Long> {

    @Query("select c from IntegratedCircuit  c where c.id = :id ")
    Optional<IntegratedCircuit> getIntegratedCircuitById(@Param("id") Long id);

    @Query("""
            select
            new gov.saip.applicationservice.modules.ic.dto.IntegratedCircuitRequestDto(ic.id, ai.applicationNumber, ai.id, ai.titleAr, ai.titleEn,  
            ic.designDescription, ic.designDate, ic.commercialExploited, ic.commercialExploitationDate, ai.applicationStatus.ipsStatusDescEnExternal, 
            ai.applicationStatus.ipsStatusDescArExternal, ai.applicationStatus.code, ic.countryId, ic.approvedNameAr, ic.approvedNameEn, ai.createdDate, ai.createdByCustomerType)
            from IntegratedCircuit ic
            left join ApplicationInfo ai on ai.id = ic.application.id
            where ai.id = :appId
            """)
    IntegratedCircuitRequestDto getIntegratedCircuitAppInfo(@Param("appId") Long appId);

    @Query(value = """
            SELECT distinct new gov.saip.applicationservice.modules.ic.dto.IntegratedCircuitListDto(ic.id, ai.applicationNumber, ai.applicationRequestNumber, 
            ai.id, ai.titleEn, ai.titleAr, ai.filingDate, ai.applicationStatus.ipsStatusDescEnExternal, ai.applicationStatus.ipsStatusDescArExternal, 
            ai.applicationStatus.code, ic.designDescription, ic.designDate, ic.commercialExploited, ic.commercialExploitationDate, ic.countryId, ic.createdDate,
            ic.approvedNameAr, ic.approvedNameEn)
            FROM IntegratedCircuit ic
            join ic.application ai
            JOIN ai.applicationCustomers ac 
            JOIN ai.applicationStatus  stat
            WHERE (:customerId is null or ac.customerId = :customerId or :customerCode = (SELECT a.customerCode FROM ApplicationRelevantType a 
                                        join a.applicationInfo info
                                        where 
                                        info.id = ai.id  AND a.type = 'Applicant_MAIN'))
            AND (:query is null
                OR UPPER(ai.applicationNumber) LIKE CONCAT('%', UPPER(:query), '%')
                OR UPPER(ai.titleAr) LIKE CONCAT('%', UPPER(:query), '%')
                OR UPPER(ai.titleEn) LIKE CONCAT('%', UPPER(:query), '%'))
            AND (:status is null or stat.code = :status)
        """
    )
    Page<IntegratedCircuitListDto> getIntegratedCircuitsApplicationsFiltering(@Param("query") String query, @Param("status") String status, @Param("customerCode") String customerCode,
                                                                              @Param("customerId") Long customerId, Pageable pageable);


    @Modifying
    @Query("""
        update IntegratedCircuit  c set c.approvedNameEn = :applicationNameEn, c.approvedNameAr = :applicationNameAr where c.application.id = :applicationId
    """)
    void updateIntegratedCircuitApprovedNames(@Param("applicationId") Long applicationId, @Param("applicationNameAr") String applicationNameAr, @Param("applicationNameEn") String applicationNameEn);

    @Query(" select case when (count(c) > 0)  then true else false end from IntegratedCircuit  c where c.application.id = :appId ")
    boolean checkApplicationHasIntegratedCircuit(@Param("appId") Long appId);

    @Query(" select c from IntegratedCircuit  c where c.application.id = :appId ")
    Optional<IntegratedCircuit> getIntegratedCircuitByApplicationId(@Param("appId") Long appId);

    @Modifying
    @Query("""
        update IntegratedCircuit  ic set ic.isDeleted='1' where ic.application.id = :appId
    """)
    void deleteByAppId(@Param("appId") Long appId);

    @Modifying
    @Query("""
            update IntegratedCircuit ic
            set ic.firstAssignationDate = :firstAssignationDate
            where ic.application.id = :appId     
    """)
    void updateFirstAssignationDateByApplicationId(@Param("appId") Long appId, @Param("firstAssignationDate") LocalDateTime firstAssignationDate);

    @Modifying
    @Query("""
            update IntegratedCircuit ic
            set ic.firstAssignationDate = null
            where ic.application.id = :appId     
    """)
    void deleteFirstAssignationDateValueByApplicationId(@Param("appId") Long appId);
}
