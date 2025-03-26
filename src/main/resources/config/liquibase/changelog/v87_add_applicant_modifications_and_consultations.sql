create table application.trademark_application_modification
(
    id                 int8    not null,
    created_by_user    varchar(255),
    created_date       timestamp,
    modified_by_user   varchar(255),
    modified_date      timestamp,
    is_deleted         int4    not null,
    hijri_filing_date  varchar(255),
    filing_date        timestamp,
    new_mark_desc      varchar(255),
    new_mark_name_ar   varchar(255),
    new_mark_name_en   varchar(255),
    new_mark_type      int4,
    new_mark_type_desc int4,
    old_mark_desc      varchar(255),
    old_mark_name_ar   varchar(255),
    old_mark_name_en   varchar(255),
    old_mark_type      int4,
    old_mark_type_desc int4,
    updated            boolean not null,
    application_id     int8,
    primary key (id)
);

alter table application.trademark_application_modification
    add constraint FK5a00vs4ab5fbhh1tps53673lf foreign key (application_id) references application.applications_info;


create table application.examiner_consultation_comments
(
    id               int8 not null,
    created_by_user  varchar(255),
    created_date     timestamp,
    modified_by_user varchar(255),
    modified_date    timestamp,
    is_deleted       int4 not null,
    comment          TEXT,
    examiner_type    varchar(255),
    consultation_id  int8,
    primary key (id)
);
create table application.examiner_consultations
(
    id                   int8    not null,
    created_by_user      varchar(255),
    created_date         timestamp,
    modified_by_user     varchar(255),
    modified_date        timestamp,
    is_deleted           int4    not null,
    replayed             boolean not null,
    user_name_receiver   varchar(255),
    user_name_sender     varchar(255),
    receiver_document_id int8,
    application_id       int8,
    sender_document_id   int8,
    primary key (id)
);

alter table application.examiner_consultation_comments
    add constraint FKpjf36ox5a9upouswy4sxkey2k foreign key (consultation_id) references application.examiner_consultations ;
alter table application.examiner_consultations
    add constraint FKaj8fdh2u6stqj8obbqfxdqob3 foreign key (receiver_document_id) references application.documents ;
alter table application.examiner_consultations
    add constraint FKd36vlwbns4mlbv3uttj78728u foreign key (application_id) references application.applications_info ;
alter table application.examiner_consultations
    add constraint FKkcxoqhupk1ecps5660p04fjc0 foreign key (sender_document_id) references application.documents;