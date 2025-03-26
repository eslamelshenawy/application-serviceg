package gov.saip.applicationservice.common.controllers.lookup;

import gov.saip.applicationservice.common.controllers.LkCertificateTypeController;
import gov.saip.applicationservice.common.dto.LkCertificateTypeDto;
import gov.saip.applicationservice.common.service.impl.LkCertificateTypeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LkCertificateTypeControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private LkCertificateTypeController lkCertificateTypeController;

    @Mock
    private LkCertificateTypeServiceImpl certificateTypeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(lkCertificateTypeController).build();
    }

    @Test
    void testGetCertificateTypesByCategoryId() throws Exception {

        LkCertificateTypeDto dto1 = new LkCertificateTypeDto();
        LkCertificateTypeDto dto2 = new LkCertificateTypeDto();
        List<LkCertificateTypeDto> dtoList = Arrays.asList(dto1, dto2);
        when(certificateTypeService.findCertificateTypesByCategoryId(anyLong())).thenReturn(dtoList);

        mockMvc.perform(get("/kc/certificate-type/specific-category")
                        .param("category-id", "1"))
                        .andExpect(status().isOk());
    }

}
