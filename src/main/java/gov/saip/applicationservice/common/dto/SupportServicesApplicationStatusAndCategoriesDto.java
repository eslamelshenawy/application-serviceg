package gov.saip.applicationservice.common.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class SupportServicesApplicationStatusAndCategoriesDto {
    
   Map<String,List<String> > applicationCategoryStatus;




}
