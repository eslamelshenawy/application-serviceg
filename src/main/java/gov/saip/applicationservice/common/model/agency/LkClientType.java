package gov.saip.applicationservice.common.model.agency;

import gov.saip.applicationservice.base.model.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "lk_client_type")
@NoArgsConstructor
public class LkClientType extends BaseEntity<Integer> {

    @Enumerated(EnumType.STRING)
    private ClientType code;

    @Column
    private String typeEn;

    @Column
    private String typeAr;

    public LkClientType(Integer id) {
        super(id);
    }
}
