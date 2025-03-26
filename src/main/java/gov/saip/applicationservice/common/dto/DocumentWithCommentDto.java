package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.common.dto.lookup.LKNexuoUserDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Setter
@Getter
public class DocumentWithCommentDto implements Serializable {


    private long id;
    private String nexuoId;
    private Date uploadedDate;
    private LKDocumentTypeDto documentType;
    private LKNexuoUserDto lkNexuoUser;
    private String fileName;
    private String fileReviewUrl;
    private List<ApplicationDocumentCommentDto> comments;
}
