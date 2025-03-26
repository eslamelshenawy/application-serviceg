--Table: application.lk_accelerated_type
COMMENT ON COLUMN application.lk_accelerated_type.id IS 'Primary key for the accelerated type.';
COMMENT ON COLUMN application.lk_accelerated_type.created_by_user IS 'Identifier for the user who created the accelerated type.';
COMMENT ON COLUMN application.lk_accelerated_type.created_date IS 'Date and time when the accelerated type was created.';
COMMENT ON COLUMN application.lk_accelerated_type.modified_by_user IS 'Identifier for the user who last modified the accelerated type.';
COMMENT ON COLUMN application.lk_accelerated_type.modified_date IS 'Date and time when the accelerated type was last modified.';
COMMENT ON COLUMN application.lk_accelerated_type.is_deleted IS 'Flag indicating whether this accelerated type is soft deleted or not.';
COMMENT ON COLUMN application.lk_accelerated_type.name_ar IS 'Name of the accelerated type in Arabic.';
COMMENT ON COLUMN application.lk_accelerated_type.name_en IS 'Name of the accelerated type in English.';
COMMENT ON COLUMN application.lk_accelerated_type."show" IS 'Flag indicating whether the accelerated type is visible (true) or hidden (false).';
COMMENT ON COLUMN application.lk_accelerated_type.application_category_id IS 'Foreign key referencing the application category associated with this accelerated type.';


--Table: application.lk_annual_request_years
COMMENT ON COLUMN application.lk_annual_request_years.id IS 'Primary key for the annual request years.';
COMMENT ON COLUMN application.lk_annual_request_years.code IS 'Code associated with the annual request years.';
COMMENT ON COLUMN application.lk_annual_request_years.name_ar IS 'Name of the annual request years in Arabic.';
COMMENT ON COLUMN application.lk_annual_request_years.name_en IS 'Name of the annual request years in English.';
COMMENT ON COLUMN application.lk_annual_request_years.years_count IS 'Number of years for the annual request period.';


--Table: application.lk_applicant_category
COMMENT ON COLUMN application.lk_applicant_category.id IS 'Primary key for the applicant category.';
COMMENT ON COLUMN application.lk_applicant_category.created_by_user IS 'Identifier for the user who created the applicant category.';
COMMENT ON COLUMN application.lk_applicant_category.created_date IS 'Date and time when the applicant category was created.';
COMMENT ON COLUMN application.lk_applicant_category.modified_by_user IS 'Identifier for the user who last modified the applicant category.';
COMMENT ON COLUMN application.lk_applicant_category.saip_code IS 'SAIP code associated with the applicant category.';
COMMENT ON COLUMN application.lk_applicant_category.modified_date IS 'Date and time when the applicant category was last modified.';
COMMENT ON COLUMN application.lk_applicant_category.is_deleted IS 'Flag indicating whether this applicant category is soft deleted or not.';
COMMENT ON COLUMN application.lk_applicant_category.applicant_category_name_ar IS 'Name of the applicant category in Arabic.';
COMMENT ON COLUMN application.lk_applicant_category.applicant_category_name_en IS 'Name of the applicant category in English.';


--Table: application.lk_application_category
COMMENT ON COLUMN application.lk_application_category.id IS 'Primary key for the application category.';
COMMENT ON COLUMN application.lk_application_category.created_by_user IS 'Identifier for the user who created the application category.';
COMMENT ON COLUMN application.lk_application_category.created_date IS 'Date and time when the application category was created.';
COMMENT ON COLUMN application.lk_application_category.modified_by_user IS 'Identifier for the user who last modified the application category.';
COMMENT ON COLUMN application.lk_application_category.modified_date IS 'Date and time when the application category was last modified.';
COMMENT ON COLUMN application.lk_application_category.saip_code IS 'SAIP code associated with the application category.';
COMMENT ON COLUMN application.lk_application_category.abbreviation IS 'Abbreviation for the application category.';
COMMENT ON COLUMN application.lk_application_category.is_deleted IS 'Flag indicating whether this application category is soft deleted or not.';
COMMENT ON COLUMN application.lk_application_category.application_category_desc_ar IS 'Description of the application category in Arabic.';
COMMENT ON COLUMN application.lk_application_category.application_category_desc_en IS 'Description of the application category in English.';
COMMENT ON COLUMN application.lk_application_category.application_category_is_active IS 'Flag indicating whether the application category is active (true) or inactive (false).';

-- Table: application.lk_application_collaborative_research
COMMENT ON COLUMN application.lk_application_collaborative_research.id IS 'Primary key for the collaborative research application.';
COMMENT ON COLUMN application.lk_application_collaborative_research.created_by_user IS 'Identifier for the user who created the collaborative research application.';
COMMENT ON COLUMN application.lk_application_collaborative_research.created_date IS 'Date and time when the collaborative research application was created.';
COMMENT ON COLUMN application.lk_application_collaborative_research.modified_by_user IS 'Identifier for the user who last modified the collaborative research application.';
COMMENT ON COLUMN application.lk_application_collaborative_research.modified_date IS 'Date and time when the collaborative research application was last modified.';
COMMENT ON COLUMN application.lk_application_collaborative_research.is_deleted IS 'Flag indicating whether this collaborative research application is soft deleted or not.';
COMMENT ON COLUMN application.lk_application_collaborative_research.name_ar IS 'Name of the collaborative research application in Arabic.';
COMMENT ON COLUMN application.lk_application_collaborative_research.name_en IS 'Name of the collaborative research application in English.';

-- Table: application.lk_application_priority_status
COMMENT ON COLUMN application.lk_application_priority_status.id IS 'Primary key for the application priority status.';
COMMENT ON COLUMN application.lk_application_priority_status.created_by_user IS 'Identifier for the user who created the application priority status.';
COMMENT ON COLUMN application.lk_application_priority_status.created_date IS 'Date and time when the application priority status was created.';
COMMENT ON COLUMN application.lk_application_priority_status.modified_by_user IS 'Identifier for the user who last modified the application priority status.';
COMMENT ON COLUMN application.lk_application_priority_status.modified_date IS 'Date and time when the application priority status was last modified.';
COMMENT ON COLUMN application.lk_application_priority_status.is_deleted IS 'Flag indicating whether this application priority status is soft deleted or not.';
COMMENT ON COLUMN application.lk_application_priority_status.ips_status_desc_ar IS 'Description of the application priority status in Arabic.';
COMMENT ON COLUMN application.lk_application_priority_status.ips_status_desc_en IS 'Description of the application priority status in English.';
COMMENT ON COLUMN application.lk_application_priority_status.code IS 'Code associated with the application priority status.';

-- Table: application.lk_application_services
COMMENT ON COLUMN application.lk_application_services.id IS 'Primary key for the application services.';
COMMENT ON COLUMN application.lk_application_services.created_by_user IS 'Identifier for the user who created the application services.';
COMMENT ON COLUMN application.lk_application_services.created_date IS 'Date and time when the application services were created.';
COMMENT ON COLUMN application.lk_application_services.modified_by_user IS 'Identifier for the user who last modified the application services.';
COMMENT ON COLUMN application.lk_application_services.modified_date IS 'Date and time when the application services were last modified.';
COMMENT ON COLUMN application.lk_application_services.is_deleted IS 'Flag indicating whether these application services are soft deleted or not.';
COMMENT ON COLUMN application.lk_application_services.code IS 'Code associated with the application services.';
COMMENT ON COLUMN application.lk_application_services.name_ar IS 'Name of the application services in Arabic.';
COMMENT ON COLUMN application.lk_application_services.name_en IS 'Name of the application services in English.';
COMMENT ON COLUMN application.lk_application_services.operation_number IS 'Operation number for the application services.';
COMMENT ON COLUMN application.lk_application_services.category_id IS 'Foreign key referencing the category associated with these application services.';

-- Table: application.lk_application_status
COMMENT ON COLUMN application.lk_application_status.id IS 'Primary key for the application status.';
COMMENT ON COLUMN application.lk_application_status.created_by_user IS 'Identifier for the user who created the application status.';
COMMENT ON COLUMN application.lk_application_status.created_date IS 'Date and time when the application status was created.';
COMMENT ON COLUMN application.lk_application_status.modified_by_user IS 'Identifier for the user who last modified the application status.';
COMMENT ON COLUMN application.lk_application_status.modified_date IS 'Date and time when the application status was last modified.';
COMMENT ON COLUMN application.lk_application_status.is_deleted IS 'Flag indicating whether this application status is soft deleted or not.';
COMMENT ON COLUMN application.lk_application_status.ips_status_desc_ar IS 'Description of the application status in Arabic.';
COMMENT ON COLUMN application.lk_application_status.ips_status_desc_en IS 'Description of the application status in English.';
COMMENT ON COLUMN application.lk_application_status.code IS 'Code associated with the application status.';
COMMENT ON COLUMN application.lk_application_status.ips_status_desc_ar_external IS 'External description of the application status in Arabic.';
COMMENT ON COLUMN application.lk_application_status.ips_status_desc_en_external IS 'External description of the application status in English.';
COMMENT ON COLUMN application.lk_application_status.application_category_id IS 'Foreign key referencing the application category associated with this status.';

-- Table: application.lk_attributes
COMMENT ON COLUMN application.lk_attributes.id IS 'Primary key for the attributes.';
COMMENT ON COLUMN application.lk_attributes.code IS 'Code associated with the attributes.';
COMMENT ON COLUMN application.lk_attributes.name_ar IS 'Name of the attributes in Arabic.';
COMMENT ON COLUMN application.lk_attributes.name_en IS 'Name of the attributes in English.';

-- Table: application.lk_certificate_status
COMMENT ON COLUMN application.lk_certificate_status.id IS 'Primary key for the certificate status.';
COMMENT ON COLUMN application.lk_certificate_status.code IS 'Code associated with the certificate status.';
COMMENT ON COLUMN application.lk_certificate_status.name_ar IS 'Name of the certificate status in Arabic.';
COMMENT ON COLUMN application.lk_certificate_status.name_en IS 'Name of the certificate status in English.';
COMMENT ON COLUMN application.lk_certificate_status.name_ar_external IS 'External name of the certificate status in Arabic.';
COMMENT ON COLUMN application.lk_certificate_status.name_en_external IS 'External name of the certificate status in English.';

-- Table: application.lk_certificate_types
COMMENT ON COLUMN application.lk_certificate_types.id IS 'Primary key for the certificate types.';
COMMENT ON COLUMN application.lk_certificate_types.code IS 'Code associated with the certificate types.';
COMMENT ON COLUMN application.lk_certificate_types.name_ar IS 'Name of the certificate types in Arabic.';
COMMENT ON COLUMN application.lk_certificate_types.name_en IS 'Name of the certificate types in English.';
COMMENT ON COLUMN application.lk_certificate_types.enabled IS 'Flag indicating whether this certificate type is enabled or not.';

-- Table: application.lk_classification_units
COMMENT ON COLUMN application.lk_classification_units.id IS 'Primary key for the classification units.';
COMMENT ON COLUMN application.lk_classification_units.code IS 'Code associated with the classification units.';
COMMENT ON COLUMN application.lk_classification_units.name_ar IS 'Name of the classification units in Arabic.';
COMMENT ON COLUMN application.lk_classification_units.name_en IS 'Name of the classification units in English.';
COMMENT ON COLUMN application.lk_classification_units.category_id IS 'Foreign key referencing the category associated with these classification units.';
COMMENT ON COLUMN application.lk_classification_units.created_by_user IS 'Identifier for the user who created the classification units.';
COMMENT ON COLUMN application.lk_classification_units.created_date IS 'Date and time when the classification units were created.';
COMMENT ON COLUMN application.lk_classification_units.modified_by_user IS 'Identifier for the user who last modified the classification units.';
COMMENT ON COLUMN application.lk_classification_units.modified_date IS 'Date and time when the classification units were last modified.';
COMMENT ON COLUMN application.lk_classification_units.is_deleted IS 'Flag indicating whether these classification units are soft deleted or not';


-- Table: application.lk_classification_versions
COMMENT ON COLUMN application.lk_classification_versions.id IS 'Primary key for the classification versions.';
COMMENT ON COLUMN application.lk_classification_versions.code IS 'Code associated with the classification versions.';
COMMENT ON COLUMN application.lk_classification_versions.name_ar IS 'Name of the classification versions in Arabic.';
COMMENT ON COLUMN application.lk_classification_versions.name_en IS 'Name of the classification versions in English.';
COMMENT ON COLUMN application.lk_classification_versions.category_id IS 'Foreign key referencing the category associated with these classification versions.';

-- Table: application.lk_client_type
COMMENT ON COLUMN application.lk_client_type.id IS 'Primary key for the client type.';
COMMENT ON COLUMN application.lk_client_type.created_by_user IS 'Identifier for the user who created the client type.';
COMMENT ON COLUMN application.lk_client_type.created_date IS 'Date and time when the client type was created.';
COMMENT ON COLUMN application.lk_client_type.modified_by_user IS 'Identifier for the user who last modified the client type.';
COMMENT ON COLUMN application.lk_client_type.modified_date IS 'Date and time when the client type was last modified.';
COMMENT ON COLUMN application.lk_client_type.is_deleted IS 'Flag indicating whether this client type is soft deleted or not.';
COMMENT ON COLUMN application.lk_client_type.code IS 'Code associated with the client type.';
COMMENT ON COLUMN application.lk_client_type.type_ar IS 'Type of the client in Arabic.';
COMMENT ON COLUMN application.lk_client_type.type_en IS 'Type of the client in English.';

-- Table: application.lk_databases
COMMENT ON COLUMN application.lk_databases.id IS 'Primary key for the databases.';
COMMENT ON COLUMN application.lk_databases.code IS 'Code associated with the databases.';
COMMENT ON COLUMN application.lk_databases.name_ar IS 'Name of the databases in Arabic.';
COMMENT ON COLUMN application.lk_databases.name_en IS 'Name of the databases in English.';

-- Table: application.lk_day_of_week
COMMENT ON COLUMN application.lk_day_of_week.id IS 'Primary key for the day of week.';
COMMENT ON COLUMN application.lk_day_of_week.code IS 'Code associated with the day of week.';
COMMENT ON COLUMN application.lk_day_of_week.name_ar IS 'Name of the day of week in Arabic.';
COMMENT ON COLUMN application.lk_day_of_week.name_en IS 'Name of the day of week in English.';

-- Table: application.lk_document_type
COMMENT ON COLUMN application.lk_document_type.id IS 'Primary key for the document type.';
COMMENT ON COLUMN application.lk_document_type.created_by_user IS 'Identifier for the user who created the document type.';
COMMENT ON COLUMN application.lk_document_type.created_date IS 'Date and time when the document type was created.';
COMMENT ON COLUMN application.lk_document_type.modified_by_user IS 'Identifier for the user who last modified the document type.';
COMMENT ON COLUMN application.lk_document_type.modified_date IS 'Date and time when the document type was last modified.';
COMMENT ON COLUMN application.lk_document_type.is_deleted IS 'Flag indicating whether this document type is soft deleted or not.';
COMMENT ON COLUMN application.lk_document_type.category IS 'Category of the document type.';
COMMENT ON COLUMN application.lk_document_type.description IS 'Description of the document type.';
COMMENT ON COLUMN application.lk_document_type.doc_order IS 'Order of the document type.';
COMMENT ON COLUMN application.lk_document_type."name" IS 'Name of the document type.';
COMMENT ON COLUMN application.lk_document_type.name_ar IS 'Name of the document type in Arabic.';
COMMENT ON COLUMN application.lk_document_type.code IS 'Code associated with the document type.';

-- Table: application.lk_examination_offices
COMMENT ON COLUMN application.lk_examination_offices.id IS 'Primary key for the examination offices.';
COMMENT ON COLUMN application.lk_examination_offices.code IS 'Code associated with the examination offices.';
COMMENT ON COLUMN application.lk_examination_offices.name_ar IS 'Name of the examination offices in Arabic.';
COMMENT ON COLUMN application.lk_examination_offices.name_en IS 'Name of the examination offices in English.';


-- Table: application.lk_fast_track_examination_target_area
COMMENT ON COLUMN application.lk_fast_track_examination_target_area.id IS 'Primary key for the fast track examination target area.';
COMMENT ON COLUMN application.lk_fast_track_examination_target_area.created_by_user IS 'Identifier for the user who created the fast track examination target area.';
COMMENT ON COLUMN application.lk_fast_track_examination_target_area.created_date IS 'Date and time when the fast track examination target area was created.';
COMMENT ON COLUMN application.lk_fast_track_examination_target_area.modified_by_user IS 'Identifier for the user who last modified the fast track examination target area.';
COMMENT ON COLUMN application.lk_fast_track_examination_target_area.modified_date IS 'Date and time when the fast track examination target area was last modified.';
COMMENT ON COLUMN application.lk_fast_track_examination_target_area.is_deleted IS 'Flag indicating whether this fast track examination target area is soft deleted or not.';
COMMENT ON COLUMN application.lk_fast_track_examination_target_area.description_ar IS 'Description of the fast track examination target area in Arabic.';
COMMENT ON COLUMN application.lk_fast_track_examination_target_area.description_en IS 'Description of the fast track examination target area in English.';
COMMENT ON COLUMN application.lk_fast_track_examination_target_area."show" IS 'Flag indicating whether this fast track examination target area should be shown or hidden.';

-- Table: application.lk_licence_purposes
COMMENT ON COLUMN application.lk_licence_purposes.id IS 'Primary key for the licence purposes.';
COMMENT ON COLUMN application.lk_licence_purposes.code IS 'Code associated with the licence purposes.';
COMMENT ON COLUMN application.lk_licence_purposes.name_ar IS 'Name of the licence purposes in Arabic.';
COMMENT ON COLUMN application.lk_licence_purposes.name_en IS 'Name of the licence purposes in English.';

-- Table: application.lk_licence_types
COMMENT ON COLUMN application.lk_licence_types.id IS 'Primary key for the licence types.';
COMMENT ON COLUMN application.lk_licence_types.code IS 'Code associated with the licence types.';
COMMENT ON COLUMN application.lk_licence_types.name_ar IS 'Name of the licence types in Arabic.';
COMMENT ON COLUMN application.lk_licence_types.name_en IS 'Name of the licence types in English.';

-- Table: application.lk_mark_types
COMMENT ON COLUMN application.lk_mark_types.id IS 'Primary key for the mark types.';
COMMENT ON COLUMN application.lk_mark_types.code IS 'Code associated with the mark types.';
COMMENT ON COLUMN application.lk_mark_types.name_ar IS 'Name of the mark types in Arabic.';
COMMENT ON COLUMN application.lk_mark_types.name_en IS 'Name of the mark types in English.';

-- Table: application.lk_monshaat_enterprise_size
COMMENT ON COLUMN application.lk_monshaat_enterprise_size.id IS 'Primary key for the Monshaat enterprise size.';
COMMENT ON COLUMN application.lk_monshaat_enterprise_size.code IS 'Code associated with the Monshaat enterprise size.';
COMMENT ON COLUMN application.lk_monshaat_enterprise_size.name_ar IS 'Name of the Monshaat enterprise size in Arabic.';
COMMENT ON COLUMN application.lk_monshaat_enterprise_size.name_en IS 'Name of the Monshaat enterprise size in English.';

-- Table: application.lk_nexuo_user
COMMENT ON COLUMN application.lk_nexuo_user.id IS 'Primary key for the Nexuo user.';
COMMENT ON COLUMN application.lk_nexuo_user.created_by_user IS 'Identifier for the user who created the Nexuo user.';
COMMENT ON COLUMN application.lk_nexuo_user.created_date IS 'Date and time when the Nexuo user was created.';
COMMENT ON COLUMN application.lk_nexuo_user.modified_by_user IS 'Identifier for the user who last modified the Nexuo user.';
COMMENT ON COLUMN application.lk_nexuo_user.modified_date IS 'Date and time when the Nexuo user was last modified.';
COMMENT ON COLUMN application.lk_nexuo_user.is_deleted IS 'Flag indicating whether this Nexuo user is soft deleted or not.';
COMMENT ON COLUMN application.lk_nexuo_user."name" IS 'Name of the Nexuo user.';
COMMENT ON COLUMN application.lk_nexuo_user."type" IS 'Type of the Nexuo user.';


-- Table: application.lk_note_category
COMMENT ON COLUMN application.lk_note_category.id IS 'Primary key for the note category.';
COMMENT ON COLUMN application.lk_note_category.code IS 'Code associated with the note category.';
COMMENT ON COLUMN application.lk_note_category.name_ar IS 'Name of the note category in Arabic.';
COMMENT ON COLUMN application.lk_note_category.name_en IS 'Name of the note category in English.';

-- Table: application.lk_notes
COMMENT ON COLUMN application.lk_notes.id IS 'Primary key for the notes.';
COMMENT ON COLUMN application.lk_notes.created_by_user IS 'Identifier for the user who created the notes.';
COMMENT ON COLUMN application.lk_notes.created_date IS 'Date and time when the notes were created.';
COMMENT ON COLUMN application.lk_notes.modified_by_user IS 'Identifier for the user who last modified the notes.';
COMMENT ON COLUMN application.lk_notes.modified_date IS 'Date and time when the notes were last modified.';
COMMENT ON COLUMN application.lk_notes.is_deleted IS 'Flag indicating whether these notes are soft deleted or not.';
COMMENT ON COLUMN application.lk_notes.code IS 'Code associated with the notes.';
COMMENT ON COLUMN application.lk_notes.description_ar IS 'Description of the notes in Arabic.';
COMMENT ON COLUMN application.lk_notes.description_en IS 'Description of the notes in English.';
COMMENT ON COLUMN application.lk_notes.enabled IS 'Flag indicating whether these notes are enabled or not.';
COMMENT ON COLUMN application.lk_notes.name_ar IS 'Name of the notes in Arabic.';
COMMENT ON COLUMN application.lk_notes.name_en IS 'Name of the notes in English.';
COMMENT ON COLUMN application.lk_notes.category_id IS 'Foreign key referencing the category associated with these notes.';
COMMENT ON COLUMN application.lk_notes.section_id IS 'Identifier for the section associated with these notes.';
COMMENT ON COLUMN application.lk_notes.step_id IS 'Identifier for the step associated with these notes.';
COMMENT ON COLUMN application.lk_notes.attribute_id IS 'Identifier for the attribute associated with these notes.';
COMMENT ON COLUMN application.lk_notes.notes_type_enum IS 'Type of the notes enumeration.';
COMMENT ON COLUMN application.lk_notes.note_category_id IS 'Identifier for the note category associated with these notes.';
COMMENT ON COLUMN application.lk_notes.notes_step IS 'Step of the notes.';

-- Table: application.lk_post_request_reasons
COMMENT ON COLUMN application.lk_post_request_reasons.id IS 'Primary key for the post request reasons.';
COMMENT ON COLUMN application.lk_post_request_reasons.code IS 'Code associated with the post request reasons.';
COMMENT ON COLUMN application.lk_post_request_reasons.name_ar IS 'Name of the post request reasons in Arabic.';
COMMENT ON COLUMN application.lk_post_request_reasons.name_en IS 'Name of the post request reasons in English.';

-- Table: application.lk_publication_issue_status
COMMENT ON COLUMN application.lk_publication_issue_status.id IS 'Primary key for the publication issue status.';
COMMENT ON COLUMN application.lk_publication_issue_status.name_ar IS 'Name of the publication issue status in Arabic.';
COMMENT ON COLUMN application.lk_publication_issue_status.name_en IS 'Name of the publication issue status in English.';
COMMENT ON COLUMN application.lk_publication_issue_status.code IS 'Code associated with the publication issue status.';
COMMENT ON COLUMN application.lk_publication_issue_status.created_date IS 'Date and time when the publication issue status was created.';
COMMENT ON COLUMN application.lk_publication_issue_status.is_deleted IS 'Flag indicating whether this publication issue status is soft deleted or not.';

