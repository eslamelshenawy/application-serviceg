create table application.support_service_application_categories (
support_service_id int8 not null,
category_id int8  not null,
FOREIGN KEY (support_service_id) REFERENCES application.lk_support_services(id),
FOREIGN KEY (category_id)  REFERENCES application.lk_application_category(id)
);


insert into application.support_service_application_categories (support_service_id , category_id)
values(1 , 1);
insert into application.support_service_application_categories (support_service_id , category_id)
values(1 , 2);


insert into application.support_service_application_categories (support_service_id , category_id)
values(2 , 1);
insert into application.support_service_application_categories (support_service_id , category_id)
values(2 , 2);

insert into application.support_service_application_categories (support_service_id , category_id)
values(3 , 1);
insert into application.support_service_application_categories (support_service_id , category_id)
values(3 , 2);


insert into application.support_service_application_categories (support_service_id , category_id)
values(7 , 1);
insert into application.support_service_application_categories (support_service_id , category_id)
values(7 , 2);
insert into application.support_service_application_categories (support_service_id , category_id)
values(7 , 5);



insert into application.support_service_application_categories (support_service_id , category_id)
values(5 , 1);
insert into application.support_service_application_categories (support_service_id , category_id)
values(5 , 2);


insert into application.support_service_application_categories (support_service_id , category_id)
values(4 , 1);
insert into application.support_service_application_categories (support_service_id , category_id)
values(4 , 2);



insert into application.support_service_application_categories (support_service_id , category_id)
values(8 , 1);


insert into application.support_service_application_categories (support_service_id , category_id)
values(10 , 1);
insert into application.support_service_application_categories (support_service_id , category_id)
values(10, 2);
insert into application.support_service_application_categories (support_service_id , category_id)
values(10, 5);



insert into application.support_service_application_categories (support_service_id , category_id)
values(9, 1);
insert into application.support_service_application_categories (support_service_id , category_id)
values(9, 2);
insert into application.support_service_application_categories (support_service_id , category_id)
values(9, 5);



insert into application.support_service_application_categories (support_service_id , category_id)
values(11, 1);
insert into application.support_service_application_categories (support_service_id , category_id)
values(11, 2);
insert into application.support_service_application_categories (support_service_id , category_id)
values(11, 5);


insert into application.support_service_application_categories (support_service_id , category_id)
values(12, 1);
insert into application.support_service_application_categories (support_service_id , category_id)
values(12, 2);
insert into application.support_service_application_categories (support_service_id , category_id)
values(12, 5);


insert into application.support_service_application_categories (support_service_id , category_id)
values(14, 1);



insert into application.support_service_application_categories (support_service_id , category_id)
values(15, 2);
insert into application.support_service_application_categories (support_service_id , category_id)
values(15, 5);


insert into application.support_service_application_categories (support_service_id , category_id)
values(16, 5);


insert into application.support_service_application_categories (support_service_id , category_id)
values(17, 5);


insert into application.support_service_application_categories (support_service_id , category_id)
values(18, 5);
