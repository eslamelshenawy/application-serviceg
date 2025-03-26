

create table application.substantive_examination_reports (id int8 not null, created_by_user varchar(255), created_date timestamp, modified_by_user varchar(255), modified_date timestamp, is_deleted int4 not null, examiner_opinion TEXT, examiner_recommendation TEXT, links varchar(3000), type varchar(255), application_info_id int8, document_id int8, primary key (id));
alter table application.substantive_examination_reports add constraint FKtf5bhm1k1f0nyqjy71nq7457p foreign key (application_info_id) references application.applications_info;
alter table application.substantive_examination_reports add constraint FKsv1jju6jhrewsrtggx1k34k7y foreign key (document_id) references application.documents;
