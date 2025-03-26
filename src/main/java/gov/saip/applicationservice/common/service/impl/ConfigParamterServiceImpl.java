package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.common.clients.rest.feigns.CustomerConfigParameterClient;
    import gov.saip.applicationservice.common.service.ConfigParameterService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ConfigParamterServiceImpl implements ConfigParameterService {

   private final CustomerConfigParameterClient customerConfigParameterClient;
    @Override
    public String getByKey(String key) {
        return customerConfigParameterClient.getConfig(key).getPayload().getValue();
    }

    @Override
    public Long getLongByKey(String key) {
        return Double.valueOf(getByKey(key)).longValue();
    }
}
