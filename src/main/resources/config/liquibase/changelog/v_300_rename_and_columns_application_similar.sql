
ALTER TABLE application.application_search_similars RENAME COLUMN application_title TO brand_name_ar;
ALTER TABLE application.application_search_similars ADD COLUMN brand_name_en VARCHAR(250);
update application.application_search_similars set saip_doc_id = NULL;
ALTER TABLE application.application_search_similars DROP COLUMN saip_doc_id;
ALTER TABLE application.application_search_similars DROP COLUMN ip_search_id;

