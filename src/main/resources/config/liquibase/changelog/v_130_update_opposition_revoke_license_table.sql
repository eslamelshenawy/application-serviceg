ALTER TABLE application.opposition_revoke_licence_request DROP COLUMN applicant_type;

create table application.opposition_revoke_licence_request_court_documents (
opposition_revoke_licence_request_id int8 not null,
document_id int8  not null,
FOREIGN KEY (opposition_revoke_licence_request_id) REFERENCES application.opposition_revoke_licence_request(id),
FOREIGN KEY (document_id)  REFERENCES application.documents(id)
);