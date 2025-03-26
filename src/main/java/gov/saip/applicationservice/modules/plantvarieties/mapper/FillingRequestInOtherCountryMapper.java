package gov.saip.applicationservice.modules.plantvarieties.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.modules.plantvarieties.dto.DUSTestingDocumentListDto;
import gov.saip.applicationservice.modules.plantvarieties.dto.FillingRequestInOtherCountryDto;
import gov.saip.applicationservice.modules.plantvarieties.model.DUSTestingDocument;
import gov.saip.applicationservice.modules.plantvarieties.model.FillingRequestInOtherCountry;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface FillingRequestInOtherCountryMapper extends BaseMapper<FillingRequestInOtherCountry, FillingRequestInOtherCountryDto> {
    List<FillingRequestInOtherCountryDto> mapToListOfFillingRequestInOtherCountryListDto(List<FillingRequestInOtherCountry> entity);

}
