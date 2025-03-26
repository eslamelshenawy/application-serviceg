package gov.saip.applicationservice.common.service.trademark.impl;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
public class CustomMultipartFile implements MultipartFile {
    
    private final String name;
    private final String originalFilename;
    
    private final String contentType;
    
    private final Boolean isEmpty;
    
    private final long size;
    private final ByteArrayResource byteArrayResource;
    
    public CustomMultipartFile(String name, String originalFilename, String contentType, Boolean isEmpty, long size, ByteArrayResource byteArrayResource) {
        this.name = name;
        this.originalFilename = originalFilename;
        this.contentType = contentType;
        this.isEmpty = isEmpty;
        this.size = size;
        this.byteArrayResource = byteArrayResource;
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
        return contentType;
    }
    
    @Override
    public boolean isEmpty() {
        return isEmpty;
    }
    
    @Override
    public long getSize() {
        return size;
    }
    
    @Override
    public byte[] getBytes() throws IOException {
        return byteArrayResource.getByteArray();
    }
    
    @Override
    public InputStream getInputStream() throws IOException {
        return byteArrayResource.getInputStream();
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
