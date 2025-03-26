package gov.saip.applicationservice.base.dto;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author nourshaheen
 *
 * @param <ID>
 */
@Setter
@Getter
@MappedSuperclass
public abstract class BaseLkpEntityDto<ID extends Number> implements Serializable {

	private static final long serialVersionUID = 955332705318486542L;

	private ID id;

	private String code;

	private String nameEn;
	private String nameAr;

	public BaseLkpEntityDto() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	public BaseLkpEntityDto(ID id) {
		this.id = id;
	}
}