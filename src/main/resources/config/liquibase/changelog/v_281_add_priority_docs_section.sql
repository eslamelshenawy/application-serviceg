INSERT INTO application.lk_sections
(id, code, name_ar, name_en)
VALUES((select max(id)+1 from application.lk_sections), 'PRECEDENCE', 'الاسبقية', 'Precedence files');
