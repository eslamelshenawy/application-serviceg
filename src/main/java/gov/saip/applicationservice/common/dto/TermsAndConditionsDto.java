package gov.saip.applicationservice.common.dto;

import lombok.*;

import java.io.Serializable;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TermsAndConditionsDto implements Serializable {
    private Long id;
    private String titleAr;
    private String titleEn;
    private String bodyAr;
    private String bodyEn;

    private String link;

}
