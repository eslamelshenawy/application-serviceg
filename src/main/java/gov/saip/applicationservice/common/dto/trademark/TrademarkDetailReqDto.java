package gov.saip.applicationservice.common.dto.trademark;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Setter
@Getter
@ToString
public class TrademarkDetailReqDto {

    private Long id;
    @NotNull
    private Integer markTypeId;
    @NotNull
    private Integer tagTypeDescId;
    private Integer tagLanguageId;
    private String nameAr;
    private String nameEn;
    private String markClaimingColor;
    private String markDescription;
    private String wordMark;
    private boolean haveExhibition;
    private String  exhibitionInfo;
    private LocalDate exhibitionDate;
    private boolean isExposedPublic;
    private LocalDate exposedDate;
    private String examinerGrantCondition;
}
