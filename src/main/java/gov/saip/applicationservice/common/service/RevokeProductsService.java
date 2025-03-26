package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.common.dto.RevokeProductsDto;
import gov.saip.applicationservice.common.model.RevokeProducts;

import java.util.List;

public interface RevokeProductsService extends SupportServiceRequestService<RevokeProducts> {
    List<Long> getRevokedSubClassificationsIdByApplicationId(Long appId);
    
    List<Long> getRevokedSubClassificationsIdByApplicationIdAndSupportServiceId(Long appId, Long supportServiceId);
    
    void deleteApplicationRevokedProductsByRevokeProductId(Long revokeProductId);
    RevokeProductsDto findRevokeProductById(Long id);

}
