package gov.saip.applicationservice.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import gov.saip.applicationservice.common.enums.CommenterType;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CustomerExtClassifyCommentsDto {

    private Long id;
    private String commenterName;
    private CommenterType commenterType;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime commentDate;
    private String comment;
    private Long custExtClassifyParentCommentId;
    private Long custExtClassifyId;
    private List<CustomerExtClassifyCommentsDto> childrenCustExtComments;
}
