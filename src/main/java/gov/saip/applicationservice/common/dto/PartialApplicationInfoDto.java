package gov.saip.applicationservice.common.dto;

import lombok.Data;

@Data
public class PartialApplicationInfoDto {

    Long id;

    String titleAr;

    String titleEn;

    String applicationNumber;

    String requestId;

}