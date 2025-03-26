package gov.saip.applicationservice.common.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor

@Builder
public class AppCharacteristics {
    private String categoryCode;
    private String applicationNumber;

    public AppCharacteristics(String categoryCode, String applicationNumber) {
        this.categoryCode = categoryCode;
        this.applicationNumber = applicationNumber;
    }
}
