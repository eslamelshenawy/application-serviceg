package gov.saip.applicationservice.common.dto.trademark;

import gov.saip.applicationservice.common.dto.DocumentDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class TrademarkDetailDto {

    private Long id;
    @NotNull
    private Long applicationId;
    @NotNull
    private LkMarkTypeDto markType;
    @NotNull
    private LkTagTypeDescDto tagTypeDesc;
    private LkTagLanguageDto tagLanguage;
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
    private List<DocumentDto> documents;
    private String examinerGrantCondition;

}
