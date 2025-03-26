package gov.saip.applicationservice.base.model;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {

			return Optional.of("UNKNOWN");
	}

}
