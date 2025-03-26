package gov.saip.applicationservice.common.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
public class PctIValidateDto implements Serializable {

    private Boolean valid;

    public PctIValidateDto(Boolean valid, String pctNumber) {
        this.valid = valid;
        this.pctNumber = pctNumber;
    }

    private String pctNumber;

}
