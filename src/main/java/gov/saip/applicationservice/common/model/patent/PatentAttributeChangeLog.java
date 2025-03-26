package gov.saip.applicationservice.common.model.patent;


import gov.saip.applicationservice.base.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "patent_attribute_change_logs")
@Setter
@Getter

public class PatentAttributeChangeLog extends BaseEntity<Long> {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patent_details_id")
    private PatentDetails patentDetails;
    private String attributeName;
    @Column(columnDefinition = "TEXT")
    private String attributeValue;
    private String taskId;
    private String taskDefinitionKey;

    @Column(columnDefinition = "INT DEFAULT 1")
    private int version = 1;
}
