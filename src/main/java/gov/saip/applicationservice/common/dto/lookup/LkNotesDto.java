package gov.saip.applicationservice.common.dto.lookup;

import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.enums.NotesTypeEnum;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Setter
@Getter
public class LkNotesDto  extends BaseDto<Long> implements Serializable {
    private String code;
    private String nameEn;
    private String nameAr;
    private String descriptionEn;
    private String descriptionAr;
    private String saipCode;
    private String sectionCode;
    private String sectionNameEn;
    private String sectionNameAr;
    private String noteCategoryCode;
    private String noteCategoryAr;
    private String noteCategoryEn;
    private String notesTypeEnum;
    private Boolean done;
    private String notesStep;
    private String attributeCode;

}
