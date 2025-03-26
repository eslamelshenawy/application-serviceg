CREATE OR REPLACE VIEW application.application_support_service_vu
AS
SELECT
    service.id AS support_service_id,
    lk_service.code AS support_service_code,
    lk_service.name_en AS support_service_name_en,
    service.application_info_id AS application_info_id,
    service.payment_status AS payment_status,
    service.request_number AS request_number,
    status.code AS request_status_code,
    status.name_en AS request_status_name_en,
    service.process_request_id AS process_request_id

FROM application.application_support_Services_type service
         JOIN application.lk_support_service_request_status status
              ON service.request_status = status.id
         JOIN application.lk_support_services lk_service
              ON service.lk_support_service_type_id = lk_service.id
;