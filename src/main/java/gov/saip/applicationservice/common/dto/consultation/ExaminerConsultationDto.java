package gov.saip.applicationservice.common.dto.consultation;


import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.dto.DocumentDto;
import gov.saip.applicationservice.common.dto.consultation.trademarkexaminercomment.ExaminerConsultationCommentDto;
import gov.saip.applicationservice.common.model.ExaminerConsultationComment;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExaminerConsultationDto extends BaseDto<Long> {

    private String userNameSender;
    private String userNameReceiver;
    @NotNull
    private Long applicationId;
    private boolean replayed;
    private List<ExaminerConsultationCommentDto> comments;
    private DocumentDto senderDocument;

    private DocumentDto receiverDocument;

    
    
}
