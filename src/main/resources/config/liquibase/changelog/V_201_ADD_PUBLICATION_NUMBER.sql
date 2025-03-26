ALTER TABLE application.application_publication ADD PUBLICATION_NUMBER varchar(255) NULL;

UPDATE application.application_publication ap
SET PUBLICATION_NUMBER = (
    SELECT ai.application_number
    FROM application.applications_info ai
    WHERE ai.lk_category_id = 1
      and ai.id = ap.application_info_id
);

UPDATE application.application_publication ap
SET PUBLICATION_NUMBER = id
where ap.application_info_id = (
    SELECT ai.id
    FROM application.applications_info ai
    WHERE ai.lk_category_id <> 1
      and ai.id = ap.application_info_id
);