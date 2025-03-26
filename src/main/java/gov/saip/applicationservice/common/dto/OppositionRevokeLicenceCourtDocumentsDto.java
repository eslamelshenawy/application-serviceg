package gov.saip.applicationservice.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class OppositionRevokeLicenceCourtDocumentsDto {
    private Long id;
    private List<Long> documentIds;
    private String courtDocumentNotes;
}
