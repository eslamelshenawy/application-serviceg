package gov.saip.applicationservice.common.dto.patent;

import lombok.Data;

import java.io.Serializable;


@Data
public class ApplicationStatusDto implements Serializable {

    private long id;
    private String ipsStatusDescEn;


    private String ipsStatusDescAr;


}
