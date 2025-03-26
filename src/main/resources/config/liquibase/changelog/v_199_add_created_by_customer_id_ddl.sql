ALTER TABLE application.applications_info ADD if not exists created_by_customer_id int8 NULL;
ALTER TABLE application.applications_info ADD if not exists created_by_customer_type varchar(255) NULL;
