package gov.saip.applicationservice.modules.plantvarieties.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PriorityDataSectionPlantVarietyDto extends PlantVarietyDto {
    private Long id;
    private Boolean marketingInKsa;
    private LocalDateTime firstFillingDateInKsa;
    private String marketingInKsaNote;
    private Boolean marketingOutKsa;
    private LocalDateTime firstFillingDateOutKsa;
    private String marketingOutKsaNote;

    public PriorityDataSectionPlantVarietyDto(Long id,Boolean marketingInKsa, LocalDateTime firstFillingDateInKsa, String marketingInKsaNote,
                                              Boolean marketingOutKsa, LocalDateTime firstFillingDateOutKsa, String marketingOutKsaNote) {
        this.id=id;
        this.marketingInKsa = marketingInKsa;
        this.firstFillingDateInKsa = firstFillingDateInKsa;
        this.marketingInKsaNote = marketingInKsaNote;
        this.marketingOutKsa = marketingOutKsa;
        this.firstFillingDateOutKsa = firstFillingDateOutKsa;
        this.marketingOutKsaNote = marketingOutKsaNote;
    }
}
