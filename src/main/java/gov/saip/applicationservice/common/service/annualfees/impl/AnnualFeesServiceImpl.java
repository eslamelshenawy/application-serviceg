package gov.saip.applicationservice.common.service.annualfees.impl;

import gov.saip.applicationservice.common.dto.ApplicationNumberGenerationDto;
import gov.saip.applicationservice.common.enums.RequestActivityLogEnum;
import gov.saip.applicationservice.common.enums.SupportServiceRequestStatusEnum;
import gov.saip.applicationservice.common.enums.SupportServiceType;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.annual_fees.AnnualFeesRequest;
import gov.saip.applicationservice.common.repository.SupportServiceRequestRepository;
import gov.saip.applicationservice.common.repository.annualfees.AnnualFeesRequestRepository;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.annualfees.AnnualFeesService;
import gov.saip.applicationservice.common.service.impl.SupportServiceRequestServiceImpl;
import gov.saip.applicationservice.common.service.installment.ApplicationInstallmentService;
import gov.saip.applicationservice.common.util.SupportServiceActivityLogHelper;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@RequiredArgsConstructor
public class AnnualFeesServiceImpl extends SupportServiceRequestServiceImpl<AnnualFeesRequest> implements AnnualFeesService {


    private final ApplicationInfoService applicationInfoService;
    private final ApplicationInstallmentService applicationInstallmentService;
    private final AnnualFeesRequestRepository annualFeesRequestRepository;
    private final SupportServiceActivityLogHelper supportServiceActivityLogHelper;

    @Override
    public SupportServiceRequestRepository getSupportServiceRequestRepository() {
        return annualFeesRequestRepository;
    }

    @Override
    public AnnualFeesRequest insert(AnnualFeesRequest entity) {

        AnnualFeesRequest inserted = super.insert(SupportServiceType.ANNUAL_FEES_PAY, entity);

        if (entity.getPostRequestReasons() != null && entity.getPostRequestReasons().getId() != null) {
            Long id = entity.getApplicationInfo().getId();
            ApplicationInfo applicationInfo = applicationInfoService.findById(id);
            applicationInstallmentService.postponedInstallment(applicationInfo, entity.getPostRequestReasons());
        }

        return inserted;
    }


    @Override
    @Transactional
    public void paymentCallBackHandler(Long id, ApplicationNumberGenerationDto applicationNumberGenerationDto) {
        AnnualFeesRequest annualFeesRequest = getFeeRequestById(id);
        applicationInstallmentService.annualFeesPaymentCallBackHandler(annualFeesRequest, applicationNumberGenerationDto);
        supportServiceActivityLogHelper.addActivityLogForSupportService(id, RequestActivityLogEnum.ANNUAL_FEES_PAY, SupportServiceType.ANNUAL_FEES_PAY);
        super.paymentCallBackHandler(id, applicationNumberGenerationDto);
    }

    @Override
    public SupportServiceRequestStatusEnum getPaymentRequestStatus() {
        return SupportServiceRequestStatusEnum.COMPLETED;
    }



    private AnnualFeesRequest getFeeRequestById(Long feeId) {
        AnnualFeesRequest annualFeesRequest = annualFeesRequestRepository.findById(feeId).orElseThrow(() -> new BusinessException(Constants.ErrorKeys.VALIDATION_INSTALLMENT_NOT_FOUND));
        return annualFeesRequest;
    }

}
