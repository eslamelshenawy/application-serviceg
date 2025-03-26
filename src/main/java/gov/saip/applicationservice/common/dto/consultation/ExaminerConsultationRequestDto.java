package gov.saip.applicationservice.common.dto.consultation;


import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.dto.consultation.trademarkexaminercomment.ExaminerConsultationCommentDto;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExaminerConsultationRequestDto extends BaseDto<Long> {

    private String userNameSender;
    private String userNameReceiver;
    @NotNull
    private Long applicationId;
    private List<ExaminerConsultationCommentDto> comments;
    private Long senderDocumentId;
    private Long receiverDocumentId;

    
    
}
