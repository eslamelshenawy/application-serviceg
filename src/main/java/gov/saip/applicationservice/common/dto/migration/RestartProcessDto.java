package gov.saip.applicationservice.common.dto.migration;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RestartProcessDto {
    private List<RestartProcessInstructionDto> instructions;
    private List<String> processInstanceIds;
    private boolean initialVariables = false;
    private boolean skipCustomListeners = true;
    private boolean withoutBusinessKey = false;
}
