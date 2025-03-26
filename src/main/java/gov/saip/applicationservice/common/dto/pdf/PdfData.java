package gov.saip.applicationservice.common.dto.pdf;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PdfData {
    private String path;
    private int pagesNumber;
    private String name;
}
