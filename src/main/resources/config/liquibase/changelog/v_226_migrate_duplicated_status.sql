
-- update status in application info
update application.applications_info ai
set application_status_id =
        (select id from  application.lk_application_status s where s.code = 'RETURN_TO_THE_CLASSIFICATIONS_OFFICIAL' and s.application_category_id = ai.lk_category_id)
where
        ai.application_status_id in (
        select id from  application.lk_application_status s where s.code = 'UNDER_PROCESS_BY_CLASSIFICATION_OFFICIAL'
    );


-- update pre status in application status change log
update application.application_status_change_log  lo
set previous_status_id  =
        (select id from  application.lk_application_status s where s.code = 'RETURN_TO_THE_CLASSIFICATIONS_OFFICIAL' and s.application_category_id = ai.lk_category_id)
    from application.applications_info ai
where
    lo.application_id = ai.id
  and lo.previous_status_id  in (
    select id from  application.lk_application_status s where s.code = 'UNDER_PROCESS_BY_CLASSIFICATION_OFFICIAL'
    );


-- update new status in application status change log
update application.application_status_change_log  lo
set new_status_id  =
        (select id from  application.lk_application_status s where s.code = 'RETURN_TO_THE_CLASSIFICATIONS_OFFICIAL' and s.application_category_id = ai.lk_category_id)
    from application.applications_info ai
where
    lo.application_id = ai.id
  and lo.new_status_id  in (
    select id from  application.lk_application_status s where s.code = 'UNDER_PROCESS_BY_CLASSIFICATION_OFFICIAL'
    );
