INSERT INTO application.lk_sections
(id, code, name_ar, name_en)
VALUES((select max (id+1) from application.lk_sections), 'PV_APPLICANT_OPPOSITION', 'اعتراض مقدم طلب الصنف النباتي علي دعوة التصحيح', 'Pv Applicant Opposition');