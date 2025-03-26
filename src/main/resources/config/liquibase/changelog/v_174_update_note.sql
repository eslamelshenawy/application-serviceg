
UPDATE application.lk_attributes
SET code='GENERAL_NOTES',name_ar='ملاحظات عامة',name_en='General topics'
WHERE code='GRANT' and name_ar='مواضيع قابلة للمنح' and name_en='Grantable topics';


UPDATE application.lk_notes
SET section_id=(SELECT x.id FROM application.lk_sections x where code = 'REPORT')
WHERE section_id = (SELECT x.id FROM application.lk_sections x where code = 'FILES')
and attribute_id = (SELECT x.id FROM application.lk_attributes x where code = 'GENERAL_NOTES');


UPDATE application.lk_notes
SET attribute_id= (SELECT x.id FROM application.lk_attributes x where code = 'PROTICTION_AR')
WHERE  section_id = (SELECT x.id FROM application.lk_sections x where code = 'REPORT')
  and attribute_id = (SELECT x.id FROM application.lk_attributes x where code = 'GENERAL_NOTES')
  and note_category_id = (SELECT x.id FROM application.lk_note_category x where code = 'NonGRANT');


INSERT INTO application.lk_note_category (id ,code,name_ar,name_en)
VALUES ((select max(id) + 1 from application.lk_note_category),'RESULT','نتيجة الفحص','The test result');


UPDATE application.lk_notes
SET attribute_id=(SELECT x.id FROM application.lk_attributes x where code = 'GENERAL_NOTES') ,
    note_category_id = (SELECT x.id FROM application.lk_note_category x where code = 'RESULT')
WHERE section_id = (SELECT x.id FROM application.lk_sections x where code = 'REPORT')
  and attribute_id = (SELECT x.id FROM application.lk_attributes x where code = 'RESULT');



