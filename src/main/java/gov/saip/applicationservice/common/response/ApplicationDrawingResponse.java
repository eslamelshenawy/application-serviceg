package gov.saip.applicationservice.common.response;

import gov.saip.applicationservice.common.dto.ApplicationDrawingDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class ApplicationDrawingResponse {

    private ApplicationDrawingDto mainDraw;
    private List<ApplicationDrawingDto> drawings;
}
