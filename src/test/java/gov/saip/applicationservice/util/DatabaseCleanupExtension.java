package gov.saip.applicationservice.util;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestExecutionListener;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;

// Clean up database after each test execution
public class DatabaseCleanupExtension implements AfterEachCallback {
    private static boolean storedProcedureCreated = false;

    @Override
    public void afterEach(ExtensionContext extensionContext) throws Exception {
        ApplicationContext applicationContext = SpringExtension.getApplicationContext(extensionContext);
        DataSource dataSource = applicationContext.getBean(DataSource.class);
        String dbSchema = applicationContext.getEnvironment().getProperty("spring.jpa.properties.hibernate.default_schema");
        String dbUser = applicationContext.getEnvironment().getProperty("spring.datasource.username");
        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()) {
            if (!storedProcedureCreated) {
                statement.execute("""
                        CREATE OR REPLACE FUNCTION truncate_tables(username IN VARCHAR, schema IN VARCHAR) RETURNS void AS $$
                        DECLARE
                            statements CURSOR FOR
                                SELECT tablename
                                FROM pg_tables
                                WHERE tableowner = username
                                  AND schemaname = schema
                                  -- don't truncate lookup tables
                                  AND "left"(tablename, 2) != 'lk';
                        BEGIN
                            FOR stmt IN statements
                                LOOP
                                    EXECUTE 'TRUNCATE TABLE ' || schema || '.' || stmt.tablename || ' CASCADE;';
                                END LOOP;
                        END ;
                        $$ LANGUAGE plpgsql;
                        """);
                storedProcedureCreated = true;
            }
            statement.execute("""
                    SELECT truncate_tables('%s', '%s');
                    """.formatted(dbUser, dbSchema));
        }
    }
}
