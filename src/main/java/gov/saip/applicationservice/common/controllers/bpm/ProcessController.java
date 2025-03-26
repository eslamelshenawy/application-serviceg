package gov.saip.applicationservice.common.controllers.bpm;

import gov.saip.applicationservice.common.dto.RequestTasksDto;
import gov.saip.applicationservice.common.service.BPMCallerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/kc/process", "/internal-calling/process"})
@RequiredArgsConstructor
@Slf4j
public class ProcessController {
    private final BPMCallerService bPMCallerService;
    @GetMapping("/tasks-by-assignee/{rowId}")
    public List<RequestTasksDto> getTasksByRowIdAndAssignee(@PathVariable("rowId") Long rowId) {
        return bPMCallerService.getTasksByRowIdAndAssignee(rowId);
    }
}
