package gov.saip.applicationservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * each group of processes that are stored in same DB table have grouped into one classification
 */
@AllArgsConstructor
@Getter
public enum ProcessClassificationEnum {
    APPLICATION_PROCESS,
    SUPPORT_SERVICE_PROCESS,
    CUSTOMER_REGISTRATION_PROCESS,
    TRADEMARK_AGENCY,
    EXTERNAL_SERVICES_PROCESS
    ;
}
