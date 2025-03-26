package gov.saip.applicationservice.base.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Setter
@Getter
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public  class AuditTrail {

	@Schema(description = "Audit Column")
	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdDate;

	@Schema(description = "Audit Column")
	@CreatedBy
	@Column(updatable = false)
	private String createdByUser;

	@Schema(description = "Audit Column")
	@LastModifiedDate
	private LocalDateTime modifiedDate;

	@Schema(description = "Audit Column")
	@LastModifiedBy
	private String modifiedByUser;

	protected AuditTrail(LocalDateTime dateTime) {
		this.createdDate = dateTime;
	}

}
