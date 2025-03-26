-- OPPOSITION_REQUEST
------------------------
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'OPPOSITION_REQUEST' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'DRAFT')
       );
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'OPPOSITION_REQUEST' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'UNDER_PROCEDURE')
       );
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'OPPOSITION_REQUEST' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'REJECTED')
       );
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'OPPOSITION_REQUEST' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'COMPLETED')
       );
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'OPPOSITION_REQUEST' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'WAIVED')
       );
----------------------------------------------------------------------------------------------------------------------------------
-- LICENSING_REGISTRATION
--------------------------
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'LICENSING_REGISTRATION' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'DRAFT')
       );
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'LICENSING_REGISTRATION' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'UNDER_PROCEDURE')
       );
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'LICENSING_REGISTRATION' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'REQUEST_CORRECTION')
       );
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'LICENSING_REGISTRATION' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'APPROVED')
       );
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'LICENSING_REGISTRATION' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'REJECTED')
       );
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'LICENSING_REGISTRATION' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'LICENSE_REVOKED')
       );
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'LICENSING_REGISTRATION' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'LICENSED')
       );
----------------------------------------------------------------------------------------------------------------------------------
-- TRADEMARK_APPEAL_REQUEST
--------------------------
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'TRADEMARK_APPEAL_REQUEST' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'APPROVED')
       );
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'TRADEMARK_APPEAL_REQUEST' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'COMPLETED')
       );
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'TRADEMARK_APPEAL_REQUEST' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'WITHDRAWAL')
       );
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'TRADEMARK_APPEAL_REQUEST' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'WAIVED')
       );
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'TRADEMARK_APPEAL_REQUEST' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'PENDING_FEES_COMPLAINT')
       );
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'TRADEMARK_APPEAL_REQUEST' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'ACCEPTED_BY_COMMITTEE')
       );
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'TRADEMARK_APPEAL_REQUEST' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'APPEAL_RETURNED_TO_DEPARTMENT')
       );
----------------------------------------------------------------------------------------------------------------------------------
-- RENEWAL_FEES_PAY
--------------------------
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'RENEWAL_FEES_PAY' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'PENDING_RENEWAL_FEES')
       );
----------------------------------------------------------------------------------------------------------------------------------
-- TRADEMARK_APPLICATION_SEARCH
--------------------------
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'TRADEMARK_APPLICATION_SEARCH' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'NOT_SIMILAR')
       );
----------------------------------------------------------------------------------------------------------------------------------
-- REVOKE_PRODUCTS
--------------------------
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'REVOKE_PRODUCTS' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'APPROVED')
       );
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'REVOKE_PRODUCTS' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'COMPLETED')
       );