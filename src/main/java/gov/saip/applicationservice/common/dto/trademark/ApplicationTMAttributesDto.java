package gov.saip.applicationservice.common.dto.trademark;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApplicationTMAttributesDto  {

    private Long id;
    private String statusCode;

    public ApplicationTMAttributesDto(Long id, String statusCode, Long createdAppCustomerId,String mainApplicantCustomerCode,String applicationNumber,String categoryCode) {
        this.id = id;
        this.statusCode = statusCode;
        this.createdAppCustomerId = createdAppCustomerId;
        this.mainApplicantCustomerCode=mainApplicantCustomerCode;
        this.categoryCode=categoryCode;
        this.applicationNumber=applicationNumber;
    }

    private Long createdAppCustomerId;
    private String mainApplicantCustomerCode;
    private String applicationNumber;
    private String categoryCode;


}
