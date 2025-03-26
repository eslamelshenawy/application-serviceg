ALTER TABLE application.applications_info ADD is_plt bool NULL;
ALTER TABLE application.applications_info ADD plt_description varchar(255) NULL;
ALTER TABLE application.applications_info ADD plt_document int8 NULL;
ALTER TABLE application.applications_info ADD CONSTRAINT applications_info_fk FOREIGN KEY (plt_document) REFERENCES application.documents(id);

INSERT INTO application.lk_application_status (id,is_deleted,ips_status_desc_ar,ips_status_desc_en,code)
VALUES ((select (max(id) + 1) from application.lk_application_status),0,'استكمال متطلبات','Complete the requirements','COMPLETE_REQUIREMENTS');


