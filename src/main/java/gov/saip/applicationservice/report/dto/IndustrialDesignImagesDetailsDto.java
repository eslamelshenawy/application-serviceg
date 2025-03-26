package gov.saip.applicationservice.report.dto;

import lombok.*;

import java.io.Serializable;

@Data
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class IndustrialDesignImagesDetailsDto implements Serializable {

    private int sampleNumber;
    private String sampleName;
    private int shapeCount;
    private String urlImage;

}
