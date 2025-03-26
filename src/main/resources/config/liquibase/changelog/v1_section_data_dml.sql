

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
VALUES(3, 'PATENT_DATA', 'بيانات الاختراع', 'Patent data');

INSERT INTO application.lk_sections
(id, code, name_ar, name_en)
VALUES(4, 'PATENT_FILES', 'ملفات براءة الاختراع', 'Patent files');

INSERT INTO application.lk_sections
(id, code, name_ar, name_en)
VALUES(6, 'REGISTRATION_REQUESTS', 'طلبات التسجيل', 'Registration requests');

update application.application_notes set section_id = 1;
update application.lk_notes set section_id = 1;