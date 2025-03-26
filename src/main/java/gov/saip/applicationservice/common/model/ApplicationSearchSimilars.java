package gov.saip.applicationservice.common.model;


import gov.saip.applicationservice.base.model.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "application_search_similars")
public class ApplicationSearchSimilars extends BaseEntity<Long> {
    @Column
    private String brandNameAr;
    @Column
    private String brandNameEn;
    @Column
    private String filingNumber;
    @Column
    private String status;
    @Column
    private LocalDateTime filingDate;
    @ManyToOne
    @JoinColumn(name = "application_search_id")
    private ApplicationSearch applicationSearch;

}
