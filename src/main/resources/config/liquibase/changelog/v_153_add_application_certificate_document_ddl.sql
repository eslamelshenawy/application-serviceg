
create table application.application_certificate_documents
(id int8 not null, created_by_user varchar(255), created_date timestamp, modified_by_user varchar(255),
 modified_date timestamp, is_deleted int4 not null, version int4 not null, application_id int8,
  document_id int8, primary key (id));

alter table application.application_certificate_documents add constraint FKr8nehvy06r4lwdoaiwvkyevn2
    foreign key (application_id) references application.applications_info;
alter table application.application_certificate_documents add constraint FKhbw86cqdub6s6e5elq3x3o9jt
    foreign key (document_id) references application.documents;
