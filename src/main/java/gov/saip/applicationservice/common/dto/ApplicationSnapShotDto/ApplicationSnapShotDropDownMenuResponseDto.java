package gov.saip.applicationservice.common.dto.ApplicationSnapShotDto;

import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.util.Utilities;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ApplicationSnapShotDropDownMenuResponseDto extends BaseDto<Long> {
    private String createdDateHijri;
    private Integer versionNumber;
    private LocalDateTime createdDate;

    public ApplicationSnapShotDropDownMenuResponseDto(Long id, Integer versionNumber, LocalDateTime createdDate) {
        super.setId(id);
        this.createdDate=createdDate;
        this.createdDateHijri = Utilities.convertDateFromGregorianToHijri(createdDate.toLocalDate());
        this.versionNumber = versionNumber;
    }
}
