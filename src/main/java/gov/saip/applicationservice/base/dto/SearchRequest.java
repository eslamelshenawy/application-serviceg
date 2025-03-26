package gov.saip.applicationservice.base.dto;

import lombok.*;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class SearchRequest implements Serializable{

	private static final long serialVersionUID = 3687907043030893075L;

	private Integer pageNo = 0;
	private Integer pageLength = 10;
	private String sortableColumn = "id" ;

	private DateWrapper dateWrapper;
	private List<FilterDto> filters = new ArrayList<>();
	private List<SortingDto>  sorting = new ArrayList<>();


	public void addFilter(FilterDto dto)
	{
		if(getFilters() == null)
		{
			setFilters(new ArrayList<>());
		}
		getFilters().add(dto);
	}

	public FilterDto getName (){
		Optional<FilterDto> dto = filters
				.stream()
				.filter(filter -> filter.getColumnName().equalsIgnoreCase("name"))
				.findAny();
	 return dto.isPresent() ? dto.get() : null;
	}


	public String getPropertyValue (String property){
		Optional<FilterDto> dto = filters
				.stream()
				.filter(filter -> filter.getColumnName().equalsIgnoreCase(property))
				.findAny();
		return dto.isPresent()  ? dto.get().getColumnValue() : null;
	}

	public List<String> getPropertyValues (String property){
		Optional<FilterDto> dto = filters
				.stream()
				.filter(filter -> filter.getColumnName().equalsIgnoreCase(property))
				.findAny();
		return dto.isPresent() ? dto.get().getColumnValues() : null;
	}
	public List<Long> getPropertyValuesLong (String property){
		Optional<FilterDto> dto = filters
				.stream()
				.filter(filter -> filter.getColumnName().equalsIgnoreCase(property))
				.findAny();
		return dto.isPresent() ? dto.get().getColumnValues()
				.stream()
				.map(value -> Long.valueOf(value)).
				collect(Collectors.toList()) : null;
	}
	public List<Integer> getPropertyValuesInt (String property){
		Optional<FilterDto> dto = filters
				.stream()
				.filter(filter -> filter.getColumnName().equalsIgnoreCase(property))
				.findAny();
		return dto.isPresent() && dto.get().getColumnValues() != null && !dto.get().getColumnValues().isEmpty() ? dto.get().getColumnValues()
				.stream()
				.map(value -> Integer.valueOf(value)).
				collect(Collectors.toList()) : null;
	}

	public List<Sort.Order> getOrders () {
		List<Sort.Order> orders = new ArrayList<Sort.Order>();
		sorting.forEach(sort -> {
			Sort.Order order = new Sort.Order(sort.getSortType().equalsIgnoreCase(SortableTypeEnum.ASC.name())
					? Sort.Direction.ASC : Sort.Direction.DESC, sort.getSortName());
			orders.add(order);
	  });

		if (orders.isEmpty()){
			Sort.Order order = new Sort.Order(	Sort.Direction.DESC, "id");
			orders.add(order);
		}

		return orders;
	}

}