-- Table: application.lk_publication_type
COMMENT ON COLUMN application.lk_publication_type.id IS 'Primary key for the publication type.';
COMMENT ON COLUMN application.lk_publication_type.code IS 'Code associated with the publication type.';
COMMENT ON COLUMN application.lk_publication_type.name_ar IS 'Name of the publication type in Arabic.';
COMMENT ON COLUMN application.lk_publication_type.name_en IS 'Name of the publication type in English.';
COMMENT ON COLUMN application.lk_publication_type.application_category_id IS 'Foreign key referencing the application category associated with this publication type.';

-- Table: application.lk_regions
COMMENT ON COLUMN application.lk_regions.id IS 'Primary key for the regions.';
COMMENT ON COLUMN application.lk_regions.code IS 'Code associated with the regions.';
COMMENT ON COLUMN application.lk_regions.name_ar IS 'Name of the regions in Arabic.';
COMMENT ON COLUMN application.lk_regions.name_en IS 'Name of the regions in English.';


-- Table: application.lk_result_document_types
COMMENT ON COLUMN application.lk_result_document_types.id IS 'Primary key for the result document type.';
COMMENT ON COLUMN application.lk_result_document_types.code IS 'Code associated with the result document type.';
COMMENT ON COLUMN application.lk_result_document_types.name_ar IS 'Name of the result document type in Arabic.';
COMMENT ON COLUMN application.lk_result_document_types.name_en IS 'Name of the result document type in English.';

-- Table: application.lk_sections
COMMENT ON COLUMN application.lk_sections.id IS 'Primary key for the sections.';
COMMENT ON COLUMN application.lk_sections.code IS 'Code associated with the sections.';
COMMENT ON COLUMN application.lk_sections.name_ar IS 'Name of the sections in Arabic.';
COMMENT ON COLUMN application.lk_sections.name_en IS 'Name of the sections in English.';

-- Table: application.lk_shapes
COMMENT ON COLUMN application.lk_shapes.id IS 'Primary key for the shapes.';
COMMENT ON COLUMN application.lk_shapes.code IS 'Code associated with the shapes.';
COMMENT ON COLUMN application.lk_shapes.name_ar IS 'Name of the shapes in Arabic.';
COMMENT ON COLUMN application.lk_shapes.name_en IS 'Name of the shapes in English.';

-- Table: application.lk_steps
COMMENT ON COLUMN application.lk_steps.id IS 'Primary key for the steps.';
COMMENT ON COLUMN application.lk_steps.code IS 'Code associated with the steps.';
COMMENT ON COLUMN application.lk_steps.name_ar IS 'Name of the steps in Arabic.';
COMMENT ON COLUMN application.lk_steps.name_en IS 'Name of the steps in English.';

-- Table: application.lk_support_service_request_status
COMMENT ON COLUMN application.lk_support_service_request_status.id IS 'Primary key for the support service request status.';
COMMENT ON COLUMN application.lk_support_service_request_status.code IS 'Code associated with the support service request status.';
COMMENT ON COLUMN application.lk_support_service_request_status.name_ar IS 'Name of the support service request status in Arabic.';
COMMENT ON COLUMN application.lk_support_service_request_status.name_en IS 'Name of the support service request status in English.';
COMMENT ON COLUMN application.lk_support_service_request_status.name_ar_external IS 'External name of the support service request status in Arabic.';
COMMENT ON COLUMN application.lk_support_service_request_status.name_en_external IS 'External name of the support service request status in English.';

-- Table: application.lk_support_service_type
COMMENT ON COLUMN application.lk_support_service_type.id IS 'Primary key for the support service type.';
COMMENT ON COLUMN application.lk_support_service_type.created_by_user IS 'Identifier for the user who created the support service type.';
COMMENT ON COLUMN application.lk_support_service_type.created_date IS 'Date and time when the support service type was created.';
COMMENT ON COLUMN application.lk_support_service_type.modified_by_user IS 'Identifier for the user who last modified the support service type.';
COMMENT ON COLUMN application.lk_support_service_type.modified_date IS 'Date and time when the support service type was last modified.';
COMMENT ON COLUMN application.lk_support_service_type.is_deleted IS 'Flag indicating whether this support service type is soft deleted or not.';
COMMENT ON COLUMN application.lk_support_service_type.desc_ar IS 'Description of the support service type in Arabic.';
COMMENT ON COLUMN application.lk_support_service_type.desc_en IS 'Description of the support service type in English.';
COMMENT ON COLUMN application.lk_support_service_type.type IS 'Type of the support service type.';
COMMENT ON COLUMN application.lk_support_service_type.code IS 'Code associated with the support service type.';
COMMENT ON COLUMN application.lk_support_service_type.is_free IS 'Flag indicating whether this support service type is free or not.';


-- Table: application.lk_support_services
COMMENT ON COLUMN application.lk_support_services.id IS 'Primary key for the support service.';
COMMENT ON COLUMN application.lk_support_services.created_by_user IS 'Identifier for the user who created the support service.';
COMMENT ON COLUMN application.lk_support_services.created_date IS 'Date and time when the support service was created.';
COMMENT ON COLUMN application.lk_support_services.modified_by_user IS 'Identifier for the user who last modified the support service.';
COMMENT ON COLUMN application.lk_support_services.modified_date IS 'Date and time when the support service was last modified.';
COMMENT ON COLUMN application.lk_support_services.is_deleted IS 'Flag indicating whether this support service is soft deleted or not.';
COMMENT ON COLUMN application.lk_support_services.cost IS 'Cost associated with the support service.';
COMMENT ON COLUMN application.lk_support_services.desc_ar IS 'Description of the support service in Arabic.';
COMMENT ON COLUMN application.lk_support_services.desc_en IS 'Description of the support service in English.';
COMMENT ON COLUMN application.lk_support_services.name_ar IS 'Name of the support service in Arabic.';
COMMENT ON COLUMN application.lk_support_services.name_en IS 'Name of the support service in English.';
COMMENT ON COLUMN application.lk_support_services.code IS 'Code associated with the support service.';

-- Table: application.lk_tag_languages
COMMENT ON COLUMN application.lk_tag_languages.id IS 'Primary key for the tag language.';
COMMENT ON COLUMN application.lk_tag_languages.code IS 'Code associated with the tag language.';
COMMENT ON COLUMN application.lk_tag_languages.name_ar IS 'Name of the tag language in Arabic.';
COMMENT ON COLUMN application.lk_tag_languages.name_en IS 'Name of the tag language in English.';

-- Table: application.lk_tag_type_desc
COMMENT ON COLUMN application.lk_tag_type_desc.id IS 'Primary key for the tag type description.';
COMMENT ON COLUMN application.lk_tag_type_desc.code IS 'Code associated with the tag type description.';
COMMENT ON COLUMN application.lk_tag_type_desc.name_ar IS 'Name of the tag type description in Arabic.';
COMMENT ON COLUMN application.lk_tag_type_desc.name_en IS 'Name of the tag type description in English.';

-- Table: application.lk_task_eqm_status
COMMENT ON COLUMN application.lk_task_eqm_status.id IS 'Primary key for the task EQM status.';
COMMENT ON COLUMN application.lk_task_eqm_status.name_ar IS 'Name of the task EQM status in Arabic.';
COMMENT ON COLUMN application.lk_task_eqm_status.name_en IS 'Name of the task EQM status in English.';
COMMENT ON COLUMN application.lk_task_eqm_status.code IS 'Code associated with the task EQM status.';
COMMENT ON COLUMN application.lk_task_eqm_status.created_date IS 'Date and time when the task EQM status was created.';
COMMENT ON COLUMN application.lk_task_eqm_status.is_deleted IS 'Flag indicating whether this task EQM status is soft deleted or not.';

-- Table: application.lk_task_eqm_types
COMMENT ON COLUMN application.lk_task_eqm_types.id IS 'Primary key for the task EQM type.';
COMMENT ON COLUMN application.lk_task_eqm_types.code IS 'Code associated with the task EQM type.';
COMMENT ON COLUMN application.lk_task_eqm_types.name_ar IS 'Name of the task EQM type in Arabic.';
COMMENT ON COLUMN application.lk_task_eqm_types.name_en IS 'Name of the task EQM type in English.';


-- Table: application.lk_tm_agency_request_status
COMMENT ON COLUMN application.lk_tm_agency_request_status.id IS 'Primary key for the TM agency request status.';
COMMENT ON COLUMN application.lk_tm_agency_request_status.code IS 'Code associated with the TM agency request status.';
COMMENT ON COLUMN application.lk_tm_agency_request_status.name_ar IS 'Name of the TM agency request status in Arabic.';
COMMENT ON COLUMN application.lk_tm_agency_request_status.name_en IS 'Name of the TM agency request status in English.';
COMMENT ON COLUMN application.lk_tm_agency_request_status.name_ar_external IS 'External name of the TM agency request status in Arabic.';
COMMENT ON COLUMN application.lk_tm_agency_request_status.name_en_external IS 'External name of the TM agency request status in English.';

-- Table: application.lk_veena_assistant_department
COMMENT ON COLUMN application.lk_veena_assistant_department.id IS 'Primary key for the Veena assistant department.';
COMMENT ON COLUMN application.lk_veena_assistant_department.code IS 'Code associated with the Veena assistant department.';
COMMENT ON COLUMN application.lk_veena_assistant_department.name_ar IS 'Name of the Veena assistant department in Arabic.';
COMMENT ON COLUMN application.lk_veena_assistant_department.name_en IS 'Name of the Veena assistant department in English.';
COMMENT ON COLUMN application.lk_veena_assistant_department.veena_department_id IS 'Identifier for the Veena department associated with this assistant department.';

-- Table: application.lk_veena_classification
COMMENT ON COLUMN application.lk_veena_classification.id IS 'Primary key for the Veena classification.';
COMMENT ON COLUMN application.lk_veena_classification.code IS 'Code associated with the Veena classification.';
COMMENT ON COLUMN application.lk_veena_classification.name_ar IS 'Name of the Veena classification in Arabic.';
COMMENT ON COLUMN application.lk_veena_classification.name_en IS 'Name of the Veena classification in English.';

-- Table: application.lk_veena_department
COMMENT ON COLUMN application.lk_veena_department.id IS 'Primary key for the Veena department.';
COMMENT ON COLUMN application.lk_veena_department.code IS 'Code associated with the Veena department.';
COMMENT ON COLUMN application.lk_veena_department.name_ar IS 'Name of the Veena department in Arabic.';
COMMENT ON COLUMN application.lk_veena_department.name_en IS 'Name of the Veena department in English.';
COMMENT ON COLUMN application.lk_veena_department.veena_classification_id IS 'Identifier for the Veena classification associated with this department.';

-- Table: application.applications_info
COMMENT ON COLUMN application.applications_info.id IS 'Primary key for the application.';
COMMENT ON COLUMN application.applications_info.created_by_user IS 'Identifier for the user who created the application.';
COMMENT ON COLUMN application.applications_info.created_date IS 'Date and time when the application was created.';
COMMENT ON COLUMN application.applications_info.modified_by_user IS 'Identifier for the user who last modified the application.';
COMMENT ON COLUMN application.applications_info.modified_date IS 'Date and time when the application was last modified.';
COMMENT ON COLUMN application.applications_info.is_deleted IS 'Flag indicating whether this application is soft deleted or not.';
COMMENT ON COLUMN application.applications_info.serial IS 'DB serial Identifier';
COMMENT ON COLUMN application.applications_info.accelerated IS 'Flag indicating whether this is an accelerated application.';
COMMENT ON COLUMN application.applications_info.address IS 'Address associated with the application.';
COMMENT ON COLUMN application.applications_info.application_number IS 'Application number.';
COMMENT ON COLUMN application.applications_info.created_by_user_id IS 'Foreign key referencing the user who created the application (references administration.users(id)).';
COMMENT ON COLUMN application.applications_info.email IS 'Email associated with the application.';
COMMENT ON COLUMN application.applications_info.ipc_number IS 'IPC (International Patent Classification) number.';
COMMENT ON COLUMN application.applications_info.mobile_code IS 'Country code for the mobile number associated with the application.';
COMMENT ON COLUMN application.applications_info.mobile_number IS 'Mobile number associated with the application.';
COMMENT ON COLUMN application.applications_info.national_security IS 'National security indicator.';
COMMENT ON COLUMN application.applications_info.partial_application IS 'Flag indicating whether this is a partial application.';
COMMENT ON COLUMN application.applications_info.partial_application_number IS 'Partial application number.';
COMMENT ON COLUMN application.applications_info.substantive_examination IS 'Flag indicating whether substantive examination is required.';
COMMENT ON COLUMN application.applications_info.title_ar IS 'Title of the application in Arabic.';
COMMENT ON COLUMN application.applications_info.title_en IS 'Title of the application in English.';
COMMENT ON COLUMN application.applications_info.application_status_id IS 'Foreign key referencing the status of the application (references application.lk_application_status(id)).';
COMMENT ON COLUMN application.applications_info.lk_category_id IS 'Foreign key referencing the category of the application (references application.lk_application_category(id)).';
COMMENT ON COLUMN application.applications_info.filing_date IS 'Date when the application was filed.';
COMMENT ON COLUMN application.applications_info.by_himself IS 'Flag indicating whether the application was submitted by the applicant directly.';
COMMENT ON COLUMN application.applications_info.pages_number IS 'Total number of pages in the application.';
COMMENT ON COLUMN application.applications_info.claim_pages_number IS 'Number of pages containing claims in the application.';
COMMENT ON COLUMN application.applications_info.shapes_number IS 'Number of shapes in the application.';
COMMENT ON COLUMN application.applications_info.total_checking_fee IS 'Total checking fee for the application.';
COMMENT ON COLUMN application.applications_info.classification_notes IS 'Notes related to classification of the application.';
COMMENT ON COLUMN application.applications_info.classification_unit_id IS 'Foreign key referencing the classification unit associated with the application (references application.lk_classification_units(id)).';
COMMENT ON COLUMN application.applications_info.full_summery_approved IS 'Flag indicating whether the full summary of the application is approved.';
COMMENT ON COLUMN application.applications_info.full_summery_notes IS 'Notes related to the full summary of the application.';
COMMENT ON COLUMN application.applications_info.geda_approved IS 'Flag indicating whether the application is approved by GEDA (General Engineering Development Authority).';
COMMENT ON COLUMN application.applications_info.geda_notes IS 'Notes related to the GEDA approval of the application.';
COMMENT ON COLUMN application.applications_info.illustrations_approved IS 'Flag indicating whether the illustrations in the application are approved.';
COMMENT ON COLUMN application.applications_info.illustrations_notes IS 'Notes related to the illustrations in the application.';
COMMENT ON COLUMN application.applications_info.industrial_applicable_approved IS 'Flag indicating whether the industrial applicability of the application is approved.';
COMMENT ON COLUMN application.applications_info.industrial_applicable_notes IS 'Notes related to the industrial applicability of the application.';
COMMENT ON COLUMN application.applications_info.innovative_step_approved IS 'Flag indicating whether the innovative step of the application is approved.';
COMMENT ON COLUMN application.applications_info.innovative_step_notes IS 'Notes related to the innovative step of the application.';
COMMENT ON COLUMN application.applications_info.name_approved IS 'Flag indicating whether the name of the application is approved.';
COMMENT ON COLUMN application.applications_info.name_notes IS 'Notes related to the name of the application.';
COMMENT ON COLUMN application.applications_info.problem IS 'Problem addressed by the application.';
COMMENT ON COLUMN application.applications_info.problem_solution IS 'Solution proposed by the application for the problem.';
COMMENT ON COLUMN application.applications_info.protection_elements_approved IS 'Flag indicating whether the protection elements of the application are approved.';
COMMENT ON COLUMN application.applications_info.protection_elements_notes IS 'Notes related to the protection elements of the application.';
COMMENT ON COLUMN application.applications_info.summery_approved IS 'Flag indicating whether the summary of the application is approved.';
COMMENT ON COLUMN application.applications_info.summery_notes IS 'Notes related to the summary of the application.';
COMMENT ON COLUMN application.applications_info.publication_date IS 'Date of publication of the application.';
COMMENT ON COLUMN application.applications_info.filing_date_hijri IS 'Hijri (Islamic calendar) date when the application was filed.';
COMMENT ON COLUMN application.applications_info.grant_date IS 'Date when the application was granted.';
COMMENT ON COLUMN application.applications_info.grant_date_hijri IS 'Hijri (Islamic calendar) date when the application was granted.';
COMMENT ON COLUMN application.applications_info.enterprise_size_id IS 'Foreign key referencing the enterprise size of the applicant (references application.lk_monshaat_enterprise_size(id)).';
COMMENT ON COLUMN application.applications_info.end_of_protection_date IS 'Date when the protection of the application ends.';
COMMENT ON COLUMN application.applications_info.id_old IS 'Old identifier for the application (legacy field).';
COMMENT ON COLUMN application.applications_info.owner_name_ar IS 'Owner name in Arabic.';
COMMENT ON COLUMN application.applications_info.owner_name_en IS 'Owner name in English.';
COMMENT ON COLUMN application.applications_info.owner_address_ar IS 'Owner address in Arabic.';
COMMENT ON COLUMN application.applications_info.owner_address_en IS 'Owner address in English.';
COMMENT ON COLUMN application.applications_info.is_plt IS 'Flag indicating whether the application is a Patent of Addition (PLT).';
COMMENT ON COLUMN application.applications_info.plt_description IS 'Description of the Patent of Addition (PLT).';
COMMENT ON COLUMN application.applications_info.plt_document IS 'Document related to the Patent of Addition (PLT).';
COMMENT ON COLUMN application.applications_info.is_priority_confirmed IS 'Flag indicating whether the priority claim is confirmed.';
COMMENT ON COLUMN application.applications_info.plt_filing_date IS 'Filing date of the Patent of Addition (PLT).';
COMMENT ON COLUMN application.applications_info.grant_number IS 'Grant number of the application.';
COMMENT ON COLUMN application.applications_info.parent_elements_count IS 'Number of parent elements associated with the application.';
COMMENT ON COLUMN application.applications_info.children_elements_count IS 'Number of children elements associated with the application.';
COMMENT ON COLUMN application.applications_info.application_relevent_type_count IS 'Count of application relevant types.';
COMMENT ON COLUMN application.applications_info.total_pages_number IS 'Total number of pages in the application.';
COMMENT ON COLUMN application.applications_info.process_request_id IS 'Process request identifier associated with the application.';
COMMENT ON COLUMN application.applications_info.application_request_number IS 'Application request number.';
COMMENT ON COLUMN application.applications_info.created_by_customer_id IS 'Identifier of the customer who created the application.';
COMMENT ON COLUMN application.applications_info.created_by_customer_type IS 'Type of the customer who created the application, (e.g, AGENT, MAIN_OWNER, SECONDARY_OWNER, PREVIOUS_MAIN_OWNER, APPLICANT, INVENTOR, RELEVANT, LICENSED_CUSTOMER, LICENSED_CUSTOMER_AGENT, OTHER)';
COMMENT ON COLUMN application.applications_info.migration_stage IS 'Migration stage of the application.';

-- Table: application.agent_substitution_request
COMMENT ON COLUMN application.agent_substitution_request.id IS 'Primary key for the agent substitution request.';
COMMENT ON COLUMN application.agent_substitution_request.delegation_document_id IS 'Identifier of the delegation document associated with the request.';
COMMENT ON COLUMN application.agent_substitution_request.eviction_document_id IS 'Identifier of the eviction document associated with the request.';
COMMENT ON COLUMN application.agent_substitution_request.lk_support_service_type_id IS 'Foreign key referencing the support service type associated with the request (references application.lk_support_service_type(id)).';
COMMENT ON COLUMN application.agent_substitution_request.customer_id IS 'Identifier of the customer associated with the request.';

-- Table: application.annual_fees_request
COMMENT ON COLUMN application.annual_fees_request.id IS 'Primary key for the annual fees request.';
COMMENT ON COLUMN application.annual_fees_request.service_type IS 'Type of service associated with the request, (e.g, PAY_ANNUAL_FEES, POST_ANNUAL_FEES)';
COMMENT ON COLUMN application.annual_fees_request.annual_year_id IS 'Foreign key referencing the annual year associated with the request (references application.lk_annual_request_years(id)).';
COMMENT ON COLUMN application.annual_fees_request.post_request_id IS 'Identifier of the post request associated with the request.';
COMMENT ON COLUMN application.annual_fees_request.cost_codes IS 'Cost codes associated with the request.';
COMMENT ON COLUMN application.annual_fees_request.migration_stage IS 'Migration stage of the annual fees request.';

-- Table: application.appeal_committee_opinion
COMMENT ON COLUMN application.appeal_committee_opinion.id IS 'Primary key for the appeal committee opinion.';
COMMENT ON COLUMN application.appeal_committee_opinion.created_by_user IS 'Identifier for the user who created the appeal committee opinion.';
COMMENT ON COLUMN application.appeal_committee_opinion.created_date IS 'Date and time when the appeal committee opinion was created.';
COMMENT ON COLUMN application.appeal_committee_opinion.modified_by_user IS 'Identifier for the user who last modified the appeal committee opinion.';
COMMENT ON COLUMN application.appeal_committee_opinion.modified_date IS 'Date and time when the appeal committee opinion was last modified.';
COMMENT ON COLUMN application.appeal_committee_opinion.is_deleted IS 'Flag indicating whether the appeal committee opinion is soft deleted or not.';
COMMENT ON COLUMN application.appeal_committee_opinion.appeal_committee_opinion IS 'Opinion of the appeal committee.';
COMMENT ON COLUMN application.appeal_committee_opinion.appeal_request_id IS 'Foreign key referencing the appeal request associated with the opinion (references application.appeal_request(id)).';
COMMENT ON COLUMN application.appeal_committee_opinion.document_id IS 'Identifier of the document associated with the appeal committee opinion.';
COMMENT ON COLUMN application.appeal_committee_opinion.migration_stage IS 'Migration stage of the appeal committee opinion.';

-- Table: application.appeal_request
COMMENT ON COLUMN application.appeal_request.id IS 'Primary key for the appeal request.';
COMMENT ON COLUMN application.appeal_request.appeal_committee_decision IS 'Decision of the appeal committee, (e.g, ACCEPTED, REJECTED)';
COMMENT ON COLUMN application.appeal_request.appeal_committee_decision_comment IS 'Comment on the decision of the appeal committee.';
COMMENT ON COLUMN application.appeal_request.appeal_reason IS 'Reason for the appeal.';
COMMENT ON COLUMN application.appeal_request.checker_decision IS 'Decision of the checker, (e.g, ACCEPTED, REJECTED, SEND_BACK)';
COMMENT ON COLUMN application.appeal_request.checker_final_notes IS 'Final notes of the checker.';
COMMENT ON COLUMN application.appeal_request.official_letter_comment IS 'Comment on the official letter.';

-- Table: application.appeal_request_documents
COMMENT ON COLUMN application.appeal_request_documents.appeal_request_id IS 'Foreign key referencing the appeal request associated with the document (references application.appeal_request(id)).';
COMMENT ON COLUMN application.appeal_request_documents.document_id IS 'Identifier of the document associated with the appeal request.';

