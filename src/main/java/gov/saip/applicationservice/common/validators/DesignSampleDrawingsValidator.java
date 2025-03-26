package gov.saip.applicationservice.common.validators;


import gov.saip.applicationservice.common.dto.industrial.DesignSampleDrawingsReqDto;
import gov.saip.applicationservice.common.dto.industrial.DesignSampleReqDto;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DesignSampleDrawingsValidator {
    public void validateDesignSampleDrawings(DesignSampleReqDto designSampleReqDto) {
        List<DesignSampleDrawingsReqDto> drawings = designSampleReqDto.getDesignSampleDrawings();
        int numMainAttachments = countMainAttachments(drawings);
        if (numMainAttachments != 1) {
            throw new BusinessException(Constants.ErrorKeys.EXACTLY_ONE_MAIN_ATTACHMENT_REQUIRED, HttpStatus.BAD_REQUEST, null);
        }
        if (drawings.size() > 7) {
            throw new BusinessException(Constants.ErrorKeys.NUMBER_OF_ATTACHMENT_EXCEEDED_ATTACHE_ONLY_SEVEN, HttpStatus.BAD_REQUEST, null);
        }
    }

    private int countMainAttachments(List<DesignSampleDrawingsReqDto> drawings) {
        int count = 0;
        for (DesignSampleDrawingsReqDto drawing : drawings) {
            if (drawing.isMain()) {
                count++;
            }
        }
        return count;
    }
}
