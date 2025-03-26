package gov.saip.applicationservice.common.dto.publication;

import gov.saip.applicationservice.common.enums.DateTypeEnum;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Data
public class PublicPublicationSearchParamDto {
    
    DateTypeEnum dateType;
    
    Integer year;
    
    String yearHijri; // gazette
    
    Date fromGazetteDate;
    
    Date toGazetteDate;
    
    Integer filingYear;
    
    String filingYearHijri;
    
    Date fromFilingDate;
    
    Date toFilingDate;
    
    Date fromPublicationDate;
    
    Date toPublicationDate;
    
    String publicationType;
    
    String applicationNumber;
    
    String agentName;
    
    String applicantName;
    
    Boolean checkRemainingTime = false;
    
    String searchField;
    
}
