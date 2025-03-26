package gov.saip.applicationservice.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProcessRequestDto {
    @NotEmpty
    private String processId;
    private Object object;
    private Map<String, Object> variables = new HashMap<>();
    private Long categoryId;

    public void addVariable(String key, Object value) {
        if (getVariables() == null)
            setVariables(new HashMap<>());
        getVariables().put(key, value);
    }


}