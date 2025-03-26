package gov.saip.applicationservice.common.dto.patent;

import gov.saip.applicationservice.common.dto.DocumentDto;
import gov.saip.applicationservice.common.dto.DocumentLightDto;
import lombok.Data;

import java.time.LocalDate;


@Data
public class PctDto {


    private String pctApplicationNo;

    private LocalDate filingDateGr;

    private String publishNo;

    private String wipoUrl;

    private String petitionNumber;

    private Long patentId;

    private Long id;

    private boolean active;

    private LocalDate internationalPublicationDate;
    private DocumentLightDto pctCopyDocument;


}
