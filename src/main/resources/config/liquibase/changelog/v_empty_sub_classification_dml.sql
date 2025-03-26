
delete from application.application_sub_classifications asc2
where asc2.sub_classification_id in (
    select id from application.sub_classifications
    where name_en = '' and name_ar = '');

delete from application.revoke_products_sub_classifications asc2
where asc2.sub_classifications_id in (
    select id from application.sub_classifications
    where name_en = '' and name_ar = '');

delete from application.sub_classifications
where name_en = '' and name_ar = '';