-- Table: application.application_accelerated
COMMENT ON COLUMN application.application_accelerated.id IS 'Primary key for the accelerated application.';
COMMENT ON COLUMN application.application_accelerated.created_by_user IS 'Identifier for the user who created the accelerated application.';
COMMENT ON COLUMN application.application_accelerated.created_date IS 'Date and time when the accelerated application was created.';
COMMENT ON COLUMN application.application_accelerated.modified_by_user IS 'Identifier for the user who last modified the accelerated application.';
COMMENT ON COLUMN application.application_accelerated.modified_date IS 'Date and time when the accelerated application was last modified.';
COMMENT ON COLUMN application.application_accelerated.is_deleted IS 'Flag indicating whether the accelerated application is soft deleted or not.';
COMMENT ON COLUMN application.application_accelerated.accelerated_examination IS 'Flag indicating whether the application is for accelerated examination.';
COMMENT ON COLUMN application.application_accelerated.fast_track_examination IS 'Flag indicating whether the application is for fast track examination.';
COMMENT ON COLUMN application.application_accelerated.pph_examination IS 'Flag indicating whether the application is for PPH (Patent Prosecution Highway) examination.';
COMMENT ON COLUMN application.application_accelerated.application_info_id IS 'Foreign key referencing the associated application information (references application.applications_info(id)).';
COMMENT ON COLUMN application.application_accelerated.closest_prior_art_documents_related_to_cited_references_file_id IS 'Identifier of the file containing the closest prior art documents related to cited references.';
COMMENT ON COLUMN application.application_accelerated.comparative_id IS 'Identifier of the comparative.';
COMMENT ON COLUMN application.application_accelerated.fast_track_examination_target_area_id IS 'Identifier of the fast track examination target area.';
COMMENT ON COLUMN application.application_accelerated.latest_patentable_claims_file_id IS 'Identifier of the file containing the latest patentable claims.';
COMMENT ON COLUMN application.application_accelerated.refused IS 'Flag indicating whether the accelerated application is refused.';
COMMENT ON COLUMN application.application_accelerated.decision IS 'Decision associated with the accelerated application.';
COMMENT ON COLUMN application.application_accelerated.migration_stage IS 'Migration stage of the accelerated application.';

-- Table: application.application_agent_documents
COMMENT ON COLUMN application.application_agent_documents.application_agent_id IS 'Identifier of the application agent associated with the document.';
COMMENT ON COLUMN application.application_agent_documents.document_id IS 'Identifier of the document associated with the application agent.';

-- Table: application.application_agents
COMMENT ON COLUMN application.application_agents.id IS 'Primary key for the application agents.';
COMMENT ON COLUMN application.application_agents.created_by_user IS 'Identifier for the user who created the application agent.';
COMMENT ON COLUMN application.application_agents.created_date IS 'Date and time when the application agent was created.';
COMMENT ON COLUMN application.application_agents.modified_by_user IS 'Identifier for the user who last modified the application agent.';
COMMENT ON COLUMN application.application_agents.modified_date IS 'Date and time when the application agent was last modified.';
COMMENT ON COLUMN application.application_agents.is_deleted IS 'Flag indicating whether the application agent is soft deleted or not.';
COMMENT ON COLUMN application.application_agents.customer_id IS 'Identifier of the customer associated with the application agent.';
COMMENT ON COLUMN application.application_agents.expiration_date IS 'Expiration date of the application agent.';
COMMENT ON COLUMN application.application_agents.status IS 'Status of the application agent, (e.g, ACTIVE , CHANGED, DELETED)';
COMMENT ON COLUMN application.application_agents.application_id IS 'Identifier of the application associated with the agent.';
COMMENT ON COLUMN application.application_agents.migration_stage IS 'Migration stage of the application agent.';

-- Table: application.application_certificate_documents
COMMENT ON COLUMN application.application_certificate_documents.id IS 'Primary key for the application certificate documents.';
COMMENT ON COLUMN application.application_certificate_documents.created_by_user IS 'Identifier for the user who created the application certificate document.';
COMMENT ON COLUMN application.application_certificate_documents.created_date IS 'Date and time when the application certificate document was created.';
COMMENT ON COLUMN application.application_certificate_documents.modified_by_user IS 'Identifier for the user who last modified the application certificate document.';
COMMENT ON COLUMN application.application_certificate_documents.modified_date IS 'Date and time when the application certificate document was last modified.';
COMMENT ON COLUMN application.application_certificate_documents.is_deleted IS 'Flag indicating whether the application certificate document is soft deleted or not.';
COMMENT ON COLUMN application.application_certificate_documents.version IS 'Version of the application certificate document.';
COMMENT ON COLUMN application.application_certificate_documents.application_id IS 'Identifier of the application associated with the certificate document.';
COMMENT ON COLUMN application.application_certificate_documents.document_id IS 'Identifier of the document associated with the certificate document.';
COMMENT ON COLUMN application.application_certificate_documents.failure_reason IS 'Reason for failure associated with the certificate document.';
COMMENT ON COLUMN application.application_certificate_documents.generation_status IS 'Generation status of the certificate document, (e.g, FAILED, SUCCEED)';
COMMENT ON COLUMN application.application_certificate_documents.migration_stage IS 'Migration stage of the application certificate document.';

-- Table: application.application_checking_reports
COMMENT ON COLUMN application.application_checking_reports.id IS 'Primary key for the application checking reports.';
COMMENT ON COLUMN application.application_checking_reports.created_by_user IS 'Identifier for the user who created the application checking report.';
COMMENT ON COLUMN application.application_checking_reports.created_date IS 'Date and time when the application checking report was created.';
COMMENT ON COLUMN application.application_checking_reports.modified_by_user IS 'Identifier for the user who last modified the application checking report.';
COMMENT ON COLUMN application.application_checking_reports.modified_date IS 'Date and time when the application checking report was last modified.';
COMMENT ON COLUMN application.application_checking_reports.is_deleted IS 'Flag indicating whether the application checking report is soft deleted or not.';
COMMENT ON COLUMN application.application_checking_reports.document_id IS 'Identifier of the document associated with the checking report.';
COMMENT ON COLUMN application.application_checking_reports.report_type IS 'Type of the checking report, (e.g, CHECKER, EXAMINER, ObjectionRequestReport, DroppedRequestReport, NoticeOfFormalCheckerReport)';
COMMENT ON COLUMN application.application_checking_reports.application_id IS 'Identifier of the application associated with the checking report.';
COMMENT ON COLUMN application.application_checking_reports.service_id IS 'Identifier of the service associated with the checking report.';
COMMENT ON COLUMN application.application_checking_reports.migration_stage IS 'Migration stage of the application checking report.';

-- Table: application.application_customers
COMMENT ON COLUMN application.application_customers.id IS 'Primary key for the application customers.';
COMMENT ON COLUMN application.application_customers.created_by_user IS 'Identifier for the user who created the application customer.';
COMMENT ON COLUMN application.application_customers.created_date IS 'Date and time when the application customer was created.';
COMMENT ON COLUMN application.application_customers.modified_by_user IS 'Identifier for the user who last modified the application customer.';
COMMENT ON COLUMN application.application_customers.modified_date IS 'Date and time when the application customer was last modified.';
COMMENT ON COLUMN application.application_customers.is_deleted IS 'Flag indicating whether the application customer is soft deleted or not.';
COMMENT ON COLUMN application.application_customers.customer_code IS 'Code of the application customer.';
COMMENT ON COLUMN application.application_customers.customer_id IS 'Identifier of the customer associated with the application.';
COMMENT ON COLUMN application.application_customers.application_customer_type IS 'Type of the application customer, (e.g, AGENTو MAIN_OWNER, SECONDARY_OWNER, PREVIOUS_MAIN_OWNER, PREVIOUS_SECONDARY_OWNER)';
COMMENT ON COLUMN application.application_customers.application_id IS 'Identifier of the application associated with the customer.';
COMMENT ON COLUMN application.application_customers.customer_access_level IS 'Access level of the customer, (e.g, VIEW_ONLY, FULL_ACCESS)';
COMMENT ON COLUMN application.application_customers.agency_request_id IS 'Identifier of the agency request associated with the customer.';
COMMENT ON COLUMN application.application_customers.migration_stage IS 'Migration stage of the application customer.';

-- Table: application.application_databases
COMMENT ON COLUMN application.application_databases.id IS 'Primary key for the application databases.';
COMMENT ON COLUMN application.application_databases.created_by_user IS 'Identifier for the user who created the application database.';
COMMENT ON COLUMN application.application_databases.created_date IS 'Date and time when the application database was created.';
COMMENT ON COLUMN application.application_databases.modified_by_user IS 'Identifier for the user who last modified the application database.';
COMMENT ON COLUMN application.application_databases.modified_date IS 'Date and time when the application database was last modified.';
COMMENT ON COLUMN application.application_databases.is_deleted IS 'Flag indicating whether the application database is soft deleted or not.';
COMMENT ON COLUMN application.application_databases.other IS 'Flag indicating whether the application database is an other type.';
COMMENT ON COLUMN application.application_databases.other_database_name IS 'Name of the other database associated with the application.';
COMMENT ON COLUMN application.application_databases.application_id IS 'Identifier of the application associated with the database.';
COMMENT ON COLUMN application.application_databases.database_id IS 'Identifier of the database associated with the application.';

-- Table: application.application_document_comments
COMMENT ON COLUMN application.application_document_comments.id IS 'Primary key for the application document comments.';
COMMENT ON COLUMN application.application_document_comments.created_by_user IS 'Identifier for the user who created the application document comment.';
COMMENT ON COLUMN application.application_document_comments.created_date IS 'Date and time when the application document comment was created.';
COMMENT ON COLUMN application.application_document_comments.modified_by_user IS 'Identifier for the user who last modified the application document comment.';
COMMENT ON COLUMN application.application_document_comments.modified_date IS 'Date and time when the application document comment was last modified.';
COMMENT ON COLUMN application.application_document_comments.is_deleted IS 'Flag indicating whether the application document comment is soft deleted or not.';
COMMENT ON COLUMN application.application_document_comments.page_number IS 'Page number of the comment.';
COMMENT ON COLUMN application.application_document_comments.paragraph_number IS 'Paragraph number of the comment.';
COMMENT ON COLUMN application.application_document_comments.comment_document_id IS 'Identifier of the document associated with the comment.';
COMMENT ON COLUMN application.application_document_comments.document_id IS 'Identifier of the document to which the comment belongs.';
COMMENT ON COLUMN application.application_document_comments.comment IS 'Content of the comment.';

-- Table: application.application_drawing
COMMENT ON COLUMN application.application_drawing.id IS 'Primary key for the application drawing.';
COMMENT ON COLUMN application.application_drawing.created_by_user IS 'Identifier for the user who created the application drawing.';
COMMENT ON COLUMN application.application_drawing.created_date IS 'Date and time when the application drawing was created.';
COMMENT ON COLUMN application.application_drawing.modified_by_user IS 'Identifier for the user who last modified the application drawing.';
COMMENT ON COLUMN application.application_drawing.modified_date IS 'Date and time when the application drawing was last modified.';
COMMENT ON COLUMN application.application_drawing.is_deleted IS 'Flag indicating whether the application drawing is soft deleted or not.';
COMMENT ON COLUMN application.application_drawing.is_default IS 'Flag indicating whether the drawing is the default drawing for the application.';
COMMENT ON COLUMN application.application_drawing.title IS 'Title of the application drawing.';
COMMENT ON COLUMN application.application_drawing.application_id IS 'Identifier of the application associated with the drawing.';
COMMENT ON COLUMN application.application_drawing.document_id IS 'Identifier of the document associated with the drawing.';
COMMENT ON COLUMN application.application_drawing.migration_stage IS 'Migration stage of the application drawing.';

-- Table: application.application_edit_name_address_request
COMMENT ON COLUMN application.application_edit_name_address_request.id IS 'Primary key for the application edit name address request.';
COMMENT ON COLUMN application.application_edit_name_address_request.edit_type IS 'Type of the edit (name/address), (e.g, NAME, ADDRESS, NAME_AND_ADDRESS)';
COMMENT ON COLUMN application.application_edit_name_address_request.old_owner_name_ar IS 'Old owner name in Arabic.';
COMMENT ON COLUMN application.application_edit_name_address_request.new_owner_name_ar IS 'New owner name in Arabic.';
COMMENT ON COLUMN application.application_edit_name_address_request.old_owner_name_en IS 'Old owner name in English.';
COMMENT ON COLUMN application.application_edit_name_address_request.new_owner_name_en IS 'New owner name in English.';
COMMENT ON COLUMN application.application_edit_name_address_request.old_owner_address_ar IS 'Old owner address in Arabic.';
COMMENT ON COLUMN application.application_edit_name_address_request.new_owner_address_ar IS 'New owner address in Arabic.';
COMMENT ON COLUMN application.application_edit_name_address_request.old_owner_address_en IS 'Old owner address in English.';
COMMENT ON COLUMN application.application_edit_name_address_request.new_owner_address_en IS 'New owner address in English.';
COMMENT ON COLUMN application.application_edit_name_address_request.notes IS 'Additional notes for the edit.';
COMMENT ON COLUMN application.application_edit_name_address_request.migration_stage IS 'Migration stage of the edit name/address request.';

-- Table: application.application_edit_trademark_image_request
COMMENT ON COLUMN application.application_edit_trademark_image_request.id IS 'Primary key for the application edit trademark image request.';
COMMENT ON COLUMN application.application_edit_trademark_image_request.old_document_id IS 'Identifier of the old document associated with the trademark image.';
COMMENT ON COLUMN application.application_edit_trademark_image_request.new_document_id IS 'Identifier of the new document associated with the trademark image.';
COMMENT ON COLUMN application.application_edit_trademark_image_request.old_description IS 'Old description of the trademark image.';
COMMENT ON COLUMN application.application_edit_trademark_image_request.new_description IS 'New description of the trademark image.';
COMMENT ON COLUMN application.application_edit_trademark_image_request.old_name_ar IS 'Old name in Arabic associated with the trademark image.';
COMMENT ON COLUMN application.application_edit_trademark_image_request.new_name_ar IS 'New name in Arabic associated with the trademark image.';
COMMENT ON COLUMN application.application_edit_trademark_image_request.old_name_en IS 'Old name in English associated with the trademark image.';
COMMENT ON COLUMN application.application_edit_trademark_image_request.new_name_en IS 'New name in English associated with the trademark image.';
COMMENT ON COLUMN application.application_edit_trademark_image_request.migration_stage IS 'Migration stage of the edit trademark image request.';

-- Table: application.application_installment_config_types
COMMENT ON COLUMN application.application_installment_config_types.id IS 'Primary key for the application installment config types.';
COMMENT ON COLUMN application.application_installment_config_types.created_by_user IS 'Identifier for the user who created the application installment config types.';
COMMENT ON COLUMN application.application_installment_config_types.created_date IS 'Date and time when the application installment config types was created.';
COMMENT ON COLUMN application.application_installment_config_types.modified_by_user IS 'Identifier for the user who last modified the application installment config types.';
COMMENT ON COLUMN application.application_installment_config_types.modified_date IS 'Date and time when the application installment config types was last modified.';
COMMENT ON COLUMN application.application_installment_config_types.is_deleted IS 'Flag indicating whether the application installment config types is soft deleted or not.';
COMMENT ON COLUMN application.application_installment_config_types.notification_template_type IS 'Type of the notification template, (e.g, PATENT_RENEWAL_DUE_PERIOD_STARTED, TRADEMARK_RENEWAL_DUE_PERIOD_STARTED, TRADEMARK_RENEWED)';
COMMENT ON COLUMN application.application_installment_config_types.notification_type IS 'Type of the notification, (e.g, RENEWAL_DUE_PERIOD_STARTED, RENEWAL_GRACE_PERIOD_STARTED, RENEWAL_APPLICATION_DESERTION)';
COMMENT ON COLUMN application.application_installment_config_types.application_installment_config_id IS 'Identifier of the application installment config associated with the types.';

-- Table: application.application_installments
COMMENT ON COLUMN application.application_installments.id IS 'Primary key for the application installments.';
COMMENT ON COLUMN application.application_installments.created_by_user IS 'Identifier for the user who created the application installments.';
COMMENT ON COLUMN application.application_installments.created_date IS 'Date and time when the application installments was created.';
COMMENT ON COLUMN application.application_installments.modified_by_user IS 'Identifier for the user who last modified the application installments.';
COMMENT ON COLUMN application.application_installments.modified_date IS 'Date and time when the application installments was last modified.';
COMMENT ON COLUMN application.application_installments.is_deleted IS 'Flag indicating whether the application installments is soft deleted or not.';
COMMENT ON COLUMN application.application_installments.end_due_date IS 'End due date for the installment.';
COMMENT ON COLUMN application.application_installments.installment_status IS 'Status of the installment, (e.g, DELETED_BECAUSE_OF_APP_REJECTION, PAID, POSTPONED, EXPIRED, DUE_OVER, DUE, NEW)';
COMMENT ON COLUMN application.application_installments.installment_type IS 'Type of the installment, (e.g, ANNUAL, RENEWAL)';
COMMENT ON COLUMN application.application_installments.last_due_date IS 'Last due date for the installment.';
COMMENT ON COLUMN application.application_installments.payment_date IS 'Payment date for the installment.';
COMMENT ON COLUMN application.application_installments.start_due_date IS 'Start due date for the installment.';
COMMENT ON COLUMN application.application_installments.application_id IS 'Identifier of the application associated with the installment.';
COMMENT ON COLUMN application.application_installments.postponed_reason_id IS 'Identifier of the reason for installment postponement.';
COMMENT ON COLUMN application.application_installments.installment_index IS 'Index of the installment.';
COMMENT ON COLUMN application.application_installments.exception_message IS 'Exception message associated with the installment.';
COMMENT ON COLUMN application.application_installments.bill_number IS 'Bill number associated with the installment.';
COMMENT ON COLUMN application.application_installments.penalty_cost IS 'Penalty cost for the installment.';
COMMENT ON COLUMN application.application_installments.tax_cost IS 'Tax cost for the installment.';
COMMENT ON COLUMN application.application_installments.fee_cost IS 'Fee cost for the installment.';
COMMENT ON COLUMN application.application_installments.grace_end_date IS 'Grace end date for the installment.';
COMMENT ON COLUMN application.application_installments.installment_count IS 'Count of installments.';
COMMENT ON COLUMN application.application_installments.support_service_id IS 'Identifier of the support service associated with the installment.';
COMMENT ON COLUMN application.application_installments.migration_stage IS 'Migration stage of the application installments.';

-- Table: application.application_installments_config
COMMENT ON COLUMN application.application_installments_config.id IS 'Primary key for the application installment config.';
COMMENT ON COLUMN application.application_installments_config.created_by_user IS 'Identifier for the user who created the application installment config.';
COMMENT ON COLUMN application.application_installments_config.created_date IS 'Date and time when the application installment config was created.';
COMMENT ON COLUMN application.application_installments_config.modified_by_user IS 'Identifier for the user who last modified the application installment config.';
COMMENT ON COLUMN application.application_installments_config.modified_date IS 'Date and time when the application installment config was last modified.';
COMMENT ON COLUMN application.application_installments_config.is_deleted IS 'Flag indicating whether the application installment config is soft deleted or not.';
COMMENT ON COLUMN application.application_installments_config.application_category IS 'Application category associated with the installment config, (e.g, PATENT, TRADEMARK, INDUSTRIAL_DESIGN, INTEGRATED_CIRCUITS, PLANT_VARIETIES)';
COMMENT ON COLUMN application.application_installments_config.desertion_duration IS 'Duration for desertion.';
COMMENT ON COLUMN application.application_installments_config.grace_duration IS 'Grace duration.';
COMMENT ON COLUMN application.application_installments_config.installment_type IS 'Type of the installment, (e.g, ANNUAL, RENEWAL)';
COMMENT ON COLUMN application.application_installments_config.last_running_date IS 'Last running date for the installment.';
COMMENT ON COLUMN application.application_installments_config.notification_duration IS 'Duration for notification.';
COMMENT ON COLUMN application.application_installments_config.payment_duration IS 'Payment duration.';
COMMENT ON COLUMN application.application_installments_config.payment_interval_years IS 'Payment interval in years.';
COMMENT ON COLUMN application.application_installments_config.application_desertion_status_id IS 'Identifier of the application desertion status.';
COMMENT ON COLUMN application.application_installments_config.open_renewal_duration IS 'Open renewal duration.';

-- Table: application.application_legal_documents
COMMENT ON COLUMN application.application_legal_documents.id IS 'Primary key for the application legal documents.';
COMMENT ON COLUMN application.application_legal_documents.file_name IS 'Name of the legal document file.';
COMMENT ON COLUMN application.application_legal_documents.application_id IS 'Identifier of the application associated with the legal document.';
COMMENT ON COLUMN application.application_legal_documents.document_id IS 'Identifier of the document associated with the legal document.';
COMMENT ON COLUMN application.application_legal_documents.is_deleted IS 'Flag indicating whether the application legal document is soft deleted or not.';
COMMENT ON COLUMN application.application_legal_documents.created_by_user IS 'Identifier for the user who created the application legal document.';
COMMENT ON COLUMN application.application_legal_documents.created_date IS 'Date and time when the application legal document was created.';
COMMENT ON COLUMN application.application_legal_documents.modified_by_user IS 'Identifier for the user who last modified the application legal document.';
COMMENT ON COLUMN application.application_legal_documents.modified_date IS 'Date and time when the application legal document was last modified.';

-- Table: application.application_nice_classifications
COMMENT ON COLUMN application.application_nice_classifications.id IS 'Primary key for the application nice classifications.';
COMMENT ON COLUMN application.application_nice_classifications.created_by_user IS 'Identifier for the user who created the application nice classifications.';
COMMENT ON COLUMN application.application_nice_classifications.created_date IS 'Date and time when the application nice classifications was created.';
COMMENT ON COLUMN application.application_nice_classifications.modified_by_user IS 'Identifier for the user who last modified the application nice classifications.';
COMMENT ON COLUMN application.application_nice_classifications.modified_date IS 'Date and time when the application nice classifications was last modified.';
COMMENT ON COLUMN application.application_nice_classifications.is_deleted IS 'Flag indicating whether the application nice classifications is soft deleted or not.';
COMMENT ON COLUMN application.application_nice_classifications.sub_classification_type IS 'Type of sub-classification, (e.g, SHORTCUT_LIST, DETAIL_LIST)';
COMMENT ON COLUMN application.application_nice_classifications.application_id IS 'Identifier of the application associated with the nice classifications.';
COMMENT ON COLUMN application.application_nice_classifications.classification_id IS 'Identifier of the classification associated with the nice classifications.';
COMMENT ON COLUMN application.application_nice_classifications.version_id IS 'Identifier of the version associated with the nice classifications.';
COMMENT ON COLUMN application.application_nice_classifications.migration_stage IS 'Migration stage of the application nice classifications.';

