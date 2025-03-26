package gov.saip.applicationservice.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ApplicationConstants {
    @Value("${camunda.date.time.format}")
    public String camundaDateTimeFormat;

}
