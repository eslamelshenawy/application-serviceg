package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.enums.ApplicationTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DocumentsTemplateDto extends BaseDto<Long> implements Serializable {
    private String labelNameEn;
    private String labelNameAr;
    private String fileName;
    private Integer size;
    private String nexuoId;
    private String applicationCategoryDescAr;
    private String applicationCategoryDescEn;
    private String name;
    private ApplicationTypeEnum type;
    private String fileReviewUrl;
    private Date uploadedDate;
    
}
