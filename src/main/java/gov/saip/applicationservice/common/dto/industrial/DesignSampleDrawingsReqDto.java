package gov.saip.applicationservice.common.dto.industrial;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DesignSampleDrawingsReqDto {

    private Long id;

    private boolean main;

    private Long docId;

    private LkShapeTypeReqDto shape;

    private boolean doc3d;

}
