package gov.saip.applicationservice.common.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class RevokeVoluntaryRequestDto extends BaseSupportServiceDto {
    private LocalDateTime createdDate;
    private String notes;
    private List<Long> documentIds;
    private List<DocumentDto> documents;
}
