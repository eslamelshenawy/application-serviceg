package gov.saip.applicationservice.common.dto.supportService;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SupportServiceHelperInfoDto {
    private String createdByCustomerCode;
    private Long applicationId;
}
