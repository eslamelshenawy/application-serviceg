-- create the new bridge table for many to many
DROP TABLE IF exists application.application_nice_classifications CASCADE;


create table application.application_nice_classifications (id int8 not null, created_by_user varchar(255), created_date timestamp, modified_by_user varchar(255), modified_date timestamp, is_deleted int4 not null, sub_classification_type varchar(255), application_id int8, classification_id int8, version_id int4, primary key (id));
alter table application.application_nice_classifications add constraint FKqaqh8cws78826a793ww6th04l foreign key (application_id) references application.applications_info;
alter table application.application_nice_classifications add constraint FKt88jow15f16996xg8d5utw6b9 foreign key (classification_id) references application.classifications;
alter table application.application_nice_classifications add constraint FKs6a68wp1bsa5pb2ww87x25bym foreign key (version_id) references application.lk_classification_versions;


-- migrate data

INSERT INTO application.application_nice_classifications (id, is_deleted, sub_classification_type, application_id, classification_id, version_id)
select nextval('application.id_seq') as id, 0 is_deleted,  ai.sub_classification_type sub_classification_type, ai.id application_id, ac.classification_id classification_id, ai.version_id version_id
from application.applications_info ai
join application.application_classification ac on ai.id = ac.application_id
where ai.classification_id is not null and ai.sub_classification_type is not null;


-- drop old bridge table
DROP TABLE IF exists application.application_classification;


--drop columns
ALTER TABLE application.applications_info  DROP COLUMN IF EXISTS classification_id;
ALTER TABLE application.applications_info  DROP COLUMN IF EXISTS version_id;
ALTER TABLE application.applications_info  DROP COLUMN IF EXISTS sub_classification_type;

