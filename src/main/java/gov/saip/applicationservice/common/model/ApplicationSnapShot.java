package gov.saip.applicationservice.common.model;


import gov.saip.applicationservice.base.model.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "application_versions")
@Setter
@Where(clause = "is_deleted = 0")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationSnapShot extends BaseEntity<Long> {



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id")
    private ApplicationInfo application;

    @Column(columnDefinition = "TEXT")
    private String applicationInfoDto;
    @Column(columnDefinition = "TEXT")
    private String patentRequestDto;
    @Column(columnDefinition = "TEXT")
    private String patentDetailDtoWithChangeLogDto;
    @Column(columnDefinition = "TEXT")
    private String pctDto;
    @Column(columnDefinition = "TEXT",name = "drawing_dto")
    private String drawingDto;
    @Column(columnDefinition = "TEXT",name = "protection_elements_dto")
    private String protectionElementsDto;

    @Column(name="version_number")
    private Integer versionNumber;

    //TODO version int




}
