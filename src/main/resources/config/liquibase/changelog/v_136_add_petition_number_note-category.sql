ALTER TABLE application.pct ADD petition_number int8 NULL;

CREATE TABLE application.lk_note_category (
                                           id int4 NOT NULL,
                                           code varchar(255) NULL,
                                           name_ar varchar(255) NULL,
                                           name_en varchar(255) NULL,
                                           CONSTRAINT lk_categories_pkey PRIMARY KEY (id)
);

ALTER TABLE application.lk_notes ADD note_category_id int4 NULL;

ALTER TABLE application.lk_notes ADD CONSTRAINT lk_notes_fk FOREIGN KEY (note_category_id) REFERENCES application.lk_note_category(id);

ALTER TABLE application.applications_info ADD grant_number varchar(255) NULL;

ALTER TABLE application.application_notes ADD stage_key varchar(255) NULL;

UPDATE application.lk_application_category
SET abbreviation='SA'
WHERE application.lk_application_category.saip_code='PATENT';

UPDATE application.applications_info
SET is_plt=FALSE
WHERE application.applications_info.is_plt is NULL;

ALTER TABLE application.applications_info ALTER COLUMN is_plt SET NOT NULL;

ALTER TABLE application.applications_info ALTER COLUMN is_plt SET DEFAULT TRUE;



