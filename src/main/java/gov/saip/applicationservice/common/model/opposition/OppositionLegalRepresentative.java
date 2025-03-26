package gov.saip.applicationservice.common.model.opposition;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
public class OppositionLegalRepresentative {
    @Column(name = "legal_representative_name")
    private String name;
    @Column(name = "legal_representative_phone")
    private String phone;
    @Column(name = "legal_representative_email")
    private String email;
}
