package gov.saip.applicationservice.base.model;

import gov.saip.applicationservice.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditAwareImpl implements AuditorAware<String> {

    private final Util util;

    @Autowired
    public AuditAwareImpl(Util util) {
        this.util = util;
    }

    @Override
    public Optional<String> getCurrentAuditor() {
        Object object = util.getFromBasicUserinfo("userName");
        return object == null ? Optional.empty() : Optional.of(object.toString());
    }

}