UPDATE application.lk_notes
SET section_id=(SELECT x.id FROM application.lk_sections x
                WHERE code = 'DATA')
WHERE section_id=(SELECT x.id FROM application.lk_sections x
                  WHERE code = 'FILES')
AND category_id = (SELECT x.id FROM application.lk_application_category x
                   WHERE saip_code = 'PATENT')
AND attribute_id = (SELECT x.id FROM application.lk_attributes x
WHERE code = 'PRIORITY');
