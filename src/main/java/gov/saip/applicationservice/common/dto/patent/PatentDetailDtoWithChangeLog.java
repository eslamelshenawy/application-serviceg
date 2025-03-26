package gov.saip.applicationservice.common.dto.patent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public class PatentDetailDtoWithChangeLog implements Serializable {

    private PatentDetailsDto patentDetails;
    private Map<String, List<PatentAttributeChangeLogDto>> patentAttributeChangeLog = new HashMap<>();
}
