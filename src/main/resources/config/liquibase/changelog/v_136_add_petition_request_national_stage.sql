INSERT INTO application.lk_support_services
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, "cost", desc_ar, desc_en, name_ar, name_en, code)
VALUES((select (max(id) + 1) from application.lk_support_services), NULL, NULL, NULL, NULL, 0, 0, 'طلب التماس الدخول للمرحلة الوطنية', 'Petition Request National Stage', 'طلب التماس الدخول للمرحلة الوطنية', 'Petition Request National Stage', 'PETITION_REQUEST_NATIONAL_STAGE');

INSERT INTO application.lk_document_type (id, name, code, name_ar, description, category, doc_order, is_deleted)
VALUES ((select (max(id) + 1) from application.lk_document_type), 'Petition Request National Stage', 'Petition Request National Stage', 'وثائق طلب التماس الدخول للمرحلة الوطنية', 'وثائق طلب التماس الدخول للمرحلة الوطنية ','PETITION_REQUEST_NATIONAL_STAGE', NULL,0);



insert into application.support_service_application_categories (support_service_id , category_id)
values((select (max(id)) from application.lk_support_services), 1);



create table application.petition_request_national_stage(
 id int8 PRIMARY KEY NOT NULL,
 global_application_number VARCHAR(250),
 reason TEXT
);


CREATE TABLE application.petition_request_national_stage_documents (
petition_request_national_stage_id int8 ,
document_id int8 ,
FOREIGN KEY (petition_request_national_stage_id) REFERENCES application.petition_request_national_stage(id),
FOREIGN KEY (document_id) REFERENCES application.documents(id)
);







