package gov.saip.applicationservice.common.service.lookup;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.lookup.LKApplicationCategoryDto;
import gov.saip.applicationservice.common.dto.lookup.LKApplicationCategoryLightDto;
import gov.saip.applicationservice.common.model.LkApplicationCategory;

import java.util.List;


public interface LkApplicationCategoryService extends BaseService<LkApplicationCategory, Long> {
    LkApplicationCategory getApplicationCategoryByDescEn(String applicationCategoryDescEn);

    List<LKApplicationCategoryDto> getAll ();

    LkApplicationCategory findBySaipCode(String saipCode);

    List<LKApplicationCategoryDto> findAllActiveCategories ();
    
    public List<LKApplicationCategoryLightDto> getCategoriesByCodes(List<String> codes);
    
    }
