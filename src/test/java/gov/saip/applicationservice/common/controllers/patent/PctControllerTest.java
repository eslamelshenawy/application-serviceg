package gov.saip.applicationservice.common.controllers.patent;

import gov.saip.applicationservice.common.dto.patent.PctDto;
import gov.saip.applicationservice.common.dto.patent.PctRequestDto;
import gov.saip.applicationservice.common.mapper.patent.PctMapper;
import gov.saip.applicationservice.common.model.patent.Pct;
import gov.saip.applicationservice.common.service.patent.PctService;
import gov.saip.applicationservice.common.validators.PctValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PctControllerTest {

    @Mock
    MockMvc mockMvc;


    @InjectMocks
    private PctController pctController;

    @Mock
    private PctService pctService;


    @Mock
    PctMapper pctMapper;

    @Mock
    PctValidator pctValidator;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(pctController).build();
    }

    @Test
    void testCreatePct() throws Exception {
        PctRequestDto requestDto = new PctRequestDto();
        requestDto.setPctApplicationNo("some value");
        String jsonString = "{\n" +
                "  \"id\": 1,\n" +
                "  \"applicationId\": 123,\n" +
                "  \"pctApplicationNo\": \"PCT12345\",\n" +
                "  \"filingDateGr\": \"2023-09-11\",\n" +
                "  \"publishNo\": \"PUB56789\",\n" +
                "  \"wipoUrl\": \"https://www.wipo.int/\",\n" +
                "  \"active\": true,\n" +
                "  \"patentDetailsRequestDto\": {\n" +
                "    \"id\": 2,\n" +
                "    \"ipdSummaryAr\": \"Arabic summary\",\n" +
                "    \"ipdSummaryEn\": \"English summary\",\n" +
                "    \"specificationsDocId\": 456,\n" +
                "    \"collaborativeResearch\": true,\n" +
                "    \"collaborativeResearchId\": {\n" +
                "      \"id\": 3,\n" +
                "      \"name\": \"Collaborative Research Name\"\n" +
                "    },\n" +
                "    \"attributeChangeLogs\": [\n" +
                "      {\n" +
                "        \"id\": 4,\n" +
                "        \"changeDescription\": \"Change 1\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 5,\n" +
                "        \"changeDescription\": \"Change 2\"\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  \"internationalPublicationDate\": \"2023-09-12\"\n" +
                "}\n";
        Pct createdPct = new Pct();
        createdPct.setId(1L);
        when(pctService.createPct(requestDto)).thenReturn(createdPct);

        mockMvc.perform(MockMvcRequestBuilders.post("/kc/pct")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(status().isOk());
    }

    @Test
    void testGetPctById() throws Exception {
        Long pctId = 1L;
        Pct pct = new Pct();
        pct.setId(pctId);
        PctDto pctDto = new PctDto();
        pctDto.setId(1l);
        when(pctMapper.map(pct)).thenReturn(pctDto);
        when(pctService.getPctById(pctId)).thenReturn(pctDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/kc/pct/{id}", pctId))
                .andExpect(status().isOk());
    }

    @Test
    void testGetByApplicationId() throws Exception {
        Long applicationId = 2L;
        Pct pct = new Pct();
        pct.setId(1L);
        when(pctService.findByApplicationId(applicationId)).thenReturn(pct);

        mockMvc.perform(MockMvcRequestBuilders.get("/kc/pct/application/{id}", applicationId))
                .andExpect(status().isOk());
    }
}

