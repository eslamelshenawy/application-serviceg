package gov.saip.applicationservice.common.model;

import gov.saip.applicationservice.base.model.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "application_drawing", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"application_id", "document_id"})
})
@Setter
@Getter
@NoArgsConstructor
public class ApplicationDrawing extends BaseEntity<Long>  {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id")
    private ApplicationInfo applicationInfo;

    @OneToOne
    @JoinColumn(name = "document_id")
    private Document document;

    private String title;
    private String numbering;
    private boolean isDefault;
}
