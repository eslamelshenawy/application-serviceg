package gov.saip.applicationservice.common.facade;

import gov.saip.applicationservice.common.dto.ApplicationInfoBaseDto;

public interface CertificateRequestFacade {

    public ApplicationInfoBaseDto getApplicationsAccordingCertificatesPreConditions(String type, Long categoryId, String searchField);
}
