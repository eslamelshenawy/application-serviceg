package gov.saip.applicationservice.base.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 *
 * @author nourshaheen
 *
 * @param <ID>
 */
@Setter
@Getter
@MappedSuperclass
public abstract class BaseLkEntity<ID > implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 3855054033844070951L;
	@SequenceGenerator(name = "id_seq",
			sequenceName = "id_seq",
			allocationSize = 1, initialValue = 1)

	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_seq")
	@Id
	@Schema(description = "The database generated ID", required = true)
	private ID id;
	@NotEmpty
	private String code;
	@NotEmpty
	private String nameAr;
	@NotEmpty
	private String nameEn;

	public BaseLkEntity(ID id, String code, String nameAr, String nameEn) {
		this.id = id;
		this.code = code;
		this.nameAr = nameAr;
		this.nameEn = nameEn;
	}

	public BaseLkEntity(String code) {
		this.code = code;
	}

	protected BaseLkEntity() {
		super();
	}
	protected BaseLkEntity(ID id) {
		this.id = id;
	}


}
