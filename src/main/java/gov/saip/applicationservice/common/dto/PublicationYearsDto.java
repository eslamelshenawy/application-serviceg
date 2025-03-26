package gov.saip.applicationservice.common.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PublicationYearsDto {
    List<Integer> publicationYearsGregorian;
    List<Integer> publicationYearsHijri;
}
