package gov.saip.applicationservice.common.dto.patent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
public class PatentFillingDateDto {


    private LocalDate internationalPublicationDate;

    public PatentFillingDateDto(LocalDate internationalPublicationDate, LocalDateTime applicationFilingDate) {
        this.internationalPublicationDate = internationalPublicationDate;
        this.applicationFilingDate = applicationFilingDate;
    }

    private LocalDateTime applicationFilingDate;




}
