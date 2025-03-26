package gov.saip.applicationservice.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@Builder
public class RequestIdAndNotesDto implements Serializable {
    private Long id;
    private String notes;
}