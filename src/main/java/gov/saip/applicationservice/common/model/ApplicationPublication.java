package gov.saip.applicationservice.common.model;


import gov.saip.applicationservice.base.model.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "application_publication")
@Setter
@Getter
@Builder
@Where(clause = "is_deleted = 0 AND ( migration_stage = 0 or migration_stage is null )  ")
@AllArgsConstructor
@NoArgsConstructor
// منشور في النشرة الرسمية ثم في عدد من الجريدة الرسمية بعد مردر فترة محددة
public class ApplicationPublication extends BaseEntity<Long> {
    @ManyToOne
    @JoinColumn
    private ApplicationInfo applicationInfo;

    @ManyToOne
    @JoinColumn
    private LKPublicationType publicationType;

    private LocalDateTime publicationDate;
    
    private String publicationDateHijri;
    
    @ManyToOne
    @JoinColumn
    private Document document;
    
    @ManyToOne
    @JoinColumn(name = "support_service_id")
    private ApplicationSupportServicesType applicationSupportServicesType;
    
// isPublished=true --> published on Gazette / isPublished=false --> published on applicationPublication
    private Boolean isPublished;

    private String publicationNumber;

    @Column(name = "migration_stage")
    private Integer migrationStage;

    
}
