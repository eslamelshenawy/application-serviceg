ALTER TABLE application.supporting_evidence
ADD CONSTRAINT  fk_document_id FOREIGN KEY (document_id) REFERENCES application.documents(id),
ADD CONSTRAINT  fk_substantive_examination_reports_id FOREIGN KEY (substantive_examination_reports_id) REFERENCES application.substantive_examination_reports(id),
ADD CONSTRAINT  fk_application_info_id FOREIGN KEY (application_info_id) REFERENCES application.applications_info(id);