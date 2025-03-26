

DROP TABLE IF EXISTS application.change_ownership_customers;
DROP TABLE IF EXISTS application.change_ownership_request;


create  table  application.change_ownership_customers (id int8 not null, created_by_user varchar(255), created_date timestamp, modified_by_user varchar(255), modified_date timestamp, is_deleted int4 not null, customer_id int8, ownership_percentage int4 not null, change_ownership_request_id int8, primary key (id));
create table  application.change_ownership_request (id int8 not null, created_by_user varchar(255), created_date timestamp, modified_by_user varchar(255), modified_date timestamp, is_deleted int4 not null, change_owner_ship_type varchar(255), customer_id int8, percentage_doc_part int8, document_transfer_type varchar(255), ownership_transfer_type varchar(255), participants_count int4 not null, application_support_services_type_id int8, mm5_document_id int8, poa_document_id int8, support_document_id int8, waive_document_id int8, primary key (id));
alter table application.change_ownership_customers add constraint   FKk1la9m2jpu5klt1adxg4mxydx foreign key (change_ownership_request_id) references application.change_ownership_request;
alter table application.change_ownership_request add constraint  FK5fty8oad4y9ex6p3x1n2fqvk6 foreign key (application_support_services_type_id) references application.application_support_services_type;
alter table application.change_ownership_request add constraint  FKevm4kkrskm4mjx5g5hw5kx1ha foreign key (mm5_document_id) references application.documents;
alter table application.change_ownership_request add constraint   FKdph0kwh0o1trsug830ieyklym foreign key (poa_document_id) references application.documents;
alter table application.change_ownership_request add constraint   FKdbhsmde21ccqjfmyedjh4fyj4 foreign key (support_document_id) references application.documents;
alter table application.change_ownership_request add constraint   FKssmikrfdwx0c5v7n84tpetvrs foreign key (waive_document_id) references application.documents;
