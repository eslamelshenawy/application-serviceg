package gov.saip.applicationservice.common.repository.agency;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.dto.TrademarkAgencyIdApplicationMainOwnerNameDto;
import gov.saip.applicationservice.common.dto.agency.TMAgencyRequestDataDto;
import gov.saip.applicationservice.common.enums.SupportServiceType;
import gov.saip.applicationservice.common.enums.agency.TrademarkAgencyType;
import gov.saip.applicationservice.common.model.agency.TrademarkAgencyRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TrademarkAgencyRequestRepository extends BaseRepository<TrademarkAgencyRequest, Long> {

    @Query("""
                select req.requestNumber from TrademarkAgencyRequest req
                where req.id = (select max(inReq.id) from TrademarkAgencyRequest inReq where inReq.requestNumber IS NOT NULL)
            """)
    String getLastRequestNumber();

    @Query("""
                select tar from TrademarkAgencyRequest tar
                where
                (:query is null or tar.requestNumber = :query or tar.agencyNumber = :query or tar.agentCustomerCode = :query or tar.clientCustomerCode = :query) and
                (:customerCode is null or tar.agentCustomerCode = :customerCode) and
                tar.requestStatus.id in (:statusList)
            """)
    Page<TrademarkAgencyRequest> filterAndListAgenciesRequests(@Param("statusList") List<Integer> statusList,
                                                               @Param("query") String query,
                                                               @Param("customerCode") String customerCode,
                                                               @Param("pageable") Pageable pageable);

    @Modifying
    @Query("""
                update TrademarkAgencyRequest tar set tar.checkerUsername = :checkerUsername where tar.id = :id
            """)
    void updateCheckerUsername(@Param("id") Long id, @Param("checkerUsername") String checkerUsername);

    @Query("""
                select tar.processRequestId from TrademarkAgencyRequest tar where tar.id = :id
            """)
    Optional<Long> getProcessRequestIdById(@Param("id") Long id);

    @Query("""
                select tar.processRequestId from TrademarkAgencyRequest tar 
                where
                    (cast(:fromFilingDate as date) is null or DATE(tar.createdDate) >= :fromFilingDate) and
                    (cast(:toFilingDate as date) is null or DATE(tar.createdDate) <= :toFilingDate) and
                    (:requestNumber is null or tar.requestNumber = :requestNumber)

            """)
    List<Long> getProcessRequestIdByAgencyRequestNumber(@Param("requestNumber") String requestNumber,
                                                        @Param("fromFilingDate") LocalDate fromFilingDate,
                                                        @Param("toFilingDate") LocalDate toFilingDate);


    @Query("""
                select req from TrademarkAgencyRequest req
                where req.clientCustomerCode = :clientCustomerCode
                and req.agentCustomerCode = :agentCustomerCode
                and req.requestStatus.code in (:trademarkAgencyStatusCode)
                and req.agencyType in (:trademarkAgencyType)
                and (:applicationId is null or req.application.id = :applicationId)
            """)
    List<TrademarkAgencyRequest> getByClientCodeAndStatusAndAgencyType(
            @Param("clientCustomerCode") String clientCustomerCode,
            @Param("agentCustomerCode") String agentCustomerCode,
            @Param("applicationId") Long applicationId,
            @Param("trademarkAgencyStatusCode") List<String> trademarkAgencyStatusCode,
            @Param("trademarkAgencyType") List<TrademarkAgencyType> trademarkAgencyType);

    @Query("""
                select req from TrademarkAgencyRequest req
                where req.clientCustomerCode = :clientCustomerCode
                and req.agentCustomerCode = :agentCustomerCode
                and req.requestStatus.code = :trademarkAgencyStatusCode
                and req.agencyType = :trademarkAgencyType
            """)
    List<TrademarkAgencyRequest> getByClientCodeAndStatusAndAgencyTypeForInvestigation(
            @Param("clientCustomerCode") String clientCustomerCode,
            @Param("agentCustomerCode") String agentCustomerCode,
            @Param("trademarkAgencyStatusCode") String trademarkAgencyStatusCode,
            @Param("trademarkAgencyType") TrademarkAgencyType trademarkAgencyType);

    @Query(value = """
             select COUNT(*) from application.trademark_agency_requests where agent_customer_code = :agentCustomerCode
            	and extract (year from CREATED_DATE) = extract (year from CURRENT_DATE)
            """, nativeQuery = true)
    Long getRequestsCountThisYearPerAgent(@Param("agentCustomerCode") String agentCustomerCode);

    @Query("select tar.agentCustomerCode from TrademarkAgencyRequest tar where tar.requestNumber = :requestNumber")
    String getAgentCustomerCodeByRequestNumber(@Param("requestNumber") String requestNumber);


    @Query("""
            select req.clientCustomerCode from TrademarkAgencyRequest req
            where req.agentCustomerCode = :agentCode
            and (:type is null or req.agencyType = :type)
            and (:clientCode is null or req.clientCustomerCode = :clientCode)
            and req.requestStatus.id = :statusId
            and req.endAgency > CURRENT_DATE
            and req.startAgency <= CURRENT_DATE
            """)
    List<String> getActiveAgenciesClientCodesByAgentCode(@Param("agentCode") String agentCode,
                                                         @Param("clientCode") String clientCode,
                                                         @Param("statusId") Integer statusId,
                                                         @Param("type") TrademarkAgencyType type);

    @Query(value = """
                      select req from TrademarkAgencyRequest req
                      where req.agentCustomerCode = :customerCode
                      and (:type is null or req.agencyType = :type)
                      and req.requestStatus.id = :statusId
                      and req.endAgency > CURRENT_DATE
                      and req.startAgency <= CURRENT_DATE
            """
    )
    Page<TrademarkAgencyRequest> getChangeOwnerByAgentCustomerCode(
            @Param("customerCode") String customerCode,
            @Param("type") TrademarkAgencyType type,
            @Param("statusId") Integer statusId,
            @Param("pageable") Pageable pageable
    );


    @Query("""
            select req from TrademarkAgencyRequest req
            where req.agentCustomerCode = :agentCode
            and (:type is null or req.agencyType = :type)
            and (:clientCode is null or req.clientCustomerCode = :clientCode)
            and req.requestStatus.id = :statusId
            and req.endAgency > CURRENT_DATE
            and req.startAgency <= CURRENT_DATE
            order by req.id desc
            """)
    List<TrademarkAgencyRequest> getActiveAgenciesByAgentCode(@Param("agentCode") String agentCode,
                                                              @Param("clientCode") String clientCode,
                                                              @Param("statusId") Integer statusId,
                                                              @Param("type") TrademarkAgencyType type);


    @Query("""
            select case when (count(req) > 0)  then true else false end 
            from TrademarkAgencyRequest req
            join req.supportServices lkser
            where req.agentCustomerCode = :agentCode
            and req.clientCustomerCode = :customerCode
            and req.requestStatus.id = :statusId
            and req.application.id = :applicationId
            and lkser.code = :serviceCode
            and (cast(:agencyEndDate as date) is null or req.endAgency > :agencyEndDate)
            and (cast(:agencyEndDate as date) is null or req.startAgency <= :agencyEndDate)
            """)
    boolean isAgentHasPermissionForApplicationSupportService(@Param("agentCode") String agentCode,
                                                             @Param("customerCode") String customerCode,
                                                             @Param("statusId") Integer statusId,
                                                             @Param("serviceCode") SupportServiceType serviceCode,
                                                             @Param("applicationId") Long applicationId,
                                                             @Param("agencyEndDate") LocalDate agencyEndDate);


    @Query("""
            select req
            from TrademarkAgencyRequest req
            join req.supportServices lkser
            where req.agentCustomerCode = :agentCode
            and (:customerCode is null or req.clientCustomerCode = :customerCode)
            and req.requestStatus.id = :statusId
            and req.application.id = :applicationId
            and lkser.code = :serviceCode
            and (cast(:agencyEndDate as date) is null or req.endAgency > :agencyEndDate)
            and (cast(:agencyEndDate as date) is null or req.startAgency <= :agencyEndDate)
            order by req.id desc
            """)
    List<TrademarkAgencyRequest> getAgencyForApplicationSupportService(@Param("agentCode") String agentCode,
                                                                       @Param("customerCode") String customerCode,
                                                                       @Param("statusId") Integer statusId,
                                                                       @Param("serviceCode") SupportServiceType serviceCode,
                                                                       @Param("applicationId") Long applicationId,
                                                                       @Param("agencyEndDate") LocalDate agencyEndDate);


    @Query("""
            select  case when (count(req) > 0)  then true else false end
            from TrademarkAgencyRequest req
            join req.supportServices lkser
            where req.agentCustomerCode = :agentCode
            and req.agencyType = 'SUPPORT_SERVICES'
            and req.clientCustomerCode = :clientCode
            and req.requestStatus.code = 'ACCEPTED'
            and req.endAgency > CURRENT_DATE
            and req.startAgency <= CURRENT_DATE
            and lkser.code = :serviceCode
            and req.requestNumber = :agencyRequestNumber
            """)
    boolean agencyExistAndActiveForSupportService(@Param("agentCode") String agentCode,
                                                  @Param("clientCode") String clientCode,
                                                  @Param("agencyRequestNumber") String agencyRequestNumber,
                                                  @Param("serviceCode") SupportServiceType serviceCode);


    @Query("""
            select req from TrademarkAgencyRequest req
            where req.agentCustomerCode = :agentCode
            and req.agencyType = :type
            and req.requestStatus.id = :statusId
            and req.requestNumber = :agencyRequestNumber
            and req.application.id = :applicationId
            and req.endAgency > CURRENT_DATE
            and req.startAgency <= CURRENT_DATE
            order by req.id desc
            """)
    List<TrademarkAgencyRequest> getActiveAgentAgencyRequestOnApplication(@Param("agentCode") String agentCode,
                                                                          @Param("agencyRequestNumber") String agencyRequestNumber,
                                                                          @Param("applicationId") Long applicationId,
                                                                          @Param("statusId") Integer statusId,
                                                                          @Param("type") TrademarkAgencyType type);


    @Query("""
            select  case when (count(req) > 0)  then true else false end
            from TrademarkAgencyRequest req
            where req.agentCustomerCode = :agentCode
            and req.agencyType = 'CHANGE_OWNERSHIP'
            and req.clientCustomerCode = :clientCode
            and req.requestStatus.code = 'ACCEPTED'
            and req.endAgency > CURRENT_DATE
            and req.startAgency <= CURRENT_DATE
            and req.agencyNumber = :requestNumber
            """)
    boolean agencyExistAndActiveForChangeOwnership(@Param("agentCode") String agentCode,
                                                   @Param("clientCode") String clientCode,
                                                   @Param("requestNumber") String requestNumber);


    @Query("""
            select  req.clientCustomerCode
            from TrademarkAgencyRequest req
            where req.requestNumber = :requestNumber
            """)
    String getAgencyCustomerCodeByRequestNumber(@Param("requestNumber") String requestNumber);


    @Query("""
            select  case when (count(req) > 0)  then true else false end
            from TrademarkAgencyRequest req
            where req.agentCustomerCode = :agentCode
            and req.agencyType = 'CHANGE_OWNERSHIP'
            and req.requestStatus.code = 'ACCEPTED'
            and req.endAgency > CURRENT_DATE
            and req.startAgency <= CURRENT_DATE
            and req.requestNumber = :requestNumber
            """)
    boolean agencyExistAndActiveForChangeOwnership(@Param("agentCode") String agentCode,
                                                   @Param("requestNumber") String requestNumber);

    @Query("""
            select  case when (count(req) > 0)  then true else false end
            from TrademarkAgencyRequest req
            join req.supportServices lkser
            where req.agentCustomerCode = :agentCode
            and req.agencyType = 'SUPPORT_SERVICES'
            and req.requestStatus.code = 'ACCEPTED'
            and req.endAgency > CURRENT_DATE
            and req.startAgency <= CURRENT_DATE
            and lkser.code = :serviceCode
            and req.requestNumber = :requestNumber
            """)
    boolean agencyExistAndActiveForSupportService(@Param("agentCode") String agentCode,
                                                  @Param("requestNumber") String requestNumber,
                                                  @Param("serviceCode") SupportServiceType serviceCode);

    @Query("""
            select  req.agentCustomerCode
            from TrademarkAgencyRequest req
            join req.supportServices lkser
            where req.clientCustomerCode = :customerCode
            and req.agencyType = 'SUPPORT_SERVICES'
            and req.requestStatus.code = 'ACCEPTED'
            and req.endAgency > CURRENT_DATE
            and req.startAgency <= CURRENT_DATE
            and lkser.code = :serviceCode
            and req.application.id = :applicationId
            """)
    String getAgentCodeForAgency(@Param("serviceCode") SupportServiceType serviceCode, @Param("customerCode") String customerCode, @Param("applicationId") Long applicationId);

    @Query("""
            select  req.requestNumber
            from TrademarkAgencyRequest req
            where req.id = :id
            """)
    String getTrademarkAgencyRequestNumberById(@Param("id") Long id);


    @Query("""
            select tar.agentCustomerCode
            from TrademarkAgencyRequest tar
            join tar.supportServices lss
            join tar.application ai
            where tar.agencyType = 'SUPPORT_SERVICES' and ai.id = :applicationId and lss.code  = :serviceCode
            """)
    List<String> getAgentCodesByApplicationIdAndSupportServiceCode(@Param("applicationId") Long applicationId,
                                                                   @Param("serviceCode") SupportServiceType serviceCode);

    @Query("""
            select new gov.saip.applicationservice.common.dto.agency.TMAgencyRequestDataDto(req.requestNumber, req.agencyType)
            from TrademarkAgencyRequest req
            where req.id = :id
            """)
    TMAgencyRequestDataDto getTMAgencyRequestDataById(@Param("id") Long id);

    @Modifying
    @Query("UPDATE TrademarkAgencyRequest tar SET tar.requestStatus.id = (select id from LKTrademarkAgencyRequestStatus where code = 'EXPIRED' ) WHERE tar.endAgency < CURRENT_DATE and " +
            "tar.requestStatus.id != (select id from LKTrademarkAgencyRequestStatus where code = 'REJECTED' ) ")
    void updateTrademarkAgencyToExpiredStatus();

    @Query("select tar.processRequestId from TrademarkAgencyRequest tar where tar.requestStatus.id in (select id from LKTrademarkAgencyRequestStatus where code != 'EXPIRED' ) and " +
            "tar.requestStatus.id in (select id from LKTrademarkAgencyRequestStatus where code != 'REJECTED' ) and tar.endAgency < CURRENT_DATE")
    List<Long> getExpiredTrademarkAgencyProcessRequestIds();

    @Query("""
            select new gov.saip.applicationservice.common.dto.TrademarkAgencyIdApplicationMainOwnerNameDto(req.id,  art.customerCode)
            from TrademarkAgencyRequest req
            join ApplicationRelevantType art on  req.application.id = art.applicationInfo.id
            where art.type = 'Applicant_MAIN' and art.isDeleted = 0 and
            req.id in :ids and req.application is not null
            """)
    List<TrademarkAgencyIdApplicationMainOwnerNameDto> getTMAgenciesApplicationMainOwner(@Param("ids") List<Long> ids);
}
