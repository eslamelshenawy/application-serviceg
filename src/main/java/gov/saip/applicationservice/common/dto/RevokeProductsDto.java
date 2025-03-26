package gov.saip.applicationservice.common.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class RevokeProductsDto extends BaseSupportServiceDto {
    private List<Long> subClassificationsIds;
    private List<SubClassificationDto> subClassifications;
    private String notes;
    private List<Long> documentIds;
    private List<DocumentDto> documents;
    private CustomerSampleInfoDto agentDetails;

}

