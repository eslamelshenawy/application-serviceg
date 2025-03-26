package gov.saip.applicationservice.common.dto;

import lombok.*;

import javax.persistence.Column;

//Michael get application info payment
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationInfoPaymentDto {
    private Integer normalPagesNumber;
    private Integer claimPagesNumber;
    private Integer shapesNumber;
    private Long totalCheckingFee;
    private Long parentElementsCount;
    private Long childrenElementsCount;
    private Long unPaidApplicationReleventTypeCount;
    private Long totalPagesNumber;

}
