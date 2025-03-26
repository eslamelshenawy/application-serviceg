-- Add the new column main_licence_request_id
ALTER TABLE application.licence_request ADD COLUMN if not exists main_licence_request_id BIGINT;
-- Assuming the table already exists and you need to add the foreign key constraint
ALTER TABLE application.licence_request
    ADD CONSTRAINT fk_main_licence_request FOREIGN KEY (main_licence_request_id) REFERENCES application.licence_request (id);