package gov.saip.applicationservice.base.service;

import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.dto.PaginationDto;

import java.io.Serializable;
import java.util.List;

public interface BaseClientService<DTO extends BaseDto<ID>, ID extends Serializable> {
	List<DTO> findAll();
	public PaginationDto<List<DTO>> findAllPaging(Integer page, Integer limit, String sortableColumn);

	DTO findById(ID id);

	ID insert (DTO entity);

	ID update (DTO entity);
	void deleteById (ID id);

}
