

 create table application.application_databases (id int8 not null, created_by_user varchar(255), created_date timestamp, modified_by_user varchar(255), modified_date timestamp, is_deleted int4 not null, other boolean not null, other_database_name varchar(255), application_id int8, database_id int4, primary key (id));
 create table application.application_document_comments (id int8 not null, created_by_user varchar(255), created_date timestamp, modified_by_user varchar(255), modified_date timestamp, is_deleted int4 not null, page_number int8 not null, paragraph_number int8 not null, comment_document_id int8, document_id int8, primary key (id));
 create table application.application_office_reports (id int8 not null, created_by_user varchar(255), created_date timestamp, modified_by_user varchar(255), modified_date timestamp, is_deleted int4 not null, application_id int8, document_id int8, office_id int4, primary key (id));
 create table application.application_other_documents (id int8 not null, created_by_user varchar(255), created_date timestamp, modified_by_user varchar(255), modified_date timestamp, is_deleted int4 not null, document_name varchar(255), application_id int8, document_id int8, primary key (id));
 create table application.application_similar_documents (id int8 not null, created_by_user varchar(255), created_date timestamp, modified_by_user varchar(255), modified_date timestamp, is_deleted int4 not null, country_id int8, document_date date, document_link varchar(255), document_number varchar(255), publication_number varchar(255), application_id int8, document_id int8, primary key (id));
 create table application.lk_databases (id int4 not null, code varchar(255), name_ar varchar(255), name_en varchar(255), primary key (id));
 create table application.lk_examination_offices (id int4 not null, code varchar(255), name_ar varchar(255), name_en varchar(255), primary key (id));
 create table application.lk_result_document_types (id int4 not null, code varchar(255), name_ar varchar(255), name_en varchar(255), primary key (id));
 create table application.application_search_results (id int8 not null, created_by_user varchar(255), created_date timestamp, modified_by_user varchar(255), modified_date timestamp, is_deleted int4 not null, country_id int8, relation_of_protection_elements varchar(255), result_date date, result_link varchar(255), application_id int8, document_id int8, primary key (id));
 alter table application.application_databases add constraint FKk6e3m1xprps53l3tp2m7oodt5 foreign key (application_id) references application.applications_info;
 alter table application.application_databases add constraint FKbdxcea2238qm0e89qa37n1nt0 foreign key (database_id) references application.lk_databases;
 alter table application.application_document_comments add constraint FKrvbitq4l5glslo83f8b2kpnhf foreign key (comment_document_id) references application.documents;
 alter table application.application_document_comments add constraint FKgn10jhlok0q8s4eli67dij5nl foreign key (document_id) references application.documents;
 alter table application.application_office_reports add constraint FK4a7bceq2u0qw661bhvqb3q4jm foreign key (application_id) references application.applications_info;
 alter table application.application_office_reports add constraint FK1i9cwxadlcgbfgapcmmjluxb3 foreign key (document_id) references application.documents;
 alter table application.application_office_reports add constraint FK1g3cpei71cb5uca4j3f6dkobw foreign key (office_id) references application.lk_examination_offices;
 alter table application.application_other_documents add constraint FK5g7328cav4f2flkkgs4xpbxf8 foreign key (application_id) references application.applications_info;
 alter table application.application_other_documents add constraint FKmju9h2ujbf89cmrewtkoyd4rt foreign key (document_id) references application.documents;
 alter table application.application_similar_documents add constraint FKd8yaw97hftdca9ph2qakmwg8k foreign key (application_id) references application.applications_info;
 alter table application.application_similar_documents add constraint FKlnuf3urjvem14uvsrgjtn6xw4 foreign key (document_id) references application.documents;
 alter table application.application_search_results add constraint FK72mqbdmsa5nf6sp6ajh0d4o0o foreign key (application_id) references application.applications_info;
 alter table application.application_search_results add constraint FK6y0y1l06ni2771l3ful8sa1hj foreign key (document_id) references application.documents;
