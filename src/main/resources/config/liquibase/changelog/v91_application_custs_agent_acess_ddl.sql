ALTER TABLE application.application_customers ADD customer_access_level varchar(255) NULL;
ALTER TABLE application.application_customers ADD agency_request_id int8 NULL;
ALTER TABLE application.application_customers ADD CONSTRAINT fkbo6ad04ihvm0uo50xririjm5w FOREIGN KEY (agency_request_id) REFERENCES application.trademark_agency_requests(id);