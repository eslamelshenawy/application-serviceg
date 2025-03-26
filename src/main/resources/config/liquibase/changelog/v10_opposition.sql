--liquibase formatted sql
-- changeset application-service:v10_opposition.sql

DROP TABLE IF exists application.opposition_documents;
DROP TABLE IF exists application.opposition_classification;
DROP TABLE IF exists application.opposition;


create table application.opposition (id int8 not null, created_by_user varchar(255), created_date timestamp, modified_by_user varchar(255), modified_date timestamp, is_deleted int4 not null, applicant_examiner_notes varchar(500), applicant_session_date date, applicant_session_file_url varchar(255), applicant_hearing_session_scheduled boolean, is_applicant_session_paid boolean, applicant_session_result varchar(255), applicant_session_time varchar(255), complainer_customer_id int8, final_decision varchar(255), final_notes varchar(500), head_examiner_notes_to_examiner varchar(500), session_date date, session_file_url varchar(255), hearing_session_scheduled boolean, is_session_paid boolean, session_result varchar(500), session_time varchar(255), head_examiner_confirmed boolean, legal_representative_email varchar(255), legal_representative_name varchar(255), legal_representative_phone varchar(255), opposition_number varchar(255), opposition_reason varchar(500), opposition_type varchar(255), application_id int8, primary key (id));
create table application.opposition_classification (opposition_id int8 not null, classification_id int8 not null);
create table application.opposition_documents (opposition_id int8 not null, document_id int8 not null);

alter table application.opposition_classification drop constraint if exists UKtgh18vih0ts0at426la0076jb;
alter table application.opposition_classification add constraint UKtgh18vih0ts0at426la0076jb unique (opposition_id, classification_id);
alter table application.opposition_documents drop constraint if exists UKkd58yr24yruty7fcbmcufktwn;
alter table application.opposition_documents add constraint UKkd58yr24yruty7fcbmcufktwn unique (opposition_id, document_id);
alter table application.opposition add constraint FKti2cpvf0bxq7hv9kfdki608q9 foreign key (application_id) references application.applications_info;
alter table application.opposition_classification add constraint FKhdb5avf0i4g89lkg7f51p5igl foreign key (classification_id) references application.classifications;
alter table application.opposition_classification add constraint FKqot3ao20pp7q2xp9fsip0kkxo foreign key (opposition_id) references application.opposition;
alter table application.opposition_documents add constraint FK3nwu2bwi2xgc48be569nq3v38 foreign key (document_id) references application.documents;
alter table application.opposition_documents add constraint FKi5j09lqel04yaf6qnxqes4gxm foreign key (opposition_id) references application.opposition;



