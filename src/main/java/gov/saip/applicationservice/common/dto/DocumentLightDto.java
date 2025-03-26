package gov.saip.applicationservice.common.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
public class DocumentLightDto {

    private long id;
    private String nexuoId;
    private Date uploadedDate;
    private String fileName;
    private String fileReviewUrl;
}
