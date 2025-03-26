package gov.saip.applicationservice.common.model.patent;

import gov.saip.applicationservice.base.model.BaseEntity;
import gov.saip.applicationservice.common.model.Document;
import gov.saip.applicationservice.util.Constants;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "pct")
@Setter
@Getter
public class Pct extends BaseEntity<Long> {


    @Column
    private String pctApplicationNo;
    @NotNull(message = Constants.ErrorKeys.FILLING_DATE_GR_REQUIRED)
    @Column
    private LocalDate filingDateGr;
    @Column
    private String publishNo;
    @Column
    private String wipoUrl;
    @Column
    private String petitionNumber;
    @ManyToOne
    @JoinColumn(name = "patent_id", unique = true)
    private PatentDetails patentDetails;
    private LocalDate internationalPublicationDate;
    @Transient
    private boolean active;
    @OneToOne
    @JoinColumn(name = "pct_copy_document_id")
    private Document pctCopyDocument;

}
