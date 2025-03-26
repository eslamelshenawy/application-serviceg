Alter table application.application_publication
    add column if not exists support_service_id int8 null;
ALTER TABLE application.application_publication
    ADD CONSTRAINT FK_APPLICATION_PUBLICATION_ON_SUPPORT_SERVICE_TYPE FOREIGN KEY (support_service_id)
        REFERENCES application.application_support_services_type (id);
