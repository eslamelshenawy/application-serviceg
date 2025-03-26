package gov.saip.applicationservice.common.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DueDateDto {
    private String duedate;
    private boolean cascade;
}
