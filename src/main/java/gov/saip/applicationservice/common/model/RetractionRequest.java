package gov.saip.applicationservice.common.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "retraction_request")
@Setter
@Getter
public class RetractionRequest extends ApplicationSupportServicesType {

    @OneToOne
    @JoinColumn(name = "lk_support_service_type_id")
    private LKSupportServiceType lkSupportServiceType;

    @ManyToOne
    @JoinColumn(name = "retraction_reason_document_id")
    private Document RetractionReasonDocument;

}
