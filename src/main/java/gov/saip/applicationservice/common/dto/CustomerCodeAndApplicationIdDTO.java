package gov.saip.applicationservice.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CustomerCodeAndApplicationIdDTO {
    private Long applicationId;
    private String customerCode;
}
