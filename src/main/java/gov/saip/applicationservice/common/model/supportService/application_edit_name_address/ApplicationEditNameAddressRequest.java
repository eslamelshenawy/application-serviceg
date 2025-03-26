package gov.saip.applicationservice.common.model.supportService.application_edit_name_address;

import gov.saip.applicationservice.common.enums.support_services_enums.EditTypeEnum;
import gov.saip.applicationservice.common.model.ApplicationSupportServicesType;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "application_edit_name_address_request")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationEditNameAddressRequest extends ApplicationSupportServicesType {
    
    @Enumerated(EnumType.STRING)
    @Column
    private EditTypeEnum editType;
    
    @Column
    private String oldOwnerNameAr;
    
    @Column
    private String newOwnerNameAr;
    
    @Column
    private String oldOwnerNameEn;
    
    @Column
    private String newOwnerNameEn;
    
    @Column
    private String oldOwnerAddressAr;
    
    @Column
    private String newOwnerAddressAr;
    
    @Column
    private String oldOwnerAddressEn;
    
    @Column
    private String newOwnerAddressEn;
    
    @Column
    private String notes;
    
}
