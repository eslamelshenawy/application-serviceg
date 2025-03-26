

INSERT INTO application.lk_sections
(id, code, name_ar, name_en)
VALUES(7, 'INDUSTRIAL_DESIGN_MODELS', 'نماذج التصميم صناعي', 'Industrial design models');

INSERT INTO application.lk_sections
(id, code, name_ar, name_en)
VALUES(8, 'SELECT_CLASSIFICATION', 'اختيار الفئة', 'Select Classification');

ALTER TABLE application.application_notes ADD task_definition_key varchar(255) NULL;
