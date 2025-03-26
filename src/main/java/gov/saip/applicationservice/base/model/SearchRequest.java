package gov.saip.applicationservice.base.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ToString
@Setter
@Getter
public class SearchRequest implements Serializable{

	private static final long serialVersionUID = 3687907043030893075L;

	private Integer pageNumber = 0;
	private Integer pageSize = 10;
	private String sortableColumn = "id" ;
	private Integer sortableType = 2 ;    //  1 for ascending , 2 for descending
	private DateWrapper dateWrapper;
	private List<Long> branchIds;
	private List<Integer> statusIds;
	private Map<String, Object> searchCriteria = new HashMap<>();


	public void addSearchCriteria(String key, Object value)
	{
		if(getSearchCriteria() == null)
		{
			setSearchCriteria(new HashMap<>());
		}
		getSearchCriteria().put(key, value);
	}
}