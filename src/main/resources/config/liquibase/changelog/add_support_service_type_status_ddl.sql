create table if not exists application.support_service_type_status
(
    lk_support_service_type_id   int8 not null,
    lk_support_service_status_id int4 not null,
    FOREIGN KEY (lk_support_service_type_id) REFERENCES application.lk_support_services (id),
    FOREIGN KEY (lk_support_service_status_id) REFERENCES application.lk_support_service_request_status (id),
    unique (lk_support_service_type_id, lk_support_service_status_id)
);

-- REQUEST_CORRECTION
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'OWNERSHIP_CHANGE' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'REQUEST_CORRECTION')
       );

-- UNDER_PROCEDURE
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'OWNERSHIP_CHANGE' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'UNDER_PROCEDURE')
       );

-- WAIVED
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'OWNERSHIP_CHANGE' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'WAIVED')
       );

-- TRANSFERRED_OWNERSHIP
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'OWNERSHIP_CHANGE' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'TRANSFERRED_OWNERSHIP')
       );

-- PAY_PUBLICATION_FEES
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'OWNERSHIP_CHANGE' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'PAY_PUBLICATION_FEES')
       );

-- REJECTED
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'OWNERSHIP_CHANGE' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'REJECTED')
       );

-- DRAFT
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'OWNERSHIP_CHANGE' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'DRAFT')
       );

---------------------------------
-- REQUEST_CORRECTION
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'RENEWAL_FEES_PAY' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'REQUEST_CORRECTION')
       );

-- UNDER_PROCEDURE
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'RENEWAL_FEES_PAY' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'UNDER_PROCEDURE')
       );

-- WAIVED
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'RENEWAL_FEES_PAY' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'WAIVED')
       );

-- PAY_PUBLICATION_FEES
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'RENEWAL_FEES_PAY' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'PAY_PUBLICATION_FEES')
       );

-- REJECTED
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'RENEWAL_FEES_PAY' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'REJECTED')
       );

-- DRAFT
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'RENEWAL_FEES_PAY' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'DRAFT')
       );

-- RENEWED
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'RENEWAL_FEES_PAY' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'RENEWED')
       );

----------------------------
-- REQUEST_CORRECTION
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'VOLUNTARY_REVOKE' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'REQUEST_CORRECTION')
       );

-- UNDER_PROCEDURE
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'VOLUNTARY_REVOKE' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'UNDER_PROCEDURE')
       );

-- WAIVED
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'VOLUNTARY_REVOKE' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'WAIVED')
       );

-- COMPLETED
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'VOLUNTARY_REVOKE' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'COMPLETED')
       );

-- PAY_PUBLICATION_FEES
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'VOLUNTARY_REVOKE' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'PAY_PUBLICATION_FEES')
       );

-- REJECTED
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'VOLUNTARY_REVOKE' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'REJECTED')
       );

-- DRAFT
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'VOLUNTARY_REVOKE' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'DRAFT')
       );

----------------------------------------
-- UNDER_PROCEDURE
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'REVOKE_BY_COURT_ORDER' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'UNDER_PROCEDURE')
       );

-- WAIVED
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'REVOKE_BY_COURT_ORDER' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'WAIVED')
       );

-- TRADMARK_REVOKED
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'REVOKE_BY_COURT_ORDER' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'TRADMARK_REVOKED')
       );

-- PAY_PUBLICATION_FEES
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'REVOKE_BY_COURT_ORDER' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'PAY_PUBLICATION_FEES')
       );

-- REJECTED
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'REVOKE_BY_COURT_ORDER' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'REJECTED')
       );

-- DRAFT
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'REVOKE_BY_COURT_ORDER' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'DRAFT')
       );

-- COURT_DOCUMENTS_CORRECTION
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'REVOKE_BY_COURT_ORDER' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'COURT_DOCUMENTS_CORRECTION')
       );
------------------------------------------------------------

-- REQUEST_CORRECTION
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'REVOKE_PRODUCTS' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'REQUEST_CORRECTION')
       );

-- UNDER_PROCEDURE
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'REVOKE_PRODUCTS' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'UNDER_PROCEDURE')
       );

-- WAIVED
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'REVOKE_PRODUCTS' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'WAIVED')
       );

-- TRADMARK_REVOKED
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'REVOKE_PRODUCTS' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'TRADMARK_REVOKED')
       );

-- PAY_PUBLICATION_FEES
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'REVOKE_PRODUCTS' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'PAY_PUBLICATION_FEES')
       );

-- REJECTED
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'REVOKE_PRODUCTS' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'REJECTED')
       );

-- DRAFT
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'REVOKE_PRODUCTS' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'DRAFT')
       );

----------------------------------------

-- REQUEST_CORRECTION
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'EDIT_TRADEMARK_NAME_ADDRESS' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'REQUEST_CORRECTION')
       );

-- UNDER_PROCEDURE
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'EDIT_TRADEMARK_NAME_ADDRESS' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'UNDER_PROCEDURE')
       );

