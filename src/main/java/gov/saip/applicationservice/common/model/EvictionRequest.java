package gov.saip.applicationservice.common.model;


import gov.saip.applicationservice.base.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "eviction_request")
@Setter
@Getter
public class EvictionRequest extends ApplicationSupportServicesType {

    @Column(columnDefinition = "TEXT")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "desc_document_id")
    private Document descDocument;

    @ManyToOne
    @JoinColumn(name = "eviction_document_id")
    private Document evictionDocument;

}
