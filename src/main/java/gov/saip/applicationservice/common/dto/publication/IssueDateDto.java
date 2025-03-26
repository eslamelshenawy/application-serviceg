package gov.saip.applicationservice.common.dto.publication;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class IssueDateDto {


    private Long issueId;

    @NotNull
    private LocalDate date;
    

}
