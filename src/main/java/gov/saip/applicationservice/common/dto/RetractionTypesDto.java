package gov.saip.applicationservice.common.dto;

import lombok.Data;

@Data
public class RetractionTypesDto {
    private Boolean pendingSupportServiceRequest;
    private Boolean inProgressRequest;

    public RetractionTypesDto() {
        this.pendingSupportServiceRequest = Boolean.FALSE;
        this.inProgressRequest = Boolean.FALSE;
    }

    public RetractionTypesDto(Boolean pendingSupportServiceRequest, Boolean inProgressRequest) {
        this.pendingSupportServiceRequest = pendingSupportServiceRequest;
        this.inProgressRequest = inProgressRequest;
    }

}
