package gov.saip.applicationservice.common.dto;


import gov.saip.applicationservice.common.dto.supportService.SupportServiceStatusChangeLogDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupportServicesTypeDecisionsDto extends SupportServicesTypeDecisionsResponse {
    private String supportServiceStatus;
    private String role;
    private String customers;
    private boolean skipChangeStatus;
    private SupportServiceStatusChangeLogDto supportServiceStatusChangeLogDto;
}
