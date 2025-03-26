package gov.saip.applicationservice.base.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class ReportRequest {


	private String reportName;
	private String fileName;
	private DateWrapper dateWrapper;
	private Map<String, Object> paramters;

}
