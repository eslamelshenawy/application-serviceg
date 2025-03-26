create table application.revoke_licence_request (
  id int8 NOT NULL PRIMARY KEY,
  licence_request_id int8,
  notes TEXT,
  applicant_type varchar(255),
  canceled_contract_document_id int8,
  updated_contract_document_id int8,
  agency_request_number varchar(255),
  CONSTRAINT updated_contract_document_id_documents_fk FOREIGN KEY (updated_contract_document_id) REFERENCES application.documents(id),
  CONSTRAINT canceled_contract_document_id_documents_fk FOREIGN KEY (canceled_contract_document_id) REFERENCES application.documents(id),
  CONSTRAINT licence_request_id_fk FOREIGN KEY (licence_request_id) REFERENCES application.licence_request(id)
);

