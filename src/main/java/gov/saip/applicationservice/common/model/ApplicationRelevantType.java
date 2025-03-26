package gov.saip.applicationservice.common.model;


import gov.saip.applicationservice.base.model.BaseEntity;
import gov.saip.applicationservice.common.enums.ApplicationRelevantEnum;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "application_relevant_type")
@Where(clause = "is_deleted = 0")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationRelevantType extends BaseEntity<Long> {

    @Column
    private String customerCode;

    @Column
    @Enumerated(EnumType.STRING)
    private ApplicationRelevantEnum type;

    @Column
    private boolean inventor;

    @ManyToOne
    @JoinColumn(name = "application_relevant_id")
    private ApplicationRelevant applicationRelevant;

    @ManyToOne
    @JoinColumn(name = "application_info_id")
    private ApplicationInfo applicationInfo;

    @OneToOne
    @JoinColumn(name = "waiver_document_id")
    private Document waiverDocumentId;

    @Column(name = "is_paid")
    private boolean isPaid;

    @Column
    private Long duplicationFlag;

}
