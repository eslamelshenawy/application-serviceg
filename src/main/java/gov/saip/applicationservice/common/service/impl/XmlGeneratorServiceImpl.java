package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import gov.saip.applicationservice.common.service.XmlGeneratorService;
import gov.saip.applicationservice.common.service.industrial.IndustrialDesignDetailService;
import gov.saip.applicationservice.common.service.patent.PatentDetailsService;
import gov.saip.applicationservice.common.service.trademark.TrademarkDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class XmlGeneratorServiceImpl implements XmlGeneratorService {

    private final TrademarkDetailService trademarkDetailService;
    private final PatentDetailsService patentDetailsService;
    private final IndustrialDesignDetailService industrialDesignDetailService;

    @Override
    public void generateUploadSaveXmlForApplication(Long applicationId, String saipCode, String documentType) {

            if (ApplicationCategoryEnum.PATENT.name().equals(saipCode)) {
                 patentDetailsService.generateUploadSaveXmlForApplication(applicationId, documentType);
            } else if (ApplicationCategoryEnum.TRADEMARK.name().equals(saipCode)) {
                 trademarkDetailService.generateUploadSaveXmlForApplication(applicationId, documentType);
            } else if (ApplicationCategoryEnum.INDUSTRIAL_DESIGN.name().equals(saipCode)) {
                 industrialDesignDetailService.generateUploadSaveXmlForApplication(applicationId, documentType);
            }

        }
}
