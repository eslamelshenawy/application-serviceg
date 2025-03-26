package gov.saip.applicationservice.common.dto.trademark;

import gov.saip.applicationservice.common.model.trademark.LkMarkType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class TrademarkApplicationInfoXmlDataDto {

    private Long applicationId;
    private String email;
    private LkMarkType markType;
    private String nameAr;
    private String nameEn;

    public TrademarkApplicationInfoXmlDataDto(Long applicationId, String email, LkMarkType markType, String nameAr, String nameEn) {
        this.applicationId = applicationId;
        this.email = email;
        this.markType = markType;
        this.nameAr = nameAr;
        this.nameEn = nameEn;
    }

}
