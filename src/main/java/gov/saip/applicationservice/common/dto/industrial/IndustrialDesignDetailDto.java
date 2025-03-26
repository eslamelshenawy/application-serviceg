package gov.saip.applicationservice.common.dto.industrial;

import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.model.industrial.RequestTypeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.TreeSet;

@Setter
@Getter
public class IndustrialDesignDetailDto {

    private Long id;
    public String getIdStr() {
        return id == null ? null : id.toString();
    }
    private String idStr;
    @NotNull
    private Long applicationId;
    private String explanationAr;
    private String explanationEn;
    private String usageAr;
    private String usageEn;
    private boolean secret;
    private boolean haveExhibition;
    private String exhibitionInfo;
    private LocalDate exhibitionDate;
    private boolean haveRevealedToPublic;
    private boolean viaMyself ;
    private LocalDate detectionDate;
    @Enumerated(EnumType.STRING)
    private RequestTypeEnum requestType;
    private TreeSet<DesignSampleResDto> designSamples;

}
