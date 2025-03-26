package gov.saip.applicationservice.util;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public class IPRMultipartFile implements MultipartFile {

    private final String name;
    private final String originalFilename;
    private MultipartFile multipartFile;

    public IPRMultipartFile(String name, String originalFilename, MultipartFile multipartFile) {
        this.name = name;
        this.originalFilename = originalFilename;
        this.multipartFile = multipartFile;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public String getOriginalFilename() {
        return originalFilename;
    }
    
    @Override
    public String getContentType() {
        return multipartFile.getContentType();
    }
    
    @Override
    public boolean isEmpty() {
        return multipartFile.isEmpty();
    }
    
    @Override
    public long getSize() {
        return multipartFile.getSize();
    }
    
    @Override
    public byte[] getBytes() throws IOException {
        return multipartFile.getBytes();
    }
    
    @Override
    public InputStream getInputStream() throws IOException {
        return multipartFile.getInputStream();
    }
    
    @Override
    public Resource getResource() {
        return MultipartFile.super.getResource();
    }
    
    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
    
    }
    
    @Override
    public void transferTo(Path dest) throws IOException, IllegalStateException {
        MultipartFile.super.transferTo(dest);
    }
}
