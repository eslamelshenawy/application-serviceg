package gov.saip.applicationservice.common.dto.lookup;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class LkSupportServiceRequestStatusDto implements Serializable {
    private Integer id;
    private String code;
    private String nameAr;
    private String nameEn;
    private String nameArExternal;
    private String nameEnExternal;

}
