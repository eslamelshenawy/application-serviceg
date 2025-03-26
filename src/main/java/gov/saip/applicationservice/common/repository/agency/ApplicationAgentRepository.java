package gov.saip.applicationservice.common.repository.agency;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.dto.ChangeAgentReportDto;
import gov.saip.applicationservice.common.dto.KeyValueDto;
import gov.saip.applicationservice.common.enums.ApplicationAgentStatus;
import gov.saip.applicationservice.common.model.agency.ApplicationAgent;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationAgentRepository extends BaseRepository<ApplicationAgent, Long>{

    @Query("select appAgent.customerId from ApplicationAgent appAgent " +
            "join appAgent.application app where app.id = :appId and appAgent.status = :status ")
    Long getAgentIdByAppIdAndStatus(@Param("appId") Long appId, @Param("status") ApplicationAgentStatus status);

    @Query("select distinct appAgent.customerId from ApplicationAgent appAgent join appAgent.application app where app.id = :appId ")
    List<Long> getAllApplicationAgentsByAppId(@Param("appId") Long appId);


    @Query("select appAgent.customerId from ApplicationAgent appAgent join appAgent.application app join app.applicationRelevantTypes relavant where relavant.customerCode  = :customerCode and relavant.type = 'Applicant_MAIN' ")
    List<Long> getAllCustomerAgentsByCustomerCode(@Param("customerCode")String customerCode);

    @Modifying
    @Query("update ApplicationAgent ag set ag.status = :newStatus where  ag.application.id = :applicationId and ag.id != :applicationAgentId and ag.status = :oldStatus")
    void deActivateOtherActiveAgents(@Param("applicationId")Long applicationId, @Param("applicationAgentId")Long applicationAgentId, @Param("newStatus") ApplicationAgentStatus newStatus,  @Param("oldStatus") ApplicationAgentStatus oldStatus );

    @Modifying
    @Query("update ApplicationAgent ag set ag.status = :newStatus where  ag.application.id in (:appIds) and ag.id not in  (:newApplicationAgentIds) and ag.status = :oldStatus ")
    void deActivateApplicationAgentsByAgentAndApps(@Param("appIds")List<Long> appIds, @Param("newApplicationAgentIds")List<Long> newApplicationAgentIds, @Param("newStatus") ApplicationAgentStatus newStatus,  @Param("oldStatus") ApplicationAgentStatus oldStatus );

    boolean existsByApplicationIdAndCustomerId(Long applicationId, Long customerId);

    @Query("select appAgent.customerId as key, count(distinct appAgent.application) as value  " +
            "from ApplicationAgent appAgent join appAgent.application app join app.applicationRelevantTypes relevant " +
            "join app.applicationStatus appStatus " +
            "where relevant.customerCode = :customerCode and relevant.type = 'Applicant_MAIN' " +
            " and (:status is null  or appAgent.status = :status) " +
            "and appStatus.code not in (:notEligibleStatus)" +
            "group by appAgent.customerId")
 List<KeyValueDto<Long, Long>> getCustomerAgentsAndCounts(@Param("customerCode")String customerCode, @Param("status") ApplicationAgentStatus status, @Param("notEligibleStatus") List<String> notEligibleStatus);

    @Query("select appAgent.id from ApplicationAgent appAgent " +
            "join appAgent.application app " +
            "join app.applicationRelevantTypes relevant " +
            "where relevant.customerCode = :customerCode and relevant.type = 'Applicant_MAIN'  and  appAgent.status = :status and appAgent.customerId = :agentId ")
    List<Long> getApplicationAgentIdsByAgentAndUserIdAndStatus(@Param("agentId") Long agentId,@Param("customerCode") String customerCode, @Param("status") ApplicationAgentStatus status);

    @Modifying
    @Query("update ApplicationAgent ag set ag.status = :newStatus where  ag.application.id in ( :applications ) and ag.status = :oldStatus")
    void deleteAgentsByAppIds(@Param("applications")List<Long> applications, @Param("newStatus") ApplicationAgentStatus newStatus,  @Param("oldStatus") ApplicationAgentStatus oldStatus );
    @Modifying
    @Query("update ApplicationAgent ag set ag.status = :newStatus where  ag.id in ( :applicationAgentIds ) and ag.status = :oldStatus")
    void delteAgentsByIds(@Param("applicationAgentIds")List<Long> applicationAgentIds, @Param("newStatus") ApplicationAgentStatus newStatus,  @Param("oldStatus") ApplicationAgentStatus oldStatus );


    @Query("select app.id from ApplicationAgent appAgent " +
            "join appAgent.application app " +
            "join app.applicationRelevantTypes relevant " +
            "where relevant.customerCode = :customerCode and relevant.type = 'Applicant_MAIN'  and  appAgent.status = :status and appAgent.customerId = :agentId ")
    List<Long> getApplicationIdsByAgentAndUserIds(@Param("agentId") Long agentId,@Param("customerCode") String customerCode, @Param("status") ApplicationAgentStatus status);


    @Query("select appAgent from ApplicationAgent appAgent join appAgent.application app where app.id = :appId and appAgent.status = :status ")
    ApplicationAgent getCurrentActiveApplicationAgent(@Param("appId") Long appId, @Param("status") ApplicationAgentStatus status);


    @Query("SELECT NEW gov.saip.applicationservice.common.dto.ChangeAgentReportDto(" +
            "a.id, ai.applicationNumber, a.modifiedDate , a.customerId) " +
            "FROM ApplicationAgent a " +
            "JOIN a.application ai " +
            "WHERE a.status = 'CHANGED' " +
            "AND (a.application.id, a.modifiedDate) IN (" +
            "   SELECT a2.application.id, MAX(a2.modifiedDate) " +
            "   FROM ApplicationAgent a2 " +
            "   WHERE a2.status = 'CHANGED' " +
            "   GROUP BY a2.application.id" +
            ") " +
            "ORDER BY a.application.id, a.modifiedDate DESC")
    List<ChangeAgentReportDto> findChangedAgentsWithApplicationNumber();






    @Query("SELECT aa " +
            "FROM ApplicationAgent aa " +
            "WHERE aa.status = 'ACTIVE' AND aa.application.id = :applicationId")
    ApplicationAgent findActiveAgentsByApplicationId(Long applicationId);
    
    @Query("SELECT aa " +
            "FROM ApplicationAgent aa " +
            "WHERE aa.status = :status AND aa.application.id in (:appIds)")
    List<ApplicationAgent> getApplicationsCustomersIdsByAppIdsAndStatus(@Param("appIds") List<Long> appIds, @Param("status") ApplicationAgentStatus status);
}
