UPDATE application.applications_info ap
SET last_internal_user_name = res.username
    FROM (
    SELECT users.application_id as applicationId, users.user_name AS username
    FROM application.application_users users
    JOIN (
        select application_id as applicationId, MAX(modified_date) AS latest_modified_date
        FROM application.application_users
        GROUP BY application_id
    ) latest ON users.application_id = latest.applicationId AND users.modified_date = latest.latest_modified_date
) AS res
WHERE ap.id = res.applicationId;