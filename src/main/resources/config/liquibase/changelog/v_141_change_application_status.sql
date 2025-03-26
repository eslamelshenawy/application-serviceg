
CREATE TABLE if not exists application.application_status_change_log (
           id int8 NOT NULL,
           created_by_user varchar(255) NULL,
           created_date timestamp NULL,
           modified_by_user varchar(255) NULL,
           modified_date timestamp NULL,
           is_deleted int4 NOT NULL,
           description_code varchar(255) NULL,
           task_definition_key varchar(255) NULL,
           task_instance_id varchar(255) NULL,
           application_id int8 NULL,
           new_status_id int8 NULL,
           previous_status_id int8 NULL,
           CONSTRAINT application_status_change_log_pkey PRIMARY KEY (id),
           CONSTRAINT fk3lf5digm1xhtsxrvr0ef74jfj FOREIGN KEY (application_id) REFERENCES application.applications_info(id),
           CONSTRAINT fk6domhyjjloi8o08x9k60mg6e0 FOREIGN KEY (new_status_id) REFERENCES application.lk_application_status(id),
           CONSTRAINT fkro7frk02oqahuxd37wtnh93oo FOREIGN KEY (previous_status_id) REFERENCES application.lk_application_status(id)
);

