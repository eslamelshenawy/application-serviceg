 -------------------- Add application_category_id column on application_status table -------------------------------------------
 alter table application.lk_application_status
 add column application_category_id int8;

 alter table application.lk_application_status
 add constraint fk_application_status_category
 foreign key (application_category_id) references application.lk_application_category;