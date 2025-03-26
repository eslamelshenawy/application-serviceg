
UPDATE application.lk_notes
SET section_id=(SELECT id FROM application.lk_sections WHERE code = 'FILES')
WHERE note_category_id is not NULL;