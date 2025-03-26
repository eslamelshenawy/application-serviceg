package gov.saip.applicationservice.common.model.industrial;

import gov.saip.applicationservice.base.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Setter
@Getter
@Entity
@Where(clause = "is_deleted = 0")
@Table(name = "design_sample_drawings")
public class  DesignSampleDrawings extends BaseEntity<Long> {


  private  boolean main;

  private  Long docId;

  @ManyToOne
  @JoinColumn(name = "shape_id")
  private LkShapeType shape;


  private  Long designSampleId;

  private  boolean doc3d;

}
