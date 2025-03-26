package gov.saip.applicationservice.base.mapper;

import gov.saip.applicationservice.base.dto.BaseDto;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.chrono.HijrahChronology;
import java.time.chrono.HijrahDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

/**
 *
 * @author nourshaheen
 *
 * @param <T>
 * @param <DTO>
 */
public interface BaseMapper<T , DTO > {
	/**
	 *
	 * @param t
	 * @return
	 */
	DTO map (T t);
	/**
	 *
	 * @param dto
	 * @return
	 */
	T unMap (DTO dto);

	BaseDto mapToBase (T t);
//	List<BaseDto> mapToBase (List<T> t);
//	Set<BaseDto> mapToBase (Set<T> t);
	/**
	 *
	 * @param t
	 * @param dto
	 */
	T unMap (@MappingTarget T t , DTO dto);
	/**
	 *
	 * @param t
	 * @return
	 */
	List<DTO> map (List<T> t);

	Set<DTO> map (Set<T> t);
	/**
	 *
	 * @param dto
	 * @return
	 */
	List<T> unMap (List<DTO> dto);

}
