package gov.saip.applicationservice.common.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "application_search")
public class ApplicationSearch extends ApplicationSupportServicesType{

    @Column
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String notes;
    
    @OneToOne
    @JoinColumn(name = "classification_id")
    private Classification classification;

    @ManyToOne
    @JoinColumn(name = "document_id" )
    private Document applicationSearchDocument;



    @OneToMany(mappedBy = "applicationSearch")
    private List<ApplicationSearchSimilars> applicationSearchSimilars;



}
