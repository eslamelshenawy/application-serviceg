ALTER TABLE application.revoke_licence_request
DROP COLUMN canceled_contract_document_id,
DROP COLUMN updated_contract_document_id;

create table application.revoke_licence_request_documents (
revoke_licence_request_id int8 not null,
document_id int8  not null,
FOREIGN KEY (revoke_licence_request_id) REFERENCES application.revoke_licence_request(id),
FOREIGN KEY (document_id)  REFERENCES application.documents(id)
);


create table application.licence_request_documents (
licence_request_id int8 not null,
document_id int8  not null,
FOREIGN KEY (licence_request_id) REFERENCES application.licence_request(id),
FOREIGN KEY (document_id)  REFERENCES application.documents(id)
);
