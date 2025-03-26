DROP TABLE IF EXISTS application.application_search_similars CASCADE;

create table application.application_search_similars(
id int8 PRIMARY KEY NOT NULL,
created_by_user varchar(255) NULL,
created_date timestamp NULL,
modified_by_user varchar(255) NULL,
modified_date timestamp NULL,
is_deleted int4 NOT NULL,
ip_search_id int8 ,
application_title varchar(250),
filing_number varchar(250),
saip_doc_id  varchar(250),
status varchar(250),
filing_date timestamp,
application_search_id int8,
FOREIGN KEY (application_search_id) REFERENCES application.application_search(id)
);