package gov.saip.applicationservice.common.model;


import gov.saip.applicationservice.base.model.BaseEntity;
import gov.saip.applicationservice.common.enums.consultation.TradeMarkExaminerTypeEnum;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "examiner_consultation_comments")
@Setter
@Where(clause = "is_deleted = 0")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExaminerConsultationComment extends BaseEntity<Long> {

    @Enumerated(EnumType.STRING)
    private TradeMarkExaminerTypeEnum examinerType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultation_id")
    private ExaminerConsultation examinerConsultation;

    @Column(columnDefinition = "TEXT")
    private String comment;


}
