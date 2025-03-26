
-- TRADMARK_REVOKED
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'VOLUNTARY_REVOKE' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'TRADMARK_REVOKED')
       );

