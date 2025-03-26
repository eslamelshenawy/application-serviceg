package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.base.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SimilarTrademarkDto extends BaseDto<Long> {

    private String taskDefinitionKey;
    private String taskInstanceId;
    private String imageLink;
    private String trademarkNumber;
    private String previewLink;
    private long ipsId;
    private long applicationInfoId;

}
