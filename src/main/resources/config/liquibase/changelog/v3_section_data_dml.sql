

update application.application_notes set section_id = null;
update application.lk_notes set section_id = null;
delete from application.lk_sections;

INSERT INTO application.lk_sections
(id, code, name_ar, name_en)
VALUES(1, 'APPLICATION', 'مقدم الطلب', 'Applicant');

INSERT INTO application.lk_sections
(id, code, name_ar, name_en)
VALUES(2, 'INVENTORS', 'المخترعون', 'Inventors');

INSERT INTO application.lk_sections
(id, code, name_ar, name_en)
VALUES(3, 'DATA', 'بيانات الطلب', 'Data');

INSERT INTO application.lk_sections
(id, code, name_ar, name_en)
VALUES(4, 'FILES', 'ملفات الطلب', 'Files');

INSERT INTO application.lk_sections
(id, code, name_ar, name_en)
VALUES(6, 'REQUESTS', 'طلبات التسجيل', 'Registration requests');

INSERT INTO application.lk_sections
(id, code, name_ar, name_en)
VALUES(7, 'MODELS', 'نماذج', 'Models');

INSERT INTO application.lk_sections
(id, code, name_ar, name_en)
VALUES(8, 'CLASSIFICATIONS', 'الفئات', 'Classifications');

update application.application_notes set section_id = 1;
update application.lk_notes set section_id = 1;