-- RENEWAL_FEES_PAY
------------------------
DELETE FROM application.support_service_type_status
WHERE lk_support_service_type_id=(SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'RENEWAL_FEES_PAY' AND sc.category_id = 5)
            AND lk_support_service_status_id=(SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'REQUEST_CORRECTION');
DELETE FROM application.support_service_type_status
WHERE lk_support_service_type_id=(SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'RENEWAL_FEES_PAY' AND sc.category_id = 5)
            AND lk_support_service_status_id=(SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'UNDER_PROCEDURE');
DELETE FROM application.support_service_type_status
WHERE lk_support_service_type_id=(SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'RENEWAL_FEES_PAY' AND sc.category_id = 5)
            AND lk_support_service_status_id=(SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'PAY_PUBLICATION_FEES');
DELETE FROM application.support_service_type_status
WHERE lk_support_service_type_id=(SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'RENEWAL_FEES_PAY' AND sc.category_id = 5)
            AND lk_support_service_status_id=(SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'REJECTED');
DELETE FROM application.support_service_type_status
WHERE lk_support_service_type_id=(SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'RENEWAL_FEES_PAY' AND sc.category_id = 5)
            AND lk_support_service_status_id=(SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'DRAFT');
DELETE FROM application.support_service_type_status
WHERE lk_support_service_type_id=(SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'RENEWAL_FEES_PAY' AND sc.category_id = 5)
            AND lk_support_service_status_id=(SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'PENDING_RENEWAL_FEES');