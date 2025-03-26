--ALTER TRADEMARKE DETAILS TABLE
ALTER TABLE application.trademark_details ALTER COLUMN mark_description TYPE varchar(2000) USING mark_description::varchar(2000);
ALTER TABLE application.trademark_details ALTER COLUMN word_mark TYPE varchar(2000) USING word_mark::varchar(2000);
ALTER TABLE application.trademark_details ALTER COLUMN name_en TYPE varchar(2000) USING name_en::varchar(2000);
ALTER TABLE application.trademark_details ALTER COLUMN name_ar TYPE varchar(2000) USING name_ar::varchar(2000);
ALTER TABLE application.trademark_details ALTER COLUMN exhibition_info TYPE varchar(2000) USING exhibition_info::varchar(2000);
--ALTER PATENT DETAILS TABLE
ALTER TABLE application.applications_info ALTER COLUMN address TYPE varchar(2000) USING address::varchar(2000);
ALTER TABLE application.applications_info ALTER COLUMN title_en TYPE varchar(2000) USING title_en::varchar(2000);
ALTER TABLE application.applications_info ALTER COLUMN title_ar TYPE varchar(2000) USING title_ar::varchar(2000);