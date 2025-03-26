package gov.saip.applicationservice.common.dto.industrial;

import gov.saip.applicationservice.common.model.industrial.RequestTypeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@Setter
@Getter
public class CreateDesignSampleDto {

    @Enumerated(EnumType.STRING)
    private RequestTypeEnum requestType;

    private Long industrialDesignId;

    private List<DesignSampleReqDto> designSamples;

    private List<String> designSamplesIdsToBeDeleted;
}
