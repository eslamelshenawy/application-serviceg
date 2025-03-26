UPDATE application.application_support_services_type
SET request_status = (select stat.id from application.lk_support_service_request_status stat where stat.name_en = 'Waived')
WHERE request_status=(select stat.id from application.lk_support_service_request_status stat where stat.name_en = ' Waived');

UPDATE application.support_service_status_change_log
SET new_status_id = (select stat.id from application.lk_support_service_request_status stat where stat.name_en = 'Waived')
WHERE new_status_id = (select stat.id from application.lk_support_service_request_status stat where stat.name_en = ' Waived');

UPDATE application.support_service_status_change_log
SET previous_status_id =(select stat.id from application.lk_support_service_request_status stat where stat.name_en = 'Waived')
WHERE previous_status_id = (select stat.id from application.lk_support_service_request_status stat where stat.name_en = ' Waived');

DELETE FROM application.lk_support_service_request_status
WHERE name_en = ' Waived';