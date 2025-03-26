ALTER TABLE application.documents ADD COLUMN document_pages int4 NULL;

ALTER TABLE application.applications_info ADD COLUMN pages_number int4 NULL;
ALTER TABLE application.applications_info ADD COLUMN claim_pages_number int4 NULL;
ALTER TABLE application.applications_info ADD COLUMN shapes_number int4 NULL;

ALTER TABLE application.applications_info ADD COLUMN total_checking_fee int8 NULL;