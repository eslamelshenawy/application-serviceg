package gov.saip.applicationservice.common.dto.applicantModifications;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TradeMarkApplicationModificationDto {

    //    appId
    private Long id;
    private String nameAr;
    private String nameEn;
    private Integer markTypeId;
    private Integer tagTypeDesc;
    private String markDescription;
    private Long appId;
    private Long tagLanguageId;

}
