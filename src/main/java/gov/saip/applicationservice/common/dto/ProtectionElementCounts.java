package gov.saip.applicationservice.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ProtectionElementCounts {
    private Long countParentElements;
    private Long countChildrenElements;
    private Long pages;
}
