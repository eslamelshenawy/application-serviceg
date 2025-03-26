package gov.saip.applicationservice.common.model;


import gov.saip.applicationservice.base.model.BaseEntity;
import gov.saip.applicationservice.util.Utilities;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "lk_application_status")
@Setter
@Getter
public class LkApplicationStatus extends BaseEntity<Long> {
    @Column
    private String ipsStatusDescEn;

    @Column
    private String ipsStatusDescAr;

    @Column
    private String ipsStatusDescArExternal;
    @Column
    private String ipsStatusDescEnExternal;

    @Column
    private String code;

    @ManyToOne
    @JoinColumn(name = "application_category_id")
    private LkApplicationCategory applicationCategory;

    public LkApplicationStatus(Long aLong, String ipsStatusDescEn, String ipsStatusDescAr, String code) {
        super(aLong);
        this.ipsStatusDescEn = ipsStatusDescEn;
        this.ipsStatusDescAr = ipsStatusDescAr;
        this.code = code;
    }

    public LkApplicationStatus() {

    }

    public LkApplicationStatus(Long id) {
        super(id);
    }

    public LkApplicationStatus(String code) {
        this.code = code;
    }


    public String getIpsStatusDescAr() {
        if(Utilities.isExternal())
            return ipsStatusDescArExternal;
        return ipsStatusDescAr;
    }

    public String getIpsStatusDescEn() {
        if(Utilities.isExternal())
            return ipsStatusDescEnExternal;
        return ipsStatusDescEn;
    }
}
