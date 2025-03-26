package gov.saip.applicationservice.common.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DurationDto {

    private  Long days;

    private  Long hours;

    private  Long minutes;

    public DurationDto(Long days, Long hours, Long minutes) {
        this.days = days;
        this.hours = hours;
        this.minutes = minutes;
    }

    @Override
    public String toString() {
        return "DurationDto{" +
                "days=" + days +
                ", hours=" + hours +
                ", minutes=" + minutes +
                '}';
    }
}
