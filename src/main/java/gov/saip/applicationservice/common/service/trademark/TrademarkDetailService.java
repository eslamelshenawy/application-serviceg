package gov.saip.applicationservice.common.service.trademark;


import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.trademark.*;
import gov.saip.applicationservice.common.enums.ThirdPartyRoute;
import gov.saip.applicationservice.common.model.trademark.TrademarkDetail;
import gov.saip.applicationservice.common.projection.TradeMarkInfo;
import org.springframework.core.io.ByteArrayResource;

import java.util.List;

public interface TrademarkDetailService extends BaseService<TrademarkDetail, Long> {
    
    Long create(TrademarkDetailReqDto req, Long applicationId);
    
    TrademarkDetail findByApplicationId(Long applicationId);
    TrademarkDetail getByApplicationId(Long applicationId);
    Long getTMByApplicationId(Long applicationId);
    TrademarkDetailDto findDtoByApplicationId(Long applicationId);
    
    TrademarkDetailDto findDtoById(Long id);
    
    List<TradeMarkInfo> getTradeMarKApplicaionInfo(String applicantCode);

    List<TradeMarkInfo> getRegisteredTradeMarkInfoByApplicantCode(String applicantCode);
    
    TradeMarkInfo getTradeMarkByApplicationId(Long id);
    
    ApplicationTrademarkDetailDto getApplicationTrademarkDetails(Long applicationId);
    
    List<ApplicationTrademarkDetailLightDto> getApplicationTrademarkDetails(List<Long> applicationIds);
    PublicationApplicationTrademarkDetailDto getPublicationTradeMarkDetails(Long applicationId, Long applicationPublicationId);

    Object findSuspciondetails(String applicationNumber);

    ByteArrayResource getApplicationInfoXml(Long applicationId);
    
    void generateUploadSaveXmlForApplication(Long applicationId, String documentType);
    
    
    public TradeMarkLightDto getTradeMarkLightDetails(Long appId);
    
    public boolean isImage(Long appId);

    boolean isTrademarkTypeVerbal(Long applicationId);

    Integer update(TradeMarkLightDto request, Long applicationId);
    public ApplicationTrademarkDetailSummaryDto getTradeMarkApplicationDetails(Long appId);
    public TradeMarkThirdPartyDto getThirdPartyIntegrationResults(Long appId, String route);
    ApplicationTradeMarkListClassificationsDto getApplicantsAndClassifications(Long appId);
}
