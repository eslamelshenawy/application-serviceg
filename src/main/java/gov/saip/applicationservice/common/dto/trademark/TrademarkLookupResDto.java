package gov.saip.applicationservice.common.dto.trademark;

import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrademarkLookupResDto {

    private List<LkMarkTypeDto> markTypes;
    private List<LkTagTypeDescDto> tagTypeDesc;
    private List<LkTagLanguageDto> tagLanguages;
}
