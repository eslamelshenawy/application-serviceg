package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.base.dto.BaseDto;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

@Data
public class LKDocumentTypeDto extends BaseDto<Long> implements Serializable {


    private String name;

    private String nameAr;

    private String code;

    private String description;

    private String category;

    private Long docOrder;

}
