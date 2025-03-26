package gov.saip.applicationservice.common.dto.consultation.trademarkexaminercomment;


import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.dto.DocumentDto;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExaminerConsultationCommentDto extends BaseDto<Long> {

    private Long id;
    private String examinerType;
    private Long consultationId;
    private String comment;
    private LocalDateTime createdCommentDate;

    
    
}
