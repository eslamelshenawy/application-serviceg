INSERT INTO application.lk_document_type (id, name, code, name_ar, description, category, doc_order, is_deleted)
VALUES ((select (max(id) + 1) from application.lk_document_type), 'Complainer Opposition Document', 'COMPLAINER_OPPOSITION_DOCUMENTS', 'وثائق مقدم طلب الاعتراض', 'وثائق مقدم طلب الاعتراض ','OPPOSITION_REQUEST', NULL,0);

INSERT INTO application.lk_document_type (id, name, code, name_ar, description, category, doc_order, is_deleted)
VALUES ((select (max(id) + 1) from application.lk_document_type), 'Application Owner Opposition Document', 'APPLICATION_OWNER_OPPOSITION_DOCUMENTS', 'وثائق المعترض عليه', 'وثائق المعترض عليه ','OPPOSITION_REQUEST', NULL,0);


create table application.opposition_request(
 id  int8 PRIMARY KEY NOT NULL,
 opposition_reason TEXT,
 application_owner_reply TEXT,
 complainer_session_date DATE,
 complainer_session_time TIME,
 complainer_session_is_paid BOOLEAN DEFAULT FALSE,
 complainer_session_result TEXT,
 complainer_session_slot_id int8,
 application_owner_session_date DATE,
 application_owner_session_time TIME,
 application_owner_is_session_paid BOOLEAN DEFAULT FALSE,
 application_owner_session_result TEXT,
 application_owner_slot_id int8
);


CREATE TABLE application.opposition_request_documents (
opposition_request_id int8 NOT NULL,
document_id int8 NOT NULL,
FOREIGN KEY (opposition_request_id) REFERENCES application.opposition_request(id),
FOREIGN KEY (document_id) REFERENCES application.documents(id)
);


CREATE TABLE application.opposition_application_similars (
opposition_request_id int8 NOT NULL,
application_info_id int8 NOT NULL,
FOREIGN KEY (opposition_request_id) REFERENCES application.opposition_request(id),
FOREIGN KEY (application_info_id) REFERENCES application.applications_info(id)
);