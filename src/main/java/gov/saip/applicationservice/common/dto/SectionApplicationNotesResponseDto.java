package gov.saip.applicationservice.common.dto;

import lombok.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SectionApplicationNotesResponseDto {
    private Integer id;
    private String code;
    private String nameEn;
    private String nameAr;
    private List<ApplicationNotesListDto> applicationNotes;


}
