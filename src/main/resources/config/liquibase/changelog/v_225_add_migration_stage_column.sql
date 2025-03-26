ALTER TABLE application.annual_fees_request ADD COLUMN if not exists migration_stage INTEGER DEFAULT 0;
ALTER TABLE application.application_agents ADD COLUMN if not exists  migration_stage INTEGER DEFAULT 0;
ALTER TABLE application.application_customers ADD COLUMN if not exists  migration_stage INTEGER DEFAULT 0;
ALTER TABLE application.application_nice_classifications ADD COLUMN if not exists  migration_stage INTEGER DEFAULT 0;
ALTER TABLE application.application_priority ADD COLUMN if not exists  migration_stage INTEGER DEFAULT 0;
ALTER TABLE application.application_publication ADD COLUMN if not exists  migration_stage INTEGER DEFAULT 0;
ALTER TABLE application.application_relevant ADD COLUMN if not exists  migration_stage INTEGER DEFAULT 0;
ALTER TABLE application.application_sub_classifications ADD COLUMN if not exists  migration_stage INTEGER DEFAULT 0;
ALTER TABLE application.application_users ADD COLUMN if not exists  migration_stage INTEGER DEFAULT 0;
ALTER TABLE application.applications_info ADD COLUMN if not exists  migration_stage INTEGER DEFAULT 0;
ALTER TABLE application.certificates_request ADD COLUMN if not exists  migration_stage INTEGER DEFAULT 0;
ALTER TABLE application.change_ownership_customers ADD COLUMN if not exists  migration_stage INTEGER DEFAULT 0;
ALTER TABLE application.change_ownership_request ADD COLUMN if not exists  migration_stage INTEGER DEFAULT 0;
ALTER TABLE application.documents ADD COLUMN if not exists  migration_stage INTEGER DEFAULT 0;
ALTER TABLE application.documents_template ADD COLUMN if not exists  migration_stage INTEGER DEFAULT 0;
ALTER TABLE application.industrial_design_details ADD COLUMN if not exists  migration_stage INTEGER DEFAULT 0;
ALTER TABLE application.initial_modification_request ADD COLUMN if not exists  migration_stage INTEGER DEFAULT 0;
ALTER TABLE application.licence_request ADD COLUMN if not exists  migration_stage INTEGER DEFAULT 0;
ALTER TABLE application.patent_attribute_change_logs ADD COLUMN if not exists  migration_stage INTEGER DEFAULT 0;
ALTER TABLE application.patent_details ADD COLUMN if not exists  migration_stage INTEGER DEFAULT 0;
ALTER TABLE application.pct ADD COLUMN if not exists  migration_stage INTEGER DEFAULT 0;
ALTER TABLE application.publication_issue ADD COLUMN if not exists  migration_stage INTEGER DEFAULT 0;
ALTER TABLE application.publication_issue_application_publication ADD COLUMN if not exists  migration_stage INTEGER DEFAULT 0;
ALTER TABLE application.trademark_details ADD COLUMN if not exists  migration_stage INTEGER DEFAULT 0;
ALTER TABLE application.revoke_products ADD COLUMN if not exists  migration_stage INTEGER DEFAULT 0;
ALTER TABLE application.revoke_voluntary ADD COLUMN if not exists  migration_stage INTEGER DEFAULT 0;
ALTER TABLE application.revoke_by_court_order ADD COLUMN if not exists  migration_stage INTEGER DEFAULT 0;
ALTER TABLE application.application_support_services_type ADD COLUMN if not exists  migration_stage INTEGER DEFAULT 0;
ALTER TABLE application.support_service_customer ADD COLUMN if not exists  migration_stage INTEGER DEFAULT 0;
ALTER TABLE application.application_relevant_type ADD COLUMN if not exists  migration_stage INTEGER DEFAULT 0;
ALTER TABLE application.application_search ADD COLUMN if not exists  migration_stage INTEGER DEFAULT 0;
ALTER TABLE application.application_edit_name_address_request ADD COLUMN if not exists  migration_stage INTEGER DEFAULT 0;
ALTER TABLE application.revoke_licence_request ADD COLUMN if not exists  migration_stage INTEGER DEFAULT 0;
ALTER TABLE application.protection_elements ADD COLUMN if not exists  migration_stage INTEGER DEFAULT 0;
ALTER TABLE application.application_drawing ADD COLUMN if not exists  migration_stage INTEGER DEFAULT 0;
ALTER TABLE application.support_service_reviews ADD COLUMN if not exists  migration_stage INTEGER DEFAULT 0;
ALTER TABLE application.trademark_appeal_request ADD COLUMN if not exists  migration_stage INTEGER DEFAULT 0;
ALTER TABLE application.opposition_request ADD COLUMN if not exists  migration_stage INTEGER DEFAULT 0;
ALTER TABLE application.application_edit_trademark_image_request ADD COLUMN if not exists  migration_stage INTEGER DEFAULT 0;
ALTER TABLE application.application_certificate_documents ADD COLUMN if not exists  migration_stage INTEGER DEFAULT 0;
ALTER TABLE application.appeal_committee_opinion ADD COLUMN if not exists  migration_stage INTEGER DEFAULT 0;
ALTER TABLE application.application_accelerated ADD COLUMN if not exists  migration_stage INTEGER DEFAULT 0;
ALTER TABLE application.application_checking_reports ADD COLUMN if not exists  migration_stage INTEGER DEFAULT 0;
ALTER TABLE application.application_installments ADD COLUMN if not exists  migration_stage INTEGER DEFAULT 0;
ALTER TABLE application.application_notes ADD COLUMN if not exists  migration_stage INTEGER DEFAULT 0;
ALTER TABLE application.application_office_reports ADD COLUMN if not exists  migration_stage INTEGER DEFAULT 0;