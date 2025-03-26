package gov.saip.applicationservice.common.enums;

import java.util.List;

public enum ApplicationStatusPreconditions {

    AGENT_PRE_CONDITIONS(
            List.of(
            ApplicationStatusEnum.THE_APPLICATION_IS_AS_IF_IT_NEVER_EXISTED.name(),
            ApplicationStatusEnum.WAIVED.name(),
            ApplicationStatusEnum.ACCEPTANCE.name(),
            ApplicationStatusEnum.THE_TRADEMARK_IS_REGISTERED.name(),
            ApplicationStatusEnum.PUBLISHED_ELECTRONICALLY.name())
    );

    private final List<String> stringStatus;

    private ApplicationStatusPreconditions(List<String> stringStatus) {
        this.stringStatus = stringStatus;
    }

    public List<String> getStatusList() {
        return stringStatus;
    }


}