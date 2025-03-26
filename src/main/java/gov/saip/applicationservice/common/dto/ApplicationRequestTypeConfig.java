package gov.saip.applicationservice.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationRequestTypeConfig {

    Long appId;

    String configValueExpression;

    Long configValue;
}
