package gov.saip.applicationservice.common.mapper.patent;



import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.patent.ApplicationPatentDetailsDto;
import gov.saip.applicationservice.common.dto.patent.PatentDetailsDto;
import gov.saip.applicationservice.common.dto.patent.PatentDetailsRequestDto;
import gov.saip.applicationservice.common.model.patent.PatentDetails;
import org.mapstruct.Mapper;

@Mapper
public interface PatentDetailsMapper extends BaseMapper<PatentDetails, PatentDetailsDto> {


    public  PatentDetails mapRequestToEntity(PatentDetailsRequestDto patentDetailsDto);

    public ApplicationPatentDetailsDto mapToApplicationPatentDetailsDto(PatentDetails entity);
}