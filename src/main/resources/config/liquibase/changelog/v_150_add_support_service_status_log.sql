CREATE TABLE if not exists application.support_service_status_change_log (
               id int8 NOT NULL,
               created_by_user varchar(255) NULL,
               created_date timestamp NULL,
               modified_by_user varchar(255) NULL,
               modified_date timestamp NULL,
               is_deleted int4 NOT NULL,
               description_code varchar(255) NULL,
               task_definition_key varchar(255) NULL,
               task_instance_id varchar(255) NULL,
               new_status_id int4 NULL,
               previous_status_id int4 NULL,
               support_service_id int8 NULL,
               CONSTRAINT support_service_status_change_log_pkey PRIMARY KEY (id),
               CONSTRAINT fkb55n9l2hafkumes7ohnw3hisw FOREIGN KEY (new_status_id) REFERENCES application.lk_support_service_request_status(id),
               CONSTRAINT fkeysdw8f52ntvp0hixedks09lc FOREIGN KEY (previous_status_id) REFERENCES application.lk_support_service_request_status(id),
               CONSTRAINT fkimvfswyqgd7o4o3to5ey37hbf FOREIGN KEY (support_service_id) REFERENCES application.application_support_services_type(id)
);