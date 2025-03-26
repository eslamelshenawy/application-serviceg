package gov.saip.applicationservice.common.projection;

import liquibase.pro.packaged.L;

public interface CountApplications {

    long getApplicationsUnderStudy();
    long getGrantedApplications();
    long getRefusedApplications();
    long getNewApplications();
}
