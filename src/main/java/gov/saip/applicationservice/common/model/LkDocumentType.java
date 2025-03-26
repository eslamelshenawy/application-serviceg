package gov.saip.applicationservice.common.model;


import gov.saip.applicationservice.base.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "lk_document_type")
@Setter
@Getter
public class LkDocumentType extends BaseEntity<Long> {
    @Column
    private String name;
    @Column
    private String nameAr;
    @Column
    private String code;

    @Column
    private String description;

    @Column
    private String category;

    @Column
    private Long docOrder;

}
