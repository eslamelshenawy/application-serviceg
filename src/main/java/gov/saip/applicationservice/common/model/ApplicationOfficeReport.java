package gov.saip.applicationservice.common.model;


import gov.saip.applicationservice.base.model.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "application_office_reports")
@Setter
@Getter
@NoArgsConstructor
public class ApplicationOfficeReport extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id")
    private ApplicationInfo applicationInfo;
    @ManyToOne
    @JoinColumn(name = "document_id")
    private Document document;
    @ManyToOne
    @JoinColumn(name = "office_id")
    private LkExaminationOffice office;



}
