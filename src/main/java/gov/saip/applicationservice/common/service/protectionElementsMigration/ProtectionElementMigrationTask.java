package gov.saip.applicationservice.common.service.protectionElementsMigration;


import java.util.List;

import com.savoirtech.logging.slf4j.json.LoggerFactory;
import com.savoirtech.logging.slf4j.json.logger.Logger;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProtectionElementMigrationTask {

    private List<Integer> applicationIdsList;
    private static final Logger logger = LoggerFactory.getLogger(ProtectionElementMigrationTask.class);

    private  final JdbcTemplate jdbcTemplate;
   private final   MigrationTask task;


    public void start() {
        try {
            populateQueue();
            for (int i : applicationIdsList) {
                task.run(i);
            }
            MigrationTask.printReport();
        } catch (Exception e) {
            logger.error().message("Failed to migrate protection elements due to the following error: "+ e.getMessage());
        }
    }

    private void populateQueue() throws Exception {
        System.out.println(" Start populateQueue func ");
        try {
            String sql = "select distinct application_id from application.protection_elements where description is not null and parent_id is null";
            applicationIdsList = jdbcTemplate.query(sql, (rs, rowNum) -> Integer.valueOf(rs.getInt("application_id")));
            System.out.println("Number of applications to be migrated: " + applicationIdsList.size());
        } catch (Exception e) {
            throw new Exception("Error populate application Id's list", e);
        }
    }
}
