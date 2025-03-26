ALTER TABLE application.trademark_details ADD column if not exists examiner_grant_condition VARCHAR(255) DEFAULT NULL;

ALTER TABLE application.substantive_examination_reports ADD column if not exists examiner_report VARCHAR(255) DEFAULT NULL;

ALTER TABLE application.substantive_examination_reports ADD column if not exists task_id VARCHAR(255) DEFAULT NULL;