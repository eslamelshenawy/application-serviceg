INSERT INTO application.lk_publication_type
(id, code, name_ar, name_en, application_category_id)
VALUES((select (max(id) + 1) from application.lk_publication_type), 'LICENCE_REVOKE', 'شطب الترخيص', 'licence Revoke', (select id from application.lk_application_category where saip_code ='TRADEMARK'));


insert into application.lk_publication_type (id,code,name_ar,name_en,application_category_id)
values((select (max(id) + 1) from application.lk_publication_type) , 'LICENCE_REVOKE' , 'شطب الترخيص' , 'licence Revoke' , (select id from application.lk_application_category where saip_code ='INDUSTRIAL_DESIGN'));


insert into application.lk_publication_type (id,code,name_ar,name_en,application_category_id)
values((select (max(id) + 1) from application.lk_publication_type) , 'LICENCE_REVOKE' , 'شطب الترخيص' , 'licence Revoke' , (select id from application.lk_application_category where saip_code ='PATENT'));




