package gov.saip.applicationservice.util.duplicatedStatusRemoval;

import gov.saip.applicationservice.common.dto.lookup.LkApplicationStatusDto;
import gov.saip.applicationservice.common.enums.ApplicationStatusGroupEnum;

import java.util.List;
import java.util.stream.Collectors;

public class UnderProcessStatusGrouping implements StatusProcessor<ApplicationStatusGroupEnum> {
    
    
    private final ApplicationStatusGroupEnum statusGroup;
    
    public UnderProcessStatusGrouping(ApplicationStatusGroupEnum statusGroupEnum) {
        this.statusGroup = statusGroupEnum;
    }
    
    @Override
    public void process(List<LkApplicationStatusDto> statuses) {
        
        
        List<LkApplicationStatusDto> filteredStatuses = getSatusesNeedToBeGrouped(statuses);
        
        if (filteredStatuses == null || filteredStatuses.isEmpty())
            return;
        
        LkApplicationStatusDto newGroupedStatus = getFirstMatchedStatus(filteredStatuses);
        
        newGroupedStatus.setStatusIds(filteredStatuses.stream().map(s -> s.getId()).collect(Collectors.toList()));
        
        statuses.removeAll(filteredStatuses);
        statuses.add(newGroupedStatus);
        
        
    }
    
    private List<LkApplicationStatusDto> getSatusesNeedToBeGrouped(List<LkApplicationStatusDto> statuses) {
        return statuses.stream()
                .filter(status -> statusGroup.getCodes().contains(status.getCode()))
                .collect(Collectors.toList());
    }
    
    private static LkApplicationStatusDto getFirstMatchedStatus(List<LkApplicationStatusDto> filteredStatuses) {
        return filteredStatuses.get(0);
    }
    
    
}
