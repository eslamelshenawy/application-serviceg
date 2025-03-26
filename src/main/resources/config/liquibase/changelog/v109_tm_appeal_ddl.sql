INSERT INTO application.lk_support_services
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, "cost", desc_ar, desc_en, name_ar, name_en, code)
VALUES((select max(id + 1) from application.lk_support_services), NULL, NULL, NULL, NULL, 0, 0, 'طلب تظلم', 'Appeal Request', 'طلب تظلم', 'Appeal Request', 'TRADEMARK_APPEAL_REQUEST');


INSERT INTO application.support_service_application_categories
(support_service_id, category_id)
VALUES((select max(id) from application.lk_support_services), 5);



ALTER TABLE application.application_support_services_type ADD created_by_customer_code varchar(255) NULL;


CREATE TABLE application.trademark_appeal_request (
      id int8 NOT NULL,
      appeal_reason text NULL,
      appeal_request_type varchar(255) NULL,
      support_services_id int8 NULL,
      final_decision_notes text NULL,
      department_reply text NULL,
      process_request_id int8 NULL,
      CONSTRAINT trademark_appeal_request_pkey PRIMARY KEY (id),
      CONSTRAINT fkijflftqsnbp5y174cjhlo4me8 FOREIGN KEY (support_services_id) REFERENCES application.application_support_services_type(id),
      CONSTRAINT fkk6phaytb7p60dqja9vg6rxc17 FOREIGN KEY (id) REFERENCES application.application_support_services_type(id)
);




CREATE TABLE application.trademark_appeal_request_documents (
    trademark_appeal_request_id int8 NOT NULL,
    document_id int8 NOT NULL,
    CONSTRAINT ukaoblmseq7uoxqhn6r20fbdq4y UNIQUE (trademark_appeal_request_id, document_id),
    CONSTRAINT fkb7trcesyqnf3yxrk10dqx2je0 FOREIGN KEY (trademark_appeal_request_id) REFERENCES application.trademark_appeal_request(id),
    CONSTRAINT fkntcgkx9ansl8nwda05cg5y54i FOREIGN KEY (document_id) REFERENCES application.documents(id)
);
