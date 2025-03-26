package gov.saip.applicationservice.base.service;


import gov.saip.applicationservice.base.client.BaseClient;
import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.List;


public abstract class BaseClientServiceImpl<DTO extends BaseDto<ID>, ID extends Serializable> implements BaseClientService<DTO, ID> {

	@Autowired
	private BaseClient<DTO, ID> baseClient;

	protected BaseClientServiceImpl() {
	}

	@Override
	public List<DTO> findAll() {
		return baseClient.findAll().getPayload();
	}

	@Override
	public PaginationDto<List<DTO>> findAllPaging(Integer page, Integer limit, String sortableColumn) {
		return baseClient.findAllPaging(page, limit, sortableColumn).getPayload();
	}

	@Override
	public DTO findById(ID id) {
		DTO dto = baseClient.findById(id).getPayload();
		if (dto == null) {
			String [] params = {id.toString()};
			new BusinessException(Constants.ErrorKeys.EXCEPTION_RECORD_NOT_FOUND, HttpStatus.NOT_FOUND, params);
		}
		return dto;
	}

	@Override
	public ID insert(DTO dto) {
		return baseClient.insert(dto).getPayload();

	}

	@Override
	public ID update(DTO dto) {
		return baseClient.update(dto).getPayload();
	}

	@Override
	public void deleteById(ID id) {
		baseClient.deleteById(id);
	}


}
