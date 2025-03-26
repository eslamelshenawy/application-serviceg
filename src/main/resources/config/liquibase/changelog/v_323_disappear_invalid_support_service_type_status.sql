-- OPPOSITION_REQUEST
------------------------
DELETE FROM application.support_service_type_status
WHERE lk_support_service_type_id=(SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'OPPOSITION_REQUEST' AND sc.category_id = 5)
            AND lk_support_service_status_id=(SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'DRAFT');
DELETE FROM application.support_service_type_status
WHERE lk_support_service_type_id=(SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'OPPOSITION_REQUEST' AND sc.category_id = 5)
            AND lk_support_service_status_id=(SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'UNDER_PROCEDURE');
DELETE FROM application.support_service_type_status
WHERE lk_support_service_type_id=(SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'OPPOSITION_REQUEST' AND sc.category_id = 5)
            AND lk_support_service_status_id=(SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'REJECTED');
DELETE FROM application.support_service_type_status
WHERE lk_support_service_type_id=(SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'OPPOSITION_REQUEST' AND sc.category_id = 5)
            AND lk_support_service_status_id=(SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'COMPLETED');
DELETE FROM application.support_service_type_status
WHERE lk_support_service_type_id=(SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'OPPOSITION_REQUEST' AND sc.category_id = 5)
            AND lk_support_service_status_id=(SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'WAIVED');


INSERT INTO application.lk_support_service_request_status (id, code, name_ar, name_en, name_ar_external, name_en_external)
VALUES ((select max(id) + 1 from application.lk_support_service_request_status ), 'NEW_OPPOSITION_REQUEST', 'معترض لدى إدارة العلامات',
'New Opposition Request', 'جديد', 'New');
INSERT INTO application.lk_support_service_request_status (id, code, name_ar, name_en, name_ar_external, name_en_external)
VALUES ((select max(id) + 1 from application.lk_support_service_request_status ), 'APPLICANT_ANSWER_OPPOSITION_DONE', 'تم الرد على الاعتراض من قبل مقدم الطلب',
'The objection was answered by the applicant', 'رد مقدم الطلب', 'Applicant Answer');
INSERT INTO application.lk_support_service_request_status (id, code, name_ar, name_en, name_ar_external, name_en_external)
VALUES ((select max(id) + 1 from application.lk_support_service_request_status ), 'UNDER_TRADEMARK_STUDY', 'تحت الدراسة من قبل العلامات التجارية',
'Under study by Trademark', 'تحت الدراسة', 'Under Procedure');
INSERT INTO application.lk_support_service_request_status (id, code, name_ar, name_en, name_ar_external, name_en_external)
VALUES ((select max(id) + 1 from application.lk_support_service_request_status ), 'OPPOSITION_REJECTED', 'رفض الاعتراض', 'Opposition Request Rejected', 'مرفوض', 'Rejected');
INSERT INTO application.lk_support_service_request_status (id, code, name_ar, name_en, name_ar_external, name_en_external)
VALUES ((select max(id) + 1 from application.lk_support_service_request_status ), 'OPPOSITION_ACCEPTED', 'قبول الاعتراض', 'Opposition Request Accepted', 'مقبول', 'Accepted');

INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'OPPOSITION_REQUEST' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'NEW_OPPOSITION_REQUEST')
       );
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'OPPOSITION_REQUEST' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'APPLICANT_ANSWER_OPPOSITION_DONE')
       );
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'OPPOSITION_REQUEST' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'UNDER_TRADEMARK_STUDY')
       );
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'OPPOSITION_REQUEST' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'OPPOSITION_REJECTED')
       );
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'OPPOSITION_REQUEST' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'OPPOSITION_ACCEPTED')
       );

----------------------------------------------------------------------------------------------------------------------------------
-- TRADEMARK_APPEAL_REQUEST
--------------------------
DELETE FROM application.support_service_type_status
WHERE lk_support_service_type_id=(SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'TRADEMARK_APPEAL_REQUEST' AND sc.category_id = 5)
            AND lk_support_service_status_id=(SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'APPROVED');

DELETE FROM application.support_service_type_status
WHERE lk_support_service_type_id=(SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'TRADEMARK_APPEAL_REQUEST' AND sc.category_id = 5)
            AND lk_support_service_status_id=(SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'COMPLETED');

DELETE FROM application.support_service_type_status
WHERE lk_support_service_type_id=(SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'TRADEMARK_APPEAL_REQUEST' AND sc.category_id = 5)
            AND lk_support_service_status_id=(SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'WITHDRAWAL');

DELETE FROM application.support_service_type_status
WHERE lk_support_service_type_id=(SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'TRADEMARK_APPEAL_REQUEST' AND sc.category_id = 5)
            AND lk_support_service_status_id=(SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'REJECTED_BY_MANAGEMENT');

