package gov.saip.applicationservice.common.enums.agency;

import gov.saip.applicationservice.common.enums.ApplicationStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum TrademarkAgencyType {
    CHANGE_OWNERSHIP(List.of(ApplicationStatusEnum.ACCEPTANCE, ApplicationStatusEnum.THE_TRADEMARK_IS_REGISTERED)), SUPPORT_SERVICES(null), TRADEMARK_REGISTRATION(null), INVESTIGATION(null);
    private List<ApplicationStatusEnum> status;
}
