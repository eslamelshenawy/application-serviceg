package gov.saip.applicationservice.common.dto.industrial;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class DesignSampleReqDto {
    private Long id;
    private String name;
    private List<DesignSampleDrawingsReqDto> designSampleDrawings;
    private List<Long> designers;
    private List<Long> classification;
    private String description;
}
