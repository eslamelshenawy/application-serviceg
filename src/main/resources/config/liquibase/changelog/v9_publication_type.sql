create table application.lk_publication_type(

                                                id int4 ,
                                                code varchar(259),
                                                name_ar varchar(259),
                                                name_en varchar(259),
                                                application_category_id int8,
                                                foreign key (application_category_id) references application.lk_application_category(id)
);

insert into application.lk_publication_type (id,code,name_ar,name_en,application_category_id)
values(1 , 'LICENCE_CANCELLATION' , 'الغاء الترخيص' , 'licence cancellation' , 5);
insert into application.lk_publication_type (id,"code","name_ar","name_en",application_category_id)
values(2 , 'TRADEMARK_RENEWAL' , 'تجديد علامة ' , 'TradeMark Renewal' , 5);
insert into application.lk_publication_type  (id,"code","name_ar","name_en",application_category_id)
values(3 , 'TRADEMARK_LICENCE_USE' , 'ترخيص استخدام علامة' , 'Trade Licence Use' , 5);
insert into application.lk_publication_type  (id,"code","name_ar","name_en",application_category_id)
values(4 , 'TRADEMARK_REGISTERATION' , 'تسجيل علامة' , 'TradeMark Registeration' , 5);
insert into application.lk_publication_type  (id,"code","name_ar","name_en",application_category_id)
values(5 , 'TRADEMARK_PICTURE_EDIT' , 'تعديل صورة علامة' , 'TradeMark Picture Edit' , 5);
insert into application.lk_publication_type  (id,"code","name_ar","name_en",application_category_id)
values(6 , 'OWNERORADDRESS_EDIT' , 'تعديل مالك او عنوان' , 'owner or address edit' , 5);
insert into application.lk_publication_type (id,"code","name_ar","name_en",application_category_id)
values(7 , 'TRADEMARK_PLEDGE' , 'رهن علامة' , 'TradeMark Pledge' , 5);
insert into application.lk_publication_type  (id,"code","name_ar","name_en",application_category_id)
values(8 , 'COURT_DELETION_ORDER' , 'شطب علامة بامر من المحكمة' , 'court deletion order' , 5);
insert into application.lk_publication_type  (id,"code","name_ar","name_en",application_category_id)
values(9 , 'VOLUNTARY_TRADEMARK_DELETION' , 'شطب علامة طوعي' , 'Voluntary Trademark Deletion' , 5);
insert into application.lk_publication_type  (id,"code","name_ar","name_en",application_category_id)
values(10 , 'UNLOCK_TRADEMARK' , 'فك رهن علامة' , 'Unlock Trademark' , 5);
insert into application.lk_publication_type  (id,"code","name_ar","name_en",application_category_id)
values(11 , ' PRODUCTS_LIMIT' , 'قصر المنتجات' , 'Products Limit' , 5);
insert into application.lk_publication_type  (id,"code","name_ar","name_en",application_category_id)
values(12 , ' OWNERSHIP_TRANSFER' , 'نقل ملكية' , 'Ownership Transfer' , 5);
