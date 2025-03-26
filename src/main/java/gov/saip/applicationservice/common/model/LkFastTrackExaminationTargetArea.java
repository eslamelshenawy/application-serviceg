package gov.saip.applicationservice.common.model;


import gov.saip.applicationservice.base.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "lk_fast_track_examination_target_area")
@Setter
@Getter
public class LkFastTrackExaminationTargetArea extends BaseEntity<Long> {
    @Column
    private String descriptionAr;

    @Column
    private String descriptionEn;

    @Column
    private Boolean show;

    public LkFastTrackExaminationTargetArea() {
    }
}
