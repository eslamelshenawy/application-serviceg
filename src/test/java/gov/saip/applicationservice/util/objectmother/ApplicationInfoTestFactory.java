package gov.saip.applicationservice.util.objectmother;

import gov.saip.applicationservice.common.enums.ApplicationAgentStatus;
import gov.saip.applicationservice.common.enums.ApplicationRelevantEnum;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.ApplicationRelevantType;
import gov.saip.applicationservice.common.model.LkApplicationCategory;
import gov.saip.applicationservice.common.model.LkApplicationStatus;
import gov.saip.applicationservice.common.model.agency.ApplicationAgent;

import java.util.ArrayList;
import java.util.List;

public class ApplicationInfoTestFactory {
    public static ApplicationInfo.ApplicationInfoBuilder anApplication(LkApplicationStatus applicationStatus, LkApplicationCategory applicationCategory) {
        return ApplicationInfo.builder()
                .category(applicationCategory)
                .applicationStatus(applicationStatus);


    }

    public static ApplicationAgent.ApplicationAgentBuilder aDefaultApplicationAgent(Long customerId, ApplicationAgentStatus applicationAgentStatus) {
        return ApplicationAgent.builder()
                .status(applicationAgentStatus)
                .customerId(customerId);
    }

    public static ApplicationRelevantType.ApplicationRelevantTypeBuilder aMainApplicant(String customerCode, ApplicationInfo applicationInfo) {
        return ApplicationRelevantType.builder()
                .customerCode(customerCode)
                .type(ApplicationRelevantEnum.Applicant_MAIN)
                .applicationInfo(applicationInfo);

    }
}
