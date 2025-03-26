package gov.saip.applicationservice.common.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListBodyDto<T> {
    List<T> data;
}
