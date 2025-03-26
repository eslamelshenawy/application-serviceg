package gov.saip.applicationservice.generics;

import com.savoirtech.logging.slf4j.json.LoggerFactory;
import com.savoirtech.logging.slf4j.json.logger.Logger;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.ByteArrayOutputStream;

@Service
public class ApplicationInfoGenericService {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationInfoGenericService.class);

    /**
     * Retrieves XML formatted file that contains applications information
     *
     * @param applicationInfoDataDto the Dto that contains the application info
     * @param <T> the Dto class that contains the application info
     * @return an {@link ByteArrayResource} object containing the XML file content
     * @throws BusinessException if a JAXB exception is thrown while generating the XML
     */
    public <T> ByteArrayResource getApplicationInfoXml(T applicationInfoDataDto) {
        // Create a ByteArrayOutputStream object to store the data
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            // Convert the data into XML format
            JAXBContext jaxbContext = JAXBContext.newInstance(applicationInfoDataDto.getClass());
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            // Store the XML formatted data in the ByteArrayOutputStream object
            jaxbMarshaller.marshal(applicationInfoDataDto, outputStream);
        } catch (JAXBException e) {
            logger.error().exception("exception", e).message(e.getMessage()).log();
            throw new BusinessException(Constants.ErrorKeys.GENERATING_APPLICATION_INFO_XML_ERROR,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // Return the ByteArrayOutputStream object which contains the XML formatted data
        return new ByteArrayResource(outputStream.toByteArray());
    }
}
