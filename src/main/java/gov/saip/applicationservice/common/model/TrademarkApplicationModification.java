package gov.saip.applicationservice.common.model;


import gov.saip.applicationservice.base.model.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "trademark_application_modification")
@Setter
@Where(clause = "is_deleted = 0")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrademarkApplicationModification extends BaseEntity<Long> {


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id")
    private ApplicationInfo application;

    private Integer oldMarkType;

    private Integer oldMarkTypeDesc;

    private String oldMarkNameAr;

    private String oldMarkNameEn;

    private String oldMarkDesc;

    boolean updated =false;

    private Integer newMarkType;

    private Integer newMarkTypeDesc;

    private String newMarkNameAr;

    private String newMarkNameEn;
    private String newMarkDesc;

    @Column(name = "Hijri_filing_Date")
    private String ModificationsHijriDate;
    @Column(name = "filing_date")
    private LocalDateTime modificationFilingDate;

    private Integer oldTagLanguageId;

    private Integer newTagLanguageId;
}
