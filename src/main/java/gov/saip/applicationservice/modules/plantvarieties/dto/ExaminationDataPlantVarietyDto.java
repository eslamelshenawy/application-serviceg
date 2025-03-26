package gov.saip.applicationservice.modules.plantvarieties.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExaminationDataPlantVarietyDto extends PlantVarietyDto {
    private Long id;
    private Boolean microbiology;
    private String microbiologyNote;
    private Boolean chemicalEdit;
    private String chemicalEditNote;
    private Boolean tissueCulture;
    private String tissueCultureNote;
    private Boolean otherFactors;
    private String otherFactorsNote;

    public ExaminationDataPlantVarietyDto(Long id, Boolean microbiology,
                                          String microbiologyNote, Boolean chemicalEdit,
                                          String chemicalEditNote, Boolean tissueCulture,
                                          String tissueCultureNote, Boolean otherFactors, String otherFactorsNote) {
        this.id = id;
        this.microbiology = microbiology;
        this.microbiologyNote = microbiologyNote;
        this.chemicalEdit = chemicalEdit;
        this.chemicalEditNote = chemicalEditNote;
        this.tissueCulture = tissueCulture;
        this.tissueCultureNote = tissueCultureNote;
        this.otherFactors = otherFactors;
        this.otherFactorsNote = otherFactorsNote;
    }




}
