package gov.saip.applicationservice.config;

import gov.saip.applicationservice.util.Utilities;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Data
@Component
public class Properties {

    @Value("${nuxeo.preview.url}")
    private String nuxeoPreviewUrl;

    @PostConstruct
    public void init() {
        Utilities.filerServiceURL = nuxeoPreviewUrl;
    }

}