-- WAIVED
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'EDIT_TRADEMARK_NAME_ADDRESS' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'WAIVED')
       );

-- COMPLETED
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'EDIT_TRADEMARK_NAME_ADDRESS' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'COMPLETED')
       );

-- PAY_PUBLICATION_FEES
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'EDIT_TRADEMARK_NAME_ADDRESS' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'PAY_PUBLICATION_FEES')
       );

-- REJECTED
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'EDIT_TRADEMARK_NAME_ADDRESS' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'REJECTED')
       );

-- DRAFT
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'EDIT_TRADEMARK_NAME_ADDRESS' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'DRAFT')
       );

----------------------------------------------------------
-- REQUEST_CORRECTION
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'TRADEMARK_APPLICATION_SEARCH' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'REQUEST_CORRECTION')
       );

-- UNDER_PROCEDURE
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'TRADEMARK_APPLICATION_SEARCH' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'UNDER_PROCEDURE')
       );

-- WAIVED
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'TRADEMARK_APPLICATION_SEARCH' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'WAIVED')
       );

-- COMPLETED
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'TRADEMARK_APPLICATION_SEARCH' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'COMPLETED')
       );

-- PAY_PUBLICATION_FEES
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'TRADEMARK_APPLICATION_SEARCH' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'PAY_PUBLICATION_FEES')
       );

-- REJECTED
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'TRADEMARK_APPLICATION_SEARCH' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'REJECTED')
       );

-- DRAFT
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'TRADEMARK_APPLICATION_SEARCH' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'DRAFT')
       );

-- HAVE_SIMILAR
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'TRADEMARK_APPLICATION_SEARCH' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'HAVE_SIMILAR')
       );
----------------------------------------

-- REQUEST_CORRECTION
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'REVOKE_LICENSE_REQUEST' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'REQUEST_CORRECTION')
       );

-- UNDER_PROCEDURE
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'REVOKE_LICENSE_REQUEST' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'UNDER_PROCEDURE')
       );

-- WAIVED
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'REVOKE_LICENSE_REQUEST' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'WAIVED')
       );

-- COMPLETED
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'REVOKE_LICENSE_REQUEST' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'COMPLETED')
       );

-- PAY_PUBLICATION_FEES
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'REVOKE_LICENSE_REQUEST' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'PAY_PUBLICATION_FEES')
       );

-- REJECTED
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'REVOKE_LICENSE_REQUEST' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'REJECTED')
       );

-- DRAFT
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'REVOKE_LICENSE_REQUEST' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'DRAFT')
       );
----------------------------------------
-- REJECTED_BY_MANAGEMENT
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'TRADEMARK_APPEAL_REQUEST' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'REJECTED_BY_MANAGEMENT')
       );

-- REJECTED_BY_APPEAL_COMMITTEE
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'TRADEMARK_APPEAL_REQUEST' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'REJECTED_BY_APPEAL_COMMITTEE')
       );

-- COMPLAINANT_TO_COMMITTEE
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'TRADEMARK_APPEAL_REQUEST' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'COMPLAINANT_TO_COMMITTEE')
       );
---------------------------
-- ACCEPTED_WITH_MOD
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'EDIT_TRADEMARK_IMAGE' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'ACCEPTED_WITH_MOD')
       );

-- ACCEPTED_MOD_REQUEST
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'EDIT_TRADEMARK_IMAGE' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'ACCEPTED_MOD_REQUEST')
       );

-- PENDING_IMG_FEES_MOD_REQ
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'EDIT_TRADEMARK_IMAGE' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'PENDING_IMG_FEES_MOD_REQ')
       );

-- PENDING_IMG_FEES_MOD_PUB
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'EDIT_TRADEMARK_IMAGE' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'PENDING_IMG_FEES_MOD_PUB')
       );

-- PUBLISHED_ELECTRONIC
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'EDIT_TRADEMARK_IMAGE' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'PUBLISHED_ELECTRONIC')
       );

-- TRADEMARK_IMAGE_MODIFIED
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'EDIT_TRADEMARK_IMAGE' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'TRADEMARK_IMAGE_MODIFIED')
       );

-- UNDER_REVIEW_VISUAL
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'EDIT_TRADEMARK_IMAGE' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'UNDER_REVIEW_VISUAL')
       );

-- UNDER_REVIEW_AUDITOR
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'EDIT_TRADEMARK_IMAGE' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'UNDER_REVIEW_AUDITOR')
       );

-- UNDER_REVIEW_SUBJECTIVE
INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
VALUES (
           (SELECT st.id
            FROM application.lk_support_services st
                     JOIN application.support_service_application_categories sc ON sc.support_service_id = st.id
            WHERE st.code = 'EDIT_TRADEMARK_IMAGE' AND sc.category_id = 5),
           (SELECT ss.id
            FROM application.lk_support_service_request_status ss
            WHERE ss.code = 'UNDER_REVIEW_SUBJECTIVE')
       );
