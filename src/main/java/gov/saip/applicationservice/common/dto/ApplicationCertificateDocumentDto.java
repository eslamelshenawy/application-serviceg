package gov.saip.applicationservice.common.dto;


import gov.saip.applicationservice.common.model.Document;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApplicationCertificateDocumentDto {

    private Long id;

    private DocumentDto document;

    private int version;
    private LocalDateTime createdDate;

}
