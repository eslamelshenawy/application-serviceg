package gov.saip.applicationservice.common.model;


import gov.saip.applicationservice.base.model.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "examiner_consultations")
@Setter
@Where(clause = "is_deleted = 0")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExaminerConsultation extends BaseEntity<Long> {
    @Column
    private String userNameSender;
    @Column
    private String userNameReceiver;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id")
    private ApplicationInfo application;
    boolean replayed = false;
    @OneToMany(mappedBy = "examinerConsultation")
    private List<ExaminerConsultationComment> comments;
    @OneToOne
    @JoinColumn(name = "sender_document_id")
    private Document senderDocument;
    @OneToOne
    @JoinColumn(name = "receiver_document_id")
    private Document ReceiverDocument;


}
