package gov.saip.applicationservice.common.clients.rest.callers;

import gov.saip.applicationservice.common.clients.rest.callers.FileServiceCaller;
import gov.saip.applicationservice.common.clients.rest.feigns.FileServiceFienClient;
import gov.saip.applicationservice.common.dto.FileServiceResponseDto;
import gov.saip.applicationservice.util.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class FileServiceCallerTest {

    @Mock
    private FileServiceFienClient fileServiceFienClient;

    @Mock
    private Util util;

    @InjectMocks
    private FileServiceCaller fileServiceCaller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddFiles() {
        // Arrange
        String nuxeoUserName = "user123";

        MultipartFile file1 = new MockMultipartFile("file1", "file1.txt", "text/plain", "Hello, World!".getBytes());
        MultipartFile file2 = new MockMultipartFile("file2", "file2.txt", "text/plain", "Test File".getBytes());

        List<MultipartFile> files = Arrays.asList(file1, file2);

        FileServiceResponseDto responseDto = new FileServiceResponseDto();
        responseDto.setDocId("doc123");

        when(fileServiceFienClient.uploadFile(eq(nuxeoUserName), any(MultipartFile.class))).thenReturn(responseDto);

        List<String> filesIds = fileServiceCaller.addFiles(files, nuxeoUserName);

        verify(fileServiceFienClient, times(2)).uploadFile(eq(nuxeoUserName), any(MultipartFile.class));

        assertEquals(2, filesIds.size());
        assertEquals("doc123", filesIds.get(0));
        assertEquals("doc123", filesIds.get(1));
    }
}
