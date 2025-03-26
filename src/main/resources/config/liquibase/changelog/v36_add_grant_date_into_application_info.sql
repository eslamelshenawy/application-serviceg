
ALTER TABLE application.applications_info ADD
    COLUMN IF NOT EXISTS grant_date timestamp NULL;

ALTER TABLE application.applications_info ADD
    COLUMN IF NOT EXISTS grant_date_hijri varchar(255) NULL;