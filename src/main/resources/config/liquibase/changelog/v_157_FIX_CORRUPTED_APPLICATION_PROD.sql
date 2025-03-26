--INSERT NEW STATUS
INSERT INTO application.lk_application_status
    (id, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external)
VALUES((SELECT MAX(ID)+1 FROM application.lk_application_status), 0, 'جديد', 'New', 'CORRUPTED', 'جديد', 'New');

--UPDATED CORRUPTED RECORDS IF ANY
UPDATE APPLICATION.APPLICATIONS_INFO
SET application_status_id = (SELECT ID FROM application.lk_application_status WHERE CODE = 'CORRUPTED')
WHERE ID IN (
 SELECT AI.ID
 FROM APPLICATION.APPLICATIONS_INFO AI
 LEFT JOIN APPLICATION.TRADEMARK_DETAILS TD
   ON AI.ID = TD.APPLICATION_ID
 WHERE TD.ID IS NULL
   AND AI.LK_CATEGORY_ID = 5
   AND AI.APPLICATION_NUMBER IS NOT NULL
   AND AI.FILING_DATE IS NOT NULL
 );