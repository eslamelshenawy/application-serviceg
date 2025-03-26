package gov.saip.applicationservice.common.dto.industrial;

import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IndustrialDesignLookupResDto {

    private List<LkShapeDto> shapes;
}
