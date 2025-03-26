package gov.saip.applicationservice.common.model.supportService.application_edit_trademark_image_request;

import gov.saip.applicationservice.common.model.ApplicationSupportServicesType;
import gov.saip.applicationservice.common.model.Document;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "application_edit_trademark_image_request")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationEditTrademarkImageRequest extends ApplicationSupportServicesType {
    
    @ManyToOne
    @JoinColumn(name = "old_document_id")
    private Document oldDocument;
    
    @ManyToOne
    @JoinColumn(name = "new_document_id")
    private Document newDocument;
    
    @Column
    private String oldDescription;
    
    @Column
    private String newDescription;
    
    @Column
    private String oldNameAr;
    
    @Column
    private String newNameAr;
    
    @Column
    private String oldNameEn;
    
    @Column
    private String newNameEn;
    
    
}
