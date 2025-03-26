package gov.saip.applicationservice.util;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import gov.saip.applicationservice.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class PdfUtil {

    public static int countPdfNumberOfPages(String pdfFilePath) {
        try (PDDocument doc = PDDocument.load(new File(pdfFilePath))) {
            return doc.getNumberOfPages();
        } catch (IOException e) {
            throw new BusinessException(Constants.ErrorKeys.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public MultipartFile createPdfWithImages(String fileName, List<ByteArrayResource> byteArrayResourceList) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        for (ByteArrayResource imageResource : byteArrayResourceList) {
            byte[] convertedImageBytes = convertImageToPng(imageResource.getByteArray());
            ImageData imageData = ImageDataFactory.create(convertedImageBytes);
            Image image = new Image(imageData);
            document.add(image);
            if (imageResource != byteArrayResourceList.get(byteArrayResourceList.size() - 1)) {
                document.add(new Paragraph("\n"));
            }
        }
        document.close();
        return covertByteArrayToMultiPartFile(byteArrayOutputStream, fileName);
    }

    private byte[] convertImageToPng(byte[] imageBytes) throws IOException {
        ImageIO.scanForPlugins();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
        BufferedImage bufferedImage = ImageIO.read(inputStream);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", outputStream);
        return outputStream.toByteArray();
    }

    private MultipartFile covertByteArrayToMultiPartFile(ByteArrayOutputStream byteArrayOutputStream, String fileName) throws IOException {
        byte[] pdfBytes = byteArrayOutputStream.toByteArray();
        FileItem fileItem = new DiskFileItem("file", "application/pdf", false, fileName, pdfBytes.length, null);
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(pdfBytes)) {
            fileItem.getOutputStream().write(pdfBytes);
        }
        MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
        return multipartFile;
    }

    public void deleteFileWithPath(String pdfFilePath) {
        File file = new File(pdfFilePath);
        boolean deleted = file.delete();
        if (deleted) {
            log.info("Deleted file: " + file.getAbsolutePath());
        } else {
            log.error("Failed to delete file: " + file.getAbsolutePath());
        }

    }

}
