package gov.saip.applicationservice.common.dto;

import java.time.LocalDate;
import java.util.Date;

public interface PublicationProjection {
    
    LocalDate getReceptionDate();
    
    Long getNumberOfPublications();
    
    Long getPublicationsApprovedNumber();
    
    Long getNumberOfPublicationsSentForEditing();
    
    Long getPublicationsAwaitingAction();
    
}
