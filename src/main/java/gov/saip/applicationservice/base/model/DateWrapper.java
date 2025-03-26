package gov.saip.applicationservice.base.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
public class DateWrapper implements Serializable {

	private static final long serialVersionUID = -8077291701392660238L;

	private LocalDateTime fromDate;
	private LocalDateTime toDate;
}