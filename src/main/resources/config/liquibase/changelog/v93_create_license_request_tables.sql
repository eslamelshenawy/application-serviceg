
ALTER TABLE application.licence_request DROP COLUMN canceled_contract_document_id;

ALTER TABLE application.licence_request DROP COLUMN licence_validity_number;

ALTER TABLE application.licence_request
ADD COLUMN applicant_type varchar(255);

ALTER TABLE application.licence_request
ADD COLUMN from_date timestamp;

ALTER TABLE application.licence_request
ADD COLUMN to_date timestamp;

ALTER TABLE application.licence_request
ADD COLUMN notes TEXT;

ALTER TABLE application.licence_request
ADD COLUMN agency_request_number varchar(255);


