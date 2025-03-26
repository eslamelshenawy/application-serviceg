package gov.saip.applicationservice.common.mapper.installment;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.installment.*;
import gov.saip.applicationservice.common.model.installment.ApplicationInstallment;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper
public interface ApplicationInstallmentMapper extends BaseMapper<ApplicationInstallment, ApplicationInstallmentDto> {

    InstallmentNotificationProjectionDto mapInstallmentNotificationProjection(InstallmentNotificationProjection dto);

    InstallmentStatusBannerDto mapApplicationInstallmentLightProjectionToBanner(ApplicationInstallmentLightProjection projection);
}
