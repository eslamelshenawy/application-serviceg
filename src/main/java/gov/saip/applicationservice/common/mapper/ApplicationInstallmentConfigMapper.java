package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.ApplicationInstallmentConfigDto;
import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import gov.saip.applicationservice.common.enums.installment.InstallmentType;
import gov.saip.applicationservice.common.model.installment.ApplicationInstallmentConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface ApplicationInstallmentConfigMapper extends BaseMapper<ApplicationInstallmentConfig , ApplicationInstallmentConfigDto> {



    @Override
    @Mapping(source = "applicationCategory", target = "applicationCategory", qualifiedByName = "mapApplicationCategory")
    @Mapping(source = "installmentType", target = "installmentType", qualifiedByName = "mapInstallmentType")
    ApplicationInstallmentConfigDto map(ApplicationInstallmentConfig applicationInstallmentConfig);


    @Named("mapApplicationCategory")
    default String mapApplicationCategory(ApplicationCategoryEnum applicationCategory) {
        return applicationCategory == null ? null : applicationCategory.name();
    }

    @Named("mapInstallmentType")
    default String mapInstallmentType(InstallmentType installmentType) {
        return installmentType == null ? null : installmentType.name();
    }
}
