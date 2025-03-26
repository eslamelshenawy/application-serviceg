package gov.saip.applicationservice.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateApplicationsStatusDto {
    List<Long> ids;
    String statusCode;
    String categoryCode;
}
