package gov.saip.applicationservice.util;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import lombok.Getter;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static org.junit.jupiter.api.extension.ExtensionContext.Namespace.GLOBAL;

public class GlobalWireMockExtension implements BeforeAllCallback, BeforeEachCallback, ExtensionContext.Store.CloseableResource {

    private static boolean started = false;

    @Getter
    private static final WireMockServer wireMock = new WireMockServer(0);

    @Override
    public void beforeAll(ExtensionContext context) {
        if (!started) {
            started = true;
            wireMock.start();
            // The following line registers a callback hook when the root test context is shut down
            context.getRoot().getStore(GLOBAL).put(GlobalWireMockExtension.class.getSimpleName(), this);
        }
    }

    @Override
    public void close() {
        wireMock.shutdownServer();
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        wireMock.resetToDefaultMappings();
    }
}
