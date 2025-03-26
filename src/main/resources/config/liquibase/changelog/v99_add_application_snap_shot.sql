create table application.application_versions
(
    id                                    int8 not null,
    created_by_user                       varchar(255),
    created_date                          timestamp,
    modified_by_user                      varchar(255),
    modified_date                         timestamp,
    is_deleted                            int4 not null,
    application_info_dto                  TEXT,
    patent_detail_dto_with_change_log_dto TEXT,
    patent_request_dto                    TEXT,
    pct_dto                               TEXT,
    version_number                        int4,
    application_id                        int8,
    primary key (id)
);

alter table application.application_versions
    add constraint FKl190khr8m0ekwvteqcql6h9ai foreign key (application_id) references application.applications_info;