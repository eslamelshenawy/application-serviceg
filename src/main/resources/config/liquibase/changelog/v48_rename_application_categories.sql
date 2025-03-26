update application.lk_application_category
set application_category_desc_ar = 'براءات الاختراع' where saip_code = 'PATENT';
update application.lk_application_category
set application_category_desc_ar='النماذج الصناعية' where saip_code = 'INDUSTRIAL_DESIGN';
update application.lk_application_category
set application_category_desc_ar = 'الدارات المتكاملة'where saip_code ='INTEGRATED_CIRCUITS';
update application.lk_application_category
set application_category_desc_ar = 'الاصناف النباتية' where saip_code = 'PLANT_VARIETIES';
update application.lk_application_category
set application_category_desc_ar = 'ترخيص مزاولة أنشطة الملكية الفكرية' where saip_code = 'LICENSE';

insert into application.lk_application_category (id , created_by_user , created_date , modified_by_user , modified_date , saip_code , abbreviation , is_deleted , application_category_desc_ar , application_category_desc_en , application_category_is_active)
values(11 , null , null , null , null , 'ENPHATH' , 'EN' , 0 , 'الانفاذ','Enphath' , true);

