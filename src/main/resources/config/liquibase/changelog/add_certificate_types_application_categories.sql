create table application.certificate_types_application_categories (
                                                                    lk_certificate_type_id int4 not null,
                                                                    lk_category_id int8  not null,
                                                                    FOREIGN KEY (lk_certificate_type_id) REFERENCES application.lk_certificate_types(id),
                                                                    FOREIGN KEY (lk_category_id)  REFERENCES application.lk_application_category(id)
);


insert into application.certificate_types_application_categories (lk_certificate_type_id ,lk_category_id)
values(1 ,1);
insert into application.certificate_types_application_categories (lk_certificate_type_id ,lk_category_id)
values(2 ,1);
insert into application.certificate_types_application_categories (lk_certificate_type_id ,lk_category_id)
values(3 ,1);
insert into application.certificate_types_application_categories (lk_certificate_type_id ,lk_category_id)
values(4 ,1);
insert into application.certificate_types_application_categories (lk_certificate_type_id ,lk_category_id)
values(5 ,1);
insert into application.certificate_types_application_categories (lk_certificate_type_id ,lk_category_id)
values(6 ,1);
insert into application.certificate_types_application_categories (lk_certificate_type_id ,lk_category_id)
values(7 ,1);
insert into application.certificate_types_application_categories (lk_certificate_type_id ,lk_category_id)
values(8 ,1);
insert into application.certificate_types_application_categories (lk_certificate_type_id ,lk_category_id)
values(9 ,1);
insert into application.certificate_types_application_categories (lk_certificate_type_id ,lk_category_id)
values(10 ,1);


insert into application.certificate_types_application_categories (lk_certificate_type_id ,lk_category_id)
values(1 ,2);
insert into application.certificate_types_application_categories (lk_certificate_type_id ,lk_category_id)
values(2 ,2);
insert into application.certificate_types_application_categories (lk_certificate_type_id ,lk_category_id)
values(3 ,2);
insert into application.certificate_types_application_categories (lk_certificate_type_id ,lk_category_id)
values(4 ,2);
insert into application.certificate_types_application_categories (lk_certificate_type_id ,lk_category_id)
values(5 ,2);
insert into application.certificate_types_application_categories (lk_certificate_type_id ,lk_category_id)
values(6 ,2);
insert into application.certificate_types_application_categories (lk_certificate_type_id ,lk_category_id)
values(7 ,2);
insert into application.certificate_types_application_categories (lk_certificate_type_id ,lk_category_id)
values(8 ,2);
insert into application.certificate_types_application_categories (lk_certificate_type_id ,lk_category_id)
values(9 ,2);
insert into application.certificate_types_application_categories (lk_certificate_type_id ,lk_category_id)
values(10 ,2);

insert into application.certificate_types_application_categories (lk_certificate_type_id ,lk_category_id)
values(1 ,5);
insert into application.certificate_types_application_categories (lk_certificate_type_id ,lk_category_id)
values(2 ,5);
insert into application.certificate_types_application_categories (lk_certificate_type_id ,lk_category_id)
values(3 ,5);
insert into application.certificate_types_application_categories (lk_certificate_type_id ,lk_category_id)
values(4 ,5);
insert into application.certificate_types_application_categories (lk_certificate_type_id ,lk_category_id)
values(5 ,5);
insert into application.certificate_types_application_categories (lk_certificate_type_id ,lk_category_id)
values(6 ,5);
insert into application.certificate_types_application_categories (lk_certificate_type_id ,lk_category_id)
values(7 ,5);
insert into application.certificate_types_application_categories (lk_certificate_type_id ,lk_category_id)
values(8 ,5);
insert into application.certificate_types_application_categories (lk_certificate_type_id ,lk_category_id)
values(9 ,5);
insert into application.certificate_types_application_categories (lk_certificate_type_id ,lk_category_id)
values(10 ,5);