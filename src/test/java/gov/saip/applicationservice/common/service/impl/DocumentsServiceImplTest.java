package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.common.clients.rest.callers.FileServiceCaller;
import gov.saip.applicationservice.common.clients.rest.callers.NotificationCaller;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.mapper.ApplicationPriorityMapper;
import gov.saip.applicationservice.common.mapper.DocumentCommentMapper;
import gov.saip.applicationservice.common.mapper.DocumentMapper;
import gov.saip.applicationservice.common.model.*;
import gov.saip.applicationservice.common.repository.ApplicationInfoRepository;
import gov.saip.applicationservice.common.repository.ApplicationPriorityRepository;
import gov.saip.applicationservice.common.repository.DocumentRepository;
import gov.saip.applicationservice.common.repository.lookup.LkApplicationPriorityStatusRepository;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.ConfigParameterService;
import gov.saip.applicationservice.common.service.DocumentsService;
import gov.saip.applicationservice.common.service.lookup.LKDocumentTypeService;
import gov.saip.applicationservice.common.service.lookup.LKNexuoUserService;
import gov.saip.applicationservice.common.service.trademark.impl.CustomMultipartFile;
import gov.saip.applicationservice.common.validators.FileValidator;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.print.Doc;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class DocumentsServiceImplTest {

    @InjectMocks
    @Spy
    DocumentsServiceImpl documentsServiceImpl;
    @Mock
    private ApplicationInfoRepository applicationInfoRepository;
    @Mock
    private FileServiceCaller fileServiceCaller;
    @Mock
    private FileValidator fileValidator;
    @Mock
    private LKDocumentTypeService lkDocumentTypeService;
    @Mock
    private LKNexuoUserService lkNexuoUserService;
    @Mock
    private DocumentRepository documentRepository;
    @Mock
    private DocumentMapper documentMapper;
    @Mock
    private DocumentCommentMapper documentCommentMapper;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddDocumentsPDF() {
        String applicationType = "appType";
        String docTypeName = "docType";
        byte[] CDRIVES = HexFormat.of().parseHex("e04fd020ea3a6910a2d808002b30309d");
        List<MultipartFile> files = Arrays.asList(new CustomMultipartFile("name", "name.pdf", "content", false, 20, new ByteArrayResource(CDRIVES, "name")));
        LkNexuoUser lkNexuoUser = new LkNexuoUser();
        lkNexuoUser.setName("name");
        Mockito.doReturn(lkNexuoUser).when(lkNexuoUserService).getNexuoUserTypeByType(applicationType);
        Mockito.doReturn(Arrays.asList("value1", "value2")).when(fileServiceCaller).addFiles(files, lkNexuoUser.getName());
        Mockito.doReturn(new LkDocumentType()).when(lkDocumentTypeService).getDocumentTypeByName(docTypeName);

        assertNotNull(documentsServiceImpl.addDocuments(files, docTypeName, applicationType, 1L));
    }

    @Test
    public void testAddDocumentsDoc() {
        String applicationType = "appType";
        String docTypeName = "docType";
        byte[] CDRIVES = HexFormat.of().parseHex("e04fd020ea3a6910a2d808002b30309d");
        List<MultipartFile> files = Arrays.asList(new CustomMultipartFile("name", "name.doc", "content", false, 20, new ByteArrayResource(CDRIVES, "name")));
        LkNexuoUser lkNexuoUser = new LkNexuoUser();
        lkNexuoUser.setName("name");
        Mockito.doReturn(lkNexuoUser).when(lkNexuoUserService).getNexuoUserTypeByType(applicationType);
        Mockito.doReturn(Arrays.asList("value1", "value2")).when(fileServiceCaller).addFiles(files, lkNexuoUser.getName());
        Mockito.doReturn(new LkDocumentType()).when(lkDocumentTypeService).getDocumentTypeByName(docTypeName);
        Mockito.doReturn(1).when(documentsServiceImpl).calculateNumberOfPages(any());

        assertNotNull(documentsServiceImpl.addDocuments(files, docTypeName, applicationType, 1L));
    }

    @Test
    public void testAddDocumentsDocx() {
        String applicationType = "appType";
        String docTypeName = "docType";
        byte[] CDRIVES = HexFormat.of().parseHex("e04fd020ea3a6910a2d808002b30309d");
        List<MultipartFile> files = Arrays.asList(new CustomMultipartFile("name", "name.docx", "content", false, 20, new ByteArrayResource(CDRIVES, "name")));
        LkNexuoUser lkNexuoUser = new LkNexuoUser();
        lkNexuoUser.setName("name");
        Mockito.doReturn(lkNexuoUser).when(lkNexuoUserService).getNexuoUserTypeByType(applicationType);
        Mockito.doReturn(Arrays.asList("value1", "value2")).when(fileServiceCaller).addFiles(files, lkNexuoUser.getName());
        Mockito.doReturn(new LkDocumentType()).when(lkDocumentTypeService).getDocumentTypeByName(docTypeName);

        assertNotNull(documentsServiceImpl.addDocuments(files, docTypeName, applicationType, 1L));
    }

    @Test
    public void testAddDocuments() {
        String applicationType = "appType";
        String docTypeName = "docType";
        byte[] CDRIVES = HexFormat.of().parseHex("e04fd020ea3a6910a2d808002b30309d");
        List<MultipartFile> files = Arrays.asList(new CustomMultipartFile("name", "name", "content", false, 20, new ByteArrayResource(CDRIVES, "name")));
        LkNexuoUser lkNexuoUser = new LkNexuoUser();
        lkNexuoUser.setName("name");
        Mockito.doReturn(lkNexuoUser).when(lkNexuoUserService).getNexuoUserTypeByType(applicationType);
        Mockito.doReturn(Arrays.asList("value1", "value2")).when(fileServiceCaller).addFiles(files, lkNexuoUser.getName());
        Mockito.doReturn(new LkDocumentType()).when(lkDocumentTypeService).getDocumentTypeByName(docTypeName);

        assertNotNull(documentsServiceImpl.addDocuments(files, docTypeName, applicationType, 1L));
    }

    @Test
    public void testAddDocumentException() {
        String applicationType = "appType";
        String docTypeName = "docType";
        byte[] CDRIVES = HexFormat.of().parseHex("e04fd020ea3a6910a2d808002b30309d");
        List<MultipartFile> files = Arrays.asList(new CustomMultipartFile("name", "name", "content", false, 20, new ByteArrayResource(CDRIVES, "name")));
        LkNexuoUser lkNexuoUser = new LkNexuoUser();
        lkNexuoUser.setName("name");
        Mockito.doThrow(new RuntimeException()).when(lkNexuoUserService).getNexuoUserTypeByType(applicationType);
        assertThrows(Exception.class, ()->{ documentsServiceImpl.addDocuments(files, docTypeName, applicationType, 1L);});
    }

    @Test
    public void testAddDocumentBusnissException() {
        String applicationType = "appType";
        String docTypeName = "docType";
        byte[] CDRIVES = HexFormat.of().parseHex("e04fd020ea3a6910a2d808002b30309d");
        List<MultipartFile> files = Arrays.asList(new CustomMultipartFile("name", "name", "content", false, 20, new ByteArrayResource(CDRIVES, "name")));
        LkNexuoUser lkNexuoUser = new LkNexuoUser();
        lkNexuoUser.setName("name");
        Mockito.doThrow(new BusinessException("")).when(lkNexuoUserService).getNexuoUserTypeByType(applicationType);
        assertThrows(BusinessException.class, ()->{ documentsServiceImpl.addDocuments(files, docTypeName, applicationType, 1L);});
    }


    @Test
    public void testFindDocumentByIdEmpty() {
        Mockito.doReturn(Optional.empty()).when(documentRepository).findById(1L);
        assertThrows(BusinessException.class, ()->{documentsServiceImpl.findDocumentById(1L);});
    }

    @Test
    public void testFindDocumentByIdException() {
        Mockito.doThrow(new RuntimeException()).when(documentRepository).findById(1L);
        assertThrows(Exception.class, ()->{documentsServiceImpl.findDocumentById(1L);});
    }

    @Test
    public void testFindDocumentById() {
        Document document = new Document();
        document.setId(1L);
        Mockito.doReturn(Optional.of(document)).when(documentRepository).findById(anyLong());
        DocumentDto result = documentsServiceImpl.findDocumentById(1L);
        assertNull(result);
    }

    @Test
    public void testFindDocumentByApplicationIdAndDocumentTypeEmpty() {

        Long id = 1L;
        String typeName = "typeName";
        Mockito.doReturn(new ArrayList<>()).when(documentRepository).findDocumentByApplicationId(id, typeName);
        assertNull(documentsServiceImpl.findDocumentByApplicationIdAndDocumentType(1L, "typeName"));
    }

    @Test
    public void testFindDocumentByApplicationIdAndDocumentType() {

        Long id = 1L;
        String typeName = "typeName";
        List<Document> documents =  new ArrayList<>();
        documents.add(new Document());
        DocumentDto documentDto = new DocumentDto();
        documentDto.setId(1);
        Mockito.doReturn(documents).when(documentRepository).findDocumentByApplicationId(id, typeName);
        Mockito.doReturn(documentDto).when(documentMapper).mapToDto(any(Document.class));
        DocumentDto result  = documentsServiceImpl.findDocumentByApplicationIdAndDocumentType(id, typeName);
        assertEquals(1, result.getId());
    }


    @Test
    public void testSoftDeleteDocumentByIdEmpty() {
        Long id = 1L;
        Mockito.doReturn(Optional.empty()).when(documentRepository).findById(id);
        assertThrows(BusinessException.class, ()->{documentsServiceImpl.softDeleteDocumentById(id);});
    }

    @Test
    public void testSoftDeleteDocumentById() {
        Long id = 1L;
        Document document = new Document();
        document.setId(1L);
        Mockito.doReturn(Optional.of(document)).when(documentRepository).findById(id);
        Mockito.doReturn(document).when(documentRepository).save(any(Document.class));
        assertNotNull(documentsServiceImpl.softDeleteDocumentById(id));
    }


    @Test
    public void testFindDocumentByIdsEmpty() {
        List<Long> ids = new ArrayList<>();
        List<DocumentDto> result = documentsServiceImpl.findDocumentByIds(ids);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testFindDocumentByIds() {
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        List<Document> list = Arrays.asList(new Document(), new Document());
        Mockito.doReturn(list).when(documentRepository).findByIdIn(ids);
        Mockito.doReturn(Arrays.asList(new DocumentDto())).when(documentMapper).mapToDtoss(list);
        List<DocumentDto> result = documentsServiceImpl.findDocumentByIds(ids);
        assertEquals(1, result.size());
    }

    @Test
    public void testFindLightDocumentByIdsEmpty() {
        List<Long> ids = new ArrayList<>();
        List<ApplicationDocumentLightDto> result = documentsServiceImpl.findLightDocumentByIds(ids);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testFindLightDocumentByIds() {
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        List<Document> list = Arrays.asList(new Document(), new Document());
        Mockito.doReturn(list).when(documentRepository).findByIdIn(ids);
        Mockito.doReturn(Arrays.asList(new ApplicationDocumentLightDto(), new ApplicationDocumentLightDto())).when(documentMapper).mapToAppDocLightDto(list);
        List<ApplicationDocumentLightDto> result = documentsServiceImpl.findLightDocumentByIds(ids);
        assertEquals(2, result.size());
    }

    @Test
    public void testFindDocumentMapByIdsEmpty() {
        Map<Long, DocumentDto> map = new LinkedHashMap<>();
        Map<Long, DocumentDto> result = documentsServiceImpl.findDocumentMapByIds(new ArrayList<>());
        assertTrue(result.isEmpty());
    }


    @Test
    public void testFindDocumentMapByIds() {
        List<Long> ids = new ArrayList<>();
        ids.add(1L);

        Mockito.doReturn(Arrays.asList(new DocumentDto())).when(documentsServiceImpl).findDocumentByIds(ids);
        Map<Long, DocumentDto> result = documentsServiceImpl.findDocumentMapByIds(ids);
        assertEquals(1, result.size());
    }

}