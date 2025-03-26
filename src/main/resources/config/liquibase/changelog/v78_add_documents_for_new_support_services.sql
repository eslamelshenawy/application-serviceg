INSERT INTO application.lk_document_type (id, name, code, name_ar, description, category, doc_order, is_deleted)
VALUES ((select (max(id) + 1) from application.lk_document_type), 'Revoke Voluntary', 'REVOKE_VOLUNTARY', 'وثيقة التنازل عن الترخيص', 'وثيقة التنازل عن الترخيص ', 'REVOKE_VOLUNTARY', NULL,0);


create table application.revoke_voluntary (
  id int8 NOT NULL PRIMARY KEY,
  notes TEXT
);

create table application.revoke_voluntary_documents (
revoke_voluntary_id int8 not null,
document_id int8  not null,
FOREIGN KEY (revoke_voluntary_id) REFERENCES application.revoke_voluntary(id),
FOREIGN KEY (document_id)  REFERENCES application.documents(id)
);

create table application.support_services_type_decisions (
id int8 NOT NULL PRIMARY KEY ,
created_by_user varchar(255) NULL,
created_date timestamp NULL,
modified_by_user varchar(255) NULL,
modified_date timestamp NULL,
is_deleted int4 NOT NULL,
decision VARCHAR(250),
return_reason TEXT,
rejection_reason TEXT,
support_services_type_id int8 Not NULL ,
FOREIGN KEY (support_services_type_id) REFERENCES application.application_support_services_type(id)
);
