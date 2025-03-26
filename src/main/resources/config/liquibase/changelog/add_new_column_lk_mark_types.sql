ALTER TABLE application.lk_mark_types
ADD COLUMN is_deleted INT4;


update application.lk_mark_types set is_deleted = 0;


update application.lk_mark_types set is_deleted = 1 where code = 'TEMP_MARK';