-- Table: application.application_notes
COMMENT ON COLUMN application.application_notes.id IS 'Primary key for the application notes.';
COMMENT ON COLUMN application.application_notes.created_by_user IS 'Identifier for the user who created the application notes.';
COMMENT ON COLUMN application.application_notes.created_date IS 'Date and time when the application notes was created.';
COMMENT ON COLUMN application.application_notes.modified_by_user IS 'Identifier for the user who last modified the application notes.';
COMMENT ON COLUMN application.application_notes.modified_date IS 'Date and time when the application notes was last modified.';
COMMENT ON COLUMN application.application_notes.is_deleted IS 'Flag indicating whether the application notes is soft deleted or not.';
COMMENT ON COLUMN application.application_notes.description IS 'Description of the notes.';
COMMENT ON COLUMN application.application_notes.is_done IS 'Flag indicating whether the note is done or not.';
COMMENT ON COLUMN application.application_notes.application_id IS 'Identifier of the application associated with the notes.';
COMMENT ON COLUMN application.application_notes.section_id IS 'Identifier of the section associated with the notes.';
COMMENT ON COLUMN application.application_notes.step_id IS 'Identifier of the step associated with the notes.';
COMMENT ON COLUMN application.application_notes.attribute_id IS 'Identifier of the attribute associated with the notes.';
COMMENT ON COLUMN application.application_notes.task_definition_key IS 'Key of the task definition.';
COMMENT ON COLUMN application.application_notes.stage_key IS 'Key of the stage.';
COMMENT ON COLUMN application.application_notes.priority_id IS 'Identifier of the priority associated with the notes.';
COMMENT ON COLUMN application.application_notes.migration_stage IS 'Migration stage of the application notes.';

-- Table: application.application_office_reports
COMMENT ON COLUMN application.application_office_reports.id IS 'Primary key for the application office reports.';
COMMENT ON COLUMN application.application_office_reports.created_by_user IS 'Identifier for the user who created the application office reports.';
COMMENT ON COLUMN application.application_office_reports.created_date IS 'Date and time when the application office reports was created.';
COMMENT ON COLUMN application.application_office_reports.modified_by_user IS 'Identifier for the user who last modified the application office reports.';
COMMENT ON COLUMN application.application_office_reports.modified_date IS 'Date and time when the application office reports was last modified.';
COMMENT ON COLUMN application.application_office_reports.is_deleted IS 'Flag indicating whether the application office reports is soft deleted or not.';
COMMENT ON COLUMN application.application_office_reports.application_id IS 'Identifier of the application associated with the office reports.';
COMMENT ON COLUMN application.application_office_reports.document_id IS 'Identifier of the document associated with the office reports.';
COMMENT ON COLUMN application.application_office_reports.office_id IS 'Identifier of the office associated with the office reports.';
COMMENT ON COLUMN application.application_office_reports.migration_stage IS 'Migration stage of the application office reports.';

-- Table: application.application_installment_notifications
COMMENT ON COLUMN application.application_installment_notifications.id IS 'Primary key for the installment notifications.';
COMMENT ON COLUMN application.application_installment_notifications.created_by_user IS 'Identifier for the user who created the installment notifications.';
COMMENT ON COLUMN application.application_installment_notifications.created_date IS 'Date and time when the installment notifications was created.';
COMMENT ON COLUMN application.application_installment_notifications.modified_by_user IS 'Identifier for the user who last modified the installment notifications.';
COMMENT ON COLUMN application.application_installment_notifications.modified_date IS 'Date and time when the installment notifications was last modified.';
COMMENT ON COLUMN application.application_installment_notifications.is_deleted IS 'Flag indicating whether the installment notifications is soft deleted or not.';
COMMENT ON COLUMN application.application_installment_notifications.notification_status IS 'Status of the notification, (e.g, PENDING, SENT, FAILED)';
COMMENT ON COLUMN application.application_installment_notifications.notification_type IS 'Type of the notification, (e.g, RENEWAL_DUE_PERIOD_STARTED, RENEWAL_GRACE_PERIOD_STARTED, RENEWAL_APPLICATION_DESERTION)';
COMMENT ON COLUMN application.application_installment_notifications.application_installment_id IS 'Identifier of the associated installment (foreign key to application.application_installments).';
COMMENT ON COLUMN application.application_installment_notifications.document_id IS 'Identifier of the associated document (foreign key to application.documents).';
COMMENT ON COLUMN application.application_installment_notifications.customer_id IS 'Identifier of the customer associated with the notification (foreign key to customer.customers).';
COMMENT ON COLUMN application.application_installment_notifications.email IS 'Email address associated with the notification.';
COMMENT ON COLUMN application.application_installment_notifications.mobile IS 'Mobile number associated with the notification.';
COMMENT ON COLUMN application.application_installment_notifications.exception_message IS 'Exception message related to the notification.';
COMMENT ON COLUMN application.application_installment_notifications.notification_template_code IS 'Code for the notification template, (e.g, TRADEMARK_RENEWAL_DUE_PERIOD_STARTED, PATENT_RENEWAL_DUE_PERIOD_STARTED, TRADEMARK_RENEWED)';
COMMENT ON COLUMN application.application_installment_notifications.user_name IS 'User name associated with the notification.';

-- Table: application.application_other_documents
COMMENT ON COLUMN application.application_other_documents.id IS 'Primary key for the other documents.';
COMMENT ON COLUMN application.application_other_documents.created_by_user IS 'Identifier for the user who created the other documents.';
COMMENT ON COLUMN application.application_other_documents.created_date IS 'Date and time when the other documents was created.';
COMMENT ON COLUMN application.application_other_documents.modified_by_user IS 'Identifier for the user who last modified the other documents.';
COMMENT ON COLUMN application.application_other_documents.modified_date IS 'Date and time when the other documents was last modified.';
COMMENT ON COLUMN application.application_other_documents.is_deleted IS 'Flag indicating whether the other documents is soft deleted or not.';
COMMENT ON COLUMN application.application_other_documents.document_name IS 'Name of the document.';
COMMENT ON COLUMN application.application_other_documents.application_id IS 'Identifier of the associated application (foreign key to application.applications_info).';
COMMENT ON COLUMN application.application_other_documents.document_id IS 'Identifier of the associated document (foreign key to application.documents).';

-- Table: application.application_priority
COMMENT ON COLUMN application.application_priority.id IS 'Primary key for priority record.';
COMMENT ON COLUMN application.application_priority.created_by_user IS 'User who created the priority record.';
COMMENT ON COLUMN application.application_priority.created_date IS 'Date when the priority record was created.';
COMMENT ON COLUMN application.application_priority.modified_by_user IS 'User who last modified the priority record.';
COMMENT ON COLUMN application.application_priority.modified_date IS 'Date when the priority record was last modified.';
COMMENT ON COLUMN application.application_priority.is_deleted IS 'Flag indicating if the priority record is deleted.';
COMMENT ON COLUMN application.application_priority.application_class IS 'Application class associated with the priority.';
COMMENT ON COLUMN application.application_priority.country_id IS 'ID of the country associated with the priority.';
COMMENT ON COLUMN application.application_priority.das_code IS 'DAS (Digital Access Service) code for the priority.';
COMMENT ON COLUMN application.application_priority.filing_date IS 'Filing date of the priority.';
COMMENT ON COLUMN application.application_priority.priority_application_number IS 'Number of the priority application.';
COMMENT ON COLUMN application.application_priority.provide_doc_later IS 'Flag indicating if documents will be provided later.';
COMMENT ON COLUMN application.application_priority.application_info_id IS 'ID of the application info associated with the priority.';
COMMENT ON COLUMN application.application_priority.priority_document_id IS 'ID of the priority document.';
COMMENT ON COLUMN application.application_priority.priority_status_id IS 'ID of the priority status.';
COMMENT ON COLUMN application.application_priority.translated_document_id IS 'ID of the translated document.';
COMMENT ON COLUMN application.application_priority.is_expired IS 'Flag indicating if the priority has expired.';
COMMENT ON COLUMN application.application_priority.comment IS 'Comment associated with the priority.';
COMMENT ON COLUMN application.application_priority.migration_stage IS 'Migration stage of the priority record.';

-- Table: application.application_priority_modify_request_details
COMMENT ON COLUMN application.application_priority_modify_request_details.id IS 'Primary key for priority modify request details record.';
COMMENT ON COLUMN application.application_priority_modify_request_details.priority_modify_request_id IS 'ID of the associated priority modify request.';
COMMENT ON COLUMN application.application_priority_modify_request_details.created_by_user IS 'User who created the priority modify request details.';
COMMENT ON COLUMN application.application_priority_modify_request_details.created_date IS 'Date when the priority modify request details was created.';
COMMENT ON COLUMN application.application_priority_modify_request_details.modified_by_user IS 'User who last modified the priority modify request details.';
COMMENT ON COLUMN application.application_priority_modify_request_details.modified_date IS 'Date when the priority modify request details was last modified.';
COMMENT ON COLUMN application.application_priority_modify_request_details.is_deleted IS 'Flag indicating if the priority modify request details is deleted.';
COMMENT ON COLUMN application.application_priority_modify_request_details.application_class IS 'Application class associated with the priority modify request details.';
COMMENT ON COLUMN application.application_priority_modify_request_details.country_id IS 'ID of the country associated with the priority modify request details.';
COMMENT ON COLUMN application.application_priority_modify_request_details.das_code IS 'DAS (Digital Access Service) code for the priority modify request details.';
COMMENT ON COLUMN application.application_priority_modify_request_details.filing_date IS 'Filing date of the priority modify request details.';
COMMENT ON COLUMN application.application_priority_modify_request_details.priority_application_number IS 'Number of the priority application associated with the modify request details.';
COMMENT ON COLUMN application.application_priority_modify_request_details.priority_status_id IS 'ID of the priority status associated with the modify request details.';
COMMENT ON COLUMN application.application_priority_modify_request_details.provide_doc_later IS 'Flag indicating if documents will be provided later for the modify request details.';
COMMENT ON COLUMN application.application_priority_modify_request_details.priority_document_id IS 'ID of the priority document associated with the modify request details.';
COMMENT ON COLUMN application.application_priority_modify_request_details.translated_document_id IS 'ID of the translated document associated with the modify request details.';
COMMENT ON COLUMN application.application_priority_modify_request_details.is_expired IS 'Flag indicating if the priority associated with the modify request details has expired.';
COMMENT ON COLUMN application.application_priority_modify_request_details.comment IS 'Comment associated with the priority modify request details.';

-- Table: application.application_priority_modify_request
COMMENT ON COLUMN application.application_priority_modify_request.id IS 'Primary key for priority modify request record.';
COMMENT ON COLUMN application.application_priority_modify_request.created_by_user IS 'User who created the priority modify request.';
COMMENT ON COLUMN application.application_priority_modify_request.created_date IS 'Date when the priority modify request was created.';
COMMENT ON COLUMN application.application_priority_modify_request.modified_by_user IS 'User who last modified the priority modify request.';
COMMENT ON COLUMN application.application_priority_modify_request.modified_date IS 'Date when the priority modify request was last modified.';
COMMENT ON COLUMN application.application_priority_modify_request.is_deleted IS 'Flag indicating if the priority modify request is deleted.';
COMMENT ON COLUMN application.application_priority_modify_request.is_request_updated IS 'Flag indicating if the request is updated.';

-- Table: application.application_priority_request
COMMENT ON COLUMN application.application_priority_request.id IS 'Primary key for the application priority request.';
COMMENT ON COLUMN application.application_priority_request.document_id IS 'Identifier of the associated document (foreign key to application.documents).';
COMMENT ON COLUMN application.application_priority_request.id IS 'Primary key for priority request record.';
COMMENT ON COLUMN application.application_priority_request.modify_type IS 'Type of modification requested, (e.g, ADD, EDIT, BOTH)';
COMMENT ON COLUMN application.application_priority_request.reason IS 'Reason for the modification request.';
COMMENT ON COLUMN application.application_priority_request.document_id IS 'ID of the document associated with the modification request.';
COMMENT ON COLUMN application.application_priority_request.is_request_updated IS 'Flag indicating if the request has been updated.';

-- Table: application.application_publication
COMMENT ON COLUMN application.application_publication.id IS 'Primary key for the application publication.';
COMMENT ON COLUMN application.application_publication.is_deleted IS 'Flag indicating whether the application publication is soft deleted or not.';
COMMENT ON COLUMN application.application_publication.created_date IS 'Date and time when the application publication was created.';
COMMENT ON COLUMN application.application_publication.created_by_user IS 'Identifier for the user who created the application publication.';
COMMENT ON COLUMN application.application_publication.modified_date IS 'Date and time when the application publication was last modified.';
COMMENT ON COLUMN application.application_publication.modified_by_user IS 'Identifier for the user who last modified the application publication.';
COMMENT ON COLUMN application.application_publication.application_info_id IS 'Identifier of the associated application (foreign key to application.applications_info).';
COMMENT ON COLUMN application.application_publication.publication_type_id IS 'Identifier of the publication type (foreign key to application.lk_publication_type).';
COMMENT ON COLUMN application.application_publication.publication_date IS 'Date of publication.';
COMMENT ON COLUMN application.application_publication.publication_date_hijri IS 'Hijri date of publication.';
COMMENT ON COLUMN application.application_publication.document_id IS 'Identifier of the associated document (foreign key to application.documents).';
COMMENT ON COLUMN application.application_publication.support_service_id IS 'Identifier of the support service (foreign key to application.lk_support_services).';
COMMENT ON COLUMN application.application_publication.is_published IS 'Flag indicating if the application has been published.';
COMMENT ON COLUMN application.application_publication.publication_number IS 'Publication number associated with the application.';
COMMENT ON COLUMN application.application_publication.migration_stage IS 'Migration stage of the publication record.';

-- Table: application.application_relevant
COMMENT ON COLUMN application.application_relevant.id IS 'Primary key for the relevant application.';
COMMENT ON COLUMN application.application_relevant.created_by_user IS 'Identifier for the user who created the relevant application.';
COMMENT ON COLUMN application.application_relevant.created_date IS 'Date and time when the relevant application was created.';
COMMENT ON COLUMN application.application_relevant.modified_by_user IS 'Identifier for the user who last modified the relevant application.';
COMMENT ON COLUMN application.application_relevant.modified_date IS 'Date and time when the relevant application was last modified.';
COMMENT ON COLUMN application.application_relevant.is_deleted IS 'Flag indicating whether the relevant application is soft deleted or not.';
COMMENT ON COLUMN application.application_relevant.country_id IS 'Identifier of the country (foreign key to customer.country).';
COMMENT ON COLUMN application.application_relevant.national_country_id IS 'Identifier of the national country (foreign key to customer.country).';
COMMENT ON COLUMN application.application_relevant.address IS 'Address associated with the relevant application.';
COMMENT ON COLUMN application.application_relevant.city IS 'City associated with the relevant application.';
COMMENT ON COLUMN application.application_relevant.full_name_ar IS 'Full name in Arabic associated with the relevant application.';
COMMENT ON COLUMN application.application_relevant.full_name_en IS 'Full name in English associated with the relevant application.';
COMMENT ON COLUMN application.application_relevant.gender IS 'Gender associated with the relevant application.';
COMMENT ON COLUMN application.application_relevant.identifier IS 'Identifier associated with the relevant application.';
COMMENT ON COLUMN application.application_relevant.identifier_type IS 'Type of identifier associated with the relevant application, (e.g, CUSTOMER_CODE, PASSPORT_NUMBER, NATIONAL_ID, IQAMA_NUMBER)';
COMMENT ON COLUMN application.application_relevant.pobox IS 'PO Box associated with the relevant application.';
COMMENT ON COLUMN application.application_relevant.migration_stage IS 'Migration stage of the relevant application record.';

-- Table: application.application_relevant_type
COMMENT ON COLUMN application.application_relevant_type.id IS 'Primary key for the relevant application type.';
COMMENT ON COLUMN application.application_relevant_type.created_by_user IS 'Identifier for the user who created the relevant application type.';
COMMENT ON COLUMN application.application_relevant_type.created_date IS 'Date and time when the relevant application type was created.';
COMMENT ON COLUMN application.application_relevant_type.modified_by_user IS 'Identifier for the user who last modified the relevant application type.';
COMMENT ON COLUMN application.application_relevant_type.modified_date IS 'Date and time when the relevant application type was last modified.';
COMMENT ON COLUMN application.application_relevant_type.is_deleted IS 'Flag indicating whether the relevant application type is soft deleted or not.';
COMMENT ON COLUMN application.application_relevant_type.application_info_id IS 'Identifier of the associated application (foreign key to application.applications_info).';
COMMENT ON COLUMN application.application_relevant_type.application_relevant_id IS 'Identifier of the associated relevant application (foreign key to application.application_relevant).';
COMMENT ON COLUMN application.application_relevant_type.waiver_document_id IS 'Identifier of the waiver document (foreign key to application.documents).';
COMMENT ON COLUMN application.application_relevant_type.customer_code IS 'Customer code associated with the relevant application type.';
COMMENT ON COLUMN application.application_relevant_type.inventor IS 'Flag indicating if the relevant application type is an inventor.';
COMMENT ON COLUMN application.application_relevant_type.type IS 'Type of relevant application, (e.g, Applicant_MAIN, Applicant_SECONDARY, INVENTOR)';
COMMENT ON COLUMN application.application_relevant_type.is_paid IS 'Flag indicating if the relevant application type is paid.';
COMMENT ON COLUMN application.application_relevant_type.duplication_flag IS 'Duplication flag for the relevant application type.';
COMMENT ON COLUMN application.application_relevant_type.migration_stage IS 'Migration stage of the relevant application type record.';

-- Table: application.application_search_results
COMMENT ON COLUMN application.application_search_results.id IS 'Primary key for the application search result.';
COMMENT ON COLUMN application.application_search_results.created_by_user IS 'Identifier for the user who created the application search result.';
COMMENT ON COLUMN application.application_search_results.created_date IS 'Date and time when the application search result was created.';
COMMENT ON COLUMN application.application_search_results.modified_by_user IS 'Identifier for the user who last modified the application search result.';
COMMENT ON COLUMN application.application_search_results.modified_date IS 'Date and time when the application search result was last modified.';
COMMENT ON COLUMN application.application_search_results.is_deleted IS 'Flag indicating whether the application search result is soft deleted or not.';
COMMENT ON COLUMN application.application_search_results.country_id IS 'Identifier of the country (foreign key to customer.country).';
COMMENT ON COLUMN application.application_search_results.application_id IS 'Identifier of the associated application (foreign key to application.applications_info).';
COMMENT ON COLUMN application.application_search_results.document_id IS 'Identifier of the associated document (foreign key to application.documents).';
COMMENT ON COLUMN application.application_search.title IS 'Title of the application search.';
COMMENT ON COLUMN application.application_search.description IS 'Description of the application search.';
COMMENT ON COLUMN application.application_search.notes IS 'Additional notes for the application search.';
COMMENT ON COLUMN application.application_search.migration_stage IS 'Migration stage of the application search record.';
COMMENT ON COLUMN application.application_search_results.relation_of_protection_elements IS 'Relation of protection elements associated with the search result.';
COMMENT ON COLUMN application.application_search_results.result_date IS 'Date of the search result.';
COMMENT ON COLUMN application.application_search_results.result_link IS 'Link to the search result.';
COMMENT ON COLUMN application.application_search_results.same_document IS 'Flag indicating if the search result refers to the same document.';
COMMENT ON COLUMN application.application_search_results.result IS 'Result of the search.';

-- Table: application.application_search
COMMENT ON COLUMN application.application_search.id IS 'Primary key for the application search.';
COMMENT ON COLUMN application.application_search.classification_id IS 'Identifier of the classification (foreign key to application.lk_classification).';
COMMENT ON COLUMN application.application_search.document_id IS 'Identifier of the associated document (foreign key to application.documents).';

-- Table: application.application_search_similars
COMMENT ON COLUMN application.application_search_similars.id IS 'Primary key for the application search similar.';
COMMENT ON COLUMN application.application_search_similars.created_by_user IS 'Identifier for the user who created the application search similar.';
COMMENT ON COLUMN application.application_search_similars.created_date IS 'Date and time when the application search similar was created.';
COMMENT ON COLUMN application.application_search_similars.modified_by_user IS 'Identifier for the user who last modified the application search similar.';
COMMENT ON COLUMN application.application_search_similars.modified_date IS 'Date and time when the application search similar was last modified.';
COMMENT ON COLUMN application.application_search_similars.is_deleted IS 'Flag indicating whether the application search similar is soft deleted or not.';
COMMENT ON COLUMN application.application_search_similars.ip_search_id IS 'Identifier of the associated IP search (foreign key to application.lk_ip_search).';
COMMENT ON COLUMN application.application_search_similars.application_search_id IS 'Identifier of the associated application search (foreign key to application.application_search).';
COMMENT ON COLUMN application.application_search_similars.application_title IS 'Title of the application';
COMMENT ON COLUMN application.application_search_similars.filing_number IS 'Filing number of the application';
COMMENT ON COLUMN application.application_search_similars.saip_doc_id IS 'SAIP document ID';
COMMENT ON COLUMN application.application_search_similars.status IS 'Status of the application';
COMMENT ON COLUMN application.application_search_similars.filing_date IS 'Date of filing the application';

-- Table: application.application_similar_documents
COMMENT ON COLUMN application.application_similar_documents.id IS 'Unique identifier for the record';
COMMENT ON COLUMN application.application_similar_documents.created_by_user IS 'User who created the record';
COMMENT ON COLUMN application.application_similar_documents.created_date IS 'Date when the record was created';
COMMENT ON COLUMN application.application_similar_documents.modified_by_user IS 'User who last modified the record';
COMMENT ON COLUMN application.application_similar_documents.modified_date IS 'Date when the record was last modified';
COMMENT ON COLUMN application.application_similar_documents.is_deleted IS 'Flag indicating if the record is deleted';
COMMENT ON COLUMN application.application_similar_documents.country_id IS 'Identifier for the country';
COMMENT ON COLUMN application.application_similar_documents.document_date IS 'Date of the document';
COMMENT ON COLUMN application.application_similar_documents.document_link IS 'Link to the document';
COMMENT ON COLUMN application.application_similar_documents.document_number IS 'Number of the document';
COMMENT ON COLUMN application.application_similar_documents.publication_number IS 'Number of the publication';
COMMENT ON COLUMN application.application_similar_documents.application_id IS 'ID of the associated application';
COMMENT ON COLUMN application.application_similar_documents.document_id IS 'ID of the associated document';

-- Table: application.application_section_notes
COMMENT ON COLUMN application.application_section_notes.id IS 'Primary key for the application section note.';
COMMENT ON COLUMN application.application_section_notes.application_notes_id IS 'Foreign key referencing the associated application notes.';
COMMENT ON COLUMN application.application_section_notes.note_id IS 'Foreign key referencing the associated note.';
COMMENT ON COLUMN application.application_section_notes.description IS 'Description of the application section note.';
COMMENT ON COLUMN application.application_section_notes.is_deleted IS 'Flag indicating whether this application section note is soft deleted or not.';
COMMENT ON COLUMN application.application_section_notes.created_date IS 'Date and time when the application section note was created.';
COMMENT ON COLUMN application.application_section_notes.created_by_user IS 'Identifier for the user who created the application section note.';
COMMENT ON COLUMN application.application_section_notes.modified_by_user IS 'Identifier for the user who last modified the application section note.';
COMMENT ON COLUMN application.application_section_notes.modified_date IS 'Date and time when the application section note was last modified.';

