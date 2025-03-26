package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.dto.lookup.LkDatabaseDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApplicationDatabaseDto extends BaseDto<Long> {

    private Long applicationId;
    private LkDatabaseDto database;
    private boolean other;
    private String otherDatabaseName;


}
