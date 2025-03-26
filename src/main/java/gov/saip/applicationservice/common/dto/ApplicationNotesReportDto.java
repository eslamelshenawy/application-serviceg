package gov.saip.applicationservice.common.dto;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationNotesReportDto implements Serializable {
     private String description;
}
