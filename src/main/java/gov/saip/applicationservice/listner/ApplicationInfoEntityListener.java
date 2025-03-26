package gov.saip.applicationservice.listner;

import gov.saip.applicationservice.common.enums.RequestTypeEnum;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.repository.ApplicationInfoRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;

@Component
@NoArgsConstructor
public class ApplicationInfoEntityListener {
    public static final String TRADEMARK_REQUEST_NUMBER_PREFIX = "SA-";

    @Lazy
    @Autowired
    private ApplicationInfoRepository applicationInfoRepository;

    @PrePersist
    public void beforePersist(ApplicationInfo application) {
        if (RequestTypeEnum.TRADEMARK.name().equals(application.getCategory().getSaipCode())) {
            application.setApplicationRequestNumber(TRADEMARK_REQUEST_NUMBER_PREFIX + getRequestSequenceNextValue());
        }
    }

    private Long getRequestSequenceNextValue() {
        return applicationInfoRepository.getRequestNumberSequenceNextValue();
    }
}
