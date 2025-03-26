package gov.saip.applicationservice.common.model;


import gov.saip.applicationservice.base.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "application_word")
@Setter
@Getter
public class ApplicationWord extends BaseEntity<Long> {

    @Column
    private String word;

    @Column
    private String synonym;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_info_id")
    private ApplicationInfo applicationInfo;

}
