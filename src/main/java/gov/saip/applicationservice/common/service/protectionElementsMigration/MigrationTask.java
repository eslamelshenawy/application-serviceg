package gov.saip.applicationservice.common.service.protectionElementsMigration;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import com.savoirtech.logging.slf4j.json.LoggerFactory;
import com.savoirtech.logging.slf4j.json.logger.Logger;
import gov.saip.applicationservice.common.model.patent.PatentAttributeChangeLog;
import gov.saip.applicationservice.common.model.patent.PatentDetails;
import gov.saip.applicationservice.common.repository.patent.PatentAttributeChangeLogRepository;
import gov.saip.applicationservice.common.repository.patent.PatentDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class MigrationTask {
    private static int SUCCEDED = 0;
    private static int FAILED = 0;

    private int applicationId;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    private  final  PatentAttributeChangeLogRepository patentAttributeChangeLogRepository;

    private final PatentDetailsRepository patentDetailsRepository;

    private static final Logger logger = LoggerFactory.getLogger(MigrationTask.class);



    public static void printReport() {
        logger.info().message("Migrated applications: " + SUCCEDED);
        logger.info().message("Failed applications: " + FAILED);
    }

    public void run(Integer applicationId) {
        this.applicationId = applicationId;
        try {
            List<OldElement> oldElementsArabic = fetchOldElements(false);
            String oldElementArabicDescription = getOldElementString(oldElementsArabic, false);

            List<OldElement> oldElementsEnglish = fetchOldElements(true);
            String oldElementEnglishDescription = getOldElementString(oldElementsEnglish, true);

            NewElement newElement = getNewElement();
            newElement.setArabicValue(oldElementArabicDescription);
            newElement.setEnglishValue(oldElementEnglishDescription);

            addNewElements(newElement);
            logger.debug().message("Application " + applicationId + " has been migrated successfully");

            this.SUCCEDED += 1;
        } catch (Exception e) {
            e.printStackTrace();
            this.FAILED += 1;
            System.out.println("Failed to migrate protection elements for application " + applicationId + e.getMessage());
        }
    }

    private NewElement getNewElement() throws Exception {
        try {
            String sql = "select id, created_by_user, created_date from  application.patent_details where application_id = ?";
            List<NewElement> elements = jdbcTemplate.query(sql, (rs, rowNum) -> new NewElement(rs.getLong("id"), rs.getString("created_by_user"), rs.getDate("created_date")), applicationId);
            return elements.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error getting patent detail id for application " + applicationId, e);
        }
    }

    private void addNewElements(NewElement newElement) throws Exception {

        try {


            System.out.println(" PatentDetails id " +  newElement.getDetailId() );

            PatentDetails details =    patentDetailsRepository.findById(newElement.getDetailId()).get();


            StringBuilder sql = new StringBuilder();
            sql.append("insert into application.patent_attribute_change_logs ");
            sql.append("(id, created_by_user, created_date, is_deleted, attribute_name, attribute_value, patent_details_id, migration_stage) ");
            sql.append("values (?, ?, ?, ?, ?, ?, ?, ?)");

           // int maxId = getMaxId();

            if (newElement.getArabicValue() != null && !newElement.getArabicValue().isBlank()) {
              //  maxId += 1;
              //  jdbcTemplate.update(sql.toString(), maxId, newElement.getCreatedByUser(), newElement.getCreatedDate(), 0, "arProtection", newElement.getArabicValue(), newElement.getDetailId(), 0);
                PatentAttributeChangeLog optDBAttribute = new PatentAttributeChangeLog();
                optDBAttribute.setPatentDetails(details);
                optDBAttribute.setIsDeleted(0);
                optDBAttribute.setCreatedByUser(newElement.getCreatedByUser());
                optDBAttribute.setCreatedDate(  newElement.getCreatedDate()   );

                optDBAttribute.setAttributeValue(newElement.getArabicValue());
                optDBAttribute.setAttributeName("arProtection");
                patentAttributeChangeLogRepository.save(optDBAttribute);
            }

            if (newElement.getEnglishValue() != null && !newElement.getEnglishValue().isBlank()) {
                PatentAttributeChangeLog optDBAttribute = new PatentAttributeChangeLog();
                optDBAttribute.setPatentDetails(details);
                optDBAttribute.setIsDeleted(0);
                optDBAttribute.setCreatedByUser(newElement.getCreatedByUser());
                optDBAttribute.setCreatedDate(  newElement.getCreatedDate()   );
                optDBAttribute.setAttributeValue(newElement.getEnglishValue());
                optDBAttribute.setAttributeName("enProtection");
                patentAttributeChangeLogRepository.save(optDBAttribute);
              //  maxId += 1;
              //  jdbcTemplate.update(sql.toString(), maxId, newElement.getCreatedByUser(), newElement.getCreatedDate(), 0, "enProtection", newElement.getEnglishValue(), newElement.getDetailId(), 0);
            }


        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error adding new protection elements for application " + applicationId, e);
        }
    }

    private int getMaxId() throws Exception {
        try {
            String sql = "select max(id) maxId from application.patent_attribute_change_logs";
            List<Integer> maxList = jdbcTemplate.query(sql, (rs, rowNum) -> Integer.valueOf(rs.getInt("maxId")));

            if (maxList == null || maxList.size() ==0) {
                return 0;
            }

            return maxList.get(0);
        } catch (Exception e) {
            logger.error().exception("exception", e).message(e.getMessage()).log();
            return 0;
//			throw new Exception("Error getting max id value", e);
        }
    }

    private String getOldElementString(List<OldElement> oldElements, boolean isEnglish) throws Exception {
        try {
            oldElements = fetchOldElements(isEnglish);
            StringBuilder sb = new StringBuilder();
            sb.append("<p>");
            for (OldElement oe : oldElements) {
                sb.append(oe.getDescription() + "<br/>");
                if (!oe.getChildElements().isEmpty()) {
                    sb.append("<ul>");
                    for (String ce : oe.getChildElements()) {
                        sb.append("<li>" + ce + "</li>");
                    }
                    sb.append("</ul>");
                }
            }
            sb.append("</p>");
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error getting element as String", e);
        }
    }

    private List<OldElement> fetchOldElements(boolean isEnglish) throws Exception {
        try {
            String sql = "select id, description, created_by_user, created_date from application.protection_elements where application_id = ? and is_english = ? order by id";
            List<OldElement> oldElements = jdbcTemplate.query(sql, (rs, rowNum) -> new OldElement(rs.getInt("id"), rs.getString("description"), rs.getString("created_by_user"), rs.getDate("created_date")), applicationId, isEnglish);

            for (OldElement oe : oldElements) {
                fetchSubElements(oe);
            }

            return oldElements;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error fetching old elements", e);
        }
    }

    private void fetchSubElements(OldElement oldElement) {
        try {
            String sql = "select description from application.protection_elements where parent_id = ? order by id";
            List<String> childElements = jdbcTemplate.query(sql, (rs, rowNum) -> new String(rs.getString("description")), oldElement.getId());
            oldElement.setChildElements(childElements);
        } catch (Exception e) {
            logger.error().message("Error fetching child elements for " + oldElement.getId());
            e.printStackTrace();
        }
    }

}
