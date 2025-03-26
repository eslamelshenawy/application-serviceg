INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code)
VALUES((SELECT MAX(id) + 1 FROM application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'مشطوب بامر محكمة', 'Revoked By Court Order', 'REVOKED_BY_COURT_ORDER');


INSERT INTO application.lk_document_type (id, name, code, name_ar, description, category, doc_order, is_deleted)
VALUES ((select (max(id) + 1) from application.lk_document_type), 'Revoke By Court Order', 'REVOKE_BY_COURT_ORDER', 'وثيقة الشطب بامر محكمة', 'وثيقة الشطب بامر محكمة', 'REVOKE_BY_COURT_ORDER', NULL,0);


create table application.revoke_by_court_order (
  id int8 NOT NULL PRIMARY KEY,
  notes TEXT,
  court_number varchar(255),
  court_name varchar(255),
  ruling_date timestamp,
  suspension_duration varchar(255),
  duration_years int8,
  duration_months int8,
  duration_days int8
);


create table application.revoke_by_court_order_documents (
revoke_by_court_order_id int8 not null,
document_id int8  not null,
FOREIGN KEY (revoke_by_court_order_id) REFERENCES application.revoke_by_court_order(id),
FOREIGN KEY (document_id)  REFERENCES application.documents(id)
);