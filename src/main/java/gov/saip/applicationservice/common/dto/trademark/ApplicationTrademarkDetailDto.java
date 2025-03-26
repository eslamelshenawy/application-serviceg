package gov.saip.applicationservice.common.dto.trademark;


import gov.saip.applicationservice.common.dto.ApplicationSubstantiveExaminationRetrieveDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Setter
@Getter
public class ApplicationTrademarkDetailDto {


    private ApplicationSubstantiveExaminationRetrieveDto application;
    private LkMarkTypeDto markType;
    @NotNull
    private LkTagTypeDescDto tagTypeDesc;
    @NotNull
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
    private  String imageLink;
    private String fileName;

}
