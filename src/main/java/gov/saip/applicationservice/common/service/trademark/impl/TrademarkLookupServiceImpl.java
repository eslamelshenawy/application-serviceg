package gov.saip.applicationservice.common.service.trademark.impl;


import gov.saip.applicationservice.common.dto.trademark.TrademarkLookupResDto;
import gov.saip.applicationservice.common.enums.ApplicationCustomerType;
import gov.saip.applicationservice.common.enums.customers.UserGroup;
import gov.saip.applicationservice.common.mapper.trademark.LkMarkTypeMapper;
import gov.saip.applicationservice.common.mapper.trademark.LkTagLanguageMapper;
import gov.saip.applicationservice.common.mapper.trademark.LkTagTypeDescMapper;
import gov.saip.applicationservice.common.model.trademark.LkMarkType;
import gov.saip.applicationservice.common.service.ApplicationCustomerService;
import gov.saip.applicationservice.common.service.CustomerServiceCaller;
import gov.saip.applicationservice.common.service.trademark.LkMarkTypeService;
import gov.saip.applicationservice.common.service.trademark.LkTagLanguageService;
import gov.saip.applicationservice.common.service.trademark.LkTagTypeDescService;
import gov.saip.applicationservice.common.service.trademark.TrademarkLookupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link TrademarkLookupService} interface.
 *
 * This class provides methods for retrieving trademark lookup data, including mark types, tag type descriptions,
 * and tag languages. The data is returned as a {@link TrademarkLookupResDto} object.
 *
 * <p>The implementation uses several mapper and service classes to retrieve and map the data.
 * These classes are injected into the implementation using the {@link RequiredArgsConstructor} annotation.
 *
 * <p>Example usage:
 *
 * <pre>{@code
 * TrademarkLookupService trademarkLookupService = new TrademarkLookupServiceImpl();
 * TrademarkLookupResDto dto = trademarkLookupService.getTrademarkLookup();
 * }</pre>
 *
 * @see TrademarkLookupService
 * @see TrademarkLookupResDto
 */
@Service
@RequiredArgsConstructor
public class TrademarkLookupServiceImpl implements TrademarkLookupService {

    public static final String COLLECTIVE_MARK = "COLLECTIVE_MARK";
    private final LkMarkTypeService markTypeService;
    private final LkMarkTypeMapper markTypeMapper;
    private final LkTagTypeDescService tagTypeDescService;
    private final LkTagTypeDescMapper tagTypeDescMapper;
    private final LkTagLanguageService tagLanguageService;
    private final LkTagLanguageMapper tagLanguageMapper;
    private final CustomerServiceCaller customerServiceFeignClient;
    private final ApplicationCustomerService applicationCustomerService;

    /**
     * Retrieves trademark lookup data and returns it as a {@link TrademarkLookupResDto} object.
     * @return a {@link TrademarkLookupResDto} object containing the trademark lookup data
     */
    @Override
    public TrademarkLookupResDto getTrademarkLookup(Long applicationId) {
        List<LkMarkType> allTypes = null;
        if (applicationId != null){
            allTypes = getTrademarkTypesBasedOnLoggedInCustomerType(applicationId);
        }
        else {
            allTypes=markTypeService.findAll();
        }

        return TrademarkLookupResDto
                .builder()
                .markTypes(markTypeMapper.map(allTypes))
                .tagTypeDesc(tagTypeDescMapper.map(tagTypeDescService.findAll()))
                .tagLanguages(tagLanguageMapper.map(tagLanguageService.findAll()))
                .build();
    }

    private List<LkMarkType> getTrademarkTypesBasedOnLoggedInCustomerType(Long applicationId) {
        String customerCode = applicationCustomerService.getAppCustomerByAppIdAndType(applicationId, ApplicationCustomerType.MAIN_OWNER).getCustomerCode();
        List<LkMarkType> allTypes = markTypeService.findAll();
        if (customerCode == null) {
            return allTypes;
        }
        String userGroupCodeByCustomerCode = customerServiceFeignClient.getUserGroupCodeByCustomerCode(customerCode);

        return filterTrademarkTypeByCustomerType(allTypes, userGroupCodeByCustomerCode);
    }

    private static List<LkMarkType> filterTrademarkTypeByCustomerType(List<LkMarkType> allTypes, String userGroupCodeByCustomerCode) {
        if (UserGroup.NATURAL_PERSON_WITH_NATIONALITY.name().equals(userGroupCodeByCustomerCode)) {
            allTypes = allTypes.stream().filter(type -> type.getCode().equals("SERVICE_MARK") || type.getCode().equals("TRADEMARK")).toList();
        }
        else if(!UserGroup.LEGAL_ENTITY.name().equals(userGroupCodeByCustomerCode) && !UserGroup.FOREIGN_CORPORATION.name().equals(userGroupCodeByCustomerCode)){
            allTypes = allTypes.stream().filter(type -> !type.getCode().equals(COLLECTIVE_MARK)).toList();
        }
        return allTypes;
    }

}
