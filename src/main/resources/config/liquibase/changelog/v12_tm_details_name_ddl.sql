

ALTER TABLE application.trademark_details ADD name_en varchar(255) NULL;
ALTER TABLE application.trademark_details ADD name_ar varchar(255) NULL;

update application.lk_tag_type_desc  set name_ar = 'تصورية ولفظية' where code = 'VISUAL_VERBAL';
update application.lk_tag_languages set code = 'LATIN' where id = 2;
