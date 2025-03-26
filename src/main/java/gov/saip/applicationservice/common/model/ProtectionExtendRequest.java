package gov.saip.applicationservice.common.model;


import gov.saip.applicationservice.common.enums.ProtectionExtendTypeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "protection_extend_request")
@Setter
@Getter
public class ProtectionExtendRequest extends ApplicationSupportServicesType {

//    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
//    @JoinColumn(name = "application_support_services_type_id")
//    private ApplicationSupportServicesType applicationSupportServicesType;

    @Enumerated(EnumType.STRING)
    private ProtectionExtendTypeEnum protectionExtendType;
    private int claimCount;
    private String claimNumber;

    @ManyToOne
    @JoinColumn(name = "support_document_id")
    private Document supportDocument;

    @ManyToOne
    @JoinColumn(name = "poa_document_id")
    private Document poaDocument;

    @ManyToOne
    @JoinColumn(name = "waive_document_id")
    private Document waiveDocument;

}
