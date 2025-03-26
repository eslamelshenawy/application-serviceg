
alter table application.classifications add column category_id int8;
alter table application.classifications add column unit_id int4;
create table application.lk_classification_units (id int4 not null, code varchar(255), name_ar varchar(255), name_en varchar(255), primary key (id));
alter table application.classifications add constraint FK5mtnqx01vabfe56w19crxap0c foreign key (category_id) references application.lk_application_category;
alter table application.classifications add constraint FK5m01qd4e5hef4ia106eqr5anc foreign key (unit_id) references application.lk_classification_units;

update application.classifications set category_id = (select id from application.lk_application_category where saip_code = 'TRADEMARK');