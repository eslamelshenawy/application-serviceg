package gov.saip.applicationservice.common.model.patent;

import gov.saip.applicationservice.base.model.BaseEntity;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.Document;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "application_certificate_documents")
@Setter
@Getter
public class ApplicationCertificateDocument extends BaseEntity<Long> {

    @ManyToOne
    @JoinColumn(name = "application_id")
    private ApplicationInfo application;

    @ManyToOne
    @JoinColumn(name = "document_id")
    private Document document;

    private String failureReason;

    @Enumerated(EnumType.STRING)
    private PdfGenerationStatus generationStatus;

    private int version;

}
