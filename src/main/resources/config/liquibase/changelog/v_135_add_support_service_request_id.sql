ALTER TABLE application.trademark_appeal_request DROP COLUMN process_request_id;
ALTER TABLE application.application_support_services_type ADD process_request_id int8 NULL;