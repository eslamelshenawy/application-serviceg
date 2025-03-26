package gov.saip.applicationservice.common.dto;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LkStepDto  implements  Serializable {
    private Long id;
    private String code;
}
