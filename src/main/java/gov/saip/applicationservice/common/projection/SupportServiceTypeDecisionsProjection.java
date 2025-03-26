package gov.saip.applicationservice.common.projection;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;


public interface SupportServiceTypeDecisionsProjection {

    public String getDecision();
    public String getComment();
    public String getSupportServiceName();
    public LocalDateTime getCreatedDate();



}
