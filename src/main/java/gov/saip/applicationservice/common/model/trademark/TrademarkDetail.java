package gov.saip.applicationservice.common.model.trademark;

import gov.saip.applicationservice.base.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Setter
@Getter
@Entity
@Where(clause = "is_deleted = 0")
@Table(name = "trademark_details")
public class TrademarkDetail extends BaseEntity<Long> {
    @NotNull
    private Long applicationId;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "mark_type_id")
    private LkMarkType markType;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "tag_type_desc_id")
    private LkTagTypeDesc tagTypeDesc;

    @ManyToOne
    @JoinColumn(name = "tag_language_id")
    private LkTagLanguage tagLanguage;
    private String nameAr;
    private String nameEn;
    private String markClaimingColor;
    private String markDescription;
    private String wordMark;
    private boolean haveExhibition;
    private String  exhibitionInfo;
    private LocalDate exhibitionDate;
    private boolean isExposedPublic;
    private LocalDate exposedDate;
    private String examinerGrantCondition;

}
