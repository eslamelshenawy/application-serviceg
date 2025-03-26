create table application.opposition_revoke_licence_request
(
    id                        int8 NOT NULL PRIMARY KEY,
    revoke_licence_request_id int8,
    objection_reason          TEXT,
    applicant_type            varchar(255),
    opposition_type            varchar(255),
    CONSTRAINT revoke_licence_request_id_fk FOREIGN KEY (revoke_licence_request_id) REFERENCES application.revoke_licence_request (id)
);

create table application.opposition_revoke_licence_request_documents (
opposition_revoke_licence_request_id int8 not null,
document_id int8  not null,
FOREIGN KEY (opposition_revoke_licence_request_id) REFERENCES application.opposition_revoke_licence_request(id),
FOREIGN KEY (document_id)  REFERENCES application.documents(id)
);

