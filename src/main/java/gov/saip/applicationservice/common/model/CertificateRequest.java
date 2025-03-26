package gov.saip.applicationservice.common.model;


import gov.saip.applicationservice.base.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "certificates_request")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CertificateRequest extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_info_id")
    private ApplicationInfo applicationInfo;
    
    @ManyToOne()
    @JoinColumn(name = "certificate_status_id")
    private LkCertificateStatus certificateStatus;

    @ManyToOne()
    @JoinColumn(name = "certificate_type_id")
    private LkCertificateType certificateType;

    @ManyToOne
    @JoinColumn(name = "document_id")
    private Document document;
    
    @Column
    private String requestNumber;
    
    @Column(columnDefinition = "BIGSERIAL")
    private Long serial;
    
}
