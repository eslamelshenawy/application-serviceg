package gov.saip.applicationservice.common.model;

import gov.saip.applicationservice.base.model.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "similar_trademark")
@Where(clause = "is_deleted = 0")
@Setter
@Getter
@NoArgsConstructor
public class SimilarTrademark extends BaseEntity<Long> {

    private String taskDefinitionKey;
    private String taskInstanceId;
    private String imageLink;
    private String trademarkNumber;
    private String previewLink;
    private Long ipsId;
    @ManyToOne
    @JoinColumn(name = "application_id")
    private ApplicationInfo applicationInfo;
}
