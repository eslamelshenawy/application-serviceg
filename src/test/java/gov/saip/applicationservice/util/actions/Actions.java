package gov.saip.applicationservice.util.actions;

import org.springframework.test.web.servlet.MockMvc;

public interface Actions {
    MockMvc getMockMvc();

    String serialize(Object o);
}
