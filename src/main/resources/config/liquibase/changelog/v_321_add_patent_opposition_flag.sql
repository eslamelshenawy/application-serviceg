ALTER TABLE application.patent_details ADD COLUMN patent_opposition BOOLEAN DEFAULT TRUE;
----------------------------------------------------------------------------------------
INSERT INTO application.lk_sections
(id, code, name_ar, name_en)
VALUES((select max(id) + 1 from application.lk_sections), 'PT_APPLICANT_OPPOSITION', 'اعتراض مقدم طلب براءة الاختراع علي دعوة التصحيح', 'PT Applicant Opposition');