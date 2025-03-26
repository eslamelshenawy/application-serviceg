ALTER TABLE application.application_certificate_documents
ADD COLUMN failure_reason text,
ADD COLUMN generation_status varchar(100);