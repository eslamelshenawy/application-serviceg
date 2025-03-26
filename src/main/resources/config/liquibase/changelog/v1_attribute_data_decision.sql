

INSERT INTO application.lk_attributes
(id, code, name_ar, name_en)
VALUES(6, 'GADA', 'الجدة', 'Gada');

INSERT INTO application.lk_attributes
(id, code, name_ar, name_en)
VALUES(7, 'INNOVATIVE_STEP', 'الخطوة الابتكارية', 'Innovative Step');

INSERT INTO application.lk_attributes
(id, code, name_ar, name_en)
VALUES(8, 'INDUSTRIAL_APPLICABLE', 'قابلة للتطبيق الصناعى', 'Industrial Applicable');

ALTER TABLE application.substantive_examination_reports ADD decision varchar(255) NULL;
