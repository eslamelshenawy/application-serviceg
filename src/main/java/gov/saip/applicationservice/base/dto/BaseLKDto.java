package gov.saip.applicationservice.base.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class BaseLKDto <ID extends Serializable> {

    private ID id;
    private String code;
    private String nameAr;
    private String nameEn;

}
