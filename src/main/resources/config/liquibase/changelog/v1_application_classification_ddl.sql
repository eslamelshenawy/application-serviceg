DROP TABLE IF EXISTS application.application_classification;
CREATE TABLE application.application_classification( application_id int8 not null,
classification_id int8 not null,
PRIMARY KEY (application_id, classification_id),
CONSTRAINT application_info_fk FOREIGN KEY(application_id) REFERENCES application.applications_info(id),
CONSTRAINT classification_fk FOREIGN KEY(classification_id) REFERENCES application.classifications(id)
);

alter table application.applications_info add column classification_notes varchar(255);
alter table application.applications_info add column classification_unit_id int8;
alter table application.applications_info add constraint applications_info__classification_unit_FK foreign key (classification_unit_id) references application.lk_classification_units;
