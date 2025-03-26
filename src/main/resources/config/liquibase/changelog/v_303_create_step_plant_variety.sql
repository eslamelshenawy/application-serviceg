--------------------------------------------------------------------------------------------------------------------
-- Insert the first row
INSERT INTO application.lk_sections (id, code, name_ar, name_en)
VALUES ((SELECT max(id) + 1 FROM application.lk_sections), 'SUMMARY', 'الملخص', 'Summary');

-- Insert the second row
INSERT INTO application.lk_sections (id, code, name_ar, name_en)
VALUES ((SELECT max(id) + 1 FROM application.lk_sections), 'TECHNICAL_SURVEY', 'الاستبيان الفني', 'Technical Survey');

-- Insert the third row
INSERT INTO application.lk_sections (id, code, name_ar, name_en)
VALUES ((SELECT max(id) + 1 FROM application.lk_sections), 'TRAITS_RESISTANCE', 'الصفات والمقاومة للامراض', 'Traits and Disease Resistance');

-- Insert the fourth row
INSERT INTO application.lk_sections (id, code, name_ar, name_en)
VALUES ((SELECT max(id) + 1 FROM application.lk_sections), 'PROOF_EXCELLENCE', 'إثبات التميز', 'Proof of Excellence');

-- Insert the fifth row
INSERT INTO application.lk_sections (id, code, name_ar, name_en)
VALUES ((SELECT max(id) + 1 FROM application.lk_sections), 'EXAMINATION_DATA', 'بيانات الفحص', 'Examination Data');---