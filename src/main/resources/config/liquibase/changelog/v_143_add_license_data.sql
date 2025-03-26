ALTER TABLE application.licence_request ADD COLUMN canceled_contract_document_id int8;

ALTER TABLE application.licence_request ADD COLUMN licence_validity_number int4;

alter table application.licence_request add constraint FKhrmgpky3csryd6rl13at2t7ft foreign key (canceled_contract_document_id) references application.documents;