DELETE FROM application.support_service_type_status
WHERE lk_support_service_type_id=(SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'TRADEMARK_APPEAL_REQUEST' AND sc.category_id = 5)
            AND lk_support_service_status_id=(SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'WAIVED');

UPDATE application.lk_support_service_request_status
SET name_ar='سداد رسوم التظلم'
WHERE code='PENDING_FEES_COMPLAINT';

UPDATE application.lk_support_service_request_status
SET name_ar='متظلم لدى لجنة التظلمات', name_ar_external='متظلم لدى لجنة التظلمات'
WHERE code='COMPLAINANT_TO_COMMITTEE';

UPDATE application.lk_support_service_request_status
SET name_ar='مقبول من قبل لجنة التظلمات', name_ar_external='مقبول من قبل لجنة التظلمات'
WHERE code='ACCEPTED_BY_COMMITTEE';

INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'TRADEMARK_APPEAL_REQUEST' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'REQUEST_CORRECTION')
       );

----------------------------------------------------------------------------------------------------------------------------------
-- TRADEMARK_APPLICATION_SEARCH
-------------------------------
DELETE FROM application.support_service_type_status
WHERE lk_support_service_type_id=(SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'TRADEMARK_APPLICATION_SEARCH' AND sc.category_id = 5)
            AND lk_support_service_status_id=(SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'REQUEST_CORRECTION');

DELETE FROM application.support_service_type_status
WHERE lk_support_service_type_id=(SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'TRADEMARK_APPLICATION_SEARCH' AND sc.category_id = 5)
            AND lk_support_service_status_id=(SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'REJECTED');

DELETE FROM application.support_service_type_status
WHERE lk_support_service_type_id=(SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'TRADEMARK_APPLICATION_SEARCH' AND sc.category_id = 5)
            AND lk_support_service_status_id=(SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'WAIVED');

DELETE FROM application.support_service_type_status
WHERE lk_support_service_type_id=(SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'TRADEMARK_APPLICATION_SEARCH' AND sc.category_id = 5)
            AND lk_support_service_status_id=(SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'HAVE_SIMILAR');

DELETE FROM application.support_service_type_status
WHERE lk_support_service_type_id=(SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'TRADEMARK_APPLICATION_SEARCH' AND sc.category_id = 5)
            AND lk_support_service_status_id=(SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'NOT_SIMILAR');

DELETE FROM application.support_service_type_status
WHERE lk_support_service_type_id=(SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'TRADEMARK_APPLICATION_SEARCH' AND sc.category_id = 5)
            AND lk_support_service_status_id=(SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'PAY_PUBLICATION_FEES');
----------------------------------------------------------------------------------------------------------------------------------
-- REVOKE_PRODUCTS
--------------------------
DELETE FROM application.support_service_type_status
WHERE lk_support_service_type_id=(SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'REVOKE_PRODUCTS' AND sc.category_id = 5)
            AND lk_support_service_status_id=(SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'TRADMARK_REVOKED');
DELETE FROM application.support_service_type_status
WHERE lk_support_service_type_id=(SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'REVOKE_PRODUCTS' AND sc.category_id = 5)
            AND lk_support_service_status_id=(SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'PAY_PUBLICATION_FEES');
DELETE FROM application.support_service_type_status
WHERE lk_support_service_type_id=(SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'REVOKE_PRODUCTS' AND sc.category_id = 5)
            AND lk_support_service_status_id=(SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'APPROVED');

DELETE FROM application.support_service_type_status
WHERE lk_support_service_type_id=(SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'REVOKE_PRODUCTS' AND sc.category_id = 5)
            AND lk_support_service_status_id=(SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'COMPLETED');

INSERT INTO application.lk_support_service_request_status (id, code, name_ar, name_en, name_ar_external, name_en_external)
VALUES ((select max(id) + 1 from application.lk_support_service_request_status ), 'PAY_APPLYING_PUBLICATION_FEES', 'سداد رسوم التقديم والنشر',
'Payment of application and publication fees', 'سداد رسوم التقديم والنشر', 'Payment of application and publication fees');

INSERT INTO application.lk_support_service_request_status (id, code, name_ar, name_en, name_ar_external, name_en_external)
VALUES ((select max(id) + 1 from application.lk_support_service_request_status ), 'ACCEPTANCE', 'مقبول', 'Acceptance', 'مقبول', 'Acceptance');

INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'REVOKE_PRODUCTS' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'PAY_APPLYING_PUBLICATION_FEES')
       );

INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'REVOKE_PRODUCTS' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'ACCEPTANCE')
       );