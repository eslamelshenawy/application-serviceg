package gov.saip.applicationservice.base.mapper;

import gov.saip.applicationservice.base.dto.BaseDto;
import org.mapstruct.MappingTarget;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * used to map light dtos that used in single and multi select
 * @param <T>
 * @param <DTO>
 */
public interface BaseLightMapper<T , DTO > {
	DTO mapLight (T t);
	Collection<DTO> mapLight (Collection<T> t);
}
