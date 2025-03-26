ALTER TABLE application.support_services_type_decisions ALTER COLUMN decision TYPE text USING decision::text;
ALTER TABLE application.support_services_type_decisions add column if not exists  to_customers varchar(250) NULL;
ALTER TABLE application.support_services_type_decisions add column if not exists  to_role varchar(250) NULL;