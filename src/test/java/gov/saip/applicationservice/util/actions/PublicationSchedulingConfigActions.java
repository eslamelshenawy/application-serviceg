package gov.saip.applicationservice.util.actions;

import gov.saip.applicationservice.common.dto.PublicationSchedulingConfigCreateDto;
import gov.saip.applicationservice.common.dto.PublicationSchedulingConfigViewDto;
import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public interface PublicationSchedulingConfigActions extends Actions {
    @NotNull
    default ResultActions putPublicationSchedulingConfig(PublicationSchedulingConfigCreateDto config) throws Exception {
        return getMockMvc().perform(
                MockMvcRequestBuilders.put(
                                "/internal-calling/publication-scheduling-config"
                        )
                        .content(serialize(config))
                        .contentType(MediaType.APPLICATION_JSON)
        );
    }

    default ResultActions getPublicationSchedulingConfig(ApplicationCategoryEnum applicationCategoryEnum) throws Exception {
        return getMockMvc().perform(MockMvcRequestBuilders.get("/internal-calling/publication-scheduling-config/%s"
                .formatted(applicationCategoryEnum.name())));
    }
}