-- Table: application.application_status_change_log
COMMENT ON COLUMN application.application_status_change_log.id IS 'Primary key for the application status change log.';
COMMENT ON COLUMN application.application_status_change_log.created_by_user IS 'Identifier for the user who created the application status change log.';
COMMENT ON COLUMN application.application_status_change_log.created_date IS 'Date and time when the application status change log was created.';
COMMENT ON COLUMN application.application_status_change_log.modified_by_user IS 'Identifier for the user who last modified the application status change log.';
COMMENT ON COLUMN application.application_status_change_log.modified_date IS 'Date and time when the application status change log was last modified.';
COMMENT ON COLUMN application.application_status_change_log.is_deleted IS 'Flag indicating whether the application status change log is soft deleted or not.';
COMMENT ON COLUMN application.application_status_change_log.application_id IS 'Identifier of the associated application (foreign key to application.applications_info).';
COMMENT ON COLUMN application.application_status_change_log.new_status_id IS 'Identifier of the new status (foreign key to application.lk_application_status).';
COMMENT ON COLUMN application.application_status_change_log.previous_status_id IS 'Identifier of the previous status (foreign key to application.lk_application_status).';
COMMENT ON COLUMN application.application_status_change_log.description_code IS 'Description code associated with the status change log.';
COMMENT ON COLUMN application.application_status_change_log.task_definition_key IS 'Task definition key associated with the status change log.';
COMMENT ON COLUMN application.application_status_change_log.task_instance_id IS 'ID of the task instance associated with the status change log.';

-- Table: application.application_sub_classifications
COMMENT ON COLUMN application.application_sub_classifications.id IS 'Primary key for the application sub-classification.';
COMMENT ON COLUMN application.application_sub_classifications.sub_classification_id IS 'Identifier of the sub-classification (foreign key to application.sub_classification).';
COMMENT ON COLUMN application.application_sub_classifications.application_id IS 'Identifier of the associated application (foreign key to application.applications_info).';
COMMENT ON COLUMN application.application_sub_classifications.created_by_user IS 'User who created the application sub-classification record.';
COMMENT ON COLUMN application.application_sub_classifications.created_date IS 'Date when the application sub-classification record was created.';
COMMENT ON COLUMN application.application_sub_classifications.modified_by_user IS 'User who last modified the application sub-classification record.';
COMMENT ON COLUMN application.application_sub_classifications.modified_date IS 'Date when the application sub-classification record was last modified.';
COMMENT ON COLUMN application.application_sub_classifications.is_deleted IS 'Flag indicating if the application sub-classification record is deleted.';
COMMENT ON COLUMN application.application_sub_classifications.migration_stage IS 'Migration stage identifier for the application sub-classification.';

-- Table: application.similar_trademark
COMMENT ON COLUMN application.similar_trademark.id IS 'Unique identifier for the similar trademark record.';
COMMENT ON COLUMN application.similar_trademark.created_by_user IS 'User who created the similar trademark record.';
COMMENT ON COLUMN application.similar_trademark.created_date IS 'Date when the similar trademark record was created.';
COMMENT ON COLUMN application.similar_trademark.modified_by_user IS 'User who last modified the similar trademark record.';
COMMENT ON COLUMN application.similar_trademark.modified_date IS 'Date when the similar trademark record was last modified.';
COMMENT ON COLUMN application.similar_trademark.is_deleted IS 'Flag indicating if the similar trademark record is deleted.';
COMMENT ON COLUMN application.similar_trademark.image_link IS 'Link to the image of the similar trademark.';
COMMENT ON COLUMN application.similar_trademark.preview_link IS 'Link to the preview of the similar trademark.';
COMMENT ON COLUMN application.similar_trademark.task_definition_key IS 'Task definition key related to the similar trademark.';
COMMENT ON COLUMN application.similar_trademark.task_instance_id IS 'Task instance identifier related to the similar trademark.';
COMMENT ON COLUMN application.similar_trademark.trademark_number IS 'Trademark number associated with the similar trademark.';
COMMENT ON COLUMN application.similar_trademark.application_id IS 'Foreign key reference to the associated application.';
COMMENT ON COLUMN application.similar_trademark.ips_id IS 'IPS (Intellectual Property System) identifier related to the similar trademark.';

-- Table: application.patent_details
COMMENT ON COLUMN application.patent_details.id IS 'Unique identifier for the patent details record.';
COMMENT ON COLUMN application.patent_details.created_by_user IS 'User who created the patent details record.';
COMMENT ON COLUMN application.patent_details.created_date IS 'Date when the patent details record was created.';
COMMENT ON COLUMN application.patent_details.modified_by_user IS 'User who last modified the patent details record.';
COMMENT ON COLUMN application.patent_details.modified_date IS 'Date when the patent details record was last modified.';
COMMENT ON COLUMN application.patent_details.is_deleted IS 'Flag indicating if the patent details record is deleted.';
COMMENT ON COLUMN application.patent_details.ipd_summary_ar IS 'Summary of the patent details in Arabic.';
COMMENT ON COLUMN application.patent_details.ipd_summary_en IS 'Summary of the patent details in English.';
COMMENT ON COLUMN application.patent_details.collaborative_research IS 'Indicator for collaborative research associated with the patent.';
COMMENT ON COLUMN application.patent_details.application_id IS 'Foreign key reference to the associated application.';
COMMENT ON COLUMN application.patent_details.specifications_doc_id IS 'Document ID of the patent specifications.';
COMMENT ON COLUMN application.patent_details.collaborative_research_id IS 'Foreign key reference to the collaborative research details.';
COMMENT ON COLUMN application.patent_details.migration_stage IS 'Indicator for the migration stage of the patent details record.';

-- Table: application.patent_attribute_change_logs
COMMENT ON COLUMN application.patent_attribute_change_logs.id IS 'Unique identifier for the patent attribute change log record.';
COMMENT ON COLUMN application.patent_attribute_change_logs.created_by_user IS 'User who created the patent attribute change log record.';
COMMENT ON COLUMN application.patent_attribute_change_logs.created_date IS 'Date when the patent attribute change log record was created.';
COMMENT ON COLUMN application.patent_attribute_change_logs.modified_by_user IS 'User who last modified the patent attribute change log record.';
COMMENT ON COLUMN application.patent_attribute_change_logs.modified_date IS 'Date when the patent attribute change log record was last modified.';
COMMENT ON COLUMN application.patent_attribute_change_logs.is_deleted IS 'Flag indicating if the patent attribute change log record is deleted.';
COMMENT ON COLUMN application.patent_attribute_change_logs.attribute_name IS 'Name of the changed attribute.';
COMMENT ON COLUMN application.patent_attribute_change_logs.attribute_value IS 'New value of the changed attribute.';
COMMENT ON COLUMN application.patent_attribute_change_logs.task_definition_key IS 'Task definition key associated with the change log.';
COMMENT ON COLUMN application.patent_attribute_change_logs.task_id IS 'Task ID associated with the change log.';
COMMENT ON COLUMN application.patent_attribute_change_logs.version IS 'Version number of the patent attribute change log.';
COMMENT ON COLUMN application.patent_attribute_change_logs.patent_details_id IS 'Foreign key reference to the associated patent details.';
COMMENT ON COLUMN application.patent_attribute_change_logs.migration_stage IS 'Indicator for the migration stage of the patent attribute change log record.';

-- Table: application.opposition
COMMENT ON COLUMN application.opposition.id IS 'Unique identifier for the opposition record.';
COMMENT ON COLUMN application.opposition.applicant_examiner_notes IS 'Notes from the applicant examiner.';
COMMENT ON COLUMN application.opposition.applicant_session_date IS 'Date of the applicant session.';
COMMENT ON COLUMN application.opposition.applicant_session_file_url IS 'URL to the file associated with the applicant session.';
COMMENT ON COLUMN application.opposition.applicant_hearing_session_scheduled IS 'Indicator if the applicant hearing session is scheduled.';
COMMENT ON COLUMN application.opposition.is_applicant_session_paid IS 'Flag indicating if the applicant session is paid.';
COMMENT ON COLUMN application.opposition.applicant_session_result IS 'Result of the applicant session.';
COMMENT ON COLUMN application.opposition.applicant_session_time IS 'Time of the applicant session.';
COMMENT ON COLUMN application.opposition.complainer_customer_id IS 'Customer ID of the complainer.';
COMMENT ON COLUMN application.opposition.final_decision IS 'Final decision of the opposition, (e.g, ACCEPTED, REJECTED)';
COMMENT ON COLUMN application.opposition.final_notes IS 'Final notes related to the opposition.';
COMMENT ON COLUMN application.opposition.head_examiner_notes_to_examiner IS 'Notes from the head examiner to the examiner.';
COMMENT ON COLUMN application.opposition.session_date IS 'Date of the opposition session.';
COMMENT ON COLUMN application.opposition.session_file_url IS 'URL to the file associated with the opposition session.';
COMMENT ON COLUMN application.opposition.hearing_session_scheduled IS 'Indicator if the hearing session is scheduled.';
COMMENT ON COLUMN application.opposition.is_session_paid IS 'Flag indicating if the session is paid.';
COMMENT ON COLUMN application.opposition.session_result IS 'Result of the opposition session.';
COMMENT ON COLUMN application.opposition.session_time IS 'Time of the opposition session.';
COMMENT ON COLUMN application.opposition.head_examiner_confirmed IS 'Indicator if the head examiner confirmed.';
COMMENT ON COLUMN application.opposition.legal_representative_email IS 'Email of the legal representative.';
COMMENT ON COLUMN application.opposition.legal_representative_name IS 'Name of the legal representative.';
COMMENT ON COLUMN application.opposition.legal_representative_phone IS 'Phone number of the legal representative.';
COMMENT ON COLUMN application.opposition.opposition_reason IS 'Reason for the opposition.';
COMMENT ON COLUMN application.opposition.opposition_type IS 'Type of the opposition, (e.g, FULL_APPLICATION, PRIORITY, CLASSIFICATION)';
COMMENT ON COLUMN application.opposition.application_id IS 'Foreign key reference to the associated application.';

-- Table: application.application_word
COMMENT ON COLUMN application.application_word.id IS 'Unique identifier for the application word entry.';
COMMENT ON COLUMN application.application_word.created_by_user IS 'User who created the application word record.';
COMMENT ON COLUMN application.application_word.created_date IS 'Date when the application word record was created.';
COMMENT ON COLUMN application.application_word.modified_by_user IS 'User who last modified the application word record.';
COMMENT ON COLUMN application.application_word.modified_date IS 'Date when the application word record was last modified.';
COMMENT ON COLUMN application.application_word.is_deleted IS 'Flag indicating whether the application word record is marked as deleted (soft delete).';
COMMENT ON COLUMN application.application_word.synonym IS 'Synonym associated with the word.';
COMMENT ON COLUMN application.application_word.word IS 'Word entry.';
COMMENT ON COLUMN application.application_word.application_info_id IS 'Foreign key reference to the associated application information.';

-- Table: application.application_support_services_type
COMMENT ON COLUMN application.application_support_services_type.id IS 'Primary key for the application support service type.';
COMMENT ON COLUMN application.application_support_services_type.id IS 'Unique identifier for the support service entry.';
COMMENT ON COLUMN application.application_support_services_type.created_by_user IS 'User who created the support service record.';
COMMENT ON COLUMN application.application_support_services_type.created_date IS 'Date when the support service record was created.';
COMMENT ON COLUMN application.application_support_services_type.modified_by_user IS 'User who last modified the support service record.';
COMMENT ON COLUMN application.application_support_services_type.modified_date IS 'Date when the support service record was last modified.';
COMMENT ON COLUMN application.application_support_services_type.is_deleted IS 'Flag indicating whether the support service record is marked as deleted (soft delete).';
COMMENT ON COLUMN application.application_support_services_type.application_info_id IS 'Foreign key reference to the associated application information.';
COMMENT ON COLUMN application.application_support_services_type.lk_support_service_type_id IS 'Foreign key reference to the support service type.';
COMMENT ON COLUMN application.application_support_services_type.payment_status IS 'Status of payment for the support service, (e.g, PAID, UNPAID, FREE)';
COMMENT ON COLUMN application.application_support_services_type.request_number IS 'Number associated with the support service request.';
COMMENT ON COLUMN application.application_support_services_type.request_status IS 'Status of the support service request.';
COMMENT ON COLUMN application.application_support_services_type.created_by_customer_code IS 'Code associated with the customer who created the support service record.';
COMMENT ON COLUMN application.application_support_services_type.process_request_id IS 'ID associated with the process request.';
COMMENT ON COLUMN application.application_support_services_type.migration_stage IS 'Migration stage identifier.';
COMMENT ON COLUMN application.application_support_services_type.application_info_id IS 'Identifier of the associated application info (foreign key to application.applications_info).';
COMMENT ON COLUMN application.application_support_services_type.lk_support_service_type_id IS 'Identifier of the associated support service type (foreign key to application.lk_support_service_type).';
COMMENT ON COLUMN application.application_support_services_type.process_request_id IS 'Identifier of the associated process request (foreign key to efiling.requests).';

-- Table: application.application_support_service_comment
COMMENT ON COLUMN application.application_support_service_comment.id IS 'Unique identifier for the support service comment entry.';
COMMENT ON COLUMN application.application_support_service_comment.created_by_user IS 'User who created the support service comment.';
COMMENT ON COLUMN application.application_support_service_comment.created_date IS 'Date when the support service comment was created.';
COMMENT ON COLUMN application.application_support_service_comment.modified_by_user IS 'User who last modified the support service comment.';
COMMENT ON COLUMN application.application_support_service_comment.modified_date IS 'Date when the support service comment was last modified.';
COMMENT ON COLUMN application.application_support_service_comment.is_deleted IS 'Flag indicating whether the support service comment is marked as deleted (soft delete).';
COMMENT ON COLUMN application.application_support_service_comment.comment IS 'The actual comment text for the support service.';
COMMENT ON COLUMN application.application_support_service_comment.application_support_services_type_id IS 'Foreign key reference to the associated support service type entry.';

-- Table: application.opposition_classification
COMMENT ON COLUMN application.opposition_classification.opposition_id IS 'Foreign key referencing the opposition entry associated with this classification.';
COMMENT ON COLUMN application.opposition_classification.classification_id IS 'Foreign key referencing the classification entry associated with this opposition.';

-- Table: application.opposition_revoke_licence_request_court_documents
COMMENT ON COLUMN application.opposition_revoke_licence_request_court_documents.opposition_revoke_licence_request_id IS 'Foreign key referencing the opposition revoke licence request associated with this court document.';
COMMENT ON COLUMN application.opposition_revoke_licence_request_court_documents.document_id IS 'Foreign key referencing the document related to the opposition revoke licence request.';

-- Table: application.opposition_application_similars
COMMENT ON COLUMN application.opposition_application_similars.opposition_request_id IS 'Foreign key referencing the opposition request associated with this similar application.';
COMMENT ON COLUMN application.opposition_application_similars.application_info_id IS 'Foreign key referencing the similar application info related to the opposition request.';

-- Table: application.opposition_documents
COMMENT ON COLUMN application.opposition_documents.opposition_id IS 'Foreign key referencing the opposition request associated with this document.';
COMMENT ON COLUMN application.opposition_documents.document_id IS 'Foreign key referencing the document attached to the opposition request.';

-- Table: application.application_users
COMMENT ON COLUMN application.application_users.id IS 'Primary key for the application user.';
COMMENT ON COLUMN application.application_users.created_by_user IS 'Identifier for the user who created the application user.';
COMMENT ON COLUMN application.application_users.created_date IS 'Date and time when the application user was created.';
COMMENT ON COLUMN application.application_users.modified_by_user IS 'Identifier for the user who last modified the application user.';
COMMENT ON COLUMN application.application_users.modified_date IS 'Date and time when the application user was last modified.';
COMMENT ON COLUMN application.application_users.is_deleted IS 'Flag indicating whether this application user is soft deleted or not.';
COMMENT ON COLUMN application.application_users.user_name IS 'Username of the application user.';
COMMENT ON COLUMN application.application_users.user_role IS 'Role of the application user (e.g, CHECKER, HEAD_OF_CHECKER, EXAMINER, HEAD_OF_EXAMINER, CLASSIFICATION_SPECIALIST)';
COMMENT ON COLUMN application.application_users.application_id IS 'Foreign key referencing the associated application (application.applications_info)';
COMMENT ON COLUMN application.application_users.migration_stage IS 'Migration stage indicator for the application user record.';

-- Table: application.application_veena_classifications
COMMENT ON COLUMN application.application_veena_classifications.id IS 'Primary key for the Veena application classification.';
COMMENT ON COLUMN application.application_veena_classifications.created_by_user IS 'Identifier for the user who created the Veena application classification.';
COMMENT ON COLUMN application.application_veena_classifications.created_date IS 'Date and time when the Veena application classification was created.';
COMMENT ON COLUMN application.application_veena_classifications.modified_by_user IS 'Identifier for the user who last modified the Veena application classification.';
COMMENT ON COLUMN application.application_veena_classifications.modified_date IS 'Date and time when the Veena application classification was last modified.';
COMMENT ON COLUMN application.application_veena_classifications.is_deleted IS 'Flag indicating whether this Veena application classification is soft deleted or not.';
COMMENT ON COLUMN application.application_veena_classifications.application_id IS 'Foreign key referencing the associated application.';
COMMENT ON COLUMN application.application_veena_classifications.veena_assistant_department_id IS 'Foreign key referencing the Veena assistant department.';
COMMENT ON COLUMN application.application_veena_classifications.veena_classification_id IS 'Foreign key referencing the Veena classification.';
COMMENT ON COLUMN application.application_veena_classifications.veena_department_id IS 'Foreign key referencing the Veena department.';

-- Table: application.application_versions
COMMENT ON COLUMN application.application_versions.id IS 'Primary key for the application version.';
COMMENT ON COLUMN application.application_versions.application_info_dto IS 'DTO (Data Transfer Object) for application information.';
COMMENT ON COLUMN application.application_versions.patent_detail_dto_with_change_log_dto IS 'DTO for patent details with change log.';
COMMENT ON COLUMN application.application_versions.patent_request_dto IS 'DTO for patent request.';
COMMENT ON COLUMN application.application_versions.pct_dto IS 'DTO for PCT (Patent Cooperation Treaty).';
COMMENT ON COLUMN application.application_versions.version_number IS 'Version number of the application.';
COMMENT ON COLUMN application.application_versions.application_id IS 'Identifier of the associated application (foreign key to application.applications_info).';
COMMENT ON COLUMN application.application_versions.drawing_dto IS 'DTO for drawings associated with the application version.';
COMMENT ON COLUMN application.application_versions.protection_elements_dto IS 'DTO for protection elements associated with the application version.';
COMMENT ON COLUMN application.application_versions.created_by_user IS 'Identifier for the user who created the application version.';
COMMENT ON COLUMN application.application_versions.created_date IS 'Date and time when the application version was created.';
COMMENT ON COLUMN application.application_versions.modified_by_user IS 'Identifier for the user who last modified the application version.';
COMMENT ON COLUMN application.application_versions.modified_date IS 'Date and time when the application version was last modified.';
COMMENT ON COLUMN application.application_versions.is_deleted IS 'Flag indicating if the application version is deleted';

-- Table: application.certificate_types_application_categories
COMMENT ON COLUMN application.certificate_types_application_categories.lk_certificate_type_id IS 'Foreign key referencing the certificate type.';
COMMENT ON COLUMN application.certificate_types_application_categories.lk_category_id IS 'Foreign key referencing the category for which the certificate type applies.';

-- Table: application.certificates_request
COMMENT ON COLUMN application.certificates_request.id IS 'Primary key for the certificate request.';
COMMENT ON COLUMN application.certificates_request.created_by_user IS 'Identifier for the user who created the certificate request.';
COMMENT ON COLUMN application.certificates_request.created_date IS 'Date and time when the certificate request was created.';
COMMENT ON COLUMN application.certificates_request.modified_by_user IS 'Identifier for the user who last modified the certificate request.';
COMMENT ON COLUMN application.certificates_request.modified_date IS 'Date and time when the certificate request was last modified.';
COMMENT ON COLUMN application.certificates_request.is_deleted IS 'Flag indicating whether this certificate request is soft deleted or not.';
COMMENT ON COLUMN application.certificates_request.application_request_status IS 'Status of the certificate request.';
COMMENT ON COLUMN application.certificates_request.application_info_id IS 'Foreign key referencing the associated application information.';
COMMENT ON COLUMN application.certificates_request.certificate_type_id IS 'Foreign key referencing the type of certificate requested.';
COMMENT ON COLUMN application.certificates_request.document_id IS 'Foreign key referencing the document associated with the certificate request.';
COMMENT ON COLUMN application.certificates_request.certificate_status_id IS 'Foreign key referencing the status of the certificate request.';
COMMENT ON COLUMN application.certificates_request.request_number IS 'Request number for the certificate request.';
COMMENT ON COLUMN application.certificates_request.migration_stage IS 'Migration stage indicator.';
COMMENT ON COLUMN application.certificates_request.serial IS 'DB serial Identifier';

-- Table: application.change_ownership_customers
COMMENT ON COLUMN application.change_ownership_customers.id IS 'Primary key for the change of ownership customer record.';
COMMENT ON COLUMN application.change_ownership_customers.created_by_user IS 'Identifier for the user who created the change of ownership record.';
COMMENT ON COLUMN application.change_ownership_customers.created_date IS 'Date and time when the change of ownership record was created.';
COMMENT ON COLUMN application.change_ownership_customers.modified_by_user IS 'Identifier for the user who last modified the change of ownership record.';
COMMENT ON COLUMN application.change_ownership_customers.modified_date IS 'Date and time when the change of ownership record was last modified.';
COMMENT ON COLUMN application.change_ownership_customers.is_deleted IS 'Flag indicating whether this change of ownership record is soft deleted or not.';
COMMENT ON COLUMN application.change_ownership_customers.customer_id IS 'Foreign key referencing the customer involved in the change of ownership.';
COMMENT ON COLUMN application.change_ownership_customers.ownership_percentage IS 'Percentage of ownership.';
COMMENT ON COLUMN application.change_ownership_customers.change_ownership_request_id IS 'Foreign key referencing the associated change of ownership request.';
COMMENT ON COLUMN application.change_ownership_customers.migration_stage IS 'Migration stage indicator.';

-- Table: application.change_ownership_request
COMMENT ON COLUMN application.change_ownership_request.id IS 'Primary key for the change of ownership request.';
COMMENT ON COLUMN application.change_ownership_request.change_owner_ship_type IS 'Type of change of ownership request, (e.g, OWNERSHIP_DOC_TRANSFER, LICENSE_TRANSFER)';
COMMENT ON COLUMN application.change_ownership_request.customer_id IS 'Foreign key referencing the customer initiating the ownership change.';
COMMENT ON COLUMN application.change_ownership_request.percentage_doc_part IS 'Percentage of ownership being transferred.';
COMMENT ON COLUMN application.change_ownership_request.document_transfer_type IS 'Type of document used for transfer, (e.g, SINGLE, SHARED)';
COMMENT ON COLUMN application.change_ownership_request.ownership_transfer_type IS 'Type of ownership transfer, (e.g, COMPLETE_DOC, PART_OF_DOC)';
COMMENT ON COLUMN application.change_ownership_request.participants_count IS 'Number of participants involved in the ownership change.';
COMMENT ON COLUMN application.change_ownership_request.mm5_document_id IS 'Foreign key referencing the MM5 document associated with the ownership change.';
COMMENT ON COLUMN application.change_ownership_request.poa_document_id IS 'Foreign key referencing the Power of Attorney document associated with the ownership change.';
COMMENT ON COLUMN application.change_ownership_request.support_document_id IS 'Foreign key referencing the support document associated with the ownership change.';
COMMENT ON COLUMN application.change_ownership_request.waive_document_id IS 'Foreign key referencing the waiver document associated with the ownership change.';
COMMENT ON COLUMN application.change_ownership_request.agency_request_number IS 'Agency request number.';
COMMENT ON COLUMN application.change_ownership_request.applicant_type IS 'Type of applicant initiating the ownership change, (e.g, NEW_OWNER, AGENT_OF_NEW_OWNER)';
COMMENT ON COLUMN application.change_ownership_request.notes IS 'Additional notes.';
COMMENT ON COLUMN application.change_ownership_request.old_owner_id IS 'Foreign key referencing the previous owner involved in the ownership change.';
COMMENT ON COLUMN application.change_ownership_request.migration_stage IS 'Migration stage indicator.';

