package gov.saip.applicationservice.common.enums.support_services_enums;

import gov.saip.applicationservice.common.enums.RequestTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TrademarkAppealRequestType {

    CHANGE_TM_IMAGE_SERVICE("تغيير صوره", "Change Image", RequestTypeEnum.EDIT_TRADEMARK_IMAGE, false),
    ACCEPTANCE_WITH_CONDITION("قبول بشرط", "Acceptance On Condition", RequestTypeEnum.TRADEMARK, true),
    SUBSTANTIVE_EXAMINATION_REJECTION("رفض تسجيل", "Refuse Registration", RequestTypeEnum.TRADEMARK, true);

    private String nameAr;
    private String nameEn;
    private RequestTypeEnum requestType;
    private boolean isApplicationRegistration;

}
