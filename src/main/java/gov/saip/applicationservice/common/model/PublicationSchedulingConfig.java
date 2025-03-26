package gov.saip.applicationservice.common.model;


import gov.saip.applicationservice.base.model.BaseEntity;
import gov.saip.applicationservice.common.enums.PublicationFrequency;
import gov.saip.applicationservice.exception.BusinessException;
import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "publication_scheduling_config")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PublicationSchedulingConfig extends BaseEntity<Long> {
    @Enumerated(EnumType.STRING)
    private PublicationFrequency publicationFrequency;

    @ManyToOne
    @JoinColumn
    private LkApplicationCategory applicationCategory; // We look up configs using this

    @JoinColumn(name = "publication_scheduling_config_id")
    @OneToMany(cascade = CascadeType.ALL)
    private Set<@Valid PublicationTime> publicationTimes;

    public LocalDateTime calculateNextIssueDate(Clock clock, int issueCutOffInDays) {
        LocalDateTime nextIssueDate = publicationTimes
                .stream()
                .map(publicationTime -> publicationTime.toIssuingDate(clock, issueCutOffInDays, publicationFrequency))
                .sorted()
                .findFirst()
                .orElseThrow(() -> new BusinessException("No next issue date found"));
        return nextIssueDate;
    }
}
