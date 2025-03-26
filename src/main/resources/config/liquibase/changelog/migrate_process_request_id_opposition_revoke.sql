UPDATE application.application_support_services_type AS asst
SET process_request_id = asst2.process_request_id
FROM application.opposition_revoke_licence_request AS orlr
    INNER JOIN application.application_support_services_type AS asst2 ON orlr.revoke_licence_request_id = asst2.id
WHERE orlr.id = asst.id;