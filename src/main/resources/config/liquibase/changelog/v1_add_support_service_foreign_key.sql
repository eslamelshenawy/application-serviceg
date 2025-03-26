
ALTER TABLE application.application_edit_trademark_image_request
    ADD CONSTRAINT
        application_edit_trademark_image_support_service_id_foreign_key
        FOREIGN KEY (id)
            REFERENCES application.application_support_services_type (id);
