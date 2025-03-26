create table application.lk_pv_excellence_traits_features (
id int8 PRIMARY KEY,
created_by_user varchar(255) NULL,
created_date timestamp NULL,
modified_by_user varchar(255) NULL,
modified_date timestamp NULL,
is_deleted int4 NOT NULL,
code varchar(255) NULL,
name_ar varchar(255) NULL,
name_en varchar(255) NULL,
note varchar(250),
is_active BOOLEAN,
example varchar(250),
pv_excellence_traits_id int8,
CONSTRAINT pv_excellence_traits_fk FOREIGN KEY (pv_excellence_traits_id) REFERENCES application.lk_pv_excellence_traits(id)
);