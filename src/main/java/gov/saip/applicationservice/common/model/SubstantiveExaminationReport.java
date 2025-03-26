package gov.saip.applicationservice.common.model;


import gov.saip.applicationservice.base.model.BaseEntity;
import gov.saip.applicationservice.common.enums.ExaminerReportType;
import gov.saip.applicationservice.common.enums.ReportDecisionEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "substantive_examination_reports")
@Setter
@Getter
@NoArgsConstructor
public class SubstantiveExaminationReport extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_info_id")
    private ApplicationInfo applicationInfo;
    @Enumerated(EnumType.STRING)
    private ExaminerReportType type;
    private String links;
    @ManyToMany
    @JoinTable(name = "sub_exam_report_documents",
            uniqueConstraints = @UniqueConstraint(columnNames={"sub_exam_report_id", "document_id"} ) ,
            joinColumns = @JoinColumn(name = "sub_exam_report_id"),
            inverseJoinColumns = @JoinColumn(name = "document_id"))
    private List<Document> documents = new ArrayList<>();
    @Column(columnDefinition = "TEXT")
    private String examinerOpinion;
    @Column(columnDefinition = "TEXT")
    private String examinerRecommendation;
    @Enumerated(EnumType.STRING)
    private ReportDecisionEnum decision;
    public SubstantiveExaminationReport(Long id) {
        super(id);
    }

}
