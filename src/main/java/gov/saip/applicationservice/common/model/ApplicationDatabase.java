package gov.saip.applicationservice.common.model;


import gov.saip.applicationservice.base.model.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "application_databases")
@Setter
@Getter
@NoArgsConstructor
public class ApplicationDatabase extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id")
    private ApplicationInfo applicationInfo;
    @ManyToOne
    @JoinColumn(name = "database_id")
    private LkDatabase database;
    private boolean other;
    private String otherDatabaseName;

}
