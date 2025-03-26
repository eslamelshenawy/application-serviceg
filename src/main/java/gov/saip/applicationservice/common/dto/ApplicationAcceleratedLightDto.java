package gov.saip.applicationservice.common.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationAcceleratedLightDto implements Serializable {

    private Long id;

    private LocalDateTime createdDate;

}
