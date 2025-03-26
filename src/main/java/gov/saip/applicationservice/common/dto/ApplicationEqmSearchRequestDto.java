package gov.saip.applicationservice.common.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class ApplicationEqmSearchRequestDto {
    
    private String searchField;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate fromDate;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate toDate;
}