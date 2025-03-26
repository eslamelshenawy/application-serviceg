package gov.saip.applicationservice.common.dto.trademark;

import lombok.Data;

import java.util.List;
@Data
public class RequestTrademarkDetailsDto {
    private List<String> trademarkOwnersCustomerCode;
    private List<String> trademarkNumbers;
    private String filterTrademarkNumber;

}