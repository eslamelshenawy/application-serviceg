package gov.saip.applicationservice.common.dto.industrial;

import gov.saip.applicationservice.common.dto.DocumentDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
public class DesignSampleDrawingsResDto implements Comparable<DesignSampleDrawingsResDto> {


    private Long id;

    private boolean main;

    private Long docId;

    private DocumentDto document;

    private LkShapeTypeResDto shape;

    private boolean doc3d;

    @Override
    public int compareTo(DesignSampleDrawingsResDto o) {
        if (o.getId() < this.getId()) return 1;
        if (o.getId() > this.getId()) return -1;
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DesignSampleDrawingsResDto that = (DesignSampleDrawingsResDto) o;
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
}
