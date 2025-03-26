-- liquibase formatted sql
-- changeset application-service:v24_appeal_DDL.sql

-- CREATE APPEAL TABLE
DROP TABLE IF EXISTS application.appeal_request_documents;
DROP TABLE IF EXISTS application.appeal_committee_opinion;
DROP TABLE IF EXISTS application.appeal_request;

create table application.appeal_request (id int8 not null, created_by_user varchar(255), created_date timestamp, modified_by_user varchar(255), modified_date timestamp, is_deleted int4 not null, appeal_committee_decision varchar(255), appeal_committee_decision_comment varchar(500), appeal_reason varchar(500), checker_decision varchar(255), checker_final_notes varchar(500), head_checker_notes_to_checker varchar(500), head_checker_confirmed boolean, official_letter_comment varchar(500), application_support_services_type_id int8, primary key (id));
create table application.appeal_request_documents (appeal_request_id int8 not null, document_id int8 not null);
create table application.appeal_committee_opinion (id int8 not null, created_by_user varchar(255), created_date timestamp, modified_by_user varchar(255), modified_date timestamp, is_deleted int4 not null, appeal_committee_opinion varchar(500), appeal_request_id int8, document_id int8, primary key (id));

alter table application.appeal_committee_opinion add constraint FKop6bpb6fk2giodf3us7jy1m9w foreign key (appeal_request_id) references application.appeal_request;
alter table application.appeal_committee_opinion add constraint FKaqqxuh0io73pa65u4qrdf3rgl foreign key (document_id) references application.documents;
alter table application.appeal_request add constraint FKjmubfqo77tfwbh9fo5na1ahvl foreign key (application_support_services_type_id) references application.application_support_services_type;
alter table application.appeal_request_documents add constraint FKir34pmws61rcpe6krthy9l8oo foreign key (document_id) references application.documents;
alter table application.appeal_request_documents add constraint FKdvo4rhvb7xy326jffwfiladrb foreign key (appeal_request_id) references application.appeal_request;

