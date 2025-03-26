package gov.saip.applicationservice.common.dto;

import lombok.Data;

import java.util.List;

@Data
public class CompletePaymentDto {


    private long rowId;

    private String taskKey;
    private List<String> taskKeys;
    private String processRequestTypesCode;

}
