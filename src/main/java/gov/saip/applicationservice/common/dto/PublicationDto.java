package gov.saip.applicationservice.common.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class PublicationDto {
    
    LocalDate receptionDate;
    
    Long numberOfPublications;
    
    Long publicationsApprovedNumber;
    
    Long numberOfPublicationsSentForEditing;
    
    Long publicationsAwaitingAction;
}
