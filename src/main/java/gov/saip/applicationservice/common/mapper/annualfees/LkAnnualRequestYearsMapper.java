package gov.saip.applicationservice.common.mapper.annualfees;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.annualfees.AnnualFeesRequestDto;
import gov.saip.applicationservice.common.dto.annualfees.LkAnnualRequestYearsDto;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.annual_fees.AnnualFeesRequest;
import gov.saip.applicationservice.common.model.annual_fees.LkAnnualRequestYears;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Objects;

@Mapper
public interface LkAnnualRequestYearsMapper extends BaseMapper<LkAnnualRequestYears, LkAnnualRequestYearsDto> {




}
