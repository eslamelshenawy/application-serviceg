CREATE TABLE if not exists application.trademark_agency_status_change_log (
                                                                              id int8 NOT NULL,
                                                                              created_by_user varchar(255) NULL,
    created_date timestamp NULL,
    modified_by_user varchar(255) NULL,
    modified_date timestamp NULL,
    is_deleted int4 NOT NULL,
    task_definition_key varchar(255) NULL,
    task_instance_id varchar(255) NULL,
    new_status_id int4 NULL,
    previous_status_id int4 NULL,
    trademark_agency_request_id int8 NULL,
    CONSTRAINT trademark_agency_status_change_log_pkey PRIMARY KEY (id),
    CONSTRAINT fk9tj3wenx0b3jlktpg1c4s3gf5 FOREIGN KEY (trademark_agency_request_id) REFERENCES application.trademark_agency_requests(id),
    CONSTRAINT fkc27j0fnmks04f7deno5smusxf FOREIGN KEY (previous_status_id) REFERENCES application.lk_tm_agency_request_status(id),
    CONSTRAINT fkrgbogf0rj9axnnpdgqlsqv4k6 FOREIGN KEY (new_status_id) REFERENCES application.lk_tm_agency_request_status(id)
    );