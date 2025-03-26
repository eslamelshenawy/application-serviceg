package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.common.enums.ApplicationCustomerType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ApplicationNotificationData {

        private String email;

        private String mobileCode;

        private String mobileNumber;

        private Long applicationId;

        private Long customerId;
    private ApplicationCustomerType customerType;
    private String customerCode;


    public ApplicationNotificationData(String email, String mobileCode, String mobileNumber, Long applicationId, Long customerId,ApplicationCustomerType customerType,String customerCode){
            this.email = email;
            this.mobileCode = mobileCode;
            this.mobileNumber= mobileNumber;
            this.applicationId = applicationId;
            this.customerId= customerId;
            this.customerType =customerType;
            this.customerCode =customerCode;
        }
    
}
