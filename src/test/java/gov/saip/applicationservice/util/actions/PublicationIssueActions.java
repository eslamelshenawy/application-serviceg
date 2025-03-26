package gov.saip.applicationservice.util.actions;

import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Arrays;
import java.util.Map;

public interface PublicationIssueActions extends Actions {

    default ResultActions countByApplicationCategory() throws Exception {
        return getMockMvc()
                .perform(MockMvcRequestBuilders.
                        get("/pb/publication-issue/count-published-by-application-category")
                );
    }

    default ResultActions getPublicationYears(ApplicationCategoryEnum applicationCategorySaipCode) throws Exception {
        return getMockMvc()
                .perform(MockMvcRequestBuilders.
                        get("/pb/publication-issue/publication-years/{application-category-saip-code}", applicationCategorySaipCode.name())
                );
    }

    default ResultActions getUnpublishedPublicationIssuesByApplicationCategory(ApplicationCategoryEnum applicationCategorySaipCode) throws Exception {
        return getMockMvc()
                .perform(MockMvcRequestBuilders.
                        get("/internal-calling/publication-issue/{application-category-saip-code}", applicationCategorySaipCode.name())
                        .param("published", "false")
                );
    }

    default ResultActions getPublishedPublicationIssuesByApplicationCategory(ApplicationCategoryEnum applicationCategorySaipCode, RequestPostProcessor... requestPostProcessors) throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.
                get("/pb/publication-issue/{application-category-saip-code}", applicationCategorySaipCode.name())
                .param("published", "true");

        Arrays.stream(requestPostProcessors)
                .forEach(request::with);

        return getMockMvc()
                .perform(request);
    }

}