-- Table: application.change_ownership_request_documents
COMMENT ON COLUMN application.change_ownership_request_documents.change_ownership_request_id IS 'Foreign key referencing the change of ownership request.';
COMMENT ON COLUMN application.change_ownership_request_documents.document_id IS 'Foreign key referencing the document associated with the change of ownership request.';

-- Table: application.change_ownership_request_licenses_waive_documents
COMMENT ON COLUMN application.change_ownership_request_licenses_waive_documents.change_ownership_request_id IS 'Foreign key referencing the change of ownership request.';
COMMENT ON COLUMN application.change_ownership_request_licenses_waive_documents.document_id IS 'Foreign key referencing the document associated with the waiver in the change of ownership request.';

-- Table: application.classifications
COMMENT ON COLUMN application.classifications.id IS 'Primary key for the classification.';
COMMENT ON COLUMN application.classifications.created_by_user IS 'Identifier for the user who created the classification.';
COMMENT ON COLUMN application.classifications.created_date IS 'Date and time when the classification was created.';
COMMENT ON COLUMN application.classifications.modified_by_user IS 'Identifier for the user who last modified the classification.';
COMMENT ON COLUMN application.classifications.modified_date IS 'Date and time when the classification was last modified.';
COMMENT ON COLUMN application.classifications.is_deleted IS 'Flag indicating whether this classification is soft deleted or not.';
COMMENT ON COLUMN application.classifications.code IS 'Code for the classification.';
COMMENT ON COLUMN application.classifications.enabled IS 'Flag indicating whether the classification is enabled.';
COMMENT ON COLUMN application.classifications.name_ar IS 'Arabic name of the classification.';
COMMENT ON COLUMN application.classifications.name_en IS 'English name of the classification.';
COMMENT ON COLUMN application.classifications.nice_version IS 'Version of the NICE classification.';
COMMENT ON COLUMN application.classifications.description_ar IS 'Arabic description of the classification.';
COMMENT ON COLUMN application.classifications.description_en IS 'English description of the classification.';
COMMENT ON COLUMN application.classifications.version_id IS 'Foreign key referencing the version of the classification.';
COMMENT ON COLUMN application.classifications.category_id IS 'Foreign key referencing the category of the classification.';
COMMENT ON COLUMN application.classifications.notes_ar IS 'Arabic notes for the classification.';
COMMENT ON COLUMN application.classifications.notes_en IS 'English notes for the classification.';
COMMENT ON COLUMN application.classifications.unit_id IS 'Foreign key referencing the unit associated with the classification.';

-- Table: application.customer_ext_classify
COMMENT ON COLUMN application.customer_ext_classify.id IS 'Primary key for the customer extension classification.';
COMMENT ON COLUMN application.customer_ext_classify.created_by_user IS 'Identifier for the user who created the customer extension classification.';
COMMENT ON COLUMN application.customer_ext_classify.created_date IS 'Date and time when the customer extension classification was created.';
COMMENT ON COLUMN application.customer_ext_classify.modified_by_user IS 'Identifier for the user who last modified the customer extension classification.';
COMMENT ON COLUMN application.customer_ext_classify.modified_date IS 'Date and time when the customer extension classification was last modified.';
COMMENT ON COLUMN application.customer_ext_classify.is_deleted IS 'Flag indicating whether this customer extension classification is soft deleted or not.';
COMMENT ON COLUMN application.customer_ext_classify.customer_id IS 'Foreign key referencing the customer associated with the extension classification.';
COMMENT ON COLUMN application.customer_ext_classify.duration IS 'Duration of the classification.';
COMMENT ON COLUMN application.customer_ext_classify.notes IS 'Additional notes for the classification.';
COMMENT ON COLUMN application.customer_ext_classify.application_id IS 'Foreign key referencing the application associated with the extension classification.';
COMMENT ON COLUMN application.customer_ext_classify.customer_ext_classify_type IS 'Type of customer extension classification, (e.g, AGENT , INSTITUTION)';
COMMENT ON COLUMN application.customer_ext_classify.duration_days IS 'Duration in days for the classification.';

-- Table: application.customer_ext_classify_comments
COMMENT ON COLUMN application.customer_ext_classify_comments.id IS 'Primary key for the customer extension classification comments.';
COMMENT ON COLUMN application.customer_ext_classify_comments.created_by_user IS 'Identifier for the user who created the comment.';
COMMENT ON COLUMN application.customer_ext_classify_comments.created_date IS 'Date and time when the comment was created.';
COMMENT ON COLUMN application.customer_ext_classify_comments.modified_by_user IS 'Identifier for the user who last modified the comment.';
COMMENT ON COLUMN application.customer_ext_classify_comments.modified_date IS 'Date and time when the comment was last modified.';
COMMENT ON COLUMN application.customer_ext_classify_comments.is_deleted IS 'Flag indicating whether this comment is soft deleted or not.';
COMMENT ON COLUMN application.customer_ext_classify_comments.comment IS 'Comment text.';
COMMENT ON COLUMN application.customer_ext_classify_comments.comment_date IS 'Date of the comment.';
COMMENT ON COLUMN application.customer_ext_classify_comments.commenter_name IS 'Name of the commenter.';
COMMENT ON COLUMN application.customer_ext_classify_comments.commenter_type IS 'Type of the commenter, (e.g, EXTERNAL_CUSTOMER , INTERNAL_EMPLOYEE)';
COMMENT ON COLUMN application.customer_ext_classify_comments.cust_ext_classify_id IS 'Foreign key referencing the customer extension classification associated with this comment.';
COMMENT ON COLUMN application.customer_ext_classify_comments.cust_ext_classify_parent_comment_id IS 'Parent comment identifier.';

-- Table: application.design_sample
COMMENT ON COLUMN application.design_sample.id IS 'Primary key for the design sample.';
COMMENT ON COLUMN application.design_sample.created_by_user IS 'Identifier for the user who created the design sample.';
COMMENT ON COLUMN application.design_sample.created_date IS 'Date and time when the design sample was created.';
COMMENT ON COLUMN application.design_sample.modified_by_user IS 'Identifier for the user who last modified the design sample.';
COMMENT ON COLUMN application.design_sample.modified_date IS 'Date and time when the design sample was last modified.';
COMMENT ON COLUMN application.design_sample.is_deleted IS 'Flag indicating whether this design sample is soft deleted or not.';
COMMENT ON COLUMN application.design_sample.name IS 'Name of the design sample.';
COMMENT ON COLUMN application.design_sample.industrial_design_id IS 'Foreign key referencing the industrial design associated with the design sample.';
COMMENT ON COLUMN application.design_sample.description IS 'Description of the design sample.';

-- Table: application.design_sample_drawings
COMMENT ON COLUMN application.design_sample_drawings.id IS 'Primary key for the design sample drawing.';
COMMENT ON COLUMN application.design_sample_drawings.created_by_user IS 'Identifier for the user who created the design sample drawing.';
COMMENT ON COLUMN application.design_sample_drawings.created_date IS 'Date and time when the design sample drawing was created.';
COMMENT ON COLUMN application.design_sample_drawings.modified_by_user IS 'Identifier for the user who last modified the design sample drawing.';
COMMENT ON COLUMN application.design_sample_drawings.modified_date IS 'Date and time when the design sample drawing was last modified.';
COMMENT ON COLUMN application.design_sample_drawings.is_deleted IS 'Flag indicating whether this design sample drawing is soft deleted or not.';
COMMENT ON COLUMN application.design_sample_drawings.doc_id IS 'Foreign key referencing the document associated with the design sample drawing.';
COMMENT ON COLUMN application.design_sample_drawings.main IS 'Flag indicating if this is the main drawing.';
COMMENT ON COLUMN application.design_sample_drawings.design_sample_id IS 'Foreign key referencing the design sample associated with this drawing.';
COMMENT ON COLUMN application.design_sample_drawings.shape_id IS 'Foreign key referencing the shape associated with this drawing.';
COMMENT ON COLUMN application.design_sample_drawings.doc3d IS 'Flag indicating if the drawing is 3D.';

-- Table: application.design_sample_relevants
COMMENT ON COLUMN application.design_sample_relevants.design_sample_id IS 'Foreign key referencing the design sample associated with the relevant application type.';
COMMENT ON COLUMN application.design_sample_relevants.application_relevant_type_id IS 'Foreign key referencing the application relevant type associated with the design sample.';

-- Table: application.design_sample_sub_classifications
COMMENT ON COLUMN application.design_sample_sub_classifications.design_sample_id IS 'Foreign key referencing the design sample associated with the sub-classification.';
COMMENT ON COLUMN application.design_sample_sub_classifications.sub_classification_id IS 'Foreign key referencing the sub-classification associated with the design sample.';

-- Table: application.document_comment_attaches
COMMENT ON COLUMN application.document_comment_attaches.document_comment_id IS 'Foreign key referencing the document comment associated with the attached document.';
COMMENT ON COLUMN application.document_comment_attaches.document_id IS 'Foreign key referencing the document attached to the document comment.';

-- Table: application.documents
COMMENT ON COLUMN application.documents.id IS 'Primary key for the document.';
COMMENT ON COLUMN application.documents.created_by_user IS 'Identifier for the user who created the document.';
COMMENT ON COLUMN application.documents.created_date IS 'Date and time when the document was created.';
COMMENT ON COLUMN application.documents.modified_by_user IS 'Identifier for the user who last modified the document.';
COMMENT ON COLUMN application.documents.modified_date IS 'Date and time when the document was last modified.';
COMMENT ON COLUMN application.documents.is_deleted IS 'Flag indicating whether this document is soft deleted or not.';
COMMENT ON COLUMN application.documents.file_name IS 'Name of the uploaded file.';
COMMENT ON COLUMN application.documents.nexuo_id IS 'Nexuo ID associated with the document.';
COMMENT ON COLUMN application.documents.uploaded_date IS 'Date and time when the document was uploaded.';
COMMENT ON COLUMN application.documents.application_id IS 'Foreign key referencing the application associated with the document.';
COMMENT ON COLUMN application.documents.document_type_id IS 'Foreign key referencing the document type.';
COMMENT ON COLUMN application.documents.lk_nexuo_user_id IS 'Foreign key referencing the Nexuo user associated with the document.';
COMMENT ON COLUMN application.documents.document_pages IS 'Number of pages in the document.';
COMMENT ON COLUMN application.documents.migration_stage IS 'Indicator for the migration stage of the document.';

-- Table: application.documents_template
COMMENT ON COLUMN application.documents_template.id IS 'Primary key for the document template.';
COMMENT ON COLUMN application.documents_template.created_by_user IS 'Identifier for the user who created the document template.';
COMMENT ON COLUMN application.documents_template.created_date IS 'Date and time when the document template was created.';
COMMENT ON COLUMN application.documents_template.modified_by_user IS 'Identifier for the user who last modified the document template.';
COMMENT ON COLUMN application.documents_template.modified_date IS 'Date and time when the document template was last modified.';
COMMENT ON COLUMN application.documents_template.is_deleted IS 'Flag indicating whether this document template is soft deleted or not.';
COMMENT ON COLUMN application.documents_template.file_name IS 'Name of the uploaded file.';
COMMENT ON COLUMN application.documents_template.label_name_ar IS 'Arabic label name for the document template.';
COMMENT ON COLUMN application.documents_template.label_name_en IS 'English label name for the document template.';
COMMENT ON COLUMN application.documents_template.nexuo_id IS 'Nexuo ID associated with the document template.';
COMMENT ON COLUMN application.documents_template.uploaded_date IS 'Date and time when the document template was uploaded.';
COMMENT ON COLUMN application.documents_template.category_id IS 'Foreign key referencing the category associated with the document template.';
COMMENT ON COLUMN application.documents_template.lk_nexuo_user_id IS 'Foreign key referencing the Nexuo user associated with the document template.';
COMMENT ON COLUMN application.documents_template.size IS 'Size of the document template.';
COMMENT ON COLUMN application.documents_template.migration_stage IS 'Indicator for the migration stage of the document template.';

-- Table: application.eviction_request
COMMENT ON COLUMN application.eviction_request.id IS 'Primary key for the eviction request.';
COMMENT ON COLUMN application.eviction_request.comment IS 'Comment associated with the eviction request.';
COMMENT ON COLUMN application.eviction_request.desc_document_id IS 'Foreign key referencing the description document associated with the eviction request.';
COMMENT ON COLUMN application.eviction_request.eviction_document_id IS 'Foreign key referencing the eviction document associated with the eviction request.';

-- Table: application.examiner_consultation_comments
COMMENT ON COLUMN application.examiner_consultation_comments.id IS 'Primary key for the examiner consultation comments.';
COMMENT ON COLUMN application.examiner_consultation_comments.created_by_user IS 'Identifier for the user who created the examiner consultation comment.';
COMMENT ON COLUMN application.examiner_consultation_comments.created_date IS 'Date and time when the examiner consultation comment was created.';
COMMENT ON COLUMN application.examiner_consultation_comments.modified_by_user IS 'Identifier for the user who last modified the examiner consultation comment.';
COMMENT ON COLUMN application.examiner_consultation_comments.modified_date IS 'Date and time when the examiner consultation comment was last modified.';
COMMENT ON COLUMN application.examiner_consultation_comments.is_deleted IS 'Flag indicating whether this examiner consultation comment is soft deleted or not.';
COMMENT ON COLUMN application.examiner_consultation_comments.comment IS 'Examiner consultation comment.';
COMMENT ON COLUMN application.examiner_consultation_comments.examiner_type IS 'Type of examiner associated with the consultation comment, (e.g, SENDER, RECEIVER)';
COMMENT ON COLUMN application.examiner_consultation_comments.consultation_id IS 'Foreign key referencing the examiner consultation associated with the comment.';

-- Table: application.examiner_consultations
COMMENT ON COLUMN application.examiner_consultations.id IS 'Primary key for the examiner consultations.';
COMMENT ON COLUMN application.examiner_consultations.created_by_user IS 'Identifier for the user who created the examiner consultation.';
COMMENT ON COLUMN application.examiner_consultations.created_date IS 'Date and time when the examiner consultation was created.';
COMMENT ON COLUMN application.examiner_consultations.modified_by_user IS 'Identifier for the user who last modified the examiner consultation.';
COMMENT ON COLUMN application.examiner_consultations.modified_date IS 'Date and time when the examiner consultation was last modified.';
COMMENT ON COLUMN application.examiner_consultations.is_deleted IS 'Flag indicating whether this examiner consultation is soft deleted or not.';
COMMENT ON COLUMN application.examiner_consultations.replayed IS 'Flag indicating whether the consultation has been replied to.';
COMMENT ON COLUMN application.examiner_consultations.user_name_receiver IS 'Username of the receiver associated with the consultation.';
COMMENT ON COLUMN application.examiner_consultations.user_name_sender IS 'Username of the sender associated with the consultation.';
COMMENT ON COLUMN application.examiner_consultations.receiver_document_id IS 'Foreign key referencing the document associated with the receiver.';
COMMENT ON COLUMN application.examiner_consultations.application_id IS 'Foreign key referencing the application associated with the consultation.';
COMMENT ON COLUMN application.examiner_consultations.sender_document_id IS 'Foreign key referencing the document associated with the sender.';

-- Table: application.extension_request
COMMENT ON COLUMN application.extension_request.id IS 'Primary key for the extension request.';
COMMENT ON COLUMN application.extension_request.lk_support_service_type_id IS 'Foreign key referencing the support service type associated with the extension request.';

-- Table: application.industrial_design_details
COMMENT ON COLUMN application.industrial_design_details.id IS 'Primary key for industrial design details.';
COMMENT ON COLUMN application.industrial_design_details.created_by_user IS 'User who created the industrial design details.';
COMMENT ON COLUMN application.industrial_design_details.created_date IS 'Date when the industrial design details were created.';
COMMENT ON COLUMN application.industrial_design_details.modified_by_user IS 'User who last modified the industrial design details.';
COMMENT ON COLUMN application.industrial_design_details.modified_date IS 'Date when the industrial design details were last modified.';
COMMENT ON COLUMN application.industrial_design_details.is_deleted IS 'Flag indicating if the industrial design details are deleted.';
COMMENT ON COLUMN application.industrial_design_details.application_id IS 'Foreign key referencing the associated application.';
COMMENT ON COLUMN application.industrial_design_details.exhibition_date IS 'Date of the exhibition related to the industrial design.';
COMMENT ON COLUMN application.industrial_design_details.exhibition_info IS 'Information about the exhibition related to the industrial design.';
COMMENT ON COLUMN application.industrial_design_details.explanation_ar IS 'Arabic explanation or description of the industrial design.';
COMMENT ON COLUMN application.industrial_design_details.explanation_en IS 'English explanation or description of the industrial design.';
COMMENT ON COLUMN application.industrial_design_details.have_exhibition IS 'Flag indicating if there is an exhibition related to the industrial design.';
COMMENT ON COLUMN application.industrial_design_details.request_type IS 'Type of request related to the industrial design, (e.g, MULTI, SINGLE)';
COMMENT ON COLUMN application.industrial_design_details.usage_ar IS 'Arabic usage information of the industrial design.';
COMMENT ON COLUMN application.industrial_design_details.usage_en IS 'English usage information of the industrial design.';
COMMENT ON COLUMN application.industrial_design_details.have_revealed_to_public IS 'Flag indicating if the industrial design has been revealed to the public.';
COMMENT ON COLUMN application.industrial_design_details.via_myself IS 'Flag indicating if the industrial design was revealed by oneself.';
COMMENT ON COLUMN application.industrial_design_details.detection_date IS 'Date of detection related to the industrial design.';
COMMENT ON COLUMN application.industrial_design_details.migration_stage IS 'Migration stage of the industrial design details.';

-- Table: application.initial_modification_request
COMMENT ON COLUMN application.initial_modification_request.id IS 'Primary key for the initial modification request.';
COMMENT ON COLUMN application.initial_modification_request.lk_support_service_type_id IS 'Foreign key referencing the support service type associated with the initial modification request.';
COMMENT ON COLUMN application.initial_modification_request.migration_stage IS 'Migration stage of the initial modification request.';

-- Table: application.integrated_circuits
COMMENT ON COLUMN application.integrated_circuits.id IS 'Primary key for the integrated circuits.';
COMMENT ON COLUMN application.integrated_circuits.application_id IS 'Foreign key referencing the application associated with the integrated circuits.';
COMMENT ON COLUMN application.integrated_circuits.design_description IS 'Description of the integrated circuit design.';
COMMENT ON COLUMN application.integrated_circuits.design_date IS 'Date of the integrated circuit design.';
COMMENT ON COLUMN application.integrated_circuits.is_commercial_exploited IS 'Flag indicating whether the integrated circuit design is commercially exploited.';
COMMENT ON COLUMN application.integrated_circuits.commercial_exploitation_date IS 'Date of commercial exploitation of the integrated circuit design.';
COMMENT ON COLUMN application.integrated_circuits.country_id IS 'Foreign key referencing the country associated with the integrated circuits.';

--COMMENT ON COLUMN application.integrated_circuits.commercial_exploitation_document_id IS 'Foreign key referencing the document associated with the commercial exploitation of the integrated circuit design.';
COMMENT ON COLUMN application.integrated_circuits.notify_checker IS 'Flag indicating whether to notify the checker.';
COMMENT ON COLUMN application.integrated_circuits.is_deleted IS 'Flag indicating whether these integrated circuits are soft deleted or not.';
COMMENT ON COLUMN application.integrated_circuits.created_by_user IS 'Identifier for the user who created the integrated circuits.';
COMMENT ON COLUMN application.integrated_circuits.created_date IS 'Date and time when the integrated circuits were created.';
COMMENT ON COLUMN application.integrated_circuits.modified_by_user IS 'Identifier for the user who last modified the integrated circuits.';
COMMENT ON COLUMN application.integrated_circuits.modified_date IS 'Date and time when the integrated circuits were last modified.';

-- Table: application.licence_request
COMMENT ON COLUMN application.licence_request.id IS 'Primary key for the license request.';
COMMENT ON COLUMN application.licence_request.customer_id IS 'Foreign key referencing the customer associated with the license request.';
COMMENT ON COLUMN application.licence_request.licence_purpose IS 'Purpose of the license, (e.g, GOVERNMENT_USE, PRIVATE_SECTOR_USE, EDIT_LICENCE_COVER, EDIT_LICENCE_PERIOD)';
COMMENT ON COLUMN application.licence_request.licence_type_enum IS 'Type of license, (e.g, CONTRACTUAL, MANDATORY, EDIT_LICENCE, CANCEL_LICENCE)';
COMMENT ON COLUMN application.licence_request.support_document_id IS 'Foreign key referencing the support document associated with the license request.';
COMMENT ON COLUMN application.licence_request.poa_document_id IS 'Foreign key referencing the power of attorney document associated with the license request.';
COMMENT ON COLUMN application.licence_request.updated_contract_document_id IS 'Foreign key referencing the updated contract document associated with the license request.';
COMMENT ON COLUMN application.licence_request.compulsory_license_document_id IS 'Foreign key referencing the compulsory license document associated with the license request.';
COMMENT ON COLUMN application.licence_request.applicant_type IS 'Type of applicant, (e.g, OWNER, OWNERS_AGENT, LICENSED_CUSTOMER, LICENSED_CUSTOMER_AGENT)';
COMMENT ON COLUMN application.licence_request.from_date IS 'Start date of the license.';
COMMENT ON COLUMN application.licence_request.to_date IS 'End date of the license.';
COMMENT ON COLUMN application.licence_request.notes IS 'Additional notes for the license request.';
COMMENT ON COLUMN application.licence_request.agency_request_number IS 'Agency request number associated with the license request.';
COMMENT ON COLUMN application.licence_request.canceled_contract_document_id IS 'Foreign key referencing the canceled contract document associated with the license request.';
COMMENT ON COLUMN application.licence_request.licence_validity_number IS 'Validity number of the license.';
COMMENT ON COLUMN application.licence_request.licence_type_id IS 'Foreign key referencing the license type associated with the license request.';
COMMENT ON COLUMN application.licence_request.licence_purpose_id IS 'Foreign key referencing the license purpose associated with the license request.';
COMMENT ON COLUMN application.licence_request.migration_stage IS 'Migration stage of the license request.';

-- Table: application.licence_request_documents
COMMENT ON COLUMN application.licence_request_documents.licence_request_id IS 'Foreign key referencing the license request associated with the document.';
COMMENT ON COLUMN application.licence_request_documents.document_id IS 'Foreign key referencing the document associated with the license request.';

