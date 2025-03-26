package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.base.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ListApplicationClassificationDto extends BaseDto<Long> {
    private String code;
    private String nameAr;
    private String nameEn;
    private LkClassificationVersionDto version;
    private List<ListSubClassificationDto> subClassifications;
}