package gov.saip.applicationservice.common.service;

import java.util.List;

public interface ConfigParameterService {
    String getByKey(String key);

    Long getLongByKey(String key);
}