-- Table: application.opposition_request
COMMENT ON COLUMN application.opposition_request.id IS 'Primary key for the opposition request.';
COMMENT ON COLUMN application.opposition_request.opposition_reason IS 'Reason for the opposition.';
COMMENT ON COLUMN application.opposition_request.application_owner_reply IS 'Reply from the application owner.';
COMMENT ON COLUMN application.opposition_request.complainer_session_date IS 'Date of complainer session.';
COMMENT ON COLUMN application.opposition_request.complainer_session_time IS 'Time of complainer session.';
COMMENT ON COLUMN application.opposition_request.complainer_session_is_paid IS 'Flag indicating whether the complainer session is paid or not.';
COMMENT ON COLUMN application.opposition_request.complainer_session_result IS 'Result of complainer session.';
COMMENT ON COLUMN application.opposition_request.complainer_session_slot_id IS 'Slot ID for complainer session.';
COMMENT ON COLUMN application.opposition_request.application_owner_session_date IS 'Date of application owner session.';
COMMENT ON COLUMN application.opposition_request.application_owner_session_time IS 'Time of application owner session.';
COMMENT ON COLUMN application.opposition_request.application_owner_is_session_paid IS 'Flag indicating whether the application owner session is paid or not.';
COMMENT ON COLUMN application.opposition_request.application_owner_session_result IS 'Result of application owner session.';
COMMENT ON COLUMN application.opposition_request.application_owner_slot_id IS 'Slot ID for application owner session.';
COMMENT ON COLUMN application.opposition_request.migration_stage IS 'Migration stage of the opposition request.';

-- Table: application.opposition_request_documents
COMMENT ON COLUMN application.opposition_request_documents.opposition_request_id IS 'Foreign key referencing the opposition request associated with the document.';
COMMENT ON COLUMN application.opposition_request_documents.document_id IS 'Foreign key referencing the document associated with the opposition request.';

-- Table: application.opposition_revoke_licence_request
COMMENT ON COLUMN application.opposition_revoke_licence_request.id IS 'Primary key for the opposition revoke licence request.';
COMMENT ON COLUMN application.opposition_revoke_licence_request.revoke_licence_request_id IS 'Foreign key referencing the revoke licence request associated with the opposition.';
COMMENT ON COLUMN application.opposition_revoke_licence_request.objection_reason IS 'Reason for the objection.';
COMMENT ON COLUMN application.opposition_revoke_licence_request.court_document_notes IS 'Notes on the court document.';

-- Table: application.opposition_revoke_licence_request_documents
COMMENT ON COLUMN application.opposition_revoke_licence_request_documents.opposition_revoke_licence_request_id IS 'Foreign key referencing the opposition revoke licence request associated with the document.';
COMMENT ON COLUMN application.opposition_revoke_licence_request_documents.document_id IS 'Foreign key referencing the document associated with the opposition revoke licence request.';

-- Table: application.pct
COMMENT ON COLUMN application.pct.id IS 'Primary key for the PCT application.';
COMMENT ON COLUMN application.pct.created_by_user IS 'User who created the PCT application.';
COMMENT ON COLUMN application.pct.created_date IS 'Date when the PCT application was created.';
COMMENT ON COLUMN application.pct.modified_by_user IS 'User who last modified the PCT application.';
COMMENT ON COLUMN application.pct.modified_date IS 'Date when the PCT application was last modified.';
COMMENT ON COLUMN application.pct.is_deleted IS 'Flag indicating whether the PCT application is deleted or not.';
COMMENT ON COLUMN application.pct.application_id IS 'Foreign key referencing the associated application.';
COMMENT ON COLUMN application.pct.filing_date_gr IS 'Filing date for the PCT application.';
COMMENT ON COLUMN application.pct.pct_application_no IS 'PCT application number.';
COMMENT ON COLUMN application.pct.publish_no IS 'Publication number for the PCT application.';
COMMENT ON COLUMN application.pct.wipo_url IS 'URL link for the PCT application on WIPO.';
COMMENT ON COLUMN application.pct.patent_id IS 'Foreign key referencing the associated patent.';
COMMENT ON COLUMN application.pct.international_publication_date IS 'Publication date for the PCT application.';
COMMENT ON COLUMN application.pct.petition_number IS 'Petition number for the PCT application.';
COMMENT ON COLUMN application.pct.pct_copy_document_id IS 'Foreign key referencing the copy document for the PCT application.';
COMMENT ON COLUMN application.pct.migration_stage IS 'Migration stage of the PCT application.';

-- Table: application.petition_recovery_request
COMMENT ON COLUMN application.petition_recovery_request.id IS 'Primary key for the petition recovery request.';
COMMENT ON COLUMN application.petition_recovery_request.eviction_document_id IS 'Foreign key referencing the eviction document associated with the petition recovery request.';
COMMENT ON COLUMN application.petition_recovery_request.lk_support_service_type_id IS 'Foreign key referencing the support service type for the petition recovery request.';
COMMENT ON COLUMN application.petition_recovery_request.recovery_document_id IS 'Foreign key referencing the recovery document associated with the petition recovery request.';
COMMENT ON COLUMN application.petition_recovery_request.justification IS 'Justification or reason for the petition recovery request.';

-- Table: application.petition_recovery_request_documents
COMMENT ON COLUMN application.petition_recovery_request_documents.petion_recovery_request_id IS 'Foreign key referencing the petition recovery request associated with the document.';
COMMENT ON COLUMN application.petition_recovery_request_documents.document_id IS 'Foreign key referencing the document associated with the petition recovery request.';

-- Table: application.petition_request_national_stage
COMMENT ON COLUMN application.petition_request_national_stage.id IS 'Primary key for the petition request at national stage.';
COMMENT ON COLUMN application.petition_request_national_stage.global_application_number IS 'Global application number for the petition request.';
COMMENT ON COLUMN application.petition_request_national_stage.reason IS 'Reason for the petition request at national stage.';

-- Table: application.petition_request_national_stage_documents
COMMENT ON COLUMN application.petition_request_national_stage_documents.petition_request_national_stage_id IS 'Foreign key referencing the petition request at national stage associated with the document.';
COMMENT ON COLUMN application.petition_request_national_stage_documents.document_id IS 'Foreign key referencing the document associated with the petition request at national stage.';

-- Table: application.protection_elements
COMMENT ON COLUMN application.protection_elements.id IS 'Primary key for protection elements.';
COMMENT ON COLUMN application.protection_elements.created_by_user IS 'User who created the protection element.';
COMMENT ON COLUMN application.protection_elements.created_date IS 'Date when the protection element was created.';
COMMENT ON COLUMN application.protection_elements.modified_by_user IS 'User who last modified the protection element.';
COMMENT ON COLUMN application.protection_elements.modified_date IS 'Date when the protection element was last modified.';
COMMENT ON COLUMN application.protection_elements.is_deleted IS 'Flag indicating whether the protection element is deleted or not.';
COMMENT ON COLUMN application.protection_elements.description IS 'Description of the protection element.';
COMMENT ON COLUMN application.protection_elements.application_id IS 'Foreign key referencing the associated application.';
COMMENT ON COLUMN application.protection_elements.parent_id IS 'Parent ID of the protection element.';
COMMENT ON COLUMN application.protection_elements.is_english IS 'Flag indicating whether the description is in English.';
COMMENT ON COLUMN application.protection_elements.document_id IS 'Foreign key referencing the document associated with the protection element.';
COMMENT ON COLUMN application.protection_elements.migration_stage IS 'Migration stage of the protection element.';

-- Table: application.protection_extend_request
COMMENT ON COLUMN application.protection_extend_request.id IS 'Primary key for protection extension request.';
COMMENT ON COLUMN application.protection_extend_request.claim_count IS 'Count of claims for the protection extension request.';
COMMENT ON COLUMN application.protection_extend_request.claim_number IS 'Claim number for the protection extension request.';
COMMENT ON COLUMN application.protection_extend_request.protection_extend_type IS 'Type of protection extension request, (e.g, SAIP_EXAMN_DELY, BIO_CHEMIST)';
COMMENT ON COLUMN application.protection_extend_request.poa_document_id IS 'Foreign key referencing the power of attorney document associated with the protection extension request.';
COMMENT ON COLUMN application.protection_extend_request.support_document_id IS 'Foreign key referencing the support document associated with the protection extension request.';
COMMENT ON COLUMN application.protection_extend_request.waive_document_id IS 'Foreign key referencing the waive document associated with the protection extension request.';

-- Table: application.publication_issue
COMMENT ON COLUMN application.publication_issue.id IS 'Primary key for publication issue.';
COMMENT ON COLUMN application.publication_issue.is_deleted IS 'Flag indicating whether the publication issue is deleted or not.';
COMMENT ON COLUMN application.publication_issue.created_date IS 'Date when the publication issue was created.';
COMMENT ON COLUMN application.publication_issue.created_by_user IS 'User who created the publication issue.';
COMMENT ON COLUMN application.publication_issue.modified_date IS 'Date when the publication issue was last modified.';
COMMENT ON COLUMN application.publication_issue.modified_by_user IS 'User who last modified the publication issue.';
COMMENT ON COLUMN application.publication_issue.issue_number IS 'Issue number of the publication.';
COMMENT ON COLUMN application.publication_issue.issuing_date IS 'Issuing date of the publication.';
COMMENT ON COLUMN application.publication_issue.name_en IS 'Name of the publication in English.';
COMMENT ON COLUMN application.publication_issue.name_ar IS 'Name of the publication in Arabic.';
COMMENT ON COLUMN application.publication_issue.lk_application_category_id IS 'Foreign key referencing the application category.';
COMMENT ON COLUMN application.publication_issue.issuing_date_hijri IS 'Hijri issuing date of the publication.';
COMMENT ON COLUMN application.publication_issue.lk_publication_issue_status_id IS 'Foreign key referencing the publication issue status.';
COMMENT ON COLUMN application.publication_issue.migration_stage IS 'Migration stage of the publication issue.';

-- Table: application.publication_issue_application_publication
COMMENT ON COLUMN application.publication_issue_application_publication.id IS 'Primary key for the relationship between publication issue and application publication.';
COMMENT ON COLUMN application.publication_issue_application_publication.is_deleted IS 'Flag indicating whether the relationship is deleted or not.';
COMMENT ON COLUMN application.publication_issue_application_publication.created_date IS 'Date when the relationship was created.';
COMMENT ON COLUMN application.publication_issue_application_publication.created_by_user IS 'User who created the relationship.';
COMMENT ON COLUMN application.publication_issue_application_publication.modified_date IS 'Date when the relationship was last modified.';
COMMENT ON COLUMN application.publication_issue_application_publication.modified_by_user IS 'User who last modified the relationship.';
COMMENT ON COLUMN application.publication_issue_application_publication.publication_issue_id IS 'Foreign key referencing the publication issue.';
COMMENT ON COLUMN application.publication_issue_application_publication.application_publication_id IS 'Foreign key referencing the application publication.';
COMMENT ON COLUMN application.publication_issue_application_publication.migration_stage IS 'Migration stage of the publication issue-application publication relationship.';

-- Table: application.publication_scheduling_config
COMMENT ON COLUMN application.publication_scheduling_config.id IS 'Primary key for publication scheduling configuration.';
COMMENT ON COLUMN application.publication_scheduling_config.is_deleted IS 'Flag indicating whether the publication scheduling configuration is deleted or not.';
COMMENT ON COLUMN application.publication_scheduling_config.created_date IS 'Date when the publication scheduling configuration was created.';
COMMENT ON COLUMN application.publication_scheduling_config.created_by_user IS 'User who created the publication scheduling configuration.';
COMMENT ON COLUMN application.publication_scheduling_config.modified_date IS 'Date when the publication scheduling configuration was last modified.';
COMMENT ON COLUMN application.publication_scheduling_config.modified_by_user IS 'User who last modified the publication scheduling configuration.';
COMMENT ON COLUMN application.publication_scheduling_config.publication_frequency IS 'Frequency of publication scheduling, (e.g, WEEKLY, MONTHLY)';
COMMENT ON COLUMN application.publication_scheduling_config.application_category_id IS 'Foreign key referencing the application category.';

-- Table: application.publication_time
COMMENT ON COLUMN application.publication_time.id IS 'Primary key for publication time.';
COMMENT ON COLUMN application.publication_time.is_deleted IS 'Flag indicating whether the publication time is deleted or not.';
COMMENT ON COLUMN application.publication_time.created_date IS 'Date when the publication time was created.';
COMMENT ON COLUMN application.publication_time.created_by_user IS 'User who created the publication time.';
COMMENT ON COLUMN application.publication_time.modified_date IS 'Date when the publication time was last modified.';
COMMENT ON COLUMN application.publication_time.modified_by_user IS 'User who last modified the publication time.';
COMMENT ON COLUMN application.publication_time.time IS 'Publication time.';
COMMENT ON COLUMN application.publication_time.day_of_week_id IS 'Foreign key referencing the day of the week.';
COMMENT ON COLUMN application.publication_time.day_of_month IS 'Day of the month for publication.';
COMMENT ON COLUMN application.publication_time.publication_scheduling_config_id IS 'Foreign key referencing the publication scheduling configuration.';

-- Table: application.regions_license_request
COMMENT ON COLUMN application.regions_license_request.licence_request_id IS 'Foreign key referencing the license request.';
COMMENT ON COLUMN application.regions_license_request.region_id IS 'Foreign key referencing the region.';

-- Table: application.retraction_request
COMMENT ON COLUMN application.retraction_request.id IS 'Primary key for retraction request.';
COMMENT ON COLUMN application.retraction_request.retraction_reason_document_id IS 'Foreign key referencing the document containing the retraction reason.';
COMMENT ON COLUMN application.retraction_request.lk_support_service_type_id IS 'Foreign key referencing the support service type.';

-- Table: application.revoke_by_court_order
COMMENT ON COLUMN application.revoke_by_court_order.id IS 'Primary key for revocation by court order.';
COMMENT ON COLUMN application.revoke_by_court_order.notes IS 'Additional notes for the revocation.';
COMMENT ON COLUMN application.revoke_by_court_order.court_number IS 'Court number related to the revocation.';
COMMENT ON COLUMN application.revoke_by_court_order.court_name IS 'Court name related to the revocation.';
COMMENT ON COLUMN application.revoke_by_court_order.ruling_date IS 'Date of the court ruling.';
COMMENT ON COLUMN application.revoke_by_court_order.suspension_duration IS 'Duration of suspension, (e.g, THREE_YEARS, OTHER)';
COMMENT ON COLUMN application.revoke_by_court_order.duration_years IS 'Duration in years for the revocation.';
COMMENT ON COLUMN application.revoke_by_court_order.duration_months IS 'Duration in months for the revocation.';
COMMENT ON COLUMN application.revoke_by_court_order.duration_days IS 'Duration in days for the revocation.';
COMMENT ON COLUMN application.revoke_by_court_order.migration_stage IS 'Migration stage of the revocation by court order.';

-- Table: application.revoke_by_court_order_documents
COMMENT ON COLUMN application.revoke_by_court_order_documents.revoke_by_court_order_id IS 'Foreign key referencing the revocation by court order.';
COMMENT ON COLUMN application.revoke_by_court_order_documents.document_id IS 'Foreign key referencing the document related to the revocation by court order.';

-- Table: application.revoke_licence_request
COMMENT ON COLUMN application.revoke_licence_request.id IS 'Primary key for revoke licence request.';
COMMENT ON COLUMN application.revoke_licence_request.licence_request_id IS 'Foreign key referencing the licence request.';
COMMENT ON COLUMN application.revoke_licence_request.notes IS 'Additional notes for the revoke licence request.';
COMMENT ON COLUMN application.revoke_licence_request.applicant_type IS 'Type of applicant for the revoke licence request, (e.g, OWNER, OWNERS_AGENT, LICENSED_CUSTOMER, LICENSED_CUSTOMER_AGENT)';
COMMENT ON COLUMN application.revoke_licence_request.agency_request_number IS 'Agency request number related to the revoke licence request.';
COMMENT ON COLUMN application.revoke_licence_request.migration_stage IS 'Migration stage of the revoke licence request.';

-- Table: application.revoke_licence_request_documents
COMMENT ON COLUMN application.revoke_licence_request_documents.revoke_licence_request_id IS 'Foreign key referencing the revoke licence request.';
COMMENT ON COLUMN application.revoke_licence_request_documents.document_id IS 'Foreign key referencing the document related to the revoke licence request.';

-- Table: application.revoke_products
COMMENT ON COLUMN application.revoke_products.id IS 'Primary key for revoke products.';
COMMENT ON COLUMN application.revoke_products.notes IS 'Additional notes for the revoke products.';
COMMENT ON COLUMN application.revoke_products.migration_stage IS 'Migration stage of the revoke products.';

-- Table: application.revoke_products_documents
COMMENT ON COLUMN application.revoke_products_documents.revoke_products_id IS 'Foreign key referencing the revoke products.';
COMMENT ON COLUMN application.revoke_products_documents.document_id IS 'Foreign key referencing the document related to the revoke products.';

-- Table: application.revoke_products_sub_classifications
COMMENT ON COLUMN application.revoke_products_sub_classifications.sub_classifications_id IS 'Foreign key referencing the sub-classifications.';
COMMENT ON COLUMN application.revoke_products_sub_classifications.revoke_products_id IS 'Foreign key referencing the revoke products.';

-- Table: application.revoke_voluntary
COMMENT ON COLUMN application.revoke_voluntary.id IS 'Primary key for revoke voluntary.';
COMMENT ON COLUMN application.revoke_voluntary.notes IS 'Additional notes for the revoke voluntary.';
COMMENT ON COLUMN application.revoke_voluntary.migration_stage IS 'Migration stage of the revoke voluntary.';

-- Table: application.revoke_voluntary_documents
COMMENT ON COLUMN application.revoke_voluntary_documents.revoke_voluntary_id IS 'Foreign key referencing the revoke voluntary.';
COMMENT ON COLUMN application.revoke_voluntary_documents.document_id IS 'Foreign key referencing the document related to the revoke voluntary.';

-- Table: application.sub_classifications
COMMENT ON COLUMN application.sub_classifications.id IS 'Primary key for sub-classifications.';
COMMENT ON COLUMN application.sub_classifications.created_by_user IS 'User who created the sub-classification.';
COMMENT ON COLUMN application.sub_classifications.created_date IS 'Date when the sub-classification was created.';
COMMENT ON COLUMN application.sub_classifications.modified_by_user IS 'User who last modified the sub-classification.';
COMMENT ON COLUMN application.sub_classifications.modified_date IS 'Date when the sub-classification was last modified.';
COMMENT ON COLUMN application.sub_classifications.is_deleted IS 'Flag indicating if the sub-classification is deleted.';
COMMENT ON COLUMN application.sub_classifications.code IS 'Code for the sub-classification.';
COMMENT ON COLUMN application.sub_classifications.enabled IS 'Flag indicating if the sub-classification is enabled.';
COMMENT ON COLUMN application.sub_classifications.is_shortcut IS 'Flag indicating if the sub-classification is a shortcut.';
COMMENT ON COLUMN application.sub_classifications.is_visible IS 'Flag indicating if the sub-classification is visible.';
COMMENT ON COLUMN application.sub_classifications.name_ar IS 'Arabic name of the sub-classification.';
COMMENT ON COLUMN application.sub_classifications.name_en IS 'English name of the sub-classification.';
COMMENT ON COLUMN application.sub_classifications.nice_version IS 'Version of the sub-classification.';
COMMENT ON COLUMN application.sub_classifications.classification_id IS 'Foreign key referencing the classification.';
COMMENT ON COLUMN application.sub_classifications.description_ar IS 'Arabic description of the sub-classification.';
COMMENT ON COLUMN application.sub_classifications.description_en IS 'English description of the sub-classification.';
COMMENT ON COLUMN application.sub_classifications.basic_number IS 'Basic number for the sub-classification.';
COMMENT ON COLUMN application.sub_classifications.serial_number_ar IS 'Arabic serial number of the sub-classification.';
COMMENT ON COLUMN application.sub_classifications.serial_number_en IS 'English serial number of the sub-classification.';

-- Table: application.sub_exam_report_documents
COMMENT ON COLUMN application.sub_exam_report_documents.sub_exam_report_id IS 'Foreign key referencing the sub-examination report.';
COMMENT ON COLUMN application.sub_exam_report_documents.document_id IS 'Foreign key referencing the document related to the sub-examination report.';

-- Table: application.substantive_examination_reports
COMMENT ON COLUMN application.substantive_examination_reports.id IS 'Primary key for substantive examination reports.';
COMMENT ON COLUMN application.substantive_examination_reports.created_by_user IS 'User who created the substantive examination report.';
COMMENT ON COLUMN application.substantive_examination_reports.created_date IS 'Date when the substantive examination report was created.';
COMMENT ON COLUMN application.substantive_examination_reports.modified_by_user IS 'User who last modified the substantive examination report.';
COMMENT ON COLUMN application.substantive_examination_reports.modified_date IS 'Date when the substantive examination report was last modified.';
COMMENT ON COLUMN application.substantive_examination_reports.is_deleted IS 'Flag indicating if the substantive examination report is deleted.';
COMMENT ON COLUMN application.substantive_examination_reports.examiner_opinion IS 'Opinion of the examiner in the substantive examination report.';
COMMENT ON COLUMN application.substantive_examination_reports.examiner_recommendation IS 'Recommendation of the examiner in the substantive examination report.';
COMMENT ON COLUMN application.substantive_examination_reports.links IS 'Links related to the substantive examination report.';
COMMENT ON COLUMN application.substantive_examination_reports.type IS 'Type of the substantive examination report, (e.g, SUBSTANTIVE_EXAMINER, SUBSTANTIVE_CHECKER, SUBSTANTIVE_AGENT)';
COMMENT ON COLUMN application.substantive_examination_reports.application_info_id IS 'Foreign key referencing the application information.';
COMMENT ON COLUMN application.substantive_examination_reports.document_id IS 'Foreign key referencing the document related to the substantive examination report.';
COMMENT ON COLUMN application.substantive_examination_reports.decision IS 'Decision related to the substantive examination report, (e.g, GRANT, OBJECTION, SEND_BACK, YES, NO, UPDATE, REJECT ,APPROVE)';

-- Table: application.support_service_application_categories
COMMENT ON COLUMN application.support_service_application_categories.support_service_id IS 'Foreign key referencing the support service.';
COMMENT ON COLUMN application.support_service_application_categories.category_id IS 'Foreign key referencing the category for the support service.';

-- Table: application.support_service_customer
COMMENT ON COLUMN application.support_service_customer.id IS 'Primary key for support service customer.';
COMMENT ON COLUMN application.support_service_customer.created_by_user IS 'User who created the support service customer.';
COMMENT ON COLUMN application.support_service_customer.created_date IS 'Date when the support service customer was created.';
COMMENT ON COLUMN application.support_service_customer.modified_by_user IS 'User who last modified the support service customer.';
COMMENT ON COLUMN application.support_service_customer.modified_date IS 'Date when the support service customer was last modified.';
COMMENT ON COLUMN application.support_service_customer.is_deleted IS 'Flag indicating if the support service customer is deleted.';
COMMENT ON COLUMN application.support_service_customer.customer_id IS 'Foreign key referencing the customer.';
COMMENT ON COLUMN application.support_service_customer.customer_code IS 'Customer code for the support service.';
COMMENT ON COLUMN application.support_service_customer.agency_request_id IS 'Foreign key referencing the agency request.';
COMMENT ON COLUMN application.support_service_customer.application_customer_type IS 'Type of the customer application related to the support service, (e.g, AGENT, MAIN_OWNER, SECONDARY_OWNER, APPLICANT, INVENTOR)';
COMMENT ON COLUMN application.support_service_customer.customer_application_access_level IS 'Access level of the customer application related to the support service, (e.g, VIEW_ONLY, FULL_ACCESS)';
COMMENT ON COLUMN application.support_service_customer.support_service_id IS 'Foreign key referencing the support service.';
COMMENT ON COLUMN application.support_service_customer.migration_stage IS 'Migration stage of the support service customer.';

