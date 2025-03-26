package gov.saip.applicationservice.util.actions;

import gov.saip.applicationservice.util.BaseIntegrationTest;
import org.jetbrains.annotations.NotNull;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

public interface ApplicationListingActions extends Actions {
    @NotNull
    default ResultActions getApplicationListByApplicationCategoryAndUserId(Map<String, List<String>> queryParams) throws Exception {
        return getMockMvc().perform(MockMvcRequestBuilders.get("/kc/applications")
                .with(BaseIntegrationTest.defaultJwt())
                .queryParams(CollectionUtils.toMultiValueMap(queryParams)));
    }
}
