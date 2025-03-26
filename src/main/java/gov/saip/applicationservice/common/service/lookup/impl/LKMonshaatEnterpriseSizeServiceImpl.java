package gov.saip.applicationservice.common.service.lookup.impl;

import gov.saip.applicationservice.base.service.BaseLkServiceImpl;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.clients.rest.feigns.CustomerServiceFeignClient;
import gov.saip.applicationservice.common.clients.rest.feigns.SoapTranslationFeignClient;
import gov.saip.applicationservice.common.dto.soap.RequestEnterpriseSize;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.LKMonshaatEnterpriseSize;
import gov.saip.applicationservice.common.repository.lookup.LKMonshaatEnterpriseSizeRepository;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.lookup.LKMonshaatEnterpriseSizeService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Slf4j
@AllArgsConstructor
public class LKMonshaatEnterpriseSizeServiceImpl extends BaseLkServiceImpl<LKMonshaatEnterpriseSize, Long>
        implements LKMonshaatEnterpriseSizeService {
    private final ApplicationInfoService applicationInfoService;
    private final LKMonshaatEnterpriseSizeRepository lkMonshaatEnterpriseSizeRepository;
    private final CustomerServiceFeignClient customerServiceFeignClient;
    private final SoapTranslationFeignClient  soapTranslationFeignClient;

    @Transactional
    @Override
    public String saveApplicationForOrganizationSize(Long appId, String customerCode) {

        String orgDns= customerServiceFeignClient.getOrganizationDnsByCustomerCode(customerCode).getPayload();
        RequestEnterpriseSize size= new RequestEnterpriseSize();
        size.setCrNumber(orgDns);

        long startTime = System.currentTimeMillis();
        String orgSize = soapTranslationFeignClient.getEnterpriseSize(size).getPayload();
        long endTime = System.currentTimeMillis();
        log.info("Call to getEnterpriseSize took {} ms", (endTime - startTime));

        LKMonshaatEnterpriseSize lkMonshaatEnterpriseSize = lkMonshaatEnterpriseSizeRepository.findByCode(orgSize).get();
        if (lkMonshaatEnterpriseSize == null) return null;

        applicationInfoService.updateApplicationInfoEnterpriseSize(lkMonshaatEnterpriseSize.getId(), appId);

        return orgDns;
    }
}