-- Table: application.support_service_reviews
COMMENT ON COLUMN application.support_service_reviews.id IS 'Primary key for support service reviews.';
COMMENT ON COLUMN application.support_service_reviews.created_by_user IS 'User who created the support service review.';
COMMENT ON COLUMN application.support_service_reviews.created_date IS 'Date when the support service review was created.';
COMMENT ON COLUMN application.support_service_reviews.modified_by_user IS 'User who last modified the support service review.';
COMMENT ON COLUMN application.support_service_reviews.modified_date IS 'Date when the support service review was last modified.';
COMMENT ON COLUMN application.support_service_reviews.is_deleted IS 'Flag indicating if the support service review is deleted.';
COMMENT ON COLUMN application.support_service_reviews.review IS 'Review content.';
COMMENT ON COLUMN application.support_service_reviews.review_status IS 'Status of the support service review, (e.g, APPROVED, REJECTED, NEW)';
COMMENT ON COLUMN application.support_service_reviews.application_support_services_type_id IS 'Foreign key referencing the support service type.';
COMMENT ON COLUMN application.support_service_reviews.migration_stage IS 'Migration stage of the support service review.';

-- Table: application.support_service_status_change_log
COMMENT ON COLUMN application.support_service_status_change_log.id IS 'Primary key for support service status change log.';
COMMENT ON COLUMN application.support_service_status_change_log.created_by_user IS 'User who created the support service status change log.';
COMMENT ON COLUMN application.support_service_status_change_log.created_date IS 'Date when the support service status change log was created.';
COMMENT ON COLUMN application.support_service_status_change_log.modified_by_user IS 'User who last modified the support service status change log.';
COMMENT ON COLUMN application.support_service_status_change_log.modified_date IS 'Date when the support service status change log was last modified.';
COMMENT ON COLUMN application.support_service_status_change_log.is_deleted IS 'Flag indicating if the support service status change log is deleted.';
COMMENT ON COLUMN application.support_service_status_change_log.description_code IS 'Code description for the support service status change log, (e.g, PETITION_ACCEPTANCE, PETITION_REJECTION, PETITION_CONDITIONAL_REJECTION)';
COMMENT ON COLUMN application.support_service_status_change_log.task_definition_key IS 'Task definition key for the support service status change log.';
COMMENT ON COLUMN application.support_service_status_change_log.task_instance_id IS 'Task instance ID for the support service status change log.';
COMMENT ON COLUMN application.support_service_status_change_log.new_status_id IS 'New status ID for the support service status change log.';
COMMENT ON COLUMN application.support_service_status_change_log.previous_status_id IS 'Previous status ID for the support service status change log.';
COMMENT ON COLUMN application.support_service_status_change_log.support_service_id IS 'Foreign key referencing the support service.';

-- Table: application.support_service_type_status
COMMENT ON COLUMN application.support_service_type_status.lk_support_service_type_id IS 'Foreign key referencing the support service type.';
COMMENT ON COLUMN application.support_service_type_status.lk_support_service_status_id IS 'Foreign key referencing the support service status.';

-- Table: application.support_service_users
COMMENT ON COLUMN application.support_service_users.id IS 'Primary key for support service users.';
COMMENT ON COLUMN application.support_service_users.created_by_user IS 'User who created the support service user.';
COMMENT ON COLUMN application.support_service_users.created_date IS 'Date when the support service user was created.';
COMMENT ON COLUMN application.support_service_users.modified_by_user IS 'User who last modified the support service user.';
COMMENT ON COLUMN application.support_service_users.modified_date IS 'Date when the support service user was last modified.';
COMMENT ON COLUMN application.support_service_users.is_deleted IS 'Flag indicating if the support service user is deleted.';
COMMENT ON COLUMN application.support_service_users.user_name IS 'Username of the support service user.';
COMMENT ON COLUMN application.support_service_users.user_role IS 'Role of the support service user, (e.g, CHECKER, HEAD_OF_CHECKER, EXAMINER, HEAD_OF_EXAMINER)';
COMMENT ON COLUMN application.support_service_users.application_support_service_type_id IS 'Foreign key referencing the support service type.';

-- Table: application.support_services_type_applications
COMMENT ON COLUMN application.support_services_type_applications.application_support_services_type_id IS 'Foreign key referencing the support service type.';
COMMENT ON COLUMN application.support_services_type_applications.application_id IS 'Foreign key referencing the application.';

-- Table: application.support_services_type_decisions
COMMENT ON COLUMN application.support_services_type_decisions.id IS 'Primary key for support service type decisions.';
COMMENT ON COLUMN application.support_services_type_decisions.created_by_user IS 'User who created the support service type decision.';
COMMENT ON COLUMN application.support_services_type_decisions.created_date IS 'Date when the support service type decision was created.';
COMMENT ON COLUMN application.support_services_type_decisions.modified_by_user IS 'User who last modified the support service type decision.';
COMMENT ON COLUMN application.support_services_type_decisions.modified_date IS 'Date when the support service type decision was last modified.';
COMMENT ON COLUMN application.support_services_type_decisions.is_deleted IS 'Flag indicating if the support service type decision is deleted.';
COMMENT ON COLUMN application.support_services_type_decisions.decision IS 'Decision related to the support service type, (e.g, ACCEPTED, SEND_BACK_DEPARTMENT, SEND_BACK, FORMAL_APPROVAL, OBJECTIVE_APPROVAL)';
COMMENT ON COLUMN application.support_services_type_decisions.support_services_type_id IS 'Foreign key referencing the support service type.';
COMMENT ON COLUMN application.support_services_type_decisions.comment IS 'Comment related to the decision.';
COMMENT ON COLUMN application.support_services_type_decisions.document_id IS 'Foreign key referencing the document.';
COMMENT ON COLUMN application.support_services_type_decisions.to_customers IS 'Flag indicating if the decision is for customers.';
COMMENT ON COLUMN application.support_services_type_decisions.to_role IS 'Role for which the decision is applicable.';

-- Table: application.supporting_evidence
COMMENT ON COLUMN application.supporting_evidence.id IS 'Primary key for supporting evidence.';
COMMENT ON COLUMN application.supporting_evidence.created_date IS 'Date when the supporting evidence was created.';
COMMENT ON COLUMN application.supporting_evidence.is_deleted IS 'Flag indicating if the supporting evidence is deleted.';
COMMENT ON COLUMN application.supporting_evidence.modified_by_user IS 'User who last modified the supporting evidence.';
COMMENT ON COLUMN application.supporting_evidence.created_by_user IS 'User who created the supporting evidence.';
COMMENT ON COLUMN application.supporting_evidence.modified_date IS 'Date when the supporting evidence was last modified.';
COMMENT ON COLUMN application.supporting_evidence.patent_number IS 'Patent number associated with the supporting evidence.';
COMMENT ON COLUMN application.supporting_evidence.address IS 'Address related to the supporting evidence.';
COMMENT ON COLUMN application.supporting_evidence.classification IS 'Classification of the supporting evidence.';
COMMENT ON COLUMN application.supporting_evidence.link IS 'Link to the supporting evidence.';
COMMENT ON COLUMN application.supporting_evidence.document_id IS 'Foreign key referencing the document.';
COMMENT ON COLUMN application.supporting_evidence.is_patent IS 'Flag indicating if the supporting evidence is a patent.';
COMMENT ON COLUMN application.supporting_evidence.substantive_examination_reports_id IS 'Foreign key referencing the substantive examination report.';
COMMENT ON COLUMN application.supporting_evidence.application_info_id IS 'Foreign key referencing the application information.';
COMMENT ON COLUMN application.supporting_evidence.evidence_date IS 'Date of the supporting evidence.';

-- Table: application.task_eqm
COMMENT ON COLUMN application.task_eqm.id IS 'Primary key for task EQM.';
COMMENT ON COLUMN application.task_eqm.created_by_user IS 'User who created the task EQM.';
COMMENT ON COLUMN application.task_eqm.created_date IS 'Date when the task EQM was created.';
COMMENT ON COLUMN application.task_eqm.modified_by_user IS 'User who last modified the task EQM.';
COMMENT ON COLUMN application.task_eqm.modified_date IS 'Date when the task EQM was last modified.';
COMMENT ON COLUMN application.task_eqm.is_deleted IS 'Flag indicating if the task EQM is deleted.';
COMMENT ON COLUMN application.task_eqm.average IS 'Average value associated with the task EQM.';
COMMENT ON COLUMN application.task_eqm.comments IS 'Comments related to the task EQM.';
COMMENT ON COLUMN application.task_eqm.is_enough IS 'Flag indicating if the task EQM is considered enough.';
COMMENT ON COLUMN application.task_eqm.task_id IS 'Foreign key referencing the task.';
COMMENT ON COLUMN application.task_eqm.application_info_id IS 'Foreign key referencing the application information.';
COMMENT ON COLUMN application.task_eqm.lk_task_eqm_status_id IS 'Foreign key referencing the task EQM status.';
COMMENT ON COLUMN application.task_eqm.task_key IS 'Key associated with the task.';
COMMENT ON COLUMN application.task_eqm.service_id IS 'Foreign key referencing the service.';
COMMENT ON COLUMN application.task_eqm.lk_task_eqm_type_id IS 'Foreign key referencing the task EQM type.';

-- Table: application.task_eqm_rating_items
COMMENT ON COLUMN application.task_eqm_rating_items.id IS 'Primary key for task EQM rating items.';
COMMENT ON COLUMN application.task_eqm_rating_items.created_by_user IS 'User who created the task EQM rating item.';
COMMENT ON COLUMN application.task_eqm_rating_items.created_date IS 'Date when the task EQM rating item was created.';
COMMENT ON COLUMN application.task_eqm_rating_items.modified_by_user IS 'User who last modified the task EQM rating item.';
COMMENT ON COLUMN application.task_eqm_rating_items.modified_date IS 'Date when the task EQM rating item was last modified.';
COMMENT ON COLUMN application.task_eqm_rating_items.is_deleted IS 'Flag indicating if the task EQM rating item is deleted.';
COMMENT ON COLUMN application.task_eqm_rating_items.value IS 'Value associated with the task EQM rating item.';
COMMENT ON COLUMN application.task_eqm_rating_items.task_eqm_id IS 'Foreign key referencing the task EQM.';
COMMENT ON COLUMN application.task_eqm_rating_items.task_eqm_item_id IS 'Foreign key referencing the task EQM item.';

-- Table: application.task_eqm_type_items
COMMENT ON COLUMN application.task_eqm_type_items.lk_task_eqm_type_id IS 'Foreign key referencing the task EQM type.';
COMMENT ON COLUMN application.task_eqm_type_items.lk_task_eqm_item_id IS 'Foreign key referencing the task EQM item.';

-- Table: application.terms_and_conditions
COMMENT ON COLUMN application.terms_and_conditions.id IS 'Primary key for terms and conditions.';
COMMENT ON COLUMN application.terms_and_conditions.created_by_user IS 'User who created the terms and conditions.';
COMMENT ON COLUMN application.terms_and_conditions.created_date IS 'Date when the terms and conditions were created.';
COMMENT ON COLUMN application.terms_and_conditions.modified_by_user IS 'User who last modified the terms and conditions.';
COMMENT ON COLUMN application.terms_and_conditions.modified_date IS 'Date when the terms and conditions were last modified.';
COMMENT ON COLUMN application.terms_and_conditions.is_deleted IS 'Flag indicating if the terms and conditions are deleted.';
COMMENT ON COLUMN application.terms_and_conditions.body_ar IS 'Arabic body of the terms and conditions.';
COMMENT ON COLUMN application.terms_and_conditions.body_en IS 'English body of the terms and conditions.';
COMMENT ON COLUMN application.terms_and_conditions.link IS 'Link associated with the terms and conditions.';
COMMENT ON COLUMN application.terms_and_conditions.sorting IS 'Sorting value for the terms and conditions.';
COMMENT ON COLUMN application.terms_and_conditions.title_ar IS 'Arabic title of the terms and conditions.';
COMMENT ON COLUMN application.terms_and_conditions.title_en IS 'English title of the terms and conditions.';
COMMENT ON COLUMN application.terms_and_conditions.application_category_id IS 'Foreign key referencing the application category.';
COMMENT ON COLUMN application.terms_and_conditions.request_type_id IS 'Foreign key referencing the request type.';

-- Table: application.lk_task_eqm_items
COMMENT ON COLUMN application.lk_task_eqm_items.id IS 'Unique identifier for the task EQM item';
COMMENT ON COLUMN application.lk_task_eqm_items.code IS 'Code associated with the task EQM item';
COMMENT ON COLUMN application.lk_task_eqm_items.name_ar IS 'Arabic name of the task EQM item';
COMMENT ON COLUMN application.lk_task_eqm_items.name_en IS 'English name of the task EQM item';
COMMENT ON COLUMN application.lk_task_eqm_items.rating_value_type IS 'Type of rating value associated with the task EQM item';

-- Table: application.tm_agency_requests_documents
COMMENT ON COLUMN application.tm_agency_requests_documents.agency_request_id IS 'Foreign key referencing the agency request.';
COMMENT ON COLUMN application.tm_agency_requests_documents.document_id IS 'Foreign key referencing the document.';

-- Table: application.tm_agency_requests_services
COMMENT ON COLUMN application.tm_agency_requests_services.agency_request_id IS 'Foreign key referencing the agency request.';
COMMENT ON COLUMN application.tm_agency_requests_services.support_service_id IS 'Foreign key referencing the support service.';

-- Table: application.trademark_agency_requests
COMMENT ON COLUMN application.trademark_agency_requests.id IS 'Primary key for trademark agency requests.';
COMMENT ON COLUMN application.trademark_agency_requests.created_by_user IS 'User who created the trademark agency request.';
COMMENT ON COLUMN application.trademark_agency_requests.created_date IS 'Date when the trademark agency request was created.';
COMMENT ON COLUMN application.trademark_agency_requests.modified_by_user IS 'User who last modified the trademark agency request.';
COMMENT ON COLUMN application.trademark_agency_requests.modified_date IS 'Date when the trademark agency request was last modified.';
COMMENT ON COLUMN application.trademark_agency_requests.is_deleted IS 'Flag indicating if the trademark agency request is deleted.';
COMMENT ON COLUMN application.trademark_agency_requests.agency_checker_notes IS 'Notes from the agency checker.';
COMMENT ON COLUMN application.trademark_agency_requests.agency_number IS 'Agency number.';
COMMENT ON COLUMN application.trademark_agency_requests.agency_type IS 'Type of agency, (e.g, CHANGE_OWNERSHIP, SUPPORT_SERVICES, TRADEMARK_REGISTRATION)';
COMMENT ON COLUMN application.trademark_agency_requests.agent_customer_code IS 'Customer code of the agent.';
COMMENT ON COLUMN application.trademark_agency_requests.agent_expiry_date IS 'Expiry date of the agent.';
COMMENT ON COLUMN application.trademark_agency_requests.agent_type_id IS 'Foreign key referencing the agent type.';
COMMENT ON COLUMN application.trademark_agency_requests.checker_username IS 'Username of the checker.';
COMMENT ON COLUMN application.trademark_agency_requests.client_customer_code IS 'Customer code of the client.';
COMMENT ON COLUMN application.trademark_agency_requests.end_agency IS 'End date of the agency.';
COMMENT ON COLUMN application.trademark_agency_requests.agent_identity_number IS 'Identity number of the agent.';
COMMENT ON COLUMN application.trademark_agency_requests.legal_agent_type IS 'Type of legal agent, (e.g, INTERNAL, EXTERNAL)';
COMMENT ON COLUMN application.trademark_agency_requests.organization_address IS 'Address of the organization.';
COMMENT ON COLUMN application.trademark_agency_requests.organization_description IS 'Description of the organization.';
COMMENT ON COLUMN application.trademark_agency_requests.request_number IS 'Request number.';
COMMENT ON COLUMN application.trademark_agency_requests.start_agency IS 'Start date of the agency.';
COMMENT ON COLUMN application.trademark_agency_requests.application_id IS 'Foreign key referencing the application.';
COMMENT ON COLUMN application.trademark_agency_requests.client_type_id IS 'Foreign key referencing the client type.';
COMMENT ON COLUMN application.trademark_agency_requests.request_status_id IS 'Foreign key referencing the request status.';
COMMENT ON COLUMN application.trademark_agency_requests.process_request_id IS 'Foreign key referencing the process request.';
COMMENT ON COLUMN application.trademark_agency_requests.returned_request_num IS 'Returned request number.';

-- Table: application.trademark_agency_status_change_log
COMMENT ON COLUMN application.trademark_agency_status_change_log.id IS 'Primary key for trademark agency status change log.';
COMMENT ON COLUMN application.trademark_agency_status_change_log.created_by_user IS 'User who created the status change log.';
COMMENT ON COLUMN application.trademark_agency_status_change_log.created_date IS 'Date when the status change log was created.';
COMMENT ON COLUMN application.trademark_agency_status_change_log.modified_by_user IS 'User who last modified the status change log.';
COMMENT ON COLUMN application.trademark_agency_status_change_log.modified_date IS 'Date when the status change log was last modified.';
COMMENT ON COLUMN application.trademark_agency_status_change_log.is_deleted IS 'Flag indicating if the status change log is deleted.';
COMMENT ON COLUMN application.trademark_agency_status_change_log.task_definition_key IS 'Key associated with the task definition.';
COMMENT ON COLUMN application.trademark_agency_status_change_log.task_instance_id IS 'Instance ID associated with the task.';
COMMENT ON COLUMN application.trademark_agency_status_change_log.new_status_id IS 'New status ID.';
COMMENT ON COLUMN application.trademark_agency_status_change_log.previous_status_id IS 'Previous status ID.';
COMMENT ON COLUMN application.trademark_agency_status_change_log.trademark_agency_request_id IS 'Foreign key referencing the trademark agency request.';

-- Table: application.trademark_appeal_request
COMMENT ON COLUMN application.trademark_appeal_request.id IS 'Primary key for trademark appeal requests.';
COMMENT ON COLUMN application.trademark_appeal_request.appeal_reason IS 'Reason for the appeal.';
COMMENT ON COLUMN application.trademark_appeal_request.appeal_request_type IS 'Type of appeal request, (e.g, CHANGE_TM_IMAGE_SERVICE, ACCEPTANCE_WITH_CONDITION, SUBSTANTIVE_EXAMINATION_REJECTION)';
COMMENT ON COLUMN application.trademark_appeal_request.support_services_id IS 'Foreign key referencing the support services.';
COMMENT ON COLUMN application.trademark_appeal_request.final_decision_notes IS 'Notes on the final decision.';
COMMENT ON COLUMN application.trademark_appeal_request.department_reply IS 'Reply from the department.';
COMMENT ON COLUMN application.trademark_appeal_request.migration_stage IS 'Migration stage of the trademark appeal request.';

-- Table: application.trademark_appeal_request_documents
COMMENT ON COLUMN application.trademark_appeal_request_documents.trademark_appeal_request_id IS 'Foreign key referencing the trademark appeal request.';
COMMENT ON COLUMN application.trademark_appeal_request_documents.document_id IS 'Foreign key referencing the document.';

-- Table: application.trademark_application_modification
COMMENT ON COLUMN application.trademark_application_modification.id IS 'Primary key for trademark application modification.';
COMMENT ON COLUMN application.trademark_application_modification.created_by_user IS 'User who created the modification.';
COMMENT ON COLUMN application.trademark_application_modification.created_date IS 'Date when the modification was created.';
COMMENT ON COLUMN application.trademark_application_modification.modified_by_user IS 'User who last modified the modification.';
COMMENT ON COLUMN application.trademark_application_modification.modified_date IS 'Date when the modification was last modified.';
COMMENT ON COLUMN application.trademark_application_modification.is_deleted IS 'Flag indicating if the modification is deleted.';
COMMENT ON COLUMN application.trademark_application_modification.hijri_filing_date IS 'Hijri filing date.';
COMMENT ON COLUMN application.trademark_application_modification.filing_date IS 'Filing date.';
COMMENT ON COLUMN application.trademark_application_modification.new_mark_desc IS 'Description of the new mark.';
COMMENT ON COLUMN application.trademark_application_modification.new_mark_name_ar IS 'Arabic name of the new mark.';
COMMENT ON COLUMN application.trademark_application_modification.new_mark_name_en IS 'English name of the new mark.';
COMMENT ON COLUMN application.trademark_application_modification.new_mark_type IS 'Type of the new mark.';
COMMENT ON COLUMN application.trademark_application_modification.new_mark_type_desc IS 'Description of the new mark type.';
COMMENT ON COLUMN application.trademark_application_modification.old_mark_desc IS 'Description of the old mark.';
COMMENT ON COLUMN application.trademark_application_modification.old_mark_name_ar IS 'Arabic name of the old mark.';
COMMENT ON COLUMN application.trademark_application_modification.old_mark_name_en IS 'English name of the old mark.';
COMMENT ON COLUMN application.trademark_application_modification.old_mark_type IS 'Type of the old mark.';
COMMENT ON COLUMN application.trademark_application_modification.old_mark_type_desc IS 'Description of the old mark type.';
COMMENT ON COLUMN application.trademark_application_modification.updated IS 'Flag indicating if the modification is updated.';
COMMENT ON COLUMN application.trademark_application_modification.application_id IS 'Foreign key referencing the application.';
COMMENT ON COLUMN application.trademark_application_modification.old_tag_language_id IS 'Foreign key referencing the old tag language.';
COMMENT ON COLUMN application.trademark_application_modification.new_tag_language_id IS 'Foreign key referencing the new tag language.';

-- Table: application.trademark_details
COMMENT ON COLUMN application.trademark_details.id IS 'Primary key for trademark details.';
COMMENT ON COLUMN application.trademark_details.created_by_user IS 'User who created the trademark details.';
COMMENT ON COLUMN application.trademark_details.created_date IS 'Date when the trademark details were created.';
COMMENT ON COLUMN application.trademark_details.modified_by_user IS 'User who last modified the trademark details.';
COMMENT ON COLUMN application.trademark_details.modified_date IS 'Date when the trademark details were last modified.';
COMMENT ON COLUMN application.trademark_details.is_deleted IS 'Flag indicating if the trademark details are deleted.';
COMMENT ON COLUMN application.trademark_details.application_id IS 'Foreign key referencing the application.';
COMMENT ON COLUMN application.trademark_details.exhibition_date IS 'Exhibition date.';
COMMENT ON COLUMN application.trademark_details.exhibition_info IS 'Information about the exhibition.';
COMMENT ON COLUMN application.trademark_details.exposed_date IS 'Exposed date.';
COMMENT ON COLUMN application.trademark_details.have_exhibition IS 'Flag indicating if there is an exhibition.';
COMMENT ON COLUMN application.trademark_details.is_exposed_public IS 'Flag indicating if the exposure is public.';
COMMENT ON COLUMN application.trademark_details.mark_claiming_color IS 'Color claimed for the mark.';
COMMENT ON COLUMN application.trademark_details.mark_description IS 'Description of the mark.';
COMMENT ON COLUMN application.trademark_details.word_mark IS 'Word mark.';
COMMENT ON COLUMN application.trademark_details.mark_type_id IS 'Foreign key referencing the mark type.';
COMMENT ON COLUMN application.trademark_details.tag_language_id IS 'Foreign key referencing the tag language.';
COMMENT ON COLUMN application.trademark_details.tag_type_desc_id IS 'Foreign key referencing the tag type description.';
COMMENT ON COLUMN application.trademark_details.name_en IS 'English name.';
COMMENT ON COLUMN application.trademark_details.name_ar IS 'Arabic name.';
COMMENT ON COLUMN application.trademark_details.examiner_grant_condition IS 'Grant condition from the examiner.';
COMMENT ON COLUMN application.trademark_details.migration_stage IS 'Migration stage of the trademark details.';
