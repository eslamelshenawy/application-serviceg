CREATE INDEX IF NOT EXISTS publication_cat_id_index ON application.publication_issue(lk_application_category_id);
CREATE INDEX IF NOT EXISTS application_info_id_index ON application.application_publication(application_info_id);
CREATE INDEX IF NOT EXISTS application_publication_is_published_index ON application.application_publication(is_published);
