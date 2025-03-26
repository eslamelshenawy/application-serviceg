package gov.saip.applicationservice.common.service.annualfees.impl;

import gov.saip.applicationservice.base.service.BaseLkServiceImpl;
import gov.saip.applicationservice.common.dto.annualfees.LkAnnualRequestYearsDto;
import gov.saip.applicationservice.common.enums.installment.AnnualInstallmentCostEnum;
import gov.saip.applicationservice.common.enums.installment.InstallmentStatus;
import gov.saip.applicationservice.common.mapper.annualfees.LkAnnualRequestYearsMapper;
import gov.saip.applicationservice.common.model.annual_fees.LkAnnualRequestYears;
import gov.saip.applicationservice.common.model.installment.ApplicationInstallment;
import gov.saip.applicationservice.common.repository.annualfees.LkAnnualRequestYearsRepository;
import gov.saip.applicationservice.common.service.annualfees.LkAnnualRequestYearsService;
import gov.saip.applicationservice.common.service.installment.ApplicationInstallmentService;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class LkAnnualRequestYearsServiceImpl extends BaseLkServiceImpl<LkAnnualRequestYears, Long> implements LkAnnualRequestYearsService {


    private final ApplicationInstallmentService installmentService;
    private final LkAnnualRequestYearsRepository lkAnnualRequestYearsRepository;
    private final LkAnnualRequestYearsMapper lkAnnualRequestYearsMapper;

    @Override
    public List<LkAnnualRequestYearsDto> getAnnualYearsByAppId(Long appId) {
//        ApplicationInstallment installment = installmentService.getUnpaidInstallmentForGivenApplicationId(appId);
//        if (installment == null) {
//            throw new BusinessException("VALIDATION.INSTALLMENT.NOT_FOUND");
//        }
        Integer installmentIndex = installmentService.getLastIndexByAppAndStatus(appId, InstallmentStatus.PAID);
        if (installmentIndex == null) {
            installmentIndex = 0;
        }
        List<LkAnnualRequestYears> remainingYears = lkAnnualRequestYearsRepository.getRemainingYears();

        return mapYearsAndAddCost(remainingYears, installmentIndex);
    }

    private List<LkAnnualRequestYearsDto> mapYearsAndAddCost(List<LkAnnualRequestYears> requestYears, int installmentIndex) {
        AnnualInstallmentCostEnum currentYear = AnnualInstallmentCostEnum.getYearByIndex(installmentIndex + 1);
        List<LkAnnualRequestYearsDto> dtoList = new ArrayList<>();
        List<String> codes = new ArrayList<>();
        for(LkAnnualRequestYears year : requestYears) {
            List<String> yearCosts = getYearCosts(currentYear, year);
            if (yearCosts.size() == codes.size()) {
                break; // duplication started so break; this occurred when remaining years less than configured years in DB
            }
            codes = yearCosts;
            LkAnnualRequestYearsDto requestYearsDto = lkAnnualRequestYearsMapper.map(year);
            requestYearsDto.setCostsCodes(codes);
            dtoList.add(requestYearsDto);
        }

        return dtoList;
    }

    private static List<String> getYearCosts(AnnualInstallmentCostEnum currentYear, LkAnnualRequestYears year) {
        List<String> codes = new ArrayList<>();
        AnnualInstallmentCostEnum tmpYear = currentYear;
        int count = year.getYearsCount();
        for (int i = 0; i < count && tmpYear != null; i++) {
            codes.add(tmpYear.name());
            tmpYear = tmpYear.getNextYear();
        }
        return codes;
    }
}
