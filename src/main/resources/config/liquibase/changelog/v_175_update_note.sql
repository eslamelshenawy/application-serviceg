UPDATE application.lk_notes
SET section_id=(SELECT x.id FROM application.lk_sections x where code = 'FILES')
WHERE section_id = (SELECT x.id FROM application.lk_sections x where code = 'REPORT')
and attribute_id = (SELECT x.id FROM application.lk_attributes x where code = 'PROTICTION_AR')
and note_category_id = (SELECT x.id FROM application.lk_note_category x where code = 'NonGRANT');
