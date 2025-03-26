
alter table application.application_notes add column attribute_id int4;
create table application.lk_attributes (id int4 not null, code varchar(255), name_ar varchar(255), name_en varchar(255), primary key (id));
alter table application.lk_notes add column attribute_id int4;
alter table application.application_notes add constraint FKbpkxnsl4qddgdmxd7gu3o1g6g foreign key (attribute_id) references application.lk_attributes;
alter table application.lk_notes add constraint FKjlhk4bfjfxn3jjp4bvy6eg8nk foreign key (attribute_id) references application.lk_attributes;

INSERT INTO application.lk_attributes
(id, code, name_ar, name_en)
VALUES(1, 'NAME', 'الاسم', 'Name');

INSERT INTO application.lk_attributes
(id, code, name_ar, name_en)
VALUES(2, 'SUMMERY', 'الملخص', 'Summery');

INSERT INTO application.lk_attributes
(id, code, name_ar, name_en)
VALUES(3, 'FULL_SUMMERY', 'الوصف الكامل', 'Full Summery');

INSERT INTO application.lk_attributes
(id, code, name_ar, name_en)
VALUES(4, 'PROTECTION_ELEMENTS', 'عناصر الحماية', 'Protection Elements');

INSERT INTO application.lk_attributes
(id, code, name_ar, name_en)
VALUES(5, 'ILLUSTRATIONS', 'الأشكال التوضيحية', 'Illustrations');
