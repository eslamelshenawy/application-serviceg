package gov.saip.applicationservice.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StartProcessResponseDto {
    private String id;
    private Long businessKey;
    private TaskHistoryUIDto taskHistoryUIDto;
}
