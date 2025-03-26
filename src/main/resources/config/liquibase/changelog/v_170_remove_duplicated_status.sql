
update application.applications_info  set
application_status_id = (select id from application.lk_application_status where code = 'RETURN_TO_THE_CLASSIFICATIONS_OFFICIAL'
and application_category_id = 5 and ips_status_desc_en_external='Under Process')
where application_status_id = (select id from application.lk_application_status where code = 'RETURN_TO_THE_CLASSIFICATIONS_OFFICIAL'
and application_category_id = 5 and ips_status_desc_en_external='COMPLETE_REQUIREMENTS');

update application.application_status_change_log ascl  set
new_status_id = (select id from application.lk_application_status where code = 'RETURN_TO_THE_CLASSIFICATIONS_OFFICIAL'
and application_category_id = 5 and ips_status_desc_en_external='Under Process')
where new_status_id = (select id from application.lk_application_status where code = 'RETURN_TO_THE_CLASSIFICATIONS_OFFICIAL'
and application_category_id = 5 and ips_status_desc_en_external='COMPLETE_REQUIREMENTS');


update application.application_status_change_log ascl  set
previous_status_id = (select id from application.lk_application_status where code = 'RETURN_TO_THE_CLASSIFICATIONS_OFFICIAL'
and application_category_id = 5 and ips_status_desc_en_external='Under Process')
where previous_status_id = (select id from application.lk_application_status where code = 'RETURN_TO_THE_CLASSIFICATIONS_OFFICIAL'
and application_category_id = 5 and ips_status_desc_en_external='COMPLETE_REQUIREMENTS');


delete from application.lk_application_status where code = 'RETURN_TO_THE_CLASSIFICATIONS_OFFICIAL'
and application_category_id = 5 and ips_status_desc_en_external='COMPLETE_REQUIREMENTS';

