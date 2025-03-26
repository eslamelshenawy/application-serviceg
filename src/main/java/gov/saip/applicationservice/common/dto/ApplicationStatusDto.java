package gov.saip.applicationservice.common.dto;

import lombok.Data;

import java.io.Serializable;


@Data
public class ApplicationStatusDto implements Serializable {

//    appId
    private long id;

    private String ipsStatusDescEn;

    private String ipsStatusDescAr;

    private String ipsStatusDescEnExternal;

    private String ipsStatusDescArExternal;

    private String code;

    private String applicationNumber;

    private String titleAr;

    private String titleEn;




}
