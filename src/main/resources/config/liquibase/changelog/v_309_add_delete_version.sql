ALTER TABLE application.lk_classification_versions ADD is_deleted int4 NULL;

update application.lk_classification_versions set is_deleted = 0 ;

