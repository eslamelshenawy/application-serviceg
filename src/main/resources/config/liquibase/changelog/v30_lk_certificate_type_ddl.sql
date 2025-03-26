

CREATE TABLE application.lk_certificate_types (
                                          id int4 NOT NULL,
                                          code varchar(255) NULL,
                                          name_ar varchar(255) NULL,
                                          name_en varchar(255) NULL,
                                          CONSTRAINT lk_certificate_types_pkey PRIMARY KEY (id)
);


INSERT INTO application.lk_certificate_types
(id, code, name_ar, name_en)
VALUES(1, 'ISSUE_CERTIFICATE', 'اصدار شهادة', 'Issuing a certificate');

INSERT INTO application.lk_certificate_types
(id, code, name_ar, name_en)
VALUES(2, 'CERTIFIED_REGISTER_COPY', 'نسخة مصدقة من السجل', 'Certified copy of the register');


INSERT INTO application.lk_certificate_types
(id, code, name_ar, name_en)
VALUES(3, 'CERTIFIED_PRIORITY_COPY', 'نسخة مصدقة من حقوق الأولوية', 'A certified copy of priority rights');


INSERT INTO application.lk_certificate_types
(id, code, name_ar, name_en)
VALUES(4, 'PROOF_ISSUANCE_APPLICATION', 'اثبات اصدار الطلب', 'Proof of issuance of the application');


INSERT INTO application.lk_certificate_types
(id, code, name_ar, name_en)
VALUES(5, 'PROOF_FACTS_APPEAL', 'اثبات الوقائع بخصوص طلب التظلم', 'Proof of facts regarding the appeal request');


INSERT INTO application.lk_certificate_types
(id, code, name_ar, name_en)
VALUES(6, 'SECRET_DESIGN_DOCUMENT', 'وثيقة اثبات التصميم السرى', 'Secret design proof document');


INSERT INTO application.lk_certificate_types
(id, code, name_ar, name_en)
VALUES(7, 'FINAL_SPECIFICATION_DOCUMENT', 'وثيقة المواصفات النهائية', 'Final specification document');


INSERT INTO application.lk_certificate_types
(id, code, name_ar, name_en)
VALUES(8, 'ALL_APPLICATION_RECORDS', 'جميع السجلات الخاصة بالطلب', 'All records of the application');

