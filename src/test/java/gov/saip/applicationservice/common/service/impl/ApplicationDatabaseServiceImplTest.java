package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.common.model.ApplicationDatabase;
import gov.saip.applicationservice.common.repository.ApplicationDatabaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ApplicationDatabaseServiceImplTest {

    @Mock
    private ApplicationDatabaseRepository applicationDatabaseRepository;

    private ApplicationDatabaseServiceImpl databaseService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        databaseService = new ApplicationDatabaseServiceImpl(applicationDatabaseRepository);
    }

    @Test
    public void testGetAllByApplicationId() {
        Long appId = 123L;
        List<ApplicationDatabase> expectedDatabases = new ArrayList<>();
        ApplicationDatabase db1 = new ApplicationDatabase();
        ApplicationDatabase db2 = new ApplicationDatabase();
        expectedDatabases.add(db1);
        expectedDatabases.add(db2);

        when(applicationDatabaseRepository.findByApplicationInfoId(appId)).thenReturn(expectedDatabases);

        List<ApplicationDatabase> actualDatabases = databaseService.getAllByApplicationId(appId);

        assertEquals(expectedDatabases, actualDatabases);
    }
}
