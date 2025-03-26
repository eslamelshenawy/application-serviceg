package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.common.dto.PublicationSchedulingConfigCreateDto;
import gov.saip.applicationservice.common.dto.PublicationSchedulingConfigViewDto;
import gov.saip.applicationservice.common.dto.PublicationTimeCreateDto;
import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import gov.saip.applicationservice.common.enums.PublicationFrequency;
import gov.saip.applicationservice.common.mapper.PublicationSchedulingConfigMapper;
import gov.saip.applicationservice.common.model.PublicationSchedulingConfig;
import gov.saip.applicationservice.common.service.PublicationSchedulingConfigService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Set;

import static org.mockito.Mockito.*;

public class PublicationSchedulingConfigControllerTest {

    @Mock
    private PublicationSchedulingConfigService publicationSchedulingConfigService;

    @Mock
    private PublicationSchedulingConfigMapper publicationSchedulingConfigMapper;

    @InjectMocks
    private PublicationSchedulingConfigController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testGetByApplicationCategory() throws Exception {
        PublicationSchedulingConfig config = new PublicationSchedulingConfig();
        when(publicationSchedulingConfigService.findByApplicationCategorySaipCode(anyString()))
                .thenReturn(config);

        PublicationSchedulingConfigViewDto dto = new PublicationSchedulingConfigViewDto();
        when(publicationSchedulingConfigMapper.map(config)).thenReturn(dto);

        mockMvc.perform(MockMvcRequestBuilders.get("/kc/publication-scheduling-config/{application-category-saip-code}", "CASE_AND_COMPLAIN")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testCreateOrUpdate() throws Exception {
        PublicationTimeCreateDto timeCreateDto = new PublicationTimeCreateDto();
        timeCreateDto.setDayOfWeekCode(DayOfWeek.MONDAY);
        timeCreateDto.setTime(LocalDateTime.of(2023, 9, 15, 10, 0));
        timeCreateDto.setDayOfMonth(15);
        String JsonString = "{\n" +
                "  \"publicationFrequency\": \"WEEKLY\",\n" +
                "  \"applicationCategorySaipCode\": \"PATENT\",\n" +
                "  \"publicationTimes\": [\n" +
                "    {\n" +
                "      \"dayOfWeekCode\": \"MONDAY\",\n" +
                "      \"time\": \"2023-09-11T10:00:00\",\n" +
                "      \"dayOfMonth\": 1\n" +
                "    },\n" +
                "    {\n" +
                "      \"dayOfWeekCode\": \"FRIDAY\",\n" +
                "      \"time\": \"2023-09-15T15:30:00\",\n" +
                "      \"dayOfMonth\": 15\n" +
                "    }\n" +
                "  ]\n" +
                "}\n";

        PublicationSchedulingConfigCreateDto createDto = new PublicationSchedulingConfigCreateDto();
        createDto.setPublicationFrequency(PublicationFrequency.WEEKLY);
        createDto.setApplicationCategorySaipCode(ApplicationCategoryEnum.CASE_AND_COMPLAIN);
        createDto.setPublicationTimes(Set.of(timeCreateDto));

        PublicationSchedulingConfig config = new PublicationSchedulingConfig();
        config.setId(1l);
        when(publicationSchedulingConfigMapper.fromCreateDto(createDto))
                .thenReturn(config);

        mockMvc.perform(MockMvcRequestBuilders.put("/kc/publication-scheduling-config")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonString))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
