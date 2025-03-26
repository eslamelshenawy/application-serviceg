-- create lookup tables
create table application.lk_veena_assistant_department (id int8 not null, code varchar(255), name_ar varchar(255), name_en varchar(255), veena_department_id int8, primary key (id));
create table application.lk_veena_classification (id int8 not null, code varchar(255), name_ar varchar(255), name_en varchar(255), primary key (id));
create table application.lk_veena_department (id int8 not null, code varchar(255), name_ar varchar(255), name_en varchar(255), veena_classification_id int8, primary key (id));

-- add fk constrains
alter table application.lk_veena_assistant_department add constraint lk_veena_department_fk foreign key (veena_department_id) references application.lk_veena_department;
alter table application.lk_veena_department add constraint lk_veena_classification_fk foreign key (veena_classification_id) references application.lk_veena_classification;

-- insert sample data for test will be truncated once the read data will be ready
INSERT INTO application.lk_veena_classification (id,code,name_ar,name_en) VALUES
	 (1,'C1','1','1'),
	 (2,'C3','2','2');

INSERT INTO application.lk_veena_department (id,code,name_ar,name_en,veena_classification_id) VALUES
	 (1,'C1','1.1','1.1',1),
	 (2,'C2','1.2','1.2',1),
	 (3,'C3','2.1','2.1',2),
	 (4,'C4','2.2','2.2',2);

INSERT INTO application.lk_veena_assistant_department (id,code,name_ar,name_en,veena_department_id) VALUES
	 (1,'C1','1.1.1','1.1.1',1),
	 (2,'C3','1.1.2','1.1.2 2',1),
	 (3,'C1','1.2.1','1.2.1',2),
	 (4,'C1','2.1.1','2.1.1',3),
	 (5,'C1','2.2.1','2.2.1',4);


-- create lookup columns in application table
alter table application.applications_info add column veena_assistant_department_id int8;
alter table application.applications_info add column veena_classification_id int8;
alter table application.applications_info add column veena_department_id int8;
alter table application.applications_info add constraint veena_assistant_department_fk foreign key (veena_assistant_department_id) references application.lk_veena_assistant_department;
alter table application.applications_info add constraint veena_classification_fk foreign key (veena_classification_id) references application.lk_veena_classification;
alter table application.applications_info add constraint veena_department_id_fk foreign key (veena_department_id) references application.lk_veena_department;
