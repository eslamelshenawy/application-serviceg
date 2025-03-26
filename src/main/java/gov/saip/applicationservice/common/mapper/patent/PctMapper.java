package gov.saip.applicationservice.common.mapper.patent;



import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.patent.PctDto;
import gov.saip.applicationservice.common.dto.patent.PctRequestDto;
import gov.saip.applicationservice.common.model.patent.Pct;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper()
public interface PctMapper extends BaseMapper<Pct, PctDto> {


    public Pct mapRequestToEntity(PctRequestDto pctRequestDto);


    void updatePctFromRequestDto(PctRequestDto dto, @MappingTarget Pct pct);

}