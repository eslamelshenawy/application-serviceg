ALTER TABLE application.applications_info ADD COLUMN IF NOT EXISTS parent_elements_count BIGINT;
ALTER TABLE application.applications_info ADD COLUMN IF NOT EXISTS children_elements_count BIGINT;
ALTER TABLE application.applications_info ADD COLUMN IF NOT EXISTS application_relevent_type_count BIGINT;