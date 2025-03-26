package gov.saip.applicationservice.common.dto.bpm;

import gov.saip.applicationservice.common.enums.RequestTypeEnum;
import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompleteTaskRequestDto {


    private Map<String,Object> variables ;
    private List<String> comments = new ArrayList<>();
    private String notes ;
    private String rejectReason;
    private String taskDefinitionKey;
    private RequestTypeEnum requestTypeEnum;

    public void addVariableToVarMap(String key, Object value) {
        if (variables == null) {
            variables = new HashMap<>();
        }

        Map<String, Object> var = new HashMap<>();
        var.put("value", value);
        variables.put(key, var);
    }
}
