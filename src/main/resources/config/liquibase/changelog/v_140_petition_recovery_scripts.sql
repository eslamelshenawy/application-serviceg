alter table application.petition_recovery_request add column justification text;

CREATE TABLE application.petition_recovery_request_documents (
                                                                 petion_recovery_request_id int8 NOT NULL,
                                                                 document_id int8 NOT NULL,
                                                                 CONSTRAINT ukhnu6ad67cpwdd9b8m35vsul0e UNIQUE (petion_recovery_request_id, document_id)
);

ALTER TABLE application.petition_recovery_request_documents ADD CONSTRAINT fkch2hfmdj5pgt31ba7gve5imil FOREIGN KEY (petion_recovery_request_id) REFERENCES application.petition_recovery_request(id);
ALTER TABLE application.petition_recovery_request_documents ADD CONSTRAINT fkofo45e57cy4svwwqlh1pp14nn FOREIGN KEY (document_id) REFERENCES application.documents(id);



INSERT INTO application.lk_document_type (id, name, code, name_ar, description, category, doc_order, is_deleted)
VALUES ((select (max(id) + 1) from application.lk_document_type), 'Petition recovery', 'PETITION_RECOVERY', 'ملفات مبررات إعادة إجراءات سير طلب', 'ملفات مبررات إعادة إجراءات سير طلب ','PETITION_RECOVERY_REQUEST', NULL,0);



INSERT INTO application.lk_support_service_request_status
(id, code, name_ar, name_en, name_ar_external, name_en_external)
VALUES((select max(id + 1) from application.lk_support_service_request_status), 'REOPENED', 'معاد لإعادة النظر', 'reopened', 'معاد لإعادة النظر', 'reopened');


INSERT INTO application.lk_support_service_request_status
(id, code, name_ar, name_en, name_ar_external, name_en_external)
VALUES((select max(id + 1) from application.lk_support_service_request_status), 'PETITION_RECOVERY_APPROVAL', 'تم قبول طلب التماس إعادة اجراءات سير العمل', 'Petition recovery request is approved', 'تم قبول طلب التماس إعادة اجراءات سير العمل', 'Petition recovery request is approved');




INSERT INTO application.lk_support_service_request_status
(id, code, name_ar, name_en, name_ar_external, name_en_external)
VALUES((select max(id + 1) from application.lk_support_service_request_status), 'PETITION_RECOVERY_REJECTION', 'تم رفض طلب التماس إعادة اجراءات سير العمل', 'Petition recovery request is Rejected', 'تم رفض طلب التماس إعادة اجراءات سير العمل', 'Petition recovery request is Rejected');





INSERT INTO application.lk_support_service_request_status
(id, code, name_ar, name_en, name_ar_external, name_en_external)
VALUES((select max(id + 1) from application.lk_support_service_request_status), 'CONDITIONAL_REJECTION', 'تم الرفض المرتقب لطلب التماس إعادة اجراءات سير العمل', 'Petition recovery request is conditionally Rejected', 'تم رفض المرتقب لطلب التماس إعادة اجراءات سير العمل', 'Petition recovery request is conditionally Rejected');










INSERT INTO application.lk_task_eqm_types (id, code, name_ar, name_en)
VALUES ((SELECT max(id) + 1 FROM application.lk_task_eqm_types), 'PETITION_RECOVERY', 'طلب التماس اعادة اجراءات سير الطلب', 'petition recovery request');


INSERT INTO application.lk_task_eqm_items (id, code, name_ar, name_en, rating_value_type)
VALUES ((SELECT max(id) + 1 FROM application.lk_task_eqm_items), 'REQUEST_EDITED_CORRECTLY', 'هل تم تعديل الطلب بصورة صحيحة؟', 'Does the request updated correctly?', 'BOOLEAN');

INSERT INTO application.task_eqm_type_items (lk_task_eqm_type_id, lk_task_eqm_item_id)
VALUES ((SELECT max(id) FROM application.lk_task_eqm_types), (SELECT max(id) FROM application.lk_task_eqm_items));

INSERT INTO application.lk_task_eqm_items (id, code, name_ar, name_en, rating_value_type)
VALUES ((SELECT max(id) + 1 FROM application.lk_task_eqm_items), 'DOCUMENTS_ATTACHED_CORRECTLY', 'هل تم ارفاق المستندات القانونية بصورة صحيحة؟', 'Is legal documents is attached correctly', 'BOOLEAN');

INSERT INTO application.task_eqm_type_items (lk_task_eqm_type_id, lk_task_eqm_item_id)
VALUES ((SELECT max(id) FROM application.lk_task_eqm_types), (SELECT max(id) FROM application.lk_task_eqm_items));