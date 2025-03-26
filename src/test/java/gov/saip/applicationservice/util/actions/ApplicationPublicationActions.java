package gov.saip.applicationservice.util.actions;

import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public interface ApplicationPublicationActions extends Actions {
    default ResultActions countApplicationPublicationsByApplicationCategory() throws Exception {
        return getMockMvc()
                .perform(MockMvcRequestBuilders.
                        get("/pb/application-publication/count-by-application-category")
                );
    }

    default ResultActions createApplicationPublication(Long applicationId) throws Exception {
        return getMockMvc()
                .perform(MockMvcRequestBuilders.
                        post("/internal-calling/application-publication/{applicationId}", applicationId)
                );
    }
}
