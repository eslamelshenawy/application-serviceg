package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.base.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ApplicationWordDto extends BaseDto<Long> {
    private String word;
    private List<String> synonyms;
    private Long applicationId;
}
