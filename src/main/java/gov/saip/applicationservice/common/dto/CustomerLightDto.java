package gov.saip.applicationservice.common.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class CustomerLightDto {
    
    private String userGroup;
    
    private String establishmentId;
    
    private String iqamaNumber;
}
