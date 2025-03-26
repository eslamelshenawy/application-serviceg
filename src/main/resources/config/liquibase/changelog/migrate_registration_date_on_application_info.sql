create table if not exists application.migration_registeration_date (id   BIGINT  NOT NULL,  created_date   TIMESTAMP);
CREATE INDEX IF NOT EXISTS application_migration ON application.migration_registeration_date (id);

insert into application.migration_registeration_date  (id,created_date)
select 	distinct ai.id as id , piap.created_date as created_date
from
    application.publication_issue_application_publication piap
        join application.application_publication ap on
            piap.application_publication_id = ap.id
        join application.lk_publication_type pt on
            pt.id = ap.publication_type_id
        join application.applications_info ai on
            ap.application_info_id = ai.id
where
        ap.is_published = true
  and ap.support_service_id is null
  and pt.code in ('TRADEMARK_REGISTERATION', 'PATENT_REGISTRATION', 'INDUSTRIAL_DESIGN_REGISTRATION') and ai.is_deleted = 0 and ai.registration_date is null;


UPDATE application.applications_info ai
SET registration_date = mig.created_date
    FROM application.migration_registeration_date mig
WHERE ai.id = mig.id;

DROP TABLE application.migration_registeration_date CASCADE;
