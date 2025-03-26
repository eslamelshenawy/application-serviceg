package gov.saip.applicationservice.common.service.impl;


import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.dto.ApplicationInfoDto;
import gov.saip.applicationservice.common.dto.ApplicationStatusDto;
import gov.saip.applicationservice.common.enums.ApplicationStatusEnum;
import gov.saip.applicationservice.common.enums.SupportServiceRequestStatusEnum;
import gov.saip.applicationservice.common.enums.SupportServiceType;
import gov.saip.applicationservice.common.enums.SupportedServiceCode;
import gov.saip.applicationservice.common.model.ApplicationSupportServicesType;
import gov.saip.applicationservice.common.model.LKSupportServiceType;
import gov.saip.applicationservice.common.repository.LKSupportServiceTypeRepository;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.ApplicationSupportServicesTypeService;
import gov.saip.applicationservice.common.service.LKSupportServiceTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static gov.saip.applicationservice.common.enums.SupportedServiceCode.WITHDRAW_APPLICATION_IF_PENDING;
import static gov.saip.applicationservice.common.enums.SupportedServiceCode.WITHDRAW_APPLICATION_UNLESS_FINAL_ACTION_ISSUED_BY_EXAMINERS_OFFICE;


@Service
@RequiredArgsConstructor
@Transactional
public class LKSupportServiceTypeServiceImpl extends BaseServiceImpl<LKSupportServiceType, Long> implements LKSupportServiceTypeService {

    private final LKSupportServiceTypeRepository lkSupportServiceTypeRepository;
    private final ApplicationSupportServicesTypeService applicationSupportServicesTypeService;
    private final ApplicationInfoService applicationInfoService;
    @Override
    protected BaseRepository<LKSupportServiceType, Long> getRepository() {
        return lkSupportServiceTypeRepository;
    }

    @Override
    public List<LKSupportServiceType> getAllByRequestType(String requestType, Long id) {
        List<LKSupportServiceType> lkSupportServiceTypes = lkSupportServiceTypeRepository.findByType(SupportServiceType.valueOf(requestType));

        if (isRetraction(requestType, id)) {
            removeUnsupportedRetractionTypes(id, lkSupportServiceTypes);
        }

        return lkSupportServiceTypes;
    }

    private boolean isRetraction(String requestType, Long id) {
        return Objects.nonNull(id) && SupportServiceType.RETRACTION.name().equalsIgnoreCase(requestType);
    }

    private void removeUnsupportedRetractionTypes(Long id, List<LKSupportServiceType> lkSupportServiceTypes) {
        ApplicationInfoDto applicationInfoDto = applicationInfoService.getApplication(id);
        List<String> codesToRemove = new ArrayList<>();
        if (isWaivedOrAccepted(applicationInfoDto.getApplicationStatus())) {
            codesToRemove.add(WITHDRAW_APPLICATION_UNLESS_FINAL_ACTION_ISSUED_BY_EXAMINERS_OFFICE.name());
        }
        if (!hasPendingSupportServices(id)) {
            codesToRemove.add(WITHDRAW_APPLICATION_IF_PENDING.name());
        }
        lkSupportServiceTypes.removeIf(lkSupportServiceType -> {
            String code = lkSupportServiceType.getCode() != null ? lkSupportServiceType.getCode().toString() : null;
            return codesToRemove.contains(code);
        });
    }

    private boolean isWaivedOrAccepted(ApplicationStatusDto applicationStatus) {
        String statusCode = applicationStatus.getCode();
        return statusCode.equals(ApplicationStatusEnum.WAIVED.name()) ||
                statusCode.equals(ApplicationStatusEnum.ACCEPTANCE.name());
    }

    private boolean hasPendingSupportServices(Long appId) {
        List<ApplicationSupportServicesType> appSupportServices =
                applicationSupportServicesTypeService.getAllByApplicationId(appId);
        return appSupportServices.stream()
                .anyMatch(supportService ->
                        supportService.getRequestStatus().getCode()
                                .equals(SupportServiceRequestStatusEnum.PENDING.name()));
    }

    @Override
    public LKSupportServiceType findByCode(SupportedServiceCode code) {
        return lkSupportServiceTypeRepository.findByCode(code);
    }
}
