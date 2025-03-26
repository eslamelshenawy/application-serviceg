package gov.saip.applicationservice.common.dto.supportService;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationPriorityModifyRequestLightDto {

    private Long applicationId;

    private Long supportServiceId;

    private String applicationCustomerType;
}
