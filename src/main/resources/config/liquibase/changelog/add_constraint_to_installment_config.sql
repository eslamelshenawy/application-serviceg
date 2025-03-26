ALTER TABLE application.application_installments_config
    ADD CONSTRAINT FK_APPLICATION_PUBLICATION_ON_LK_APPLICATION_STATUS
        FOREIGN KEY (application_desertion_status_id)
            REFERENCES application.lk_application_status (id);

UPDATE application.application_installments_config
SET desertion_duration = (
    SELECT las.id
    FROM application.lk_application_status las
    WHERE las.code = 'WAIVED'
      AND las.application_category_id = 5
)
WHERE application_category = 'TRADEMARK';


UPDATE application.application_installments_config
SET desertion_duration= (select las.id from application.lk_application_status las where las.code='WAIVED' and las.application_category_id=1)
WHERE application_category='PATENT';

UPDATE application.application_installments_config
SET desertion_duration= (select las.id from application.lk_application_status las where las.code='WAIVED' and las.application_category_id=2)
WHERE application_category='INDUSTRIAL_DESIGN';
