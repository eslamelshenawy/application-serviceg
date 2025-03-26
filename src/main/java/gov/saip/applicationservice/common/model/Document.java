package gov.saip.applicationservice.common.model;


import gov.saip.applicationservice.base.model.BaseEntity;
import gov.saip.applicationservice.util.Utilities;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "documents")
@Where(clause = "is_deleted = 0")
@Setter
@Getter
public class Document extends BaseEntity<Long> {


    @Column
    private String nexuoId;

    @Column
    private Date uploadedDate;

    @ManyToOne
    @JoinColumn(name = "application_id")
    private ApplicationInfo applicationInfo;

    @ManyToOne
    @JoinColumn(name = "document_type_id")
    private LkDocumentType documentType;

    @ManyToOne
    @JoinColumn(name = "lk_nexuo_user_id")
    private LkNexuoUser lkNexuoUser;

    @Column
    private String fileName;

    @Column(name = "document_pages", nullable = true)
    private Integer noOfDocumentPages; //Michael calculate the number of papers of the file

    @OneToMany(mappedBy = "document")
    private List<ApplicationDocumentComment> documentComments;

    @ManyToMany(mappedBy = "documents")
    private List<SubstantiveExaminationReport> substantiveExaminationReports = new ArrayList<>();

    public String getFileReviewUrl() {
        if (nexuoId == null || nexuoId.isEmpty())
            return null ;
        return Utilities.filerServiceURL + nexuoId + "/" + (lkNexuoUser != null ? lkNexuoUser.getName() : "") + "/";
    }


    public Document() {
    }

    public Document(Long id) {
        super(id);
    }

}
