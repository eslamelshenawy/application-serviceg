package gov.saip.applicationservice.common.model;

import gov.saip.applicationservice.base.model.BaseLkEntity;
import gov.saip.applicationservice.util.Utilities;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "lk_support_service_request_status")
@Setter
@Getter
@NoArgsConstructor
public class LKSupportServiceRequestStatus extends BaseLkEntity<Integer> {
    @NotEmpty
    private String nameArExternal;
    @NotEmpty
    private String nameEnExternal;
    public LKSupportServiceRequestStatus(Integer id) {
        super(id);
    }

    public LKSupportServiceRequestStatus(String code) {
        super(code);
    }

    @Override
    public String getNameEn() {
        if(Utilities.isExternal())
            return nameEnExternal;
        return super.getNameEn();
    }

    @Override
    public String getNameAr() {
        if(Utilities.isExternal())
            return nameArExternal;
        return super.getNameAr();
    }
}
