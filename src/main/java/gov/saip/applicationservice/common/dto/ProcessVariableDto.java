package gov.saip.applicationservice.common.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProcessVariableDto {
    String type;
    Object value;

    public String getStringValue() {
      return String.valueOf(value);
    }
}
