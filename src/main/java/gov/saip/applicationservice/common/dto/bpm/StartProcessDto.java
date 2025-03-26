package gov.saip.applicationservice.common.dto.bpm;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StartProcessDto {

    private String id;
    private String fullNameAr;
    private String fullNameEn;
    private String email;
    private String mobile;
    private String requestTypeCode;
    private String identifier;
    private String applicationCategory;
    private String processName;
    private String requestType;
    private String applicantUserName;
    private String supportServiceCode;
    private String supportServiceTypeCode;
    private String applicationIdColumn;
    private String requestNumber;
    private String applicantCustomerCode;
    private String mainApplicationApplicantCustomerCode;
    private Map<String, Object> variables;

    public void addVariable(String key, Object value) {
        if(getVariables() == null)
        {
            setVariables(new HashMap<>());
        }
        getVariables().put(key, value);
    }
}
