

ALTER TABLE application.certificates_request
    ADD COLUMN IF NOT EXISTS request_number VARCHAR(255);