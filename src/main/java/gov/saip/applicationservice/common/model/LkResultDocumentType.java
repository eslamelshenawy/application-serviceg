package gov.saip.applicationservice.common.model;


import gov.saip.applicationservice.base.model.BaseLkEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "lk_result_document_types")
@Setter
@Getter
@NoArgsConstructor
public class LkResultDocumentType extends BaseLkEntity<Integer> {
    

}
