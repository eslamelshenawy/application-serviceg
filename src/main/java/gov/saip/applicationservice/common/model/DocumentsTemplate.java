package gov.saip.applicationservice.common.model;


import gov.saip.applicationservice.base.model.BaseEntity;
import gov.saip.applicationservice.util.Utilities;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "documents_template")
@Setter
@Getter
public class DocumentsTemplate extends BaseEntity<Long> {

    @Column
    private String fileName;

    @Column
    private String labelNameEn;

    @Column
    private String labelNameAr;

    @Column
    private String nexuoId;

    @Column
    private Date uploadedDate;

    @Column
    private Integer size;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lk_nexuo_user_id")
    private LkNexuoUser lkNexuoUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private LkApplicationCategory category;

    public String getFileReviewUrl() {

        if (lkNexuoUser != null &&lkNexuoUser.getName() != null){
            return Utilities.filerServiceURL + nexuoId + "/" + lkNexuoUser.getName() + "/";
        }

      return null;


    }
}
