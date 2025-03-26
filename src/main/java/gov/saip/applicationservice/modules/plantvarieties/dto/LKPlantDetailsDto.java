package gov.saip.applicationservice.modules.plantvarieties.dto;


import gov.saip.applicationservice.base.dto.BaseLkpEntityDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LKPlantDetailsDto extends BaseLkpEntityDto<Integer> {

    private String mainCode;
    public LKPlantDetailsDto(Integer id) {

       super(id);
    }

}
