package gov.saip.applicationservice.common.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IpsearchDetailDataDto {
    @JsonProperty("BibliographicData")
    private BibliographicData bibliographicData;
}
