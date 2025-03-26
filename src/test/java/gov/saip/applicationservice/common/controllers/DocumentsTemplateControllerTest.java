package gov.saip.applicationservice.common.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.DocumentsTemplateDto;
import gov.saip.applicationservice.common.dto.lookup.LKNexuoUserDto;
import gov.saip.applicationservice.common.mapper.DocumentsTemplateMapper;
import gov.saip.applicationservice.common.model.DocumentsTemplate;
import gov.saip.applicationservice.common.model.LkNexuoUser;
import gov.saip.applicationservice.common.service.DocumentsTemplateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class DocumentsTemplateControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DocumentsTemplateService documentsTemplateService;

    @Mock
    private DocumentsTemplateMapper documentsTemplateMapper;

    @InjectMocks
    private DocumentsTemplateController documentsTemplateController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(documentsTemplateController).build();
    }

    @Test
    public void testFindDocumentTemplates() throws Exception {
        // Setup
        String saipCode = "TestCode";

        DocumentsTemplateDto documentsTemplateDto = new DocumentsTemplateDto();
        documentsTemplateDto.setId(1L);

        List<DocumentsTemplateDto> documentsTemplateDtos = List.of(documentsTemplateDto);

        when(documentsTemplateService.findDocumentsTemplates(saipCode))
                .thenReturn(documentsTemplateDtos);

        // Execute and Assert
        mockMvc.perform(get("/kc/documentsTemplate/{saipCode}", saipCode))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindAll() throws Exception {
        DocumentsTemplateDto documentsTemplateDto = new DocumentsTemplateDto();
        documentsTemplateDto.setId(1L);

        DocumentsTemplate documentsTemplate = new DocumentsTemplate();
        documentsTemplate.setId(1L);

        List<DocumentsTemplate> documentsTemplates = List.of(documentsTemplate);

        when(documentsTemplateService.findAll())
                .thenReturn(documentsTemplates);
        when(documentsTemplateMapper.map(documentsTemplates)).thenReturn(Arrays.asList(documentsTemplateDto));

        mockMvc.perform(get("/kc/documentsTemplate"))
                .andExpect(status().isOk());
    }

//    @Test
    public void testFindAllPaging() throws Exception {
        int page = 1;
        int limit = 1;
        String sortableColumn = "id";

        DocumentsTemplateDto documentsTemplateDto = new DocumentsTemplateDto();
        documentsTemplateDto.setId(1L);

        List<DocumentsTemplateDto> documentsTemplateDtos = List.of(documentsTemplateDto);

        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortableColumn));

        Page<DocumentsTemplateDto> pageDto = new PageImpl<>(documentsTemplateDtos, pageable, documentsTemplateDtos.size());

        when(documentsTemplateService.findAll(pageable))
                .thenReturn(null);

        mockMvc.perform(get("/kc/documentsTemplate/page")
                        .param("page", String.valueOf(page))
                        .param("limit", String.valueOf(limit))
                        .param("sortableColumn", sortableColumn))
                .andExpect(status().isOk());

    }

    @Test
    public void testFindById() throws Exception {
        Long id = 1L;

        DocumentsTemplateDto documentsTemplateDto = new DocumentsTemplateDto();
        documentsTemplateDto.setId(id);
        documentsTemplateDto.setFileName("ssadsd");
        //documentsTemplateDto.setLkNexuoUser(new LKNexuoUserDto());

        DocumentsTemplate value = new DocumentsTemplate();
        value.setId(id);
        value.setLkNexuoUser(new LkNexuoUser());
        when(documentsTemplateService.findById(id))
                .thenReturn(value);

        when(documentsTemplateMapper.map(value)).thenReturn(documentsTemplateDto);

        mockMvc.perform(get("/kc/documentsTemplate/id/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    public void testInsert() throws Exception {
        DocumentsTemplateDto documentsTemplateDto = new DocumentsTemplateDto();
        documentsTemplateDto.setId(1L);

        DocumentsTemplate documentsTemplate = new DocumentsTemplate();
        documentsTemplate.setId(1L);
        when(documentsTemplateMapper.unMap(documentsTemplateDto)).thenReturn(documentsTemplate);
        when(documentsTemplateService.insert(Mockito.any(DocumentsTemplate.class))).thenReturn(documentsTemplate);
        when(documentsTemplateMapper.map(Mockito.any(DocumentsTemplate.class))).thenReturn(documentsTemplateDto);
        when(documentsTemplateService.findById(Mockito.anyLong())).thenReturn(documentsTemplate);
        mockMvc.perform(post("/kc/documentsTemplate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(documentsTemplateDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdate() throws Exception {
        DocumentsTemplateDto documentsTemplateDto = new DocumentsTemplateDto();
        documentsTemplateDto.setId(1L);

        DocumentsTemplate documentsTemplate = new DocumentsTemplate();
        documentsTemplate.setId(1L);

        when(documentsTemplateMapper.unMap(documentsTemplateDto)).thenReturn(documentsTemplate);
        when(documentsTemplateMapper.map(documentsTemplate)).thenReturn(documentsTemplateDto);
        when(documentsTemplateService.update(any(DocumentsTemplate.class)))
                .thenReturn(documentsTemplate);

        mockMvc.perform(put("/kc/documentsTemplate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(documentsTemplateDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteById() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/kc/documentsTemplate/{id}", id))
                .andExpect(status().isOk());
    }

}