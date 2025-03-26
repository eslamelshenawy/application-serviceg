
update application.trademark_details set tag_type_desc_id = 3 where tag_type_desc_id = 5;

DELETE FROM application.lk_tag_type_desc
WHERE code = 'SMELL';