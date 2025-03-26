
ALTER TABLE application.applications_info ADD
    COLUMN IF NOT EXISTS registration_date timestamp NULL;

ALTER TABLE application.applications_info ADD
    COLUMN IF NOT EXISTS registration_date_hijri varchar(255) NULL;