
create table application.certificates_request (id int8 not null, created_by_user varchar(255), created_date timestamp, modified_by_user varchar(255), modified_date timestamp, is_deleted int4 not null, application_request_status varchar(255), application_info_id int8, certificate_type_id int4, document_id int8, primary key (id));
alter table application.certificates_request add constraint FK4o4yjjc2r6jf07j7ltb8ryx0t foreign key (application_info_id) references application.applications_info;
alter table application.certificates_request add constraint FK1h29rlap9ljlsjn0lh7ioysuv foreign key (certificate_type_id) references application.lk_certificate_types;
alter table application.certificates_request add constraint FKrndx2ijnv5eykcrpn7j0onsk2 foreign key (document_id) references application.documents;
