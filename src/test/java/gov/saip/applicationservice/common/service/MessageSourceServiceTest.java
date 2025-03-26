package gov.saip.applicationservice.common.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class MessageSourceServiceTest {

    @Mock
    private MessageSource messageSource;

    private MessageSourceService messageSourceService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        messageSourceService = new MessageSourceService(messageSource);
    }

    @Test
    public void testGetMessageByKey() {
        String key = "test.key";
        String expectedMessage = "Test Message";
        when(messageSource.getMessage(key, new Object[]{}, LocaleContextHolder.getLocale()))
                .thenReturn(expectedMessage);

        String actualMessage = messageSourceService.getMessage(key);

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void testGetMessageByKeyWithParams() {
        String key = "test.key";
        String[] params = {"param1", "param2"};
        String expectedMessage = "Test Message with param1 and param2";
        when(messageSource.getMessage(key, params, LocaleContextHolder.getLocale()))
                .thenReturn(expectedMessage);

        String actualMessage = messageSourceService.getMessage(key, params);

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void testGetMessageWithMessageAndKey() {
        String message = "test.message";
        String key = "test.key";
        String expectedMessage = "Test Message with key";
        String[] params = {key};
        when(messageSource.getMessage(message, params, LocaleContextHolder.getLocale()))
                .thenReturn(expectedMessage);

        String actualMessage = messageSourceService.getMessage(message, key);

        assertEquals(expectedMessage, actualMessage);
    }
}

