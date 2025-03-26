package gov.saip.applicationservice.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class DocumentWithTypeDto extends DocumentLightDto{
    private LKDocumentTypeDto documentType;
}
