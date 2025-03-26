package gov.saip.applicationservice.common.dto.industrial;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class IndustrialDesignDetailReqDto {

    private Long id;
    private String explanationAr;
    private String explanationEn;
    private String usageAr;
    private String usageEn;
//    private boolean secret;
    private boolean haveExhibition;
    private String  exhibitionInfo;
    private LocalDate exhibitionDate;

    private boolean haveRevealedToPublic;
    private boolean viaMyself;
    private LocalDate detectionDate;

}
