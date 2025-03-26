package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.common.dto.lookup.LKNexuoUserDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class DocumentDto implements Serializable {


    private long id;

    private String nexuoId;

    private Long appId;
    private Date uploadedDate;
    private LKDocumentTypeDto documentType;
    private LKNexuoUserDto lkNexuoUser;
    private String fileName;
    private String fileReviewUrl;
    private List<ApplicationDocumentCommentDto> comments;
    private int isDeleted;

    public DocumentDto(long id) {
        this.id = id;
    }
}
