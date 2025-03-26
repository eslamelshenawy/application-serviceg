package gov.saip.applicationservice.common.service.pdf;

import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;
import com.documents4j.job.RemoteConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Slf4j
public class FileConverterFactory {
    @Autowired
    private Environment environment;

    @Value("${file.converter}")
    private String fileRemoteConverterUri;

    private final String LOCAL_ENV_PROFILE = "local";

    public IConverter getConverter() {
        log.info("converter url  is => {}",fileRemoteConverterUri);
        IConverter converter;
        if (isLocalProfileActive())
            converter = getLocalConverter();
        else
            converter = getRemoteConverter();

        return converter;
    }

    private boolean isLocalProfileActive() {
        String[] activeProfiles = environment.getActiveProfiles();
        return Arrays.stream(activeProfiles).anyMatch(activeProfile -> {
            return activeProfile.equals(LOCAL_ENV_PROFILE);
        });
    }

    private static IConverter getLocalConverter() {
        return LocalConverter.builder().build();
    }

    private IConverter getRemoteConverter() {
        return RemoteConverter.builder()
                .baseUri(fileRemoteConverterUri)
                .build();
    }
}