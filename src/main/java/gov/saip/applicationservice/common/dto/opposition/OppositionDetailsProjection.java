package gov.saip.applicationservice.common.dto.opposition;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.time.LocalTime;

public interface OppositionDetailsProjection {


    Long getId();

    Long getAppId();

    String getRequestNumber();
    String getApplicationNumber();
    @JsonIgnore

    LocalDate getComplainerSessionDate();
    @JsonIgnore

    LocalDate getApplicationOwnerSessionDate();
    @JsonIgnore

    LocalTime getApplicationOwnerSessionTime();
    @JsonIgnore

    LocalTime getComplainerSessionTime();

    String getRequesterType();

    default LocalDate getDate(){
        return (getApplicationOwnerSessionDate() == null) ? getComplainerSessionDate() : getApplicationOwnerSessionDate();
    }

    default LocalTime getTime(){
        return (getApplicationOwnerSessionTime() == null) ? getComplainerSessionTime() : getApplicationOwnerSessionTime();
    }


}
