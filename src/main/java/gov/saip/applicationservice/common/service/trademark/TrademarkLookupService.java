package gov.saip.applicationservice.common.service.trademark;


import gov.saip.applicationservice.common.dto.trademark.TrademarkLookupResDto;

public interface TrademarkLookupService {

    TrademarkLookupResDto getTrademarkLookup (Long applicationId);
}
