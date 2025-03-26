

create table application.lk_monshaat_enterprise_Size (
id int8 PRIMARY KEY,
code varchar(255) NULL,
name_ar varchar(255) NULL,
name_en varchar(255) NULL
);

insert into application.lk_monshaat_enterprise_Size (id ,code , name_ar , name_en)
values(1 ,'MICRO' , 'متناهية الصغر' , 'micro');
insert into application.lk_monshaat_enterprise_Size (id ,code , name_ar , name_en)
values(2  ,'SMALL' , 'صغيرة' , 'small');
insert into application.lk_monshaat_enterprise_Size (id ,code , name_ar , name_en)
values(3  ,'MEDIUM' , 'متوسطة الحجم' , 'medium');
insert into application.lk_monshaat_enterprise_Size (id ,code , name_ar , name_en)
values(4  , 'LARGE' , 'كبيرة' , 'large');

ALTER TABLE application.applications_info
ADD COLUMN enterprise_size_id int8;

ALTER TABLE application.applications_info
ADD CONSTRAINT eneterprise_size
FOREIGN KEY (enterprise_size_id)
REFERENCES application.lk_monshaat_enterprise_size(id);



update application.applications_info set enterprise_size_id = 1
where lk_category_id = 1 ;