package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.base.dto.BaseDto;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BillReminderNotificationDetailsDto extends BaseDto<Long> implements Serializable {
    // CUSTOMER DETAILS TO SEND THE NOTIFICATION
    private String mail;
    private String mobile;
    private String customerCode;

    private String requestNumber; // request number that will appear in the notification [based on business] may be [application or support services or certificate numbers]
    private String requestId; // efiling request id
    private String requestType; // efiling request type to make FE able to redirect when click on the notification

    // CATEGORY OF THE APPLICATION
    private String category;
    private String categoryNameAr;
    private String categoryNameEn;
    private String applicationTitleAr;
    private String applicationTitleEn;

    public BillReminderNotificationDetailsDto(Long id, String mail, String mobile, String customerCode,
                                              String requestNumber, String requestId, String requestType,
                                              String category, String categoryNameEn, String categoryNameAr,
                                              String applicationTitleAr, String applicationTitleEn) {
        this.setId(id);
        this.mail = mail;
        this.mobile = mobile;
        this.customerCode = customerCode;
        this.requestNumber = requestNumber;
        this.requestId = requestId;
        this.requestType = requestType;
        this.category = category;
        this.categoryNameEn = categoryNameEn;
        this.categoryNameAr = categoryNameAr;
        this.applicationTitleAr = applicationTitleAr;
        this.applicationTitleEn = applicationTitleEn;
    }
}
