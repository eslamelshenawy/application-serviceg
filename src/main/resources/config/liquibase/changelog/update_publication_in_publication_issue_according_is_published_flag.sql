UPDATE application.application_publication
SET is_published = true
    FROM application.application_publication ap
JOIN application.publication_issue_application_publication piap ON piap.application_publication_id = ap.id
    JOIN application.publication_issue pi2 ON pi2.id = piap.publication_issue_id;