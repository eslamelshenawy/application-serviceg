package gov.saip.applicationservice.common.dto.industrial;

import gov.saip.applicationservice.common.dto.ApplicationRelevantTypeDto;
import gov.saip.applicationservice.common.dto.LkClassificationUnitDto;
import gov.saip.applicationservice.common.dto.SubClassificationDto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.TreeSet;

@Setter
@Getter
public class DesignSampleResDto implements Comparable<DesignSampleResDto> {


    private Long id;
    private String name;
    private TreeSet<DesignSampleDrawingsResDto> designSampleDrawings;

    private List<ApplicationRelevantTypeDto> applicationRelevantTypes;
    private List<SubClassificationDto> subClassifications;

    private ArrayList<LkClassificationUnitDto> unitList;
    private String description;
    private Integer code;

    @Override
    public int compareTo(DesignSampleResDto o) {
        if (o.getId() < this.getId()) return 1;
        if (o.getId() > this.getId()) return -1;
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DesignSampleResDto that = (DesignSampleResDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getIdStr() {
        return id == null ? null : id.toString();
    }

    private String idStr;

    private int isDeleted;

}
