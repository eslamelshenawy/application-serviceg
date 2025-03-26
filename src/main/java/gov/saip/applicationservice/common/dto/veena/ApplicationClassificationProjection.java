package gov.saip.applicationservice.common.dto.veena;

import gov.saip.applicationservice.common.model.ApplicationNiceClassification;
import gov.saip.applicationservice.common.model.veena.ApplicationVeenaClassification;

import javax.persistence.Transient;
import java.util.List;

public interface ApplicationClassificationProjection {

    Long getId();
    String getApplicationNumber();
    Boolean getNationalSecurity();
    Long getClassificationUnitId();
    String getClassificationNotes();
    List<ApplicationVeenaClassification> getVeenaClassifications();
    List<ApplicationNiceClassification> getNiceClassifications();
}

